import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhuzhengping
 * on 2018/9/10.
 */
public class TestRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRepository.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 流程部署测试
     */
    @Test
    public void testRepository(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();

        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();

        deploymentBuilder.name("测试部署资源1")
                .addClasspathResource("second_approve.bpmn")
                .addClasspathResource("MyProcess.bpmn20.xml");
        Deployment deployment = deploymentBuilder.deploy();
        LOGGER.info("deploy = {}",deployment);

        DeploymentBuilder deploymentBuilder1 = repositoryService.createDeployment();
        deploymentBuilder1.name("测试部署资源2")
                .addClasspathResource("second_approve.bpmn")
                .addClasspathResource("MyProcess.bpmn20.xml");
        deploymentBuilder1.deploy();

        DeploymentQuery deploymentQuery = repositoryService.createDeploymentQuery();
//        Deployment deploy =
        List<Deployment> deploymentList =
                  deploymentQuery
//                .deploymentId(deployment.getId())
                .orderByDeploymenTime().asc()
                .listPage(0,100);
//                .singleResult();
        for (Deployment deployment1 : deploymentList){
            LOGGER.info("deployMent = {}",deployment);
        }
        LOGGER.info("deploymentList.size() = {}",deploymentList.size());
        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
//                .deploymentId(deploy.getId())
                .orderByProcessDefinitionKey().asc()
                .listPage(0,100);
        for(ProcessDefinition processDefinition : processDefinitions){
            LOGGER.info("processDefiniton = {},version = {},key={},id={}",
                    processDefinition,
                    processDefinition.getVersion(),
                    processDefinition.getKey(),
                    processDefinition.getId());
        }
    }

    /**
     * 流程暂停与激活
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testSuspend(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        LOGGER.info("processDefiniton.id={}",processDefinition.getId());
        repositoryService.suspendProcessDefinitionById(processDefinition.getId());
        try {
            LOGGER.info("启动");
            activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
            LOGGER.info("启动成功");
        }catch (Exception e){
            LOGGER.info("启动失败");
            LOGGER.info(e.getMessage(),e);
        }
        repositoryService.activateProcessDefinitionById(processDefinition.getId());
        LOGGER.info("启动");
        activitiRule.getRuntimeService().startProcessInstanceById(processDefinition.getId());
        LOGGER.info("启动成功");
    }

    /**
     * 用户与用户组测试
     */
    @Test
    @org.activiti.engine.test.Deployment(resources = "MyProcess.bpmn20.xml")
    public void testCandidateStarter(){
        RepositoryService repositoryService = activitiRule.getRepositoryService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().singleResult();
        LOGGER.info("processDefiniton.id={}",processDefinition.getId());
        repositoryService.addCandidateStarterUser(processDefinition.getId(),"user");
        repositoryService.addCandidateStarterGroup(processDefinition.getId(),"groupM");
        List<IdentityLink> identityLinkList = repositoryService.getIdentityLinksForProcessDefinition(processDefinition.getId());
        for (IdentityLink identityLink :identityLinkList){
            LOGGER.info("identityLink = {}",identityLink);
        }
        repositoryService.deleteCandidateStarterGroup(processDefinition.getId(),"groupM");
        repositoryService.deleteCandidateStarterUser(processDefinition.getId(),"user");
    }
}
