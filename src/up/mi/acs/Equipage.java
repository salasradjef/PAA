package up.mi.acs;

import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * La classe Equipage represente un equipage, elle permet d'effectuer diff�rentes actions sur les pirates
 * dont les principales definir le nombre de pirates de l'equipage et les
 * relations entre eux ainsi que d'attribuer a chaque pirate l'objet recu apres
 * la r�partition des biens selon l'utilisateur.
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
	
	// surcharge qui sert pour le main 2
	public Equipage(ArrayList<String> listPirate, HashMap<String,String> listeDeteste,  HashMap<String,ArrayList<String>> listPref) {
		// creer nombre pirate
		this.nombre_pirate = listPirate.size();
		System.out.println(listPirate);
		System.out.println(listeDeteste);
		System.out.println("listpref = " +listPref);

		
		// creer equipage cad l'arraylist de pirates
		ArrayList<Pirate> equipage = new ArrayList<Pirate>();
		for (String s : listPirate) {
			System.out.println("s =" + s);

			Pirate p = new Pirate(s);
			System.out.println("p =" + p.getID());
			String[] prefs = new String[listPref.size()];
			System.out.println("listPref keys " + listPref.keySet());
			System.out.println("listPref values " + listPref.values());
			System.out.println("test = " +listPref.get(s).get(0));
			for (int i = 0; i <listPref.get(s).size(); i++) {
				prefs[i] = listPref.get(s).get(i);
			}
			p.addPreference(prefs);
			equipage.add(p);
		}
		
		// creer relation entre pirate
		this.relation_pirate = new int[this.nombre_pirate][this.nombre_pirate];
		initRelation();
		int P1, P2;
		boolean valide = false;
		for (String key : listeDeteste.keySet() ) {
			// marche que si les pirates sont appeles par des chiffres tho
			P1 = Integer.parseInt(key);
			P2 = Integer.parseInt(listeDeteste.get(key));
			if ((P1 >= 0 && P1 <=this.nombre_pirate) && (P2 >=0 && P2 <= this.nombre_pirate)) {
				valide = true;
			}
			if ( valide ) {
				this.relation_pirate[P1][P2] = 1;
				this.relation_pirate[P2][P1] = 1;
			}
		}
		
	}
	

	private void initRelation(){

		for(int i =0;i<this.nombre_pirate;i++) {
			for(int j=0;j<this.nombre_pirate;j++) {
				this.relation_pirate[i][j] = 0;
			}
		}
	}

	
	/**
	 * La m�thode addRelation permet � l'utilisateur d'ajouter une relation de rancoeur entre
	 * deux pirates en ajoutant 1 � la matrice d'adjacence qui repr�sente un graphe  
	 * 
	 * @param A repr�sente le pirate qui aura la relation "n'aime pas" avec le
	 *          pirate B
	 * @param B repr�sente le second pirate qui va partager la relation "n'aime pas"
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
			
			System.out.println("La relation  ne s'aiment pas entre " +  A.getID() + " et " + B.getID() +" a bien ete ajoutee" );
		}else {
			System.out.print("Une erreur est survenue et la relation n'a pas pu etre ajoutee ");
		}

	}
	
	/**
	 * La methode findPirateByID permet de r�cup�rer un pirate � partir de son ID (l'ID d'un pirate pass� en
	 * param�tre) un pirate se trouve dans dans l'ArrayList qui repr�sente les
	 * membres de l'equipage
	 * 
	 * @param ID repr�sente l'ID du pirate, ce qui le distingue des autres pirates
	 * @return l'objet pirate dans le cas o� nous l'avons trouv� qu'il appartient �
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
	 * La m�thode affectationNaive permet d'affecter de fa�on na�ve � chaque pirate un objet parmi
	 * ceux qu'il y � partager en fonction des pr�f�rences de chaqu'un. 
	 * Chaque pirate sera associ� � un objet et sera
	 * repr�sent� dans une HashMap <Pirate,objet>
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
	 * La methode changerObjet permet � l'utilisateur d'�changer les objets entre deux pirates en
	 * utilisant la HashMap
	 * 
	 * @param A repr�sente le pirate qui a l'un des objets a echanger
	 * @param B repr�sente le pirate avec qui il va echanger objet avec le pirate
	 *          du premier parametre
	 */
	
	public void changerObjet(Pirate A, Pirate B) {
		String recuA = this.objet_recu.get(A);
		String recuB = this.objet_recu.get(B);
		
		this.objet_recu.replace(A, recuB);
		this.objet_recu.replace(B, recuA);
	}

	/**
	 * La m�thode calcule le nombre de pirates jaloux dans l'�quipage apr�s
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
	 * La m�thode v�rifie si le pirate A est jaloux du pirate B
	 * 
	 * @param A repr�sente un pirate de l'�quipage pour lequel on v�rifie s'il est
	 *          jaloux du pirate B
	 * @param B repr�sente un pirate de l'�quipage
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
	 * La methode hateRelation verifie si deux pirates ne s'aiment pas
	 * 
	 * @param A represente un pirate de l'equipage
	 * @param B represente un pirate de l'equipage
	 * @return true si le pirate A et le pirate B se detestent sinon retourne faux
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
	 * m�thode qui v�rifie si l'ID (nom) x d'un pirate se trouve dans un ArrayList
	 * xs
	 * 
	 * @param x  repr�sente une chaine de caract�res (String)
	 * @param xs repr�sente un tableau dynamique (Tableau de String)
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