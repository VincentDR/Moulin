package moulin;

public class Humain extends Joueur {
	
	
	private String Pseudo;

	
	
	// Retourne le pseudo du joueur Humain
	public String getPseudo() {
		return Pseudo;
	}

	// Modifie le pseudo du joueur Humain par newPseudo
	public void setPseudo(String newPseudo) {
		Pseudo = newPseudo;
	}
	
	

	// Création d'un nouveau joueur Humain avec un pseudo et un numéro de joueur nul 
	public Humain() {		
		super();
		Pseudo="";
	}

	// Création d'un nouveau joueur Humain avec un pseudo égal à s et un numéro de joueur égal à numJ
	public Humain(String s,int numJ){
		super(numJ);
		Pseudo=s;
	}	

}
