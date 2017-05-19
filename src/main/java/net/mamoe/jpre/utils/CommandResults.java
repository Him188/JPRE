package net.mamoe.jpre.utils;

import net.mamoe.jpre.JPREMain;
import net.mamoe.jpre.scheduler.Task;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class CommandResults extends LinkedHashMap<Byte, Object> {
    private final Queue<Byte> keys = new LinkedList<>();

    public CommandResults() {
        super(255);
        for (byte i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
            keys.offer(i);
        }
    }

    /**
     * 请求一个 id
     * 若 id 已经使用完毕则阻塞线程直到有 id
     *
     * @return id
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public byte requestId() throws InterruptedException {
        while (keys.size() == 0) { //ID已经被使用完毕
            Thread.sleep(1);
        }

        return keys.poll();
    }

    public boolean booleanResult(byte id) {
        return intResult(id) == 1;
    }

    public int intResult(byte id) {
        return Utils.parseInt(stringResult(id));
    }

    public long longResult(byte id) {
        return Utils.parseLong(stringResult(id));
    }

    public String stringResult(byte id) {
        Task task = JPREMain.getServerScheduler().scheduleTimingTask(() -> this.put(id, ""), 5000);//5s
        Object result = this.remove(id);
        while (result == null) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                return null;
            }
        }
        task.forceCancel();
        return String.valueOf(result);
    }


    public void setResult(byte id, Object result) {
        if (result == null) {
            result = "";
        }
        this.put(id, result);
    }
}