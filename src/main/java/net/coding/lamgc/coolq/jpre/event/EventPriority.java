package net.coding.lamgc.coolq.jpre.event;

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
			LOWEST,
			LOW,
			NORMAL,
			HIGH,
			HIGHEST
	};
}
