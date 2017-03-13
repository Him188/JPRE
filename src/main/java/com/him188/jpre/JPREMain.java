package com.him188.jpre;

import com.him188.jpre.event.action.replay.ReplayGroupMessageEvent;
import com.him188.jpre.event.action.replay.ReplayPrivateMessageEvent;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.command.CommandManager;
import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.action.replay.ReplayDiscussMessageEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.exception.PluginLoadException;
import com.him188.jpre.log.LogManager;
import com.him188.jpre.log.logger.SystemLogger;
import com.him188.jpre.plugin.JavaPlugin;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Vector;

/**
 * Java 插件加载器主类
 * 基本上全是给酷Q的接口
 * <p>
 * 如果你想开发插件. 请查看 {@link Plugin}
 *
 * @author Him188
 * @see PluginManager 真正的插件管理器
 */
public final class JPREMain {
	public static final String VERSION_TYPE = "BETA";
	public static final String VERSION = "1.0.0";

	// TODO: 2017/3/4  现在只能单线程执行, 更新支持多线程
	protected static final List<Object> commandResults = new Vector<>();
	public static int CQ_API;
	public static String dataFolder;
	public static SystemCoolQCaller caller;
	public static SystemLogger logger;
	private static boolean running = true;

	public static void main(String[] args) {
		System.out.println("CoolQ JavaPluginRuntimeEnvironment");
		System.out.println("Version: " + VERSION_TYPE + ", v" + VERSION);
		System.out.println("Author: Him188 & LamGC");
		System.out.println();
		System.out.println("Welcome to use JPRE, you can put this jar into CoolQ\\app\\net.coding.lamgc.coolq.jpre\\bin");
		//System.out.println();
		//System.out.println("Starting tests...");
		//test();
	}

	public static void test() {
		JPREMain.init(9, "E:\\MEGA\\IDEAPojects\\CQ-JPRE\\out\\test\\CQ-JPRE");

		new Thread(() -> {
			while (running) {
				System.out.println(JPREMain.getLog());
			}
		}).start();

		new Thread(() -> {
			while (running) {
				System.out.println(JPREMain.getCommand());
				JPREMain.setCommandResult("1");
			}
		}).start();

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		JPREMain.setAuthCode(97);

		JPREMain.getLogger().info("test", "this is a message");
		JPREMain.getCaller().sendLike(1040400290L, 10);

		//System.out.println(JPREMain.loadPluginDescription(PLUGIN_NAME));
		//System.out.println(PluginManager.matchPluginDescription(PLUGIN_NAME).getMainClass());

		//JPREMain.loadPlugin(PLUGIN_NAME);
		//JPREMain.enablePlugin(PLUGIN_NAME);

		//GroupMessageEvent event = new GroupMessageEvent(GroupMessageEvent.TYPE_GROUP, 0, 0, 1040400290L, "", "!添加黑名单", 0);
		//System.out.println(JPREMain.callEvent(event));

		running = false;
		System.exit(0); //succeed
	}

	/**
	 * 初始化插件环境
	 *
	 * @param CQ_API     酷 Q API版本
	 * @param dataFolder 配置目录
	 */
	@SuppressWarnings("SameParameterValue")
	public static void init(int CQ_API, String dataFolder) {
		JPREMain.CQ_API = CQ_API;
		JPREMain.dataFolder = dataFolder;
		caller = new SystemCoolQCaller();
		logger = new SystemLogger();
	}

	public static SystemLogger getLogger() {
		return logger;
	}

	public static int getCqApi() {
		return CQ_API;
	}

	public static String getDataFolder() {
		return dataFolder;
	}

	/**
	 * 不建议使用该方法. 请使用 {@link JavaPlugin}({@link CoolQCaller}) 下的方法.
	 *
	 * @return 酷Q调用器
	 */
	public static SystemCoolQCaller getCaller() {
		return caller;
	}

	protected static List<Object> getCommandResults() {
		return commandResults;
	}

