package cn.codeyourlife.server.methods;

import java.lang.reflect.Method;

/**
 * 请求映射注册策略接口
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public interface RequestMappingRegisterStrategy {
    
    /**
     * 注册请求映射
     * @param clazz
     * @param baseUrl
     * @param method
     */
    void register(Class<?> clazz, String baseUrl, Method method);

}
