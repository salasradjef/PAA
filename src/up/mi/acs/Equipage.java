package up.mi.acs;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;



/**
 * La classe Equipage represente un equipage, elle permet d'effectuer differentes actions sur les pirates
 * dont les principales definir le nombre de pirates de l'equipage et les
 * relations entre eux ainsi que d'attribuer a chaque pirate l'objet recu apres
 * la repartition des biens selon l'utilisateur.
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
	private HashMap<Pirate, String> objet_recu;
	private boolean validation;

	public ArrayList<String> getObjets() {
		return objets;
	}

	public void setObjets(ArrayList<String> objets) {
		this.objets = objets;
	}

	private ArrayList<String> objets;


	/**
	 * Le constructeur de Equipage d'initialiser les attributs de la classe en
	 * recuperant le contenu à partir d'un fichier dont nous passons le chemin en
	 * parametre
	 * 
	 * @param path : c'est un parametre de type String qui donne le chemin vers un
	 *             le fichier ou il y a les informations de l'equipage et qui ont
	 *             ete recuperer dans des variables de la classe Reader
	 * @throws IOException : lève une exception si une erreur d'entrée/sortie s'est
	 *                     produit ( si le fichier auquel nous voulons accéder
	 *                     n'existe pas)
	 */

	public Equipage(String path) throws IOException, ParseException {


		try {
			Reader reader = new Reader(path);
			if(reader.verify()){
				ArrayList<Pirate> listPirate = new ArrayList<>();
				this.objet_recu = new HashMap<>();
				this.equipage = new ArrayList<>();

				for(int i=0;i<reader.getListPirate().size();i++){
						try{
							Pirate tmp = new Pirate(reader.getListPirate().get(i));
							String[] prefs = new String[reader.getListPirate().size()];

							for(int z=0;z<prefs.length;z++){
								prefs[z] = reader.getListPref().get(tmp.getID()).get(z);
							}
							tmp.setPreference(prefs);
							this.objet_recu.put(tmp,null);
							listPirate.add(tmp);

						}catch (NullPointerException e) {
							e.printStackTrace();
						}

				}
				this.nombre_pirate = listPirate.size();
				this.relation_pirate = new int[this.nombre_pirate][this.nombre_pirate];
				this.objets = reader.getListObjet();
				this.equipage.addAll(listPirate);
				initRelation();

				HashMap<String,ArrayList<String>> deteste = reader.getListeDeteste();
				for(String key: deteste.keySet()){
					Pirate a = findPirateByID(key);
					ArrayList <String> tst = deteste.get(key);
					if(tst != null) {
						for (int i = 0; i < tst.size(); i++) {
							Pirate b = findPirateByID(tst.get(i));
							// on verifie que les 2 pirates de la relation deteste existent avant de les ajouter.
							/*if (a == null | b == null) {
								String errorMessage = "Un des pirates appartenant a une relation deteste n'existe pas.";
								throw new ParseException(errorMessage, 0);
							} else {
								this.addRelation(a, b);
							}*/
							this.addRelation(a, b);
						}
					}
				}
				this.validation = true;
			}else {
				this.validation = false;
			}
		} catch (ParseException e) {
			System.err.println(e.getMessage() + " " + e.getErrorOffset());
		}

	}


	/**
	 * Cette méthode permet d'initialiser à 0 le tableau à deux dimension
	 * representer par l'attribut de la classe Equipage "relation_pirate"
	 */

	private void initRelation(){

		for(int i =0;i<this.nombre_pirate;i++) {
			for(int j=0;j<this.nombre_pirate;j++) {
				this.relation_pirate[i][j] = 0;
			}
		}
	}

	/**
	 * Une méthode qui nous permet d'avoir la position d'un pirate passé en
	 * parametre dans l'ArrayList equipage en la parcourant. Dans le cas ou le
	 * pirate n'existe pas dans l'equipage la methode retourne -1
	 * 
	 * @param A
	 * @return la position d'un pirate dans l'ArrayList equipage
	 */

	public int getPosOfPirate(Pirate A){
		for(int i=0;i<this.nombre_pirate;i++){
			if(A.getID().equals(this.equipage.get(i).getID())){
				return i;
			}
		}

		return -1;
	}

	/**
	 * La methode addRelation permet a l'utilisateur d'ajouter une relation de
	 * rancoeur entre deux pirates en ajoutant 1 a la matrice d'adjacence qui
	 * represente un graphe
	 * 
	 * @param A represente le pirate qui aura la relation "n'aime pas" avec le
	 *          pirate B
	 * @param B represente le second pirate qui va partager la relation "n'aime pas"
	 *          avec A
	 */
	public void addRelation(Pirate A, Pirate B) {
		int P_A, P_B;
		boolean validation = false;
		P_A = this.getPosOfPirate(A);
		P_B = this.getPosOfPirate(B);

		if ((P_A >= 0 && P_A <=this.nombre_pirate) && (P_B >=0 && P_B<= this.nombre_pirate)) {
			validation = true;
		}


		if(validation) {
			this.relation_pirate[P_A][P_B] = 1;
			this.relation_pirate[P_B][P_A] = 1;

			//System.out.println("La relation  ne s'aiment pas entre " +  A.getID() + " et " + B.getID() +" a bien ete ajoutee" );
		}else {
			System.out.print("Une erreur est survenue et la relation n'a pas pu etre ajoutee ");
		}

	}

	/**
	 * La methode findPirateByID permet de recuperer un pirate a partir de son
	 * ID (l'ID d'un pirate passe en parametre) un pirate se trouve dans dans
	 * l'ArrayList qui represente les membres de l'equipage
	 * 
	 * @param ID represente l'ID du pirate, ce qui le distingue des autres pirates
	 * @return l'objet pirate dans le cas ou nous l'avons trouve qu'il
	 *         appartient a l'equipage sinon elle retourne null
	 */

	public Pirate findPirateByID(String ID) {

		Pirate tmp=null;
		for (Pirate pirate : this.equipage) {
			if (pirate.getID().equals(ID)) {
				tmp = pirate;
			}
		}

		return tmp;
	}

	/**
	 * La methode affectationNaive permet d'affecter de facon naive et chaque
	 * pirate un objet parmi ceux qu'il y a a partager en fonction des preferences
	 * de chacun. Chaque pirate sera associe a un objet et sera represente
	 * dans une HashMap <Pirate,objet>
	 */
	public void affectationNaive() {
		ArrayList<String> affecte = new ArrayList<>();
		for(int i =0;i<nombre_pirate;i++) {
			Pirate tmp = this.equipage.get(i);
			String[] obj = tmp.getPreference();

			for (String s : obj) {
				if (!isIn(s, affecte)) {
					this.objet_recu.put(tmp, s);
					tmp.setObjet_recu(s);
					affecte.add(s);
					break;
				}
			}
		}
	}


	/**
	 * La methode changerObjet permet a l'utilisateur d'echanger les objets entre deux pirates en
	 * utilisant la HashMap
	 *
	 * @param A represente le pirate qui a l'un des objets a echanger
	 * @param B represente le pirate avec qui il va echanger objet avec le pirate
	 *          du premier parametre
	 */

	public void changerObjet(Pirate A, Pirate B) {
		String recuA = this.objet_recu.get(A);
		String recuB = this.objet_recu.get(B);

		this.objet_recu.replace(A, recuB);
		A.setObjet_recu(recuB);
		this.objet_recu.replace(B, recuA);
		B.setObjet_recu(recuA);
	}

	/**
	 * La methode calcule le nombre de pirates jaloux dans l'equipage apres
	 * l'attribution des objets
	 * 
	 * @return le nombre de pirates jaloux
	 */

	public int cost() {

		ArrayList<String> jaloux = new ArrayList<>(); //Liste des pirates jaloux
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
	 * La methode verifie si le pirate A est jaloux du pirate B
	 *
	 * @param A represente un pirate de l'equipage pour lequel on verifie s'il est
	 *          jaloux du pirate B
	 * @param B represente un pirate de l'equipage
	 * @return true si le pirate A est jaloux du pirate B sinon retourne faux
	 */

	public boolean estJaloux(Pirate A, Pirate B) {
		if(hateRelation(A,B)) {
			int IDrecuA = A.findIDofPref(this.objet_recu.get(A));
			int IDrecuB = A.findIDofPref(this.objet_recu.get(B));

			return IDrecuA > IDrecuB;
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

		int a = this.relation_pirate[this.getPosOfPirate(A)][this.getPosOfPirate(B)];
		int b = this.relation_pirate[this.getPosOfPirate(B)][this.getPosOfPirate(A)];

		return a == 1 && b == 1;

	}



	/**
	 * methode qui verifie si l'ID (nom) x d'un pirate se trouve dans un ArrayList
	 * xs
	 *
	 * @param x  represente une chaine de caracteres (String)
	 * @param xs represente un tableau dynamique (Tableau de String)
	 * @return true si x appartient au  tableau dynamique xs
	 */
	private boolean isIn(String x, ArrayList<String> xs) {
		boolean isIn = false;
		for (String s : xs) {
			if (x.equals(s)) {
				isIn = true;
				break;
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

	public int getNombrePirate() {
		return nombre_pirate;
	}
	public boolean isValidation() {
		return validation;
	}

	public HashMap<Pirate,String> getObjet_recu(){return this.objet_recu;}
}