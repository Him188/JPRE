package net.mamoe.jpre.network.packet;

import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.EventType;

/**
 * @author Him188 @ JPRE Project
 */
public class ClientEventPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.CLIENT_EVENT;

	private byte id;
	private EventType type;
	private RobotQQ robot;
	private int subType;
	private long from;
	private long active;
	private long passive;
	private String message;

	private boolean broken;

	public byte getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}

	public RobotQQ getRobot() {
		return robot;
	}

	public EventType getType() {
		return type;
	}

	public int getSubType() {
		return subType;
	}

	public long getActive() {
		return active;
	}

	public long getFrom() {
		return from;
	}

	public long getPassive() {
		return passive;
	}

	public boolean isBroken() {
		return broken;
	}

	@Override
	public void encode() {

	}

	@Override
	public void decode() {
		id = this.getByte();
		type = EventType.match(this.getIntAdded());
		if (type == null) {
			broken = true;
			return;
		}

		long robotQQ = this.getLong();
		robot = null;
		if (robotQQ != 0) {
			robot = RobotQQ.getRobot(this.getClient().getFrame(), robotQQ);
		}

		subType = this.getInt(); // sub type
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
		from = this.getLong();
		active = this.getLong();
		passive = this.getLong();
		message = this.getStringDecoded();
	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
