package cn.codeyourlife.interceptor;

import cn.codeyourlife.server.io.HttpResponse;
import cn.codeyourlife.server.interceptor.Interceptor;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 跨域拦截器
 * @author Leo
 */
public final class CorsInterceptor implements Interceptor {

    @Override
    public boolean preHandle(FullHttpRequest request, HttpResponse response) throws Exception {
        response.getHeaders().put("Access-Control-Allow-Origin", "*");
        response.getHeaders().put("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.getHeaders().put("Access-Control-Max-Age", "3600");
        response.getHeaders().put("Access-Control-Allow-Headers", "Content-Type, X-Token");
        return true;
    }

    @Override
    public void postHandle(FullHttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    public void afterCompletion(FullHttpRequest request, HttpResponse response) {
    }

}
