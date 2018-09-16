import com.google.common.collect.Maps;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/12.
 */
public class TestFormService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestFormService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 用户，用户组，关系
     */
    @Test
    @Deployment(resources = "MyProcess-form.bpmn20.xml")
    public void tastIndentityService(){
        FormService formService = activitiRule.getFormService();
        ProcessDefinition processDefinition = activitiRule.getRepositoryService().createProcessDefinitionQuery().singleResult();
        String startFormKey = formService.getStartFormKey(processDefinition.getId());
        LOGGER.info("startFormKey = {}",startFormKey);

        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties){
            LOGGER.info("formProperty = {}", ToStringBuilder.reflectionToString(formProperty, ToStringStyle.SIMPLE_STYLE));
        }
        Map<String,String> properties = Maps.newHashMap();
        properties.put("message","my test message");
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), properties);
        Task task = activitiRule.getTaskService().createTaskQuery().singleResult();
        TaskFormData taskFormData = formService.getTaskFormData(task.getId());
        List<FormProperty> formProperties1 = taskFormData.getFormProperties();
        for (FormProperty formProperty : formProperties1){
            LOGGER.info("TaskPropertie = {}", ToStringBuilder.reflectionToString(formProperty, ToStringStyle.SIMPLE_STYLE));
        }
        HashMap<String, String> map = Maps.<String, String>newHashMap();
        map.put("yesORno","yes");
        formService.submitTaskFormData(task.getId(), map);
        Task task1 = activitiRule.getTaskService().createTaskQuery().singleResult();
        LOGGER.info("task1 = {}",task1);
    }

}
