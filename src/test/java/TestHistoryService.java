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
     * task获取变量，描述
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
        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().listPage(0, 100);
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            LOGGER.info("historicProcessInstance = {}",historicProcessInstance);
        }
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery().listPage(0, 100);
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            LOGGER.info("historicActivityInstance = {}",historicActivityInstance);
        }

        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery().listPage(0, 100);
        for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
            LOGGER.info("historicTaskInstances = {}",historicTaskInstance);
        }
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().listPage(0, 100);
        for (HistoricVariableInstance historicVariableInstance : historicVariableInstances) {
            LOGGER.info("historicVariableInstance = {}",historicVariableInstance);
        }
        List<HistoricDetail> historicDetails = historyService.createHistoricDetailQuery().listPage(0, 100);
        for (HistoricDetail historicDetail : historicDetails) {
            LOGGER.info("historicDetail = {}",historicDetail);
        }

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
        historyService.deleteHistoricProcessInstance(myProcess.getId());
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(myProcess.getId())
                .singleResult();
        LOGGER.info("historicProcessInstance = {}",historicProcessInstance);

    }

}
