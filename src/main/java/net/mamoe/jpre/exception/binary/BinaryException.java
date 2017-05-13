package net.mamoe.jpre.exception.binary;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class BinaryException extends RuntimeException {
    public BinaryException(String message, Throwable cause) {
        super("There have a error happened to network system: " + message, cause);
    }
}
