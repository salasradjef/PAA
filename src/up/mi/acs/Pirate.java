package up.mi.acs;


public class Pirate {
	private String ID;
	private String[] preference;
	
	public Pirate(String ID) {
		this.setID(ID);
		
		
	}
	
	
	public void addPreference(String[] objets) {
		
		for (int i =0;i<(objets.length)-1;i++) {
			this.preference[i] = objets[i+1];
		
		}
	}
	
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




}
