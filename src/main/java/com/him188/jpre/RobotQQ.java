package com.him188.jpre;

import com.him188.jpre.event.send.SendGroupMessageEvent;
import com.him188.jpre.event.send.SendMessageEvent;
import com.him188.jpre.event.send.SendPrivateMessageEvent;
import com.him188.jpre.event.message.MessageEvent;
import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.NetworkPacketHandler;
import com.him188.jpre.network.packet.CommandPacket;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.scheduler.Scheduler;
import com.him188.jpre.scheduler.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.him188.jpre.CommandId.*;

/**
 * 框架上的机器人 QQ(响应 QQ)
 *
 * @author Him188
 */
public class RobotQQ {
	public final long QQ;

	public RobotQQ(long QQ) {
		this.QQ = QQ;
	}

	public long getQQ() {
		return QQ;
	}

	@Override
	public String toString() {
		return "RobotQQ(QQ=" + getQQ() + ")";
	}


	/* MPQ STATIC API */

	/**
	 * 将群名片加入高速缓存当中
	 *
	 * @param group 群号
	 * @param QQ    QQ
	 * @param card  群名片
	 */
	public static void cacheNameCard(long group, long QQ, String card) {
		runCommand(CACHE_NAME_CARD, 0L, group, QQ, card);
	}

	/**
	 * 根据图片 GUID 取得图片下载连接 失败返回空
	 *
	 * @param guid GUID
	 *
	 * @return 图片下载链接
	 */
	public static String guidGetPicLink(String guid) {
		runCommand(GUID_GET_PIC_LINK, 0L, guid);
		return stringResult();
	}

	/**
	 * 发送封包
	 *
	 * @param data 封包数据
	 *
	 * @return unknown
	 */
	public static int send(String data) {
		runCommand(SEND, 0L, data);
		return intResult();
	}

	/**
	 * 在框架记录页输出一行信息
	 *
	 * @param context 输出的内容
	 *
	 * @return unknown
	 */
	public static int output(String context) {
		runCommand(OUTPUT, 0L, context);
		return intResult();
	}

	/**
	 * 取得本插件(JPRE)启用状态
	 *
	 * @return 本插件(JPRE)启用状态
	 */
	public static boolean isEnabled() {
		runCommand(IS_ENABLE, 0L);
		return booleanResult();
	}

	/**
	 * 登录一个现存的 QQ
	 *
	 * @param QQ QQ
	 *
	 * @return 是否成功
	 */
	public static boolean login(long QQ) {
		runCommand(LOGIN, 0L, QQ);
		return booleanResult();
	}

	/**
	 * 让指定 QQ 下线
	 *
	 * @param QQ QQ
	 */
	public static void logout(long QQ) {
		runCommand(LOGOUT, 0L, QQ);
	}

	/**
	 * Tean 加密算法
	 *
	 * @param context 内容
	 * @param key     key
	 *
	 * @return 加密结果
	 */
	public static String teaEncode(String context, String key) {
		runCommand(TEA_ENCODE, 0L, context, key);
		return stringResult();
	}

	/**
	 * Tean 解密算法
	 *
	 * @param context 内容
	 * @param key     key
	 *
	 * @return 解密结果
	 */
	public static String teaDecode(String context, String key) {
		runCommand(TEA_DECODE, 0L, context, key);
		return stringResult();
	}

	/**
	 * 取用户名
	 *
	 * @param QQ QQ
	 *
	 * @return 用户名
	 */
	public static String getNick(long QQ) {
		runCommand(GET_NICK, 0L, QQ);
		return stringResult();
	}

	/**
	 * 获取 QQ 等级
	 *
	 * @param QQ QQ
	 *
	 * @return QQ 等级
	 */
	public static String getQQLevel(long QQ) {
		runCommand(GET_QQ_LEVEL, 0L, QQ);
		return stringResult();
	}

	/**
	 * 获取 GID
	 *
	 * @param groupNumber 群号
	 *
	 * @return GID
	 */
	public static String getGId(long groupNumber) {
		runCommand(GN_GET_GID, 0L, groupNumber);
		return stringResult();
	}

