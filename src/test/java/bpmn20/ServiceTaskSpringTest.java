package bpmn20;

import com.google.common.collect.Maps;
import com.zzp.activiti.demo.delegate.MyJavaDelegate;
import com.zzp.activiti.demo.pojo.MyJavaBean;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:activiti.cfg_ServiceTaskSpring.xml")
public class ServiceTaskSpringTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTaskSpringTest.class);

    @Resource
    @Rule
    public ActivitiRule activitiRule;

    @Test
    @Deployment(resources = {"MyProcess-ServiceTask4.bpmn20.xml"})
    public void testServiceTaskDelegate() throws InterruptedException {
        Map<String, Object> varibles = Maps.newHashMap();
        MyJavaDelegate myJavaDelegate = new MyJavaDelegate();
        LOGGER.info("myJavaDelegate = {}",myJavaDelegate);
        varibles.put("desc","THE TSET JAVA DELEGATE");
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        List<HistoricActivityInstance> list = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance historicProcessInstance : list) {
            LOGGER.info("activity = {}",historicProcessInstance);
        }


    }


    @Test
    @Deployment(resources = {"MyProcess-ServiceTask5.bpmn20.xml"})
    public void testServiceTaskDelegate5() throws InterruptedException {
        Map<String, Object> varibles = Maps.newHashMap();
        MyJavaBean myJavaBean = new MyJavaBean("test");
        LOGGER.info("myJavaBean = {}",myJavaBean);
        varibles.put("myJavaBean",myJavaBean);
        ProcessInstance myProcess = activitiRule.getRuntimeService().startProcessInstanceByKey("myProcess",varibles);
        List<HistoricActivityInstance> list = activitiRule.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list();
        for (HistoricActivityInstance historicProcessInstance : list) {
            LOGGER.info("activity = {}",historicProcessInstance);
        }


    }

}
