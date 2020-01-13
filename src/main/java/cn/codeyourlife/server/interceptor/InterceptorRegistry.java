package cn.codeyourlife.server.interceptor;

import java.util.*;

/**
 * 拦截器注册类
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public final class InterceptorRegistry {
    
    private final static Set<Interceptor> interceptors = new LinkedHashSet<>(8);
    
    private final static Map<String, List<String>> excludeMappings = new HashMap<String, List<String>>(8);
    
    public static void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public static void addInterceptor(Interceptor interceptor, String... excludeMappings) {
        interceptors.add(interceptor);
        List<String> excludeMappingList = new ArrayList<String>(8);
        for(String excludeMapping : excludeMappings) {
            excludeMappingList.add(excludeMapping);
        }
        InterceptorRegistry.excludeMappings.put(interceptor.getClass().getName(), excludeMappingList);
    }
    
    public static Set<Interceptor> getInterceptors() {
        return interceptors;
    }
    
    public static List<String> getExcludeMappings(Interceptor interceptor) {
        return excludeMappings.get(interceptor.getClass().getName());
    }

}
