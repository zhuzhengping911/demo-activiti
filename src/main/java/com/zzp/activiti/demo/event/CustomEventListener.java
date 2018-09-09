package com.zzp.activiti.demo.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class CustomEventListener implements ActivitiEventListener{
    private static final Logger loger = LoggerFactory.getLogger(CustomEventListener.class);
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
        if(ActivitiEventType.CUSTOM.equals(eventType)){
            loger.info("监听自定义事件 {},\t id{}",eventType,activitiEvent.getProcessInstanceId());
        }

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
