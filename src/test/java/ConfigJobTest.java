import org.activiti.engine.runtime.Job;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *
 * Job TEST
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ConfigJobTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigJobTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg_job.xml");

    /**
     * 定时任务测试
     * @throws InterruptedException
     */
    @Test
    @Deployment(resources = {"MyProcess_job.bpmn20.xml"})
    public void test() throws InterruptedException {
        LOGGER.info("START");

        List<Job> jobs = activitiRule.getManagementService().createTimerJobQuery().listPage(0,100);

        for (Job job : jobs){
            LOGGER.info("定时任务{},默认重拾次数{}",job,job.getRetries());
        }
        LOGGER.info("jobList.size={}",jobs.size());
        Thread.sleep(1000*100);
        LOGGER.info("END");

    }
}
