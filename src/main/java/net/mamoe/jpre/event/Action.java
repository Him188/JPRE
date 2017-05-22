package net.mamoe.jpre.event;

/**
 * @author Him188 @ JPRE Project
 */
public enum Action {
    UNILATERAL_ACCEPT(30),  //单向同意, 仅好友添加事件时可用. 其他事件不确定会发生什么
    ACCEPT(10),             //同意
    DECLINE(20),            //拒绝
    IGNORE(0);              //忽略

    private final int intVal;

    Action(int intVal) {
        this.intVal = intVal;
    }

    public int getIntVal() {
        return intVal;
    }
}
