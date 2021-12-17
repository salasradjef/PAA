package up.mi.acs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Une classe qui permet de recuperer le contenu d'un fichier à travers un
 * chemin et de stocker chaque information dans l'attribut qui lui convient.
 * Elle fait egalement divers verifications pour s'assurer que le contenu du
 * fichier a bien été copier et ne contient pas le caracteres incomprehensible
 *
 * @author Salas Radjef
 * @author Christopher Viet
 * @author Amel Nait Amer
 *
 */

public class Reader {
	private String filePath;
	private ArrayList<String> Lines;
	private ArrayList<String> listPirate;
	private ArrayList<String> listObjet;

	private HashMap<String,ArrayList<String>> listeDeteste;
	private HashMap<String,ArrayList<String>> listPref;
	private ArrayList<Integer> idOfErrors = new ArrayList<>();
	//private boolean verify = false;


	/**
	 * Le constructeur de la classe permet de charger le contenu du fichier dont
	 * nous avons donner le chemin en paremetre et remplir en fonction de ca les
	 * attributs de la classe Reader. Les donnees a recuperer sont les noms des
	 * pirates,les objets,les preferences de chaque pirate et les pirates qui se
	 * detestent dans l'equipage
	 *
	 * @param path :c'est un parametre de type String qui donne le chemin vers un le
	 *             fichier ou il y a les informations concernant l'equipage
	 * @throws IOException    : leve une exception si une erreur d'entree/sortie
	 *                        s'est produit ( si le fichier auquel nous voulons
	 *                        accéder n'existe pas)
	 * @throws ParseException : leve une exception et renvoie le message d'erreur
	 *                        qui a ete precise
	 */

	public Reader(String path) throws IOException, ParseException {
		this.setFilePath(path);

		FileReader r = null;
		try {
			r = new FileReader(new File(path));

		}catch(FileNotFoundException | NullPointerException e) {
			System.err.println("Erreur : le fichier est introuvable, veuillez recommencer");
			System.exit(0);
		}

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(r);

		}catch(NullPointerException e) {
			System.err.println("Erreur lors de la creation du buffered reader, veuillez recommencer");
			System.exit(0);
		}

		String s = ""; int z=0;
		this.Lines = new ArrayList<>();
		while(s != null) {
			s = reader.readLine();
			z++;
			if(s != null){
				if(s.equals("")){
					System.out.println("la ligne " + z +   " est vide, veuillez la supprimer");
					System.exit(0);

				}else {
					Lines.add(s);
				}
			}

		}

		// on cree les arraylist dont on va avoir besoin a l'interieur de la boucle
		this.listPirate = new ArrayList<>();
		this.listObjet = new ArrayList<>();
		this.listPref = new HashMap<>();
		
		// ces deux arraylist nous permettent de verifier qu'il n'y a qu'une occurence de chaque ligne, lorsque l'on utilise des hashmaps pour
		// les stocker
		
		ArrayList<String> linesPreferences = new ArrayList<>();
		ArrayList<String> linesDeteste = new ArrayList<>();
		String errorMessage = "Erreur a la ligne ";
		
		
		// pour toute cette boucle, on sait que les pirates sont tous definis en premier lieu dans le fichier
		// puis les objets sont definis, les deteste et enfin les preferences
		// si ce n'est pas le cas, le programme ne fonctionnera pas correctement
		
