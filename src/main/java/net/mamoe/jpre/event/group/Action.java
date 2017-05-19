package net.mamoe.jpre.event.group;

/**
 * @author Him188 @ JPRE Project */
public enum Action {
    ACCEPT(10),   //同意
    DECLINE(20),  //拒绝
    IGNORE(0);    //忽略

    private final int intVal;

    Action(int intVal) {
        this.intVal = intVal;
    }

    public int getIntVal() {
        return intVal;
    }
}
