package up.mi.acs;

/**
 *Classe qui permet d'avoir les lettres alphabétiques en majuscules qui vont 
 *correspondre au nom des pirates.
 * @author Salas RADJEF
 * @author Christopher VIET
 * @author Amel NAIT AMER
 *
 */
public class ALPHABET {
	public static String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
	
	
	/**
	 * Méthode qui permet d'avoir l'indice (la position) d'une lettre passée en
	 * paramètre dans le tableau de String ALPHABET
	 * 
	 * @param S  lettre pour laquelle nous voulons connaitre l'indice
	 * @return l'indice de la lettre passée en paramètre sinon -1 dans le cas ou la lettre n'est pas dans ALPHABET
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