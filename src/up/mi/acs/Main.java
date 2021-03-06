package up.mi.acs;

import java.util.Scanner;


public class Main {
	
	
	
	
	public static void main(String[] args) {


		
		
		Scanner sc = new Scanner(System.in);
		
		int nombre_pirate=-1;
		
		
		while(nombre_pirate<0 | nombre_pirate > 26) {
			System.out.println("Bonjour, quel est le nombre de pirates ?");
			nombre_pirate = sc.nextInt();
			
			if(nombre_pirate < 0 | nombre_pirate > 26) {
				System.out.println("Le nombre de pirates doit ?tre sup?rieur ? 0 et inf?rieur ? 26");
			}
	
		}
		
		
		
		
		
		Equipage equipage = new Equipage(nombre_pirate);
		
		
		int reponse=-1;
		boolean termine = false;
		
		while(!termine) {
			reponse = Menu(sc);
			
			if(reponse == 1) {
				Pirate Ap=null,Bp=null;
				boolean pirate_exist = false;	
				
				do {
					System.out.println("Quel est le nom du premier pirate de cette relation?");
					String A = sc.next();
				
					if(equipage.findPirateByID(A) != null) {
						pirate_exist = true;
						Ap = equipage.findPirateByID(A);
					}
	
				}  while(pirate_exist != true);
				
				pirate_exist = false;
				do {
					System.out.println("Quel est le nom du second pirate de cette relation?");
					String B = sc.next();
					if(equipage.findPirateByID(B) != null) {
						Bp = equipage.findPirateByID(B);
						pirate_exist = true;
						
					}
					else {
						System.out.println("Le pirate que vous avez entr? n'existe pas.");
					}
				} while(pirate_exist != true);
				
				
				if((pirate_exist) && (Ap!=Bp)) {
					equipage.addRelation(Ap,Bp);	
				}else {
					System.out.println("Erreur lors de l'ajout de la relation. Veuillez r?essayer.");
				}
				
			}
			
			
			if(reponse == 2) {
				
				boolean validation = false;
				int i = 0;
				System.out.println("Pour ajouter des pr?ferences ? un pirate, merci de suivre la syntaxe suivante :");
				System.out.println("idPIRATE pref1 pref2 ... prefn");
				do{
					i++;
					
					String preference = sc.nextLine();
					String[] preferences = preference.split(" ");	
					
					
					if((equipage.findPirateByID(preferences[0]) != null) && (preferences.length == (nombre_pirate + 1))) {
						validation = true;
					}
					
					if(validation) {
						Pirate A = equipage.findPirateByID(preferences[0]);
						A.addPreference(preferences);
						System.out.println("Les pr?f?rences du pirate " + A.getID() + " ont ?t? mises ? jour.");
						
					}
					if ( (!validation ) && i>1)  {
						System.out.println("Syntaxe incorrecte, les pr?f?rences du pirate n'ont pas ?t? mises ? jour.");
					}
					
				} while(validation != true);
				
				
			}
			
		//}
		
			if(reponse ==3) {
				boolean verify = true;
				
				for(int i=0;i<nombre_pirate;i++) {
					Pirate tmp = equipage.getEquipage().get(i);
					String[] pref = tmp.getPreference();
					for(int j =0;j<nombre_pirate;j++) {
						if(pref[j] == null) {
							verify = false;
							System.out.println("Il manque une pr?f?rence pour le pirate " + tmp.getID());
						}
					}
					
				}
				
				
				if(verify) {
					equipage.affectationNaive();
					termine = true;
				}
				else {
					System.out.println("Erreur : Vous n'avez pas encore ajout? toutes les pr?f?rences pour chacun des pirates.");
					System.out.println("Veuillez ajouter les pr?f?rences manquantes avant de continuer. ");
					
				}
			}
			if ( reponse == -1) {
				System.out.println("Votre r?ponse n'est pas valide");
			}
		} 
				
				boolean termine2 = false;
				while(!termine2) {
					reponse = Menu2(sc);
						
					
						if(reponse == 1) {
							//String B;
							Pirate Ap=null,Bp=null;
							boolean pirate_exist = false;	
							
							while(pirate_exist != true) {
								
								System.out.println("Quel est le nom du premier pirate participant ? l'?change?");
								String A = sc.next();
								
								if(equipage.findPirateByID(A) != null) {
									pirate_exist = true;
									Ap = equipage.findPirateByID(A);
							
								}
							}
							
							pirate_exist = false;
							while(pirate_exist != true) {
								System.out.println("Quel est le nom du second pirate participant ? l'?change?");
								String B = sc.next();
								if(equipage.findPirateByID(B) != null) {
									Bp = equipage.findPirateByID(B);
									pirate_exist = true;
									
								}
								else {
									System.out.println("Le pirate que vous avez entr? n'existe pas.");
								}
								
							}
							
							
							if(pirate_exist) {
								equipage.changerObjet(Ap, Bp);
								System.out.println("Les objets des deux pirates ont bien ?t? ?chang?s. ");
							}else {
								System.out.println("Erreur lors de l'?change des objets. Veuillez r?essayer.");
							}

						}
						
						if(reponse == 2) {
							System.out.println("Le co?t de la solution actuelle est " + equipage.cost()+".");
						}
						
						for(int i=0;i<nombre_pirate;i++) {
							System.out.println(equipage.getEquipage().get(i).getID() + ":" + "o"+equipage.objet_recu.get(equipage.getEquipage().get(i)));
							}	
						
						if(reponse == 3) {
							termine2 = true;
							System.out.println("Vous quittez le programme.");
							
						}
						if (reponse == -1) {
							System.out.println("Votre r?ponse n'est pas valide");
						}
				
					} sc.close();
			/*}
			else {
				System.out.println("Erreur : Vous n'avez pas encore ajout? toutes les pr?f?rences pour chacun des pirates.");
				System.out.println("Veuillez ajouter les pr?f?rences manquantes avant de continuer. ");
				
			}*/
	
	}
	
	
	
	

	
	
	
	
	public static int Menu(Scanner sc) {
		System.out.println("*-------Menu-------*");
		System.out.println("1)->Ajouter une relation");
		System.out.println("2)->Ajouter des pr?f?rences");
		System.out.println("3)->Fin");
		int key = sc.nextInt();
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
	
	
	
	public static int Menu2(Scanner sc) {
		System.out.println("*-------Menu 2-------*");
		System.out.println("1)->Echanger objets");
		System.out.println("2)->Afficher Co?t");
		System.out.println("3)-> Fin");
		int key = sc.nextInt();
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
	
	
	
	
		
	
}