package com.him188.jpre.network;

import com.him188.jpre.JPREMain;
import com.him188.jpre.PluginManager;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.Utils;
import com.him188.jpre.binary.Binary;
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
import java.util.Arrays;

import static com.him188.jpre.network.packet.PacketIds.*;

/**
 * 连接到服务器的客户端 (MPQ)]
 * MPQ 连接到 JPRE 后创建. 详见: {@link NetworkPacketHandler#channelActive}
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

	/**
	 * 获取 IP 地址
	 *
	 * @return IP
	 */
	public SocketAddress getAddress() {
		return address;
	}

	/**
	 * 是否已验证密码 {@link LoginPacket}
	 *
	 * @return 是否已验证密码
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * 数据包处理
	 *
	 * @param data 数据
	 */
	public void dataReceive(byte[] data) {
		Unpack packet = new Unpack(data);

		byte pid = packet.getByte();
		switch (pid) {
			case EVENT:
				Event event = null;
				int eid = packet.getInt();
				switch (eid) {
					case EventTypes.EXIT:
					case EventTypes.DISABLE:
						event = new JPREDisableEvent(this);
						break;
					case EventTypes.PRIVATE_MESSAGE:
						event = new PrivateMessageEvent(RobotQQ.getRobot(packet.getLong()), PrivateMessageEvent.MESSAGE_TYPE_PRIVATE, 0, packet.getLong(), Utils.utf8Decode(packet.getString()));
						break;// TODO: 2017/4/9 check them
					case EventTypes.GROUP_MESSAGE:
						event = new GroupMessageEvent(RobotQQ.getRobot(packet.getLong()), 0, packet.getLong(), packet.getLong(), Utils.utf8Decode(packet.getString()));
						break;
					case EventTypes.DISCUSS_MESSAGE:
						event = new DiscussMessageEvent(RobotQQ.getRobot(packet.getLong()), 0, packet.getLength(), packet.getLong(), Utils.utf8Decode(packet.getString()));
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
					sendPacket(new InvalidEventPacket());
					break;
				}
				System.out.println("[Event] Parsed: " + event);
				sendPacket(new EventResultPacket(JPREMain.callEvent(event)));
				break;
			default:
				Packet pk = Packet.matchPacket(pid);
				System.out.println("Packet: " + pk);
				if (pk == null) {
					sendPacket(new InvalidIdPacket());
					return;
				}
				pk.setClient(this);
				composePacket(pk, packet.getAll());
				break;
		}
	}

	/**
	 * 数据包处理
	 *
	 * @param packet 包
	 * @param data   数据
	 */
	public void composePacket(Packet packet, byte[] data) {
		Unpack unpack = new Unpack(data);
		packet.decode(unpack);

		DataPacketReceiveEvent event = new DataPacketReceiveEvent(packet, this);
		JPREMain.callEvent(event);
		if (event.isCancelled()) {
			return;
		}

		switch (packet.getNetworkId()) {
			case PING:
				sendPacket(new ServerPongPacket());
				break;
			case LOGIN:
				if (((LoginPacket) packet).getPassword().equalsIgnoreCase(JPREMain.getPassword())) {
					sendPacket(new LoginResultPacket(true));
					loggedIn = true;
					System.out.println("Client login: succeed");
				} else {
					sendPacket(new LoginResultPacket(false));
					loggedIn = false;
					System.out.println("Client login: failed");
				}

				break;
			default:
				if (!isLoggedIn()) {
					sendPacket(new AccessDeniedPacket());
					break;
				}

				switch (packet.getNetworkId()) {
					case ENABLE_PLUGIN:
						sendPacket(new EnablePluginResultPacket(JPREMain.enablePlugin(((EnablePluginPacket) packet).getName())));
						break;
					case LOAD_PLUGIN:
						sendPacket(new LoadPluginResultPacket(JPREMain.loadPlugin(((LoadPluginPacket) packet).getName())));
						break;
					case DISABLE_PLUGIN:
						sendPacket(new DisablePluginResultPacket(JPREMain.disablePlugin(((DisablePluginPacket) packet).getName())));
						break;
					case LOAD_PLUGIN_DESCRIPTION:
						sendPacket(new LoadPluginDescriptionResultPacket(JPREMain.loadPluginDescription(((LoadPluginDescriptionPacket) packet).getName())));
						break;
					case GET_PLUGIN_INFORMATION:
						PluginDescription description = PluginManager.matchPluginDescription(((GetPluginInformationPacket) packet).getName());
						if (description == null) {
							sendPacket(new GetPluginInformationResultPacket("", "", "", "", 0, ""));
							break;
						}
						sendPacket(new GetPluginInformationResultPacket(
								description.getName(),
								description.getAuthor(),
								description.getVersion(),
								description.getMainClass(),
								description.getAPIVersion(),
								description.getDescription()
						));
						break;
					case SET_INFORMATION:
						JPREMain.init(((SetInformationPacket) packet).getDataFolder());
						sendPacket(new SetInformationResultPacket(true));
						break;
					case COMMAND_RESULT:
						RobotQQ.addResult(((CommandResultPacket) packet).getResult());
						break;
					default:
						sendPacket(new InvalidIdPacket());
						break;
				}
		}
	}

	public ChannelHandlerContext getLastCtx() {
		return lastCtx;
	}

	/**
	 * 发送数据包
	 *
	 * @param packet 包
	 */
	public void sendPacket(Packet packet) {
		byte[] data = packet.encode();
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
}
