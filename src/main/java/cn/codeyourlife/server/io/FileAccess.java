package cn.codeyourlife.server.io;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/13
 */


public class FileAccess {

    private static void send100Continue(ChannelHandlerContext ctx){
        FullHttpResponse response=new DefaultFullHttpResponse(HTTP_1_1,HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }
    public static boolean accessFile(FullHttpRequest request, ChannelHandlerContext ctx) throws IOException {
        boolean STATIC_RES = false;
        //获取客户端的URL
        String uri = request.uri();
        String LOCATION = "E:\\WebIDE\\src\\main\\resources\\static";
        // 根据路径地址构建文件
        String path = LOCATION + uri;
        File files = new File(path);
        // 状态为1xx的话，继续请求
        if (HttpUtil.is100ContinueExpected(request)) {
            send100Continue(ctx);
        }
        if (files.exists()) {
            STATIC_RES = true;
            RandomAccessFile file = new RandomAccessFile(files, "r");
            io.netty.handler.codec.http.HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
            // 设置文件格式内容
            if (path.endsWith(".html")) {
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            } else if (path.endsWith(".js")) {
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/x-javascript");
            } else if (path.endsWith(".css")) {
                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/css; charset=UTF-8");
            }

            boolean keepAlive = HttpUtil.isKeepAlive(request);

            if (keepAlive) {
                response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
                response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            }
            ctx.write(response);

            if (ctx.pipeline().get(SslHandler.class) == null) {
                ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                ctx.write(new ChunkedNioFile(file.getChannel()));
            }
            // 写入文件尾部
            ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if (!keepAlive) {
                future.addListener(ChannelFutureListener.CLOSE);
            }
            file.close();
        }
        return STATIC_RES;
    }
}
