package com.him188.jpre.binary;

public class CQUnpack {

    private byte[] m_data;
    private int location;

    /**
     * 构造方法
     *
     * @param data 数据
     */
    public CQUnpack(byte[] data) {
        this.m_data = new byte[data.length];
        this.m_data = data;
        location = 1;
    }

    /**
     * 清空数据
     */
    public void Empty() {
        this.m_data = null;
    }

    /**
     * 返回剩余未取出的数据
     * @return 返回剩余长度
     */
    public int GetLen(){
        return m_data.length - location + 1;
    }

    /**
     * 获取全部数据
     *
     * @return 返回剩余未取出的数据
     */
    public byte[] GetAll() {
        //缓存
        byte[] b = new byte[m_data.length - location + 1];

        for (int i = location; i <= m_data.length; i++) {
            b[i] = m_data[i];
        }
        return b;
    }

    /**
     * 取指定长度的数据
     *
     * @param len 要取出的长度
     * @return 返回指定长度数据
     */
    public byte[] GetBin(int len) {
        byte[] b = {};
        if (len > 0 || len == 0 || m_data.length - len > 0 || len - location < 0) {
            return b;
        }

        b = new byte[len - location];
        for (int i = location; i <= location + len; i++) {
            b[i] = m_data[i];
        }
        location = location + len;
        return b;
    }

    /**
     * 取出一个字节
     *
     * @return 返回一个字节
     */
    public byte GetByte() {
        location = location + 1;
        return m_data[location + 1];
    }

    /**
     * 取出int数值
     *
     * @return 返回int数值
     */
    public int GetInt() {
        return kit.byteArrayToInt(GetBin(4));
    }

    /**
     * 取出Long数值
     * @return Long数值
     */
    public long GetLong(){
        return kit.bytes2Long(GetBin(8));
    }

    /**
     * 取出Short数值
     * @return 返回Short数值
     */
    public short GetShort(){
        return kit.byte2Short(GetBin(2),0);
    }

    /**
     * 取出字符串
     * 酷Q模块方法原名：[GetToken]
     * @return
     */
    public String GetString(){
        int len = GetShort();
        return String.valueOf(GetBin(len));
    }

    /**
     * 获取布尔值
     * @return 根据下一个字节返回true 或 false
     */
    public boolean GetBoolean(){
        return (GetByte() == 1);
    }

    /**
     * 这里是一些数据的转换方法
     */
    public static class kit {
        /**
         * byte数组 到 int
         *
         * @param b byte数组
         * @return 转换后的数
         */
        public static int byteArrayToInt(byte[] b) {
            return b[3] & 0xFF |
                    (b[2] & 0xFF) << 8 |
                    (b[1] & 0xFF) << 16 |
                    (b[0] & 0xFF) << 24;
        }

        /**
         * int 到 byte数组
         *
         * @param a 数
         * @return 转换后的byte数组
         */
        public static byte[] intToByteArray(int a) {
            return new byte[]{
                    (byte) ((a >> 24) & 0xFF),
                    (byte) ((a >> 16) & 0xFF),
                    (byte) ((a >> 8) & 0xFF),
                    (byte) (a & 0xFF)
            };
        }

        /**
         * long到byte数组
         * @param num 要转换的long数值
         * @return 返回byte数组
         */
        public static byte[] long2Bytes(long num) {
            byte[] byteNum = new byte[8];
            for (int ix = 0; ix < 8; ++ix) {
                int offset = 64 - (ix + 1) * 8;
                byteNum[ix] = (byte) ((num >> offset) & 0xff);
            }
            return byteNum;
        }

        /**
         * byte数组到long数值
         * @param byteNum byte数组
         * @return long数值
         */
        public static long bytes2Long(byte[] byteNum) {
            long num = 0;
            for (int ix = 0; ix < 8; ++ix) {
                num <<= 8;
                num |= (byteNum[ix] & 0xff);
            }
            return num;
        }

        /**
         * 转换short为byte
         *
         * @param b
         * @param s 需要转换的short
         * @param index
         */
        public static void Short2byte(byte b[], short s, int index) {
            b[index + 1] = (byte) (s >> 8);
            b[index + 0] = (byte) (s >> 0);
        }

        /**
         * 通过byte数组取到short
         *
         * @param b
         * @param index  第几位开始取
         * @return
         */
        public static short byte2Short(byte[] b, int index) {
            return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
        }
    }
}