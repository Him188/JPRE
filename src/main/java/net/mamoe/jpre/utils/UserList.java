package net.mamoe.jpre.utils;

import net.mamoe.jpre.Discussion;
import net.mamoe.jpre.DiscussionTemporary;
import net.mamoe.jpre.Group;
import net.mamoe.jpre.GroupTemporary;
import net.mamoe.jpre.QQ;
import net.mamoe.jpre.User;

import java.util.HashSet;

/**
 * 存储 {@link Group}, {@link QQ}, {@link Discussion}, {@link DiscussionTemporary}, {@link GroupTemporary} 等实例的容器
 *
 * @author Him188 @ JPRE Project
 */
public class UserList<T extends User> extends HashSet<T> {
    private final ClassGenerator<T> generator;

    public UserList(ClassGenerator<T> generator) {
        super();
        this.generator = generator;
    }

    /**
     * 获取 {@link T} 实例, 不存在时自动创建
     *
     * @return {@link T}
     */
    public T get(long number) {
        for (T user : this) {
            if (user.getNumber() == number) {
                return user;
            }
        }

        T user = generator.generate(number);
        this.add(user);
        return user;
    }
}
