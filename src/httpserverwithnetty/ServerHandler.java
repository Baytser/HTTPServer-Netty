/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserverwithnetty;

import DBWorking.ManagerDB;
import Pages.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;
import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpResponseStatus.TEMPORARY_REDIRECT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.handler.traffic.TrafficCounter;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Request handler
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final String STATUS = "/status";
    private final String HELLO = "/hello";
    private final String REDIRECT = "/redirect";
    private final int TIMESLEEP = 10;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private TrafficCounter trafficCounter;
    private long sentBytes;
    private long receivedBytes;
    private long speed;

    public ServerHandler(TrafficCounter trafficCounter) {
        this.trafficCounter = trafficCounter;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        trafficCounter.start();

        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        String ip = address.getHostString();

        if (msg instanceof HttpRequest) {
            System.out.println("in ChenelRead");
            FullHttpRequest request = (FullHttpRequest) msg;
            if (!request.getUri().equals("/falicon.ico")) {
                ServerStatus.addConnection();
            }
            if (is100ContinueExpected(request)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            String uri = request.getUri();
            handleHttpRequest(ctx, (FullHttpRequest) msg, ip);
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("in ChanelReadRomplete");
        ServerStatus.subConnection();
        ctx.flush();
        ctx.close();
    }

    /**
     *Method Opening page what you need
     * @param ctx
     * @param req
     * @param ip
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req, String ip) {
        String uri = req.getUri();
        FullHttpResponse resp = null;
        if (uri.equalsIgnoreCase(STATUS)) {
            ManagerDB.addOrUpdateRequestAmount(ip, dateFormat.format(new Date()));
            ServerStatus.addRequest();
            PageStatus page = new PageStatus();
            String send = page.getPage();
            ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(send, CharsetUtil.US_ASCII));
            resp = new DefaultFullHttpResponse(HTTP_1_1, OK, buf);
            resp.headers().set(CONTENT_TYPE, "text/HTML");
            resp.headers().set(CONTENT_LENGTH, resp.content().readableBytes());
            sendHttpResponse(ctx, req, resp);
            trafficLog();

            ManagerDB.insertConnection(ip, uri, dateFormat.format(new Date()), sentBytes, receivedBytes, speed);

        } else if (uri.equalsIgnoreCase(HELLO)) {
            try {
                Thread.sleep(TIMESLEEP * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Page page = new PageHello();
            ManagerDB.addOrUpdateRequestAmount(ip, dateFormat.format(new Date()));
            ServerStatus.addRequest();
            String send = page.getPage();
            ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(send, CharsetUtil.US_ASCII));
            resp = new DefaultFullHttpResponse(HTTP_1_1, OK, buf);
            resp.headers().set(CONTENT_TYPE, "text/HTML");
            resp.headers().set(CONTENT_LENGTH, resp.content().readableBytes());
            sendHttpResponse(ctx, req, resp);
            trafficLog();

            ManagerDB.insertConnection(ip, uri, dateFormat.format(new Date()), sentBytes, receivedBytes, speed);

        } else if (uri.toLowerCase().contains(REDIRECT) && uri.toLowerCase().contains("url")) {
            ManagerDB.addOrUpdateRequestAmount(ip, dateFormat.format(new Date()));
            ServerStatus.addRequest();
            String redirect;
            if (uri.toLowerCase().contains("http://")) {
                redirect = uri.substring(14);
            } else {
                redirect = "http://" + uri.substring(14);
            }
            ByteBuf buf23 = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("", CharsetUtil.US_ASCII));
            resp = new DefaultFullHttpResponse(HTTP_1_1, TEMPORARY_REDIRECT, buf23);
            resp.headers().set(LOCATION, redirect);
            sendHttpResponse(ctx, req, resp);
            trafficLog();
            ManagerDB.insertConnection(ip, uri, dateFormat.format(new Date()), sentBytes, receivedBytes, speed);
        } else {
            ByteBuf buf2 = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("404 page not found", CharsetUtil.US_ASCII));
            resp = new DefaultFullHttpResponse(HTTP_1_1, OK, buf2);
            resp.headers().set(CONTENT_TYPE, "text/plain");
            resp.headers().set(CONTENT_LENGTH, resp.content().readableBytes());
            sendHttpResponse(ctx, req, resp);
        }
    }
/**
 * Send the response and close the connection if necessary.
 * @param ctx
 * @param req
 * @param resp 
 */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse resp) {
        if (!isKeepAlive(req)) {
            ctx.write(resp).addListener(ChannelFutureListener.CLOSE);
        } else {
            resp.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            ctx.write(resp);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("in ExceptionCaugh");
        ServerStatus.subConnection();
        cause.printStackTrace();
        ctx.close();
    }
/**
 * Write traffic log
 */
    synchronized public void trafficLog() {
        trafficCounter.stop();
        receivedBytes = trafficCounter.cumulativeReadBytes();
        sentBytes = trafficCounter.cumulativeWrittenBytes();
        speed = trafficCounter.lastWriteThroughput();
        trafficCounter.resetCumulativeTime();

    }
}
