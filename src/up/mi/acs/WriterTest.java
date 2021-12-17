package up.mi.acs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

class WriterTest {


	@Test
	void testAccessTofile() throws FileAlreadyExistsException {
		File file=new File("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\results\\fichier");
		file.delete();
		String name="fichier";
		file=Writer.accessTofile(name);

		assertTrue(file.exists());


		file.delete();
		
	}

	
	@Test
	void testSaveSolution() throws IOException, ParseException{
		String name="fichier";

		Equipage equipage = new Equipage("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\equipage1");
		equipage.affectationNaive();

		
		
		Writer.saveSolution(name, equipage);

		BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ameln\\OneDrive\\Bureau\\projet_paa\\PAA\\results\\fichier"));
		String str,str2;
		int i=0;
		
		while ((str = reader.readLine()) != null) {

			str2=equipage.getEquipage().get(i).getID()+":"+equipage.getObjet_recu().get(equipage.getEquipage().get(i));
			assertTrue(str.equals(str2));
			i++;
		}

		reader.close();
	}
}
