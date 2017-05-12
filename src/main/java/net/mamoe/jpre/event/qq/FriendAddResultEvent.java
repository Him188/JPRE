package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 机器人添加好友成功/失败
 *
 * @author Him188
 */
public class FriendAddResultEvent extends QQEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final boolean succeed;

	public FriendAddResultEvent(RobotQQ robot, QQ qq, boolean succeed) {
		super(robot, qq);
		this.succeed = succeed;
	}

	/**
	 * 是否成功添加
	 *
	 * @return TRUE: 对方已添加为双向好友. FALSE: 拒绝添加
	 */
	public boolean isSucceed() {
		return succeed;
	}
}
