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
 * chemin et le stocker dans ses attributs
 * 
 * @author ameln
 *
 */

public class Reader {
	private String filePath;
	private ArrayList<String> Lines;
	private ArrayList<String> listPirate;
	private ArrayList<String> listObjet;
	private HashMap<String, String> listeDeteste;
	private HashMap<String, ArrayList<String>> listPref;
	private ArrayList<Integer> idOfErrors = new ArrayList<>();
	// private boolean verify = false;

	/**
	 * Le constructeur de la classe permet de charger le contenu du fichier dont
	 * nous avons donner le chemin en paremetre et remplir en fonction de ca les
	 * attributs de la classe Reader Les donnees a recuperer sont les noms des
	 * pirates,les objets,les preferences de chaque pirate et les pirates qui se
	 * detestent dans l'equipage
	 * 
	 * @param path :c'est un parametre de type String qui donne le chemin vers un le
	 *             fichier ou il y a les informations concernant l'equipage
	 * @throws IOException : lève une exception si une erreur d'entrée/sortie s'est
	 *                     produit ( si le fichier auquel nous voulons accéder
	 *                     n'existe pas)
	 */

	public Reader(String path) throws IOException {
		this.setFilePath(path);

		FileReader r = null;
		try {
			r = new FileReader(new File(path));

		} catch (FileNotFoundException | NullPointerException e) {
			System.err.println("Erreur : le fichier est introuvable, veuillez recommencer");
			System.exit(0);
		}

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(r);

		} catch (NullPointerException e) {
			System.err.println("Erreur lors de la creation du buffered reader, veuillez recommencer");
			System.exit(0);
		}

		String s = "";
		this.Lines = new ArrayList<>();
		while (s != null) {
			s = reader.readLine();
			Lines.add(s);

		}

		/* Chargement des pirates */
		this.listPirate = new ArrayList<>();
		boolean lastPirate = false;
		int cmp = 0;

		while (!lastPirate) {
			String[] pInfo = Lines.get(cmp).split("\\(");
			if (pInfo[0].equals("pirate")) {
				String nomPirate = Lines.get(cmp).split("[\\(\\)]")[1];
				listPirate.add(nomPirate);
				cmp++;
			} else {
				lastPirate = true;
			}

		}

		/* Chargement des objets */
		this.listObjet = new ArrayList<>();
		for (int i = 0; i < Lines.size() - 1; i++) {
			String oInfo = Lines.get(i).split("\\(")[0];
			if (oInfo.equals("objet")) {
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				listObjet.add(nomObjet);
			}

		}

		/** Chargement des relations "deteste" **/
		this.listeDeteste = new HashMap<>();
		for (int i = 0; i < Lines.size() - 1; i++) {
			String dInfo = Lines.get(i).split("\\(")[0];
			if (dInfo.equals("deteste")) {

				String pirates = Lines.get(i).split("[\\(\\)]")[1];
				String[] piratess = pirates.split(",");
				String pirate1 = piratess[0];
				String pirate2 = piratess[1];
				listeDeteste.put(pirate1, pirate2);

			}
		}

		/* Chargement des preferences pour chaque pirate */
		this.listPref = new HashMap<>();
		for (int i = 0; i < Lines.size() - 1; i++) {
			ArrayList<String> tmp = new ArrayList<>();

			String prefInfo = Lines.get(i).split("\\(")[0];

			if (prefInfo.equals("preferences")) {
				try {
					String prefss = Lines.get(i).split("[\\(\\)]")[1];
					String[] prefs = prefss.split(",");

					for (int j = 1; j <= listObjet.size(); j++) {
						tmp.add(prefs[j]);
					}

					listPref.put(prefs[0], tmp);

				} catch (ArrayIndexOutOfBoundsException e) {
					System.err.println(
							"Erreur dans le chargement des preferences, veuillez verifier la syntaxe de votre fichier");
				}
			}

		}

