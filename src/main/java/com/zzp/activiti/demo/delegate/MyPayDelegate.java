package com.zzp.activiti.demo.delegate;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class MyPayDelegate implements JavaDelegate{

    private static final Logger loger = LoggerFactory.getLogger(MyPayDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) {
        loger.info("variables = {}",delegateExecution.getVariables());
        loger.info("支付开始{}",this);
        delegateExecution.getParent().setVariableLocal("key2","value2");
        delegateExecution.setVariable("key1","value1");
        delegateExecution.setVariable("key3","value3");
        Object errorFlag = delegateExecution.getVariable("errorFlag");
        if (Objects.equals(errorFlag,true)){
            loger.info("支付失败{}",this);
            throw new BpmnError("bpmnError");
        }
        loger.info("支付成功{}",this);
    }
}
