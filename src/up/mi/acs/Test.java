package up.mi.acs;

import java.io.IOException;

public class Test {
	public static void main(String[] args) throws IOException {
		Reader a = new Reader("C:\\Users\\SALAS\\eclipse-workspace\\PROJET_PAA\\src\\up\\mi\\acs\\files\\file.txt");
		System.out.println(a.getListeDeteste());
	}
}
