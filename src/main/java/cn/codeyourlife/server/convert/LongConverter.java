package cn.codeyourlife.server.convert;

/**
 * 长整数转换器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class LongConverter implements Converter<Long> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Long convert(Object source) {
        return Long.parseLong(source.toString());
    }

}