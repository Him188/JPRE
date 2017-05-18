package net.mamoe.jpre.event.qq;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.RobotEvent;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
abstract public class QQEvent extends RobotEvent {
	private QQ qq;

	public QQEvent(RobotQQ robot, QQ qq) {
		super(robot);
		this.qq = qq;
	}

	public QQ getQQ() {
		return qq;
	}
}
