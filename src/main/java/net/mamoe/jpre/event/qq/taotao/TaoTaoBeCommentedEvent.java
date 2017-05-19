package net.mamoe.jpre.event.qq.taotao;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 说说被评论
 *
 * @author Him188 @ JPRE Project */
public class TaoTaoBeCommentedEvent extends TaoTaoEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	public TaoTaoBeCommentedEvent(RobotQQ robot, QQ qq){
		super(robot, qq);
	}
}
