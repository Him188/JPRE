package com.him188.jpre.plugin;

import com.him188.jpre.BaseCoolQCaller;

/**
 * @author Him188
 */
public class PluginCoolQCaller extends BaseCoolQCaller {
	private final int authCode;

	@Override
	public int getAuthCode() {
		return authCode;
	}

	public PluginCoolQCaller(Plugin plugin) {
		this.authCode = plugin.getAuthCode();
	}
}
