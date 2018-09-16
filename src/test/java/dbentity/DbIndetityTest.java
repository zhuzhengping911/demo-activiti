package dbentity;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/17.
 */
public class DbIndetityTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DbIndetityTest.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti-mysql.cfg.xml");

    @Test
    public void testDbIndetityTest(){
        IdentityService identityService = activitiRule.getIdentityService();

        User user = identityService.newUser("user1");
        user.setFirstName("zhu");
        user.setLastName("zhengping");
        user.setEmail("zhuzhengping@qqq.com");
        user.setPassword("1234");
        identityService.saveUser(user);

        Group group = identityService.newGroup("group1");
        group.setName("test");
        identityService.saveGroup(group);

        identityService.createMembership(user.getId(),group.getId());

        User user2 = identityService.newUser("user2");
        user2.setFirstName("xiao");
        user2.setLastName("wen");
        user2.setEmail("wen@qqq.com");
        user2.setPassword("111");
        identityService.saveUser(user2);
        identityService.createMembership(user2.getId(),group.getId());

        identityService.setUserInfo(user.getId(),"age","18");
        identityService.setUserInfo(user.getId(),"address","shanghai");

    }

}