	/**
	 * 获取群号
	 *
	 * @param gid GID
	 *
	 * @return 群号
	 */
	public static long getGroupNumber(String gid) {
		runCommand(GID_GET_GN, 0L, gid);
		return longResult();
	}

	/**
	 * 取框架版本号(发布时间戳)
	 *
	 * @return 框架版本号(发布时间戳)
	 */
	public static int getVersion() {
		runCommand(GET_VERSION, 0L);
		return intResult();
	}

	/**
	 * 取框架版本名
	 *
	 * @return 框架版本名
	 */
	public static String getVersionName() {
		runCommand(GET_VERSION_NAME, 0L);
		return stringResult();
	}

	/**
	 * 取当前框架内部时间戳, 周期性与服务器时间同步
	 *
	 * @return 当前框架内部时间戳, 周期性与服务器时间同步
	 */
	public static int getTimeStamp() {
		runCommand(GET_TIME_STAMP, 0L);
		return intResult();
	}

	/**
	 * 取得框架输出列表内所有信息
	 * 包可能过长, 导致接受慢
	 *
	 * @return 框架输出列表内所有信息
	 */
	//LONG PACKET WARNING!!!
	public static String getLog() {
		runCommand(GET_LOG, 0L);
		return stringResult();
	}

	/**
	 * 取得框架内随机一个在线且可以使用的QQ
	 *
	 * @return 框架内随机一个在线且可以使用的QQ
	 */
	public static String getRandomOnlineQQ() {
		runCommand(GET_RANDOM_ONLINE_QQ, 0L);
		return stringResult();
	}

	/**
	 * 往帐号列表添加一个 QQ. 当该 QQ 已存在时则覆盖密码
	 *
	 * @param QQ        QQ
	 * @param password  密码
	 * @param autoLogin 运行框架时是否自动登录该Q.若添加后需要登录该 QQ 则需要通过 Api_Login 操作
	 *
	 * @return 是否成功
	 */
	public static boolean addQQ(long QQ, long password, boolean autoLogin) {
		runCommand(ADD_QQ, 0L, QQ, password, autoLogin);
		return booleanResult();
	}

	/**
	 * 设置在线状态和附加信息
	 *
	 * @param QQ              QQ
	 * @param status          状态. STATUS_ 开头常量
	 * @param additionMessage 附加消息. 最大 255 字节
	 *
	 * @return 是否成功
	 */
	public static boolean setOnlineStatus(long QQ, int status, String additionMessage) {
		runCommand(SET_OL_STATUS, 0L, QQ, status, additionMessage);
		return booleanResult();
	}

	/**
	 * 取得机器码
	 *
	 * @return 机器码
	 */
	public static String getMachineCode() {
		runCommand(GET_MC, 0L);
		return stringResult();
	}

	/**
	 * 取得框架所在目录
	 *
	 * @return 框架所在目录
	 */
	public static String getRunPath() {
		runCommand(GET_RUN_PATH, 0L);
		return stringResult();
	}

	/**
	 * 取得当前框架内在线可用的QQ列表
	 *
	 * @return 当前框架内在线可用的QQ列表
	 */
	public static String getOnlineQQList() {
		runCommand(GET_ONLINE_QQ_LIST, 0L);
		return stringResult();
	}

	/**
	 * 取得框架内所有QQ列表。包括未登录以及登录失败的QQ
	 *
	 * @return 框架内所有QQ列表
	 */
	public static String getQQList() {
		runCommand(GET_QQ_LIST, 0L);
		return stringResult();
	}


	/* MPQ API*/


	/**
	 * 计算得到页面操作用参数 Bkn 或 G_tk
	 *
	 * @return 页面操作用参数 Bkn 或 G_tk
	 */
	public String getGtkBkn() {
		runCommand(ADD_QQ, this.getQQ());
		return stringResult();
	}

	/**
	 * 计算得到页面操作用参数长 Bkn 或长 G_tk
	 *
	 * @return 页面操作用参数长 Bkn 或长 G_tk
	 */
	public String getBKN32() {
		runCommand(GET_BKN32, this.getQQ());
		return stringResult();
	}

