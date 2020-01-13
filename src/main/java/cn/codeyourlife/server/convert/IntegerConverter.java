package cn.codeyourlife.server.convert;

/**
 * 整数转换器
 * 
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class IntegerConverter implements Converter<Integer> {

    /**
     * 类型转换
     * 
     * @param source
     * @return
     */
    @Override
    public Integer convert(Object source) {
        return Integer.parseInt(source.toString());
    }

}
