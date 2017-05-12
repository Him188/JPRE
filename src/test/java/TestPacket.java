import net.mamoe.jpre.binary.Binary;

/**
 * @author Him188 @ JPRE
 * @since JPRE 1.0.0
 */
public class TestPacket {
    public static void main(String[] args) {
        System.out.println(Binary.toInt(Binary.realReverse(new byte[]{2, 0, 0, 95})));

    }

}