	/**
	 * 计算得到页面操作用参数长 Ldw
	 *
	 * @return 页面操作用参数长 Ldw
	 */
	public String getLdw() {
		runCommand(GET_LDW, this.getQQ());
		return stringResult();
	}

	/**
	 * 取得对应的会话秘钥
	 *
	 * @return 取得对应的会话秘钥
	 */
	public String getSessionKey() {
		runCommand(GET_SESSION_KEY, this.getQQ());
		return stringResult();
	}

	/**
	 * 取得页面登录用的 ClientKey
	 *
	 * @return 页面登录用的 ClientKey
	 */
	public String getClientKey() {
		runCommand(GET_CLIENT_KEY, this.getQQ());
		return stringResult();
	}

	/**
	 * 取得页面登录用的长 ClientKey
	 *
	 * @return 页面登录用的长 ClientKey
	 */
	public String getLongClientKey() {
		runCommand(GET_LONG_CLIENT_KEY, this.getQQ());
		return stringResult();
	}

	/**
	 * 取得页面操作用的 Cookies
	 *
	 * @return 页面操作用的 Cookies
	 */
	public String getCookies() {
		runCommand(GET_COOKIES, this.getQQ());
		return stringResult();
	}

	/**
	 * 取得框架内设置的信息发送前缀
	 *
	 * @return 框架内设置的信息发送前缀
	 */
	public String getPrefix() {
		runCommand(GET_PREFIX, this.getQQ());
		return stringResult();
	}

	/**
	 * 将指定 QQ 移出 QQ 黑名单
	 *
	 * @param QQ QQ
	 */
	public void unBan(long QQ) {
		runCommand(D_BAN, this.getQQ(), QQ);
	}

	/**
	 * 将指定 QQ 列入 QQ 黑名单
	 *
	 * @param QQ QQ
	 */
	public void ban(long QQ) {
		runCommand(BAN, this.getQQ(), QQ);
	}

	/**
	 * 禁言群成员
	 *
	 * @param group 群号
	 * @param QQ    QQ
	 * @param time  时间, 单位秒, 最大一个月. 为零时解除禁言
	 *
	 * @return 是否成功
	 */
	public boolean shutUp(long group, long QQ, int time) {
		runCommand(SHUT_UP, this.getQQ(), group, QQ, time);
		return booleanResult();
	}

	/**
	 * 设置/取消全群禁言
	 *
	 * @param group 群号
	 *
	 * @return 是否成功
	 */
	public boolean shutUpWhole(long group) {
		return shutUp(group, 0, 0);
	}

	/**
	 * 发群公告
	 *
	 * @param group   群号
	 * @param title   公告标题
	 * @param content 公告内容
	 */
	public void setNotice(long group, String title, String content) {
		runCommand(SET_NOTICE, this.getQQ(), group, title, content);
	}

	/**
	 * 取群公告
	 *
	 * @param group 群号
	 *
	 * @return 群公告
	 */
	public String getNotice(long group) {
		runCommand(GET_NOTICE, this.getQQ(), group);
		return stringResult();
	}

	/**
	 * 取群名片
	 *
	 * @param group 群号
	 * @param QQ    QQ
	 *
	 * @return 群名片
	 */
	public String getNameCard(long group, long QQ) {
		runCommand(GET_NAME_CARD, this.getQQ(), group, QQ);
		return stringResult();
	}

	/**
	 * 退出讨论组
	 *
	 * @param discuss 讨论组号
	 */
	public void quitDiscussGroup(long discuss) {
		runCommand(QUIT_DG, this.getQQ(), discuss);
	}

	/**
	 * 删除好友
	 *
	 * @param QQ QQ
	 */
	public void deleteFriend(long QQ) {
		runCommand(DEL_FRIEND, this.getQQ(), QQ);
	}

	/**
	 * 移出群
	 *
	 * @param group 群号
	 * @param QQ    QQ
	 *
	 * @return 是否成功
	 */
	public boolean kick(long group, long QQ) {
		runCommand(KICK, this.getQQ(), group, QQ);
		return booleanResult();
	}

	/**
	 * 主动加群.为了避免广告、群发行为。出现验证码时将会直接失败不处理
	 *
	 * @param group  群号
	 * @param reason 理由
	 */
	public void joinGroup(long group, String reason) {
		runCommand(JOIN_GROUP, this.getQQ(), group, reason);
	}

