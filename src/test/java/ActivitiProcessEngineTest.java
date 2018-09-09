import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ActivitiProcessEngineTest {

    private static final Logger loger = LoggerFactory.getLogger(ActivitiProcessEngineTest.class);

    @Test
    public void SQLTest(){
        ProcessEngineConfiguration processEngineConfiguration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
        loger.info("config = {}",processEngineConfiguration);
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        loger.info("获取流程引擎{}",processEngine.getName());
        processEngine.close();

    }

    @Test
    public void SQLTest2(){
        ProcessEngineConfiguration processEngineConfiguration =
                ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti_druid.cfg.xml");
        loger.info("config = {}",processEngineConfiguration);
        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        loger.info("获取流程引擎{}",processEngine.getName());
        processEngine.close();

    }
}
