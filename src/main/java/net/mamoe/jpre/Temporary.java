package net.mamoe.jpre;

/**
 * 临时会话消息. <br>
 * <p>
 * 临时会话分两种,
 * 1. 群临时会话: {@link GroupTemporary}
 * 2. 讨论组临时会话: {@link DiscussionTemporary}
 *
 * @author Him188 @ JPRE Project
 */
public abstract class Temporary extends User {
    private final long session;

    public Temporary(RobotQQ robot, long session) {
        super(robot);
        this.session = session;
    }

    public long getNumber() {
        return session;
    }

    @Override
    public String toString() {
        return String.valueOf(session);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Temporary && ((Temporary) obj).getNumber() == this.getNumber();
    }
}
