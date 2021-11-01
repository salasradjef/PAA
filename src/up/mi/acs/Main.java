package up.mi.acs;

import java.util.Scanner;


public class Main {
	
	
	
	
	public static void main(String[] args) {


		
		
		Scanner sc = new Scanner(System.in);
		
		int nombre_pirate=-1;
		
		
		while(nombre_pirate<0 | nombre_pirate > 26) {
			System.out.println("Bonjour, Quelle est le nombre de pirates ?");
			nombre_pirate = sc.nextInt();
			
			if(nombre_pirate < 0 | nombre_pirate > 26) {
				System.out.println("Le nombre de pirates doit être supérieure a 0 et inférieure a 26");
			}
	
		}
		
		
		
		
		
		Equipage equipage = new Equipage(nombre_pirate);
		
		
		int reponse=-1;

		
		while(reponse != 3) {
			reponse = Menu(sc);
			
			if(reponse == 1) {
				String B;
				Pirate Ap=null,Bp=null;
				boolean pirate_exist = false;	
				
				
				
				while(pirate_exist != true) {
					
					System.out.println("Quelle  est le nom du pirate 1?");
					String A = sc.nextLine();
					
					
					
					if(equipage.findPirateByID(A) != null) {
						pirate_exist = true;
						Ap = equipage.findPirateByID(A);
						

					}
	
					
				}
				
				pirate_exist = false;
				while(pirate_exist != true) {
					B = sc.nextLine();
					System.out.println("Quelle  est le nom du pirate 2?");
					if(equipage.findPirateByID(B) != null) {
						Bp = equipage.findPirateByID(B);
						pirate_exist = true;
						
					}
				
				}
				
				
				if(pirate_exist) {
					equipage.addRelation(Ap,Bp);	
				}else {
					System.out.println("err");
				}
				
			}
			
			
			if(reponse == 2) {
				
				boolean validation = false;
				
				System.out.println("pour ajouter des préferences a un pirate il faut suivre cette syntaxe");
				System.out.println("idPIRATE pref1 pref2 ... prefn");
				while(validation != true){
					

					String preference = sc.nextLine();
					String[] preferences = preference.split(" ");	
					
					if(preferences.length == (nombre_pirate + 1)) {
						validation = true;
					}
					validation =false;
					if(equipage.findPirateByID(preferences[0]) != null) {
						validation = true;
					}
					
					if(validation) {
						Pirate A = equipage.findPirateByID(preferences[0]);
						A.addPreference(preferences);
						
					}else {
						System.out.println("Syntaxe incorrecte");
					}
					
				}
				
				
			}
			
			
			if(reponse == -1) {
				System.out.println("de quoi tu parles ? on a dis 1,2 ou 3 ");
			}
			
			

		}
		
		
		
		
		
		if(reponse ==3) {
			boolean verify = true;
			
			for(int i=0;i<nombre_pirate;i++) {
				Pirate tmp = equipage.getEquipage().get(i);
				String[] pref = tmp.getPreference();
				for(int j =0;j<nombre_pirate;j++) {
					if(pref[j] == null) {
						verify = false;
					}
				}
				
			}
			
			
			if(verify) {
				equipage.affectationNaive();
				
				
				
				while(reponse !=4) {
					reponse = Menu2(sc);
						for(int i=0;i<nombre_pirate;i++) {
						System.out.println(equipage.getEquipage().get(i).getID() + ":" + equipage.objet_recu.get(equipage.getEquipage().get(i)));
						}	
					
					
						if(reponse ==1) {
							String B;
							Pirate Ap=null,Bp=null;
							boolean pirate_exist = false;	
							
							
							
							while(pirate_exist != true) {
								
								System.out.println("Quelle  est le nom du pirate 1?");
								String A = sc.nextLine();
								
								
								
								if(equipage.findPirateByID(A) != null) {
									pirate_exist = true;
									Ap = equipage.findPirateByID(A);
									

								}
				
								
							}
							
							pirate_exist = false;
							while(pirate_exist != true) {
								B = sc.nextLine();
								System.out.println("Quelle  est le nom du pirate 2?");
								if(equipage.findPirateByID(B) != null) {
									Bp = equipage.findPirateByID(B);
									pirate_exist = true;
									
								}
							
							}
							
							
							if(pirate_exist) {
								equipage.changerObjet(Ap, Bp);	
							}else {
								System.out.println("err");
							}

						}
						
						if(reponse==2) {
							System.out.println("reponse 2");
						}
						
						if(reponse == -1) {
							System.out.println("hein");
						}
			
						
					}
			}else {
				System.out.println("err vous n'avez pas encore ajouté toutes les pref");
			}
			
			
			
			
			
		}
		
		sc.close();
		
		
		
		
		
	}
	
	

	
	
	
	
	public static int Menu(Scanner sc) {
		System.out.println("*-------Menu-------*");
		System.out.println("1)-> Ajouter une relation");
		System.out.println("2)->Ajouter des préférences");
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
			return 4;
		default:
			return -1;
		}
	
	
		
	}
	
	
	
	
		
	
}
