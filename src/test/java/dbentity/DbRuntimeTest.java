package dbentity;

import com.google.common.collect.Maps;
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
public class DbRuntimeTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbRuntimeTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testDbRuntimeTest(){
        activitiRule.getRepositoryService().createDeployment()
                .name("二次审批流程")
                .addClasspathResource("second_approve.bpmn20.xml")
                .deploy();
        Map<String,Object> varibles = Maps.newHashMap();
        varibles.put("key1","value1");
        activitiRule.getRuntimeService()
                .startProcessInstanceByKey("secode_approve",varibles);
    }

    @Test
    public void testSuspend(){

    }

}