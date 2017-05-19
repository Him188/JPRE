package net.mamoe.jpre.exception.binary;

/**
 * @author Him188 @ JPRE Project */
public class BinaryException extends RuntimeException {
    public BinaryException(String message, Throwable cause) {
        super("There have a error happened to network system: " + message, cause);
    }
}
