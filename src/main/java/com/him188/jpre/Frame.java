package com.him188.jpre;

import com.him188.jpre.event.Event;
import com.him188.jpre.event.EventTypes;
import com.him188.jpre.event.message.DiscussMessageEvent;
import com.him188.jpre.event.message.GroupMessageEvent;
import com.him188.jpre.event.message.PrivateMessageEvent;
import com.him188.jpre.event.reply.ReplyDiscussMessageEvent;
import com.him188.jpre.event.reply.ReplyGroupMessageEvent;
import com.him188.jpre.event.reply.ReplyPrivateMessageEvent;
import com.him188.jpre.event.request.AddFriendRequestEvent;
import com.him188.jpre.event.request.AddGroupRequestEvent;
import com.him188.jpre.exception.PluginLoadException;
import com.him188.jpre.network.Network;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.scheduler.Scheduler;

import java.io.File;

/**
 * @author Him188
 */
public final class Frame {
	private PluginManager pluginManager;
	private Scheduler scheduler;

	public PluginManager getPluginManager() {
		return pluginManager;
	}


	public String dataFolder;

	private boolean shutdown;

	String password; //加密过

	public String getPassword() {
		return password;
	}


	void startServer(final int port) {
		System.out.println("Starting server...");
		new Thread(() -> {
			try {
				Network.start(port); // TODO: 2017/4/11 non-static field
			} catch (InterruptedException e) {
				System.out.println("Starting server failed. Could not open port " + port);
				System.exit(0);
			}
		}).start();
		System.out.println("JPRE server is listening port " + port);
	}

	/**
	 * Stop the frame, network server and scheduler
	 */
	public void shutdown(boolean shutdown) {
		this.shutdown = shutdown;
		scheduler.shutdown();
		// TODO: 2017/4/11  stop netty
	}

	public boolean isShutdown() {
		return shutdown;
	}

	/**
	 * 初始化插件环境
	 *
	 * @param dataFolder 配置目录
	 */
	@SuppressWarnings({"SameParameterValue", "ResultOfMethodCallIgnored"})
	public void init(String dataFolder) {
		this.dataFolder = dataFolder;
		new File(dataFolder + "/plugins/").mkdir();
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
	public boolean callEvent(Event event) {
		boolean cancelled = pluginManager.callEvent(event);
		if (cancelled) {
			return true;
		}

		try {
			switch (Event.getEventType(event.getClass())) {
				case EventTypes.DISCUSS_MESSAGE:
					if (event instanceof DiscussMessageEvent) {
						if (((DiscussMessageEvent) event).getRepeat() == null || ((DiscussMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplyDiscussMessageEvent ev = new ReplyDiscussMessageEvent((DiscussMessageEvent) event);
						pluginManager.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}


						ev.getRobot().sendDiscussMessage(ev.getDiscuss(), ev.getRepeat());
					}
					return false;
				case EventTypes.GROUP_MESSAGE:
					if (event instanceof GroupMessageEvent) {
						if (((GroupMessageEvent) event).getRepeat() == null || ((GroupMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplyGroupMessageEvent ev = new ReplyGroupMessageEvent((GroupMessageEvent) event);
						pluginManager.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}

						ev.getRobot().sendGroupMessage(ev.getGroup(), ev.getRepeat());
					}
					return false;
				case EventTypes.PRIVATE_MESSAGE:
					if (event instanceof PrivateMessageEvent) {
						if (((PrivateMessageEvent) event).getRepeat() == null || ((PrivateMessageEvent) event).getRepeat().isEmpty()) {
							return false;
						}

						ReplyPrivateMessageEvent ev = new ReplyPrivateMessageEvent((PrivateMessageEvent) event);
						pluginManager.callEvent(ev);
						if (ev.isCancelled()) {
							return false;
						}

						ev.getRobot().sendPrivateMessage(ev.getQQ(), ev.getRepeat());
					}
					return false;
				case EventTypes.REQUEST_FRIEND_ADD:
					if (event instanceof AddFriendRequestEvent) {
						// TODO: 2017/4/9
						//JPREMain.getCaller().friendAnswerAddRequest(((AddFriendRequestEvent) event).getResponseFlag(), ((AddFriendRequestEvent) event).isAccept(), ((AddFriendRequestEvent) event).getNickIfAccept());
					}
					return false;
				case EventTypes.REQUEST_GROUP_ADD:
					if (event instanceof AddGroupRequestEvent) {
						// TODO: 2017/4/9
						//JPREMain.getCaller().groupAnswerJoinRequest(((AddGroupRequestEvent) event).getResponseFlag(), ((AddGroupRequestEvent) event).getType(), ((AddGroupRequestEvent) event).isAccept(), ((AddGroupRequestEvent) event).reasonIfRefused);
					}
					return false;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	public String[] getLoadedPluginList() {
		String[] names = new String[pluginManager.getPlugins().size()];
		int i = 0;
		for (Plugin plugin : pluginManager.getPlugins()) {
			names[i++] = plugin.getName();
		}
		return names;
	}

	//加载一个插件:
	public boolean loadPluginDescription(String fileName) {
		try {
			return pluginManager.loadPluginDescription(fileName) != null;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean loadPlugin(String fileName) throws PluginLoadException {
		return pluginManager.loadPlugin(fileName);
	}

	public boolean enablePlugin(String name) {
		try {
			Plugin plugin = pluginManager.getPlugin(name);
			if (plugin == null) {
				return false;
			}
			plugin.enable();
			System.out.println("[Plugin] " + plugin.getName() + " enabled!");
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean disablePlugin(String name) {
		try {
			Plugin plugin = pluginManager.getPlugin(name);
			if (plugin == null) {
				return false;
			}
			plugin.disable();
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	public boolean isPluginEnabled(String name) {
		Plugin plugin = pluginManager.getPlugin(name);
		return plugin != null && plugin.isEnabled();
	}


	public Scheduler getScheduler() {
		return scheduler;
	}
}
