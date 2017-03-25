package com.him188.jpre.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Him188
 */
public class HandlerList extends ArrayList<Handler> {
	public void remove(Handler handler) {
		super.remove(handler);
	}

	public List<Handler> getAll() {
		return this;
	}
}
