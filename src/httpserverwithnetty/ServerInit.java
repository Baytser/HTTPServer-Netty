/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserverwithnetty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.traffic.GlobalTrafficShapingHandler;
import io.netty.handler.traffic.TrafficCounter;

/**
 *
 * @author ike
 */
public class ServerInit extends ChannelInitializer<SocketChannel> {
    private TrafficCounter trafficCounter;
    
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        System.err.println("in ServerInit");
        ChannelPipeline pipeline = ch.pipeline();
        GlobalTrafficShapingHandler globalTrafficShapingHandler = new GlobalTrafficShapingHandler(ch.eventLoop());
        trafficCounter = globalTrafficShapingHandler.trafficCounter();

        pipeline.addLast("traffic", globalTrafficShapingHandler);
        pipeline.addLast("codec-http", new HttpServerCodec());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("handler", new ServerHandler(trafficCounter));
    }
    
}
