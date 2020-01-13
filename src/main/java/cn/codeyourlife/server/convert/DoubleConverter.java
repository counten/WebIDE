package cn.codeyourlife.server.convert;

/**
 * 双精度转换器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class DoubleConverter implements Converter<Double> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Double convert(Object source) {
        return Double.parseDouble(source.toString());
    }

}
