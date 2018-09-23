package com.zzp.activiti.demo.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class MytakeDelegate implements JavaDelegate{

    private static final Logger loger = LoggerFactory.getLogger(MytakeDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        loger.info("收获成功{}",this);
    }
}
