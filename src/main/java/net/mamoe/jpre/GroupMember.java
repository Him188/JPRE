package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project
 */
public class GroupMember extends QQ { // TODO: 2017/6/9 在 Group 里面创建
	private final Group group;

	private Permission permission;

	public GroupMember(RobotQQ robot, Group group, long qq, Permission permission) {
		super(robot, qq);
		this.group = group;
		this.permission = permission;
	}

	public Group getGroup() {
		return group;
	}

	public Permission getPermission() {
		return permission;
	}

	/**
	 * 设置权限.
	 *
	 * @param permission 权限
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public void setPermission(Permission permission, boolean silent) {
		this.permission = permission;

		if (!silent) {
			// TODO: 2017/6/9  call event and send packet
		}
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof GroupMember && ((GroupMember) obj).getNumber() == this.getNumber() && getGroup().equals(((GroupMember) obj).getGroup()))
		       || super.equals(obj);
	}
}
