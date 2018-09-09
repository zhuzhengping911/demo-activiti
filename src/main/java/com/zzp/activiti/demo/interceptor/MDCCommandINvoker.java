package com.zzp.activiti.demo.interceptor;

import org.activiti.engine.impl.agenda.AbstractOperation;
import org.activiti.engine.impl.interceptor.DebugCommandInvoker;
import org.activiti.engine.logging.LogMDC;

/**
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class MDCCommandINvoker extends DebugCommandInvoker{

    @Override
    public void executeOperation(Runnable runnable) {
        boolean mdcEnabled = LogMDC.isMDCEnabled();
        LogMDC.setMDCEnabled(true);
        if (runnable instanceof AbstractOperation) {
            AbstractOperation operation = (AbstractOperation)runnable;
            if (operation.getExecution() != null) {
                LogMDC.putMDCExecution(operation.getExecution());
            }
        }

        super.executeOperation(runnable);
        LogMDC.clear();
        if (!mdcEnabled){
            LogMDC.setMDCEnabled(false);
        }

    }
}
