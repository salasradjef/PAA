package up.mi.acs;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * La classe Equipage représente un equipage, elle permet d'effectuer différentes actions sur les pirates
 * dont les principales definir le nombre de pirates de l'equipage et les
 * relations entre eux ainsi que d'attribuer à chaque pirate l'objet reçu après
 * la répartition des biens selon l'utilisateur.
 * @author Salas RADJEF
 * @author Christopher VIET
 * @author Amel NAIT AMER
 * 
 *
 */
public class Equipage {
	private ArrayList<Pirate> equipage;
	private int[][] relation_pirate;
	private int nombre_pirate;
	HashMap<Pirate, String> objet_recu;
	
	
	
	
	
	public Equipage(int nombre_pirate) {
		this.nombre_pirate = nombre_pirate;
		this.equipage = new ArrayList <Pirate>();
		this.objet_recu = new HashMap<Pirate, String>();
		
		for(int i=0;i<nombre_pirate;i++) {
			Pirate tmp = new Pirate(ALPHABET.ALPHABET[i]);
			tmp.setPreference(new String[this.nombre_pirate]);
			this.equipage.add(tmp);
			objet_recu.put(tmp, null);
		
		}
		this.relation_pirate = new int[this.nombre_pirate][this.nombre_pirate];
		initRelation();
		
	}
	

	private void initRelation(){

		for(int i =0;i<this.nombre_pirate;i++) {
			for(int j=0;j<this.nombre_pirate;j++) {
				this.relation_pirate[i][j] = 0;
			}
		}
	}

	
	/**
	 * La méthode addRelation permet à l'utilisateur d'ajouter une relation de rancoeur entre
	 * deux pirates en ajoutant 1 à la matrice d'adjacence qui représente un graphe  
	 * 
	 * @param A représente le pirate qui aura la relation "n'aime pas" avec le
	 *          pirate B
	 * @param B représente le second pirate qui va partager la relation "n'aime pas"
	 *          avec A
	 */
	public void addRelation(Pirate A, Pirate B) {
		int P_A, P_B;
		Boolean validation = false;
		P_A = ALPHABET.getIDofALPHABET(A.getID());
		P_B = ALPHABET.getIDofALPHABET(B.getID());
		
		if ((P_A >= 0 && P_A <=this.nombre_pirate) && (P_B >=0 && P_B<= this.nombre_pirate)) {
			validation = true;
		}
		
		
		if(validation) {
			this.relation_pirate[P_A][P_B] = 1;
			this.relation_pirate[P_B][P_A] = 1;
			
			System.out.println("La relation  ne s'aiment pas entre " +  A.getID() + " et " + B.getID() +" a bien été ajoutée" );
		}else {
			System.out.print("Une erreur est survenue et la relation n'a pas pu être ajoutée ");
		}
		
		
	
		
		
	}
	
	/**
	 * La methode findPirateByID permet de récupérer un pirate à partir de son ID (l'ID d'un pirate passé en
	 * paramètre) un pirate se trouve dans dans l'ArrayList qui représente les
	 * membres de l'equipage
	 * 
	 * @param ID représente l'ID du pirate, ce qui le distingue des autres pirates
	 * @return l'objet pirate dans le cas où nous l'avons trouvé qu'il appartient à
	 *         l'equipage sinon elle retourne null
	 */
	
	public Pirate findPirateByID(String ID) {
		
		Pirate tmp=null;
		for(int i = 0;i<this.equipage.size();i++) {
			if(this.equipage.get(i).getID().equals(ID)  ) {
				tmp = this.equipage.get(i);
			}
		}
		
		return tmp;
	}
	

	
	/**
	 * La méthode affectationNaive permet d'affecter de façon naïve à chaque pirate un objet parmi
	 * ceux qu'il y à partager en fonction des préférences de chaqu'un. 
	 * Chaque pirate sera associé à un objet et sera
	 * représenté dans une HashMap <Pirate,objet>
	 */
	public void affectationNaive() {
		ArrayList<String> affecte = new ArrayList<String>();
		for(int i =0;i<nombre_pirate;i++) {
			Pirate tmp = this.equipage.get(i);
			String[] obj = tmp.getPreference();
			
			for(int j=0;j<obj.length;j++) {
				
				if(!isIn(obj[j], affecte)) {
					this.objet_recu.put(tmp, obj[j]);
					affecte.add(obj[j]);
					break;
				}
			}
			
			
		}
		
	
		
	}
	
