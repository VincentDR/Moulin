package moulin;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controleur {
	private Fenetre fenetre;
	private PlateauMoulin plateau;
	
	public Controleur()
	{
		fenetre = new Fenetre(this);
	}	
	
	// Nouvelle partie Joueur contre Joueur
	public void newPartieJJ(String nomJ1, String nomJ2){
		Humain H1 = new Humain(nomJ1,1);
		Humain H2 = new Humain(nomJ2,2);

		plateau = new PlateauMoulin(H1,H2);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	// Nouvelle partie Joueur contre Ordi
	public void newPartieJO(String nomJ,int difficulte){
		Humain H = new Humain(nomJ,2);
		NonHumain NH = new NonHumain(difficulte,1);
		
		plateau = new PlateauMoulin(NH,H);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	// Nouvelle partie Ordi contre Ordi
	public void newPartieOO(){
		NonHumain NH1 = new NonHumain(0,1);
		NonHumain NH2 = new NonHumain(0,2);
		
		plateau = new PlateauMoulin(NH1,NH2);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	

	// Demande à ajouter une pièce sur PlaceAJouer avec la possession du joueur actif
	public void AjouterPiece(int placeAJouer)
	{
		plateau.AjouterPiece(placeAJouer);
	}

	// Retourne vrai si au moins 1 déplacement est possible pour le joueur actif
	public boolean ExisteDeplacement()
	{
		return this.plateau.ExisteDeplacement();
	}
	
	// Demande à déplacer une pièce de la case placeARetirer vers placeAJouer avec la possession en paramètre
	public void DeplacerPiece(int placeARetirer, int placeAJouer, int possession)
	{
		plateau.DeplacerPiece(placeARetirer, placeAJouer, possession);
	}
		
	// Demande à retirer la pièce à la case placeARetirer qui doit avoir sa possession = possessionAdv
	public void RetirerPiece(int placeARetirer, int possessionAdv)
	{
		plateau.RetirerPiece(placeARetirer, possessionAdv);
	}
	
	// Appel de la méthode qui permet de faire jouer le/un ordi
	public void ordi()
	{
		plateau.ControleurOrdi();
	}
	
	// Appel de la méthode qui permet de sauvegarder une partie
	public void sauvegarder(String nomPartie)
	{
		plateau.sauvegarder(nomPartie);
	}
	
	// Appel de la méthode qui permet de charger une partie
	public void charger(String nomPartie)
	{
		try {
			this.plateau = (PlateauMoulin) Plateau.getSave(nomPartie);
			plateau.addObserver(fenetre.getPanneau());
			this.plateau.charger();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
