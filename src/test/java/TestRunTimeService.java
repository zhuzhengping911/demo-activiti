import com.google.common.collect.Maps;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceBuilder;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * RunTimeService Test
 * Created by zhuzhengping
 * on 2018/9/10.
 */
public class TestRunTimeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRunTimeService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 通过Key启动流程
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testStartProcess(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",varibles);
        LOGGER.info("processInstance = {}",processInstance);
    }

    /**
     * 通过ID启动流程
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testStartProcessById(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessDefinition processDefinition = activitiRule.getRepositoryService().createProcessDefinitionQuery().singleResult();

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),varibles);
        LOGGER.info("processInstance = {}",processInstance);
    }

    /**
     * 实例构造器测试
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testStartProcessInstanceBuilder(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();
        ProcessInstance processInstance = processInstanceBuilder.businessKey("business001")
                .processDefinitionKey("myProcess")
                .variables(varibles)
                .start();
        LOGGER.info("processInstance = {}",processInstance);
    }

    /**
     * 变量测试
     */

    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testVariables(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        varibles.put("key2","value2");
        varibles.put("key3","value3");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",varibles);
        LOGGER.info("processInstance = {}",processInstance);
        runtimeService.setVariable(processInstance.getId(),"key3","123");
        runtimeService.setVariable(processInstance.getId(),"key2","321");
        Map<String, Object> variables = runtimeService.getVariables(processInstance.getId());
        LOGGER.info("variables = {}",variables);
    }

    /**
     * 流程是咧测试
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testProcessInstanceQuery(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",varibles);
        LOGGER.info("processInstance = {}",processInstance);

        ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .singleResult();
    }

    /**
     * 执行流测试
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testExecutionQuery(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess",varibles);
        LOGGER.info("processInstance = {}",processInstance);

        List<Execution> executions = runtimeService.createExecutionQuery()
                .listPage(0, 100);
        for (Execution execution:executions){
            LOGGER.info("Execution = {}",execution);

        }
    }
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess_tragger.bpmn20.xml")
    public void testTrigger(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess");
        Execution execution = runtimeService.createExecutionQuery()
                .activityId("someTask")
                .singleResult();
        LOGGER.info("execution ={}",execution);
        runtimeService.trigger(execution.getId());
        LOGGER.info("execution ={}",execution);

    }

    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess_Signal.bpmn20.xml")
    public void testSignalCatchingEvent(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess");
        Execution execution = runtimeService.createExecutionQuery().signalEventSubscriptionName("my-signal").singleResult();
        LOGGER.info("execution= {}",execution);
        runtimeService.signalEventReceived("my-signal");
        Execution execution1= runtimeService.createExecutionQuery().signalEventSubscriptionName("my-signal").singleResult();
        LOGGER.info("execution1= {}",execution1);
    }

    /**
     * messageReceived
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess_message.bpmn20.xml")
    public void testMessageEventReceived(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess");
        Execution execution = runtimeService.createExecutionQuery().messageEventSubscriptionName("my-message").singleResult();
        LOGGER.info("execution= {}",execution);
        runtimeService.messageEventReceived("my-message",execution.getId());
        Execution execution1= runtimeService.createExecutionQuery().messageEventSubscriptionName("my-message").singleResult();
        LOGGER.info("execution1= {}",execution1);
    }

    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess_messageEvent.bpmn20.xml")
    public void testMessage(){
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        ProcessInstance myProcess = runtimeService
//                .startProcessInstanceByKey("myProcess");
                .startProcessInstanceByMessage("my-message");
        LOGGER.info("processInstance = {}",myProcess);
    }
}
