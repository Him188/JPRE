package com.him188.jpre.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Him188
 */
public class NetworkPacketHandler extends SimpleChannelInboundHandler<byte[]> {
	private static List<ConnectedClient> clients = new ArrayList<>();

	public static List<ConnectedClient> getClients() {
		return clients;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] data) throws Exception {
		System.out.println("[Network] Data packet received: " + Arrays.toString(data));

		for (ConnectedClient client : clients) {
			if (client.is(ctx.channel().remoteAddress())) {
				client.dataReceive(ctx, data);
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (cause instanceof IOException) { //远程主机强迫关闭了一个现有的连接
			return;
		}

		super.exceptionCaught(ctx, cause);

		// TODO: 2017/3/22  配置是否显示错误信息.
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		if (!clients.isEmpty()) {
			return;
		}

		ConnectedClient client = new ConnectedClient(ctx.channel().remoteAddress(), ctx);
		clients.add(client);

		System.out.println("[Network] RemoteClient: " + ctx.channel().remoteAddress() + " connected.");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ConnectedClient found = null;
		for (ConnectedClient client : clients) {
			if (found != null) {
				continue;
			}
			if (client.is(ctx.channel().remoteAddress())) {
				found = client;
			}
		}
		if (found != null) {
			clients.remove(found);
		}
		System.out.println("[Network] RemoteClient: " + ctx.channel().remoteAddress() + " disconnected.");
		super.channelInactive(ctx);
	}
}