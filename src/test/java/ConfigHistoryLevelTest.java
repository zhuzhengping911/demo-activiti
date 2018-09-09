import com.google.common.collect.Maps;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
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
 *
 * History TEST
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ConfigHistoryLevelTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigHistoryLevelTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg_history.xml");

    @Test
    @Deployment(resources = {"MyProcess.bpmn20.xml"})
    public void test(){


        //        启动流程
        startProcessEngine();
        //        修改变量
        changeVaribles();
        //        提交表单
        submitForm();


//        输出历史内容
//        输出历史活动
        shouHistotyActive();
//        输出历史任务
        shouHistotyTask();
//        输出历史表单
        shouHistotyForm();
//        输出历史详情
        shouHistoryDetail();

    }

    private void shouHistoryDetail() {
        List<HistoricDetail> historicDetailQueries = activitiRule.getHistoryService()
                .createHistoricDetailQuery()
                .listPage(0,100);
        for (HistoricDetail historicDetail : historicDetailQueries){
            LOGGER.info("HistoricDetail = {}",historicDetail);
        }
        LOGGER.info("HistoricDetail.siz = {}",historicDetailQueries.size());
    }

    private void shouHistotyForm() {
        List<HistoricDetail> historicDetailFormQueries = activitiRule.getHistoryService()
                .createHistoricDetailQuery()
                .formProperties()
                .listPage(0,100);
        for (HistoricDetail historicDetail : historicDetailFormQueries){
            LOGGER.info("historicDetailFormQueries = {}",historicDetail);
        }
        LOGGER.info("historicDetailFormQueries.siz = {}",historicDetailFormQueries.size());
    }

    private void shouHistotyTask() {
        List<HistoricTaskInstance> historicTaskInstances = activitiRule.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .listPage(0,100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances){
            LOGGER.info("historicTaskInstance = {}",historicTaskInstance);
        }
        LOGGER.info("historicTaskInstance.siz = {}",historicTaskInstances.size());
    }

    private void shouHistotyActive() {
        List<HistoricActivityInstance> historicActivityInstances = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .listPage(0,100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances){
            LOGGER.info("historiActivityInstance = {}",historicActivityInstance);
        }
        LOGGER.info("historiActivityInstance.siz = {}",historicActivityInstances.size());
    }

    private void submitForm() {
        Task task =activitiRule.getTaskService().createTaskQuery().singleResult();
        Map<String, String> poperties = Maps.newHashMap();
        poperties.put("formKey1","valuef1");
        poperties.put("formKey2","valuef2");
        activitiRule.getFormService().submitStartFormData(task.getId(), poperties);
    }

    private void changeVaribles() {
        List<Execution> executions = activitiRule.getRuntimeService().createExecutionQuery()
                .listPage(0,100);
        for (Execution execution : executions){
            LOGGER.info("execution = {}",execution);
        }
        LOGGER.info("execution.size = {}",executions.size());

        String id = executions.iterator().next().getId();
        activitiRule.getRuntimeService().setVariable(id,"keyStart1","value1_");
    }

    private void startProcessEngine() {
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("keyStart1","value1");
        varibles.put("keyStart2","value2");
        ProcessInstance processInstance = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("myProcess",varibles);
    }
}
