package com.him188.jpre;

import com.google.gson.Gson;
import com.him188.jpre.event.*;
import com.him188.jpre.exception.PluginEventException;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.plugin.PluginDescription;
import com.him188.jpre.exception.PluginLoadException;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
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
 * {@link JPREMain#callEvent(Event)}: 调用事件
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
 * @author Him188
 */
public final class PluginManager {
	private static Map<Plugin, List<Handler>> listeners = new HashMap<>();
	private static List<Plugin> plugins = new ArrayList<>();
	private static Map<String, PluginDescription> descriptions = new HashMap<>();

	/**
	 * 获取插件信息列表 (从 plugin.json 读取的信息)
	 *
	 * @return 插件信息列表
	 */
	public static Map<String, PluginDescription> getDescriptions() {
		return descriptions;
	}

	/**
	 * 根据插件文件名/名称寻找插件
	 *
	 * @param name 插件 Jar 路径/名称
	 *
	 * @return 插件
	 */
	public static Plugin getPlugin(String name) {
		for (Plugin plugin : plugins) {
			System.out.println(plugin.toString());
			if (plugin.getName()
					.equals(name) ||
					plugin.getFileName().equals(name)) {
				return plugin;
			}
		}

		return null;
	}

	/**
	 * 加载一个插件
	 *
	 * @param file 插件 Jar 路径
	 *
	 * @return 是否成功
	 */
	public static boolean loadPlugin(String file) throws PluginLoadException {

		try {
			if (!loadPlugin(new JarFile(file))) {
				return false;
			}
			Plugin plugin = getPlugin(file);
			return plugin != null;
		} catch (IOException e) {
			return false;
		} catch (PluginLoadException e) {
			throw new PluginLoadException(e.getMessage());
		}
	}

	/**
	 * 加载一个插件
	 *
	 * @param file 插件 Jar 文件
	 *
	 * @return 是否成功
	 */
	public static boolean loadPlugin(JarFile file) throws PluginLoadException {
		PluginDescription description = descriptions.get(file.getName());
		if (description.getMainClass().isEmpty()) {
			throw new PluginLoadException("Could not load plugin description: " + description.getName());
		}
		if (description.getMainClass().startsWith("com.him188.jpre.")) {
			throw new PluginLoadException("Could not load main class " + description.getMainClass() + ". Caused by plugin " + description.getName());
		}

		for (Plugin plugin : plugins) {
			if (plugin.getName().equals(description.name)) {
				return true;
			}
		}

		Class<?> mainClass;
		try {
			Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			method.setAccessible(true);
			URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			method.invoke(classLoader, new File(file.getName()).toURI().toURL());

			mainClass = Class.forName(description.getMainClass());
		} catch (Throwable e) {
			throw new PluginLoadException("Could not found main class " + description.getMainClass() + " Caused by plugin " + description.getName(), e);
		}

		if (!Plugin.class.isAssignableFrom(mainClass)) {
			throw new PluginLoadException("The main class is not assignable from Plugin: " + mainClass.getName());
		}

		try {
			Constructor<?> constructor = mainClass.getConstructor();
			constructor.setAccessible(true);
			Plugin plugin = (Plugin) constructor.newInstance();
			plugins.add(plugin);
			plugin.setPluginDescription(description);
			plugin.onLoad();

			System.out.println("[Plugin] " + plugin.getName() + " loaded!");
			return true;
		} catch (Throwable e) {
			throw new PluginLoadException("Could not create instance of " + description.getName(), e);
		}
	}

	/**
	 * 获取已加载的插件列表 (包含未启用的插件)
	 *
	 * @return 插件列表
	 */
	public static List<Plugin> getPlugins() {
		return plugins;
	}

	/**
	 * 获取插件信息
	 *
	 * @param file 插件 Jar 路径
	 *
	 * @return 已加载返回插件信息, 若未加载返回 null. 未加载时可以使用 {@link #loadPluginDescription(JarFile)} 来加载
	 */
	public static PluginDescription matchPluginDescription(String file) {
		return descriptions.get(file);
	}

	/**
	 * 加载插件信息
	 *
	 * @param file 插件 Jar 路径
	 *
	 * @return 插件信息
	 */
	public static PluginDescription loadPluginDescription(String file) throws PluginLoadException {
		try {
			System.out.println(file);
			return loadPluginDescription(new JarFile(file));
		} catch (IOException e) {
			throw new PluginLoadException("Could not load file: " + file);
		}
	}

	/**
	 * 加载插件信息
	 *
	 * @param file 插件 Jar 文件
	 *
	 * @return 插件信息
	 */
	public static PluginDescription loadPluginDescription(JarFile file) throws PluginLoadException {
		PluginDescription description = getDescription(file);
		if (description == null) {
			throw new PluginLoadException("Could not load plugin description: " + file.getName());
		}

		descriptions.put(file.getName(), description);
		return description;
	}

	/**
	 * 启动所有插件
	 */
	public static void enablePlugins() {
		plugins.stream().filter(Plugin::isDisabled).forEach(Plugin::enable);
	}

	/**
	 * 关闭所有插件
	 */
	public static void disablePlugins() {
		plugins.stream().filter(Plugin::isEnabled).forEach(Plugin::enable);
	}

	/**
	 * 获取 jar 包中的资源文件
	 *
	 * @param file     jar 包
	 * @param resource 资源文件名
	 *
	 * @return 读取流
	 */
	public static InputStream getResourceFile(JarFile file, String resource) {
		try {

			ZipEntry entry = file.getEntry("resources/" + resource);
			if (entry == null) {
				entry = file.getEntry(resource);
				if (entry == null) {
					return null;
				}
			}

			return file.getInputStream(entry);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * 获取 Jar 包内的插件信息 (PluginDescription)  [cq.json/plugin.json/jpre.json]
	 * 本方法只会读取插件信息, 不会添加到插件信息列表中.
	 * 若要添加到插件列表, 请使用 {@link #loadPluginDescription(String)}
	 *
	 * @param file 文件
	 *
	 * @return PluginDescription
	 */
	public static PluginDescription getDescription(JarFile file) {
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

			System.out.println("\n\n\n\n");
			System.out.println(file.getName());
			return new Gson().fromJson(Utils.readFile(file.getInputStream(entry)), PluginDescription.class).setFileName(file.getName());
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 注册事件监听器
	 * <p>
	 * 插件必须已经开启 (enable) 才能注册事件. 否则本方法将会抛出错误 {@link PluginEventException}
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
	 * public class mainClass extends JavaPlugin{
	 *     public void onEnable(){
	 *         PluginManager.registerEvents(new MySimpleListener(), this);
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
	 *
	 * @throws PluginEventException {@code plugin} 未启用 (enable) 时
	 * @see #registerEvent(Listener, Plugin, Class, Method) 会循环调用该方法
	 */
	@SuppressWarnings("unchecked")
	public static void registerEvents(Listener listener, Plugin plugin) throws PluginEventException {
		try {
			if (!plugin.isEnabled()) {
				throw new PluginEventException("Plugin " + plugin.getName() + " tried to register event while not enabled");
			}

			Collection<Method> methods = new HashSet<>();
			Collections.addAll(methods, listener.getClass().getDeclaredMethods());
			Collections.addAll(methods, listener.getClass().getMethods());
			for (Method method : methods) {
				EventHandler handler;
				try {
					if (method.getAnnotation(Deprecated.class) != null) {
						continue;
					}

					handler = method.getAnnotation(EventHandler.class);
				} catch (ClassCastException e) {
					continue;
				}
				if (handler == null) {
					continue;
				}

				Class<?> clazz;
				if (method.getParameterTypes().length != 1 || !Event.class.isAssignableFrom(clazz = method.getParameterTypes()[0])) {
					continue;
				}

				registerEvent(listener, plugin, (Class<Event>) clazz, method);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册单个事件监听器
	 *
	 * @param listener 事件监听器
	 * @param plugin   插件
	 * @param event    需要监听的事件
	 * @param method   事件处理器
	 *
	 * @return 是否成功
	 *+
	 * @throws PluginEventException 当 {@code plugin} 未启用 (enable) 时
	 * @throws PluginEventException 当 {@code method} 没有注释 {@link EventHandler} 时
	 */
	public static boolean registerEvent(Listener listener, Plugin plugin, Class<Event> event, Method method) throws PluginEventException {
		try {
			if (!plugin.isEnabled()) {
				throw new PluginEventException("Plugin " + plugin.getName() + " tried to register event while not enabled");
			}

			try {
				method.getAnnotation(EventHandler.class);
			} catch (ClassCastException e) {
				throw new PluginEventException("Plugin " + plugin.getName() + " tried to register event while method is not annotated EventHandler");
			}

			HandlerList handlerList = getHandlerList(event);
			if (handlerList == null) {
				return false;
			}
			List<Handler> list = listeners.getOrDefault(plugin, new ArrayList<>());
			MethodHandler handler = new MethodHandler(plugin, listener, method, event);
			list.add(handler);
			listeners.put(plugin, list);
			handlerList.add(handler);
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	protected static void unregisterAllEvents() {
		listeners.clear();
	}

	/**
	 * 取消该插件已注册的所有事件监听器
	 *
	 * @param plugin 插件
	 *
	 * @return 是否成功
	 */
	public static boolean unregisterEvents(Plugin plugin) {
		List<Handler> remove = new ArrayList<>();
		for (List<Handler> handlers : listeners.values()) {
			for (Handler handler : handlers) {
				if (handler.getPlugin().equals(plugin)) {
					remove.add(handler);
				}
			}
			handlers.removeAll(remove);
		}

		return true;
	}

	/**
	 * 取消该事件监听器已注册的所有事件处理器
	 *
	 * @param listener 事件监听器
	 *
	 * @return 是否成功
	 */
	public static boolean unregisterEvents(Listener listener) {
		List<Handler> remove = new ArrayList<>();
		for (List<Handler> handlers : listeners.values()) {
			for (Handler handler : handlers) {
				if (handler.getListener().equals(listener)) {
					remove.add(handler);
				}
			}
			handlers.removeAll(remove);
		}

		return true;
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

	/**
	 * 调用事件. 本方法仅运行环境内部使用,
	 * 插件请使用 {@link JPREMain#callEvent(Event)}
	 *
	 * @param event 事件
	 *
	 * @return 是否拦截
	 *
	 * @see JPREMain#callEvent(Event)
	 */
	protected static boolean callEvent(Event event) {
		//try {

			HandlerList list = getHandlerList(event.getClass());
			if (list == null) {
				return false;
			}

			if (list.size() == 0) {
				return false;
			}
			for (EventPriority i : EventPriority.PRIORITIES) {
				list.getAll().stream().filter(handler -> handler.getPriority() == i).forEach(handler -> handler.execute(handler.getListener(), event));
			}
			event.close();
			return event.isCancelled();
		//} catch (Throwable e) {
		//	e.printStackTrace();
		//	JPREMain.getLogger().error("EventError", e.getMessage());
		//	return false;
		//}
	}
}
