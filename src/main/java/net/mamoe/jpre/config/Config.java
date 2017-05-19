package net.mamoe.jpre.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配置接口
 *
 * @author Him188 @ JPRE Project */
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


    public char getChar(String key) {
        return getChar(key, (char) 0);
    }

    public char getChar(String key, char defaultValue) {
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
    public <T> List<T> getList(String key, Class<T> type) {
        return get(key, new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Long> getLongList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Long>() {
                {
                    ((List<Object>) value).forEach(value -> add(Long.parseLong(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Integer> getIntegerList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Integer>() {
                {
                    ((List<Object>) value).forEach(value -> add((int) Long.parseLong(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Double> getDoubleList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Double>() {
                {
                    ((List<Object>) value).forEach(value -> add(Double.parseDouble(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Float> getFloatList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Float>() {
                {
                    ((List<Object>) value).forEach(value -> add((float) Double.parseDouble(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Short> getShortList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Short>() {
                {
                    ((List<Object>) value).forEach(value -> add((short) Long.parseLong(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Byte> getByteList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Byte>() {
                {
                    ((List<Object>) value).forEach(value -> add(Byte.parseByte(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getStringList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<String>() {
                {
                    ((List<Object>) value).forEach(value -> add(value.toString()));
                }
            };
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Object> getObjectList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return (ArrayList<Object>) value;
        }

        return new ArrayList<>(0);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Boolean> getBooleanList(String key) {
        Object value = get(key);

        if (value instanceof List) {
            return new ArrayList<Boolean>() {
                {
                    ((List<Object>) value).forEach(value -> add(parseBoolean(value.toString())));
                }
            };
        }

        return new ArrayList<>(0);
    }


    private static boolean parseBoolean(String value) {
        if (Boolean.parseBoolean(value)) {
            return true;
        }

        try {
            long longValue = Long.parseLong(value);

            return longValue == 1L;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

