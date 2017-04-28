import com.him188.jpre.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Him188
 */
public class Test {
	public static <T> void putList(List<T> list, Class<T> valueClass) {
		System.out.println(valueClass.getName()); //java.lang.String: ok
	}

	public static void main(String... args){
		System.out.println(Utils.arraySearch(new byte[]{1, 2, 3, 4, 5, 6}, new byte[]{2, 3}));//1: success
		putList(new ArrayList<>(), String.class);
	}
}
