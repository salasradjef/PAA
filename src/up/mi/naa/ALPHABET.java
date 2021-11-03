package up.mi.naa;



/**
 * Classe qui permet d'avoir les lettres alphabétiques en majuscule qui vont
 * correspondre aux nom des pirates.
 * 
 *
 */
public class ALPHABET {
	public static String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	/**
	 * Méthode qui permet d'avoir l'indice (la position) d'une lettre passer en
	 * parametre dans le tableau de String ALPHABET
	 * 
	 * @param S  lettre pour laquelle nous voulons connaitre l'indice
	 * @return l'indice de la lettre passer en parametre sinon -1 dans le cas ou la lettre n'est pas dans ALPHABET
	 */
	public static int getIDofALPHABET(String S) {
		Boolean trouver= false;
		int ID =-1;
		int cmp=0;
		do {
			
			if(S == ALPHABET[cmp]) {
				ID = cmp;
				trouver = true;
			}else {
				cmp++;
			}
			
		}while(trouver != true);
		return ID;
	}
	
	
	
}