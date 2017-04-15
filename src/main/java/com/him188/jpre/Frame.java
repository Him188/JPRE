package com.him188.jpre;

import com.him188.jpre.exception.PluginLoadException;
import com.him188.jpre.network.MPQClient;
import com.him188.jpre.plugin.Plugin;
import com.him188.jpre.plugin.PluginManager;
import com.him188.jpre.scheduler.Scheduler;

import java.io.File;

/**
 * 框架. JPRE 允许多个MPQ框架连接, 每次连接都会创建本类, 断开连接后 {@link #shutdown(boolean)}
 *
 * @author Him188
 */
@SuppressWarnings("WeakerAccess")
public final class Frame {
	private final JPREMain jpre;
	private MPQClient client;

	public void setClient(MPQClient client) {
		if (this.client != null) {
			return;
		}
		this.client = client;
	}

	public MPQClient getClient() {
		return client;
	}

	public JPREMain getJPREMain() {
		return jpre;
	}

	public Frame(JPREMain jpre) {
		this.jpre = jpre;
		pluginManager = new PluginManager(this);
		scheduler = new Scheduler(this);
	}

	private PluginManager pluginManager;
	private Scheduler scheduler;

	public PluginManager getPluginManager() {
		return pluginManager;
	}


	private String dataFolder;

	public String getDataFolder() {
		return dataFolder;
	}

	private boolean shutdown;

	/**
	 * Stop the frame, network server and scheduler
	 */
	public void shutdown(boolean shutdown) {
		this.shutdown = shutdown;
		scheduler.shutdown();
		pluginManager.disablePlugins();
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
