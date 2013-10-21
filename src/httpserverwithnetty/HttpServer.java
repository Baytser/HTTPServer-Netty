/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package httpserverwithnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * HTTP server which serves Web Socket requests at:
 * http://localhost:80/
 */
public class HttpServer {
    
     private final int PORT = 80;
     
    public void run() {
       
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("in HttpServRun");
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ServerInit());
             
            Channel ch = b.bind(PORT).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException e ) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        try {
            System.out.println("in Main");
            new HttpServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
