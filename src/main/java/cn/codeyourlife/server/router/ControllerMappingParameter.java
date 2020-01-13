package cn.codeyourlife.server.router;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 * 请求映射参数类
 */
public class ControllerMappingParameter {
    private String name;

    private Class<?> dataType;

    private ControllerMappingParameterTypeEnum type;

    private boolean required = true;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getDataType() {
        return this.dataType;
    }
    public void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }

    public ControllerMappingParameterTypeEnum getType() {
        return this.type;
    }
    public void setType(ControllerMappingParameterTypeEnum type) {
        this.type = type;
    }

    public boolean getRequired() {
        return this.required;
    }
    public void setRequired(boolean required) {
        this.required = required;
    }
}
