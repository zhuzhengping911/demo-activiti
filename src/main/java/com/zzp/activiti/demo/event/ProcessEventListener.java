package com.zzp.activiti.demo.event;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程Event
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class ProcessEventListener implements ActivitiEventListener{
    private static final Logger loger = LoggerFactory.getLogger(ProcessEventListener.class);
    @Override
    public void onEvent(ActivitiEvent activitiEvent) {
        ActivitiEventType eventType = activitiEvent.getType();
        if(ActivitiEventType.PROCESS_STARTED.equals(eventType)){
            loger.info("流程启动 {},\t id{}",eventType,activitiEvent.getProcessInstanceId());
        }else if (ActivitiEventType.PROCESS_COMPLETED.equals(eventType)){
            loger.info("流程结束 {},\t id{}",eventType,activitiEvent.getProcessInstanceId());
        }

    }

    @Override
    public boolean isFailOnException() {
        return false;
    }
}
