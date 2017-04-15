package com.him188.jpre.config;

import com.google.gson.*;
import com.him188.jpre.Utils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

/**
 * Json 配置
 * 注意, 本类不会自动保存配置.
 *
 * @author Him188
 */
public class JsonConfig extends Config {
	private Map<String, Object> list;

	private JsonToMap converter;

	public JsonConfig(String file) {
		this(file, false);
	}

	/**
	 * 创建 Json 配置, 并从磁盘中读取
	 *
	 * @param file               文件名 (绝对路径)
	 * @param useSynchronization 是否使用线程同步. true, HashTable 方式存放数据; false, HashMap 方式存放数据 当本配置需要在多线程环境中使用时, 请填 true
	 */
	@SuppressWarnings("WeakerAccess")
	public JsonConfig(String file, boolean useSynchronization) {
		super(file);
		converter = new JsonToMap();
		converter.setSynchronized(useSynchronization);

		reload();
		list.forEach((k, v) -> {
			System.out.println(k);
			System.out.println(v);
		});
	}

	private static String readFile(String fileName) {
		try {
			return Utils.readFile(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public Object get(String key) {
		return list.get(key);
	}

	@Override
	public void set(String key, Object value) {
		list.put(key, value);
	}

	@Override
	public void remove(String key) {
		list.remove(key);
	}

	@Override
	public Map<String, Object> getAll() {
		return list;
	}

	@Override
	public void setAll(Map<String, Object> map) {
		list = map;
	}

	@Override
	public void reload() {
		try {
			//System.out.println(file);
			//noinspection ResultOfMethodCallIgnored
			new File(file).createNewFile();
		} catch (IOException ignored) {

		}
		list = converter.toMap(readFile(file));
	}

	@Override
	public void save() {
		try {
			Utils.writeFile(file, new JsonParser().parse(new Gson().toJson(list)).getAsJsonObject().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final class JsonToMap {
		private boolean useSynchronization = false;

		void setSynchronized(boolean b) {
			useSynchronization = b;
		}

		/**
		 * 获取JsonObject
		 *
		 * @param json JsonString
		 *
		 * @return JsonObject
		 */
		JsonObject parseJson(String json) {
			return new JsonParser().parse(json).getAsJsonObject();
		}

		/**
		 * 根据 json 字符串返回 Map 对象
		 *
		 * @param json JsonString
		 *
		 * @return Map
		 */
		Map<String, Object> toMap(String json) {
			return toMap(parseJson(json));
		}

		/**
		 * 将 JsonObject 转换成 Map-List 集合
		 *
		 * @param json JsonObject
		 *
		 * @return Map
		 */
		Map<String, Object> toMap(JsonObject json) {
			Map<String, Object> map = useSynchronization ? new Hashtable<>() : new HashMap<>();
			Set<Entry<String, JsonElement>> entrySet = json.entrySet();
			for (Entry<String, JsonElement> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof JsonArray)
					map.put(key, toList((JsonArray) value));
				else if (value instanceof JsonObject)
					map.put(key, toMap((JsonObject) value));
				else
					map.put(key, value);
			}
			return map;
		}

		/**
		 * 将 JsonArray 对象转换成 List 集合
		 *
		 * @param json JsonArray
		 *
		 * @return List
		 */
		List<Object> toList(JsonArray json) {
			List<Object> list = useSynchronization ? new Vector<>() : new ArrayList<>();
			for (int i = 0; i < json.size(); i++) {
				Object value = json.get(i);
				if (value instanceof JsonArray) {
					list.add(toList((JsonArray) value));
				} else if (value instanceof JsonObject) {
					list.add(toMap((JsonObject) value));
				} else {
					list.add(value);
				}
			}
			return list;
		}

	}
}
