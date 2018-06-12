package com.idata365.websocket;
import com.idata365.app.config.SystemProperties;
import com.idata365.app.service.SpringContextUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
public class NettyServer {
	private  static NettyServer ns=null;
	public static void main(String[] args) {
		new NettyServer().run();
	}
	public static NettyServer getInstance(){
		 if(ns==null)
		 {
			 ns=new NettyServer();
			 ns.run();
		 }
		 return ns;
	}
	private NettyServer()  {
		
	 }
	public void run(){
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup).handler(new LoggingHandler(LogLevel.DEBUG));
			ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
//			ServerBootstrap b =new ServerBootstrap();
//			b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 2048).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChildChannelHandler());

	 
			b.channel(NioServerSocketChannel.class);
			
			System.out.println("服务端开启等待客户端连接 ... ...");
//			Channel ch = b.bind(7397).sync().channel();
			b.childHandler(new ChannelInitializer<Channel>() {
	            @Override
	            protected void initChannel(Channel socketChannel) throws Exception {
	                ChannelPipeline p = socketChannel.pipeline();
//	                p.addLast(new MyEncoder());
////	              p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
//	                p.addLast(new MyDecoder());
//	                b.childHandler();
	                p.addLast(new ChildChannelHandler());
//	                p.addLast(new DataHandler());
	            }
	        });
			
			SystemProperties properties=SpringContextUtil.getBean("systemProperties",SystemProperties.class);
			 ChannelFuture f= b.bind(Integer.valueOf(properties.getSocketPort())).sync();
			 System.out.println("端口:"+properties.getSocketPort());
			 System.out.println("url:"+properties.getWebSocketUrl());
			 if(f.isSuccess()){
				 System.out.println("server start---------------");
			}
//			ch.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
//			bossGroup.shutdownGracefully();
//			workGroup.shutdownGracefully();
		}
		System.out.println("run over ... ...");
	}
	


}	
