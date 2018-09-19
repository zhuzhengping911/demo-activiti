package com.zzp.activiti.demo.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/20.
 */
public class MyActivitiBehavior implements ActivityBehavior {

    private static final Logger loger = LoggerFactory.getLogger(MyActivitiBehavior.class);
    @Override
    public void execute(DelegateExecution delegateExecution) {
        loger.info("run my MyActivitiBehavior {}",this);
    }
}
