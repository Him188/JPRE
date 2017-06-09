package net.mamoe.jpre;

/**
 * 讨论组临时会话
 *
 * @author Him188 @ JPRE Project
 */
public final class DiscussionTemporary extends Temporary {
    public DiscussionTemporary(RobotQQ robot, long session) {
        super(robot, session);
    }

    @Override
    public boolean sendMessage(String content) {
        return getRobot().sendDiscussionTemporaryMessage(this, content);
    }

    @Override
    public boolean sendObjectMessage(String content, String subType) {
        return getRobot().sendDiscussionTemporaryObjectMessage(this.getNumber(), content, subType);
    }
}
