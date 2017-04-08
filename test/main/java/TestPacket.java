import com.him188.jpre.binary.Unpack;

import java.util.Arrays;

/**
 * @author Him188
 */
public class TestPacket {
	private static final byte[] data = new byte[]
			{
					2, 0, 0, 0, 2, 0, 0, 0, 1, 88, -27, -66, -42, 4, -50, 72, 37, 0, 0, 0, 0, -94, 63, 3, 62, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, -50, -46, 0, 0, 0, 8, -50, -94, -56, -19, -47, -59, -70, -38, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10
			};

	public static void main(String[] args){
		Unpack unpack
				= new Unpack(data);
		unpack.getByte();
		unpack.getInt();
		unpack.getInt();
		unpack.getInt();
		System.out.println(unpack.getLong());
		System.out.println(unpack.getLong());
		System.out.println(unpack.getString());
		System.out.println(Arrays.toString(unpack.getString().getBytes()));
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
