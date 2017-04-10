package com.him188.jpre.event.tenpay;

import com.him188.jpre.RobotQQ;

/**
 * @author Him188
 */
public class ReceiveTransferEvent extends TenpayEvent {
	private final RobotQQ robot;
	private final long qq;
	private final float amount;
	private final String message;

	public ReceiveTransferEvent(RobotQQ robot, long QQ, float amount, String message){
		this.robot = robot;
		qq = QQ;
		this.amount = amount;
		this.message = message;
	}

	@Override
	public RobotQQ getRobot() {
		return robot;
	}

	public long getQQ() {
		return qq;
	}

	public String getMessage() {
		return message;
	}

	public float getAmount() {
		return amount;
	}
}
