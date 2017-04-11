import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Arrays;

public class EchoClientHandler extends SimpleChannelInboundHandler<byte[]> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client channelActive..");
		ctx.writeAndFlush(Unpooled.copiedBuffer(new byte[]{3, 0, 0, 0, 0}));
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