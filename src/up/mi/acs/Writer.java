package up.mi.acs;

import java.io.*;
//import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
//import java.util.HashMap;

/**
 * Cette classe permet d'enregistrer le resultat d'une resolution mannuelle ou
 * automatique dans un fichier
 * 
 * @author RADJEF SALAS
 * @author VIET CHRISTOPHER
 * @author NAIT AMER AMEL
 *
 */

public class Writer {
    //private HashMap<Pirate,String> solution;

    /*public Writer(HashMap<Pirate,String> solution){
        //this.solution = solution;
    }*/

    /*public static File accessTofile(String name) throws FileAlreadyExistsException {
        File file = new File("./src/up/mi/acs/results/" + name);

        if(file.exists()){
                throw new FileAlreadyExistsException(file.getAbsolutePath());
        }else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Une erreur s'est produite lors de la création du fichier de sauvegarde.");
            }

        }

        return file;
    }*/


    public static void saveSolution(String fileName,Equipage equipage) throws IOException {
        File file = new File("./src/up/mi/acs/results/" + fileName);
        if(file.exists()){
            throw new FileAlreadyExistsException(file.getAbsolutePath());
        }else {
            try {
                file.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for(int i=0;i<equipage.getEquipage().size();i++) {
                    writer.write(equipage.getEquipage().get(i).getID() + ":" +equipage.getObjet_recu().get(equipage.getEquipage().get(i)) + "\n");

                }
                writer.close();

            } catch (IOException e) {
                System.err.println("Une erreur s'est produite lors de la création du fichier de sauvegarde.");

            }
        }







    }







}