package com.zzp.activiti.springboot.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhuzhengping
 * on 2018/9/24.
 */
@RestController
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "Hello World!";
    }
}
