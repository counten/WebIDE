package cn.codeyourlife.server.router;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 * 控制器 Bean 类
 */
public class ControllerBean {
    public ControllerBean(Class<?> clazz, boolean singleton) {
        this.clazz = clazz;
        this.singleton = singleton;
    }

    private Class<?> clazz;

    private boolean singleton;

    public Class<?> getClazz() {
        return clazz;
    }
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean getSingleton() {
        return this.singleton;
    }
    public void setSingleton(boolean singleton) {
        this.singleton = singleton;
    }
}
