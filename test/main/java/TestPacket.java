import com.him188.jpre.binary.Pack;
import com.him188.jpre.binary.Unpack;

/**
 * @author Him188
 */
public class TestPacket {
	private static final byte[] data = new byte[]
			{2,     0, 0, 0, 2,     0, 0, 0, 1, 88, -42, -128, -67, 0, 0, 0, 0, 30, 117, -9, -76, 0, 0, 0, 0, -80, 119, 37, 6, 0, 0, 0, 0, 0, 0, 0, 24, -27, -113, -115, -26, -83, -93, -24, -75, -108, -23, -110, -79, -28, -71, -97, -26, -78, -95, -26, -124, -113, -26, -128, -99, 7, 125, -74, 72};

	public static void main(String[] args){
		Unpack unpack
				= new Unpack(data);
		unpack.getByte();
		unpack.getInt();
		unpack.getInt();
		unpack.getInt();
		System.out.println(unpack.getLong());
		System.out.println(unpack.getLong());

		Pack pack = new Pack();
		pack.putLong(Long.MAX_VALUE);
		unpack.setData(pack.getData());

		System.out.println(Long.MAX_VALUE);

		System.out.println(unpack.getLong());
	}
}
/*
		p.SetByte (J_EVENT)
		p.SetInt (2)
		p.SetInt (subType)
		p.SetInt (sendTime)
		p.SetLong (fromGroup)
		p.SetLong (fromQQ)
		p.SetUtf8LenStr (fromAnonymous)
		p.SetUtf8LenStr (msg)
		p.SetInt (font)*/
