package net.mamoe.jpre.plugin;

import com.google.gson.Gson;
import net.mamoe.jpre.Frame;
import net.mamoe.jpre.event.*;
import net.mamoe.jpre.exception.EventException;
import net.mamoe.jpre.exception.PluginException;
import net.mamoe.jpre.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * 插件管理器
 * 包含: 插件加载, 事件管理.
 * <p>
 * 事件管理:
 * {@link #registerEvents(Listener, Plugin)}: 注册事件
 * {@link #unregisterEvents(Plugin)}: 取消注册事件
 * {@link #callEvent(Event)}: 调用事件
 * <p>
 * 加载插件:
 * 注意! 运行环境启动时 (即MPQ启动时), 会自动搜索插件目录下所有 .jar 后缀的文件并尝试加载.
 * 一般来说, 不需要手动加载插件.
 * 本类中所有 Javadoc 的 "文件名" 都是绝对路径 (完整路径)
 * ===================================================================
 * 请注意加载时的错误捕获!
 * <p>
 * 手动加载插件方法:
 * 1. {@link #loadPluginDescription(String)}: 加载插件信息
 * 2. {@link #loadPlugin(String)}: 加载插件
 * 3. {@code {@link #getPlugin(String)}.enable() }: 启动插件
 *
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class PluginManager {
    /* Popular methods */

	/**
	 * 调用(触发) 事件.
	 *
	 * @param event 事件
	 * @return 事件返回值
	 */
	public int callEvent(final Event event) {
		HandlerList list = getHandlerList(event.getClass());
		if (list != null && list.size() != 0) {

			for (EventPriority i : EventPriority.PRIORITIES) {
				try {
					list.getAll().stream().filter(handler -> handler.getPriority() == i).forEach(handler -> handler.execute(handler.getListener(), event));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			event.close();
		}

		return event.getResultStatus();
	}

	public File getPluginDataFolder() {
		return pluginDataFolder;
	}

	/* Constructor */

	public PluginManager(Frame frame) {
		this.frame = frame;
		pluginDataFolder = new File(getFrame().getDataFolder() + File.separator + "plugins" + File.separator);
		loadPlugins();

		enablePlugins();
	}


	/* Declarations */

	private final Frame frame;

	public Frame getFrame() {
		return frame;
	}

	private final Map<Plugin, List<Handler>> listeners = new HashMap<>();
	private final HashSet<Plugin> plugins = new HashSet<>();
	private final Map<String, PluginDescription> descriptions = new HashMap<>();

	private final File pluginDataFolder;

	/* Plugin manager */

	/**
	 * 获取插件信息列表 (从 plugin.json 读取的信息)
	 *
	 * @return 插件信息列表
	 */
	public Map<String, PluginDescription> getDescriptions() {
		return descriptions;
	}

	/**
	 * 根据插件文件名/名称寻找插件
	 *
	 * @param name 插件 Jar 路径/名称
	 * @return 插件
	 */
	public Plugin getPlugin(String name) {
		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(name) ||
					plugin.getFileName().equals(name)) {
				return plugin;
			}
		}

		return null;
	}

	/**
	 * 从磁盘读取插件
	 */
	public void loadPlugins() throws PluginException {
		File[] list = listPlugins();
		if (list == null) {
			return;
		}
		for (File file : list) {
			try {
				loadPlugin(new JarFile(file));
			} catch (IOException e) {
				throw new PluginException("could not load plugin", e);
			}
		}
	}

	/**
	 * 重新加载插件
	 *
	 * @param reloadFromDisk 从磁盘中重新读取插件. 可用于添加插件
	 */
	public void reloadPlugins(boolean reloadFromDisk) throws PluginException {
		disablePlugins();
		if (reloadFromDisk) {
			plugins.clear();
			loadPlugins();
		}
	}

	/**
	 * 获取 plugins 目录下所有 .jar 为后缀的文件
	 *
	 * @return plugins 目录下所有 .jar 为后缀的文件
	 */
	public File[] listPlugins() {
		return pluginDataFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jar"));
	}

	/**
	 * 加载一个插件
	 *
	 * @param file 插件 Jar 路径
	 * @return 是否成功
	 */
	public boolean loadPlugin(String file) throws PluginException {
		try {
			loadPlugin(new JarFile(file));
			Plugin plugin = getPlugin(file);
			return plugin != null;
		} catch (IOException e) {
			throw new PluginException("could not load plugin", e);
		}
	}

	/**
	 * 加载一个插件
	 *
	 * @param file 插件 Jar 文件
	 * @return 是否成功
	 */
	public void loadPlugin(JarFile file) throws PluginException {
		PluginDescription description = descriptions.get(file.getName());
		if (description == null) {
			description = loadPluginDescription(file);
		}

		if (description.getMainClass().isEmpty()) {
			throw new PluginException("could not load plugin " + description.getName(), "plugin description in file " + file.getName() + " is empty");
		}
		if (description.getMainClass().startsWith("net.mamoe.jpre")) {
			throw new PluginException("could not load plugin " + description.getName(), "main class " + description.getMainClass() + " must not be started " +
					"with net.mamoe.jpre");
		}

		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(description.getName())) {
				return;
			}
		}

		Class<?> mainClass;
		try {
			Utils.loadClass(new File(file.getName()), (URLClassLoader) this.getClass().getClassLoader());
			mainClass = Class.forName(description.getMainClass());
		} catch (Exception e) {
			throw new PluginException("could not load plugin " + description.getName(), "main class " + description.getMainClass() + " not found", e);
		}

		if (!Plugin.class.isAssignableFrom(mainClass)) {
			throw new PluginException("could not load plugin " + description.getName(), "main class " + mainClass.getCanonicalName() + " is not " +
					"assignable from " + Plugin.class.getCanonicalName());
		}

		Plugin plugin;
		try {
			Constructor<?> constructor = mainClass.getConstructor();
			constructor.setAccessible(true);
			plugin = (Plugin) constructor.newInstance();
		} catch (Exception e) {
			throw new PluginException("could not load plugin " + description.getName(), "could not create instance of main class " + mainClass.getCanonicalName(), e);
		}

		plugin.setPluginManager(this);
		plugins.add(plugin);
		plugin.setPluginDescription(description);
		plugin.onLoad();

		System.out.println("[Plugin] " + plugin.getName() + " loaded!");

	}

	/**
	 * 获取已加载的插件列表 (包含未启用的插件)
	 *
	 * @return 插件列表
	 */
	public HashSet<Plugin> getPlugins() {
		return plugins;
	}

	/**
	 * 获取插件信息
	 *
	 * @param file 插件 Jar 路径
	 * @return 已加载返回插件信息, 若未加载返回 null. 未加载时可以使用 {@link #loadPluginDescription(JarFile)} 来加载
	 */
	public PluginDescription matchPluginDescription(String file) {
		return descriptions.get(file);
	}

	/**
	 * 加载插件信息
	 *
	 * @param file 插件 Jar 路径
	 * @return 插件信息
	 */
	public PluginDescription loadPluginDescription(String file) throws PluginException {
		try {
			return loadPluginDescription(new JarFile(file));
		} catch (Exception e) { //NPE, IOException...
			throw new PluginException("could not load plugin description in plugin file " + file, e);
		}
	}

	/**
	 * 加载插件信息
	 *
	 * @param file 插件 Jar 文件
	 * @return 插件信息
	 */
	public PluginDescription loadPluginDescription(JarFile file) throws PluginException {
		PluginDescription description = getDescription(file);
		if (description == null) {
			throw new PluginException("could not load plugin description in plugin file " + file.getName());
		}

		descriptions.put(file.getName(), description);
		return description;
	}

	/**
	 * 启动所有插件
	 */
	public void enablePlugins() {
		plugins.stream().filter(Plugin::isDisabled).forEach(Plugin::enable);
	}

	/**
	 * 关闭所有插件
	 */
	public void disablePlugins() {
		unregisterAllEvents();
		plugins.stream().filter(Plugin::isEnabled).forEach(Plugin::enable);
	}

	/**
	 * 获取 jar 包中的资源文件
	 *
	 * @param file     jar 包
	 * @param resource 资源文件名
	 * @return 读取流
	 */
	public InputStream getResourceFile(JarFile file, String resource) {
		try {

			ZipEntry entry = file.getEntry("resources/" + resource);
			if (entry == null) {
				entry = file.getEntry(resource);
				if (entry == null) {
					entry = file.getEntry("resource/" + resource);
					if (entry == null) {
						return null;
					}
				}
			}

			return file.getInputStream(entry);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取 Jar 包内的插件信息 (PluginDescription)  [cq.json/plugin.json/jpre.json]
	 * 本方法只会读取插件信息, 不会添加到插件信息列表中.
	 * 若要添加到插件列表, 请使用 {@link #loadPluginDescription(String)}
	 *
	 * @param file 文件
	 * @return PluginDescription
	 */
	public PluginDescription getDescription(JarFile file) {
		try {
			ZipEntry entry = file.getEntry("cq.json");
			if (entry == null) {
				entry = file.getEntry("plugin.json");
				if (entry == null) {
					entry = file.getEntry("jpre.json");
					if (entry == null) {
						return null;
					}
				}
			}

			return new Gson().fromJson(Utils.readFile(file.getInputStream(entry)), PluginDescription.class).setFileName(file.getName());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/* Event manager */

	/**
	 * 注册事件监听器
	 * <p>
	 * 插件必须已经开启 ({@link Plugin#isEnabled()}) 才能注册事件. 否则本方法将会抛出异常 {@link EventException}
	 * <p>
	 * 参数 {@code listener} 中的所有符合规范的事件处理器都会被注册
	 * 规范即为: 有一个参数, 并且参数的类型是任何一个 Event. 返回值类型, 方法是否公共都不影响.
	 * 若事件处理器带有注释 {@link Deprecated}, 将不会被注册
	 * <p>
	 * 注册监听器例子:
	 * <p>
	 * <pre>
	 *
	 * import ...;
	 * public class MainClass extends JavaPlugin{
	 *     public void onEnable(){
	 *         this.registerEvents(new MySimpleListener(), this);
	 *     }
	 * }
	 *
	 * class MySimpleListener implements Listener{
	 *     @code @EventListener
	 *     public void onMessage(GroupMessageEvent event){
	 *         //...
	 *     }
	 * }
	 * </pre>
	 *
	 * @param listener 事件监听器
	 * @param plugin   插件
	 * @return 成功注册的监听器数量
	 * @throws EventException {@code plugin} 未启用 (enable) 时
	 * @see #registerEvent(Listener, Plugin, Class, Handler) 会循环调用该方法
	 */
	@SuppressWarnings("unchecked")
	public int registerEvents(Listener listener, Plugin plugin) throws PluginException {
		if (!plugin.isEnabled()) {
			throw new PluginException("plugin " + plugin.getName() + " tried to register events while not enabled");
		}

		int count = 0;

		HashSet<Method> methods = new HashSet<>();
		Collections.addAll(methods, listener.getClass().getDeclaredMethods());
		Collections.addAll(methods, listener.getClass().getMethods());
		for (Method method : methods) {
			if (method.getAnnotation(Deprecated.class) != null) {
				continue;
			}

			if (method.getAnnotation(EventHandler.class) == null) {
				continue;
			}

			Class<?> clazz; // TODO: 2017/5/22 !!! 使用 EventHandler 替换 method
			if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(clazz = method.getParameterTypes()[0])) {
				throw new PluginException("plugin " + plugin.getName() + " tried to register a event handler which is invalid");
			}

			MethodHandler handler = new MethodHandler(plugin, listener, method, (Class<Event>) clazz);
			registerEvent(listener, plugin, (Class<Event>) clazz, handler);
			count++;
		}
		return count;
	}

	/**
	 * 注册单个事件监听器
	 *
	 * @param listener 事件监听器
	 * @param plugin   插件
	 * @param event    需要监听的事件
	 * @param handler  事件处理器
	 * @throws EventException 当 {@code plugin} 未启用 (enable) 时
	 */
	public void registerEvent(Listener listener, Plugin plugin, Class<Event> event, Handler handler) throws PluginException {
		if (!plugin.isEnabled()) {
			throw new PluginException("plugin " + plugin.getName() + " tried to register event listener while not enabled");
		}

		HandlerList handlerList = getHandlerList(event);
		if (handlerList == null) {
			throw new PluginException("plugin " + plugin.getName() + " tried to register event listener while event has not handler list");
		}

		List<Handler> list = listeners.getOrDefault(plugin, new ArrayList<>());
		list.add(handler);
		listeners.put(plugin, list);
		handlerList.add(handler);
	}

	/**
	 * 取消该插件已注册的所有事件监听器
	 *
	 * @param plugin 插件
	 */
	public void unregisterEvents(Plugin plugin) {
		List<Handler> remove = new ArrayList<>();
		for (List<Handler> handlers : listeners.values()) {
			for (Handler handler : handlers) {
				if (handler.getPlugin().equals(plugin)) {
					remove.add(handler);
				}
			}
			handlers.removeAll(remove);
		}
	}

	/**
	 * 取消该事件监听器已注册的所有事件处理器
	 *
	 * @param listener 事件监听器
	 */
	public void unregisterEvents(Listener listener) {
		List<Handler> remove = new ArrayList<>();
		for (List<Handler> handlers : listeners.values()) {
			for (Handler handler : handlers) {
				if (handler.getListener().equals(listener)) {
					remove.add(handler);
				}
			}
			handlers.removeAll(remove);
		}
	}


	/* Private methods */

	// TODO: 2017/4/11 Restart server
	private void unregisterAllEvents() {
		listeners.clear();
	}

	private static HandlerList getHandlerList(Class<?> eventClass) {
		try {
			Method method = eventClass.getDeclaredMethod("getHandlers");
			method.setAccessible(true);
			return (HandlerList) method.invoke(null);
		} catch (Exception e) {
			return null;
		}
	}
}
