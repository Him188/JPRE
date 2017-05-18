package net.mamoe.jpre.exception;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class CommandException extends RuntimeException {
	public CommandException(String message) {
		super(message);
	}

	public CommandException(String message, Throwable e) {
		this(message);
		e.printStackTrace();
	}
}
