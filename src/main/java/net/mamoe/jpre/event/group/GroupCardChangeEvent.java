package net.mamoe.jpre.event.group;

import net.mamoe.jpre.Group;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 群成员名片变更
 *
 * @author Him188 @ JPRE Project
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