	/**
	 * 退出群
	 *
	 * @param group 群号
	 */
	public void quitGroup(long group) {
		runCommand(QUIT_GROUP, this.getQQ(), group);
	}

	/**
	 * 上传图片
	 *
	 * @param file 图片文件绝对路径
	 *
	 * @return GUID | null
	 */
	public String uploadImage(String file) {
		runCommand(UPLOAD, this.getQQ(), file, 0);
		return stringResult();
	}

	/**
	 * 上传图片
	 *
	 * @param image 图片字节集数据 (int???)
	 *
	 * @return GUID | null
	 */
	public String uploadImage(int image) {
		runCommand(UPLOAD, this.getQQ(), "", image);
		return stringResult();
	}

	public static final int TYPE_FRIEND = 1;
	public static final int TYPE_PRIVATE = 1;
	public static final int TYPE_GROUP = 2;
	public static final int TYPE_DISCUSS = 3;
	public static final int TYPE_GROUP_TEMPORARY_SESSION = 4;
	public static final int TYPE_DISCUSS_TEMPORARY_SESSION = 5;

	/**
	 * 回复信息 请尽量避免使用该 API
	 * 不会触发事件
	 *
	 * @param type    类型. TYPE_ 开头常量
	 * @param target  发送目标
	 * @param context 消息内容
	 *
	 * @return unknown
	 */
	public int reply(int type, long target, String context) {
		runCommand(REPLY, this.getQQ(), type, target, context);
		return intResult();
	}

	/**
	 * 发送消息. 将会触发事件 ({@link MessageEvent})
	 * 注:好友图片发送暂不支持(2015年5月23日
	 *
	 * @param type    类型. TYPE_ 开头常量
	 * @param subType 子类型, 无特殊说明情况下为 0
	 * @param group   群号/讨论组号/临时会话号. 发送好友消息时为 0
	 * @param QQ      QQ. 发送非好友消息时为 0
	 * @param context 消息内容
	 *
	 * @return unknown
	 */
	public int sendMessage(int type, int subType, long group, long QQ, String context) {
		SendMessageEvent event;
		switch (type) {
			case TYPE_FRIEND:
				event = new SendPrivateMessageEvent(this, QQ, context);

				JPREMain.callEvent(event);
				if (event.isCancelled()) {
					return -2;
				}
				runCommand(SEND_MSG, this.getQQ(), type, subType, 0, ((SendPrivateMessageEvent) event).getQQ(), event.getMessage());
				return intResult();
			case TYPE_DISCUSS_TEMPORARY_SESSION:
			case TYPE_GROUP_TEMPORARY_SESSION:
				event = new SendPrivateMessageEvent(this, group, context);

				JPREMain.callEvent(event);
				if (event.isCancelled()) {
					return -2;
				}
				runCommand(SEND_MSG, this.getQQ(), type, subType, ((SendPrivateMessageEvent) event).getQQ(), 0, event.getMessage());
				return intResult();
			case TYPE_GROUP:
			case TYPE_DISCUSS:
				event = new SendGroupMessageEvent(this, group, context);

				JPREMain.callEvent(event);
				if (event.isCancelled()) {
					return -2;
				}
				runCommand(SEND_MSG, this.getQQ(), type, subType, ((SendGroupMessageEvent) event).getGroup(), 0, event.getMessage());
				return intResult();
			default:
				return -1;
		}

	}

	public int sendPrivateMessage(long QQ, String context) {
		return sendMessage(TYPE_FRIEND, 0, 0, QQ, context);
	}

	public int sendGroupMessage(long group, String context) {
		return sendMessage(TYPE_GROUP, 0, group, 0, context);
	}

	public int sendDiscussMessage(long discuss, String context) {
		return sendGroupMessage(discuss, context);
	}

	public int sendGroupTemporaryMessage(long session, String context) {
		return sendGroupMessage(session, context);
	}

	public int sendDiscussTemporaryMessage(long session, String context) {
		return sendGroupMessage(session, context);
	}


