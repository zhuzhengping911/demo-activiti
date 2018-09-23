package bpmn20;

import com.google.common.collect.Maps;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class SupProcessTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupProcessTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 子流程
     * 候选人确认
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"MyProcess-subProcess.bpmn20.xml"})
    public void testSupProcessTest() throws InterruptedException {

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("errorFlag",true);
        ProcessInstance myProcess = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("myProcess",varibles);

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("user1 task = {}",task);
        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(myProcess.getId());
        LOGGER.info("variables = {}",variables);

    }

    /**
     * 事件子流程，可以获取local变量，如果是全局的流程，可以捕获到所有错误
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"MyProcess-subProcess2.bpmn20.xml"})
    public void testSupProcessTest2() throws InterruptedException {

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("errorFlag",true);
        varibles.put("key1","value1");
        ProcessInstance myProcess = activitiRule.getRuntimeService()
                .startProcessInstanceByKey("myProcess",varibles);

        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("user1 task = {}",task);
        Map<String, Object> variables = activitiRule.getRuntimeService().getVariables(myProcess.getId());
        LOGGER.info("variables = {}",variables);

    }

}
