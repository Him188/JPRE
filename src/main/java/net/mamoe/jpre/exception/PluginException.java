package net.mamoe.jpre.exception;

/**
 * 由插件引起的异常
 *
 * @author Him188 @ JPRE Project
 * @see PluginException
 */
public class PluginException extends JPREException {
    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Exception e) {
        super(message, e);
    }

    public PluginException(String message, String subMessage) {
        super(message, subMessage);
    }

    public PluginException(String message, String subMessage, Exception e) {
        super(message, subMessage, e);
    }
}