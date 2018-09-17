package bpmn20;

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
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class TimerEventTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimerEventTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg-timer.xml");

    @Test
    @Deployment(resources = {"MyProcess-timer-boundary.bpmn20.xml"})
    public void testTimerEventTest() throws InterruptedException {

        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");

        List<Task> tasks = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : tasks) {
            LOGGER.info("task.name = {}",task.getName());
        }
        LOGGER.info("tasks.size = {}",tasks.size());
        Thread.sleep(1000*15L);

        tasks = activitiRule.getTaskService().createTaskQuery().listPage(0, 100);
        for (Task task : tasks) {
            LOGGER.info("task.name = {}",task.getName());
        }
        LOGGER.info("tasks.size = {}",tasks.size());
    }

}
