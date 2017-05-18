package net.mamoe.jpre.event;

import net.mamoe.jpre.event.qq.FriendAddRequestEvent;
import net.mamoe.jpre.plugin.PluginManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;

/**
 * @author Him188
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
abstract public class Event {
    /* Interception */

    private boolean intercepted = false;

    /**
     * 拦截这个事件. 不会影响事件处理, 仅仅只是起到标识作用.<br>
     * 插件可根据需要进行忽略处理.<br>
     * <p>
     * "拦截" 概念是从 MPQ 插件中移植来的, 将事件拦截后, JPRE 返回给 MPQ 的事件状态也会发生改变.<br>
     * 将事件拦截, 也可以让 MPQ 的事件终止处理.
     * </p>
     */
    public void setIntercepted() {
        setIntercepted(true);
    }

    public boolean isIntercepted() {
        return intercepted;
    }

    public void setIntercepted(boolean intercepted) {
        this.intercepted = intercepted;
    }


	/* Cancellation*/

    private boolean cancelled = false;

    /**
     * 检查这个事件是否被取消了.
     *
     * @throws NotImplementedException 当事件没有实现接口 {@link Cancellable} 时抛出.
     */
    public boolean isCancelled() {
        if (!(this instanceof Cancellable)) {
            throw new NotImplementedException();
        }

        return cancelled;
    }

    /**
     * 取消这个事件.<br>
     * 当事件被取消后, 插件可以自行进行回复,<br>
     * 一些设置了忽略被取消事件的事件处理器 ({@link EventHandler#ignoreCancelled()} 为 false) 就不会收到事件,<br>
     * 例如:<br>
     * <p> TODO 2017/05/16 javadoc
     * 事件: {@link FriendAddRequestEvent}, 且设置为接受请求.<br>
     * 如果不取消事件, 事件系统最终会同意该请求.<br>
     * 如果取消事件, 事件系统不会进行处理. 注意! 是不会处理(忽略), 而不是拒绝!<br>
     * </p>
     * <p>
     * 取消事件不会影响 MPQ 事件. 只有 {@link #setIntercepted()} 才会影响 MPQ 事件!
     * </p>
     *
     * @throws NotImplementedException 当事件没有实现接口 {@link Cancellable} 时抛出.
     */
    public void setCancelled(boolean cancelled) {
        if (!(this instanceof Cancellable)) {
            throw new NotImplementedException();
        }

        this.cancelled = cancelled;
    }

    /**
     * 取消这个事件. 详细的注释请查看 {@link #setCancelled(boolean)} <br>
     * <p>
     * 本方法中的代码实际就是 {@code setCancelled(true)}
     *
     * @throws NotImplementedException 当事件没有实现接口 {@link Cancellable} 时抛出.
     */
    public void setCancelled() {
        setCancelled(true);
    }

	/* Others */

    /**
     * 当所有事件处理器都处理过事件后(即事件处理已经达到尾声时), 本方法被调用
     * JPRE自带的所有事件中, 都没有复写本方法. 本方法只是留给插件自定义事件用.
     * <p>
     * 详细过程请查看 {@link PluginManager#callEvent(Event)}
     */
    public void close() {

    }

    /**
     * 返回当前类的类名, 当前类和父类(不包括 Object)的成员变量信息
     * 例如, GroupMessageEvent
     *
     * @return {@code 类名(子类成员变量=值,父类成员变量=值)}
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName() + "(");
        Class<?> clazz = getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    result.append(field.getName()).append("=").append(field.get(this)).append(",");
                } catch (IllegalAccessException ignored) {
                }
            }
            clazz = clazz.getSuperclass();
        }

        result.deleteCharAt(result.length() - 1);
        result.append(")");
        return result.toString();
    }
}
