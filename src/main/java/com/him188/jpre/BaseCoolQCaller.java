package com.him188.jpre;

import com.him188.jpre.event.action.send.SendDiscussMessageEvent;
import com.him188.jpre.event.action.send.SendGroupMessageEvent;
import com.him188.jpre.event.action.send.SendPrivateMessageEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.infomation.Member;
import com.him188.jpre.infomation.User;
import com.him188.jpre.plugin.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.him188.jpre.CoolQCaller.*;
/**
 * 酷 Q API 调用器
 *
 * @author Him188
 */
@SuppressWarnings("SameParameterValue")
abstract public class BaseCoolQCaller {
	abstract public int getAuthCode();

	/**
	 * 发送私聊消息
	 *
	 * @param QQ      QQ号码
	 * @param message 消息内容
	 * @return 是否成功
	 */
	public boolean sendPrivateMessage(long QQ, String message) {
		SendPrivateMessageEvent ev = new SendPrivateMessageEvent(QQ, message);
		JPREMain.callEvent(ev);
		return !ev.isCancelled() && CQ_sendPrivateMsg(getAuthCode(), QQ, ev.getMessage()) == 1;
	}

	/**
	 * 发送群消息
	 *
	 * @param group   群号码
	 * @param message 消息内容
	 * @return 是否成功
	 */
	public boolean sendGroupMessage(long group, String message) {
		SendGroupMessageEvent ev = new SendGroupMessageEvent(group, message);
		JPREMain.callEvent(ev);
		return !ev.isCancelled() && CQ_sendGroupMsg(getAuthCode(), group, ev.getMessage()) == 1;
	}

	/**
	 * 发送讨论组消息
	 *
	 * @param discuss 讨论组号码
	 * @param message 消息内容
	 * @return 是否成功
	 */
	public boolean sendDiscussMessage(long discuss, String message) {
		SendDiscussMessageEvent ev = new SendDiscussMessageEvent(discuss, message);
		JPREMain.callEvent(ev);
		return !ev.isCancelled() && CQ_sendDiscussMsg(getAuthCode(), discuss, ev.getMessage()) == 1;
	}

	/**
	 * 发送名片赞 (1次)
	 *
	 * @param QQ QQ号码
	 * @return 是否成功
	 */
	public final boolean sendLike(long QQ) {
		return sendLike(QQ, 1);
	}

	/**
	 * 发送名片赞
	 *
	 * @param QQ    QQ号码
	 * @param times 次数 (0~10)
	 * @return 是否成功
	 */
	public final boolean sendLike(long QQ, int times) {
		return CQ_sendLikeV2(getAuthCode(), QQ, Math.min(Math.max(times, 1), 10)) == 1;
	}

	/**
	 * 获取 Cookies
	 *
	 * @return Cookies
	 */
	public final int getCookies() {
		return CQ_getCookies(getAuthCode());
	}

	/**
	 * 接受语音
	 *
	 * @param fileName  接受消息的文件名
	 * @param outFormat 应用所需格式, 目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac
	 * @return ? 似乎会直接返回 file
	 */
	public final String getRecord(String fileName, String outFormat) {
		return CQ_getRecord(getAuthCode(), fileName, outFormat);
	}

