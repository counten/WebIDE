package cn.codeyourlife.server.exception;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 * 处理请求异常类
 */
public final class HandleRequestException extends RuntimeException {

    private static final long serialVersionUID = -630225144002649999L;

    public HandleRequestException() {
    }

    public HandleRequestException(String message) {
        super(message);
    }

    public HandleRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleRequestException(Throwable cause) {
        super(cause);
    }

}
