package com.him188.jpre.network;

import com.him188.jpre.Frame;
import com.him188.jpre.JPREMain;
import com.him188.jpre.binary.Pack;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 网络数据包接收器. 该类属于网络层, 插件一般不需要使用
 *
 * @author Him188
 */
public class NetworkPacketHandler extends SimpleChannelInboundHandler<byte[]> {
	private JPREMain jpre;

	public JPREMain getJPREMain() {
		return jpre;
	}

	NetworkPacketHandler(JPREMain jpre) {
		this.jpre = jpre;
	}


	private static List<MPQClient> clients = new ArrayList<>();

	public static List<MPQClient> getClients() {
		return clients;
	}


	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, byte[] data) throws Exception {
		synchronized (this) {
			System.out.println("[Network] Data packet received: " + Arrays.toString(data));
			handlePacket(ctx, data);
		}
	}

	private boolean _reading;
	private byte[] _readingData;
	private int _wantLength;
	private int _currentLength;

	private void handlePacket(ChannelHandlerContext ctx, byte[] data) {
		while (true) {
			if (data.length == 0) {
				return;
			}

			if (_reading) {
				_currentLength += data.length;
				if (_currentLength < _wantLength) return;

				_reading = false; //当前包已处理完毕, 第二个包又会有 int length, 需重新读取
				_currentLength = 0;
				_readingData = concatArray(_readingData, data);

				byte[][] out = removeArray(_readingData, _wantLength);

				processPacket(ctx, new Pack(out[0]));
				data = out[1];
				continue;
				//长度不够, 继续读取
			}

			Pack stream = new Pack(data);
			_wantLength = stream.getInt();
			_readingData = stream.getLast();
			_reading = true;
			_currentLength = 0;
		}
	}

	private void processPacket(ChannelHandlerContext ctx, Pack pack) {
		for (MPQClient client : clients) {
			if (client.is(ctx.channel().remoteAddress())) {
				client.getFrame().getScheduler().scheduleTask(null, () -> client.dataReceive(pack));
			}
		}
	}

	private static byte[] concatArray(byte[] original, byte[] target) {
		byte[] newArray = new byte[original.length + target.length];
		System.arraycopy(original, 0, newArray, 0, original.length);
		System.arraycopy(target, 0, newArray, original.length - 1, target.length);
		return newArray;
	}

	private static byte[][] removeArray(byte[] original, int length) {
		byte[] newArray = new byte[original.length - length];
		System.arraycopy(original, 0, newArray, 0, length);

		byte[] deletedArray = new byte[length];
		System.arraycopy(original, length - 1, deletedArray, 0, length);

		byte[][] result = {{}, {}};
		result[0] = newArray;
		result[1] = deletedArray;
		return result;
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
		if (!clients.isEmpty()) { //只允许一个MPQ连接
			return;
		}

		MPQClient client = new MPQClient(new Frame(getJPREMain()), ctx.channel().remoteAddress(), ctx);
		clients.add(client);

		System.out.println("[Network] RemoteClient: " + ctx.channel().remoteAddress() + " connected.");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		MPQClient found = null;
		for (MPQClient client : clients) {
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