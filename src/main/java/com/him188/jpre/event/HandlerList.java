package com.him188.jpre.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Him188
 */
public class HandlerList {
	public List<Handler> handlers = new ArrayList<>();

	public void add(Handler handler) {
		handlers.add(handler);
	}

	public void remove(Handler handler) {
		handlers.remove(handler);
	}

	public List<Handler> getAll() {
		return handlers;
	}
}
