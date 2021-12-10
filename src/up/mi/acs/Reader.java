package up.mi.acs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Reader {
	private String filePath;
	private ArrayList<String> Lines;
	private ArrayList<String> listPirate;
	private ArrayList<String> listObjet;
	private HashMap<String,String> listeDeteste;
	private HashMap<String,ArrayList<String>> listPref;



	public Reader(String path) throws IOException  {
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
			System.err.println("Erreur lors de la création du buffered reader, veuillez recommencer");
			System.exit(0);
		}

		String s = "";
		this.Lines = new ArrayList<>();
		while(s != null) {
			s = reader.readLine();
			Lines.add(s);

		}



		/*Chargement des pirates*/
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



		/*Chargement des objets*/

		this.listObjet = new ArrayList<>();
		for(int i=0;i<Lines.size()-1;i++) {
			String oInfo = Lines.get(i).split("\\(")[0];
			if(oInfo.equals("objet")) {
				String nomObjet = Lines.get(i).split("[\\(\\)]")[1];
				listObjet.add(nomObjet);
			}

		}



		/**Chargement des relations "deteste"**/
		this.listeDeteste = new HashMap<>();
		for(int i=0;i<Lines.size()-1;i++) {
			String dInfo = Lines.get(i).split("\\(")[0];
			if(dInfo.equals("deteste")) {
				String pirates = Lines.get(i).split("[\\(\\)]")[1];
				String[] piratess = pirates.split(",");
				String pirate1 = piratess[0];
				String pirate2 = piratess[1];
				listeDeteste.put(pirate1, pirate2);

			}
		}

		/*Chargement des preferences pour chaque pirate*/
		this.listPref = new HashMap<>();
		for(int i=0;i<Lines.size()-1;i++) {
			ArrayList<String> tmp = new ArrayList<>();

			String prefInfo = Lines.get(i).split("\\(")[0];

			if(prefInfo.equals("preferences")) {
				String prefss = Lines.get(i).split("[\\(\\)]")[1];
				String[] prefs = prefss.split(",");

				for(int j=1;j<=listObjet.size();j++) {
					tmp.add(prefs[j]);
				}

				listPref.put(prefs[0], tmp);
			}



		}

		r.close();



	}


	/*Getters and setters*/

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






}