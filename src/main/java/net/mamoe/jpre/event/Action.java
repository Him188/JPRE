package net.mamoe.jpre.event;

/**
 * @author Him188 @ JPRE Project
 */
public enum Action {
	/**
	 * 单向同意, 仅好友添加事件时可用. 其他事件不确定会发生什么
	 */
	UNILATERAL_ACCEPT(30),

	/**
	 * 同意
	 */
	ACCEPT(10),

	/**
	 * 拒绝
	 */
	DECLINE(20),

	/**
	 * 忽略
	 */
	IGNORE(0);

    private final int intVal;

    Action(int intVal) {
        this.intVal = intVal;
    }

    public int getIntVal() {
        return intVal;
    }
}
