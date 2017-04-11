import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author Him188
 */
public class TestDecoding {
	public static void main(String... args) {
		System.out.println("Text decoding: ");
		byte[] bytes = {
				-17, -65, -67, -17, -65, -67
		};
		System.out.println("Original:\t" + Arrays.toString(bytes));
		System.out.println("None:\t\t" + new String(bytes));
		for (String s : new String[]{"GBK", "GB2312", "UTF-8", "ASCII"}) {
			try {
				System.out.println(s + ":\t\t" + new String(bytes, s));
			} catch (UnsupportedEncodingException ignored) {
			}
		}
		try {
			System.out.println("UniCode:\t" + new String(bytes, "Unicode"));
		} catch (UnsupportedEncodingException ignored) {

		}
	}
}
