package up.mi.acs;


/**
 * La classe Pirate repr�sente l'objet pirate avec son nom ainsi que l'ordre des
 * pr�f�rences des diff�rents objets � se partager
 * @author Salas RADJEF
 * @author Christopher VIET
 * @author Amel NAIT AMER
 *
 */
public class Pirate {
	private String ID;
	private String[] preference;
	private String objet_recu;


	public Pirate(String ID) {
		this.setID(ID);
		
		
	}
	
	
	
	/**
	 * La methode addPreference permet a l'utilisateur d'ajouter a un pirate les ressources
	 * selon l'ordre de ses preferences
	 * 
	 * @param objets tableau de String qui contient l'ID du pirate et les ressources � partager selon
	 *               l'ordre de preference du pirate 
	 */
	public void addPreference(String[] objets) {
		//objets[0] => repr�sente l'ID du pirate
		//objets[1 -- > objets.length] => repr�sente l'ensemble des pr�ferences du pirate
		for (int i =0;i<(objets.length)-1;i++) {
			this.preference[i] = objets[i+1];
		
		}
	}
	
	
	/**
	 * La methode findIDofPref permet de chercher l'indice d'une ressource parmi celles � partager
	 * represent�e par un String  � partir du tableau des preferences d'un pirate
	 * 
	 * @param x repr�sente l'un des objets � partager sous forme d'un String
	 * @return l'indice de l'objet x dans le tableau des pref�rences
	 */
	
	public int findIDofPref(String x) {
		int ID=-1;
		
		for(int i=0;i<this.preference.length;i++) {
			if(x.equals(this.preference[i])) {
				ID = i;
			}
		}
		
		return ID;
	}
	
	
	
	/**
	 * La methode getPref affiche l'ensemble des objets a partager selon l'odre de
	 * preference d'un pirate
	 * 
	 * @param nbr reprensente le nombre d'objets a partager par les pirates
	 */
	
	public void getPref(int nbr) {
		for(int i=0;i<nbr;i++){
			System.out.println(this.preference[i]);
		}
	}

	public String[] getPreference() {
		return preference;
	}

	public void setPreference(String[] preference) {
		this.preference = preference;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}


	public String getObjet_recu() {return objet_recu;}

	public void setObjet_recu(String objet_recu) {this.objet_recu = objet_recu; }
}
