package up.mi.acs;

public class ALPHABET {
	public static String[] ALPHABET = {"A", "B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	
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
