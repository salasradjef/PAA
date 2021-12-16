package up.mi.acs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class EquipageTest {

	@Test
	void testGetPosOfPirate() throws IOException {
		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		Pirate pirate1 = new Pirate("12");
		Pirate pirate2 = new Pirate("6");

		assertEquals(6, equipage.getPosOfPirate(pirate2));
		assertEquals(-1, equipage.getPosOfPirate(pirate1));
	}

	@Test
	void testFindPirateByID() throws IOException {
		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		String str = "7";

		assertEquals(equipage.getEquipage().get(7), equipage.findPirateByID(str));
		assertEquals(null, equipage.findPirateByID("10"));
	}

	@Test
	void testCost() throws IOException {
		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		equipage.affectationNaive();

		assertEquals(2, equipage.cost());
	}

	@Test
	void testChangerObjet() throws IOException {

		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");

		equipage.affectationNaive();

		Pirate pirate1 = equipage.getEquipage().get(0);
		String obj1 = equipage.getObjet_recu().get(pirate1);

		Pirate pirate2 = equipage.getEquipage().get(1);
		String obj2 = equipage.getObjet_recu().get(pirate2);

		equipage.changerObjet(pirate1, pirate2);
		assertEquals(equipage.getObjet_recu().get(pirate2), obj1);
		assertEquals(equipage.getObjet_recu().get(pirate1), obj2);

	}
	/*
	 * @Test void testEstJaloux() throws IOException { Equipage equipage = new
	 * Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
	 * equipage.affectationNaive();
	 * 
	 * Pirate pirate1 = equipage.getEquipage().get(9); String obj1 =
	 * equipage.getObjet_recu().get(pirate1); int id=
	 * 
	 * Pirate pirate2 = equipage.getEquipage().get(3); String obj2 =
	 * equipage.getObjet_recu().get(pirate2);
	 * 
	 * assertFalse(equipage.estJaloux(pirate1,pirate2)); }
	 */

	/*@Test
	void testHateRelation() throws IOException {
		Reader reader = new Reader("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		HashMap<String, String> deteste = reader.getListeDeteste();

		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		equipage.affectationNaive();

		Pirate pirate1 = equipage.getEquipage().get(1);

		Pirate pirate2 = equipage.getEquipage().get(6);


		pirate1.getID();
		pirate2.getID();
		System.out.println(equipage.hateRelation(pirate1, pirate2));
		assertFalse(deteste.get(0)==pirate2.getID());
		//assertTrue(equipage.hateRelation(pirate1, pirate2));

	}*/
	

	@Test
	void testAddRelation() throws IOException {
		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		
		Pirate pirate1 = equipage.getEquipage().get(6);
		Pirate pirate2 = equipage.getEquipage().get(7);
		
		equipage.addRelation(pirate1, pirate2);
		
		int dtst=equipage.getRelation_pirate()[6][7];
		int dtst2=equipage.getRelation_pirate()[7][6];
		
		assertEquals(dtst,dtst2);
		
	}
	
}
