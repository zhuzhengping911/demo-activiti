package bpmn20;

import com.google.common.collect.Maps;
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
public class GatewayTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(GatewayTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"MyProcess-exclusiveGateway.bpmn20.xml"})
    public void testExclusiveGatewayTest()  {

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("score",91);
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task.name = {}",task.getName());

    }

    @Test
    @Deployment(resources = {"MyProcess-exclusiveGateway.bpmn20.xml"})
    public void testExclusiveGatewayTest2()  {

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("score",70);
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task.name = {}",task.getName());

    }


}