		for(int i=0;i<Lines.size();i++){
			if(Lines.get(i).startsWith("pirate")){
				String nomPirate = Lines.get(i).split("[\\(\\)]")[1];
				
				// on verifie que chaque pirate n'apparait qu'une fois
				if(oneOccurence(nomPirate,this.listPirate)){
					listPirate.add(nomPirate);
				}else{
					throw new ParseException(errorMessage,(i+1));
				}

			}else if(Lines.get(i).startsWith("objet")){
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				
				// on verifie que chaque objet n'apparait qu'une fois
				if(oneOccurence(nomObjet,this.listObjet)){
					listObjet.add(nomObjet);
				}else{
					throw new ParseException(errorMessage,(i+1));
				}
				
				// comme explique au dessus, si la prochaine ligne commence par deteste, on sait qu'on a fini de traiter les objets
				// on peut donc v�rifier que le nombre d'objet est egal au nombre de pirate, et initialiser la hashmap des relations deteste
				if(Lines.get(i+1).startsWith("deteste")){
					verifyPirateObject();
					InitDeteste();
				}

			}else if(Lines.get(i).startsWith("deteste")){

				// on verifie que chaque deteste n'apparait qu'une fois
				if(oneOccurence(Lines.get(i),linesDeteste)){
					linesDeteste.add(Lines.get(i));
					String[] pres = Lines.get(i).split("[\\(\\)]");
					String[] nomsPirates = pres[1].split(",");
					
					// on verifie que les deux pirates existent
					if(!isIn(nomsPirates[0],this.listPirate) || !isIn(nomsPirates[1],this.listPirate)){
						System.err.println("Un des deux pirates indique dans la relation deteste n'existe pas");
						throw new ParseException(errorMessage,(i+1));
					}else {
						String pirate1 = nomsPirates[0];
						String pirate2 = nomsPirates[1];
						if(!pirate1.equals(pirate2)){ //Verifier que les pirates dans la relation deteste sont differents
							ArrayList<String> ts = this.listeDeteste.get(pirate1);
							if(ts == null){
								ts = new ArrayList<>();
								ts.add(pirate2);
								this.listeDeteste.put(pirate1,ts);
							}else {
								ts.add(pirate2);
								this.listeDeteste.put(pirate1,ts);
							}
						}else {
							System.err.println("Le meme pirate ne peux pas se detester lui meme ");
							throw new ParseException(errorMessage,(i+1));
						}



					}

				}else {
					throw new ParseException(errorMessage,(i+1));
				}

			}else if(Lines.get(i).startsWith("preferences")){
				
				// on verifie que chaque preferences n'apparait qu'une fois
				if(oneOccurence(Lines.get(i),linesPreferences)){
					linesPreferences.add(Lines.get(i));
					String prefss = Lines.get(i).split("[\\(\\)]")[1];
					String[] prefs = prefss.split(",");
					
					// on verifie que le pirate existe
					if(isIn(prefs[0],this.listPirate)){
						
						// on verifie qu'il y a autant de preferences que d'objets
						if((prefs.length-1) == this.listObjet.size()){
							ArrayList<String> tmp = new ArrayList<>();
							for(int j=1;j<prefs.length;j++){
								
								// on verifie que chaque objet de la preference existe
								if(!isIn(prefs[j],this.listObjet)){
									System.err.println("Un objet indique dans la preference n'existe pas");
									throw new ParseException(errorMessage,(i+1));
								}
							}

							for(int j=1;j<=listObjet.size();j++) {
								tmp.add(prefs[j]);
							}

							listPref.put(prefs[0], tmp);

						}else {
							System.err.println("Erreur au niveau du nombre d'objets dans la preference");
							throw new ParseException(errorMessage, (i+1));
						}


					}else{
						System.err.println("Le pirate indique dans la preference n'existe pas");
						throw new ParseException(errorMessage,(i+1));
					}



				}else {
					throw new ParseException(errorMessage,(i+1));
				}
			}else {
				throw new ParseException(errorMessage, i+1 );
			}


		}



