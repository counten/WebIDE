package cn.codeyourlife.server.annotation;

import java.lang.annotation.*;

/**
 * UrlEncodedForm 注解
 * 
 * @author Leo
 * @date 2018/3/20
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UrlEncodedForm {

}
