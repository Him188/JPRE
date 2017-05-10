import com.him188.jpre.binary.Binary;
import com.him188.jpre.binary.Pack;

import java.util.Arrays;

/**
 * @author Him188 @ JPRE
 * @since JPRE 1.0.0
 */
public class TestPacket {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(convert(new byte[]{-1, -128, 0, 127})));
        System.out.println(Arrays.toString(Binary.toBytes(11001L)));
        System.out.println((Binary.toLong(new byte[]{-7, 42, 0, 0, 0, 0, 0, 0})));

        System.out.println(0xff); // 1111 1111
    }

    public static int[] convert(byte[] bytes){
        int[] value = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            value[i] = bytes[i] & 0xff;
        }

        return value;
    }
}
