package cn.codeyourlife.server;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Author: wbq813@foxmail.com
 * Copyright: http://codeyourlife.cn
 * Platform: Win10 Jdk8
 * Date: 2020/1/8
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
    private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
    private static final AsciiString CONNECTION = new AsciiString("Connection");
    private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");
    // 资源所在路径
    private static final String LOCATION = "E:\\WebIDE\\src\\main\\resources\\static";


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {

        if (msg instanceof FullHttpRequest) {
            //客户端的请求对象
            FullHttpRequest request = (FullHttpRequest) msg;
            //新建一个返回消息的Json对象
            JSONObject responseJson = new JSONObject();

            //把客户端的请求数据格式化为Json对象
            JSONObject requestJson = null;
            try {
                requestJson = JSONObject.parseObject(parseJosnRequest(request));
            } catch (Exception e) {
                ResponseJson(ctx, request, new String("error json"));
                return;
            }

            //获取客户端的URL
            String uri = request.uri();
            // 先判断静态文件
            // 根据路径地址构建文件
            String path = LOCATION + uri;
            File files = new File(path);
            // 状态为1xx的话，继续请求
            if (HttpUtil.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }
            if (files.exists()) {
                RandomAccessFile file = new RandomAccessFile(files, "r");
                HttpResponse response = new DefaultHttpResponse(request.protocolVersion(), HttpResponseStatus.OK);
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
            } else {
                //根据不同的请求API做不同的处理(路由分发)，只处理POST方法
                if (request.method() == HttpMethod.POST) {
                    if (request.uri().equals("/bmi")) {
                        //计算体重质量指数
                        double height = 0.01 * requestJson.getDouble("height");
                        double weight = requestJson.getDouble("weight");
                        double bmi = weight / (height * height);
                        bmi = ((int) (bmi * 100)) / 100.0;
                        responseJson.put("bmi", bmi + "");

                    } else if (request.uri().equals("/bmr")) {
                        //计算基础代谢率
                        boolean isBoy = requestJson.getBoolean("isBoy");
                        double height = requestJson.getDouble("height");
                        double weight = requestJson.getDouble("weight");
                        int age = requestJson.getIntValue("age");
                        double bmr = 0;
                        if (isBoy) {
                            //66 + ( 13.7 x 体重kg ) + ( 5 x 身高cm ) - ( 6.8 x 年龄years )
                            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);

                        } else {
                            //655 + ( 9.6 x 体重kg ) + ( 1.8 x 身高cm ) - ( 4.7 x 年龄years )
                            bmr = 655 + (9.6 * weight) + 1.8 * height - 4.7 * age;
                        }

                        bmr = ((int) (bmr * 100)) / 100.0;
                        responseJson.put("bmr", bmr + "");
                    } else {
                        //错误处理
                        responseJson.put("error", "404 Not Find");
                    }

                } else {
                    if (request.uri().equals("/")) {
                        responseJson.put("data", "Hello World");
                    } else {
                        //错误处理
                        responseJson.put("error", "404 Not Find");
                    }
                }

                //向客户端发送结果
                ResponseJson(ctx, request, responseJson.toString());
            }


        }
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    /**
     * 响应HTTP的请求
     *
     * @param ctx
     * @param req
     * @param jsonStr
     */
    private void ResponseJson(ChannelHandlerContext ctx, FullHttpRequest req, String jsonStr) {

        boolean keepAlive = HttpUtil.isKeepAlive(req);
        byte[] jsonByteByte = jsonStr.getBytes();
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(jsonByteByte));
        response.headers().set(CONTENT_TYPE, "text/json");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (!keepAlive) {
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        } else {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 获取请求的内容
     *
     * @param request
     * @return
     */
    private String parseJosnRequest(FullHttpRequest request) {
        ByteBuf jsonBuf = request.content();
        String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
        return jsonStr;
    }
}
