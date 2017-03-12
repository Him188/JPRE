package net.coding.lamgc.coolq.jpre.event.plugin;

import net.coding.lamgc.coolq.jpre.event.Event;
import net.coding.lamgc.coolq.jpre.plugin.JavaPlugin;

/**
 * @author Him188
 */
abstract public class PluginEvent extends Event {
	abstract public JavaPlugin getPlugin();
}
