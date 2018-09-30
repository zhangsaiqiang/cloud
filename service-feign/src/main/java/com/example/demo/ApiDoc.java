package com.example.demo;

import java.lang.annotation.*;

/**
 * @author zhagsaiqiang
 * @date 2018/9/30 15:24
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ApiDoc {
    /**
     * 如果为true，启作用，否则忽略
     * @return
     */
    boolean docEnable() default true;
}
