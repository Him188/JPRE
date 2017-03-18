package com.him188.jpre.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Him188
 */
public class Network extends io.netty.channel.ChannelHandlerAdapter {
	private static final int PORT = 8888;

	public static void main(String args[]) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChannelHandlerAdapter() {
			});

			// 服务器绑定端口监听
			ChannelFuture f = b.bind(PORT).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();

			// 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
