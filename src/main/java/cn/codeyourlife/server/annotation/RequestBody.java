package cn.codeyourlife.server.annotation;

import java.lang.annotation.*;

/**
 * Request 请求体注解
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBody {
    
    String value() default "";

}
