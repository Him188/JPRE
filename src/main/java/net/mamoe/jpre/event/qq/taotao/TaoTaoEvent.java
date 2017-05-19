package net.mamoe.jpre.event.qq.taotao;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.qq.QQEvent;

/**
 * @author Him188 @ JPRE Project */
public abstract class TaoTaoEvent extends QQEvent {
	public TaoTaoEvent(RobotQQ robot, QQ qq){
		super(robot, qq);
	}
}
