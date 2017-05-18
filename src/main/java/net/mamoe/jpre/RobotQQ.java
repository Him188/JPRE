package net.mamoe.jpre;

import net.mamoe.jpre.event.send.SendDiscussionMessageEvent;
import net.mamoe.jpre.event.send.SendGroupMessageEvent;
import net.mamoe.jpre.event.send.SendPrivateMessageEvent;
import net.mamoe.jpre.network.MPQClient;
import net.mamoe.jpre.network.NetworkPacketHandler;
import net.mamoe.jpre.network.packet.CommandPacket;
import net.mamoe.jpre.network.packet.StaticCommandPacket;
import net.mamoe.jpre.plugin.Plugin;
import net.mamoe.jpre.scheduler.Task;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 框架上的机器人 QQ(响应 QQ)
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class RobotQQ {
    private final Frame frame;
    public final long QQ;

    public Frame getFrame() {
        return frame;
    }

    public RobotQQ(Frame frame, long QQ) {
        Objects.requireNonNull(frame);

        this.frame = frame;
        this.QQ = QQ;
    }

    /**
     * 获取该机器人的 QQ
     *
     * @return 机器人的 QQ
     */
    public long getQQNumber() {
        return QQ;
    }

    @Override
    public String toString() {
        return "RobotQQ(QQ=" + getQQNumber() + ",Frame=" + frame.toString() + ")";
    }


	/* QQ LIST */

    private final Set<QQ> qqList = new HashSet<>();

    public Set<QQ> getQQList() {
        return qqList;
    }

    /**
     * 获取 QQ 实例, 不存在时自动创建
     *
     * @return QQ
     */
    public QQ getQQ(long QQ) {
        for (QQ robotQQ : qqList) {
            if (robotQQ.getNumber() == QQ) {
                return robotQQ;
            }
        }

        QQ qq = new QQ(this, QQ);
        qqList.add(qq);
        return qq;
    }

	/* Group LIST */

    private final Set<Group> groupList = new HashSet<>();

    public Set<Group> getGroupList() {
        return groupList;
    }

    /**
     * 获取 Group 实例, 不存在时自动创建
     *
     * @return Group
     */
    public Group getGroup(long Group) {
        for (Group robotGroup : groupList) {
            if (robotGroup.getNumber() == Group) {
                return robotGroup;
            }
        }

        Group group = new Group(this, Group);
        groupList.add(group);
        return group;
    }

	/* Discussion LIST */

    private final Set<Discussion> discussionList = new HashSet<>();

    public Set<Discussion> getDiscussionList() {
        return discussionList;
    }

    /**
     * 获取 Discussion 实例, 不存在时自动创建
     *
     * @return Discussion
     */
    public Discussion getDiscussion(long Discussion) {
        for (Discussion robotDiscussion : discussionList) {
            if (robotDiscussion.getNumber() == Discussion) {
                return robotDiscussion;
            }
        }

        Discussion discussion = new Discussion(this, Discussion);
        discussionList.add(discussion);
        return discussion;
    }


	/* MPQ STATIC API */

    // TODO: 2017/5/17  package-private api,

    /**
     * 将群名片加入高速缓存当中
     *
     * @param group 群号
     * @param QQ    QQ
     * @param card  群名片
     */
    public static void cacheNameCard(long group, long QQ, String card) {
        staticRunCommand(CommandId.CACHE_NAME_CARD, 0L, group, QQ, card);
    }

    /**
     * 根据图片 GUID 取得图片下载连接 失败返回空
     *
     * @param guid GUID
     * @return 图片下载链接
     */
    public static String guidGetPicLink(String guid) {
        staticRunCommand(CommandId.GUID_GET_PIC_LINK, 0L, guid);
        return staticStringResult();
    }

    /**
     * 发送封包
     *
     * @param data 封包数据
     * @return unknown
     */
    public static int send(String data) {
        staticRunCommand(CommandId.SEND, 0L, data);
        return staticIntResult();
    }

    /**
     * 在框架记录页输出一行信息
     *
     * @param content 输出的内容
     * @return unknown
     */
    public static int output(String content) {
        staticRunCommand(CommandId.OUTPUT, 0L, content);
        return staticIntResult();
    }

    /**
     * 取得本插件(JPRE)启用状态
     *
     * @return 本插件(JPRE)启用状态
     */
    public static boolean isEnabled() {
        staticRunCommand(CommandId.IS_ENABLE, 0L);
        return staticBooleanResult();
    }

    /**
     * 登录一个现存的 QQ
     *
     * @param QQ QQ
     * @return 是否成功
     */
    public static boolean login(long QQ) {
        staticRunCommand(CommandId.LOGIN, 0L, QQ);
        return staticBooleanResult();
    }

    /**
     * 让指定 QQ 下线
     *
     * @param QQ QQ
     */
    public static void logout(long QQ) {
        staticRunCommand(CommandId.LOGOUT, 0L, QQ);
    }

    /**
     * Tean 加密算法
     *
     * @param content 内容
     * @param key     key
     * @return 加密结果
     */
    public static String teaEncode(String content, String key) {
        staticRunCommand(CommandId.TEA_ENCODE, 0L, content, key);
        return staticStringResult();
    }

    /**
     * Tean 解密算法
     *
     * @param content 内容
     * @param key     key
     * @return 解密结果
     */
    public static String teaDecode(String content, String key) {
        staticRunCommand(CommandId.TEA_DECODE, 0L, content, key);
        return staticStringResult();
    }

    /**
     * 取用户名
     *
     * @param QQ QQ
     * @return 用户名
     */
    public static String getNick(long QQ) {
        staticRunCommand(CommandId.GET_NICK, 0L, QQ);
        return staticStringResult();
    }

    /**
     * 获取 QQ 等级
     *
     * @param QQ QQ
     * @return QQ 等级
     */
    public static String getQQLevel(long QQ) {
        staticRunCommand(CommandId.GET_QQ_LEVEL, 0L, QQ);
        return staticStringResult();
    }

    /**
     * 获取 GID
     *
     * @param groupNumber 群号
     * @return GID
     */
    public static String getGId(long groupNumber) {
        staticRunCommand(CommandId.GN_GET_GID, 0L, groupNumber);
        return staticStringResult();
    }

    /**
     * 获取群号
     *
     * @param gid GID
     * @return 群号
     */
    public static long getGroupNumber(String gid) {
        staticRunCommand(CommandId.GID_GET_GN, 0L, gid);
        return staticLongResult();
    }

    /**
     * 取框架版本号(发布时间戳)
     *
     * @return 框架版本号(发布时间戳)
     */
    public static int getVersion() {
        staticRunCommand(CommandId.GET_VERSION, 0L);
        return staticIntResult();
    }

    /**
     * 取框架版本名
     *
     * @return 框架版本名
     */
    public static String getVersionName() {
        staticRunCommand(CommandId.GET_VERSION_NAME, 0L);
        return staticStringResult();
    }

    /**
     * 取当前框架内部时间戳, 周期性与服务器时间同步
     *
     * @return 当前框架内部时间戳, 周期性与服务器时间同步
     */
    public static int getTimeStamp() {
        staticRunCommand(CommandId.GET_TIME_STAMP, 0L);
        return staticIntResult();
    }

    /**
     * 取得框架输出列表内所有信息
     * 包可能过长, 导致接受慢
     *
     * @return 框架输出列表内所有信息
     */
    //LONG PACKET WARNING!!!
    public static String getLog() {
        staticRunCommand(CommandId.GET_LOG, 0L);
        return staticStringResult();
    }

    /**
     * 取得框架内随机一个在线且可以使用的QQ
     *
     * @return 框架内随机一个在线且可以使用的QQ
     */
    public static String getRandomOnlineQQ() {
        staticRunCommand(CommandId.GET_RANDOM_ONLINE_QQ, 0L);
        return staticStringResult();
    }

    /**
     * 往帐号列表添加一个 QQ. 当该 QQ 已存在时则覆盖密码
     *
     * @param QQ        QQ
     * @param password  密码
     * @param autoLogin 运行框架时是否自动登录该Q.若添加后需要登录该 QQ 则需要通过 Api_Login 操作
     * @return 是否成功
     */
    public static boolean addQQ(long QQ, String password, boolean autoLogin) {
        staticRunCommand(CommandId.ADD_QQ, 0L, QQ, password, autoLogin);
        return staticBooleanResult();
    }

    /**
     * 设置在线状态和附加信息
     *
     * @param QQ              QQ
     * @param status          状态.
     * @param additionMessage 附加消息. 最大 255 字节
     * @return 是否成功
     */
    public static boolean setOnlineStatus(long QQ, OnlineStatus status, String additionMessage) {
        staticRunCommand(CommandId.SET_OL_STATUS, 0L, QQ, status.getId(), additionMessage);
        return staticBooleanResult();
    }

    /**
     * 取得机器码
     *
     * @return 机器码
     */
    public static String getMachineCode() {
        staticRunCommand(CommandId.GET_MC, 0L);
        return staticStringResult();
    }

    /**
     * 取得框架所在目录
     *
     * @return 框架所在目录
     */
    public static String getRunPath() {
        staticRunCommand(CommandId.GET_RUN_PATH, 0L);
        return staticStringResult();
    }

    /**
     * 取得当前框架内在线可用的QQ列表
     *
     * @return 当前框架内在线可用的QQ列表
     */
    public static String getOnlineQQList() {
        staticRunCommand(CommandId.GET_ONLINE_QQ_LIST, 0L);
        return staticStringResult();
    }

    /**
     * 取得框架内所有QQ列表。包括未登录以及登录失败的QQ
     *
     * @return 框架内所有QQ列表
     */
    public static String getFrameQQList() {
        staticRunCommand(CommandId.GET_QQ_LIST, 0L);
        return staticStringResult();
    }

	/* MPQ API*/


    /**
     * 计算得到页面操作用参数 Bkn 或 G_tk
     *
     * @return 页面操作用参数 Bkn 或 G_tk
     */
    public String getGtkBkn() {
        runCommand(CommandId.GET_GTK_BKN, this.getQQNumber());
        return stringResult();
    }

    /**
     * 计算得到页面操作用参数长 Bkn 或长 G_tk
     *
     * @return 页面操作用参数长 Bkn 或长 G_tk
     */
    public String getBKN32() {
        runCommand(CommandId.GET_BKN32, this.getQQNumber());
        return stringResult();
    }

    /**
     * 计算得到页面操作用参数长 Ldw
     *
     * @return 页面操作用参数长 Ldw
     */
    public String getLdw() {
        runCommand(CommandId.GET_LDW, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取得对应的会话秘钥
     *
     * @return 取得对应的会话秘钥
     */
    public String getSessionKey() {
        runCommand(CommandId.GET_SESSION_KEY, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取得页面登录用的 ClientKey
     *
     * @return 页面登录用的 ClientKey
     */
    public String getClientKey() {
        runCommand(CommandId.GET_CLIENT_KEY, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取得页面登录用的长 ClientKey
     *
     * @return 页面登录用的长 ClientKey
     */
    public String getLongClientKey() {
        runCommand(CommandId.GET_LONG_CLIENT_KEY, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取得页面操作用的 Cookies
     *
     * @return 页面操作用的 Cookies
     */
    public String getCookies() {
        runCommand(CommandId.GET_COOKIES, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取得框架内设置的信息发送前缀
     *
     * @return 框架内设置的信息发送前缀
     */
    public String getPrefix() {
        runCommand(CommandId.GET_PREFIX, this.getQQNumber());
        return stringResult();
    }

    /**
     * 将指定 QQ 移出 QQ 黑名单
     *
     * @param QQ QQ
     */
    public void unBan(long QQ) {
        runCommand(CommandId.D_BAN, this.getQQNumber(), QQ);
    }

    /**
     * 将指定 QQ 列入 QQ 黑名单
     *
     * @param QQ QQ
     */
    public void ban(long QQ) {
        runCommand(CommandId.BAN, this.getQQNumber(), QQ);
    }

    /**
     * 禁言群成员
     *
     * @param group 群号
     * @param QQ    QQ
     * @param time  时间, 单位秒, 最大一个月. 为零时解除禁言
     * @return 是否成功
     */
    public boolean shutUp(long group, long QQ, int time) {
        runCommand(CommandId.SHUT_UP, this.getQQNumber(), group, QQ, time);
        return booleanResult();
    }

    /**
     * 设置/取消全群禁言
     *
     * @param group 群号
     * @return 是否成功
     */
    public boolean shutUpWhole(long group) { // TODO: 2017/5/12 it doesn't work. fix (unknown course)
        runCommand(CommandId.SHUT_UP, this.getQQNumber(), group, "", 0);
        return booleanResult();
    }

    /**
     * 发群公告
     *
     * @param group   群号
     * @param title   公告标题
     * @param content 公告内容
     */
    public boolean setNotice(long group, String title, String content) {
        runCommand(CommandId.SET_NOTICE, this.getQQNumber(), group, title, content);
        return booleanResult();
    }

    /**
     * 取群公告
     *
     * @param group 群号
     * @return 群公告
     */
    public String getNotice(long group) {
        runCommand(CommandId.GET_NOTICE, this.getQQNumber(), group);
        return stringResult();
    }

    /**
     * 取群名片
     *
     * @param group 群号
     * @param QQ    QQ
     * @return 群名片
     */
    public String getNameCard(long group, long QQ) {
        runCommand(CommandId.GET_NAME_CARD, this.getQQNumber(), group, QQ);
        return stringResult();
    }

    /**
     * 设置群名片
     *
     * @param group 群号
     * @param QQ    QQ
     * @param card  群名片
     */
    public boolean setNameCard(long group, long QQ, String card) {
        runCommand(CommandId.SET_NAME_CARD, this.getQQNumber(), group, QQ, card);
        return booleanResult();
    }

    /**
     * 退出讨论组
     *
     * @param discuss 讨论组号
     */
    public void quitDiscussGroup(long discuss) {
        runCommand(CommandId.QUIT_DG, this.getQQNumber(), discuss);
    }

    /**
     * 删除好友
     *
     * @param QQ QQ
     */
    public void deleteFriend(long QQ) {
        runCommand(CommandId.DEL_FRIEND, this.getQQNumber(), QQ);
    }

    /**
     * 移出群
     *
     * @param group 群号
     * @param QQ    QQ
     * @return 是否成功
     */
    public boolean kick(long group, long QQ) {
        runCommand(CommandId.KICK, this.getQQNumber(), group, QQ);
        return booleanResult();
    }

    /**
     * 主动加群.为了避免广告、群发行为。出现验证码时将会直接失败不处理
     *
     * @param group  群号
     * @param reason 理由
     */
    public void joinGroup(long group, String reason) {
        runCommand(CommandId.JOIN_GROUP, this.getQQNumber(), group, reason);
    }

    /**
     * 退出群
     *
     * @param group 群号
     */
    public void quitGroup(long group) {
        runCommand(CommandId.QUIT_GROUP, this.getQQNumber(), group);
    }

    // TODO: 2017/4/22 上传图片修复 p.SetUtf8LenStr (Api_UploadPic (局_响应QQ, u.GetInt (), 到文本 (u.GetLong ()), u.GetByte ()))

    /**
     * 上传图片
     *
     * @param file 图片文件绝对路径
     * @return GUID | null
     */
    public String uploadImage(String file) {
        runCommand(CommandId.UPLOAD_PIC, this.getQQNumber(), file, 0);
        return stringResult();
    }

    /**
     * 上传图片
     *
     * @param image 图片字节集数据
     * @return GUID | null
     */
    public String uploadImage(byte[] image) {
        runCommand(CommandId.UPLOAD_PIC, this.getQQNumber(), "", image);
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
     * @param content 消息内容
     * @return unknown
     */
    public int reply(int type, long target, String content) {
        runCommand(CommandId.REPLY, this.getQQNumber(), type, target, content);
        return intResult();
    }

    public boolean sendPrivateMessage(long QQ, String content) {
        return sendPrivateMessage(getQQ(QQ), content);
    }

    public boolean sendPrivateMessage(QQ QQ, String content) {
        SendPrivateMessageEvent event = new SendPrivateMessageEvent(this, QQ, content);
        getFrame().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        runCommand(CommandId.SEND_MSG, this.getQQNumber(), TYPE_FRIEND, 0, 0L, QQ.getNumber(), event.getMessage());
        return booleanResult();
    }

    public boolean sendGroupMessage(long group, String content) {
        return sendGroupMessage(getGroup(group), content);
    }

    public boolean sendGroupMessage(Group group, String content) {
        SendGroupMessageEvent event = new SendGroupMessageEvent(this, group, content);
        getFrame().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        runCommand(CommandId.SEND_MSG, this.getQQNumber(), TYPE_GROUP, 0, event.getGroup().getNumber(), 0L, event.getMessage());
        return booleanResult();
    }

    public boolean sendDiscussionMessage(long discussion, String content) {
        return sendDiscussionMessage(getDiscussion(discussion), content);
    }

    public boolean sendDiscussionMessage(Discussion discussion, String content) {
        SendDiscussionMessageEvent event = new SendDiscussionMessageEvent(this, discussion, content);
        getFrame().getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return false;
        }
        runCommand(CommandId.SEND_MSG, this.getQQNumber(), TYPE_DISCUSS, 0, 0L, event.getDiscussion().getNumber(), event.getMessage());
        return booleanResult();
    }
    // TODO: 2017/5/13 临时会话消息
/*
    public int sendDiscussMessage(long discuss, String content) {
		return sendGroupMessage(discuss, content);
	}

	public int sendGroupTemporaryMessage(long session, String content) {
		return sendGroupMessage(session, content);
	}

	public int sendDiscussTemporaryMessage(long session, String content) {
		return sendGroupMessage(session, content);
	}
*/

    /**
     * 判断是否处于被屏蔽群信息状态
     *
     * @return 是否处于被屏蔽群信息状态
     */
    public boolean isBlocked() { // TODO: 2017/4/8  check if it is this.getNumber()
        runCommand(CommandId.IF_BLOCK, this.getQQNumber());
        return booleanResult();
    }

    /**
     * 取包括群主在内的群管列表
     *
     * @param group 群号
     * @return 群管列表
     */
    public String getAdminList(long group) {
        runCommand(CommandId.GET_ADMIN_LIST, this.getQQNumber(), group);
        return stringResult();
    }

    /**
     * 发说说
     *
     * @param content 内容
     * @return unknown
     */
    @SuppressWarnings("SpellCheckingInspection")
    public String addTaotao(String content) {
        runCommand(CommandId.ADD_TAOTAO, this.getQQNumber(), content);
        return stringResult();
    }

    /**
     * 取个性签名
     *
     * @return 个性签名
     */
    public String getSign() {
        runCommand(CommandId.GET_SIGN, this.getQQNumber());
        return stringResult();
    }

    /**
     * 设置个性签名
     *
     * @param content 内容
     * @return unknown
     */
    public String setSign(String content) {
        runCommand(CommandId.SET_SIGN, this.getQQNumber(), content);
        return stringResult();
    }

    /**
     * 通过 qun.qzone.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
     *
     * @return 转码后的 JSON 格式文本信息
     */
    // TODO: 2017/4/9  解析 json
    public String getGroupListA() {
        runCommand(CommandId.GET_GROUP_LIST_A, this.getQQNumber());
        return stringResult();
    }

    /**
     * 通过 qun.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
     *
     * @return 转码后的 JSON 格式文本信息
     */
    public String getGroupListB() {
        runCommand(CommandId.GET_GROUP_LIST_B, this.getQQNumber());
        return stringResult();
    }

    /**
     * 通过 qun.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
     *
     * @return 转码后的 JSON 格式文本
     */
    // TODO: 2017/4/9  解析 json
    public String getGroupMemberA() {
        runCommand(CommandId.GET_GROUP_MEMBER_A, this.getQQNumber());
        return stringResult();
    }

    /**
     * 通过 qun.qzone.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
     *
     * @return 转码后的 JSON 格式文本
     */
    public String getGroupMemberB() {
        runCommand(CommandId.GET_GROUP_MEMBER_B, this.getQQNumber());
        return stringResult();
    }

    /**
     * 通过 qun.qq.com 接口取得好友列表。成功返回转码后的 JSON 文本
     *
     * @return 转码后的 JSON 文本
     */
    public String getFriendList() {
        runCommand(CommandId.GET_FRIEND_LIST, this.getQQNumber());
        return stringResult();
    }

    /**
     * 取 Q 龄 成功返回 Q 龄 失败返回 -1
     *
     * @param QQ QQ
     * @return 成功返回 Q 龄 失败返回 -1
     */
    public int getQQAge(long QQ) {
        runCommand(CommandId.GET_QQ_AGE, this.getQQNumber(), QQ);
        return intResult();
    }

    /**
     * 取年龄 成功返回年龄 失败返回 -1
     *
     * @param QQ QQ
     * @return 成功返回年龄 失败返回 -1
     */
    public int getAge(long QQ) {
        runCommand(CommandId.GET_AGE, this.getQQNumber(), QQ);
        return intResult();
    }

    /**
     * 取邮箱 成功返回邮箱 失败返回空
     *
     * @param QQ Qq
     * @return 成功返回邮箱 失败返回空
     */
    public String getEmail(long QQ) {
        runCommand(CommandId.GET_EMAIL, this.getQQNumber(), QQ);
        return stringResult();
    }

    /**
     * 取对象性别  1女 2男 失败返回 -1
     *
     * @param QQ QQ
     * @return 1女 2男 失败返回 -1
     */
    public int getGender(long QQ) {
        runCommand(CommandId.GET_GENDER, this.getQQNumber(), QQ);
        return intResult();
    }

    /**
     * 向好友发送 "正在输入" 的状态信息. 当好友收到信息之后 "正在输入" 状态会立刻被打断
     *
     * @param QQ QQ
     * @return unknown
     */
    public int sendTyping(long QQ) {
        runCommand(CommandId.SEND_TYPING, this.getQQNumber(), QQ);
        return intResult();
    }

    /**
     * 向好友发送窗口抖动信息
     *
     * @param QQ QQ
     * @return unknown
     */
    public int sendShake(long QQ) {
        runCommand(CommandId.SEND_SHAKE, this.getQQNumber(), QQ);
        return intResult();
    }

    /**
     * 呵呵呵呵呵呵呵呵呵呵呵呵呵
     *
     * @param target 群号或 QQ 号
     */// TODO: 2017/5/18  check it
    public int crackIOSQQ(long target) {
        runCommand(CommandId.CRACK_IOS_QQ, this.getQQNumber(), target);
        return intResult();
    }

    /**
     * 邀请对象加入群 失败返回错误理由
     *
     * @param group  群号
     * @param QQList QQ 列表. 换行分割
     * @return 成功返回空 失败返回错误理由
     */
    public String groupInvitation(long group, String QQList) {
        runCommand(CommandId.GROUP_INVITATION, this.getQQNumber(), QQList, group);
        return stringResult();
    }

    /**
     * 邀请对象加入群 失败返回错误理由
     *
     * @param group 群号
     * @param QQ    QQ 列表
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
        runCommand(CommandId.CREATE_DG, this.getQQNumber());
        return longResult();
    }

    /**
     * 将对象移除讨论组. 成功返回空 失败返回理由
     *
     * @param discuss 讨论组 ID
     * @param QQ      QQ
     * @return 成功返回空 失败返回理由
     */
    public String kickFromDiscussGroup(long discuss, long QQ) {
        runCommand(CommandId.KICK_DG, this.getQQNumber(), discuss, QQ);
        return stringResult();
    }

    /**
     * 邀请对象加入讨论组 成功返回空 失败返回理由
     *
     * @param discuss 讨论组 ID
     * @param QQList  QQ 列表, 换行分割
     * @return 成功返回空 失败返回理由
     */
    public String discussGroupInvitation(long discuss, String QQList) {
        runCommand(CommandId.DG_INVITATION, this.getQQNumber(), discuss, QQList);
        return stringResult();
    }

    /**
     * 邀请对象加入讨论组 成功返回空 失败返回理由
     *
     * @param discuss 讨论组 ID
     * @param QQ      QQ 列表
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
        runCommand(CommandId.GET_DG_LIST, this.getQQNumber());
        return stringResult();
    }

    /**
     * 上传音频文件 成功返回 guid 用于发送
     *
     * @param amrData 音频文件数据. (int???)
     * @return 成功返回 guid 用于发送
     */
    public String uploadVoice(int amrData) {
        runCommand(CommandId.UPLOAD_VOICE, this.getQQNumber(), amrData);
        return stringResult();
    }

    /**
     * 通过音频、语音 guid 取得下载连接
     *
     * @param guid GUID
     * @return 下载连接
     */
    public String guidGetVoiceLink(String guid) {
        runCommand(CommandId.GUID_GET_VOICE_LINK, guid);
        return stringResult();
    }

    /**
     * 发送名片赞 10赞每 Q 每日 至多 50 人\日系列 成功返回空 失败返回理由 (腾讯爸爸给出的) 一次一赞 速度请自行管控以免冻结
     *
     * @param QQ QQ
     * @return 成功返回空 失败返回理由 (腾讯爸爸给出的)
     */
    public String like(long QQ) {
        runCommand(CommandId.LIKE, this.getQQNumber(), QQ);
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
        frame.getScheduler().scheduleTask(plugin, () -> {
            for (int i = 0; i < times; i++) {
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


    /**
     * 发送卡片信息. 推荐使用:
     * <p>
     * 发送好友卡片信息: {@link #sendPrivateObjectMessage(long, String, String)}
     * 发送群卡片信息: {@link #sendGroupObjectMessage(long, String, String)}
     * 发送讨论组卡片信息: {@link #sendDiscussionObjectMessage(long, String, String)}
     *
     * @param msgType          收信对象类型. TYPE 开头常量. 1好友 2群 3讨论组 4群临时会话 5讨论组临时会话
     * @param receiveGid       接受消息的群. 发群内, 临时会话必填 好友可不填
     * @param receiveQq        接受消息的 QQ. 临时会话, 好友必填 发至群内可不填
     * @param objectMsg        消息内容
     * @param objectMsgSubType 结构子类型,  00 基本 02 点歌 其他不明
     * @return 是否成功
     */
    public boolean sendObjectMessage(int msgType, long receiveGid, long receiveQq, // TODO: 2017/5/13 放到正确的位置
                                     String objectMsg, String objectMsgSubType) {
        runCommand(CommandId.SEND_OBJECT_MSG, this.getQQNumber(), msgType, receiveGid, receiveQq, objectMsg, objectMsgSubType);
        return booleanResult();
    }

    /**
     * 调用 {@link #sendObjectMessage(int, long, long, String, String)}
     * 本方法只是简化了调用参数
     *
     * @return 是否成功
     */
    public boolean sendPrivateObjectMessage(long qq, String objectMsg, String objectMsgSubType) {
        return sendObjectMessage(TYPE_PRIVATE, 0L, qq, objectMsg, objectMsgSubType);
    }

    /**
     * 调用 {@link #sendObjectMessage(int, long, long, String, String)}
     * 本方法只是简化了调用参数
     *
     * @return 是否成功
     */
    public boolean sendGroupObjectMessage(long group, String objectMsg, String objectMsgSubType) {
        return sendObjectMessage(TYPE_GROUP, group, 0L, objectMsg, objectMsgSubType);
    }

    /**
     * 调用 {@link #sendObjectMessage(int, long, long, String, String)}
     * 本方法只是简化了调用参数
     *
     * @return 是否成功
     */
    public boolean sendDiscussionObjectMessage(long discussion, String objectMsg, String objectMsgSubType) {
        return sendObjectMessage(TYPE_DISCUSS, discussion, 0L, objectMsg, objectMsgSubType);
    }// TODO: 2017/5/13 临时会话卡片信息


    // TODO: 2017/4/8  other commands

	/* COMMAND SENDER */

    private ConcurrentLinkedQueue<Object> results = new ConcurrentLinkedQueue<>();

    private static int parseInt(String value) {
        if (value == null) {
            return 0;
        }

        if (value.isEmpty()) {
            return 0;
        }
        if (value.equalsIgnoreCase("false")) {
            return 0;
        }
        if (value.equalsIgnoreCase("true")) {
            return 1;
        }
        if (value.equalsIgnoreCase("null")) {
            return 0;
        }
        return Integer.parseInt(value);
    }

    private static long parseLong(String value) {
        if (value == null) {
            return 0;
        }
        if (value.isEmpty()) {
            return 0;
        }
        if (value.equalsIgnoreCase("false")) {
            return 0;
        }
        if (value.equalsIgnoreCase("true")) {
            return 1;
        }
        if (value.equalsIgnoreCase("null")) {
            return 0;
        }
        return Long.parseLong(value);
    }

    private boolean booleanResult() {
        return intResult() == 1;
    }

    private int intResult() {
        //return 1;
        return parseInt(stringResult());
    }

    private long longResult() {
        return parseLong(stringResult());
    }


    private String stringResult() {
        Task task = JPREMain.getServerScheduler().scheduleTimingTask(() -> results.offer(""), 500);//0.5s
        //synchronized (MPQCaller.class) {//使正在等待返回值时, 指令不传达
        while (results.isEmpty()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        task.cancel();
        return Objects.toString(results.poll());
        //}
    }

    private synchronized void runCommand(CommandId id, Object... args) {
        //synchronized (MPQCaller.class) {
        for (MPQClient connectedClient : NetworkPacketHandler.getClients()) {
            connectedClient.sendPacket(new CommandPacket(this, id, args));
        }
        //}
    }

    public void addResult(Object result) {
        results.offer(result);
    }


	/* STATIC COMMAND SENDER */

    private static ConcurrentLinkedQueue<Object> staticResults = new ConcurrentLinkedQueue<>();

    private static int staticParseInt(String value) {
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

    private static long staticParseLong(String value) {
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

    private static boolean staticBooleanResult() {
        return staticIntResult() == 1;
    }

    private static int staticIntResult() {
        return 1;
        // TODO: 2017/4/8 fix that
        //return parseInt(stringResult());
    }

    private static long staticLongResult() {
        return staticParseLong(staticStringResult());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private static String staticStringResult() {
        Task task = JPREMain.getServerScheduler().scheduleTimingTask(() -> staticResults.add(""), 500);//0.5s
        //synchronized (MPQCaller.class) {//使正在等待返回值时, 指令不传达
        while (staticResults.isEmpty()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        task.forceCancel();

        return String.valueOf(staticResults.poll());
        //}
    }

    //args 必须至少有一个值并且类型为 long.
    private static void staticRunCommand(CommandId id, Object... args) {
        //synchronized (MPQCaller.class) {
        for (MPQClient connectedClient : NetworkPacketHandler.getClients()) {
            connectedClient.sendPacket(new StaticCommandPacket(id, args));
        }
        //}
    }

    public static void staticAddResult(Object result) {
        staticResults.add(result);
    }


	/* ROBOT LIST*/

    private static final Set<RobotQQ> list = new HashSet<>();

    public static Set<RobotQQ> getRobotList() {
        return list;
    }

    /**
     * 获取 Robot 实例, 不存在时自动创建
     *
     * @param frameIfCreate 如果 Robot 实例不存在, 构造新实例时的参数 1. 保证实例存在时可为 null
     * @return Robot
     * @throws NullPointerException 当实例不存在且 {@code frameIfCreate} 为 null 时抛出
     */
    public static RobotQQ getRobot(Frame frameIfCreate, long QQ) {
        for (RobotQQ robotQQ : list) {
            if (robotQQ.getQQNumber() == QQ) {
                return robotQQ;
            }
        }

        RobotQQ qq = new RobotQQ(frameIfCreate, QQ);
        list.add(qq);
        return qq;
    }
}