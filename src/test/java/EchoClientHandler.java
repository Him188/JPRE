import net.mamoe.jpre.network.packet.Protocol;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

public class EchoClientHandler extends SimpleChannelInboundHandler<byte[]> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client channelActive..");
		new Thread(() -> {
			while (true) {
				ctx.writeAndFlush(Unpooled.copiedBuffer(new byte[]{Protocol.CLIENT_EVENT, 0, 0, 0, 0, 127, 127}));
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] msg) throws Exception {
		System.out.println("Client received:" + Arrays.toString(msg));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}