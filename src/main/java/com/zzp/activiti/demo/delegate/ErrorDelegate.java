package com.zzp.activiti.demo.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ErrorDelegate implements JavaDelegate{
    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("---------------");
    }
}
