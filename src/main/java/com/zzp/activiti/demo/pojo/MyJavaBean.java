package com.zzp.activiti.demo.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Created by zhuzhengping
 * on 2018/9/20.
 */
public class MyJavaBean implements Serializable {

    private static final Logger loger = LoggerFactory.getLogger(MyJavaBean.class);

    private String name;

    public String getName() {
        loger.info("name = {}",name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MyJavaBean() {
    }

    public MyJavaBean(String name) {
        this.name = name;
    }

    public void sayHello(){
        loger.info("run say hello");
    }
}
