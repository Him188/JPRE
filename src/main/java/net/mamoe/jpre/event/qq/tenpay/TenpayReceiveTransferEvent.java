package net.mamoe.jpre.event.qq.tenpay;

import net.mamoe.jpre.QQ;
import net.mamoe.jpre.RobotQQ;
import net.mamoe.jpre.event.HandlerList;

/**
 * 收到财付通转账
 *
 * @author Him188
 */
public class TenpayReceiveTransferEvent extends TenpayEvent {
	private static final HandlerList handlers = new HandlerList();

	public static HandlerList getHandlers() {
		return handlers;
	}


	private final float amount;
	private final String message;

	public TenpayReceiveTransferEvent(RobotQQ robot, QQ QQ, float amount, String message) {
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
