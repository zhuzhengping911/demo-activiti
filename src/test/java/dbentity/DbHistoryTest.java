package dbentity;

import com.google.common.collect.Maps;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class DbHistoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbHistoryTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void DbHistoryTest(){
        activitiRule.getRepositoryService().createDeployment()
                .name("测试部署")
                .addClasspathResource("MyProcess.bpmn20.xml")
                .deploy();
        RuntimeService runtimeService = activitiRule.getRuntimeService();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key0","value0");
        varibles.put("key1","value1");
        varibles.put("key2","value2");
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess", varibles);
        runtimeService.setVariable(myProcess.getId(),"key1","value_1");
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceId(myProcess.getId()).singleResult();
        taskService.setOwner(task.getId(),"user1");
        taskService.createAttachment("url",task.getId(),
                myProcess.getId(),
                "name",
                "dsc",
                "/url/test.png");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"record note1");
        taskService.addComment(task.getId(),task.getProcessInstanceId(),"record note2");
        Map<String,String> properties = Maps.newHashMap();
        properties.put("key1","value2_1");
        properties.put("key2","value2_2");
        activitiRule.getFormService().submitStartFormData(task.getId(),properties);

    }
    @Test
    public void testByteArrayInsert(){


    }

}
