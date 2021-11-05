package up.mi.acs;

import java.util.Scanner;


public class Main {
	
	
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		
		int nombre_pirate=-1; 
		
		
		while(nombre_pirate<1 | nombre_pirate > 26) {
			System.out.println("Bonjour, quel est le nombre de pirates ?");
			nombre_pirate = sc.nextInt();
			
			if(nombre_pirate < 1 | nombre_pirate > 26) {
				System.out.println("Le nombre de pirates doit être compris en 1 et 26.");
			}
	
		}
		
		
		Equipage equipage = new Equipage(nombre_pirate);
		
		
		int reponse=-1; // valeur par default
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
						System.out.println("Le pirate que vous avez entré n'existe pas.");
					}
				} while(pirate_exist != true);
				
				
				if((pirate_exist) && (Ap!=Bp)) {
					equipage.addRelation(Ap,Bp);	
				}else {
					System.out.println("Erreur lors de l'ajout de la relation. Veuillez réessayer.");
				}
				
			}
			
			
			if(reponse == 2) {
				
				boolean validation = false;
				int i = 0;  
				// compteur qui sert à ne pas déclencher le message d'erreur du else ci-dessous avant l'écriture dans le menu. 
				// En effet, le sc.nextLine() fait faire un passage dans la boucle do while avant de récupérer ce qui est entré par l'utilisateur.
				// Ce problème n'existe pas ailleurs car on utilise sc.next(), or on ne peut pas faire cela ici  car cela produit une erreur lorsque l'on fait .split()
				
				System.out.println("Pour ajouter des préferences à un pirate, merci de suivre la syntaxe suivante :");
				System.out.println("idPIRATE pref1 pref2 ... prefn");
				// on indique la syntaxe correcte pour que le split(" ") fonctionne correctement
				// Par exemple , il faut taper 'A 1 2 3' dans le cas où nombre_pirate =3.
				
				do{
					i++;
					
					String preference = sc.nextLine();
					String[] preferences = preference.split(" ");	
					int tests = 0; // compteur qui sert à vérifier que chacune des préférences entrées au clavier est valable.
					
					if((equipage.findPirateByID(preferences[0]) != null) && (preferences.length == (nombre_pirate + 1))) {
						for (int j = 1; j< preferences.length; j++) {
							if ( (preferences[j].matches("-?\\d+")) && (Integer.parseInt(preferences[j]) <= nombre_pirate )) {
								// on vérifie que les préférences entrées sont bien des entiers avec  .matches(), le "-?\\d+" est une syntaxe trouvée après recherche web
								// et on vérifie que l'entier en question est bien inférieur au nombre de pirates, càd correspond à un objet qu'il est possible d'attribuer.
								tests++;
							}
						}
						
						if (tests == nombre_pirate) {
							validation = true;
						}
					}
					
					if(validation) {
						Pirate A = equipage.findPirateByID(preferences[0]);
						A.addPreference(preferences);
						System.out.println("Les préférences du pirate " + A.getID() + " ont été mises à jour.");
						
					}
					if ( (!validation ) && i>1)  {
						System.out.println("Syntaxe incorrecte, les préférences du pirate n'ont pas été mises à jour.");
					}
					
				} while(validation != true);
				
				
			}
			
		
		
			if(reponse ==3) {
				boolean verify = true;
				
				for(int i=0;i<nombre_pirate;i++) {
					Pirate tmp = equipage.getEquipage().get(i);
					String[] pref = tmp.getPreference();
					for(int j =0;j<nombre_pirate;j++) {
						if(pref[j] == null) {
							verify = false;
							System.out.println("Il manque une préférence pour le pirate " + tmp.getID());
						} // Cette boucle vérifie que chaque pirate possède bien n préférences. Elle affiche un message pour chaque préférence manquante.
					}
					
				}
				
				
				if(verify) {
					equipage.affectationNaive();
					termine = true;
				}
				else {
					System.out.println("Erreur : Vous n'avez pas encore ajouté toutes les préférences pour chacun des pirates.");
					System.out.println("Veuillez ajouter les préférences manquantes avant de continuer. ");
					
				}
			}
			if ( reponse == -1) {
				System.out.println("Votre réponse n'est pas valide");
			} // le default du switch du menu renvoie -1, donc toutes valeurs qui ne sont pas entre 1 et 3 sont renvoyés dans ce if.
		} 
				
				boolean termine2 = false;
				while(!termine2) {
					reponse = Menu2(sc);
						
					
						if(reponse == 1) {
							
							Pirate Ap=null,Bp=null;
							boolean pirate_exist = false;	
							
							while(pirate_exist != true) {
								
								System.out.println("Quel est le nom du premier pirate participant à l'échange?");
								String A = sc.next();
								
								if(equipage.findPirateByID(A) != null) {
									pirate_exist = true;
									Ap = equipage.findPirateByID(A);
							
								}
							}
							
							pirate_exist = false;
							while(pirate_exist != true) {
								System.out.println("Quel est le nom du second pirate participant à l'échange?");
								String B = sc.next();
								if(equipage.findPirateByID(B) != null) {
									Bp = equipage.findPirateByID(B);
									pirate_exist = true;
									
								}
								else {
									System.out.println("Le pirate que vous avez entré n'existe pas.");
								}
								
							}
							
							
							if(pirate_exist) {
								equipage.changerObjet(Ap, Bp);
								System.out.println("Les objets des deux pirates ont bien été échangés. ");
							}else {
								System.out.println("Erreur lors de l'échange des objets. Veuillez réessayer.");
							}

						}
						
						if(reponse == 2) {
							System.out.println("Le coût de la solution actuelle est " + equipage.cost()+".");
						}
						
						for(int i=0;i<nombre_pirate;i++) {
							System.out.println(equipage.getEquipage().get(i).getID() + ":" + "o"+equipage.objet_recu.get(equipage.getEquipage().get(i)));
							}	// affiche les objets que possède chaque pirate après chaque action ( 1 ou 2 ). 
								// Nous avons déterminés à la lecture du sujet qu'il n'était pas nécessaire de le faire après l'affectation naive,
								// car il serait logique de s'intéresser au coût de cette dernière avant d'effectuer les échanges d'objets.
						
						if(reponse == 3) {
							termine2 = true;
							System.out.println("Vous quittez le programme.");
							
						}
						if (reponse == -1) {
							System.out.println("Votre réponse n'est pas valide");
						}
				
					} sc.close();
			
	}
	
	
	
	
	
	
	
	public static int Menu(Scanner sc) {
		System.out.println("*-------Menu-------*");
		System.out.println("1)->Ajouter une relation");
		System.out.println("2)->Ajouter des préférences");
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
		System.out.println("2)->Afficher Coût");
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