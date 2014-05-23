package moulin;

public class NonHumain extends Joueur{


	/*  
	*  Représente le niveau de difficulté de l'ordinateur, 
	*  0 pour chaque ordi dans le mode OrdiVsOrdi qui représente le niveau difficile
	*  1 pour facile, 2 pour moyen, 3 pour difficile dans le mode OrdiVsJoueur
	*/
	private int Niveau;



	// Retourne le niveau de difficulté du jour joueur non Humain (ordi)
	public int getNiveau() {
		return Niveau;
	}

	// Modifie le niveau de difficulté du joueur non Humain par la valeur de niveau
	public void setNiveau(int niveau) {
		Niveau = niveau;
	}
	

	
	//Cré un nouveau joueur non Humain avec un numéro de joueur nul et un niveau de difficulté égal à 1
	public NonHumain() {		
		super();
		Niveau=1;
	}
	
	//Cré un nouveau joueur non Humain avec un numéro de joueur égal à numOrdi et un niveau de difficulté égal à nivo
	public NonHumain(int nivo,int numOrdi){
		super(numOrdi);
		Niveau=nivo;
	}

}
