package up.mi.acs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.junit.jupiter.api.Test;

class PirateTest {

	/*
	 * @Test void testAddPreference() throws IOException { Equipage equipage = new
	 * Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
	 * Pirate pirate=equipage.getEquipage().get(0); Reader reader=new
	 * Reader("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
	 * 
	 * ArrayList<String> listPref=reader.getListPref().get(pirate.getID()); String[]
	 * tab=(String[]) listPref.toArray(new String[listPref.size()]);
	 * 
	 * StringBuffer str= new StringBuffer(); System.out.println(tab.toString());
	 * for(int i=0;i<tab.length;i++) { System.out.println(tab[i]); }
	 * System.out.println(); for(int j=0;j<tab.length;j++) {
	 * System.out.print(pirate.getPreference()[j]); } pirate.addPreference(tab);
	 * for(int j=0;j<tab.length;j++) {
	 * System.out.println(tab[j]+" "+tab[j]==pirate.getPreference()[j]); }
	 * assertTrue(tab.equals(pirate.getPreference())); }
	 */

	@Test
	void testFindIDofPref() throws IOException {
		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");

		String str = "o1";
		Pirate pirate = equipage.getEquipage().get(0);

		int cpt = pirate.findIDofPref(str);
		assertEquals(4,cpt);
	}

}
