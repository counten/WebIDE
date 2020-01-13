package cn.codeyourlife.server.router;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public class ControllerMapping {
    private String url;

    private String className;

    private String classMethod;

    private List<ControllerMappingParameter> parameters = new ArrayList<>();

    /**
     * 是否输出结果为JSON
     */
    private boolean JsonResponse;

    /**
     * 单例类
     */
    private boolean singleton;

    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return this.className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassMethod() {
        return this.classMethod;
    }
    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public List<ControllerMappingParameter> getParameters() {
        return this.parameters;
    }

    public boolean getJsonResponse() {
        return this.JsonResponse;
    }
    public void setJsonResponse(boolean jsonResponse) {
        this.JsonResponse = jsonResponse;
    }

    public boolean getSingleton() {
        return this.singleton;
    }
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}
