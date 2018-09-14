import org.activiti.engine.FormService;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    @Deployment(resources = "")
    public void tastIndentityService(){
        FormService formService = activitiRule.getFormService();
    }

}
