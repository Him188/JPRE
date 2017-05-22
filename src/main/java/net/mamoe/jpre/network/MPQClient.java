package net.mamoe.jpre.network;

import io.netty.channel.ChannelHandlerContext;
import net.mamoe.jpre.Frame;
import net.mamoe.jpre.OnlineStatus;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.binary.BinaryStream;
import net.mamoe.jpre.event.Event;
import net.mamoe.jpre.event.EventType;
import net.mamoe.jpre.event.discussion.DiscussionMessageEvent;
import net.mamoe.jpre.event.frame.*;
import net.mamoe.jpre.event.group.*;
import net.mamoe.jpre.event.jpre.MenuActionEvent;
import net.mamoe.jpre.event.network.DataPacketReceiveEvent;
import net.mamoe.jpre.event.network.DataPacketSendEvent;
import net.mamoe.jpre.event.qq.*;
import net.mamoe.jpre.event.qq.taotao.TaoTaoBeCommentedEvent;
import net.mamoe.jpre.event.qq.tenpay.TenpayReceiveTransferEvent;
import net.mamoe.jpre.network.packet.*;
import net.mamoe.jpre.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

import static net.mamoe.jpre.network.packet.Protocol.*;

/**
 * 连接到服务器的客户端 (MPQ)
 * MPQ 连接到 JPRE 后创建. 详见: {@link NetworkPacketHandler#channelActive}
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("WeakerAccess")
public final class MPQClient {
    MPQClient(Frame frame, InetSocketAddress address, ChannelHandlerContext initCtx) {
        frame.setClient(this);
        this.frame = frame;
        this.address = address;
        this.lastCtx = initCtx;
    }

    @Override
    public String toString() {
        return "MPQClient(Address=" + address.toString() + ")";
    }

    public boolean is(InetSocketAddress address) {
        return address.getAddress().equals(this.address.getAddress());
    }

    /* Frame */

    private final Frame frame;

    public Frame getFrame() {
        return frame;
    }


    /* Address */
    private InetSocketAddress address;

    /**
     * 获取 IP 地址
     *
     * @return IP
     */
    public InetSocketAddress getAddress() {
        return address;
    }


    /* Network */

    /**
     * 数据包处理
     */
    @SuppressWarnings("ConstantConditions")
    public void dataReceive(BinaryStream packet) {
        byte pid = packet.getByte();
        switch (pid) {

            default:
                Packet pk;
                try {
                    pk = Packet.matchPacket(pid);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
                    sendPacket(new ServerInvalidIdPacket());
                    return;
                }
                if (pk == null) {
                    sendPacket(new ServerInvalidIdPacket());
                    return;
                }
                pk.setClient(this);
                pk.setData(packet.getLast());
                pk.setEncoded(true);
                composePacket(pk);
                break;
        }
    }

    /**
     * 数据包处理
     *
     * @param packet 包
     */
    @SuppressWarnings("ConstantConditions")
    public void composePacket(Packet packet) {
        packet.decode();

        System.out.print("Packet: " + packet);
        DataPacketReceiveEvent ev = new DataPacketReceiveEvent(packet);
        frame.getPluginManager().callEvent(ev);
        if (ev.isCancelled()) {
            System.out.println("  (Cancelled)");
            return;
        }
        System.out.println("");

        switch (packet.getNetworkId()) {
            case CLIENT_EVENT: {
                Event event = null;

                byte id = packet.getByte();
                EventType type = EventType.match(packet.getIntAdded());
                if (type == null) {
                    sendPacket(new ServerEventResultPacket(Event.STATUS_CONTINUE, id));
                    return;
                }

                long robotQQ = packet.getLong();
                RobotQQ robot = null;
                if (robotQQ != 0) {
                    robot = RobotQQ.getRobot(this.getFrame(), robotQQ);
                }

                int subType = packet.getInt(); // sub type
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
                String message = packet.getString();

                switch (type) {
                    case UNKNOWN:
                        sendPacket(new ServerEventResultPacket(Event.STATUS_CONTINUE, id));
                        break;

                    /* Message */
                    case MESSAGE_FRIEND:
                        event = new FriendMessageEvent(robot, robot.getQQ(active), message);
                        break;
                    case MESSAGE_GROUP:
                        event = new GroupMessageEvent(robot, robot.getGroup(from), robot.getQQ(active), message);
                        break;
                    case MESSAGE_DISCUSSION:
                        event = new DiscussionMessageEvent(robot, robot.getDiscussion(from), robot.getQQ(active), message);
                        break;

                    /* Friend */
                    case FRIEND_ADD_RESULT:
                        event = new FriendAddResultEvent(robot, robot.getQQ(active), true);
                        break;
                    case FRIEND_ADD_REQUEST:
                        event = new FriendAddRequestEvent(robot, robot.getQQ(active), message, subType == 1);
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
                        event = new TaoTaoBeCommentedEvent(robot, robot.getQQ(active), message);
                        break;
                    case FRIEND_TYPING:
                        event = new FriendTypingEvent(robot, robot.getQQ(active));
                        break;
                    case FRIEND_FIRST_CONVERSATION:
                        event = new FriendFirstConversationEvent(robot, robot.getQQ(active));
                        break;
                    case FRIEND_SHAKE:
                        event = new FriendShakeEvent(robot, robot.getQQ(active));
                        break;

                    /* Group */
                    case GROUP_JOIN_REQUEST:
                        event = new GroupJoinRequestEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_INVITATION_REQUEST:
                        event = new GroupInvitationRequestEvent(robot, robot.getGroup(from), robot.getQQ(active), robot.getQQ(passive), subType == 1);
                        break;
                    case GROUP_INVITATION:
                        event = new GroupInvitationEvent(robot, robot.getGroup(from), robot.getQQ(active));
                        break;
                    case GROUP_JOIN:
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

                    /* Frame */
                    case FRAME_STARTUP:
                        event = new FrameStartupEvent(this.getFrame());
                        break;
                    case FRAME_REBOOT:
                        event = new FrameRebootEvent(this.getFrame());
                        break;
                    case FRAME_QQ_ADD:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotAddEvent(this.getFrame(), robot);
                        break;
                    case FRAME_QQ_LOGIN:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotLoginEvent(this.getFrame(), robot);
                        break;
                    case FRAME_QQ_OFFLINE:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotOfflineEvent(this.getFrame(), robot);
                        break;
                    case FRAME_QQ_FORCE_OFFLINE:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotForceOfflineEvent(this.getFrame(), robot);
                        break;
                    case FRAME_QQ_CRASH:
                        robot = RobotQQ.getRobot(this.getFrame(), active);
                        event = new FrameRobotCrashEvent(this.getFrame(), robot);
                        break;

                    /* TENPAY */
                    case TENPAY_RECEIVE_TRANSFER:
                        event = new TenpayReceiveTransferEvent(robot, robot.getQQ(active), Integer.parseInt(message), packet.getString());
                        break;

                    /* JPRE */
                    case JPRE_MENU_ACTION:
                        event = new MenuActionEvent(robot, MenuActionEvent.ActionType.fromInt(subType));
                        break;
                }

                if (event == null) {
                    sendPacket(new ServerInvalidEventPacket(id));
                    return;

                }
                System.out.println("[Event] Parsed: " + event);
                sendPacket(new ServerEventResultPacket(frame.getPluginManager().callEvent(event), id));
                break;
            }

            case CLIENT_PING: {
                sendPacket(new ServerPongPacket());
                break;
            }

            case CLIENT_COMMAND_RESULT: {
                ClientCommandResultPacket pk = (ClientCommandResultPacket) packet;
                RobotQQ robot = RobotQQ.getRobot(this.getFrame(), pk.getRobot());
                robot.setResult(pk.getId(), pk.getResult());
                break;
            }

            case CLIENT_STATIC_COMMAND_RESULT: {
                ClientStaticCommandResultPacket pk = (ClientStaticCommandResultPacket) packet;
                this.getFrame().setResult(pk.getId(), pk.getResult());
                break;
            }

            case CLIENT_GET_PLUGIN_INFORMATION:
            case CLIENT_GET_PLUGIN_LIST:
                // TODO: 2017/5/18 完成包
            default: {
                sendPacket(new ServerInvalidIdPacket());
                break;
            }
        }
    }

    /**
     * 发送数据包
     *
     * @param packet 包
     */
    public void sendPacket(Packet packet) {
        packet.setClient(this);
        packet.encode();

        DataPacketSendEvent event = new DataPacketSendEvent(packet);
        getFrame().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }

        byte[] data = packet.getAll();
        byte[] result = new byte[data.length + 1];//数据包ID
        try {
            result[0] = Packet.getNetworkId(packet);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        System.arraycopy(data, 0, result, 1, data.length);
        result = Utils.arrayAppend(result, Protocol.SIGNATURE);
        this.getLastCtx().writeAndFlush(result);
        System.out.println("[Network] Packet sent:" + packet);
    }


    private ChannelHandlerContext lastCtx;

    private ChannelHandlerContext getLastCtx() {
        return lastCtx;
    }

    void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    void setCtx(ChannelHandlerContext lastCtx) {
        this.lastCtx = lastCtx;
    }
}
