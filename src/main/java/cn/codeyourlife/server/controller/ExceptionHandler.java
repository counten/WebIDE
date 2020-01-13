package cn.codeyourlife.server.controller;

/**
 * 异常处理器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public interface ExceptionHandler {

    /**
     * 处理异常
     * @param e
     */
    void doHandle(Exception e);
    
}
