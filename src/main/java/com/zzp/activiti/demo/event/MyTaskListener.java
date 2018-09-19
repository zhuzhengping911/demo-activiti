package com.zzp.activiti.demo.event;

import com.google.common.collect.Lists;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/20.
 */
public class MyTaskListener implements TaskListener {

    private static final Logger loger = LoggerFactory.getLogger(MyTaskListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {

        String eventName = delegateTask.getEventName();
        if (StringUtils.equals("create",eventName)){
            loger.info("config by listener");

            delegateTask.addCandidateUsers(Lists.newArrayList("user1","user2"));
            delegateTask.addCandidateGroup("group1");
            delegateTask.setVariable("key1","value1");
            delegateTask.setDueDate(DateTime.now().plusDays(3).toDate());
        }else if (StringUtils.equals("complete",eventName)){
            loger.info("config cantbe create");
        }


    }
}
