package com.him188.jpre.event.qq.tenpay;

import com.him188.jpre.QQ;
import com.him188.jpre.RobotQQ;

/**
 * 收到财付通转账
 *
 * @author Him188
 */
public class ReceiveTransferEvent extends TenpayEvent {
	private final float amount;
	private final String message;

	public ReceiveTransferEvent(RobotQQ robot, QQ QQ, float amount, String message) {
		super(robot, QQ);
		this.amount = amount;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public float getAmount() {
		return amount;
	}
}
