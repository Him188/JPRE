package com.him188.jpre.event.group;

/**
 * 群名变动事件, 暂不支持
 *
 * @author Him188
 */
@Deprecated
public class GroupNameChangeEvent extends GroupEvent {
	@Override
	public long getGroup() {
		return 0;
	}
}
