package net.mamoe.jpre;

/**
 * @author Him188 @ JPRE Project
 */
public enum Permission {
	MASTER(2),
	ADMIN(1),
	MEMBER(0);

	private int level;

	Permission(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public boolean isMember() {
		return level >= 0;
	}

	public boolean isMaster() {
		return level >= 2;
	}

	public boolean isAdmin() {
		return level >= 1;
	}


	public static boolean isMember(Permission permission) {
		return permission.level >= 0;
	}

	public static boolean isMaster(Permission permission) {
		return permission.level >= 2;
	}

	public static boolean isAdmin(Permission permission) {
		return permission.level >= 1;
	}
}