	/**
	 * 判断是否处于被屏蔽群信息状态
	 *
	 * @return 是否处于被屏蔽群信息状态
	 */
	public boolean isBlocked() { // TODO: 2017/4/8  check if it is this.getQQ()
		runCommand(IF_BLOCK, this.getQQ());
		return booleanResult();
	}

	/**
	 * 取包括群主在内的群管列表
	 *
	 * @param group 群号
	 *
	 * @return 群管列表
	 */
	public String getAdminList(long group) {
		runCommand(GET_ADMIN_LIST, this.getQQ(), group);
		return stringResult();
	}

	/**
	 * 发说说
	 *
	 * @param context 内容
	 *
	 * @return unknown
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public String addTaotao(String context) {
		runCommand(ADD_TAOTAO, this.getQQ(), context);
		return stringResult();
	}

	/**
	 * 取个性签名
	 *
	 * @return 个性签名
	 */
	public String getSign() {
		runCommand(GET_SIGN, this.getQQ());
		return stringResult();
	}

	/**
	 * 设置个性签名
	 *
	 * @param context 内容
	 *
	 * @return unknown
	 */
	public String setSign(String context) {
		runCommand(SET_SIGN, this.getQQ(), context);
		return stringResult();
	}

	/**
	 * 通过 qun.qzone.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
	 *
	 * @return 转码后的 JSON 格式文本信息
	 */
	// TODO: 2017/4/9  解析 json
	public String getGroupListA() {
		runCommand(GET_GROUP_LIST_A, this.getQQ());
		return stringResult();
	}

	/**
	 * 通过 qun.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
	 *
	 * @return 转码后的 JSON 格式文本信息
	 */
	public String getGroupListB() {
		runCommand(GET_GROUP_LIST_B, this.getQQ());
		return stringResult();
	}

	/**
	 * 通过 qun.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
	 *
	 * @return 转码后的 JSON 格式文本
	 */
	// TODO: 2017/4/9  解析 json
	public String getGroupMemberA() {
		runCommand(GET_GROUP_MEMBER_A, this.getQQ());
		return stringResult();
	}

	/**
	 * 通过 qun.qzone.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
	 *
	 * @return 转码后的 JSON 格式文本
	 */
	public String getGroupMemberB() {
		runCommand(GET_GROUP_MEMBER_B, this.getQQ());
		return stringResult();
	}

	/**
	 * 通过 qun.qq.com 接口取得好友列表。成功返回转码后的 JSON 文本
	 *
	 * @return 转码后的 JSON 文本
	 */
	public String getFriendList() {
		runCommand(GET_FRIEND_LIST, this.getQQ());
		return stringResult();
	}

	/**
	 * 取 Q 龄 成功返回 Q 龄 失败返回 -1
	 *
	 * @param QQ QQ
	 *
	 * @return 成功返回 Q 龄 失败返回 -1
	 */
	public int getQQAge(long QQ) {
		runCommand(GET_QQ_AGE, this.getQQ(), QQ);
		return intResult();
	}

	/**
	 * 取年龄 成功返回年龄 失败返回 -1
	 *
	 * @param QQ QQ
	 *
	 * @return 成功返回年龄 失败返回 -1
	 */
	public int getAge(long QQ) {
		runCommand(GET_AGE, this.getQQ(), QQ);
		return intResult();
	}

	/**
	 * 取邮箱 成功返回邮箱 失败返回空
	 *
	 * @param QQ Qq
	 *
	 * @return 成功返回邮箱 失败返回空
	 */
	public String getEmail(long QQ) {
		runCommand(GET_EMAIL, this.getQQ(), QQ);
		return stringResult();
	}

	/**
	 * 取对象性别  1女 2男 失败返回 -1
	 *
	 * @param QQ QQ
	 *
	 * @return 1女 2男 失败返回 -1
	 */
	public int getGender(long QQ) {
		runCommand(GET_GENDER, this.getQQ(), QQ);
		return intResult();
	}

	/**
	 * 向好友发送 "正在输入" 的状态信息. 当好友收到信息之后 "正在输入" 状态会立刻被打断
	 *
	 * @param QQ QQ
	 *
	 * @return unknown
	 */
	public int sendTyping(long QQ) {
		runCommand(SEND_TYPING, this.getQQ(), QQ);
		return intResult();
	}

