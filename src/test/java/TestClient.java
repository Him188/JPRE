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
		new TestClient("127.0.0.1", 420).start();
	}
}


/*
8,

221,184,228,118,0,0,0,0,

28,0,0,0,6,3,0,0,0,10,49,57,57,52,55,48,49,48,50,49,0,0,0,0,2,0,0,0,0,0,3,0,0,0,1,48,3,0,0,0,9,54,49,50,53,51,52,51,53,57,3,0,0,0,95,72,101,108,108,111,33,32,91,64,49,48,52,48,52,48,48,50,57,48,93,44,32,89,111,117,32,106,117,115,116,32,115,101,110,116,32,97,32,109,101,115,115,97,103,101,32,119,104,105,99,104,32,104,97,115,32,116,104,101,32,108,101,110,103,116,104,32,111,102,32,52,44,32,109,101,115,115,97,103,101,32,99,111,110,116,101,120,116,58,32,32,84,101,115,116
*
*
*
*
*
*
*
*
*
* */