package moulin;

import java.util.Vector;
import java.util.Observable;
import java.util.Observer;


public class Plateau extends Observable{
		
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private Vector<Piece> Pieces;
	private Joueur JoueurActif;
	private Joueur JoueurNActif;

	
	/*****************/
	/***ACCESSEURS****/
	/*****************/
	public Vector<Piece> getPieces() {
		return Pieces;
	}

	public void setPieces(Vector<Piece> pieces) {
		Pieces = pieces;
	}

	public Joueur getJoueurActif() {
		return JoueurActif;
	}

	public void setJoueurActif(Joueur joueurActif) {
		JoueurActif = joueurActif;
	}
	
	public Joueur getJoueurNActif() {
		return JoueurNActif;
	}

	public void setJoueurNActif(Joueur joueurNActif) {
		JoueurNActif = joueurNActif;
	}
	
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	public Plateau(Joueur ja,Joueur jna) {		
		JoueurActif = ja;
		JoueurNActif = jna;
		Pieces = new Vector <Piece> (24);
		for(int i=0;i<24;i++){
			Pieces.addElement(new Piece());			
		}
	
	}

	
	/**************/
	/***METHODES***/
	/**************/
	public void ChangerJoueurActif(){
		Joueur Joueur = JoueurActif;
		JoueurActif = JoueurNActif;
		JoueurNActif = Joueur;
	}
	public boolean CoupValide(){
		return false;		
	}
	
	public void AjouterPiece(){
		
	}
	
	public void DeplacerPiece(){
		
	}
	
	public void RetirerPiece(){
		
	}
	
	public boolean isJoueur(Joueur j){
		
		return JoueurActif == j;
	}
	
	
	/* --------- Implementation Observable --------- */
	
	public void updateGrid ()
	{

	}
	
	public void addObserver (Observer obs)
	{
		super.addObserver(obs);
	}
	
	

}
