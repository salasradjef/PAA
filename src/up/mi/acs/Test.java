package up.mi.acs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Test {

	public static void main(String[] args) throws IOException {
		String s = "hello";
		File file = new File("./src/up/mi/acs/results/ydy");
		if(!file.exists()){
			file.createNewFile();
			String str = "tst";
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(str);
			writer.close();
		}
	}

}
