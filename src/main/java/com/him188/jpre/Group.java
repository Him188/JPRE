package com.him188.jpre;

/**
 * QQ 群
 *
 * @author Him188
 */
public class Group extends User {
    private final long group;

    public Group(RobotQQ robot, long group) {
        super(robot);
        this.group = group;
    }

    public long getNumber() {
        return group;
    }

    @Override
    public boolean sendMessage(String content) {
        return getRobot().sendGroupMessage(this, content);
    }

    @Override
    public String toString() {
        return "Group(" + group + ")";
    }
}
