import org.activiti.engine.event.EventLogEntry;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * MDC TEST
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ConfigEventLogTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigEventLogTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg_eventlog.xml");

    @Test
    @Deployment(resources = {"MyProcess.bpmn20.xml"})
    public void test(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        Task task =activitiRule.getTaskService().createTaskQuery().singleResult();
        List<EventLogEntry> eventLogEntries = activitiRule.getManagementService().getEventLogEntriesByProcessInstanceId(processInstance.getProcessDefinitionId());

        for (EventLogEntry eventLogEntry : eventLogEntries){
            LOGGER.info("eventLog.type = {},eventLog.data={}",eventLogEntry.getType(),new String(eventLogEntry.getData()));
        }
        LOGGER.info("eventLogEntries.size={}",eventLogEntries.size());
    }
}
