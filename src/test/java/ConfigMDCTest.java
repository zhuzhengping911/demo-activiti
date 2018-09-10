import org.activiti.engine.logging.LogMDC;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * MDC TEST
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ConfigMDCTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMDCTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg_mdc.xml");

    /**
     * MDC日志测试
     */
    @Test
    @Deployment(resources = {"MyProcess.bpmn20.xml"})
    public void test(){
        LogMDC.setMDCEnabled(true);
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        Assert.assertNotNull(processInstance);
        Task task =activitiRule.getTaskService().createTaskQuery().singleResult();
        Assert.assertEquals("Activiti is awesome",task.getName());
        activitiRule.getTaskService().complete(task.getId());

    }
}
