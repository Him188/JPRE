package com.him188.jpre.event.qq;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 机器人添加好友成功/失败
 *
 * @author Him188
 */
public class FriendAddResultEvent extends QQEvent {
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