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
public class JobEventListener implements ActivitiEventListener{
    private static final Logger loger = LoggerFactory.getLogger(JobEventListener.class);
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
        String name = eventType.name();
        if(name.startsWith("TIMER") || name.startsWith("JOB")){
            loger.info("监听Job事件 {},\t id{}",eventType,activitiEvent.getProcessInstanceId());
        }

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
