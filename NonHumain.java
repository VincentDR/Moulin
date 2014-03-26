package moulin;

public class NonHumain extends Joueur{

	/***************/
	/***ATTRIBUTS***/
	/***************/
	private int Niveau;

	
	
	/*****************/
	/***ACCESSEURS****/
	/*****************/
	public int getNiveau() {
		return Niveau;
	}

	public void setNiveau(int niveau) {
		Niveau = niveau;
	}
	
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public NonHumain() {		
		super();
		Niveau=1;
	}
	
	public NonHumain(int n){
		super();
		Niveau=n;
	}


	
	
	/**************/
	/***METHODES***/
	/**************/
}
