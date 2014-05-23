package moulin;

import java.io.Serializable;

public class Joueur implements Serializable{
	
	
	private static final long serialVersionUID = -2788360831910322017L;
	
	// Représente la possession pour les pièces de jeu, fait la différence entre le joueur 1 et joueur 2
	private int numJoueur;
	

	
	// Retourne le numéro du joueur
	public int getNumJoueur() {
		return numJoueur;
	}

	// Modifie le numéro du joueur avec la valeur de numJoueur
	public void setNumJoueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}

	// Retourne le niveau du joueur, 0 si joueur Humain, autre si joueur NonHumain (surcharge de la méthode)
	public int getNiveau() {
		return 0;
	}
	
	
	
	// Cré un nouveau Joueur avec son numéro égal à 0
	public Joueur() {		
		numJoueur = 0;
	}
	
	// Cré un nouveau Joueur avec son numéro égal à num
	public Joueur(int num){
		numJoueur = num;
	}

	// Cré un nouveau Joueur par copie, prend le même numéro de joueur que j
	public Joueur(Joueur j){
		numJoueur = j.numJoueur;
		
	}

}
