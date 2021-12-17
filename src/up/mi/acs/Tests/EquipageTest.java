package up.mi.acs.Tests;


import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import up.mi.acs.Equipage;
import up.mi.acs.Pirate;

class EquipageTest {
    private String path = "./src/up/mi/acs/files/file.txt";

    @Test
    void testGetPosOfPirate() throws IOException, ParseException {
        Equipage equipage = new Equipage(path);
        Pirate pirate1 = new Pirate("12");
        Pirate pirate2 = new Pirate("6");

        assertEquals(6, equipage.getPosOfPirate(pirate2));
        assertEquals(-1, equipage.getPosOfPirate(pirate1));
    }

    @Test
    void testFindPirateByID() throws IOException, ParseException {
        Equipage equipage = new Equipage(path);
        String str = "7";

        assertEquals(equipage.getEquipage().get(7), equipage.findPirateByID(str));
        assertEquals(null, equipage.findPirateByID("10"));
    }


    void testChangerObjet() throws IOException, ParseException {

        Equipage equipage = new Equipage(path);

        equipage.affectationNaive();

        Pirate pirate1 = equipage.getEquipage().get(0);
        String obj1 = equipage.getObjet_recu().get(pirate1);

        Pirate pirate2 = equipage.getEquipage().get(1);
        String obj2 = equipage.getObjet_recu().get(pirate2);

        equipage.changerObjet(pirate1, pirate2);
        assertEquals(equipage.getObjet_recu().get(pirate2), obj1);
        assertEquals(equipage.getObjet_recu().get(pirate1), obj2);

    }

    @Test
    void testAddRelation() throws IOException, ParseException {
        Equipage equipage = new Equipage(path);

        Pirate pirate1 = equipage.getEquipage().get(6);
        Pirate pirate2 = equipage.getEquipage().get(7);

        equipage.addRelation(pirate1, pirate2);

        int dtst=equipage.getRelation_pirate()[6][7];
        int dtst2=equipage.getRelation_pirate()[7][6];

        assertEquals(dtst,dtst2);

    }

}