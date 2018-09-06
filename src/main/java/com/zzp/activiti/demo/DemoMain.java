package com.zzp.activiti.demo;

import com.google.common.collect.Maps;
import org.activiti.engine.*;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.form.DateFormType;
import org.activiti.engine.impl.form.StringFormType;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * Created by zhuzhengping
 * on 2018/9/6.
 */
public class DemoMain {

    private static final Logger loger = LoggerFactory.getLogger(DemoMain.class);

    public static void main(String[] args) throws ParseException {
        loger.info("start -------");

        /**
         * 创建流程引擎
         * 1。创建流程引擎配置对象
         * 2。配置对象获取流程引擎
         *
         */

        ProcessEngine processEngine = getProcessEngine();

        String name = processEngine.getName();
        String VERSION = ProcessEngine.VERSION;

        loger.info("流程引擎名称[{}],版本[{}]",name,VERSION);

        /**
         * 部署流程定义文件
         * 1。通过引擎获取资源service
         * 2。通过service获取流程加载器
         * 3。加载流程图
         * 4。部署
         * 5。通过ID获取流程定义对象
         */
        ProcessDefinition processDefinition = getProcessDefinition(processEngine);
        loger.info("流程定义文件[{}],流程ID[{}]",processDefinition.getName(),processDefinition.getId());

        /**
         * 启动运行流程
         * 1。获取运行时service
         * 2。启动流程
         */
        ProcessInstance processInstance = getProcessInstance(processEngine, processDefinition);
        loger.info("启动流程{}",processInstance.getProcessDefinitionKey());

        /**
         * 处理流程任务
         * 1。通过引擎获取任务Service
         * 2。通过任务service获取流程图上所有节点
         * 3。处理
         * 4。通过complete将节点结束
         * 5。处理完一个任务节点后要将流程对象指向下一个节点
         */
        processTask(processEngine, processInstance);
        loger.info("end ------------");
    }




    private static void processTask(ProcessEngine processEngine, ProcessInstance processInstance) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        while (processInstance != null && !processInstance.isEnded()){

            TaskService taskService = processEngine.getTaskService();
            List<Task> list = taskService.createTaskQuery().list();
            loger.info("待处理任务数量[{}]",list.size());

            for (Task task : list){
                loger.info("待处理任务[{}]",task.getName());
                Map<String, Object> variables = getMap(processEngine, scanner, task);
                taskService.complete(task.getId(),variables);
                processInstance = processEngine.getRuntimeService().
                        createProcessInstanceQuery().
                        processInstanceId(processInstance.getId())
                        .singleResult();
            }
        }
        scanner.close();
    }

    private static Map<String, Object> getMap(ProcessEngine processEngine, Scanner scanner, Task task) throws ParseException {
        FormService formService = processEngine.getFormService();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        Map<String,Object> variables = Maps.newHashMap();

        for (FormProperty formProperty : formProperties){

            String line = null;
            if (StringFormType.class.isInstance(formProperty.getType())){

                loger.info("请输入{} ?",formProperty.getName());
                line = scanner.nextLine();
                variables.put(formProperty.getId(),line);
            }else if (DateFormType.class.isInstance(formProperty.getType())){

                loger.info("请输入  {} ? 格式 (yyyy-MM-dd)",formProperty.getName());
                line = scanner.nextLine();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(line);
                variables.put(formProperty.getId(),date);
            }else {

                loger.info("类型暂不支持，{}",formProperty.getType());
            }
            loger.info("您输入的内容是[{}]",line);
        }
        return variables;
    }

    private static ProcessInstance getProcessInstance(ProcessEngine processEngine, ProcessDefinition processDefinition) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        return runtimeService.startProcessInstanceById(processDefinition.getId());
    }

    private static ProcessDefinition getProcessDefinition(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        deploymentBuilder.addClasspathResource("second_approve.bpmn");
        Deployment deployment = deploymentBuilder.deploy();
        String deployId = deployment.getId();
        return repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployId)
                .singleResult();
    }

    private static ProcessEngine getProcessEngine() {
        ProcessEngineConfiguration cfg = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        return cfg.buildProcessEngine();
    }
}
