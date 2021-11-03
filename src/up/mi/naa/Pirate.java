package up.mi.naa;


/**
 * La classe Pirate represente l'objet pirate avec son nom ainsi que l'ordre de
 * ses prefernces des differents objets � se partager
 * 
 *
 */

public class Pirate {
	private String ID;
	private String[] preference;

	public Pirate(String ID) {
		this.setID(ID);

	}

	/**
	 * La m�thode permet � l'utilisateur d'ajouter � un objet pirate les objets
	 * selon l'ordre de ses preferences
	 * 
	 * @param objets tableau de String qui contient les objets � partager selon
	 *               l'ordre de preference du pirate sans inclure le ID du pirate
	 *               qui se retrouve � la position 0 du tableau
	 */
	public void addPreference(String[] objets) {

		for (int i = 0; i < (objets.length) - 1; i++) {
			this.preference[i] = objets[i + 1];

		}
	}

	/**
	 * La methode permet de chercher l'indice d'un objet parmi ceux � partager
	 * representer par un String � partir du tableau des preferences d'un pirate
	 * 
	 * @param x represente l'un des objets � partager sous forme d'un String
	 * @return l'indice de l'objet x dans le tableau des preferences
	 */
	public int findIDofPref(String x) {
		int ID = -1;

		for (int i = 0; i < this.preference.length; i++) {
			if (x.equals(this.preference[i])) {
				ID = i;
			}
		}

		return ID;
	}

	/**
	 * La methode affiche l'ensemble des objets � partager selon l'odre de
	 * preference d'un pirate
	 * 
	 * @param nbr reprensente le nombre d'objets � partager par les pirates
	 */
	public void getPref(int nbr) {
		for (int i = 0; i < nbr; i++) {
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

}