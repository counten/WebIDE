package cn.codeyourlife.server.annotation;

import java.lang.annotation.*;

/**
 * 上传文件注解
 * 
 * @author Leo
 * @date 2018/5/11
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UploadFile {

}
