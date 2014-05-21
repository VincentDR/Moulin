package moulin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;
import java.util.Observable;
import java.util.Observer;


public class Plateau extends Observable implements Serializable{
		
	/**
	 * 
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 4215081519258187885L;
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
	
	public static Plateau getSave (String s) throws FileNotFoundException, IOException, ClassNotFoundException
	{
		File f = new File(s);
		if(f.length() > 0)//f.exists() && !f.isDirectory())
		{
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(f)) ;
			return (Plateau)ois.readObject() ;
			
		}
		return null;
	}
	
	public void sauvegarder(String s)
    {
       File fichier =  new File(s);

		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichier, false));
			try {
				oos.writeObject(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 // sérialization de l'objet
		
    }
	
	public Plateau(){
		Pieces = new Vector <Piece> (24);
		for(int i=0;i<24;i++){
			Pieces.addElement(new Piece());			
		}
	}
	public Plateau(Plateau p){
		this.JoueurActif = p.getJoueurActif();
		this.JoueurNActif = p.getJoueurNActif();
		Pieces = new Vector <Piece> (24);
		for(int i=0;i<24;i++){
			Pieces.addElement(p.getPieces().elementAt(i));			
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
	
	public int[] charger(){
		int[] Result = new int[29];
		
		Result[0] = 7;

		
		return Result;		
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
