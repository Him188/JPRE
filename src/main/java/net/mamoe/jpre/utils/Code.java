package net.mamoe.jpre.utils;

import net.mamoe.jpre.*;

/**
 * 发送消息可使用的动态变量
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings({"WeakerAccess"})
public final class Code {
    /**
     * At 某个 qq
     *
     * @param targetQQ 目标 QQ
     * @return at 标识
     */
    public static String at(long targetQQ) {
        return "[@" + targetQQ + "]";
    }

    public static String at(QQ targetQQ) {
        return at(targetQQ.getNumber());
    }

    /**
     * At 发送消息对象. At 目标将会在发送消息时自动替换为目标 qq
     *
     * @return at 标识
     * @deprecated 暂未支持
     */
    @Deprecated
    public static String at() {
        return "[@[QQ]]";
    }

    /**
     * 字体
     *
     * @param color 颜色
     * @param size  字号
     * @return 字体标识
     */
    public static String font(int color, int size) {
        return "字体[颜色=" + color + ",大小=" + size + "]";
    }

    /**
     * 群名
     *
     * @return 群名标识
     */
    public static String gname() {
        return "gname";
    }

    /**
     * 对象 QQ
     *
     * @return QQ 标识
     */
    public static String qq() {
        return "[QQ]";
    }

    /**
     * 换行 (不必使用本方法, 可直接通过 {@code \n} 进行换行)
     *
     * @return 换行标识
     */
    public static String newLine() {
        return "\\n";
    }

    /**
     * 将字符转义为 unicode 字符 <br>
     * 如: Hello World 转换为 unicode 为 {@code \\u48\\u65\\u6c\\u6c\\u6f\\u20\\u57\\u6f\\u72\\u6c\\u64} <br>
     * (由于 Java 规范才写的两个斜杠, 真实结果只有一个斜杠!)<br>
     * <p>
     * 注意: 一些消息如颜文字的特殊符号收到时会被转义为 unicode.
     *
     * @return 转义后的 unicode 字符
     */
    public static String unicode(String string) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            sb.append("\\u").append(Integer.toHexString(c));
        }

        return sb.toString();
    }

    /**
     * 时间段. <br>
     *
     * @return 时间段. 如 早上, 中午, 傍晚, 晚上, 凌晨等
     */
    public static String timeSlot() {
        return "[时间段]";
    }

    /**
     * 随机数 0~100 <br>
     * 可叠加使用 {@code String number = random() + random()} = 0~10000
     *
     * @return 随机数 0~100
     */
    public static String random() {
        return "[r]";
    }

    /**
     * 自定义随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 自定义范围的随机数
     */
    public static String random(int min, int max) {
        return "r[" + min + "," + max + "]";
    }

    /**
     * 分句 <br>
     * 如: 早上好[next]吃过早饭了吗 ? 将会分成两条信息分别发送 每条信息最多允许使用 10 个分句标识 <br>
     * 注意, 消息将在 MPQ 内进行分句发送. JPRE 只会触发一次总信息的发送事件
     *
     * @return 分句标识
     */
    public static String split() {
        return "[next]";
    }

    /**
     * QQ默认小黄豆表情
     *
     * @param faceId 表情 id
     * @return 表情标识
     */
    public static String face(int faceId) {
        return "[bq" + faceId + "]";
    }

    /**
     * emoji 表情
     *
     * @param faceId 表情 id
     * @return 表情标识
     */
    public static String emoji(int faceId) {
        return "[em" + faceId + "]";
    }

    /**
     * At 全体
     *
     * @return at 标识
     */
    public static String atAll() {
        return "[@all]";
    }

    /**
     * 发送网页图片. 若已经有图片 GUID(例如 {@code {CC10C739-C125-06BD-496C-7DBCE02BB9BB}.jpg}), 不需要使用本方法转义, 直接发送 <br>
     * 注: 也可以通过 {@link RobotQQ#uploadImage(int, long, byte[])} 上传图片
     *
     * @param url 网页图片链接
     * @return 图片标识
     */
    public static String image(String url) {
        return "[" + url + "]";
    }

    /**
     * 规定消息气泡类型
     *
     * @param id 气泡 id. 已知 ID 0~328(2015/11/12)
     * @return 气泡标识
     */
    public static String bubbleStyle(int id) {
        return "[气泡" + id + "]";
    }
}