		r.close();

	}

	/* Method to verify */

	/**
	 * @return
	 */
	public boolean verify() throws ParseException {
		if (verifyListDeteste() && verifyListPref() && verifyPirate() && verifySyntax()) {
			return true;
		} else {

			String errorMessage;
			if (this.idOfErrors.size() == 0) {
				errorMessage = "La syntaxe du fichier contient des elements illisibles pour le parser, veuillez verifier votre fichier ";
			} else {
				errorMessage = "La syntaxe du fichier contient des elements illisibles pour le parser, veuillez verifier votre fichier Verifiez ";
				if (this.idOfErrors.size() > 1) {
					errorMessage += "les lignes ";
					for (int i = 0; i < this.idOfErrors.size(); i++) {
						if (i == (this.idOfErrors.size() - 1)) {
							errorMessage += (this.idOfErrors.get(i) + 1) + " ";
						} else {
							errorMessage += (this.idOfErrors.get(i) + 1) + ",";
						}
					}

				} else {
					errorMessage += "la ligne " + (this.idOfErrors.get(0) + 1) + " ";
				}
				errorMessage += "De votre fichier d'equipage";
			}
			throw new ParseException(errorMessage, 0);

		}
	}

	// TODO verify that nb of pirates == nb of objects
	/**
	 * methode qui verifie qu'il ya qu'une seule occurence pour chaque pirate dans
	 * l'attribut listePirate
	 * 
	 * @return true si 
	 */
	private boolean verifyPirate() {
		for (String s : this.listPirate) {
			if (!oneOccurence(s, this.listPirate)) {
				return false;
			}
		}

		return true;
	}

	private boolean verifyListDeteste() {
		// Verifier que les pirates qui se deteste existent tous.
		boolean verified = true;
		for (String key : this.listeDeteste.keySet()) {
			if (!isIn(key, this.listPirate) && !isIn(this.listeDeteste.get(key), this.listPirate)) {
				verified = false;
			} else if (key.equals(this.listeDeteste.get(key))) {
				verified = false;
			}
		}
		return verified;
	}

	private boolean verifyListPref() {

		boolean verifiedPref = true;
		// Verifier que les preferences des pirates existent vraiment.
		for (String s : this.listPref.keySet()) {
			ArrayList<String> tmp = this.listPref.get(s);
			for (String objet : tmp) {
				if (!isIn(objet, this.listObjet)) {
					verifiedPref = false;
				}
			}

		}

		// Verifier que les pirates dans listPref existent vraiment
		boolean verifiedPirate = true;
		for (String pirate : this.listPref.keySet()) {
			if (!isIn(pirate, this.listPirate)) {
				verifiedPirate = false;
			}
		}

		return (verifiedPirate && verifiedPref);
	}

	private boolean verifySyntax() {
		// Verifier que toutes les lignes de notre fichier se termine par "."
		boolean verify = true;
		for (int i = 0; i < this.Lines.size(); i++) {
			if (this.Lines.get(i) != null) {
				if (!this.Lines.get(i).endsWith(".")) {
					idOfErrors.add(i);
					verify = false;
				}
			}
		}

		return verify;
	}

	/* Getters and setters */

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList<String> getLines() {
		return Lines;
	}

	public void setLines(ArrayList<String> lines) {
		Lines = lines;
	}

	public ArrayList<String> getListPirate() {
		return listPirate;
	}

	public void setListPirate(ArrayList<String> listPirate) {
		this.listPirate = listPirate;
	}

	public ArrayList<String> getListObjet() {
		return listObjet;
	}

	public void setListObjet(ArrayList<String> listObjet) {
		this.listObjet = listObjet;
	}

	public HashMap<String, String> getListeDeteste() {
		return listeDeteste;
	}

	public void setListeDeteste(HashMap<String, String> listeDeteste) {
		this.listeDeteste = listeDeteste;
	}

	public HashMap<String, ArrayList<String>> getListPref() {
		return listPref;
	}

	public void setListPref(HashMap<String, ArrayList<String>> listPref) {
		this.listPref = listPref;
	}

	/* Util */
	/**
	 * methode qui verifie si l'ID (nom) x d'un pirate qui est un String se trouve dans un ArrayList
	 * xs
	 * 
	 * @param x  represente une chaine de caracteres (String)
	 * @param xs represente un tableau dynamique (Tableau de String)
	 * @return true si x appartient au tableau dynamique xs
	 */
	private boolean isIn(String x, ArrayList<String> xs) {
		boolean isIn = false;
		for (int i = 0; i < xs.size(); i++) {
			if (x.equals(xs.get(i))) {
				isIn = true;

			}
		}
		return isIn;
	}

	/**
	 * methode qui verifie l'existance d'une seule occurence d'un String x dans l'ArrayList xs
	 * @x : represente une chaine de caracteres (String) 
	 * @xs : represente un tableau dynamique (Tableau de String)
	 * 
	 * @return true si il y a qu'une seule occurence de x dans xs sinon retourne false
	 */
	private boolean oneOccurence(String x, ArrayList<String> xs) {
		int cmp = 0;
		for (String s : xs) {
			if (x.equals(s)) {
				cmp++;
			}
		}
		if (cmp == 1) {
			return true;
		} else {
			return false;
		}

	}

}