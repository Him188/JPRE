package net.mamoe.jpre.network.packet;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class EventResultPacket extends Packet {
	public static final byte NETWORK_ID = Protocol.SERVER_EVENT_RESULT;

	//返回值-1 已收到信息但拒绝处理  //JPRE不使用
	//返回0 没有收到信息或不被处理   //JPRE不使用

	//返回1 被处理完毕,继续执行其他插件
	//返回2 被处理完毕,阻塞信息不再处理其他插件

	//特殊返回值:
	//0 忽略/取消该事件(如被添加好友 申请加入群
	//10 同意/批准该事件(如被添加好友 申请加入群
	//20 不同意/拒绝该事件(如被添加好友 申请加入群
	//30 单向同意该事件(仅用于被添加好友

	private final int returnValue;

	public EventResultPacket(int returnValue) {
		this.returnValue = returnValue;
	}

	@Override
	public void encode() {
		if (setEncoded(true)) {
			return;
		}

		clear();
		putInt(returnValue);
	}

	@Override
	public void decode() {

	}

	@Override
	public byte getNetworkId() {
		return NETWORK_ID;
	}
}
