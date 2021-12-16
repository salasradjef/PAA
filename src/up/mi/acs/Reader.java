package up.mi.acs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

public class Reader {
	private String filePath;
	private ArrayList<String> Lines;
	private ArrayList<String> listPirate;
	private ArrayList<String> listObjet;

	public HashMap<String, ArrayList<String>> getListeDeteste() {
		return listeDeteste;
	}

	public void setListeDeteste(HashMap<String, ArrayList<String>> listeDeteste) {
		this.listeDeteste = listeDeteste;
	}

	private HashMap<String,ArrayList<String>> listeDeteste;
	private HashMap<String,ArrayList<String>> listPref;
	private ArrayList<Integer> idOfErrors = new ArrayList<>();
	//private boolean verify = false;



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

		this.listPirate = new ArrayList<>();
		this.listObjet = new ArrayList<>();
		this.listPref = new HashMap<>();
		ArrayList<String> linesPreferences = new ArrayList<>();
		String errorMessage = "Erreur a la ligne ";
		ArrayList<String> linesDeteste = new ArrayList<>();
		for(int i=0;i<Lines.size();i++){
			if(Lines.get(i).startsWith("pirate")){
				String nomPirate = Lines.get(i).split("[\\(\\)]")[1];
				if(oneOccurence(nomPirate,this.listPirate)){
					listPirate.add(nomPirate);
				}else{
					throw new ParseException(errorMessage,(i+1));
				}

			}else if(Lines.get(i).startsWith("objet")){
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				if(oneOccurence(nomObjet,this.listObjet)){
					listObjet.add(nomObjet);
				}else{
					throw new ParseException(errorMessage,(i+1));
				}
				
				if(Lines.get(i+1).startsWith("deteste")){
					verifyPirateObject();
					InitDeteste();
				}

			}else if(Lines.get(i).startsWith("deteste")){


				if(oneOccurence(Lines.get(i),linesDeteste)){
					linesDeteste.add(Lines.get(i));
					String[] pres = Lines.get(i).split("[\\(\\)]");
					String[] nomsPirates = pres[1].split(",");
					if(!isIn(nomsPirates[0],this.listPirate) && !isIn(nomsPirates[1],this.listPirate)){
						throw new ParseException(errorMessage,(i+1));
					}else {
						String pirate1 = nomsPirates[0];
						String pirate2 = nomsPirates[1];
						ArrayList<String> ts = this.listeDeteste.get(pirate1);
						if(ts == null){
							ts = new ArrayList<>();
							ts.add(pirate2);
							this.listeDeteste.put(pirate1,ts);
						}else {
							ts.add(pirate2);
							this.listeDeteste.put(pirate1,ts);
						}

					}

				}else {
					throw new ParseException(errorMessage,(i+1));
				}

			}else if(Lines.get(i).startsWith("preferences")){
				if(oneOccurence(Lines.get(i),linesPreferences)){
					linesPreferences.add(Lines.get(i));
					String prefss = Lines.get(i).split("[\\(\\)]")[1];
					String[] prefs = prefss.split(",");
					if(isIn(prefs[0],this.listPirate)){
						if((prefs.length-1) == this.listObjet.size()){
							ArrayList<String> tmp = new ArrayList<>();
							for(int j=1;i<prefs.length;j++){
								if(!isIn(prefs[i],prefs)){
									throw new ParseException(errorMessage,(i+1));
								}
							}

							for(int j=1;j<=listObjet.size();j++) {
								tmp.add(prefs[j]);
							}

							listPref.put(prefs[0], tmp);

						}else {
							throw new ParseException(errorMessage, (i+1));
						}


					}else{
						throw new ParseException(errorMessage,(i+1));
					}



				}else {
					throw new ParseException(errorMessage,(i+1));
				}
			}else {
				throw new ParseException(errorMessage, i+1 );
			}


		}



