package com.him188.jpre;

import com.him188.jpre.plugin.JavaPlugin;
import static com.him188.jpre.CoolQCaller.*;

/**
 * 系统酷 Q调用器
 * 不建议插件使用. 请使用 {@link JavaPlugin} 中的方法.
 *
 * @author Him188
 */
public final class SystemCoolQCaller extends BaseCoolQCaller{
	private int authCode;

	@Override
	public int getAuthCode() {
		return authCode;
	}

	protected void setAuthCode(int authCode) {
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
		return CQ_sendPrivateMsg(getAuthCode(), QQ, message) == 1;
	}

	@Override
	public boolean sendDiscussMessage(long discuss, String message) {
		return CQ_sendDiscussMsg(getAuthCode(), discuss, message) == 1;
	}

	@Override
	public boolean sendGroupMessage(long group, String message) {
		return CQ_sendGroupMsg(getAuthCode(), group, message) == 1;
	}
}
