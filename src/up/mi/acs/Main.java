
package up.mi.acs;


import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.text.ParseException;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        Equipage equipage = null;
        try {
            equipage = new Equipage(args[0]);
        } catch (IOException e) {
            System.err.println("Erreur lors de la creation de l'equipage");
            System.exit(0);
        } catch (ParseException e){
        	e.printStackTrace();
        }

        if(equipage.isValidation()){

            equipage.affectationNaive();
            Scanner sc = new Scanner(System.in);
            int reponse = -1;
            boolean termine = false;
            while (!termine) {
                reponse = Menu(sc);
                if( reponse == 1) {
                   ResAuto(equipage,200);
                }

                if(reponse == 2) {
                    ResManuelle(equipage, sc);
                }

                if(reponse == 3) {
                    try {
                        Sauvegarder(sc, equipage);
                    } catch (IOException e) {
                        System.err.println("Une erreur s'est produite lors de l'ecriture dans le fichier de sauvegarde.");
                    }
                }

                if(reponse == 4) {
                    termine = true;
                    System.out.println("Vous quittez le programme.");
                }
                if (reponse == -1) {
                    System.out.println("Votre reponse n'est pas valide");
                }
                
            }

            sc.close();
        }else {
            System.err.println("Le fichier passé en parametres est mal syntaxé");
            System.exit(0);
        }
    }



    /*Menu*/
    public static int Menu(Scanner sc){
        System.out.println("*-------Menu-------*");
        System.out.println("1)->Resolution automatique");
        System.out.println("2)->Resolution manuelle");
        System.out.println("3)-> Sauvegarde");
        System.out.println("4)-> Fin");
        int key = -1;
        try {
            key = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erreur, vous n'avez pas entre un chiffre !");
            sc.nextLine();
        } 
        switch (key) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            default:
                return -1;
        }
    }





    /*Menu pour la resolution manuelle*/
    public static int MenuResManuelle(Scanner sc) {
        System.out.println("*-------Resolution Manuelle-------*");
        System.out.println("1)->Echanger objets");
        System.out.println("2)->Afficher Cout");
        System.out.println("3)-> Revenir au menu precedent");
        int key = -1;
        try {
            key = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Erreur, vous n'avez pas entre un chiffre !");
            sc.nextLine();
        } 
        switch (key) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return -1;
        }
    }





    /**
     * La methode ResAuto est une methode static qui applique un algorithme de
     * resolution qui essaie de trouver le cout minimum d'une solution pour des
     * combinaison de (pirate,objet) qui donne le moins de pirates jaloux possible
     *
     * @param equipage : un parametre de type Equipage qui permet d'acceder au
     *                 differentes informations concerant les pirates et les
     *                 relations entre eux
     * @param k       : un entier donne par l'utilisateur
     *
     * @return l'equipage qui a ete utilise pour cette solution
     */
    public static Equipage ResAuto(Equipage equipage, int k) {
        int i = 0;
        //equipage.affectationStupide();
        int S = equipage.cost();
        while (i <k) {
            Pirate PirateRandom = equipage.getEquipage().get(new Random().nextInt(equipage.getEquipage().size()));
            ArrayList<Pirate> equipagee = equipage.getEquipage();
            ArrayList<Pirate> deteste = new ArrayList<>(); //ArrayList de pirates qui deteste notre pirateRandom

            for(int j=0;j<equipagee.size();j++){
                if(equipage.hateRelation(PirateRandom,equipagee.get(j))){
                    deteste.add(equipagee.get(j));
                }
            }

            if(deteste.size() != 0){
                Pirate VoisinRandom = deteste.get(new Random().nextInt(deteste.size()));
                equipage.changerObjet(PirateRandom, VoisinRandom);
                int S2 = equipage.cost();
                //System.out.println("Valeur de S =  " + S + " Valeur de S2 =  " + S2);

                if (S2 < S) {
                    S = S2;
                }else {
                    equipage.changerObjet(VoisinRandom,PirateRandom);
                }

            }
            i++;

        }
        System.out.println("Le cout de la solution actuelle est " + equipage.cost()+".");

        return equipage;
    }




    /**
     * La methode resManuelle permet de trouver le cout d'une repartition des objets
     * sur les pirates (nombre de pirates jaloux pour une repartition). Permet
     * egalement de changer la repartition entre les pirates choisi par
     * l'utilisateur et affiche les objets que possede chaque pirate apres chaque
     * action
     *
     * @param equipage : est de type Equipage. C'est l'equipage sur lequel on va
     *                 appliquer la resolution
     * @param sc       : un flux d'entree qui permet a l'utilisateur de saisir les
     *                 pirates entre lesquels il veut echanger les objets
     *
     * @return
     */
    public static Equipage ResManuelle(Equipage equipage, Scanner sc) throws IllegalArgumentException {
        boolean termine2 = false;
        while(!termine2) {
            int reponse2 = -1;
            reponse2 = MenuResManuelle(sc);


            if(reponse2 == 1) {

                Pirate Ap=null,Bp=null;
                boolean pirate_exist = false;

                while(pirate_exist != true) {

                    System.out.println("Quel est le nom du premier pirate participant a l'echange?");
                    String A = sc.next();

                    if(equipage.findPirateByID(A) != null) {
                        pirate_exist = true;
                        Ap = equipage.findPirateByID(A);

                    }
                }

                pirate_exist = false;
                while(pirate_exist != true) {
                    System.out.println("Quel est le nom du second pirate participant a l'echange?");
                    String B = sc.next();
                    if(equipage.findPirateByID(B) != null) {
                        Bp = equipage.findPirateByID(B);
                        pirate_exist = true;

                    }
                    else {
                        System.out.println("Le pirate que vous avez entre n'existe pas.");
                    }

                }

                if(pirate_exist) {
                    equipage.changerObjet(Ap, Bp);
                    System.out.println("Les objets des deux pirates ont bien ete echanges. ");
                }else {
                    System.out.println("Erreur lors de l'echange des objets. Veuillez reessayer.");
                }
            }

            if(reponse2 == 2) {
                System.out.println("Le cout de la solution actuelle est " + equipage.cost()+".");
            }
            System.out.println("Voici la repartition actuelle des objets :");
            for(int i=0;i<equipage.getNombrePirate();i++) {
                System.out.println(equipage.getEquipage().get(i).getID() + ":"+equipage.getObjet_recu().get(equipage.getEquipage().get(i)));
            }
            // affiche les objets que possede chaque pirate apres chaque action.
            // Nous avons determines lors de la lecture du sujet qu'il n'etait pas necessaire de le faire apres l'affectation naive,
            // car il serait logique de s'interesser au cout de la solution initiale avant d'effectuer les echanges d'objets.

            if(reponse2 == 3) {
                termine2 = true;
                System.out.println("Vous quittez le menu de r�solution manuelle.");

            }
            if (reponse2 == -1) {
                System.out.println("Votre reponse n'est pas valide");
            }

        }
        return equipage;

    }


    /**
     * La methode Sauvegarder permet de sauvegarder dans un fichier une l'equipage
     * qui a ete utilise lors de l'execution du projet
     *
     * @param sc       : c'est un flux d'entree qui va nous permettre de saisir le
     *                 nom du fichier dans lequel on veut sauvegarder l'equipage
     * @param equipage :est de type Equipage et represente l'equipage a sauvegarder
     *
     */
    public static void Sauvegarder(Scanner sc, Equipage equipage) throws IOException {
        System.out.println("Quel est le nom du fichier dans lequel vous souhaitez enregistrer la sauvegarde actuelle ?");
        String nomFile = sc.next();
        while(true){
            try{
                Writer.saveSolution(nomFile,equipage);
                System.out.println("Votre fichier a bien ete enregistre. ");
                break;
            }catch(FileAlreadyExistsException e){
                System.err.println("Le nom du fichier que vous avez saisie existe déja ");
                nomFile = sc.next();
            }
        }


    }



}