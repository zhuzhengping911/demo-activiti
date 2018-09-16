import com.google.common.collect.Maps;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/12.
 */
public class TestHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestHistoryService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_history.cfg.xml");

    /**
     * 历史变量获取，持久化
     */
    @Test
    @Deployment(resources = "MyProcess.bpmn20.xml")
    public void tastHistoryService(){
        HistoryService historyService = activitiRule.getHistoryService();
        ProcessInstanceBuilder processInstanceBuilder = activitiRule.getRuntimeService().createProcessInstanceBuilder();
        Map<String,Object> variable = Maps.newHashMap();
        variable.put("key1","value1");
        variable.put("key2","value2");
        variable.put("key3","value3");
        Map<String,Object> transientVariable = Maps.newHashMap();
        variable.put("tkey1","tvalue1");
        ProcessInstance myProcess = processInstanceBuilder.processDefinitionKey("myProcess")
                .variables(variable)
                .transientVariables(transientVariable).start();
        activitiRule.getRuntimeService().setVariable(myProcess.getId(),"key1","value_1");

        Task task = activitiRule.getTaskService().createTaskQuery().processInstanceId(myProcess.getId()).singleResult();
        Map<String, String> properties = Maps.newHashMap();
        properties.put("fkey1","fvalue1");
        properties.put("key2","value_2");
        activitiRule.getFormService().submitTaskFormData(task.getId(),properties);
//        获取历史的流程实例
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().listPage(0, 100);
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            LOGGER.info("historicProcessInstance = {}",historicProcessInstance);
        }
//        获取历史节点实例
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().listPage(0, 100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}",historicActivityInstance);
        }
//        获取历史Task
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            LOGGER.info("historicTaskInstances = {}",historicTaskInstance);
        }
//        获取历史变量（持久化）
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance = {}",historicVariableInstance);
        }
//        获取历史细节
        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            LOGGER.info("historicDetail = {}",historicDetail);
        }
//        获取历史日志，变量，表单，留言，任务，变量的变动
        ProcessInstanceHistoryLog processInstanceHistoryLog = historyService.createProcessInstanceHistoryLogQuery(myProcess.getId())
                .includeVariables()
                .includeFormProperties()
                .includeComments()
                .includeTasks()
                .includeVariableUpdates()
                .singleResult();
        List<HistoricData> historicData = processInstanceHistoryLog.getHistoricData();
        for (HistoricData historicDatum : historicData) {
            LOGGER.info("historicDatum = {}",historicDatum);
        }
//        删除历史流程实例
        historyService.deleteHistoricProcessInstance(myProcess.getId());
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(myProcess.getId())
                .singleResult();
        LOGGER.info("historicProcessInstance = {}",historicProcessInstance);

    }

}
