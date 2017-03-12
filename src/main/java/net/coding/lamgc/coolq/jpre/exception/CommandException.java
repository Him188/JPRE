package net.coding.lamgc.coolq.jpre.exception;

public class CommandException extends RuntimeException {
	public CommandException(String message) {
		super(message);
	}

	public CommandException(String message, Throwable e) {
		this(message);
		e.printStackTrace();
	}
}