	/**
	 * 向好友发送窗口抖动信息
	 *
	 * @param QQ QQ
	 *
	 * @return unknown
	 */
	public int sendShake(long QQ) {
		runCommand(SEND_SHAKE, this.getQQ(), QQ);
		return intResult();
	}

	/**
	 * 呵呵呵呵呵呵呵呵呵呵呵呵呵
	 *
	 * @param target 群号或 QQ 号
	 */
	public int crackIOSQQ(long target) {
		runCommand(CRACK_IOS_QQ, this.getQQ(), target);
		return intResult();
	}

	public static final int STATUS_ONLINE = 1;          //我在线上
	public static final int STATUS_Q_ME = 2;            //Q 我吧
	public static final int STATUS_LEAVE = 3;           //离开
	public static final int STATUS_WORKING = 4;         //忙碌
	public static final int STATUS_DO_NOT_DISTURB = 5;  //请勿打扰
	public static final int STATUS_HIDE = 6;            //隐身

	/**
	 * 邀请对象加入群 失败返回错误理由
	 *
	 * @param group  群号
	 * @param QQList QQ 列表. 换行分割
	 *
	 * @return 成功返回空 失败返回错误理由
	 */
	public String groupInvitation(long group, String QQList) {
		runCommand(GROUP_INVITATION, this.getQQ(), QQList, group);
		return stringResult();
	}

	/**
	 * 邀请对象加入群 失败返回错误理由
	 *
	 * @param group 群号
	 * @param QQ    QQ 列表
	 *
	 * @return 成功返回空 失败返回错误理由
	 */
	public String groupInvitation(long group, long... QQ) {
		StringBuilder arg = new StringBuilder();
		for (long l : QQ) {
			arg.append(l).append("\n");
		}
		return groupInvitation(group, arg.toString());
	}

	/**
	 * 创建一个讨论组 成功返回讨论组 ID 失败返回 0 注: 每 24 小时只能创建 100 个讨论组 悠着点用
	 *
	 * @return 讨论组 ID. 失败为 0
	 */
	public long createDiscussGroup() {
		runCommand(CREATE_DG, this.getQQ());
		return longResult();
	}

	/**
	 * 将对象移除讨论组. 成功返回空 失败返回理由
	 *
	 * @param discuss 讨论组 ID
	 * @param QQ      QQ
	 *
	 * @return 成功返回空 失败返回理由
	 */
	public String kickFromDiscussGroup(long discuss, long QQ) {
		runCommand(KICK_DG, this.getQQ(), discuss, QQ);
		return stringResult();
	}

	/**
	 * 邀请对象加入讨论组 成功返回空 失败返回理由
	 *
	 * @param discuss 讨论组 ID
	 * @param QQList  QQ 列表, 换行分割
	 *
	 * @return 成功返回空 失败返回理由
	 */
	public String discussGroupInvitation(long discuss, String QQList) {
		runCommand(DG_INVITATION, this.getQQ(), discuss, QQList);
		return stringResult();
	}

	/**
	 * 邀请对象加入讨论组 成功返回空 失败返回理由
	 *
	 * @param discuss 讨论组 ID
	 * @param QQ      QQ 列表
	 *
	 * @return 成功返回空 失败返回理由
	 */
	public String discussGroupInvitation(long discuss, long... QQ) {
		StringBuilder arg = new StringBuilder();
		for (long l : QQ) {
			arg.append(l).append("\n");
		}
		return discussGroupInvitation(discuss, arg.toString());
	}

	/**
	 * 成功返回以换行符分割的讨论组号列表. 最大为 100 个讨论组  失败返回空
	 *
	 * @return 讨论组列表
	 */
	public String getDiscussGroupList() {
		runCommand(GET_DG_LIST, this.getQQ());
		return stringResult();
	}

	/**
	 * 上传音频文件 成功返回 guid 用于发送
	 *
	 * @param amrData 音频文件数据. (int???)
	 *
	 * @return 成功返回 guid 用于发送
	 */
	public String uploadVoice(int amrData) {
		runCommand(UPLOAD_VOICE, this.getQQ(), amrData);
		return stringResult();
	}

