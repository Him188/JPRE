package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project
 */
public class Discussion extends User {
	private final long number;

	public Discussion(RobotQQ robot, long number){
		super(robot);
		this.number = number;
	}

	public long getNumber() {
		return number;
	}

	@Override
	public boolean sendMessage(String content) {// TODO: 2017/4/19 return value
		return getRobot().sendDiscussionMessage(this, content);
	}

	@Override
	public boolean sendObjectMessage(String content, String subType) {
		return getRobot().sendDiscussionObjectMessage(this.getNumber(), content, subType);
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Discussion && ((Discussion) obj).getNumber() == this.getNumber();
	}

	// TODO: 2017/5/17 添加所有方法
}
