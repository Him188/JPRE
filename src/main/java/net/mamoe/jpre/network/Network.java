package net.mamoe.jpre.network;

import net.mamoe.jpre.JPREMain;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
	public static void start(JPREMain jpre, int port) throws InterruptedException {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			//b.option(ChannelOption.SO_BACKLOG, 100);
			//b.handler(new LoggingHandler(LogLevel.INFO));
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("bytesDecoder", new ByteArrayDecoder());
					pipeline.addLast("bytesEncoder", new ByteArrayEncoder());
					pipeline.addLast("handler", new NetworkPacketHandler(jpre));
				}
			});

			b.bind(port).sync().channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
