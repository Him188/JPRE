package com.him188.jpre;

import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.action.replay.ReplayDiscussMessageEvent;
import com.him188.jpre.event.action.replay.ReplayGroupMessageEvent;
import com.him188.jpre.event.action.replay.ReplayPrivateMessageEvent;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.exception.PluginLoadException;
import com.him188.jpre.log.logger.SystemLogger;
import com.him188.jpre.network.ConnectedClient;
import com.him188.jpre.network.Network;
import com.him188.jpre.network.NetworkPacketHandler;
import com.him188.jpre.network.packet.LoginResultPacket;
import com.him188.jpre.plugin.JavaPlugin;
import com.him188.jpre.plugin.Plugin;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import static com.him188.jpre.Utils.md5Encode;

/**
 * Java 插件加载器主类
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
	public static int CQ_API;
	public static String dataFolder;
	public static SystemCoolQCaller caller;
	public static SystemLogger logger;

	@SuppressWarnings("OctalInteger")
	public static final int DEFAULT_PORT = 0420; //不要问为什么, 我女票生日. 十进制 272

	private static String PASSWORD; //加密过

	public static String getPassword() {
		return PASSWORD;
	}

	public static void main(String[] args) throws ParseException, UnsupportedEncodingException, NoSuchAlgorithmException {
		printAbout();

		CommandLineParser parser = new DefaultParser();
		Options options = new Options();
		options.addOption("h", "help", false, "Print the usage information");
		options.addOption("p", "port", true, "Set the server port number");
		options.addOption("pwd", "password", true, "Set the password that client must verify after connecting to server");

		CommandLine commandLine = parser.parse(options, args);

		if (commandLine.hasOption("h")) {
			System.out.println("Help Message");
			System.exit(0);
		}

		int port = DEFAULT_PORT;
		if (commandLine.hasOption("p")) {
			try {
				port = Integer.parseInt(commandLine.getOptionValue("p"));
			} catch (NumberFormatException e) {
				System.out.println("The port you set is invalid. It must be a number in 1~65535");
				System.exit(0);
			}
		}
		System.out.println("Server port: " + port);

		if (commandLine.hasOption("pwd")) {
			PASSWORD = md5Encode(commandLine.getOptionValue("pwd"));
		}

		System.out.println("\n");
		startServer(port);
		System.out.println("\n");
		init(System.getProperty("usr.dir"));


		System.out.println("enter everything to send login done packet");
		new Scanner(System.in).next();
		LoginResultPacket packet = new LoginResultPacket(true);
		for (ConnectedClient connectedClient : NetworkPacketHandler.getClients()) {
			connectedClient.sendPacket(connectedClient.getLastCtx(), packet);
		}
		System.out.println("data packet sent.");
	}

	public static void printAbout() {
		System.out.println("CoolQ JavaPluginRuntimeEnvironment");
		System.out.println("Version: " + VERSION_TYPE + ", v" + VERSION);
		System.out.println("Author: Him188 & LamGC");
		System.out.println("GitHub: https://github.com/Him188/CQ-JPRE\n");
	}

	private static void startServer(final int port) {
		System.out.println("Starting server...");
		new Thread(() -> {
			try {
				Network.start(port);
			} catch (InterruptedException e) {
				System.out.println("Starting server failed. Could not open port " + port);
				System.exit(0);
			}
		}).start();
		System.out.println("JPRE server is listening port " + port);
	}

	/**
	 * 初始化插件环境
	 *
	 * @param dataFolder 配置目录
	 */
	@SuppressWarnings({"SameParameterValue", "ResultOfMethodCallIgnored"})
	private static void init(String dataFolder) {
		JPREMain.dataFolder = dataFolder;
		caller = new SystemCoolQCaller();
		logger = new SystemLogger();
		new File(System.getProperty("usr.dir") + File.pathSeparator + "plugins" + File.pathSeparator).mkdir();
	}

	public static void setCqApi(int cqApi) {
		CQ_API = cqApi;
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
