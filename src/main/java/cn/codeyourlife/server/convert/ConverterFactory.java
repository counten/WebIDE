package cn.codeyourlife.server.convert;

import java.util.Date;

/**
 * 转换器工厂类
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
public final class ConverterFactory {

    /**
     * 创建转换器
     * @param clazz
     * @return
     */
    public static Converter<?> create(Class<?> clazz) {
        if (clazz.equals(String.class)) {
            return new StringConverter();
        }
        if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
            return new IntegerConverter();
        }
        if (clazz.equals(long.class) || clazz.equals(Long.class)) {
            return new LongConverter();
        }
        if (clazz.equals(float.class) || clazz.equals(Float.class)) {
            return new FloatConverter();
        }
        if (clazz.equals(double.class) || clazz.equals(Double.class)) {
            return new DoubleConverter();
        }
        if (clazz.equals(Date.class)) {
            return new DateConverter();
        }
        return null;
    }

}
