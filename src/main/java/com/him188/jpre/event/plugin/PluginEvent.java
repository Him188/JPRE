package com.him188.jpre.event.plugin;

import com.him188.jpre.event.Event;
import com.him188.jpre.plugin.JavaPlugin;

/**
 * @author Him188
 */
abstract public class PluginEvent extends Event {
	abstract public JavaPlugin getPlugin();
}