	/**
	 * 获取酷Q执行完方法后的返回值
	 *
	 * @return 返回值
	 */
	@SuppressWarnings("StatementWithEmptyBody")
	public static Object getCommandResult() {
		while (getCommandResults().isEmpty()) ;
		return getCommandResults().remove(0);
	}

	/**
	 * 酷Q传回返回值
	 *
	 * @param s 方法返回值
	 */
	public static void setCommandResult(String s) {
		commandResults.add(s);
	}

	/**
	 * 酷Q传回返回值
	 *
	 * @param s 方法返回值
	 */
	public static void setCommandResult(int s) {
		commandResults.add(s);
	}

	/**
	 * 酷Q获取方法
	 */
	public static String getCommand() {
		synchronized (CommandManager.class) {//当正在等待指令返回值时, 指令不传达
			return CommandManager.getCommand().toString();
		}
	}

	@SuppressWarnings({"ConstantConditions"})
	public static String getLog() {
		try {
			return LogManager.getFirst().toString();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static boolean callEvent(Object... args) {
		if (args.length == 0) {
			return false;
		}

		try {
			Object[] newArgs = new Object[args.length - 1];
			System.arraycopy(args, 1, newArgs, 0, args.length - 1);
			return callEvent(Integer.parseInt(args[0].toString()), newArgs);
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 触发事件.
	 *
	 * @param type 事件 type
	 * @param args 参数
	 */
	public static boolean callEvent(int type, Object... args) {
		if (type == EventTypes.DISABLE) {
			PluginManager.disablePlugins();
			System.exit(0);
			return false;
		}

		Event event = Event.matchEvent(type, args);
		return event != null && callEvent(event);
	}

	/**
	 * 触发事件.
	 *
	 * @param event 事件
	 *
	 * @return 是否拦截
	 *
	 * @see PluginManager#callEvent(Event)
	 */
	public static boolean callEvent(Event event) {
		boolean cancelled = PluginManager.callEvent(event);
		if (cancelled) {
			return true;
		}

		try {
			Method method = event.getClass().getMethod("getEventType");
			method.setAccessible(true);
			switch ((int) method.invoke(null)) {
				case EventTypes.DISCUSS_MESSAGE:
					if (event instanceof DiscussMessageEvent) {
						if (((DiscussMessageEvent) event).getRepeat() == null || ((DiscussMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplayDiscussMessageEvent ev = new ReplayDiscussMessageEvent((DiscussMessageEvent) event);
						JPREMain.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}

						JPREMain.getCaller().sendDiscussMessage(ev.getDiscuss(), ev.getRepeat());
					}
					return false;
				case EventTypes.GROUP_MESSAGE:
					if (event instanceof GroupMessageEvent) {
						if (((GroupMessageEvent) event).getRepeat() == null || ((GroupMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplayGroupMessageEvent ev = new ReplayGroupMessageEvent((GroupMessageEvent) event);
						JPREMain.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}

						JPREMain.getCaller().sendGroupMessage(ev.getGroup(), ev.getRepeat());
					}
					return false;
				case EventTypes.PRIVATE_MESSAGE:
					if (event instanceof PrivateMessageEvent) {
						if (((PrivateMessageEvent) event).getRepeat() == null || ((PrivateMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplayPrivateMessageEvent ev = new ReplayPrivateMessageEvent((PrivateMessageEvent) event);
						JPREMain.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}

						JPREMain.getCaller().sendPrivateMessage(ev.getQQ(), ev.getRepeat());
					}
					return false;
				case EventTypes.REQUEST_FRIEND_ADD:
					if (event instanceof AddFriendRequestEvent) {
						JPREMain.getCaller().friendAnswerAddRequest(((AddFriendRequestEvent) event).getResponseFlag(), ((AddFriendRequestEvent) event).isAccept(), ((AddFriendRequestEvent) event).getNickIfAccept());
					}
					return false;
				case EventTypes.REQUEST_GROUP_ADD:
					if (event instanceof AddGroupRequestEvent) {
						JPREMain.getCaller().groupAnswerJoinRequest(((AddGroupRequestEvent) event).getResponseFlag(), ((AddGroupRequestEvent) event).getType(), ((AddGroupRequestEvent) event).isAccept(), ((AddGroupRequestEvent) event).reasonIfRefused);
					}
					return false;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			JPREMain.getLogger().exception(e);
			return false;
		}

		return false;
	}

	/*
	//0
	public static boolean callEvent(int type) {
		return callEvent(type, new Object[]{});
	}

	//1
	public static boolean callEvent(int type, Object arg1) {
		return callEvent(type, new Object[]{arg1});
	}

	//2
	public static boolean callEvent(int type, Object arg1, Object arg2) {
		return callEvent(type, new Object[]{arg1, arg2});
	}

	//3
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3) {
		return callEvent(type, new Object[]{arg1, arg2, arg3});
	}

	//4
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3, Object arg4) {
		return callEvent(type, new Object[]{arg1, arg2, arg3, arg4});
	}

	//5
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
		return callEvent(type, new Object[]{arg1, arg2, arg3, arg4, arg5});
	}

	//6
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6) {
		return callEvent(type, new Object[]{arg1, arg2, arg3, arg4, arg5, arg6});
	}

	//7
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7) {
		return callEvent(type, new Object[]{arg1, arg2, arg3, arg4, arg5, arg6, arg7});
	}

	//8
	public static boolean callEvent(int type, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6, Object arg7, Object arg8) {
		return callEvent(type, new Object[]{arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8});
	}
	*/

	public static String[] getLoadedPluginList() {
		String[] names = new String[PluginManager.getPlugins().size()];
		int i = 0;
		for (Plugin plugin : PluginManager.getPlugins()) {
			names[i++] = plugin.getName();
		}
		return names;
	}


	//加载一个插件:
	public static boolean loadPluginDescription(String fileName) {
		try {
			return PluginManager.loadPluginDescription(fileName) != null;
		} catch (Throwable e) {
			JPREMain.getLogger().exception(e);
			return false;
		}
	}

	public static String getPluginName(String fileName) {
		return PluginManager.matchPluginDescription(fileName).getName();
	}

	public static int getPluginAPI(String fileName) {
		return PluginManager.matchPluginDescription(fileName).getAPIVersion();
	}

	public static String getPluginVersion(String fileName) {
		return PluginManager.matchPluginDescription(fileName).getVersion();
	}

	public static String getPluginAuthor(String fileName) {
		return PluginManager.matchPluginDescription(fileName).getAuthor();
	}

	public static String getPluginDescription(String fileName) {
		return PluginManager.matchPluginDescription(fileName).getDescription();
	}

	public static boolean loadPlugin(String fileName) throws PluginLoadException {
		return PluginManager.loadPlugin(fileName);
	}

	public static void setAuthCode(int authCode) {
		try {
			PluginManager.getPlugins().forEach(plugin -> plugin.initialize(authCode));
			caller.setAuthCode(authCode);
		} catch (Throwable e) {
			JPREMain.getLogger().exception(e);
		}

	}

	public static boolean enablePlugin(String name) {
		try {
			Plugin plugin = PluginManager.getPlugin(name);
			if (plugin == null) {
				return false;
			}
			plugin.enable();
			return true;
		} catch (Throwable e) {
			JPREMain.getLogger().exception(e);
			return false;
		}
	}

	public static boolean disablePlugin(String name) {
		try {
			Plugin plugin = PluginManager.getPlugin(name);
			if (plugin == null) {
				return false;
			}
			plugin.disable();
			return true;
		} catch (Throwable e) {
			JPREMain.getLogger().exception(e);
			return false;
		}
	}

	public static boolean isPluginEnabled(String name) {
		Plugin plugin = PluginManager.getPlugin(name);
		return plugin != null && plugin.isEnabled();
	}
}
