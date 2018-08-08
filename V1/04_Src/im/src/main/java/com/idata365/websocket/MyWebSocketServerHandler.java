package com.idata365.websocket;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.service.ImService;
import com.idata365.app.service.SpringContextUtil;
import com.idata365.app.util.GsonUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
public class MyWebSocketServerHandler extends
		SimpleChannelInboundHandler<Object> {
	private static final Logger logger = Logger
			.getLogger(WebSocketServerHandshaker.class.getName());
	private WebSocketServerHandshaker handshaker;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 添加
		Global.group.add(ctx.channel());
		String id=ctx.channel().id().asLongText();
		logger.info(id+"客户端与服务端连接开启");
	}
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		String id=ctx.channel().id().asLongText();
		logger.info(id+"客户端与服务端连接关闭");
		// 移除
		Global.inActiveChannel(id);
		Global.group.remove(ctx.channel());
		//插入日志
		ImService imService=(ImService)SpringContextUtil.getBean("imService");
		imService.updateOffLog(id);
		
	}
	@Override
	protected void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, ((FullHttpRequest) msg));
		} else if (msg instanceof WebSocketFrame) {
			handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	private void handlerWebSocketFrame(ChannelHandlerContext ctx,
			WebSocketFrame frame) {
		// 判断是否关闭链路的指令
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame
					.retain());
			return;
		}
		// 判断是否ping消息
		if (frame instanceof PingWebSocketFrame) {
			ctx.channel().write(
					new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// 本例程仅支持文本消息，不支持二进制消息
		if (!(frame instanceof TextWebSocketFrame)) {
			System.out.println("本例程仅支持文本消息，不支持二进制消息");
			throw new UnsupportedOperationException(String.format(
					"%s frame types not supported", frame.getClass().getName()));
//			ReferenceCountUtil.release(frame);
//			return;
		}
		// 返回应答消息
		String request = ((TextWebSocketFrame) frame).text();
//		System.out.println("服务端收到：" + request);
		if (logger.isLoggable(Level.FINE)) {
			logger
					.fine(String.format("%s received %s", ctx.channel(),
							request));
		}
		 
		try {
			if(!request.contains("msgType")) {
				return;
			}
		
			Map<String,Object> map=GsonUtils.fromJson(request);
			if(map.get("msgType").equals("10")) {//注册通知模块
				String userId=String.valueOf(map.get("userId"));
				Map<String,String> datas=(Map<String,String>)map.get("data");
				String notifyMoudle=String.valueOf(datas.get("notifyMoudle"));
				String keyIds=String.valueOf(datas.get("keyId"));
				Global.mapChannelUser(ctx.channel().id().asLongText(),userId);
				Global.addMoudle(notifyMoudle, ctx.channel(), userId, keyIds);
				//插入日志
				ImService imService=(ImService)SpringContextUtil.getBean("imService");
				imService.insertInLog(userId, ctx.channel().id().asLongText());
				
			}else if(map.get("msgType").equals("11")) {//移除模块
				String userId=String.valueOf(map.get("userId"));
				Map<String,String> datas=(Map<String,String>)map.get("data");
				String notifyMoudle=String.valueOf(datas.get("notifyMoudle"));
				Global.removeMoudle(notifyMoudle,userId);
			}else {
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
// 群发
//		Global.group.writeAndFlush(new TextWebSocketFrame(new Date().toString()
//					+ ctx.channel().id() + ":" + request+"--这个是服务器返回的消息"));
		
		
//		ReferenceCountUtil.release(tws.content());
	}
	private void handleHttpRequest(ChannelHandlerContext ctx,
			FullHttpRequest req) {
		if (!req.getDecoderResult().isSuccess()
				|| (!"websocket".equals(req.headers().get("Upgrade")))) {
			sendHttpResponse(ctx, req, new DefaultFullHttpResponse(
					HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}
		SystemProperties properties=SpringContextUtil.getBean("systemProperties",SystemProperties.class);
		WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
				"ws://"+properties.getWebSocketUrl()+":"+properties.getSocketPort()+"/websocket", null, false);
		handshaker = wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory
					.sendUnsupportedWebSocketVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}
	private static void sendHttpResponse(ChannelHandlerContext ctx,
			FullHttpRequest req, DefaultFullHttpResponse res) {
		// 返回应答给客户端
		if (res.getStatus().code() != 200) {
			ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(),
					CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
		}
		// 如果是非Keep-Alive，关闭连接
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!isKeepAlive(req) || res.getStatus().code() != 200) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
	private static boolean isKeepAlive(FullHttpRequest req) {
		return false;
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
} 


