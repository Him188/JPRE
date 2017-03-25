package com.him188.jpre.event;

/**
 * @author Him188
 */
public enum EventPriority {
	LOWEST,
	LOW,
	NORMAL,
	HIGH,
	HIGHEST;

	public static final EventPriority[] PRIORITIES = new EventPriority[]{
			HIGHEST,
			HIGH,
			NORMAL,
			LOW,
			LOWEST,
	};
}
