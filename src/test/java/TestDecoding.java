import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * @author Him188 */
public class TestDecoding {
	public static void main(String... args) {
		System.out.println("Text decoding: ");
		byte[] bytes = {
				-87, 100, 40, -93, -17, 92, 117, 69, 51, 56, 51, 66, 66, -90, -40, 92, 117, 69, 51, 56, 51, 66, 66, 41, -91, -50
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
