import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * nterceptorTest TEST
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ConfigInterceptorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigInterceptorTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg_interceptor.xml");

    /**
     * 拦截器测试
     */
    @Test
    @Deployment(resources = {"MyProcess.bpmn20.xml"})
    public void test(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        Task task =activitiRule.getTaskService().createTaskQuery().singleResult();
        activitiRule.getTaskService().complete(task.getId());

    }
}
