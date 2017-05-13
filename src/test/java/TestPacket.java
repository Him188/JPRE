import net.mamoe.jpre.binary.Binary;

/**
 * @author Him188 @ JPRE
 * @since JPRE 1.0.0
 */
public class TestPacket {
    public static void main(String[] args) {
        System.out.println(Binary.toInt(new byte[]{-24, 24, 0, 0,}));
        System.out.println(Binary.toInt(Binary.realReverse(new byte[]{-24, 24, 0, 0,})));


        System.out.println(Integer.MAX_VALUE);

        System.out.println(Binary.toInt(Binary.realReverse(new byte[]{-45, 7, 0, 0})));
        System.out.println(Binary.toLong(new byte[]{0, 0, 0, 0, 31, -30, -10, 37,}));


        System.out.println();
    }

}