		/*Chargement des pirates*//*
		this.listPirate =new ArrayList<>();
		boolean lastPirate = false;
		int cmp=0;

		while(!lastPirate) {
			String[] pInfo = Lines.get(cmp).split("\\(");
			if(pInfo[0].equals("pirate")) {
				String nomPirate = Lines.get(cmp).split("[\\(\\)]")[1];
				listPirate.add(nomPirate);
				cmp++;
			}else {
				lastPirate = true;
			}

		}



		*//*Chargement des objets*//*
		this.listObjet = new ArrayList<>();
		for(int i=0;i<Lines.size()-1;i++) {
			String oInfo = Lines.get(i).split("\\(")[0];
			if(oInfo.equals("objet")) {
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				listObjet.add(nomObjet);
			}

		}



		*//**Chargement des relations "deteste"**//*
		this.listeDeteste = new HashMap<>();
		for(int i=0;i<this.listPirate.size();i++){
			this.listeDeteste.put(this.listPirate.get(i),null);
		}

		for(int i=0;i<Lines.size();i++) {
			String dInfo = Lines.get(i).split("\\(")[0];
			if(dInfo.equals("deteste")) {
				String pirates = Lines.get(i).split("[\\(\\)]")[1];
				String[] piratess = pirates.split(",");
				String pirate1 = piratess[0];
				String pirate2 = piratess[1];
				//ArrayList<String> tmp = new ArrayList<>();
				ArrayList<String> ts = this.listeDeteste.get(pirate1);
				if(ts == null){
					ts = new ArrayList<>();
					ts.add(pirate2);
					this.listeDeteste.put(pirate1,ts);
				}else {
					ts.add(pirate2);
					this.listeDeteste.put(pirate1,ts);
				}


			}
		}

		*//*Chargement des preferences pour chaque pirate*//*
		this.listPref = new HashMap<>();
		for(int i=0;i<Lines.size();i++) {
			ArrayList<String> tmp = new ArrayList<>();

			String prefInfo = Lines.get(i).split("\\(")[0];

			if(prefInfo.equals("preferences")) {
				try{
				String prefss = Lines.get(i).split("[\\(\\)]")[1];
				String[] prefs = prefss.split(",");

				for(int j=1;j<=listObjet.size();j++) {
					tmp.add(prefs[j]);
				}

				listPref.put(prefs[0], tmp);

				}catch(ArrayIndexOutOfBoundsException e){
					System.err.println("Erreur dans le chargement des preferences, veuillez verifier la syntaxe de votre fichier");
				}
			}


		}
*/
		r.close();


	}



	private void InitDeteste(){
		this.listeDeteste = new HashMap<>();
		for(int i=0;i<this.listPirate.size();i++){
			this.listeDeteste.put(this.listPirate.get(i),null);
		}
	}
	private void verifyPirateObject() throws ParseException {
		if(listPirate.size() != listObjet.size()){
			throw new ParseException("Le nombre de pirates n'est pas egale au nombre d'objets",0);
		}
	}




	/*Method to verify*/

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



	//TODO verify that nb of pirates == nb of objects
	private boolean verifyPirate(){
		//verifier qu'il ya qu'une seule occurence pour chaque pirate
		for(String s : this.listPirate){
			if(!oneOccurence(s,this.listPirate)){
				return false;
			}
		}

		return true;
	}


	private boolean verifyListDeteste(){
		//Verifier que les pirates qui se deteste existent tous.
		boolean verified = true;
		for(String key : this.listeDeteste.keySet()){
			if(!isIn(key,this.listPirate)){
				System.out.println("L'erreur est au niveau du pirate" + key);
			}else {
				for(String s : this.listeDeteste.get(key)){
					if(!isIn(s,this.listeDeteste.get(key))){
						verified = false;
						System.out.println("Erreur dans le pirate " + s);
					}
				}
			}
		}
		return verified;
	}

	private boolean verifyListPref(){

		boolean verifiedPref = true;
		//Verifier que les preferences des pirates existent vraiment.
		for(String s : this.listPref.keySet()){
			ArrayList<String> tmp = this.listPref.get(s);
			for(String objet : tmp){
			 	if(!isIn(objet,this.listObjet)){
					 verifiedPref = false;
				}
			}

		}

		//Verifier que les pirates dans listPref existent vraiment
		boolean verifiedPirate = true;
		for(String pirate : this.listPref.keySet() ){
			if(!isIn(pirate,this.listPirate)){
				verifiedPirate = false;
			}
		}



		return (verifiedPirate && verifiedPref);
	}






	private boolean verifySyntax(){
		//Verifier que toutes les lignes de notre fichier se termine par "."
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


	private boolean isIn(String x, String[] xs) {
		boolean isIn = false;
		for(int i =0;i<xs.length;i++) {
			if(x.equals(xs[i])) {
				isIn =true;
			}
		}
		return isIn;
	}


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