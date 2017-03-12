package net.coding.lamgc.coolq.jpre;

import net.coding.lamgc.coolq.jpre.command.Command;
import net.coding.lamgc.coolq.jpre.command.CommandId;
import net.coding.lamgc.coolq.jpre.command.CommandManager;

public class CoolQLibrary {
	public static final int RESULT_TYPE_ACCEPT = 1;
	public static final int RESULT_TYPE_DENIED = 2;
	public static final int REQUEST_TYPE_ACTIVE_JOIN = 1;
	public static final int REQUEST_TYPE_INVITE = 2;

	public static int CQ_sendPrivateMsg(int authCode, long QQ, String message) {
		return CommandManager.runCommand(new Command(CommandId.SEND_PRIVATE_MESSAGE, false, authCode, QQ, message), int.class);
	}

	public static int CQ_sendGroupMsg(int authCode, long group, String message) {
		return CommandManager.runCommand(new Command(CommandId.SEND_GROUP_MESSAGE, false, authCode, group, message), int.class);
	}

	public static int CQ_sendDiscussMsg(int authCode, long discuss, String message) {
		return CommandManager.runCommand(new Command(CommandId.SEND_DISCUSS_MESSAGE, false, authCode, discuss, message), int.class);
	}

	public static int CQ_sendLike(int authCode, long QQ) {
		return CommandManager.runCommand(new Command(CommandId.SEND_LIKE, false, authCode, QQ), int.class);
	}

	//新版QQ的10次赞
	public static int CQ_sendLikeV2(int authCode, long QQ, int times) {
		return CommandManager.runCommand(new Command(CommandId.SEND_LIKE_V2, false, authCode, QQ, times), int.class);
	}

	public static int CQ_getCookies(int authCode) {
		return CommandManager.runCommand(new Command(CommandId.GET_COOKIES, true, authCode), int.class);
	}

	/**
	 * 接受语音
	 *
	 * @param authCode  authCode
	 * @param file      接受消息的文件名
	 * @param outFormat 应用所需格式
	 *
	 * @return ? 似乎会直接返回 file
	 */
	public static String CQ_getRecord(int authCode, String file, String outFormat) {
		return CommandManager.runCommand(new Command(CommandId.GET_RECORD, true, authCode, file, outFormat), String.class);
	}

	public static int CQ_getCsrfToken(int authCode) {
		return CommandManager.runCommand(new Command(CommandId.GET_CSRF_TOKEN, true, authCode), int.class);
	}

	public static String CQ_getAppDirectory(int authCode) {
		return CommandManager.runCommand(new Command(CommandId.GET_APP_DIRECTORY, true, authCode), String.class);
	}

	public static long CQ_getLoginQQ(int authCode) {
		return CommandManager.runCommand(new Command(CommandId.GET_APP_DIRECTORY, true, authCode), long.class);
	}

	public static String CQ_getLoginNick(int authCode) {
		return CommandManager.runCommand(new Command(CommandId.GET_LOGIN_NICK, true, authCode), String.class);
	}

