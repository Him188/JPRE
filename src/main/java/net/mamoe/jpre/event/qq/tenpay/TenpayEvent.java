package net.mamoe.jpre.event.qq.tenpay;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.qq.QQEvent;

/**
 * 财付通事件
 *
 * @author Him188 @ JPRE Project
 */
abstract public class TenpayEvent extends QQEvent {
	public TenpayEvent(RobotQQ robot, QQ qq) {
		super(robot, qq);
	}
}
