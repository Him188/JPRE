package net.mamoe.jpre.utils;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
@SuppressWarnings("Duplicates")
public final class Code {
    // TODO: 2017/4/22 finish

    /**
     * At 某个 qq
     *
     * @param targetQQ 目标 QQ
     */
    public static String at(long targetQQ) {
        return "[@" + targetQQ + "]";
    }

    /**
     * At 发送消息对象. At 目标将会在发送消息时自动替换为目标 qq
     *
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
     * @return 字体信息
     */
    public static String font(int color, int size) {
        return "字体[颜色=" + color + ",大小=" + size + "]";
    }


}