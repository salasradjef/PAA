package up.mi.acs;


import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main2 {
	
	
	public static void main(String[] args) {
		//lire le fichier en ligne de commande ??
		
		Reader reader = null;
		try {
			reader = new Reader(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("1 =" + reader.getLines());
		System.out.println("2 =" + reader.getListPirate());
		System.out.println("3 =" + reader.getListObjet());
		System.out.println("4 =" + reader.getListPref());

		Equipage equipage = new Equipage(reader.getListPirate(), reader.getListeDeteste(), reader.getListPref());
		Scanner sc = new Scanner(System.in);
		int reponse = -1;
		boolean termine = false;
		while (!termine) {
			reponse = Menu(sc);
			
			if( reponse == 1) {
				ResAuto(equipage,30);
			}
			
			if(reponse == 2) {
				ResManuelle(equipage, sc);
			}
			
			if(reponse == 3) {
				try {
					Sauvegarder(sc, equipage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
	}
	public static int Menu(Scanner sc){
		System.out.println("*-------Menu-------*");
		System.out.println("1)->Résolution automatique");
		System.out.println("2)->Résolution manuelle");
		System.out.println("3)-> Sauvegarde");
		System.out.println("4)-> Fin");
		int key = sc.nextInt();
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
	
	public static Equipage ResAuto(Equipage equipage, int k) {
		int i = 0;
		equipage.affectationNaive();
		int S = equipage.cost();
		while (i <k) {
			Pirate PirateRandom = equipage.getEquipage().get(new Random().nextInt(equipage.getEquipage().size()));
			Pirate VoisinRandom = equipage.getEquipage().get(new Random().nextInt(equipage.getEquipage().size()));
			equipage.changerObjet(PirateRandom, VoisinRandom);
			int S2 = equipage.cost();
			if (S2 < S) {
				S = S2;
			}
			i++;
		}
		return equipage;
	}
	
	public static Equipage ResManuelle(Equipage equipage, Scanner sc) {
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
		System.out.println("Le cout de la solution actuelle est " + equipage.cost()+".");
		return equipage;
	}
	
	
	 public static void Sauvegarder(Scanner sc, Equipage equipage) throws IOException {

		 System.out.println("Quel est le nom du fichier dans lequel vous souhaitez enregistrer la sauvegarde actuelle ?");
		 String nomFile = sc.next();
	     sc.close();
	     Writer writer = new Writer(equipage.objet_recu);
	     writer.writeTofile(nomFile);
	     System.out.println("Votre fichier a bien ete enregistre. ");

	  }
}
	