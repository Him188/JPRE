package net.mamoe.jpre.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置接口
 *
 * @author Him188
 */
@SuppressWarnings({"unused", "WeakerAccess"})
abstract public class Config {
    protected File file;

    @SuppressWarnings("WeakerAccess")
    public Config(File file) {
        this.file = file;
    }

    /**
     * 获取配置值
     *
     * @param key 键
     * @return 值
     */
    abstract public Object get(String key);

    abstract public void set(String key, Object value);

    abstract public void remove(String key);

    abstract public Map<String, Object> getAll();

    abstract public void setAll(Map<String, Object> map);

    abstract public void reload();

    abstract public void save();

    /**
     * 获取配置值. 当值为 {@code null} 时返回 {@code defaultValue}
     *
     * @param key          键
     * @param defaultValue 默认值
     * @param <T>          值类型
     * @return 值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        Object result = get(key);
        try {
            return result == null ? defaultValue : (T) result;
        } catch (Exception e) {
            return defaultValue;
        }
    }


    public int getInteger(String key) {
        return getInteger(key, 0);
    }

    public int getInteger(String key, int defaultValue) {
        return get(key, defaultValue);
    }


    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        return get(key, defaultValue);
    }


    public long getLong(String key) {
        return getLong(key, 0L);
    }

    public long getLong(String key, long defaultValue) {
        return get(key, defaultValue);
    }


    public byte getByte(String key) {
        return getByte(key, (byte) 0);
    }

    public byte getByte(String key, byte defaultValue) {
        return get(key, defaultValue);
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return get(key, defaultValue);
    }


    public short getShort(String key) {
        return getShort(key, (short) 0);
    }

    public short getShort(String key, short defaultValue) {
        return get(key, defaultValue);
    }


    public float getFloat(String key) {
        return getFloat(key, 0F);
    }

    public float getFloat(String key, float defaultValue) {
        return get(key, defaultValue);
    }


    public double getDouble(String key) {
        return getDouble(key, 0D);
    }

    public double getDouble(String key, double defaultValue) {
        return get(key, defaultValue);
    }


    @SuppressWarnings("unchecked")
    public List<String> getStringList(String key) {
        return get(key, new ArrayList<>());
    }
}
