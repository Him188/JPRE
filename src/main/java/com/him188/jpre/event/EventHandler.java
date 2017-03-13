package com.him188.jpre.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LamGC
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
	/**
	 * 定义这个监测器的优先级
	 */
	EventPriority priority() default EventPriority.NORMAL;

	/**
	 * 定义这个监测器是否忽略已经被取消的事件
	 * @see Event#setCancelled()
	 */
	boolean ignoreCancelled() default true;

	/**
	 * 定义这个监测器是否忽略已经被拦截的事件
	 *
	 * @see Event#setIntercepted()
	 */
	boolean ignoreIntercepted() default true;
}
