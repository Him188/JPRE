package com.him188.jpre.network;

import com.him188.jpre.Frame;
import com.him188.jpre.OnlineStatus;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.binary.Binary;
import com.him188.jpre.binary.Pack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventType;
import com.him188.jpre.event.frame.*;
import com.him188.jpre.event.group.*;
import com.him188.jpre.event.network.DataPacketReceiveEvent;
import com.him188.jpre.event.qq.*;
import com.him188.jpre.event.qq.tenpay.TenpayReceiveTransferEvent;
import com.him188.jpre.network.packet.*;

import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.InvocationTargetException;
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
public final class MPQClient {
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
						event = new PrivateMessageEvent(robot, robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					case MESSAGE_GROUP:
						event = new GroupMessageEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					// TODO: 2017/4/20 DISCUSSION, TEMPORARY event
					case FRIEND_ADD_RESULT:
						event = new FriendAddResultEvent(robot, robot.getQQNumber(packet.getLong()), true);
						break;
					case FRIEND_ADD_REQUEST:
						event = new FriendAddRequestEvent(robot, robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					case FRIEND_STATUS_CHANGE:
						event = new FriendStatusChangeEvent(robot, robot.getQQNumber(packet.getLong()), OnlineStatus.match(packet.getInt()));
						break;
					case FRIEND_DELETE:
						event = new FriendDeleteEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRIEND_SIGN_CHANGE:
						event = new FriendSignChangeEvent(robot, robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					case FRIEND_TAOTAO_BE_COMMENT:
						event = new FriendTaotaoCommitEvent(robot, robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					case FRIEND_TYPING:
						event = new FriendTypingEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRIEND_FIRST_CONVESATION:
						event = new FriendFirstConversationEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRIEND_SHAKE:
						event = new FriendShakeEvent(robot, robot.getQQNumber(packet.getLong()));
						break;

					case GROUP_JOIN_REQUEST:
						event = new GroupJoinRequestEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_INVITATION_REQUEST:
						event = new GroupInvitationRequestEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_INVITATION:
						event = new GroupInvitationEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_JOIN:
						event = new GroupJoinEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_QUIT:
						event = new GroupQuitEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_KICK:
						event = new GroupKickEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_DISSOLUTION:
						event = new GroupDissolutionEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_ADMIN_CHANGE:
						event = new GroupAdminChangeEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), packet.getBoolean() ? GroupAdminChangeEvent.ChangeType.PROMOTION : GroupAdminChangeEvent.ChangeType.DEMOTION);
						break;
					case GROUP_CARD_CHANGE:
						event = new GroupCardChangeEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), packet.getString());
						break;
					//case GROUP_NAME_CHANGE:
					case GROUP_NOTIFICATION_CHANGE:
						event = new GroupNotificationChangeEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), packet.getString());
						break;

					case GROUP_MUTE:
						event = new GroupMuteEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), robot.getQQNumber(packet.getLong()), packet.getInt());
						break;
					case GROUP_UNMUTE:
						event = new GroupUnmuteEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_WHOLE_MUTE:
						event = new GroupWholeMuteEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_WHOLE_UNMUTE:
						event = new GroupWholeUnmuteEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_ANONYMOUS_ENABLE:
						event = new GroupAnonymousEnableEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;
					case GROUP_ANONYMOUS_DISABLE:
						event = new GroupAnonymousDisableEvent(robot, robot.getGroup(packet.getLong()), robot.getQQNumber(packet.getLong()));
						break;

					case FRAME_STARTUP:
						event = new FrameStartupEvent(robot);
						break;
					case FRAME_REBOOT:
						event = new FrameRebootEvent(robot);
						break;
					case FRAME_QQ_ADD:
						event = new FrameQQAddEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRAME_QQ_LOGIN:
						event = new FrameQQLoginEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRAME_QQ_OFFLINE:
						event = new FrameQQOfflineEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRAME_QQ_FORCE_OFFLINE:
						event = new FrameQQForceOfflineEvent(robot, robot.getQQNumber(packet.getLong()));
						break;
					case FRAME_QQ_CRASH:
						event = new FrameQQCrashEvent(robot, robot.getQQNumber(packet.getLong()));
						break;

					case TENPAY_RECEIVE_TRANSFER:
						event = new TenpayReceiveTransferEvent(robot, robot.getQQNumber(packet.getLong()), packet.getInt(), packet.getString());
						break;
				}

				if (event == null) {
					sendPacket(new InvalidEventPacket());
					break;
				}
				System.out.println("[Event] Parsed: " + event);
				sendPacket(new EventResultPacket(frame.getPluginManager().callEvent(event)));
				break;
			default:
				Packet pk;
				try {
					pk = Packet.matchPacket(pid);
				} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
					sendPacket(new InvalidIdPacket());
					return;
				}
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

		DataPacketReceiveEvent event = new DataPacketReceiveEvent(packet);
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
