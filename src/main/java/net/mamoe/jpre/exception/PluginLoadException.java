package net.mamoe.jpre.exception;

/**
 * 加载插件时引起的异常
 *
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 * @see PluginException
 */
public class PluginLoadException extends PluginException {
	public PluginLoadException(String message) {
		super(message);
	}

	public PluginLoadException(String message, Throwable e) {
		this(message);
		e.printStackTrace();
	}
}

