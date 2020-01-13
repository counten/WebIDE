package cn.codeyourlife.server.annotation;

import java.lang.annotation.*;

/**
 * REST 控制器注解
 * 
 * @author Leo
 * @date 2018/3/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RestController {
    
    boolean singleton() default true;
    
}