	/**
	 * La methode changerObjet permet à l'utilisateur d'échanger les objets entre deux pirates en
	 * utilisant la HashMap
	 * 
	 * @param A représente le pirate qui a l'un des objets a echanger
	 * @param B représente le pirate avec qui il va echanger objet avec le pirate
	 *          du premier paramètre
	 */
	
	public void changerObjet(Pirate A, Pirate B) {
		String recuA = this.objet_recu.get(A);
		String recuB = this.objet_recu.get(B);
		
		this.objet_recu.replace(A, recuB);
		this.objet_recu.replace(B, recuA);
		
		
		
	}
	

	/**
	 * La méthode calcule le nombre de pirates jaloux dans l'équipage après
	 * l'attribution des objets
	 * 
	 * @return le nombre de pirates jaloux
	 */
	
	public int cost() {
		
		ArrayList<String> jaloux = new ArrayList<String>(); //Liste des pirates jaloux 
		for(int i=0;i<this.nombre_pirate;i++) {
			for(int j =0;j<this.nombre_pirate;j++) {
				if(i !=j) { 
					if(estJaloux(this.equipage.get(i), this.equipage.get(j))) {
						if(!isIn(this.equipage.get(i).getID(), jaloux)) {
							jaloux.add(this.equipage.get(i).getID());
						}
					}
				}
			}
		}
		
		return jaloux.size();
	}
	
	/**
	 * La méthode vérifie si le pirate A est jaloux du pirate B
	 * 
	 * @param A représente un pirate de l'équipage pour lequel on vérifie s'il est
	 *          jaloux du pirate B
	 * @param B représente un pirate de l'équipage
	 * @return true si le pirate A est jaloux du pirate B sinon retourne faux
	 */
	
	public boolean estJaloux(Pirate A, Pirate B) {
		if(hateRelation(A,B)) {
			
			
			int IDrecuA = A.findIDofPref(this.objet_recu.get(A));
			int IDrecuB = A.findIDofPref(this.objet_recu.get(B));

			if(IDrecuA > IDrecuB) {
				return true;
			}else {
				return false;
			}
			
			}else {
			return false;
		}
		
		
	}
	
	/**
	 * La méthode hateRelation vérifie si deux pirates ne s'aiment pas
	 * 
	 * @param A représente un pirate de l'équipage
	 * @param B représente un pirate de l'équipage
	 * @return true si le pirate A et le pirate B se détestent sinon retourne faux
	 */
	
	public boolean hateRelation(Pirate A , Pirate B) {
	
	int a = this.relation_pirate[ALPHABET.getIDofALPHABET(A.getID())][ALPHABET.getIDofALPHABET(B.getID())];	
	int b = this.relation_pirate[ALPHABET.getIDofALPHABET(B.getID())][ALPHABET.getIDofALPHABET(A.getID())];
	
		if(a ==1 && b ==1) {
			return true;
		}else {
			return false;
		}
	
	}
	
	
	
	/**
	 * méthode qui vérifie si l'ID (nom) x d'un pirate se trouve dans un ArrayList
	 * xs
	 * 
	 * @param x  représente une chaine de caractéres (String)
	 * @param xs représente un tableau dynamique (Tableau de String)
	 * @return true si x appartient au  tableau dynamique xs
	 */
	private boolean isIn(String x, ArrayList<String> xs) {
		 boolean isIn = false;
		for(int i =0;i<xs.size();i++) {
			if(x.equals(xs.get(i))) {
				isIn =true;
			}
		}
		
		
		
		return isIn;
	}
	
	private boolean isIn(String x, String[] xs) {
		 boolean isIn = false;
		for(int i =0;i<xs.length;i++) {
			if(x.equals(xs[i])) {
				isIn =true;
			}
		}
		
		
		
		return isIn;
	}
	

	public ArrayList<Pirate> getEquipage() {
		return equipage;
	}





	public void setEquipage(ArrayList<Pirate> equipage) {
		this.equipage = equipage;
	}





	public int[][] getRelation_pirate() {
		return relation_pirate;
	}





	public void setRelation_pirate(int[][] relation_pirate) {
		this.relation_pirate = relation_pirate;
	}
	

}