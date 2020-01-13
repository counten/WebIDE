package cn.codeyourlife.server.convert;

/**
 * 数据转换器接口
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 *
 * @param <S>
 * @param <T>
 */
public interface Converter<T> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    T convert(Object source);

}
