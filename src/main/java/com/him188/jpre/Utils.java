package com.him188.jpre;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("WeakerAccess")
public final class Utils {
    public static void writeFile(String fileName, String content) throws IOException {
        writeFile(fileName, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
    }

    public static void writeFile(String fileName, InputStream content) throws IOException {
        writeFile(new File(fileName), content);
    }

    public static void writeFile(File file, String content) throws IOException {
        writeFile(file, new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeFile(File file, InputStream content) throws IOException {
        if (content == null) {
            throw new IllegalArgumentException("content must not be null");
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream stream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = content.read(buffer)) != -1) {
            stream.write(buffer, 0, length);
        }
        stream.close();
        content.close();
    }

    public static String readFile(File file) throws IOException {
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        return readFile(new FileInputStream(file));
    }

    public static String readFile(String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundException();
        }
        return readFile(new FileInputStream(file));
    }

    public static String readFile(InputStream inputStream) throws IOException {
        return readFile(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private static String readFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        temp = br.readLine();
        while (temp != null) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(temp);
            temp = br.readLine();
        }
        br.close();
        reader.close();
        return stringBuilder.toString();
    }


    public static byte[] base64decode(String string) {
        if (string == null || string.isEmpty()) {
            return new byte[0];
        }
        try {
            return new BASE64Decoder().decodeBuffer(string);
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static String md5Encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

    public static String utf8Decode(String message) {
        try {
            return new String(message.getBytes(), "utf-8");
        } catch (UnsupportedEncodingException ignored) {
            return message;
        }
    }

    public static String GBKDecode(String encodedString) {
        try {
            return new String(encodedString.getBytes("GBK"), "UTF-8");
        } catch (UnsupportedEncodingException ignored) {
            return null;
        }
    }

    public static byte[] arrayAppend(byte[] original, byte[] append) {
        byte[] newArray = new byte[original.length + append.length];
        System.arraycopy(original, 0, newArray, 0, original.length);
        System.arraycopy(append, 0, newArray, original.length, append.length);
        return newArray;
    }

    public static byte[] arrayDelete(byte[] array, int length) {
        byte[] newArray = new byte[array.length - length];
        System.arraycopy(array, length, newArray, 0, array.length - length);
        return newArray;
    }

    public static boolean parseBoolean(String value) {
        return value != null && (value.equalsIgnoreCase("true") || value.equals("1"));
    }

    /**
     * 寻找 {@code search} 在 {@code array} 中存在的位置, 不存在返回 -1
     *
     * @param array  数组
     * @param search 待寻找子数组
     * @return {@code search} 在 {@code array} 中存在的位置, 不存在返回 -1
     */
    public static int arraySearch(byte[] array, byte[] search) {
        if (search.length == 0) {
            return -1;
        }

        int found = 0;
        loop:
        while (true) {
            found = binarySearch(array, search[0], found);
            if (found < 0 || found > array.length) {
                return -1;
            }
            for (int i = 1; i < search.length; i++) {
                try {
                    if (array[found + i] != search[i]) {
                        continue loop;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    return -1;
                }
            }
            break;
        }

        return found;
    }


    public static int binarySearch(byte[] a, byte key, int startPos) {

        if (startPos > a.length) {
            return -1;
        }

        if (startPos < 0) {
            return -1;
        }
        for (int i = startPos; i < a.length; i++) {
            if (a[i] == key) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取数组中间
     *
     * @param array    数组
     * @param location 起始取出位置
     * @param length   取出长度
     * @return 数组中间
     */
    public static byte[] arrayGetCenter(byte[] array, int location, int length) {
        if (length == 0) {
            return new byte[0];
        }
        byte[] result = new byte[length];
        try {
            System.arraycopy(array, location, result, 0, length);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean isZeroArray(byte[] array) {
        for (byte b : array) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将数组中所有 long 类型值转换为 String. 供使用 MPQ API
     */
    public static Object[] convertLongToString(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].getClass() == long.class || array[i] instanceof Long) {
                array[i] = String.valueOf(array[i]);
            }
        }

        return array;
    }
}
