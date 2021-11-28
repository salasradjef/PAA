package up.mi.acs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) throws IOException, InterruptedException {
		Map<String, Integer> map = new HashMap<>();
		map.put("key1", 1);
		map.put("key2", 2);
		map.put("key3", 3);
		map.put("key4", 3);
		map.put("key5", null);
		map.put(null, 3);

		//Set<String> keys = map.keySet();

		// print all keys
		for (String key : map.keySet()) {
			System.out.println(key);
			System.out.println(map.get(key));
		}
	}
}
