package net.mamoe.jpre.event;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
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
