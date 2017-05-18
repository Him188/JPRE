package net.mamoe.jpre.event;

import net.mamoe.jpre.event.qq.FriendAddRequestEvent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
@SuppressWarnings("unused")
public interface Cancellable {

    /**
     * 检查这个事件是否被取消
     *
     * @throws NotImplementedException 当事件没有实现接口 {@link Cancellable} 时抛出.
     */
    boolean isCancelled();

    /**
     * 取消这个事件.<br>
     * 当事件被取消后, 插件可以自行进行回复,<br>
     * 一些设置了忽略被取消事件的事件处理器 ({@link EventHandler#ignoreCancelled()} 为 false) 就不会收到事件,<br>
     * 例如:<br>
     * <p> TODO 2017/05/16 javadoc
     * 事件: {@link FriendAddRequestEvent}, 且设置为接受请求.<br>
     * 如果不取消事件, 事件系统最终会同意该请求.<br>
     * 如果取消事件, 事件系统不会进行处理(忽略)<br>
     * </p>
     * <p>
     * 取消事件不会影响 MPQ 事件. 只有 {@link Event#setIntercepted()} 才会影响 MPQ 事件!
     * </p>
     *
     * @throws NotImplementedException 当事件没有实现接口 {@link Cancellable} 时抛出.
     */
    void setCancelled(boolean cancelled);
}
