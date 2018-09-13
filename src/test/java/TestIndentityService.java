import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by zhuzhengping
 * on 2018/9/12.
 */
public class TestIndentityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestIndentityService.class);

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    /**
     * 用户，用户组，关系
     */
    @Test
    public void tastIndentityService(){
        IdentityService identityService = activitiRule.getIdentityService();
        User user1 = identityService.newUser("user1");
        user1.setEmail("zzp@163.com");
        User user2 = identityService.newUser("user2");
        user2.setEmail("user2@163.com");
        identityService.saveUser(user1);
        identityService.saveUser(user2);
        Group group1 = identityService.newGroup("group1");
        identityService.saveGroup(group1);
        Group group2 = identityService.newGroup("group2");
        identityService.saveGroup(group2);
        identityService.createMembership("user1","group1");
        identityService.createMembership("user2","group1");
        identityService.createMembership("user1","group2");
        List<User> group11 = identityService.createUserQuery().memberOfGroup("group1").listPage(0, 100);
        for (User user : group11){
            LOGGER.info("user = {}", ToStringBuilder.reflectionToString(user, ToStringStyle.SHORT_PREFIX_STYLE));
        }
        List<Group> user11 = identityService.createGroupQuery().groupMember("user1").listPage(0, 100);
        for (Group group : user11){
            LOGGER.info("group = {}", ToStringBuilder.reflectionToString(group, ToStringStyle.SHORT_PREFIX_STYLE));
        }

    }

}
