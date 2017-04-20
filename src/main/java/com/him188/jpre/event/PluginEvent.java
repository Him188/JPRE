package com.him188.jpre.event;

import com.him188.jpre.plugin.Plugin;

/**
 * 插件自定义的事件
 *
 * @author Him188
 */
public class PluginEvent<T extends Plugin> extends Event {
	private final T plugin;

	public PluginEvent(T plugin) {
		this.plugin = plugin;
	}

	public T getPlugin() {
		return plugin;
	}
}
