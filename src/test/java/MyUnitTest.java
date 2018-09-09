import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class MyUnitTest {

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = {"/MyProcess.bpmn20.xml"})
    public void test(){
        ProcessInstance processInstance = activitiRule.getRuntimeService().startProcessInstanceById("myProcess");
        Assert.assertNotNull(processInstance);
        Task task =activitiRule.getTaskService().createTaskQuery().singleResult();
        Assert.assertEquals("Activiti is awesome",task.getName());
        activitiRule.getTaskService().complete(task.getId());

    }
}
