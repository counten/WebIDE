package cn.codeyourlife.server;

import cn.codeyourlife.server.io.FileAccess;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http 请求处理器
 * <p>
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */
final class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final static Logger logger = LoggerFactory.getLogger(HttpRequestHandler.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage());
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (WebServer.getIgnoreUrls().contains(request.uri())) {
            return;
        }
        if (!FileAccess.accessFile(request, ctx)) {
            new RequestDispatcher().doDispatch(request, ctx);
        }
    }

}
