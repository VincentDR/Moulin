package moulin;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Controleur {
	private Fenetre fenetre;
	private PlateauMoulin plateau;
	private Save save;
	
	public Controleur()
	{
		fenetre = new Fenetre(this);
	}	
	
	// Nouvelle partie Joueur contre Joueur
	public void newPartieJJ(String nomJ1, String nomJ2){
		Humain H1 = new Humain(nomJ1);
		Humain H2 = new Humain(nomJ2);

		plateau = new PlateauMoulin(H1,H2);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	// Nouvelle partie Joueur contre Ordi
	public void newPartieJO(String nomJ,int difficulte){
		Humain H = new Humain(nomJ);
		NonHumain NH = new NonHumain(difficulte);
		
		plateau = new PlateauMoulin(NH,H);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	// Nouvelle partie Ordi contre Ordi
	public void newPartieOO(){
		NonHumain NH1 = new NonHumain(0);
		NonHumain NH2 = new NonHumain(0);
		
		plateau = new PlateauMoulin(NH1,NH2);
		plateau.addObserver(fenetre.getPanneau());
	}
	
	
	
	/*	
		
		try {
			this.save = Save.getInstance ();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	
	//Ajoute une piÃ¨ce sur PlaceAJouer du joueur actif
	public void AjouterPiece(int placeAJouer)
	{
		plateau.AjouterPiece(placeAJouer);
	}
		
	//Retourne 1 si le dÃ©placement est possible, 0 sinon.
	public void DeplacerPiece(int placeARetirer, int placeAJouer, int possession)
	{
		plateau.DeplacerPiece(placeARetirer, placeAJouer, possession);
	}
		
	public void RetirerPiece(int placeARetirer, int possession)
	{
		plateau.RetirerPiece(placeARetirer, possession);
	}
	
	public void ordi()
	{
		plateau.ControleurOrdi();
	}
	
	public void save(String nomPartie)
	{
		//this.save.sauvegarder(nomPartie);
	}
}








/*
Tableau controle :
	 * 
	 * 		
	 * 		ENVOI PAR LA VUE
	 * 		Premiere case -> Placement (ou depart deplacement)
	 * 		Deuxieme case -> Deplacement arrive
	 * 		Troisieme case -> Piece a eliminer
	 * 
	 * 
	 * 		ENVOI PAR LE MODELE
	 *  	Premiere case -> Info sur les infos transmises afin d'informer la vue
	 *  		Result[0] =	-1 ->	Rien
	 *  				1 ->	Placement
	 * 				2 ->	Deplacement
	 * 				3 ->	Moulin
	 * 				4 ->	Placement+Moulin
	 * 				5 ->	Deplacement+Moulin
	 * 
	 * 		Seconde case -> La possession (1 si action ordi, 2 si action joueur)
	 * 			Result[1] =	1 ->	Ordi
	 *  					2 ->	Joueur
	 *  
	 *		Troisieme case -> Le placement
	 * 			Result[2] =	-1 ->	Il ne s'agit pas d'un placement
	 *  					x ->	La case x ou la piece sera posee

	 * 		Quatrieme case -> Le deplacement, case depart
	 * 			Result[3] =	-1 ->	Il ne s'agit pas d'un deplacement
	 *  					x ->	La case x d'ou la piece sera retiree
	 *  
	 *  	Quatrieme case -> Le deplacement, case arrivee
	 * 			Result[4] =	-1 ->	Il ne s'agit pas d'un deplacement
	 *  					x ->	La case x ou la piece sera posee
	 *  
	 *  	Cinquieme case -> Le moulin
	 * 			Result[5] =	-1 ->	Il ne s'agit pas d'un moulin
	 *  					x ->	La case x ou la piece sera detruite
	 *  
	 *  
	 *  Resume:
	 * 			La vue envoie un tableau de 3 entiers, le modele lui repond par un tableau de 6 entiers
	 * 
	 * 	Ex: La vue envoie Proposition[0] == Proposition[1] == -1 et Proposition[2] = x
	 * 		Si x est une piece adverse, alors le modele renverra Result[0] = 3 et Result[5] = x
	 * 		La vue supprimera donc la case x
	 * 
	 */
