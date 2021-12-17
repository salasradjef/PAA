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
	private HashMap<String, ArrayList<String>> listeDeteste;
	private HashMap<String, ArrayList<String>> listPref;
	private ArrayList<Integer> idOfErrors = new ArrayList<>();
	// private boolean verify = false;

	public HashMap<String, ArrayList<String>> getListeDeteste() {
		return listeDeteste;
	}

	public void setListeDeteste(HashMap<String, ArrayList<String>> listeDeteste) {
		this.listeDeteste = listeDeteste;
	}

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
		int z = 0;
		this.Lines = new ArrayList<>();
		while (s != null) {
			s = reader.readLine();
			z++;
			if (s != null) {
				if (s.equals("")) {
					System.out.println("la ligne " + z + " est vide, veuillez la supprimer");
					System.exit(0);

				} else {
					Lines.add(s);
				}
			}

		}

		this.listPirate = new ArrayList<>();
		this.listObjet = new ArrayList<>();
		this.listPref = new HashMap<>();
		ArrayList<String> linesPreferences = new ArrayList<>();
		String errorMessage = "Erreur a la ligne ";
		ArrayList<String> linesDeteste = new ArrayList<>();
		for (int i = 0; i < Lines.size(); i++) {
			if (Lines.get(i).startsWith("pirate")) {
				String nomPirate = Lines.get(i).split("[\\(\\)]")[1];
				if (oneOccurence(nomPirate, this.listPirate)) {
					listPirate.add(nomPirate);
				} else {
					throw new ParseException(errorMessage, (i + 1));
				}

			} else if (Lines.get(i).startsWith("objet")) {
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				if (oneOccurence(nomObjet, this.listObjet)) {
					listObjet.add(nomObjet);
				} else {
					throw new ParseException(errorMessage, (i + 1));
				}

				if (Lines.get(i + 1).startsWith("deteste")) {
					verifyPirateObject();
					InitDeteste();
				}

			} else if (Lines.get(i).startsWith("deteste")) {

				if (oneOccurence(Lines.get(i), linesDeteste)) {
					linesDeteste.add(Lines.get(i));
					String[] pres = Lines.get(i).split("[\\(\\)]");
					String[] nomsPirates = pres[1].split(",");
					if (!isIn(nomsPirates[0], this.listPirate) && !isIn(nomsPirates[1], this.listPirate)) {
						throw new ParseException(errorMessage, (i + 1));
					} else {
						String pirate1 = nomsPirates[0];
						String pirate2 = nomsPirates[1];
						ArrayList<String> ts = this.listeDeteste.get(pirate1);
						if (ts == null) {
							ts = new ArrayList<>();
							ts.add(pirate2);
							this.listeDeteste.put(pirate1, ts);
						} else {
							ts.add(pirate2);
							this.listeDeteste.put(pirate1, ts);
						}

					}

				} else {
					throw new ParseException(errorMessage, (i + 1));
				}

			} else if (Lines.get(i).startsWith("preferences")) {
				if (oneOccurence(Lines.get(i), linesPreferences)) {
					linesPreferences.add(Lines.get(i));
					String prefss = Lines.get(i).split("[\\(\\)]")[1];
					String[] prefs = prefss.split(",");
					if (isIn(prefs[0], this.listPirate)) {
						if ((prefs.length - 1) == this.listObjet.size()) {
							ArrayList<String> tmp = new ArrayList<>();
							for (int j = 1; i < prefs.length; j++) {
								if (!isIn(prefs[i], prefs)) {
									throw new ParseException(errorMessage, (i + 1));
								}
							}

							for (int j = 1; j <= listObjet.size(); j++) {
								tmp.add(prefs[j]);
							}

							listPref.put(prefs[0], tmp);

						} else {
							throw new ParseException(errorMessage, (i + 1));
						}

					} else {
						throw new ParseException(errorMessage, (i + 1));
					}

				} else {
					throw new ParseException(errorMessage, (i + 1));
				}
			} else {
				throw new ParseException(errorMessage, i + 1);
			}

		}

	}

	/**
	 * methode qui permet d'ajouter dans une HashMap (qui correspond aux pirates qui
	 * se detestent dans l'equipage) tout les pirates comme clé et comme valeur null
	 */

	private void InitDeteste() {
		this.listeDeteste = new HashMap<>();
		for (int i = 0; i < this.listPirate.size(); i++) {
			this.listeDeteste.put(this.listPirate.get(i), null);
		}
	}

	/**
	 * methode qui permet de verifier si le nombre de pirates est le meme que le
	 * nombre d'objets a se partager en levant une exception dasn le cas echeant
	 * 
	 */
	private void verifyPirateObject() throws ParseException {
		if (listPirate.size() != listObjet.size()) {
			throw new ParseException("Le nombre de pirates n'est pas egale au nombre d'objets", 0);
		}
	}

	/* Method to verify */

	/**
	 * methode qui permet de verifier si le fichier contient des elements illisible
	 * 
	 * @retrun true si le fichier n'a pas d'erreur sinon false
	 */
	public boolean verify() throws ParseException {
		if (verifySyntax()) {
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

	/**
	 * la methode verifyPirate verifie qu'il ya qu'une seule occurence pour chaque
	 * pirate dasn listePirate
	 * 
	 * @return true si il y a qu'une seule occurence pour chaque pirate sinon
	 *         retourne faux
	 */
	private boolean verifyPirate() {
		for (String s : this.listPirate) {
			if (!oneOccurence(s, this.listPirate)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * methode verifyListDeteste verifie que les pirates qui sont dans l'attribut
	 * listeDeteste existe tous que se soit comme clé ou comme valeur
	 * 
	 * @return vrai si tous les pirates malipule dans listeDeteste existent sinon
	 *         retourne faux
	 */
	private boolean verifyListDeteste() {
		// Verifier que les pirates qui se deteste existent tous.
		boolean verified = true;
		for (String key : this.listeDeteste.keySet()) {
			if (!isIn(key, this.listPirate)) {
				System.out.println("L'erreur est au niveau du pirate" + key);
			} else {
				for (String s : this.listeDeteste.get(key)) {
					if (!isIn(s, this.listeDeteste.get(key))) {
						verified = false;
						System.out.println("Erreur dans le pirate " + s);
					}
				}
			}
		}
		return verified;
	}

	/**
	 * methode verifyListPref verifie que chaque pirate a des preferences(objets
	 * preferes) qui existe dans la liste des objets proposé dans l'attribut
	 * listObjet et que les pirates qui sont manipules se trouve bien dans la liste
	 * des pirates proposé par l'attribut listPirate
	 * 
	 * @return true si tous les pirates manipules par la classe et leurs des
	 *         preferences existent sinon retourne faux
	 */
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

	/**
	 * methode qui verifie que toutes les lignes de notre fichier se termine par "."
	 * 
	 * @return vrai si toute les lignes se termine par un point sinon retourne faux
	 */
	private boolean verifySyntax() {
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

	public HashMap<String, ArrayList<String>> getListPref() {
		return listPref;
	}

	public void setListPref(HashMap<String, ArrayList<String>> listPref) {
		this.listPref = listPref;
	}

	/* Util */
	/**
	 * methode qui verifie si un String x se trouve dans un ArrayList xs
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
	 * methode qui verifie si un String x d'un pirate se trouve dans un le tableau
	 * de String xs
	 *
	 * @param x  represente une chaine de caracteres (String)
	 * @param xs represente un tableau dynamique (Tableau de String)
	 * @return true si x appartient au tableau dynamique xs
	 */
	private boolean isIn(String x, String[] xs) {
		boolean isIn = false;
		for (int i = 0; i < xs.length; i++) {
			if (x.equals(xs[i])) {
				isIn = true;
			}
		}
		return isIn;
	}

	/**
	 * methode qui verifie l'existance d'une seule occurence d'un String x dans une
	 * ArrayList xs
	 * 
	 * @param x  : parametre represente en String
	 * @param xs : une ArrayList de String dans laquelle on verifie l'existance
	 *           d'une ou plusieurs occurence de x
	 * 
	 * @return vrai si il y a qu'une seule occurence sinon false
	 */
	private boolean oneOccurence(String x, ArrayList<String> xs) {
		int cmp = 0;
		if (xs == null) {
			return true;
		} else {
			for (String s : xs) {
				if (x.equals(s)) {
					cmp++;
				}
			}
			if (cmp == 0) {
				return true;
			} else {
				return false;
			}

		}

	}

}