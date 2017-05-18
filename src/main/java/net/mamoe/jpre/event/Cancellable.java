package net.mamoe.jpre.event;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
@SuppressWarnings("unused")
public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
