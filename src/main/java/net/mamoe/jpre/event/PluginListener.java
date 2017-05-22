package net.mamoe.jpre.event;

/**
 * 插件的事件检测器
 *
 * @author Him188 @ JPRE Project
 */
public class PluginListener<T> implements Listener {
    private final T owner;

    public PluginListener(T owner){
        this.owner = owner;
    }

    public T getOwner() {
        return owner;
    }
}
