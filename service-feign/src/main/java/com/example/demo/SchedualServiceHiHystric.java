package com.example.demo;

import org.springframework.stereotype.Component;

/**
 * @author zhagsaiqiang
 * @date 2018/9/30 15:24
 */
@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}