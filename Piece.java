package moulin;

import java.io.Serializable;

public class Piece implements Serializable {


	private static final long serialVersionUID = 5990060319590476877L;

	private Joueur Proprietaire;

	
	// Retourne le Joueur qui possede la piece
	public Joueur getProprietaire() {
		return Proprietaire;
	}

	// Modifie le Joueur propriétaire de la pièce par le Joueur propriet
	public void setProprietaire(Joueur propriet) {
		this.Proprietaire = propriet;
	}
	

	
	// Cré une nouvelle pièce de jeu avec un nouveau joueur propriétaire
	public Piece(){
		this.Proprietaire = new Joueur();
	}
	
	// Cré une nouvelle pièce de jeu avec un joueur propriétaire égal à j
	public Piece(Joueur j) {		
		this.Proprietaire=j;
	}

	// Retorune le numéro du joueur propriétaire de la pièce de jeu
	public int getPossession()
	{
		return Proprietaire.getNumJoueur();
	}

}
