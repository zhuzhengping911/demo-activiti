package com.zzp.activiti.demo.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class MyJavaDelegate implements JavaDelegate{

    private static final Logger loger = LoggerFactory.getLogger(MyJavaDelegate.class);

    private Expression name;
    private Expression desc;
    @Override
    public void execute(DelegateExecution delegateExecution) {
        if (name != null){
            Object value = name.getValue(delegateExecution);
            loger.info("name = {}",value);
        }
        if (desc != null){
            Object value = desc.getValue(delegateExecution);
            loger.info("desc = {}",value);
        }
        loger.info("run my delegate {}",this);
    }
}
