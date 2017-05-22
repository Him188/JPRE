
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Him188 @ JPRE Project
 */
public class ForeachSpeedTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        long t1, t2;
        for (int j = 0; j < 1000000; j++) {
            list.add("aaaaaa" + j);
        }


        System.out.println("List first visit method:");
        t1 = System.currentTimeMillis();
        for (String tmp : list) {
        }
        t2 = System.currentTimeMillis();
        System.out.println("Run Time:" + (t2 - t1) + "(ms)");


        System.out.println("List second visit method:");
        t1 = System.currentTimeMillis();
        int size = list.size();
        for (int i = 0; i < size; i++) { //FASTEST
            list.get(i);
        }
        t2 = System.currentTimeMillis();
        System.out.println("Run Time:" + (t2 - t1) + "(ms)");


        System.out.println("List Third visit method:");
        Iterator<String> iter = list.iterator();
        t1 = System.currentTimeMillis();
        while (iter.hasNext()) {
            iter.next();
        }
        t2 = System.currentTimeMillis();
        System.out.println("Run Time:" + (t2 - t1) + "(ms)");


        System.out.println("List Forth visit method:");
        list.forEach(value -> { //SLOWEST

        });
        t2 = System.currentTimeMillis();
        System.out.println("Run Time:" + (t2 - t1) + "(ms)");


        System.out.println("Finished!!!!!!!!");


        /*
        * Result
        *
        * List first visit method:
        * Run Time:15(ms)
        * List second visit method:
        * Run Time:7(ms)
        * List Third visit method:
        * Run Time:9(ms)
        * List Forth visit method:
        * Run Time:75(ms)
        * Finished!!!!!!!!
        * */
    }
}