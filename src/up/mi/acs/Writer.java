package up.mi.acs;

import java.io.*;
//import java.io.FileWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashMap;

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


    /**
	 * Une methode qui permet de cree un fichier dont le nom est donne en parametre.
	 * Dans le cas ou le fichier n'a pas pu se cree un message d'erreur sera renvoye
	 * 
	 * @param name : represente le nom du fichier ou on va stocke le resultat d'une
	 *             resolution qui est de type String
	 * @throws FileAlreadyExistsException : leve une exception si le fichier demande existe deja
	 * 
	 * @return le fichier qui sera cree grace a la methode
	 *  
	 */
    
    

	/**
	 * Methode qui permet d'ecrire a l'interieur d'un fichier ou le nom est precise
	 * en parametre tout les pirates de l'equipage avec l'objet qui leur a ete
	 * affecter lors d'une resolution
	 * 
	 * @param FileName : nom du fichier dans on va sauvegarder les pirates de l'equipage du type String 
	 * @param equipage : c'est l'objet de la classe Equipage dont on veut sauvegarder le contenu 
	 * 
	 */
    
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
                System.err.println("Une erreur s'est produite lors de la creation du fichier de sauvegarde.");

            }
        }

    }







}