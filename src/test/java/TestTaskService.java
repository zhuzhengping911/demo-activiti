import com.google.common.collect.Maps;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/12.
 */
public class TestTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTaskService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @Test
    @Deployment(resources = "MyProcess-task.bpmn20.xml")
    public void tastTaskService(){

        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("message","Its Test!!!!!");
        activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        TaskService taskService = activitiRule.getTaskService();
        Task task = taskService.createTaskQuery().singleResult();
        LOGGER.info("Task = {}", ToStringBuilder.reflectionToString(task, ToStringStyle.SIMPLE_STYLE));
        LOGGER.info("Task.description = {}",task.getDescription());
        taskService.setVariable(task.getId(),"key1","value1");
        taskService.setVariableLocal(task.getId(),"localKey1","localValue1");
        Map<String, Object> variables = taskService.getVariables(task.getId());
        Map<String, Object> variablesLocal = taskService.getVariablesLocal(task.getId());
        Map<String, Object> variables1 = activitiRule.getRuntimeService().getVariables(task.getExecutionId());
        LOGGER.info( "variables = {}",variables);
        LOGGER.info( "variablesLocal = {}",variablesLocal);
        LOGGER.info( "variables1 = {}",variables1);
        Map<String,Object> maps = Maps.newHashMap();
        maps.put("cKey1","cValue1");
        taskService.complete(task.getId(),maps);
        Task task1 = taskService.createTaskQuery().taskId(task.getId()).singleResult();
        LOGGER.info( "Task1 = {}",task1);
    }
}
