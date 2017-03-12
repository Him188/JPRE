package net.coding.lamgc.coolq.jpre.plugin.config;

import net.coding.lamgc.coolq.jpre.JPREMain;
import net.coding.lamgc.coolq.jpre.Utils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Yaml 配置
 * 注意, 本类不会自动保存配置.
 *
 * @author Him188
 */
public class YamlConfig extends Config {
	private Map<String, Object> list;
	private boolean useSynchronization;

	public YamlConfig(String file) {
		this(file, false);
	}

	/**
	 * 创建 Yaml 配置, 并从磁盘中读取
	 *
	 * @param file               文件名 (绝对路径)
	 * @param useSynchronization 是否使用线程同步. true, HashTable 方式存放数据; false, HashMap 方式存放数据 当本配置需要在多线程环境中使用时, 请填 true
	 */
	public YamlConfig(String file, boolean useSynchronization) {
		super(file);
		this.useSynchronization = useSynchronization;
		reload();
	}


	@Override
	public Object get(String key) {
		return this.list.get(key);
	}

	@Override
	public void set(String key, Object value) {
		this.list.put(key, value);
	}

	@Override
	public void remove(String key) {
		this.list.remove(key);
	}

	@Override
	public Map<String, Object> getAll() {
		return this.list;
	}

	@Override
	public void setAll(Map<String, Object> map) {
		this.list = useSynchronization ? new Hashtable<>(map) : new HashMap<>(map);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void reload() {
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(dumperOptions);
		try {
			this.list = yaml.loadAs(Utils.readFile(file), Map.class);
		} catch (IOException e) {
			JPREMain.getLogger().exception(e);
		}

		if (this.list == null) {
			this.list = useSynchronization ? new Hashtable<>() : new HashMap<>();
		} else {
			this.list = useSynchronization ? new Hashtable<>(this.list) : new HashMap<>(this.list);
		}
	}

	@Override
	public void save() {
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
		Yaml yaml = new Yaml(dumperOptions);
		try {
			Utils.writeFile(file, yaml.dump(this.list));
		} catch (IOException e) {
			JPREMain.getLogger().exception(e);
		}
	}
}
