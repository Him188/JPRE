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
		putList(new ArrayList<>(), String.class);
	}
}
