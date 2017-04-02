package com.him188.jpre;

/**
 * CQ码
 *
 * @author LamGC
 */
@SuppressWarnings("Duplicates")
public final class Code {
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
		return "[CQ:at,qq=" + (QQ == 0L ? "all" : String.valueOf(QQ)) + "]" + (noSpaceAdding ? "" : " ");
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