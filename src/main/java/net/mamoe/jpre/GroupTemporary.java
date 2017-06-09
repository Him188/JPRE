package net.mamoe.jpre;

/**
 * 群临时会话
 *
 * @author Him188 @ JPRE Project
 */
public final class GroupTemporary extends Temporary {
    public GroupTemporary(RobotQQ robot, long session) {
        super(robot, session);
    }

    @Override
    public boolean sendMessage(String content) {// TODO: 2017/4/19 return value
        return getRobot().sendGroupTemporaryMessage(this, content);
    }

    @Override
    public boolean sendObjectMessage(String content, String subType) {
        return getRobot().sendGroupTemporaryObjectMessage(this.getNumber(), content, subType);
    }
}
