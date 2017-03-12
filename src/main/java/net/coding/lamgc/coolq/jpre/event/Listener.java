package net.coding.lamgc.coolq.jpre.event;

import net.coding.lamgc.coolq.jpre.PluginManager;
import net.coding.lamgc.coolq.jpre.plugin.Plugin;

/**
 * 事件监听器. 一个监听器必须实现这接口.
 * <p>
 * 一个监听器例子:
 * 注意, 方法上的注解 {@code @EventListener} 是必须的
 * <p>
 * <pre>
 * class MySimpleListener implements Listener{
 *     {@code @EventListener(priority = EventPriority.NORMAL, ignoreCancelled = true)}
 *     public void onMessage(GroupMessageEvent event){
 *         if (event.getMessage().equals("说 OK")){
 *              event.replay = "OK";
 *              event.setCancelled(true);
 *         }
 *     }
 * }
 * </pre>
 * <p>
 * 关于注册监听器, 请查看: {@link PluginManager#registerEvents(Listener, Plugin)}
 *
 * @author Him188 (javadoc)
 */
public interface Listener {
}
