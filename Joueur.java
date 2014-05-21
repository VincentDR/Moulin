package moulin;

import java.io.Serializable;

public class Joueur implements Serializable{
	
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private static final long serialVersionUID = -2788360831910322017L;
	
	private int numJoueur; // Représente la possession pour les pièces de jeu

	/*****************/
	/***ACCESSEURS****/
	/*****************/
	
	public int getNumJoueur() {
		return numJoueur;
	}

	public void setNumJoueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}

	public int getNiveau() {
		return 0;
	}
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public Joueur() {		
		numJoueur = 0;
	}
	
	public Joueur(int num){
		numJoueur = num;
	}

	
	/**************/
	/***METHODES***/
	/**************/
}
