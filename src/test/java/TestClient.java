import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.net.InetSocketAddress;

public class TestClient {
	private final String host;
	private final int port;

	public TestClient() {
		this(0);
	}

	public TestClient(int port) {
		this("localhost", port);
	}

	public TestClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
					.channel(NioSocketChannel.class)
					.remoteAddress(new InetSocketAddress(this.host, this.port))
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							System.out.println("connected server...");
							ch.pipeline().addLast(new ByteArrayEncoder());
							ch.pipeline().addLast(new ByteArrayDecoder());
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});

			ChannelFuture cf = b.connect().sync();

			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new TestClient("127.0.0.1", 0420).start();
	}
}