package com.zzp.activiti.demo.interceptor;

import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拦截器
 * 执行时间
 * Created by zhuzhengping
 * on 2018/9/9.
 */
public class DurationCommandInterceptor extends AbstractCommandInterceptor{
    private static final Logger LOGGER = LoggerFactory.getLogger(DurationCommandInterceptor.class);
    @Override
    public <T> T execute(CommandConfig commandConfig, Command<T> command) {
        long start = System.currentTimeMillis();
        try {
            return this.getNext().execute(commandConfig,command);
        }finally {
            long duration = System.currentTimeMillis() - start;
            LOGGER.info("{}执行时长{}毫秒",command.getClass().getSimpleName(),duration);
        }

    }
}
