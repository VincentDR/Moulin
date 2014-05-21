package moulin;

public class Humain extends Joueur {
	
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private String Pseudo;

	
	
	/*****************/
	/***ACCESSEURS****/
	/*****************/
	public String getPseudo() {
		return Pseudo;
	}

	public void setPseudo(String pseudo) {
		Pseudo = pseudo;
	}
	
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public Humain() {		
		super();
		Pseudo="";
	}

	public Humain(String s,int numJ){
		super(numJ);
		Pseudo=s;
	}	
	
	/**************/
	/***METHODES***/
	/**************/
}
