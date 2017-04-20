package com.him188.jpre.network;

import com.him188.jpre.Frame;
import com.him188.jpre.OnlineStatus;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.Utils;
import com.him188.jpre.binary.Binary;
import com.him188.jpre.binary.Pack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventType;
import com.him188.jpre.event.group.GroupAdminChangeEvent;
import com.him188.jpre.event.group.GroupFileUploadEvent;
import com.him188.jpre.event.group.GroupMemberDecreaseEvent;
import com.him188.jpre.event.group.GroupMemberIncreaseEvent;
import com.him188.jpre.event.group.GroupMessageEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.mpq.MPQDisableEvent;
import com.him188.jpre.event.network.DataPacketReceiveEvent;
import com.him188.jpre.event.qq.*;
import com.him188.jpre.network.packet.*;
import io.netty.channel.ChannelHandlerContext;

import javax.lang.model.element.PackageElement;
import java.awt.*;
import java.net.SocketAddress;
import java.util.Arrays;

import static com.him188.jpre.network.packet.PacketIds.CLIENT_EVENT;
import static com.him188.jpre.network.packet.PacketIds.CLIENT_PING;

import static com.him188.jpre.event.EventType.*;

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

	@Override
	public String toString() {
		return "MPQClient(Address=" + address.toString() + ")";
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

				EventType type = EventType.match(packet.getInt());
				if (type == null) {
					sendPacket(new InvalidIdPacket());
					break;
				}

				RobotQQ robot = RobotQQ.getRobot(this.getFrame(), packet.getLong());

				switch (type) {
					case UNKNOWN:
						sendPacket(new InvalidIdPacket());
						break;
					case MESSAGE_FRIEND:
						event = new PrivateMessageEvent(robot, robot.getQQ(packet.getLong()), packet.getString());
						break;
					case MESSAGE_GROUP:
						event = new GroupMessageEvent(robot, robot.getGroup(packet.getLong()), robot.getQQ(packet.getLong()), packet.getString());
						break;
					// TODO: 2017/4/20 DISCUSSION, TEMPORARY event
					case FRIEND_ADD_RESULT:
						event = new FriendAddResultEvent(robot, robot.getQQ(packet.getLong()), true);
						break;
					case FRIEND_ADD_REQUEST:
						event = new FriendAddRequestEvent(robot, robot.getQQ(packet.getLong()), packet.getString());
						break;
					case FRIEND_STATUS_CHANGE:
						event = new FriendStatusChangeEvent(robot, robot.getQQ(packet.getLong()), OnlineStatus.match(packet.getInt()));
						break;
					case FRIEND_DELETE:
						event = new FriendDeleteEvent(robot, robot.getQQ(packet.getLong()));
						break;
					case FRIEND_SIGN_CHANGE:
						event = new FriendSignChangeEvent(robot, robot.getQQ(packet.getLong()), packet.getString());
						break;
					case FRIEND_TAOTAO_BE_COMMENT:
						event = new FriendTaotaoCommitEvent(robot, robot.getQQ(packet.getLong()), packet.getString());
						break;
					case FRIEND_TYPING:
						event = new FriendTypingEvent(robot, robot.getQQ(packet.getLong()));
						break;
					case FRIEND_FIRST_CONVESATION:
						event = new FriendFirstConversationEvent(robot, robot.getQQ(packet.getLong()));
						break;
					case FRIEND_SHAKE:
						event = new FriendShakeEvent(robot, robot.getQQ(packet.getLong()));
						break;

					// TODO: 2017/4/20 !!! group event
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
