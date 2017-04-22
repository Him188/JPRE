import com.him188.jpre.binary.Pack;

/**
 * @author Him188 @ JPRE
 * @since JPRE
 */
public class TestPacket {
	public static void main(String[] args){
		Pack pack = new Pack(new byte[]{3, 0, 0, 0, 2, -35, -72, -28, 118, 0, 0, 0, 0, -94, 63, 3, 62, 0, 0, 0, 0, -94, 63, 3, 62, 0, 0, 0, 0, -94, 63, 3, 62, 0, 0, 0, 0, 0, 0, 0, 0, 127, 127});
		System.out.println(pack.getByte());
		System.out.println(pack.getInt());
		System.out.println(pack.getLong());
		System.out.println(pack.getLong());
		System.out.println(pack.getLong());
		System.out.println(pack.getLong());
		System.out.println(pack.getString());
	}
}