	/**
	 * 接受并读取语音文件
	 *
	 * @param fileName  接受消息的文件名
	 * @param outFormat 应用所需格式, 目前支持 mp3,amr,wma,m4a,spx,ogg,wav,flac
	 * @return 语音文件内容
	 */
	public final byte[] getRecordBytes(String fileName, String outFormat) {
		getRecord(fileName, outFormat);

		String result = "";
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));
			String tempString;
			while ((tempString = reader.readLine()) != null) {
				result += tempString + "\n";
			}
			reader.close();
		} catch (Exception e) {
			return new byte[]{};
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ignored) {
				}
			}
		}

		return result.getBytes();
	}

	/**
	 * CsrfToken, 即 QQ 网页用到的 bkn/g_tk 等
	 *
	 * @return CsrfToken
	 */
	public final int getCsrfToken() {
		return CQ_getCsrfToken(getAuthCode());
	}

	/**
	 * 获取应用配置目录
	 *
	 * @return 应用配置目录
	 */
	public final String getAppDirectory() {
		return CQ_getAppDirectory(getAuthCode());
	}

	/**
	 * 获取酷Q登录的QQ号码
	 *
	 * @return 酷Q登录的QQ号码
	 */
	public final long getLoginQQ() {
		return CQ_getLoginQQ(getAuthCode());
	}

	/**
	 * 获取酷Q登录的QQ号码的昵称
	 *
	 * @return 酷Q登录的QQ号码的昵称
	 */
	public final String getNickOfLoginedQQ() {
		return CQ_getLoginNick(getAuthCode());
	}

	/**
	 * 踢出群成员
	 *
	 * @param group 群号码
	 * @param QQ    QQ号码
	 * @param block 是否禁止再次加入
	 * @return 是否成功
	 */
	public final boolean groupKick(long group, long QQ, boolean block) {
		return CQ_setGroupKick(getAuthCode(), group, QQ, block) == 1;
	}

	/**
	 * 禁言群成员
	 *
	 * @param group 群号码
	 * @param QQ    QQ号码
	 * @param time  时间. 单位为秒, 范围 60-2592000
	 * @return 是否成功
	 */
	public final boolean groupBanMember(long group, long QQ, long time) {
		return CQ_setGroupBan(getAuthCode(), group, QQ, Math.max(Math.min(time, 2592000), 60)) == 1;
	}

	/**
	 * 设置/取消群管理员
	 *
	 * @param group 群号码
	 * @param QQ    QQ号码
	 * @param admin 是否管理员
	 * @return 是否成功
	 */
	public final boolean groupSetAdmin(long group, long QQ, boolean admin) {
		return CQ_setGroupAdmin(getAuthCode(), group, QQ, admin) == 1;
	}

	/**
	 * 设置群成员的专属头衔
	 *
	 * @param group     群号码
	 * @param QQ        QQ号码
	 * @param title     头衔
	 * @param timeLimit 时间限制. 单位为秒, 永久为 -1
	 * @return 是否成功
	 */
	public final boolean groupSetTitleOfMember(long group, long QQ, String title, long timeLimit) {
		return CQ_setGroupSpecialTitle(getAuthCode(), group, QQ, title, timeLimit == -1 ? -1 : Math.abs(timeLimit)) == 1;
	}

	/**
	 * 开启/关闭全员禁言 (开启后只允许管理员和群主发言)
	 *
	 * @param group 群号码
	 * @param ban   是否开启
	 * @return 是否成功
	 */
	public final boolean groupSetWholeBan(long group, boolean ban) {
		return CQ_setGroupWholeBan(getAuthCode(), group, ban) == 1;
	}

	/**
	 * 禁言匿名用户
	 *
	 * @param group       群号码
	 * @param anonymousId 匿名昵称
	 * @param time        时间. 单位为秒, 范围 60-2592000
	 * @return 是否成功
	 */
	public final boolean groupSetAnonymousBan(long group, String anonymousId, long time) {
		return CQ_setGroupAnonymousBan(getAuthCode(), group, anonymousId, Math.max(Math.min(time, 2592000), 60)) == 1;
	}

	/**
	 * 开启/关闭匿名功能
	 * 关闭后群员将无法切换到匿名发言
	 *
	 * @param group   群号码
	 * @param enabled 是否开启
	 * @return 是否成功
	 */
	public final boolean groupSetAnonymousEnabled(long group, boolean enabled) {
		return CQ_setGroupAnonymous(getAuthCode(), group, enabled) == 1;
	}

	/**
	 * 设置群成员名片
	 *
	 * @param group 群号码
	 * @param QQ    QQ号码
	 * @param card  名片
	 * @return 是否成功
	 */
	public final boolean groupSetCard(long group, long QQ, String card) {
		return CQ_setGroupCard(getAuthCode(), group, QQ, card) == 1;
	}

	/**
	 * 离开/解散群
	 *
	 * @param group    群号
	 * @param dissolve true: 解散(群主), false: 离开(成员, 管理员)
	 * @return 是否成功
	 */
	public final boolean groupLeave(long group, boolean dissolve) {
		return CQ_setGroupLeave(getAuthCode(), group, dissolve) == 1;
	}

	/**
	 * 离开讨论组
	 *
	 * @param discuss 讨论组号码
	 * @return 是否成功
	 */
	public final boolean discussLeave(long discuss) {
		return CQ_setDiscussLeave(getAuthCode(), discuss) == 1;
	}

	/**
	 * 响应好友添加请求
	 *
	 * @param requestFlag 请求 Id
	 * @param accept      是否接受请求
	 * @return 是否成功
	 */
	public final boolean friendAnswerAddRequest(String requestFlag, boolean accept) {
		return friendAnswerAddRequest(requestFlag, accept, "");
	}

	/**
	 * 响应好友添加请求
	 *
	 * @param requestFlag  请求 Id
	 * @param accept       是否接受请求
	 * @param nickIfAccept 接受后的好友备注
	 * @return 是否成功
	 */
	public final boolean friendAnswerAddRequest(String requestFlag, boolean accept, String nickIfAccept) {
		return CQ_setFriendAddRequest(getAuthCode(), requestFlag, accept ? RESULT_TYPE_ACCEPT : RESULT_TYPE_DENIED, nickIfAccept) == 1;
	}

	/**
	 * 响应群添加请求
	 *
	 * @param requestFlag 请求 Id
	 * @param requestType 请求类型
	 * @param accept      是否同意请求
	 * @return 是否成功
	 * @see CoolQCaller#REQUEST_TYPE_ACTIVE_JOIN
	 * @see CoolQCaller#REQUEST_TYPE_INVITE
	 */
	public final boolean groupAnswerJoinRequest(String requestFlag, int requestType, boolean accept) {
		return groupAnswerJoinRequest(requestFlag, requestType, accept, "");
	}

	/**
	 * 响应群添加请求
	 *
	 * @param requestFlag 请求 Id
	 * @param requestType 请求类型 ({@link AddGroupRequestEvent#TYPE_JOIN}或{@link AddGroupRequestEvent#TYPE_INVITE})
	 * @param accept      是否同意请求
	 * @param reason      拒绝原因 (同意时填入空字符串)
	 * @return 是否成功
	 * @see CoolQCaller#REQUEST_TYPE_ACTIVE_JOIN
	 * @see CoolQCaller#REQUEST_TYPE_INVITE
	 */
	public final boolean groupAnswerJoinRequest(String requestFlag, int requestType, boolean accept, String reason) {
		return CQ_setGroupAddRequestV2(getAuthCode(), requestFlag, requestType, accept ? RESULT_TYPE_ACCEPT : RESULT_TYPE_DENIED, reason) == 1;
	}

	/**
	 * 记录日志.
	 * 不建议使用本方法, 请使用 {@link JavaPlugin#getLogger()} 中的分类记录方法
	 *
	 * @param priority 优先级
	 * @param type     类型
	 * @param message  内容
	 */
	public final void log(int priority, String type, String message) {
		CQ_addLog(getAuthCode(), priority, type, message);
	}

	/**
	 * 记录致命错误
	 *
	 * @param message 错误内容
	 * @return 是否成功
	 */
	public final boolean error(String message) {
		return CQ_setFatal(getAuthCode(), message) == 1;
	}

	/**
	 * 获取群成员资料
	 *
	 * @param group   群号
	 * @param QQ      QQ
	 * @param noCache 是否不使用缓存
	 * @return 群成员资料
	 */
	public final Member groupGetMemberInfo(long group, long QQ, boolean noCache) {
		return new Member(Utils.base64decode(CQ_getGroupMemberInfoV2(getAuthCode(), group, QQ, noCache)));
	}

	/**
	 * 获取群成员资料 (使用缓存)
	 *
	 * @param group 群号
	 * @param QQ    QQ
	 * @return 群成员资料
	 */
	public final Member groupGetMemberInfo(long group, long QQ) {
		return groupGetMemberInfo(group, QQ, false);
	}

	/**
	 * 获取陌生人资料
	 *
	 * @param QQ      QQ
	 * @param noCache 是否不使用缓存
	 * @return 陌生人资料
	 */
	public final User strangerGetInfo(long QQ, boolean noCache) {
		return new User(Utils.base64decode(CQ_getStrangerInfo(getAuthCode(), QQ, noCache)));
	}

	/**
	 * 获取陌生人资料 (使用缓存)
	 *
	 * @param QQ QQ
	 * @return 陌生人资料
	 */
	public final User strangerGetInfo(long QQ) {
		return strangerGetInfo(QQ, false);
	}
}