package com.him188.jpre.event;

import com.him188.jpre.plugin.Plugin;

/**
 * 事件处理器
 * 一般不会在插件中使用
 *
 * @author Him188
 */
public interface Handler {
	Plugin getPlugin();

	Listener getListener();

	Class<Event> getEvent();

	void execute(Listener listener, Event event);

	EventPriority getPriority();
}
