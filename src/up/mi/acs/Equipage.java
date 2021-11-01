package up.mi.acs;

import java.util.ArrayList;
import java.util.HashMap;

public class Equipage {
	private ArrayList<Pirate> equipage;
	private int[][] relation_pirate;
	private int nombre_pirate;
	HashMap<Pirate, String> objet_recu;
	
	
	
	
	
	public Equipage(int nombre_pirate) {
		this.nombre_pirate = nombre_pirate;
		this.equipage = new ArrayList <Pirate>();
		this.objet_recu = new HashMap<Pirate, String>();
		
		for(int i=0;i<nombre_pirate;i++) {
			Pirate tmp = new Pirate(ALPHABET.ALPHABET[i]);
			tmp.setPreference(new String[this.nombre_pirate]);
			this.equipage.add(tmp);
			objet_recu.put(tmp, null);
		
		}
		this.relation_pirate = new int[this.nombre_pirate][this.nombre_pirate];
		initRelation();
		
		
		
		
		
	}
	
	
	
	
	

	
	
	private void initRelation(){

		for(int i =0;i<this.nombre_pirate;i++) {
			for(int j=0;j<this.nombre_pirate;j++) {
				this.relation_pirate[i][j] = 0;
			}
		}
	}

	public void addRelation(Pirate A, Pirate B) {
		int P_A, P_B;
		Boolean validation = false;
		P_A = ALPHABET.getIDofALPHABET(A.getID());
		P_B = ALPHABET.getIDofALPHABET(B.getID());
		
		if ((P_A >= 0 && P_A <=this.nombre_pirate) && (P_B >=0 && P_B<= this.nombre_pirate)) {
			validation = true;
		}
		
		
		if(validation) {
			this.relation_pirate[P_A][P_B]= 1;
			this.relation_pirate[P_B][P_A] = 1;
			
			System.out.println("La relation  ne s'aiment pas entre " +  A.getID() + " et " + B.getID() +" a bien été ajouté" );
		}else {
			System.out.print("Une erreur est survenue et la relation na pas pu etre ajouté ");
		}
		
		
	
		
		
	}
	
	
	
	
	public Pirate findPirateByID(String ID) {
		
		Pirate tmp=null;
		for(int i = 0;i<this.equipage.size();i++) {
			if(this.equipage.get(i).getID().equals(ID)  ) {
				tmp = this.equipage.get(i);
			}
		}
		
		
		
		
		return tmp;
	}
	

	
	public void affectationNaive() {
		ArrayList<String> affecte = new ArrayList<String>();
		int cmp_affecte=0;
		for(int i =0;i<nombre_pirate;i++) {
			Pirate tmp = this.equipage.get(i);
			String[] obj = tmp.getPreference();
			
			for(int j=0;j<obj.length;j++) {
				
				if(!isIn(obj[j], affecte)) {
					this.objet_recu.put(tmp, obj[j]);
					affecte.add(obj[j]);
					cmp_affecte++;
					break;
				}
			}
			
			
		}
		
	
		
	}
	
	public void changerObjet(Pirate A, Pirate B) {
		String recuA = this.objet_recu.get(A);
		String recuB = this.objet_recu.get(B);
		
		this.objet_recu.replace(A, recuB);
		this.objet_recu.replace(B, recuA);
		
		
		
	}
	
	
	public int cost() {
		
		
		return 0;
	}
	
	private boolean isIn(String x, ArrayList<String> xs) {
		 boolean isIn = false;
		for(int i =0;i<xs.size();i++) {
			if(x.equals(xs.get(i))) {
				isIn =true;
			}
		}
		
		
		
		return isIn;
	}
	

	public ArrayList<Pirate> getEquipage() {
		return equipage;
	}





	public void setEquipage(ArrayList<Pirate> equipage) {
		this.equipage = equipage;
	}





	public int[][] getRelation_pirate() {
		return relation_pirate;
	}





	public void setRelation_pirate(int[][] relation_pirate) {
		this.relation_pirate = relation_pirate;
	}
	

}
