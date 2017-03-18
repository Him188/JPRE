package com.him188.jpre.network;

import com.him188.jpre.CoolQCaller;
import com.him188.jpre.JPREMain;
import com.him188.jpre.PluginManager;
import com.him188.jpre.binary.Unpack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.friend.FriendAddEvent;
import com.him188.jpre.event.group.GroupAdminChangeEvent;
import com.him188.jpre.event.group.GroupFileUploadEvent;
import com.him188.jpre.event.group.GroupMemberDecreaseEvent;
import com.him188.jpre.event.group.GroupMemberIncreaseEvent;
import com.him188.jpre.event.jpre.JPREDisableEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.network.DataPacketReceiveEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.network.packet.*;
import com.him188.jpre.plugin.PluginDescription;
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

	private ChannelHandlerContext lastCtx;

	public ConnectedClient(SocketAddress address, ChannelHandlerContext initCtx) {
		this.address = address;
		this.lastCtx = initCtx;
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
		lastCtx = ctx;
		Unpack packet = new Unpack(data);

		switch (packet.getByte()) {
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
				sendPacket(ctx, new EventResultPacket(JPREMain.callEvent(event)));
				break;
			default:
				Packet pk = Packet.matchPacket(packet.getByte());
				if (pk == null) {
					sendPacket(ctx, new InvalidIdPacket());
					return;
				}
				pk.setChannelHandlerContext(ctx);
				composePacket(pk, data);
				break;
		}
	}

	public void composePacket(Packet packet, byte[] data) {
		Unpack unpack = new Unpack(data);
		packet.decode(unpack);

		DataPacketReceiveEvent event = new DataPacketReceiveEvent(packet, this);
		JPREMain.callEvent(event);
		if (event.isCancelled()) {
			return;
		}

		ChannelHandlerContext ctx = packet.getChannelHandlerContext();
		switch (packet.getNetworkId()) {
			case PING:
				sendPacket(ctx, new ServerPongPacket());
				break;
			case LOGIN:
				if (((LoginPacket) packet).getPassword().equals(JPREMain.getPassword())) {
					sendPacket(ctx, new LoginResultPacket(true));
					loggedIn = true;
				} else
					sendPacket(ctx, new LoginResultPacket(false));

				break;
			default:
				if (!isLoggedIn()) {
					sendPacket(ctx, new InvalidIdPacket());
					break;
				}

				switch (packet.getNetworkId()) {
					case ENABLE_PLUGIN:
						sendPacket(ctx, new EnablePluginResultPacket(JPREMain.enablePlugin(((EnablePluginPacket) packet).getName())));
						break;
					case LOAD_PLUGIN:
						sendPacket(ctx, new LoadPluginResultPacket(JPREMain.loadPlugin(((LoadPluginPacket) packet).getName())));
						break;
					case DISABLE_PLUGIN:
						sendPacket(ctx, new DisablePluginResultPacket(JPREMain.disablePlugin(((DisablePluginPacket) packet).getName())));
						break;
					case LOAD_PLUGIN_DESCRIPTION:
						sendPacket(ctx, new LoadPluginDescriptionResultPacket(JPREMain.loadPluginDescription(((LoadPluginDescriptionPacket) packet).getName())));
						break;
					case GET_PLUGIN_INFORMATION:
						PluginDescription description = PluginManager.matchPluginDescription(((LoadPluginDescriptionPacket) packet).getName());
						if (description == null) {
							sendPacket(ctx, new GetPluginInformationResultPacket("", "", "", "", 0, ""));
							break;
						}
						sendPacket(ctx, new GetPluginInformationResultPacket(
								description.getName(),
								description.getAuthor(),
								description.getVersion(),
								description.getMainClass(),
								description.getAPIVersion(),
								description.getDescription()
						));
						break;
					case SET_INFORMATION:
						JPREMain.setAuthCode(((SetInformationPacket) packet).getAuthCode());
						JPREMain.setCqApi(((SetInformationPacket) packet).getApi());
						sendPacket(ctx, new SetInformationResultPacket(true));
						break;
					case COMMAND_RESULT:
						CoolQCaller.addResult(((CommandResultPacket) packet).getResult());
						break;
					default:
						sendPacket(ctx, new InvalidIdPacket());
						break;
				}
		}
	}

	public ChannelHandlerContext getLastCtx() {
		return lastCtx;
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
		lastCtx = ctx;

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
