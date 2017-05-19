package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project */
@SuppressWarnings("unused")
public class QQ extends User {

    private final long qq;

    public QQ(RobotQQ robot, long qq) {
        super(robot);
        this.qq = qq;
    }

    public long getNumber() {
        return qq;
    }

    @Override
    public String toString() {
        return String.valueOf(qq);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof QQ && ((QQ) obj).getNumber() == this.getNumber();
    }


    @Override
    public boolean sendMessage(String content) {
        return getRobot().sendPrivateMessage(this, content);
    }

    @Override
    public boolean sendObjectMessage(String content, String subType) {
        return getRobot().sendPrivateObjectMessage(this.getNumber(), content, subType);
    }

    // TODO: 2017/5/17 添加所有方法

    public String getNickName() {
        return this.getRobot().getFrame().getNick(this.getNumber()); // TODO: 2017/5/12  存储, 监听事件修改
    }

    public String like() {
        return getRobot().like(this.getNumber());
    }
}
