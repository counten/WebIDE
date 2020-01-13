package cn.codeyourlife.server.annotation;

import java.lang.annotation.*;

/**
 * Http 请求头注解
 * 
 * @author Leo
 * @date 2018/3/23
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestHeader {

    String value() default "";
    
    boolean required() default true;
    
}
