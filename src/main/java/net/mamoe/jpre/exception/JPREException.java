package net.mamoe.jpre.exception;

/**
 * @author Him188 @ JPRE Project
 */
@SuppressWarnings("WeakerAccess")
public abstract class JPREException extends RuntimeException {
	public JPREException() {
		super();
	}

	public JPREException(String message) {
		super(message);
	}

	public JPREException(String message, Exception cause) {
		super(message, cause);
	}

	public JPREException(String message, String subMessage) {
		super(message + ": " + subMessage);
	}

	public JPREException(String message, String subMessage, Exception cause) {
		this(message + ": " + subMessage, cause);
	}

	public JPREException(Exception cause) {
		super(cause);
	}
}
