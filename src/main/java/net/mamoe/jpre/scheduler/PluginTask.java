package net.mamoe.jpre.scheduler;

import net.mamoe.jpre.plugin.Plugin;

/**
 * @author Him188 @ JPRE Project
 */
public abstract class PluginTask extends Task {
	private final Plugin owner;

	public PluginTask(Plugin owner) {
		super();
		this.owner = owner;
	}

	public PluginTask(Plugin owner, TaskInfo info) {
		super(info);
		this.owner = owner;
	}

	public PluginTask(Plugin owner, long delay) {
		this(owner, delay, -1);
	}

	public PluginTask(Plugin owner, long delay, long period) {
		super(delay, period);
		this.owner = owner;
	}

	public Plugin getOwner() {
		return owner;
	}

	@Override
	public abstract void onRun();
}