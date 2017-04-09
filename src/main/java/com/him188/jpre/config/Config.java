package com.him188.jpre.config;

import java.util.List;
import java.util.Map;

/**
 * 配置接口
 *
 * @author Him188
 */
abstract public class Config {
	protected String file;

	public Config(String file) {
		this.file = file;
	}

	/**
	 * 获取配置值
	 *
	 * @param key 键
	 *
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
	 *
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

	public int getInteger(String key) throws NumberFormatException {
		return Integer.parseInt(getString(key));
	}

	public String getString(String key) {
		return String.valueOf(get(key));
	}

	public long getLong(String key) throws NumberFormatException {
		return Long.parseLong(getString(key));
	}

	public byte getByte(String key) throws NumberFormatException {
		return Byte.parseByte(getString(key));
	}

	/**
	 * 获取逻辑值. 值为 "true" 时返回 true, 否则 false
	 *
	 * @param key 键
	 *
	 * @return 值
	 */
	public boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	public short getShort(String key) throws NumberFormatException {
		return Short.parseShort(getString(key));
	}

	@SuppressWarnings("unchecked")
	public List<String> getList(String key) {
		return (List<String>) get(key);
	}
}
