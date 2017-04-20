package com.him188.jpre.event.group;

import com.him188.jpre.Group;
import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;
import com.him188.jpre.event.HandlerList;

/**
 * 群成员名片变更
 *
 * @author Him188
 */
public class GroupCardChangeEvent extends GroupEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final String newCard;

	public GroupCardChangeEvent(RobotQQ robot, Group group, QQ qq, String newCard) {
		super(robot, group, qq);
		this.newCard = newCard;
	}

	public String getNewCard() {
		return newCard;
	}
}
