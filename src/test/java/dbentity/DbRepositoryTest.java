package dbentity;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class DbRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbRepositoryTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testDploy(){
        activitiRule.getRepositoryService().createDeployment()
                .name("二次审批流程")
                .addClasspathResource("second_approve.bpmn20.xml")
                .deploy();
    }

    @Test
    public void testSuspend(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        repositoryService.suspendProcessDefinitionById("secode_approve:2:7504");
//        挂起
        boolean processDefinitionSuspended = repositoryService.isProcessDefinitionSuspended("secode_approve:2:7504");
        LOGGER.info("" + processDefinitionSuspended);
    }

}
