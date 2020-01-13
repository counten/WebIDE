package cn.codeyourlife.server.convert;

/**
 * 单精度转换器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class FloatConverter implements Converter<Float> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Float convert(Object source) {
        return Float.parseFloat(source.toString());
    }

}
