package bpmn20;

import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class UserTaskTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserTaskTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"MyProcess-userTask.bpmn20.xml"})
    public void testTimerEventTest() throws InterruptedException {

        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateUser("user1").singleResult();
        LOGGER.info("user1 task = {}",task);
        task = taskService.createTaskQuery().taskCandidateUser("user2").singleResult();
        LOGGER.info("user2 task = {}",task);
        task = taskService.createTaskQuery().taskCandidateGroup("group1").singleResult();
        LOGGER.info("group1 task = {}",task);
        taskService.claim(task.getId(),"user2");
//        taskService.setAssignee(task.getId(),"user2");
        LOGGER.info("cliam task.id = {} by user2",task.getId());
        task = taskService.createTaskQuery().taskCandidateOrAssigned("user1").singleResult();
        LOGGER.info("user1 task = {}",task);
        task = taskService.createTaskQuery().taskCandidateOrAssigned("user2").singleResult();
        LOGGER.info("user2 task = {}",task);

    }

    @Test
    @Deployment(resources = {"MyProcess-userTask2.bpmn20.xml"})
    public void testTimerEventTest2() throws InterruptedException {

        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().taskCandidateUser("user1").singleResult();
        LOGGER.info("user1 task = {}",task);
        taskService.complete(task.getId());

    }

}
