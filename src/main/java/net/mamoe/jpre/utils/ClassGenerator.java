package net.mamoe.jpre.utils;

/**
 * 用于 {@link UserList#get(long)} 的实例构造器
 */
@FunctionalInterface
public interface ClassGenerator<T> {
    T generate(long number);
}