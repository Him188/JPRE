import java.util.*;

/**
 * @author Him188 @ JPRE Project
 * @since JPRE 1.0.0
 */
public class ListSpeedTest {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        Collection<Object>[] collections = new Collection[]{
                new HashSet(), //93ms
                new LinkedHashSet(), //SLOWEST, 855ms
                new LinkedList(),  //40ms
                new ArrayList(), //FASTEST, 25ms
        };

        for (int i = 0; i < collections.length; i++) {
            Collection collection = collections[i];
            System.out.println(collection.getClass().getSimpleName() + ": " + speedTest(collection));
        }
    }

    private static long speedTest(Collection<Object> collection) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            collection.add(i);
        }

        Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }

        return System.currentTimeMillis() - time;
    }
}