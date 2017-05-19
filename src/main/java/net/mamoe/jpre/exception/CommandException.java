package net.mamoe.jpre.exception;

/**
 * @author Him188 @ JPRE Project */
public class CommandException extends RuntimeException {
	public CommandException(String message) {
		super(message);
	}

	public CommandException(String message, Throwable e) {
		this(message);
		e.printStackTrace();
	}
}
