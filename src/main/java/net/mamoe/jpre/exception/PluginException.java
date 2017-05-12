package net.mamoe.jpre.exception;

/**
 * 由插件引起的异常
 *
 * @author Him188
 * @see PluginLoadException
 */
abstract public class PluginException extends RuntimeException {
	public PluginException(String message) {
		super(message);
	}
}

// TODO: 2017/3/10 完善异常系统.