package com.him188.jpre.network;

import com.him188.jpre.*;
import com.him188.jpre.binary.Pack;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventType;
import com.him188.jpre.event.discussion.DiscussionMessageEvent;
import com.him188.jpre.event.frame.*;
import com.him188.jpre.event.group.*;
import com.him188.jpre.event.network.DataPacketReceiveEvent;
import com.him188.jpre.event.qq.*;
import com.him188.jpre.event.qq.tenpay.TenpayReceiveTransferEvent;
import com.him188.jpre.network.packet.*;
import io.netty.channel.ChannelHandlerContext;
import sun.misc.Unsafe;

import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.util.Arrays;

import static com.him188.jpre.network.packet.Protocol.CLIENT_EVENT;
import static com.him188.jpre.network.packet.Protocol.CLIENT_PING;

/**
 * 连接到服务器的客户端 (MPQ)
 * MPQ 连接到 JPRE 后创建. 详见: {@link NetworkPacketHandler#channelActive}
 *
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public final class MPQClient {
    MPQClient(Frame frame, SocketAddress address, ChannelHandlerContext initCtx) {
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

    /* Frame */

    private final Frame frame;

    public Frame getFrame() {
        return frame;
    }


    /* Address */
    private SocketAddress address;

    /**
     * 获取 IP 地址
     *
     * @return IP
     */
    public SocketAddress getAddress() {
        return address;
    }


    /* Network */

    /**
     * 数据包处理
     */
    @SuppressWarnings("ConstantConditions")
    public void dataReceive(Pack packet) {
        byte pid = packet.getByte();
        switch (pid) {
            case CLIENT_EVENT:
                Event event = null;

                EventType type = EventType.match(packet.getInt());
                if (type == null) {
                    sendPacket(new EventResultPacket(false));
                    break;
                }

                long robotQQ = packet.getLong();
                RobotQQ robot = null;
                if (robotQQ != 0) {
                    robot = RobotQQ.getRobot(this.getFrame(), robotQQ);
                }

                packet.getInt(); // sub type
                /*
                * .版本 2
				* .参数 参_机器人QQ, 文本型, , 多QQ登录情况下用于识别是哪个Q
				* .参数 参_消息类型, 整数型, , 信息唯一标识-1 未定义事件 1 好友信息 2,群信息 3,讨论组信息 4,临时会话 1001,被添加好友 1002,好友在线状态改变 1003 被删除好友 1004 签名变更 2001 某人申请加入群 2002 某人被邀请加入群 2003 我被邀请加入群 2005 某人被批准加入了群 2006 某人退出群  2007 某人被管理移除群 2008 某群被解散 2009 某人成为管理员 2010 某人被取消管理员 2011 群名片变动 2012 群名变动//暂未解析 2013 群公告变动
				* .参数 参_消息子类型, 整数型, , 对象申请、被邀请入群事件下该值为1时即对象为不良成员
				* .参数 参_消息来源, 文本型, , 信息的源头  群号,好友QQ,讨论组ID,临时会话对象QQ等
				* .参数 参_触发对象_主动, 文本型, , 主动触发这条信息的对象 T人时为T人的管理员QQ
				* .参数 参_触发对象_被动, 文本型, , 被动接受这条信息的对象 T人时为被T对象的QQ
				* .参数 参_消息内容, 文本型, , 视情况而定的信息内容  申请入群时为入群理由,添加好友为附加信息,T人之类的为空
				* .参数 参_原始信息, 文本型, , 经过解密后的封包字节数据或json结构信息
				* .参数 参_信息回传文本指针_Out, 整数型, , 信息回传指针。 视情况而定的返回附加文本信息  拒绝好友申请时则为拒绝理由 方式:’写到内存("测试",参_信息回传文本指针_Out)
				* */
                long from = packet.getLong();
                long active = packet.getLong();
                long passive = packet.getLong();
                //String message = Utils.GBKDecode(packet.getString());
                String message =packet.getString();

                switch (type) {
                    case UNKNOWN:
                        sendPacket(new EventResultPacket(false));
                        break;
                    case MESSAGE_FRIEND:
                        event = new PrivateMessageEvent(robot, robot.getQQ(active), message);
                        break;
                    case MESSAGE_GROUP:
                        event = new GroupMessageEvent(robot, robot.getGroup(from), robot.getQQ(active), message);
                        if (message.contains("测试") || message.contains("Test")) {
                            robot.sendGroupMessage(from, "Hello! " + Code.at(active) + ", You just sent a message which has the length of " + message.length() + ", message context:  " + message);
                        }
                        break;
                    case MESSAGE_DISCUSSION:
                        event = new DiscussionMessageEvent(robot, robot.getDiscussion(from), robot.getQQ(active), message);
                        break;
                    case FRIEND_ADD_RESULT:
                        event = new FriendAddResultEvent(robot, robot.getQQ(active), true);
                        break;
                    case FRIEND_ADD_REQUEST:
                        event = new FriendAddRequestEvent(robot, robot.getQQ(active), message);
                        break;
                    case FRIEND_STATUS_CHANGE:
                        event = new FriendStatusChangeEvent(robot, robot.getQQ(active), OnlineStatus.match(Integer.parseInt(message)));
                        break;
                    case FRIEND_DELETE:
                        event = new FriendDeleteEvent(robot, robot.getQQ(active));
                        break;
                    case FRIEND_SIGN_CHANGE:
                        event = new FriendSignChangeEvent(robot, robot.getQQ(active), message);
                        break;
                    case FRIEND_TAOTAO_BE_COMMENT:
                        event = new FriendTaotaoCommitEvent(robot, robot.getQQ(active), message);
                        break;
                    case FRIEND_TYPING:
                        event = new FriendTypingEvent(robot, robot.getQQ(active));
                        break;
                    case FRIEND_FIRST_CONVESATION:
                        event = new FriendFirstConversationEvent(robot, robot.getQQ(active));
                        break;
                    case FRIEND_SHAKE:
                        event = new FriendShakeEvent(robot, robot.getQQ(active));
                        break;

                    case GROUP_JOIN_REQUEST:
                        event = new GroupJoinRequestEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_INVITATION_REQUEST:
                        event = new GroupInvitationRequestEvent(robot, robot.getGroup(from), robot.getQQ(active), robot.getQQ(passive));
                        break;
                    case GROUP_INVITATION:
                        event = new GroupInvitationEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_JOIN: // TODO: 2017/4/22  check active and passive
                        event = new GroupJoinEvent(robot, robot.getGroup(from), robot.getQQ(active), robot.getQQ(passive));
                        break;
                    case GROUP_QUIT:
                        event = new GroupQuitEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_KICK:
                        event = new GroupKickEvent(robot, robot.getGroup(from), robot.getQQ(passive), robot.getQQ(active));
                        break;
                    case GROUP_DISSOLUTION:
                        event = new GroupDissolutionEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_ADMIN_PROMOTION:
                        event = new GroupAdminChangeEvent(robot, robot.getGroup(from), robot.getQQ(passive), GroupAdminChangeEvent.ChangeType.PROMOTION);
                        break;
                    case GROUP_ADMIN_DEMOTION:
                        event = new GroupAdminChangeEvent(robot, robot.getGroup(from), robot.getQQ(passive), GroupAdminChangeEvent.ChangeType.DEMOTION);
                        break;
                    case GROUP_CARD_CHANGE: // TODO: 2017/4/22  check active and passive
                        event = new GroupCardChangeEvent(robot, robot.getGroup(from), robot.getQQ(active), message);
                        break;
                    //case GROUP_NAME_CHANGE:
                    case GROUP_NOTIFICATION_CHANGE:
                        event = new GroupNotificationChangeEvent(robot, robot.getGroup(from), robot.getQQ(active), message);
                        break;

                    case GROUP_MUTE:
                        event = new GroupMuteEvent(robot, robot.getGroup(from), robot.getQQ(passive), robot.getQQ(active), Integer.parseInt(message));
                        break;
                    case GROUP_UNMUTE:
                        event = new GroupUnmuteEvent(robot, robot.getGroup(from), robot.getQQ(passive), robot.getQQ(active));
                        break;
                    case GROUP_WHOLE_MUTE:
                        event = new GroupWholeMuteEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_WHOLE_UNMUTE:
                        event = new GroupWholeUnmuteEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_ANONYMOUS_ENABLE:
                        event = new GroupAnonymousEnableEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_ANONYMOUS_DISABLE:
                        event = new GroupAnonymousDisableEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;

                    case FRAME_STARTUP:
                        event = new FrameStartupEvent();
                        break;
                    case FRAME_REBOOT:
                        event = new FrameRebootEvent();
                        break;
                    case FRAME_QQ_ADD:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotAddEvent(robot);
                        break;
                    case FRAME_QQ_LOGIN:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotLoginEvent(robot);
                        break;
                    case FRAME_QQ_OFFLINE:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotOfflineEvent(robot);
                        break;
                    case FRAME_QQ_FORCE_OFFLINE:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotForceOfflineEvent(robot);
                        break;
                    case FRAME_QQ_CRASH:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotCrashEvent(robot);
                        break;

                    case TENPAY_RECEIVE_TRANSFER:
                        event = new TenpayReceiveTransferEvent(robot, robot.getQQ(active), Integer.parseInt(message), packet.getString());
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
                    sendPacket(new EventResultPacket(false));
                    return;
                }
                System.out.println("Packet: " + pk);
                if (pk == null) {
                    sendPacket(new EventResultPacket(false));
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
                sendPacket(new EventResultPacket(false));
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
        byte[] result = new byte[data.length + 1];//数据包ID
        try {
            result[0] = Packet.getNetworkId(packet);
        } catch (Throwable e) {
            e.printStackTrace();
            return;
        }
        System.arraycopy(data, 0, result, 1, data.length);
        result = Utils.arrayAppend(result, Protocol.SIGNATURE);
        this.getLastCtx().writeAndFlush(result);
        System.out.println("[Network] Packet sent:" + packet + ", data: " + Arrays.toString(result));
    }


    private ChannelHandlerContext lastCtx;

    private ChannelHandlerContext getLastCtx() {
        return lastCtx;
    }

}