	/**
	 * 通过音频、语音 guid 取得下载连接
	 *
	 * @param guid GUID
	 *
	 * @return 下载连接
	 */
	public String guidGetVoiceLink(String guid) {
		runCommand(GUID_GET_VOICE_LINK, guid);
		return stringResult();
	}

	/**
	 * 发送名片赞 10赞每 Q 每日 至多 50 人\日系列 成功返回空 失败返回理由 (腾讯爸爸给出的) 一次一赞 速度请自行管控以免冻结
	 *
	 * @param QQ QQ
	 *
	 * @return 成功返回空 失败返回理由 (腾讯爸爸给出的)
	 */
	public String like(long QQ) {
		runCommand(LIKE, this.getQQ(), QQ);
		return stringResult();
	}

	/**
	 * 发送名片赞. 10赞每 Q 每日 至多 50 人\日系列 成功返回空 失败返回理由 (腾讯爸爸给出的)
	 * 本方法将启用一个 Task 来进行赞延迟操作. 即不会阻塞线程
	 *
	 * @param QQ     被赞 QQ 号
	 * @param times  次数, 范围 1~10
	 * @param millis 赞间隔时间, 单位毫秒
	 * @param plugin 用于接受 Task
	 */
	public void like(long QQ, int times, long millis, Plugin plugin) {
		Scheduler.scheduleTask(plugin, () -> {
			for (int i = 0; i < 10; i++) {
				Thread.currentThread();
				try {
					Thread.sleep(millis);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				like(QQ);
			}
		});
	}


	// TODO: 2017/4/8  

	/* STATIC(COMMAND SENDER) */

	// TODO: 2017/3/28 带上id, 避免顺序错误
	private static ConcurrentLinkedQueue<Object> results = new ConcurrentLinkedQueue<>();

	private static int parseInt(String value) {
		if (value.isEmpty()) {
			return 0;
		}
		if (value.equalsIgnoreCase("false")) {
			return 0;
		}
		if (value.equalsIgnoreCase("true")) {
			return 1;
		}
		return Integer.parseInt(value);
	}

	private static long parseLong(String value) {
		if (value.isEmpty()) {
			return 0;
		}
		if (value.equalsIgnoreCase("false")) {
			return 0;
		}
		if (value.equalsIgnoreCase("true")) {
			return 1;
		}
		return Long.parseLong(value);
	}

	private static boolean booleanResult() {
		return intResult() == 1;
	}

	private static int intResult() {
		return 1;
		// TODO: 2017/4/8
		//return parseInt(stringResult());
	}

	private static long longResult() {
		return parseLong(stringResult());
	}


	@SuppressWarnings("StatementWithEmptyBody")
	private static String stringResult() {
		// TODO: 2017/3/28 result修改为带id的map后优化此方法. 现在这个方法性能低且易出错.

		Task task = Scheduler.scheduleTimingTask(null,
				() ->
						results.add("")
				, 500);//0.5s
		//synchronized (MPQCaller.class) {//使正在等待返回值时, 指令不传达
		while (results.isEmpty()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		task.forceCancel();
		return String.valueOf(results.remove(0));
		//}
	}

	private static void runCommand(CommandId id, Object... args) {
		//synchronized (MPQCaller.class) {
		for (ConnectedClient connectedClient : NetworkPacketHandler.getClients()) {
			connectedClient.sendPacket(new CommandPacket(id, args));
		}
		//}
	}

	public static void addResult(Object result) {
		results.add(result);
	}


	/* ROBOT QQ LIST*/

	private static final Set<RobotQQ> list = new HashSet<>();

	public static Set<RobotQQ> getList() {
		return list;
	}

	/**
	 * 获取 Robot 实例, 不存在时自动创建
	 *
	 * @return Robot
	 */
	public static RobotQQ getRobot(long QQ) {
		for (RobotQQ robotQQ : list) {
			if (robotQQ.getQQ() == QQ) {
				return robotQQ;
			}
		}

		RobotQQ qq = new RobotQQ(QQ);
		list.add(qq);
		return qq;
	}
}