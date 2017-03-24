package com.him188.jpre.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * JPRE 网络层启动器.
 * 本类用于启动网络服务器. 包接受器请参考 {@link NetworkPacketHandler}
 * (插件请不要使用本类, 用了也会因端口占用而抛出异常)
 *
 * @author Him188
 */
public final class Network {
	/**
	 * 启动网路服务器. 会阻塞线程直到关闭网络服务器.
	 *
	 * @param port 端口号
	 *
	 * @throws InterruptedException 端口被占用时产生
	 */
	public static void start(int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();

					//pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4));

					// Encoder
					pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));

					pipeline.addLast(new OutBoundHandler());

					//pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
					//pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
					pipeline.addLast("bytesDecoder", new ByteArrayDecoder());

					//pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
					pipeline.addLast("bytesEncoder", new ByteArrayEncoder());

					pipeline.addLast("handler", new NetworkPacketHandler());
				}
			});

			b.bind(port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
