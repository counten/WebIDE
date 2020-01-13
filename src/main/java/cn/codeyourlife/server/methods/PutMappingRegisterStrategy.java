package cn.codeyourlife.server.methods;

import cn.codeyourlife.server.annotation.PutMapping;
import cn.codeyourlife.server.router.ControllerMapping;
import cn.codeyourlife.server.router.ControllerMappingRegistry;

import java.lang.reflect.Method;

/**
 * PUT 请求映射注册策略类
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public final class PutMappingRegisterStrategy extends AbstractRequestMappingRegisterStrategy implements RequestMappingRegisterStrategy {
    
    /**
     * 得到控制器方法的Url
     * @param method
     * @return
     */
    @Override
    public String getMethodUrl(Method method) {
        if(method.getAnnotation(PutMapping.class) != null) {
            return method.getAnnotation(PutMapping.class).value();
        }
        return "";
    }

    /**
     * 得到Http请求的方法类型
     * @return
     */
    @Override
    public String getHttpMethod() {
        return "PUT";
    }
    
    /**
     * 注册Mapping
     * @param url
     * @param mapping
     */
    @Override
    public void registerMapping(String url, ControllerMapping mapping) {
        ControllerMappingRegistry.getPutMappings().put(url, mapping);
    }

}