	public static int CQ_setGroupKick(int authCode, long group, long QQ, boolean block) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_KICK, false, authCode, group, QQ, block), int.class);
	}

	//time: s. 解禁0
	public static int CQ_setGroupBan(int authCode, long group, long QQ, long time) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_BAN, false, authCode, group, QQ, time), int.class);
	}

	public static int CQ_setGroupAdmin(int authCode, long group, long QQ, boolean admin) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ADMIN, false, authCode, group, QQ, admin), int.class);
	}

	public static int CQ_setGroupSpecialTitle(int authCode, long group, long QQ, String title, long timeLimit) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_SPECIAL_TITLE, false, authCode, group, QQ, title, timeLimit), int.class);
	}

	public static int CQ_setGroupWholeBan(int authCode, long group, boolean ban) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_WHOLE_BAN, false, authCode, group, ban), int.class);
	}

	public static int CQ_setGroupAnonymousBan(int authCode, long group, String anonymousId, long time) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ANONYMOUS_BAN, false, authCode, group, anonymousId, time), int.class);
	}

	public static int CQ_setGroupAnonymous(int authCode, long group, boolean enabled) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ANONYMOUS, false, authCode, group, enabled), int.class);
	}

	public static int CQ_setGroupCard(int authCode, long group, long QQ, String newCard) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_CARD, false, authCode, group, QQ, newCard), int.class);
	}

	public static int CQ_setGroupLeave(int authCode, long group, boolean dissolve) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_LEAVE, false, authCode, group, dissolve), int.class);
	}

	public static int CQ_setDiscussLeave(int authCode, long discuss) {
		return CommandManager.runCommand(new Command(CommandId.SET_DISCUSS_LEAVE, false, authCode, discuss), int.class);
	}

	public static int CQ_setFriendAddRequest(int authCode, String requestId, int resultType, String nick) {
		return CommandManager.runCommand(new Command(CommandId.SET_FRIEND_ADD_REQUEST, false, authCode, requestId, resultType, nick), int.class);
	}


	@Deprecated
	public static int CQ_setGroupAddRequest(int authCode, String requestId, int requestType, int resultType) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ADD_REQUEST, false, authCode, requestId, resultType, resultType), int.class);
	}

	public static int CQ_setGroupAddRequestV2(int authCode, String requestId, int requestType, int resultType, String reason) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ADD_REQUEST_V2, false, authCode, requestId, resultType, resultType, reason), int.class);
	}

	public static int CQ_addLog(int authCode, int priority, String type, String message) {
		return CommandManager.runCommand(new Command(CommandId.SET_GROUP_ADD_REQUEST, false, authCode, priority, type, message), int.class);
	}

	public static int CQ_setFatal(int authCode, String message) {
		return CommandManager.runCommand(new Command(CommandId.SET_FATAL, false, authCode, message), int.class);
	}


	@Deprecated
	public static String CQ_getGroupMemberInfo(int authCode, long group, long QQ) {
		return CommandManager.runCommand(new Command(CommandId.GET_GROUP_MEMBER_INFO, false, authCode, group, QQ), String.class);
	}

	public static String CQ_getGroupMemberInfoV2(int authCode, long group, long QQ, boolean noCache) {
		return CommandManager.runCommand(new Command(CommandId.GET_GROUP_MEMBER_INFO_V2, false, authCode, group, QQ, noCache), String.class);
	}

	public static String CQ_getStrangerInfo(int authCode, long QQ, boolean noCache) {
		return CommandManager.runCommand(new Command(CommandId.GET_STRANGER_INFO, false, authCode, QQ, noCache), String.class);
	}

	// TODO: 2017/1/26 0026 RtlMoveMemory
	// TODO: 2017/1/26 0026 GlobalSize

	/**
	 * CQ码
	 *
	 * @author LamGC
	 */
	public static final class Code {
		/**
		 * 禁忌符号的转换233333
		 *
		 * @param oldMessage    待转换的文本
		 * @param commaEscaping 是否转换逗号
		 *
		 * @return 返回转义后文本
		 */
		@SuppressWarnings("SameParameterValue")
		public static String escape(String oldMessage, boolean commaEscaping) {
			String newMessage = oldMessage;
			newMessage = newMessage.replace("&", "&amp");
			newMessage = newMessage.replace("[", "&#91");
			newMessage = newMessage.replace("]", "&#93");
			if (commaEscaping) {
				newMessage = newMessage.replace(",", "&#44");
			}
			return newMessage;
		}

		/**
		 * 同样的，将获得的CQ码里的代替符换回去
		 *
		 * @param oldMessage 待转换文本
		 *
		 * @return 返回转义后文本
		 */
		public static String unescape(String oldMessage) {
			return oldMessage.replace("&amp", "&")
					.replace("&#91", "[")
					.replace("&#93", "]")
					.replace("&#44", ",");
		}

		public static String at(long QQ) {
			return at(QQ, false);
		}

		/**
		 * At某人
		 *
		 * @param QQ            要At的Q号，0为全体，暂时不支持
		 * @param noSpaceAdding At后添加空格，可使At更规范美观，如果不需要添加空格，请填true
		 */
		public static String at(long QQ, boolean noSpaceAdding) {
			return "[CQ:at,qq=" + (QQ == 0 ? "all" : QQ) + "]" + (noSpaceAdding ? "" : " ");
		}

		/**
		 * QQ表情
		 *
		 * @param face Face类里Face_开头变量
		 */
		public static String face(int face) {
			return "[CQ:face,id=" + String.valueOf(face) + "]";
		}

		/**
		 * emoji表情
		 *
		 * @param face emoji表情的 unicode编号
		 */
		public static String emoji(int face) {
			return "[CQ:emoji,id=" + String.valueOf(face) + "]";
		}

		/**
		 * 窗口抖动，仅限好友
		 */
		public static String shake() {
			return "[CQ:shake]";
		}

		/**
		 * 匿名聊天，仅限群
		 *
		 * @param noForce 如果希望在匿名失败时将消息发出请填true，false为取消发送
		 */
		public static String anonymous(boolean noForce) {
			if (noForce) {
				return "[CQ:anonymous,ignore=true]";
			}
			return "[CQ:anonymous]";
		}

		/**
		 * 音乐分享
		 *
		 * @param MusicID       音乐的歌曲ID
		 * @param Music_website 音乐网站类型，目前支持  qq/QQ音乐  163/网易云音乐  xiami/虾米音乐
		 */
		public static String music(long MusicID, String Music_website) {
			return "[CQ:music,id=" + String.valueOf(MusicID) + ",type=" + escape(Music_website, true) + "]";
		}

		/**
		 * 名片分享
		 *
		 * @param type    类型 目前支持  qq/好友分享  group/群分享
		 * @param account 要分享的帐号，类型为qq，则为QQID，类型为group，则为群号
		 */
		public static String contact(String type, long account) {
			return "[CQ:contact,type=" + escape(type, true) + ",id=" + String.valueOf(account) + "]";
		}

		/**
		 * 链接分享
		 *
		 * @param url        分享的链接
		 * @param title      分享的标题，建议12字以内
		 * @param content    分享的简介，建议30字以内
		 * @param pictureUrl 摁下的图片链接，留空则为默认图片
		 */
		public static String share(String url, String title, String content, String pictureUrl) {
			String para = ",url=" + escape(url, true);
			if (!(title.equals(""))) {
				para = para + ",title=" + escape(title, true);
			} else if (!(content.equals(""))) {
				para = para + ",content=" + escape(content, true);
			} else if (!(pictureUrl.equals(""))) {
				para = para + ",image=" + escape(pictureUrl, true);
			}
			return para;
		}

		/**
		 * 音乐自定义分享
		 *
		 * @param customUrl 分享链接  点击分享后进入的链接（如歌曲介绍页）
		 * @param audioUrl  音频链接  音乐的音频链接（如mp3链接）
		 * @param title     标题  音乐的标题，建议12字以内
		 * @param content   内容 音乐的简介，建议30字以内
		 * @param coverUrl  封面图片链接  音乐的封面图片链接，留空为默认图片
		 */
		public static String customMusicSharing(String customUrl, String audioUrl, String title, String content, String coverUrl) {
			String para = ",url=" + escape(customUrl, true);
			para = para + ",audio=" + escape(audioUrl, true);
			if (!(title.equals(""))) {
				para = para + ",title=" + escape(title, true);
			} else if (!(content.equals(""))) {
				para = para + ",content=" + escape(content, true);
			} else if (!(coverUrl.equals(""))) {
				para = para + ",image=" + escape(coverUrl, true);
			}
			return "[CQ:music,type=custom" + para + "]";
		}

		/**
		 * 图片
		 *
		 * @param imagePath 图片路径  将图片放在机器人目录下的 data\image 下，并填写相对路径。如 data\image\1.jpg 则填写1.jpg
		 */
		public static String image(String imagePath) {
			return "[CQ:image,file=" + escape(imagePath, true) + "]";
		}

		/**
		 * 语音
		 *
		 * @param filepath 语音路径  将语音放在机器人目录下的 data\record 下，并填写相对路径。如 data\record\1.amr 则填写1.amr
		 */
		public static String record(String filepath) {
			return "[CQ:record,file=" + escape(filepath, true) + "]";
		}

	}
}
