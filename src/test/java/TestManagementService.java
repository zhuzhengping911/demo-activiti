import com.zzp.activiti.demo.mapper.MyCustomMapper;
import org.activiti.engine.ManagementService;
import org.activiti.engine.impl.cmd.AbstractCustomSqlExecution;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.management.TablePage;
import org.activiti.engine.runtime.DeadLetterJobQuery;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.activiti.engine.runtime.SuspendedJobQuery;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/12.
 */
public class TestManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestManagementService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti_job.cfg.xml");

    /**
     * task获取变量，描述
     */
    @Test
    @Deployment(resources = "MyProcess-job.bpmn20.xml")
    public void testManagementService(){
        ManagementService managementService = activitiRule.getManagementService();
        List<Job> jobs = managementService.createTimerJobQuery().listPage(0, 100);
        for (Job job : jobs) {
            LOGGER.info("job = {}",job);
        }
        JobQuery jobQuery = managementService.createJobQuery();
        SuspendedJobQuery suspendedJobQuery = managementService.createSuspendedJobQuery();
        DeadLetterJobQuery deadLetterJobQuery = managementService.createDeadLetterJobQuery();



    }

    @Test
    @Deployment(resources = "MyProcess-job.bpmn20.xml")
    public void testTablePageQuery(){
        ManagementService managementService = activitiRule.getManagementService();
        TablePage tablePage = managementService.createTablePageQuery()
                .tableName(managementService.getTableName(ProcessDefinitionEntity.class))
                .listPage(0, 100);

        List<Map<String, Object>> rows = tablePage.getRows();
        for (Map<String, Object> row : rows) {
            LOGGER.info("row = {}",row);
        }



    }

    @Test
    @Deployment(resources = "MyProcess.bpmn20.xml")
    public void testCustomQuery(){
        activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        ManagementService managementService = activitiRule.getManagementService();
        List<Map<String, Object>> maps = managementService.executeCustomSql(new AbstractCustomSqlExecution<MyCustomMapper, List<Map<String, Object>>>(MyCustomMapper.class) {
            @Override
            public List<Map<String, Object>> execute(MyCustomMapper o) {
                return o.findAll();
            }
        });
        for (Map<String, Object> map : maps) {
            LOGGER.info("map = {}",map);
        }


    }

    @Test
    @Deployment(resources = "MyProcess.bpmn20.xml")
    public void testCommand(){
        activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess");
        ManagementService managementService = activitiRule.getManagementService();
        ProcessDefinitionEntity myProcess = managementService.executeCommand(new Command<ProcessDefinitionEntity>() {
            @Override
            public ProcessDefinitionEntity execute(CommandContext commandContext) {
                ProcessDefinitionEntity myProcess = commandContext.getProcessDefinitionEntityManager()
                        .findLatestProcessDefinitionByKey("myProcess");
                return myProcess;
            }
        });
        LOGGER.info("ProcessDefinitionEntity = {}",myProcess);


    }

}
