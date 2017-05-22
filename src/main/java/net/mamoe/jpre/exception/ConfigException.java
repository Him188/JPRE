package net.mamoe.jpre.exception;

/**
 * @author Him188 @ JPRE Project
 */
public class ConfigException extends JPREException {
	public ConfigException(String message) {
		super(message);
	}

	public ConfigException(String message, String subMessage) {
		super(message, subMessage);
	}

	public ConfigException(String message, Exception cause) {
		super(message, cause);
	}
}
