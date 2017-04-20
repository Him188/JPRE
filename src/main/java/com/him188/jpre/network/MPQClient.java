package com.him188.jpre.network;

import com.him188.jpre.Frame;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.Utils;
import com.him188.jpre.binary.Binary;
import com.him188.jpre.binary.Pack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.group.GroupAdminChangeEvent;
import com.him188.jpre.event.group.GroupFileUploadEvent;
import com.him188.jpre.event.group.GroupMemberDecreaseEvent;
import com.him188.jpre.event.group.GroupMemberIncreaseEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.mpq.MPQDisableEvent;
import com.him188.jpre.event.network.DataPacketReceiveEvent;
import com.him188.jpre.event.qq.PrivateMessageEvent;
import com.him188.jpre.network.packet.*;
import com.him188.jpre.plugin.PluginDescription;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;
import java.util.Arrays;

import static com.him188.jpre.network.packet.PacketIds.CLIENT_EVENT;
import static com.him188.jpre.network.packet.PacketIds.CLIENT_PING;

/**
 * 连接到服务器的客户端 (MPQ)]
 * MPQ 连接到 JPRE 后创建. 详见: {@link NetworkPacketHandler#channelActive}
 *
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public class MPQClient {
	/* Frame */

	private final Frame frame;

	public Frame getFrame() {
		return frame;
	}


	private SocketAddress address;

	public MPQClient(Frame frame, SocketAddress address, ChannelHandlerContext initCtx) {
		frame.setClient(this);
		this.frame = frame;
		this.address = address;
		this.lastCtx = initCtx;
	}

	public boolean is(SocketAddress address) {
		return this.address == address;
	}

	/**
	 * 获取 IP 地址
	 *
	 * @return IP
	 */
	public SocketAddress getAddress() {
		return address;
	}

	/**
	 * 数据包处理
	 *
	 * @param data 数据
	 */
	public void dataReceive(byte[] data) {
		Pack packet = new Pack(data);

		byte pid = packet.getByte();
		switch (pid) {
			case CLIENT_EVENT:
				Event event = null;
				int eid = packet.getInt();
				switch (eid) {
					case EventTypes.EXIT:
					case EventTypes.DISABLE:
						// TODO: 2017/4/20 close plugins
						break;
					case EventTypes.PRIVATE_MESSAGE:
						event = new PrivateMessageEvent(RobotQQ.getRobot(getFrame(), packet.getLong()), , Utils.utf8Decode(packet.getString()));
						break;// TODO: 2017/4/9 check them
					case EventTypes.GROUP_MESSAGE:
						event = new GroupMessageEvent(RobotQQ.getRobot(getFrame(), packet.getLong()), 0, packet.getLong(), packet.getLong(), Utils.utf8Decode(packet.getString()));
						break;
					case EventTypes.DISCUSS_MESSAGE:
						event = new DiscussMessageEvent(RobotQQ.getRobot(getFrame(), packet.getLong()), 0, packet.getLong(), packet.getLong(), Utils.utf8Decode(packet.getString()));
						break;
					case EventTypes.GROUP_UPLOAD:
						event = new GroupFileUploadEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong(), packet.getString());
						break;
					case EventTypes.GROUP_ADMIN:
						event = new GroupAdminChangeEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong());
						break;
					case EventTypes.GROUP_MEMBER_DECREASE:
						event = new GroupMemberDecreaseEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong(), packet.getLong());
						break;
					case EventTypes.GROUP_MEMBER_INCREASE:
						event = new GroupMemberIncreaseEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong(), packet.getLong());
						break;
					// TODO: 2017/4/11 add all
				}
				if (event == null) {
					sendPacket(new InvalidEventPacket());
					break;
				}
				System.out.println("[Event] Parsed: " + event);
				sendPacket(new EventResultPacket(frame.getPluginManager().callEvent(event)));
				break;
			default:
				Packet pk = Packet.matchPacket(pid);
				System.out.println("Packet: " + pk);
				if (pk == null) {
					sendPacket(new InvalidIdPacket());
					return;
				}
				pk.setClient(this);
				pk.setData(packet.getAll());
				composePacket(pk);
				break;
		}
	}

	/**
	 * 数据包处理
	 *
	 * @param packet 包
	 */
	public void composePacket(Packet packet) {
		packet.decode();

		DataPacketReceiveEvent event = new DataPacketReceiveEvent(packet, this);
		frame.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return;
		}

		switch (packet.getNetworkId()) {
			case CLIENT_PING:
				sendPacket(new ServerPongPacket());
				break;

			default:
				sendPacket(new InvalidIdPacket());
				break;

		}
	}

	/**
	 * 发送数据包
	 *
	 * @param packet 包
	 */
	public void sendPacket(Packet packet) {
		packet.encode();
		byte[] data = packet.getAll();
		byte[] result = new byte[data.length + 4 + 1];//数据包长度, 数据包ID
		try {
			System.arraycopy(Binary.toBytes(data.length + 1), 0, result, 0, 4); //+1: 数据包ID
			result[4] = Packet.getNetworkId(packet); //数据包长度占用 0 1 2 3
		} catch (Throwable e) {
			e.printStackTrace();
			return;
		}
		System.arraycopy(data, 0, result, 5, data.length);
		this.getLastCtx().writeAndFlush(result);
		System.out.println("[Network] Packet sent:" + packet + ", data: " + Arrays.toString(result));
	}


	private ChannelHandlerContext lastCtx;

	private ChannelHandlerContext getLastCtx() {
		return lastCtx;
	}

}
