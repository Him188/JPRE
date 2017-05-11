import com.him188.jpre.binary.Binary;
import com.him188.jpre.binary.Pack;

import java.util.Arrays;
import java.util.List;

/**
 * @author Him188 @ JPRE
 * @since JPRE 1.0.0
 */
public class TestPacket {
    public static void main(String[] args) {
        System.out.println(Binary.toInt(Binary.realReverse(new byte[]{-38, 7, 0, 0})));

    }

}
