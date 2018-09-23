package com.zzp.activiti.springboot.springboot;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {
    Logger logger = LoggerFactory.getLogger(SpringbootApplicationTests.class);

    @Autowired
    public RuntimeService runtimeService;

    @Test
    public void contextLoads() {
        ProcessInstance myProcess = runtimeService.startProcessInstanceByKey("myProcess");
        logger.info(" this is the instance = {}",myProcess);

    }

}
