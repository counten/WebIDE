package cn.codeyourlife.server.router;

import cn.codeyourlife.server.methods.RequestMappingRegisterStrategy;

import java.lang.reflect.Method;

/**
 * 请求映射策略上下文类
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public final class RequestMappingRegisterContext {
    
    private RequestMappingRegisterStrategy strategy;

    public RequestMappingRegisterContext(RequestMappingRegisterStrategy strategy) {
        this.strategy = strategy;  
    }
    
    /**
     * 注册 Mapping
     * @param clazz
     * @param baseUrl
     * @param method
     */
    public void registerMapping(Class<?> clazz, String baseUrl, Method method) {
        if(this.strategy == null) {
            return;
        }
        this.strategy.register(clazz, baseUrl, method);
    }
    
}
