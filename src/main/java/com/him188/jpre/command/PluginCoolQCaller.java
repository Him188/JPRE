package com.him188.jpre.command;

import com.him188.jpre.plugin.Plugin;

/**
 * @author Him188
 */
public class PluginMPQCaller extends BaseMPQCaller {
	private final int authCode;

	@Override
	public int getAuthCode() {
		return authCode;
	}

	public PluginMPQCaller(Plugin plugin) {
		this.authCode = plugin.getAuthCode();
	}
}
