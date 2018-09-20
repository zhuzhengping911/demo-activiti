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

import java.util.List;
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

    /**
     * 单一网管，根据条件只会走一条线，注意默认
     */
    @Test
    @Deployment(resources = {"MyProcess-exclusiveGateway.bpmn20.xml"})
    public void testExclusiveGatewayTest2()  {

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("score",70);
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task.name = {}",task.getName());

    }

    /**
     * 多路网管，多条线都会走，有end和start
     */
    @Test
    @Deployment(resources = {"MyProcess-parelleGateway.bpmn20.xml"})
    public void testParallelGatewayTest()  {

        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        List<Task> tasks = activitiRule.getTaskService().createTaskQuery().processInstanceId(myProcess.getId()).listPage(0, 100);
        tasks.stream().forEach(x -> {
            System.out.println(x.getName());
            activitiRule.getTaskService().complete(x.getId());
        });
        LOGGER.info("tasks.size = {}",tasks.size());
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task.name = {}",task.getName());

    }
    /**
     * 还有包容性网管，可能两条线都执行，也可能只执行一条线，支持并发，合并（有end）
     * 事件网关，根据捕获事件的结果执行一条线流程会暂停，事件订阅，铺货事件，单一执行
     */

}
