package cn.codeyourlife;

import cn.codeyourlife.controller.ExceptionController;
import cn.codeyourlife.interceptor.CorsInterceptor;
import cn.codeyourlife.server.WebServer;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public class JDEApplication {
    public static void main(String[] args) {
        // 忽略指定url
        WebServer.getIgnoreUrls().add("/favicon.ico");

        // 全局异常处理
        WebServer.setExceptionHandler(new ExceptionController());

        // 设置监听端口号
        WebServer server = new WebServer(2006);

        // 设置Http最大内容长度（默认 为10M）
        server.setMaxContentLength(1024 * 1024 * 50);

        // 设置Controller所在包
        server.setControllerBasePackage("cn.codeyourlife.controller");

        // 添加拦截器，按照添加的顺序执行。
        // 跨域拦截器
        server.addInterceptor(new CorsInterceptor(), "/不用拦截的url");

        try {
            server.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
