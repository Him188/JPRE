package net.mamoe.jpre.event;

import net.mamoe.jpre.plugin.Plugin;

/**
 * 插件自定义的事件
 *
 * @author Him188 @ JPRE Project */
public class PluginEvent<T extends Plugin> extends Event {
	private final T plugin;

	public PluginEvent(T plugin) {
		this.plugin = plugin;
	}

	public T getPlugin() {
		return plugin;
	}
}
