package moulin;

import java.io.Serializable;

public class Joueur implements Serializable{
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private static final long serialVersionUID = -2788360831910322017L;
	
	private int numJoueur;

	/*****************/
	/***ACCESSEURS****/
	/*****************/
	
	public int getNumJoueur() {
		return numJoueur;
	}

	public void setNumJoueur(int numJoueur) {
		this.numJoueur = numJoueur;
	}


	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public Joueur() {		
	
	}
	
	public Joueur(int num){
		numJoueur = num;
	}

	public int getNiveau() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	/**************/
	/***METHODES***/
	/**************/
}
