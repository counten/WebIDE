package cn.codeyourlife.server.convert;

/**
 * 字符串转换器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class StringConverter implements Converter<String> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public String convert(Object source) {
        return source.toString();
    }

}
