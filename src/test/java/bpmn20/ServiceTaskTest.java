package bpmn20;

import com.google.common.collect.Maps;
import org.activiti.engine.ActivitiEngineAgenda;
import org.activiti.engine.ManagementService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
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
 * on 2018/9/17.
 */
public class ServiceTaskTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTaskTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 使用delegate
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"MyProcess-ServiceTask.bpmn20.xml"})
    public void testServiceTaskDelegate() throws InterruptedException {
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        List<HistoricActivityInstance> list = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance historicProcessInstance : list) {
            LOGGER.info("activity = {}",historicProcessInstance);
        }


    }

    /**
     * 使用behavior
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"MyProcess-ServiceTask2.bpmn20.xml"})
    public void testServiceTaskBehaivor() throws InterruptedException {
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        List<HistoricActivityInstance> list = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance historicProcessInstance : list) {
            LOGGER.info("activity = {}",historicProcessInstance);
        }
        final Execution execution = activitiRule.getRuntimeService().createExecutionQuery()
                .activityId("someTask")
                .singleResult();
        LOGGER.info("execution = {}",execution);

        ManagementService managementService = activitiRule.getManagementService();
        managementService.executeCommand(new Command<Object>() {
            @Override
            public Object execute(CommandContext commandContext) {
                ActivitiEngineAgenda agenda = commandContext.getAgenda();
                agenda.planTakeOutgoingSequenceFlowsOperation((ExecutionEntity) execution,false);
                return null;
            }
        });
        list = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance historicProcessInstance : list) {
            LOGGER.info("activity = {}",historicProcessInstance);
        }


    }

    /**
     * 传入字符串或表达式获取的字符串常量
     */
    @Test
    @Deployment(resources = {"MyProcess-ServiceTask3.bpmn20.xml"})
    public void testServiceTaskDelegate2()  {
        Map<String, Object> varibles = Maps.newHashMap();
        varibles.put("desc","THE TSET JAVA DELEGATE");
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);

    }

}
