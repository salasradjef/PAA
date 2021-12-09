package up.mi.acs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Writer {
    private HashMap<Pirate,String> solution;

    public Writer(HashMap<Pirate,String> solution){
        this.solution = solution;
    }

    public static File accessTofile(String name) {
        File file = new File("./src/up/mi/acs/results/" + name);

        if(!file.exists()){
            try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Une erreur s'est produite lors de la création du fichier de sauvegarde.");
			}

        }

        return file;
    }


    public void writeTofile(String name) throws IOException {
        File file = new File("./src/up/mi/acs/results/" + name);

        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        for(Pirate key : solution.keySet()){
            String rslt = key.getID() + ":" +solution.get(key) + "\n";
            writer.write(rslt);
        }
    }




}