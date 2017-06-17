package net.mamoe.jpre;

import net.mamoe.jpre.event.send.*;
import net.mamoe.jpre.network.packet.ServerCommandPacket;
import net.mamoe.jpre.network.packet.ServerStaticCommandPacket;
import net.mamoe.jpre.plugin.Plugin;
import net.mamoe.jpre.utils.CommandResults;
import net.mamoe.jpre.utils.UserList;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 框架上的机器人 QQ(响应 QQ)
 *
 * @author Him188 @ JPRE Project
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

	/* LISTS */

    private final UserList<QQ> qqList = new UserList<>(number -> new QQ(this, number));
    private final UserList<Group> groupList = new UserList<>(number -> new Group(this, number));
    private final UserList<Discussion> discussionList = new UserList<>(number -> new Discussion(this, number));
    private final UserList<DiscussionTemporary> discussionTemporaryList = new UserList<>(number -> new DiscussionTemporary(this, number));
    private final UserList<GroupTemporary> groupTemporaryList = new UserList<>(number -> new GroupTemporary(this, number));

    public UserList<QQ> getQQList() {
        return qqList;
    }

    public UserList<Group> getGroupList() {
        return groupList;
    }

    public UserList<Discussion> getDiscussionList() {
        return discussionList;
    }

    public UserList<DiscussionTemporary> getDiscussionTemporaryList() {
        return discussionTemporaryList;
    }

    public UserList<GroupTemporary> getGroupTemporaryList() {
        return groupTemporaryList;
    }

    public QQ getQQ(long number) {
        return getQQList().get(number);
    }

    public Group getGroup(long number) {
        return getGroupList().get(number);
    }

    public Discussion getDiscussion(long number) {
        return getDiscussionList().get(number);
    }

    public DiscussionTemporary getDiscussionTemporary(long number) {
        return getDiscussionTemporaryList().get(number);
    }

    public GroupTemporary getGroupTemporary(long number) {
        return getGroupTemporaryList().get(number);
    }

    /* MPQ API*/

    /**
     * 计算得到页面操作用参数 Bkn 或 G_tk
     *
     * @return 页面操作用参数 Bkn 或 G_tk
     */
    public String getGtkBkn() {
        try {
            byte id = runCommand(CommandId.GET_GTK_BKN, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 计算得到页面操作用参数长 Bkn 或长 G_tk
     *
     * @return 页面操作用参数长 Bkn 或长 G_tk
     */
    public String getBKN32() {
        try {
            byte id = runCommand(CommandId.GET_BKN32, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 计算得到页面操作用参数长 Ldw
     *
     * @return 页面操作用参数长 Ldw
     */
    public String getLdw() {
        try {
            byte id = runCommand(CommandId.GET_LDW, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取得对应的会话秘钥
     *
     * @return 取得对应的会话秘钥
     */
    public String getSessionKey() {
        try {
            byte id = runCommand(CommandId.GET_SESSION_KEY, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取得页面登录用的 ClientKey
     *
     * @return 页面登录用的 ClientKey
     */
    public String getClientKey() {
        try {
            byte id = runCommand(CommandId.GET_CLIENT_KEY, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取得页面登录用的长 ClientKey
     *
     * @return 页面登录用的长 ClientKey
     */
    public String getLongClientKey() {
        try {
            byte id = runCommand(CommandId.GET_LONG_CLIENT_KEY, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取得页面操作用的 Cookies
     *
     * @return 页面操作用的 Cookies
     */
    public String getCookies() {
        try {
            byte id = runCommand(CommandId.GET_COOKIES, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 将指定 QQ 移出 QQ 黑名单
     *
     * @param QQ QQ
     */
    public void unBan(long QQ) {
        try {
            runVoidCommand(CommandId.D_BAN, this.getQQNumber(), QQ);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * 将指定 QQ 列入 QQ 黑名单
     *
     * @param QQ QQ
     */
    public void ban(long QQ) {
        try {
            runVoidCommand(CommandId.BAN, this.getQQNumber(), QQ);
        } catch (InterruptedException ignored) {

        }
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
        try {
            byte id = runCommand(CommandId.SHUT_UP, this.getQQNumber(), group, QQ, time);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 设置/取消全群禁言
     *
     * @param group 群号
     * @return 是否成功
     */
    public boolean shutUpWhole(long group) { // TODO: 2017/5/12 it doesn't work. fix (unknown course)
        try {
            byte id = runCommand(CommandId.SHUT_UP, this.getQQNumber(), group, "", 0);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 发群公告
     *
     * @param group   群号
     * @param title   公告标题
     * @param content 公告内容
     */
    public boolean setNotice(long group, String title, String content) {
        try {
            byte id = runCommand(CommandId.SET_NOTICE, this.getQQNumber(), group, title, content);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 取群公告
     *
     * @param group 群号
     * @return 群公告
     */
    public String getNotice(long group) {
        try {
            byte id = runCommand(CommandId.GET_NOTICE, this.getQQNumber(), group);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取群名片
     *
     * @param group 群号
     * @param QQ    QQ
     * @return 群名片
     */
    public String getNameCard(long group, long QQ) {
        try {
            byte id = runCommand(CommandId.GET_NAME_CARD, this.getQQNumber(), group, QQ);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 设置群名片
     *
     * @param group 群号
     * @param QQ    QQ
     * @param card  群名片
     */
    public boolean setNameCard(long group, long QQ, String card) {
        try {
            byte id = runCommand(CommandId.SET_NAME_CARD, this.getQQNumber(), group, QQ, card);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 退出讨论组
     *
     * @param discuss 讨论组号
     */
    public void quitDiscussGroup(long discuss) {
        try {
            runVoidCommand(CommandId.QUIT_DG, this.getQQNumber(), discuss);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * 删除好友
     *
     * @param QQ QQ
     */
    public void deleteFriend(long QQ) {
        try {
            runVoidCommand(CommandId.DEL_FRIEND, this.getQQNumber(), QQ);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * 移出群
     *
     * @param group 群号
     * @param QQ    QQ
     * @return 是否成功
     */
    public boolean kick(long group, long QQ) {
        try {
            byte id = runCommand(CommandId.KICK, this.getQQNumber(), group, QQ);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 主动加群.为了避免广告、群发行为。出现验证码时将会直接失败不处理
     *
     * @param group  群号
     * @param reason 理由
     */
    public void joinGroup(long group, String reason) {
        try {
            runVoidCommand(CommandId.JOIN_GROUP, this.getQQNumber(), group, reason);
        } catch (InterruptedException ignored) {

        }
    }

    /**
     * 退出群
     *
     * @param group 群号
     */
    public void quitGroup(long group) {
        try {
            runVoidCommand(CommandId.QUIT_GROUP, this.getQQNumber(), group);
        } catch (InterruptedException ignored) {

        }
    }


    public static final int UPLOAD_TYPE_FRIEND = 1;
    public static final int UPLOAD_TYPE_TEMPORARY = UPLOAD_TYPE_FRIEND;

    public static final int UPLOAD_TYPE_GROUP = 2;
    public static final int UPLOAD_TYPE_DISCUSSION = UPLOAD_TYPE_GROUP;

    /**
     * 上传图片
     *
     * @param uploadType UPLOAD_TYPE 开头常量. 好友图和群图的GUID并不相同并不通用 需要非别上传
     * @param uploaderQq 上传该图片所属的群号或QQ
     * @param file       图片数据. 可使用 {@link net.mamoe.jpre.utils.Utils#readFile} 读取图片文件
     * @return 图片 GUID
     */
    public String uploadImage(int uploadType, long uploaderQq, byte[] file) {
        try {
            byte id = runCommand(CommandId.UPLOAD_PIC, this.getQQNumber(), uploadType, uploaderQq, file);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
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
     * @param type    类型. TYPE 开头常量
     * @param target  发送目标
     * @param content 消息内容
     * @return unknown
     */
    public int reply(int type, long target, String content) {
        try {
            byte id = runCommand(CommandId.REPLY, this.getQQNumber(), type, target, content);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }


    /**
     * 发送消息
     * <p>
     * 不建议使用本方法, 推荐使用以下方法:
     * 发送群消息: {@link #sendGroupMessage(Group, String)}
     * 发送好友消息: {@link #sendFriendMessage(QQ, String)}
     * 发送讨论组消息: {@link #sendDiscussionMessage(Discussion, String)}
     * 发送群临时会话消息: {@link #sendGroupTemporaryMessage(GroupTemporary, String)}
     * 发送讨论组临时会话消息: {@link #sendDiscussionTemporaryMessage(DiscussionTemporary, String)}
     *
     * @param msgType    消息类型, TYPE 开头常量
     * @param msgSubType 消息子类型. 无特殊说明情况下留空或填零
     * @param receiveGid 接受消息的群号或讨论组号或临时会话号
     * @param receiveQq  接受消息的 QQ 号
     * @param msg        消息内容
     * @return 是否成功
     */
    @SuppressWarnings("SameParameterValue")
    public boolean sendMessage(int msgType, int msgSubType, long receiveGid, long receiveQq, String msg) {
        try {
            byte id = runCommand(CommandId.SEND_MSG, this.getQQNumber(), TYPE_DISCUSS, 0, receiveGid, receiveQq, msg);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }


    public boolean sendFriendMessage(long QQ, String content) {
        return sendFriendMessage(getQQ(QQ), content);
    }

    public boolean sendFriendMessage(QQ QQ, String content) {
        SendFriendMessageEvent event = new SendFriendMessageEvent(this, QQ, content);
        getFrame().getPluginManager().callEvent(event);
        return !event.isCancelled() && sendMessage(TYPE_FRIEND, 0, 0L, QQ.getNumber(), event.getMessage());
    }


    public boolean sendGroupMessage(long group, String content) {
        return sendGroupMessage(getGroup(group), content);
    }

    public boolean sendGroupMessage(Group group, String content) {
        SendGroupMessageEvent event = new SendGroupMessageEvent(this, group, content);
        getFrame().getPluginManager().callEvent(event);
        return !event.isCancelled() && sendMessage(TYPE_GROUP, 0, group.getNumber(), 0L, event.getMessage());
    }


    public boolean sendDiscussionMessage(long discussion, String content) {
        return sendDiscussionMessage(getDiscussion(discussion), content);
    }

    public boolean sendDiscussionMessage(Discussion discussion, String content) {
        SendDiscussionMessageEvent event = new SendDiscussionMessageEvent(this, discussion, content);
        getFrame().getPluginManager().callEvent(event);
        return !event.isCancelled() && sendMessage(TYPE_DISCUSS, 0, discussion.getNumber(), 0L, event.getMessage());
    }


    public boolean sendGroupTemporaryMessage(GroupTemporary session, String content) {
        SendGroupTemporaryMessageEvent event = new SendGroupTemporaryMessageEvent(this, session, content);
        getFrame().getPluginManager().callEvent(event);
        return !event.isCancelled() && sendMessage(TYPE_GROUP_TEMPORARY_SESSION, 0, session.getNumber(), 0L, event.getMessage());
    }

    public boolean sendGroupTemporaryMessage(long session, String content) {
        return sendGroupTemporaryMessage(getGroupTemporary(session), content);
    }


    public boolean sendDiscussionTemporaryMessage(DiscussionTemporary session, String content) {
        SendDiscussionTemporaryMessageEvent event = new SendDiscussionTemporaryMessageEvent(this, session, content);
        getFrame().getPluginManager().callEvent(event);
        return !event.isCancelled() && sendMessage(TYPE_DISCUSS_TEMPORARY_SESSION, 0, session.getNumber(), 0L, event.getMessage());
    }

    public boolean sendDiscussionTemporaryMessage(long session, String content) {
        return sendDiscussionTemporaryMessage(getDiscussionTemporaryList().get(session), content);
    }


    /**
     * 判断是否处于被屏蔽群信息状态
     *
     * @return 是否处于被屏蔽群信息状态
     */
    public boolean isBlocked() {
        try {
            byte id = runCommand(CommandId.IF_BLOCK, this.getQQNumber());
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
    }

    /**
     * 取包括群主在内的群管列表
     *
     * @param group 群号
     * @return 群管列表
     */
    public String getAdminList(long group) {
        try {
            byte id = runCommand(CommandId.GET_ADMIN_LIST, this.getQQNumber(), group);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 发说说
     *
     * @param content 内容
     * @return unknown
     */
    @SuppressWarnings("SpellCheckingInspection")
    public String addTaotao(String content) {
        try {
            byte id = runCommand(CommandId.ADD_TAOTAO, this.getQQNumber(), content);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取个性签名
     *
     * @return 个性签名
     */
    public String getSign() {
        try {
            byte id = runCommand(CommandId.GET_SIGN, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 设置个性签名
     *
     * @param content 内容
     * @return unknown
     */
    public String setSign(String content) {
        try {
            byte id = runCommand(CommandId.SET_SIGN, this.getQQNumber(), content);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过 qun.qzone.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
     *
     * @return 转码后的 JSON 格式文本信息
     */
    // TODO: 2017/4/9  解析 json
    public String getGroupListA() {
        try {
            byte id = runCommand(CommandId.GET_GROUP_LIST_A, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过 qun.qq.com 接口取得取群列表.成功返回转码后的 JSON 格式文本信息
     *
     * @return 转码后的 JSON 格式文本信息
     */
    public String getGroupListB() {
        try {
            byte id = runCommand(CommandId.GET_GROUP_LIST_B, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过 qun.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
     *
     * @return 转码后的 JSON 格式文本
     */
    // TODO: 2017/4/9  解析 json
    public String getGroupMemberA(long group) {
        try {
            byte id = runCommand(CommandId.GET_GROUP_MEMBER_A, this.getQQNumber(), group);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过 qun.qzone.qq.com 接口取得群成员列表 成功返回转码后的 JSON 格式文本
     * (测试发现这似乎是管理员列表?)
     *
     * @return 转码后的 JSON 格式文本
     */
    public String getGroupMemberB(long group) {
        try {
            byte id = runCommand(CommandId.GET_GROUP_MEMBER_B, this.getQQNumber(), group);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过 qun.qq.com 接口取得好友列表。成功返回转码后的 JSON 文本
     *
     * @return 转码后的 JSON 文本
     */
    public String getFriendList() {
        try {
            byte id = runCommand(CommandId.GET_FRIEND_LIST, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取 Q 龄 成功返回 Q 龄 失败返回 -1
     *
     * @param QQ QQ
     * @return 成功返回 Q 龄 失败返回 -1
     */
    public int getQQAge(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_QQ_AGE, this.getQQNumber(), QQ);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 取年龄 成功返回年龄 失败返回 -1
     *
     * @param QQ QQ
     * @return 成功返回年龄 失败返回 -1
     */
    public int getAge(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_AGE, this.getQQNumber(), QQ);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 取邮箱 成功返回邮箱 失败返回空
     *
     * @param QQ Qq
     * @return 成功返回邮箱 失败返回空
     */
    public String getEmail(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_EMAIL, this.getQQNumber(), QQ);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 取对象性别  1女 2男 失败返回 -1
     *
     * @param QQ QQ
     * @return 1女 2男 失败返回 -1
     */
    public int getGender(long QQ) {
        try {
            byte id = runCommand(CommandId.GET_GENDER, this.getQQNumber(), QQ);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 向好友发送 "正在输入" 的状态信息. 当好友收到信息之后 "正在输入" 状态会立刻被打断
     *
     * @param QQ QQ
     * @return unknown
     */
    public int sendTyping(long QQ) {
        try {
            byte id = runCommand(CommandId.SEND_TYPING, this.getQQNumber(), QQ);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 向好友发送窗口抖动信息
     *
     * @param QQ QQ
     * @return unknown
     */
    public int sendShake(long QQ) {
        try {
            byte id = runCommand(CommandId.SEND_SHAKE, this.getQQNumber(), QQ);
            return results.intResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 邀请对象加入群 失败返回错误理由
     *
     * @param group  群号
     * @param QQList QQ 列表. 换行分割
     * @return 成功返回空 失败返回错误理由
     */
    public String groupInvitation(long group, String QQList) {
        try {
            byte id = runCommand(CommandId.GROUP_INVITATION, this.getQQNumber(), QQList, group);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
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
        try {
            byte id = runCommand(CommandId.CREATE_DG, this.getQQNumber());
            return results.longResult(id);
        } catch (InterruptedException ignored) {
            return 0;
        }
    }

    /**
     * 将对象移除讨论组. 成功返回空 失败返回理由
     *
     * @param discuss 讨论组 ID
     * @param QQ      QQ
     * @return 成功返回空 失败返回理由
     */
    public String kickFromDiscussGroup(long discuss, long QQ) {
        try {
            byte id = runCommand(CommandId.KICK_DG, this.getQQNumber(), discuss, QQ);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 邀请对象加入讨论组 成功返回空 失败返回理由
     *
     * @param discuss 讨论组 ID
     * @param QQList  QQ 列表, 换行分割
     * @return 成功返回空 失败返回理由
     */
    public String discussGroupInvitation(long discuss, String QQList) {
        try {
            byte id = runCommand(CommandId.DG_INVITATION, this.getQQNumber(), discuss, QQList);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
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
        try {
            byte id = runCommand(CommandId.GET_DG_LIST, this.getQQNumber());
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 上传音频文件 成功返回 guid 用于发送
     *
     * @param amrData 音频文件数据. (int???)
     * @return 成功返回 guid 用于发送
     */
    public String uploadVoice(int amrData) {
        try {
            byte id = runCommand(CommandId.UPLOAD_VOICE, this.getQQNumber(), amrData);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 通过音频、语音 guid 取得下载连接
     *
     * @param guid GUID
     * @return 下载连接
     */
    public String guidGetVoiceLink(String guid) {
        try {
            byte id = runCommand(CommandId.GUID_GET_VOICE_LINK, guid);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
    }

    /**
     * 发送名片赞 10赞每 Q 每日 至多 50 人\日系列 成功返回空 失败返回理由 (腾讯爸爸给出的) 一次一赞 速度请自行管控以免冻结
     *
     * @param QQ QQ
     * @return 成功返回空 失败返回理由 (腾讯爸爸给出的)
     */
    public String like(long QQ) {
        try {
            byte id = runCommand(CommandId.LIKE, this.getQQNumber(), QQ);
            return results.stringResult(id);
        } catch (InterruptedException ignored) {
            return null;
        }
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
        frame.getScheduler().addTask(plugin, () -> {
            for (int i = 0; i < times; i++) {
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
     * 发送群临时会话卡片信息: {@link #sendGroupTemporaryObjectMessage(long, String, String)}
     * 发送讨论组临时会话卡片信息: {@link #sendDiscussionTemporaryObjectMessage(long, String, String)}
     *
     * @param msgType          收信对象类型. TYPE 开头常量. 1好友 2群 3讨论组 4群临时会话 5讨论组临时会话
     * @param receiveGid       接受消息的群. 发群内, 临时会话必填 好友可不填
     * @param receiveQq        接受消息的 QQ. 临时会话, 好友必填 发至群内可不填
     * @param objectMsg        消息内容
     * @param objectMsgSubType 结构子类型,  00 基本 02 点歌 其他不明
     * @return 是否成功
     */
    public boolean sendObjectMessage(int msgType, long receiveGid, long receiveQq,
                                     String objectMsg, String objectMsgSubType) {
        try {
            byte id = runCommand(CommandId.SEND_OBJECT_MSG, this.getQQNumber(), msgType, receiveGid, receiveQq, objectMsg, objectMsgSubType);
            return results.booleanResult(id);
        } catch (InterruptedException ignored) {
            return false;
        }
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
    }
    // TODO: 2017/5/22 发送卡片消息事件

    /**
     * 调用 {@link #sendObjectMessage(int, long, long, String, String)}
     * 本方法只是简化了调用参数
     *
     * @return 是否成功
     */
    public boolean sendDiscussionTemporaryObjectMessage(long session, String objectMsg, String objectMsgSubType) {
        return sendObjectMessage(TYPE_DISCUSS_TEMPORARY_SESSION, session, 0L, objectMsg, objectMsgSubType);
    }

    /**
     * 调用 {@link #sendObjectMessage(int, long, long, String, String)}
     * 本方法只是简化了调用参数
     *
     * @return 是否成功
     */
    public boolean sendGroupTemporaryObjectMessage(long session, String objectMsg, String objectMsgSubType) {
        return sendObjectMessage(TYPE_GROUP_TEMPORARY_SESSION, session, 0L, objectMsg, objectMsgSubType);
    }


    // TODO: 2017/4/8  other commands

	/* COMMAND SENDER */

    private CommandResults results = new CommandResults();

	private byte runCommand(CommandId id, Object... args) throws InterruptedException {
		byte i = results.requestId();
        this.getFrame().getClient().sendPacket(new ServerCommandPacket(i, this, id, args));
        return i;
    }

	private void runVoidCommand(CommandId id, Object... args) throws InterruptedException {
		this.getFrame().getClient().sendPacket(new ServerStaticCommandPacket((byte) 0, id, args));
    }

    public void setResult(byte id, Object result) {
        this.results.setResult(id, result);
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