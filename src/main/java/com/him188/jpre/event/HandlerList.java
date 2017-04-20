package com.him188.jpre.event;

import java.util.ArrayList;

/**
 * @author Him188
 */
public class HandlerList extends ArrayList<Handler> {
	public void remove(Handler handler) {
		super.remove(handler);
	}

	public ArrayList<Handler> getAll() {
		return this;
	}
}
