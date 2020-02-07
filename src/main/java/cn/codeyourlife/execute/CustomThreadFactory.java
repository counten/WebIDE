package cn.codeyourlife.execute;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/2/7
 */
public class CustomThreadFactory implements ThreadFactory {
    private final AtomicInteger nextId = new AtomicInteger();
    private final String threadPrefix;

    public CustomThreadFactory(String prefix) {
        // 设置线程池名称前缀
        this.threadPrefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, threadPrefix + nextId.incrementAndGet());
    }
}