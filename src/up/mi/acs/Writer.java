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

    public static File accessTofile(String name) throws IOException {
        File file = new File("C:\\Users\\Utilisateur\\eclipse-workspace\\PROJET_PAA\\src\\up\\mi\\acs\\results\\" + name);

        if(!file.exists()){
            file.createNewFile();
        
        }
        
        return file;
    }
        
    public void writeTofile(String name) throws IOException {
        File file = new File("C:\\Users\\INFOTECH\\eclipse-workspace\\PROJET_PAA\\src\\up\\mi\\acs\\results\\" + name);

        if(!file.exists()){
            file.createNewFile();
        }
        FileWriter writer = new FileWriter(file);
        for(Pirate key : solution.keySet()){
            String rslt = key.getID() + solution.get(key) + "\n";
            writer.write(rslt);
        }
    }

    // manque le throw new Exception nn ?


}