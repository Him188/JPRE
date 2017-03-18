package com.him188.jpre.network;

import com.him188.jpre.JPREMain;
import com.him188.jpre.binary.Unpack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.jpre.JPREDisableEvent;
import com.him188.jpre.event.friend.FriendAddEvent;
import com.him188.jpre.event.group.GroupAdminChangeEvent;
import com.him188.jpre.event.group.GroupFileUploadEvent;
import com.him188.jpre.event.group.GroupMemberDecreaseEvent;
import com.him188.jpre.event.group.GroupMemberIncreaseEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.network.packet.*;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

import static com.him188.jpre.network.packet.PacketIds.*;

/**
 * 连接到服务器的客户端 (酷 Q 插件)
 *
 * @author Him188
 */
public class ConnectedClient {
	private boolean loggedIn = false;

	private SocketAddress address;

	public ConnectedClient(SocketAddress address) {
		this.address = address;
	}

	public boolean is(SocketAddress address) {
		return this.address == address;
	}

	public SocketAddress getAddress() {
		return address;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * 数据包处理
	 *
	 * @param ctx  管道
	 * @param data 数据
	 */
	public void dataReceive(ChannelHandlerContext ctx, byte[] data) {

		// TODO: 2017/3/18   修改为先把 data 转换为 Packet, 然后再进行处理

		Unpack packet = new Unpack(data);
		switch (packet.getByte()) {
			case PING:
				sendPacket(ctx, new ServerPongPacket());
				break;
			case EVENT:
				Class<?> eventClass = Event.matchEvent(packet.getByte());
				if (eventClass == null) {
					sendPacket(ctx, new InvalidEventPacket());
				}

				Event event = null;
				switch (packet.getInt()) {
					case EventTypes.EXIT:
					case EventTypes.DISABLE:
						event = new JPREDisableEvent(ctx.channel().remoteAddress());
						break;
					case EventTypes.PRIVATE_MESSAGE:
						event = new PrivateMessageEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getString(), packet.getInt());
						break;
					case EventTypes.GROUP_MESSAGE:
						event = new GroupMessageEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong(), packet.getString(), packet.getString(), packet.getInt());
						break;
					case EventTypes.DISCUSS_MESSAGE:
						event = new DiscussMessageEvent(packet.getInt(), packet.getInt(), packet.getLength(), packet.getLong(), packet.getString(), packet.getInt());
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
					case EventTypes.FRIEND_ADD:
						event = new FriendAddEvent(packet.getInt(), packet.getInt(), packet.getLong());
						break;
					case EventTypes.REQUEST_FRIEND_ADD:
						event = new AddFriendRequestEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getString(), packet.getString());
						break;
					case EventTypes.REQUEST_GROUP_ADD:
						event = new AddGroupRequestEvent(packet.getInt(), packet.getInt(), packet.getLong(), packet.getLong(), packet.getString(), packet.getString());
						break;
				}
				if (event == null) {
					sendPacket(ctx, new InvalidEventPacket());
					break;
				}
				sendPacket(ctx, new EventReplayPacket(JPREMain.callEvent(event)));
				break;
			case LOGIN:
				LoginPacket loginPacket = new LoginPacket();
				break;
			case PONG:
			case COMMAND:
			case LOGIN_RESULT:
			case INVALID_EVENT:
			case INVALID_ID:
				break;
			default:
				sendPacket(ctx, new InvalidIdPacket());
				break;
		}
	}

	/**
	 * 向管道发送数据包
	 *
	 * @param ctx    管道
	 * @param packet 包
	 *
	 * @return 是否成功
	 */
	public boolean sendPacket(ChannelHandlerContext ctx, Packet packet) {
		byte[] data = packet.encode();
		byte[] result = new byte[data.length + 1];
		try {
			result[0] = Packet.getNetworkId(packet);
		} catch (Throwable e) {
			return false;
		}
		System.arraycopy(data, 0, result, 1, data.length);
		ctx.writeAndFlush(result);
		return true;
	}
}
