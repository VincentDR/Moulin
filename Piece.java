package moulin;

public class Piece {
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private Joueur Proprietaire;

	
	
	/*****************/
	/***ACCESSEURS****/
	/*****************/
	public Joueur getProprietaire() {
		return Proprietaire;
	}

	public void setProprietaire(Joueur proprietaire) {
		Proprietaire = proprietaire;
	}
	
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public Piece(){
		Proprietaire = new Joueur();
	}
	public Piece(Joueur j) {		
		Proprietaire=j;
	}



	
	
	
	/**************/
	/***METHODES***/
	/**************/
	public int getPossession()
	{
		if(Proprietaire.getClass().getName() == "moulin.Humain"){
			return 2;
		}
		else{
			if(Proprietaire.getClass().getName() == "moulin.NonHumain"){
				return 1;
			}
		
		else{ return 0;}}
	}

}
