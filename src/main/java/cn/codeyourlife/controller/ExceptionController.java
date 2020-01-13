package cn.codeyourlife.controller;

import cn.codeyourlife.server.HttpContextHolder;
import cn.codeyourlife.server.io.HttpResponse;
import cn.codeyourlife.server.io.HttpStatus;
import cn.codeyourlife.server.controller.ExceptionHandler;
import cn.codeyourlife.server.exception.ResourceNotFoundException;

public class ExceptionController implements ExceptionHandler {

    /**
     * 处理异常
     * @param e
     */
    @Override
    public void doHandle(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if(e instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        }
        String errorMessage = e.getCause() == null ? "" : e.getCause().getMessage();
        if(errorMessage == null) {
            errorMessage = e.getMessage();
        }
        HttpResponse response = HttpContextHolder.getResponse();
        response.write(status, errorMessage);
        response.closeChannel();
    }

}