		r.close();

	}

	/**
	 * methode qui permet d'init une HashMap(relation deteste entre pirates)
	 * chaque pirate sera represente comme une key, et une ArrayList sera associe a cette key, elle represente la liste des noms
	 * de pirates que la key deteste
	 */


	private void InitDeteste(){
		this.listeDeteste = new HashMap<>();
		for(int i=0;i<this.listPirate.size();i++){
			this.listeDeteste.put(this.listPirate.get(i),null);
		}
	}





	/*Method to verify*/


	/**
	 * methode qui permet de verifier si le nombre de pirates est le meme que le
	 * nombre d'objets a se partager en levant une exception dasn le cas echeant
	 *
	 */
	private void verifyPirateObject() throws ParseException {
		if(listPirate.size() != listObjet.size()){
			throw new ParseException("Le nombre de pirates n'est pas egale au nombre d'objets",0);
		}
	}

	/**
	 * methode qui permet de verifier si le fichier contient des elements illisible
	 *
	 * @retrun true si le fichier n'a pas d'erreur sinon false
	 */

	public boolean verify() throws ParseException{
		if(verifySyntax()){
			return true;
		}else {

			String errorMessage;
			if (this.idOfErrors.size() == 0) {
				 errorMessage = "La syntaxe du fichier contient des elements illisibles pour le parser, veuillez verifier votre fichier ";
			}else {
				errorMessage = "La syntaxe du fichier contient des elements illisibles pour le parser, veuillez verifier votre fichier Verifiez ";
				if(this.idOfErrors.size() > 1){
					errorMessage += "les lignes ";
					for(int i=0;i<this.idOfErrors.size();i++){
						if(i == (this.idOfErrors.size() -1)){
							errorMessage += (this.idOfErrors.get(i)+1) +" ";
						}else {
							errorMessage += (this.idOfErrors.get(i)+1)  + ",";
						}
					}

				}else {
					errorMessage += "la ligne " + (this.idOfErrors.get(0)+1)+ " ";
				}
				errorMessage += "De votre fichier d'equipage";
			}
			throw new ParseException(errorMessage, 0);

			
		}
	}




	/**
	 * methode qui verifie que toutes les lignes de notre fichier se termine par "."
	 *
	 * @return vrai si toute les lignes se termine par un point sinon retourne faux
	 */
	private boolean verifySyntax(){
		boolean verify = true;
		for(int i=0;i<this.Lines.size();i++){
			if(this.Lines.get(i)!= null){
				if(!this.Lines.get(i).endsWith(".")){
					idOfErrors.add(i);
					verify = false;
				}
			}
		}

		return verify;
	}







	/*Getters and setters*/

	public String getFilePath() {return filePath;}



	public void setFilePath(String filePath) {this.filePath = filePath;}

	public ArrayList<String> getLines() {return Lines;}



	public void setLines(ArrayList<String> lines) {	Lines = lines;}



	public ArrayList<String> getListPirate() {return listPirate;}



	public void setListPirate(ArrayList<String> listPirate) {this.listPirate = listPirate;}



	public ArrayList<String> getListObjet() {return listObjet;}



	public void setListObjet(ArrayList<String> listObjet) {this.listObjet = listObjet;}



	public HashMap<String, ArrayList<String>> getListPref() {
		return listPref;
	}


	public HashMap<String, ArrayList<String>> getListeDeteste() {
		return listeDeteste;
	}

	public void setListeDeteste(HashMap<String, ArrayList<String>> listeDeteste) {
		this.listeDeteste = listeDeteste;
	}

	public void setListPref(HashMap<String, ArrayList<String>> listPref) {
		this.listPref = listPref;
	}




	/*Util*/
	private boolean isIn(String x, ArrayList<String> xs) {
		boolean isIn = false;
		for(int i =0;i<xs.size();i++) {
			if(x.equals(xs.get(i))) {
				isIn =true;

			}
		}
		return isIn;
	}


	/*private boolean isIn(String x, String[] xs) {
		boolean isIn = false;
		for(int i =0;i<xs.length;i++) {
			if(x.equals(xs[i])) {
				isIn =true;
			}
		}
		return isIn;
	}*/


	private boolean oneOccurence(String x, ArrayList<String> xs){
		int cmp =0;
		if(xs== null){
			return true;
		}else {
			for(String s : xs ){
				if(x.equals(s)){
					cmp++;
				}
			}
			if(cmp == 0){
				return true;
			}else {
				return false;
			}

		}

	}









}