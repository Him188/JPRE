package com.him188.jpre.command;

import com.him188.jpre.plugin.JavaPlugin;

/**
 * 系统酷 Q调用器
 * 不建议插件使用. 请使用 {@link JavaPlugin} 中的方法.
 *
 * @author Him188
 */
public final class SystemCoolQCaller extends BaseCoolQCaller{
	private int authCode = -1;

	@Override
	public int getAuthCode() {
		return authCode;
	}

	public void setAuthCode(int authCode) {
		if (this.authCode != -1) {
			return;
		}
		this.authCode = authCode;
	}

	/**
	 * 相较 {@link JavaPlugin}, 本方法不会调用事件
	 *
	 * @param QQ      QQ号码
	 * @param message 消息内容
	 *
	 * @return 是否成功
	 */
	@Override
	public boolean sendPrivateMessage(long QQ, String message) {
		return CoolQCaller.CQ_sendPrivateMsg(getAuthCode(), QQ, message) == 0;
	}

	@Override
	public boolean sendDiscussMessage(long discuss, String message) {
		return CoolQCaller.CQ_sendDiscussMsg(getAuthCode(), discuss, message) == 0;
	}

	@Override
	public boolean sendGroupMessage(long group, String message) {
		return CoolQCaller.CQ_sendGroupMsg(getAuthCode(), group, message) == 0;
	}
}
