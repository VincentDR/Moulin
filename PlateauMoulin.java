package moulin;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.Vector;


/**
 * Tableau controle :
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
 *  					1 ->	Placement
 * 						2 ->	Deplacement
 * 						3 ->	Moulin
 * 						4 ->	Placement+Moulin
 * 						5 ->	Deplacement+Moulin
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


public class PlateauMoulin extends Plateau{
	
	/***************/
	/***ATTRIBUTS***/
	/***************/
	private int[][] VoisinsHorizontaux;
	private int[][] VoisinsVerticaux;
	private Scanner sc;
	private int nivArbo;
	//De 0 a 17 -> Pair l'ordi pose une piece, impair le joueur pose une piece
	//Superieur a 17, Pair l'ordi bouge, impair le joueur bouge
	private int TourDeJeu;
	
	/*****************/
	/***ACCESSEURS****/
	/*****************/
	public int[][] getVoisinsHorizontaux() {
		return VoisinsHorizontaux;
	}

	public void setVoisinsHorizontaux(int[][] voisinsHorizontaux) {
		VoisinsHorizontaux = voisinsHorizontaux;
	}

	public int[][] getVoisinsVerticaux() {
		return VoisinsVerticaux;
	}

	public void setVoisinsVerticaux(int[][] voisinsVerticaux) {
		VoisinsVerticaux = voisinsVerticaux;
	}
	
	public int getNivHarbo() {
		return nivArbo;
	}
	
	public void setNivHarbo(int niveau) {
		nivArbo = niveau;
	}
	

	public int getTourDeJeu() {
		return TourDeJeu;
	}

	public void setTourDeJeu(int tourDeJeu) {
		TourDeJeu = tourDeJeu;
	}
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/

	public PlateauMoulin(Joueur ja,Joueur jna) {		
		super(ja,jna);

		nivArbo=0;
		TourDeJeu=0;
		VoisinsHorizontaux = new int[24][2];
		VoisinsVerticaux = new int[24][2];
		
		for(int i=0;i<24;i++){
			for(int y=0;y<2;y++){
				VoisinsHorizontaux[i][y] = 42;
				VoisinsVerticaux[i][y] = 42;
			}
			
		}
		
		
		//Par Ligne
		/*
		VoisinsHorizontaux[0][0] = 1;
		VoisinsVerticaux[0][0] = 9;
		
		VoisinsHorizontaux[1][0] = 0;
		VoisinsHorizontaux[1][1] = 2;
		VoisinsVerticaux[1][0] = 4;			
		
		VoisinsHorizontaux[2][0] = 1;	
		VoisinsVerticaux[2][0] = 14;
		
		
		//LIGNE 2	
		VoisinsHorizontaux[3][0] = 4;
		VoisinsVerticaux[3][0] = 10;
		
		VoisinsHorizontaux[4][0] = 3;
		VoisinsHorizontaux[4][1] = 5;
		VoisinsVerticaux[4][0] = 1;			
		VoisinsVerticaux[4][1] = 7;	
		
		VoisinsHorizontaux[5][0] = 4;
		VoisinsVerticaux[5][0] = 13;
		
		//LIGNE 3	
		VoisinsHorizontaux[6][0] = 7;
		VoisinsVerticaux[6][0] = 11;
		
		VoisinsHorizontaux[7][0] = 6;
		VoisinsHorizontaux[7][1] = 8;
		VoisinsVerticaux[7][0] = 4;	
		
		VoisinsHorizontaux[8][0] = 7;
		VoisinsVerticaux[8][0] = 12;
			
		//LIGNE 4			
		VoisinsHorizontaux[9][0] = 10;
		VoisinsVerticaux[9][0] = 0;
		VoisinsVerticaux[9][1] = 21;
		
		VoisinsHorizontaux[10][0] = 9;
		VoisinsHorizontaux[10][1] = 11;
		VoisinsVerticaux[10][0] = 3;	
		VoisinsVerticaux[10][1] = 18;
		
		VoisinsHorizontaux[11][0] = 10;
		VoisinsVerticaux[11][0] = 6;
		VoisinsVerticaux[11][1] = 15;
		
		
		VoisinsHorizontaux[12][0] = 13;
		VoisinsVerticaux[12][0] = 8;
		VoisinsVerticaux[12][1] = 17;
		
		VoisinsHorizontaux[13][0] = 12;
		VoisinsHorizontaux[13][1] = 14;
		VoisinsVerticaux[13][0] = 5;	
		VoisinsVerticaux[13][1] = 20;
		
		VoisinsHorizontaux[14][0] = 13;
		VoisinsVerticaux[14][0] = 2;
		VoisinsVerticaux[14][1] = 23;
		
		
		//LIGNE 5
		VoisinsHorizontaux[15][0] = 16;
		VoisinsVerticaux[15][0] = 11;
		
		VoisinsHorizontaux[16][0] = 15;
		VoisinsHorizontaux[16][1] = 17;
		VoisinsVerticaux[16][0] = 19;	
		
		VoisinsHorizontaux[17][0] = 16;
		VoisinsVerticaux[17][0] = 12;
		
		
		//LIGNE 6
		VoisinsHorizontaux[18][0] = 19;
		VoisinsVerticaux[18][0] = 10;
		
		VoisinsHorizontaux[19][0] = 18;
		VoisinsHorizontaux[19][1] = 20;
		VoisinsVerticaux[19][0] = 16;			
		VoisinsVerticaux[19][1] = 22;	
		
		VoisinsHorizontaux[20][0] = 19;
		VoisinsVerticaux[20][0] = 13;
		
		
		//LIGNE 7	
		VoisinsHorizontaux[21][0] = 22;
		VoisinsVerticaux[21][0] = 9;
		
		VoisinsHorizontaux[22][0] = 21;
		VoisinsHorizontaux[22][1] = 23;
		VoisinsVerticaux[22][0] = 19;			
		
		VoisinsHorizontaux[23][0] = 22;	
		VoisinsVerticaux[23][0] = 14;
		*/
		
		//Par cercle
		VoisinsHorizontaux[0][0] = 1;
		VoisinsVerticaux[0][0] = 7;
		
		VoisinsHorizontaux[1][0] = 0;
		VoisinsHorizontaux[1][1] = 2;
		VoisinsVerticaux[1][0] = 9;			
		
		VoisinsHorizontaux[2][0] = 1;	
		VoisinsVerticaux[2][0] = 3;
			
		VoisinsHorizontaux[3][0] = 11;
		VoisinsVerticaux[3][0] = 2;
		VoisinsVerticaux[3][1] = 4;
		
		VoisinsHorizontaux[4][0] = 5;
		VoisinsVerticaux[4][0] = 3;			
		
		VoisinsHorizontaux[5][0] = 4;
		VoisinsHorizontaux[5][1] = 6;
		VoisinsVerticaux[5][0] = 13;
		
		VoisinsHorizontaux[6][0] = 5;
		VoisinsVerticaux[6][0] = 7;
		
		VoisinsHorizontaux[7][0] = 15;
		VoisinsVerticaux[7][0] = 0;	
		VoisinsVerticaux[7][1] = 6;	
		
		VoisinsHorizontaux[8][0] = 9;
		VoisinsVerticaux[8][0] = 15;
					
		VoisinsHorizontaux[9][0] = 8;
		VoisinsHorizontaux[9][1] = 10;
		VoisinsVerticaux[9][0] = 1;
		VoisinsVerticaux[9][1] = 17;
		
		VoisinsHorizontaux[10][0] = 9;
		VoisinsVerticaux[10][0] = 11;	
		
		VoisinsHorizontaux[11][0] = 3;
		VoisinsHorizontaux[11][1] = 19;
		VoisinsVerticaux[11][0] = 10;
		VoisinsVerticaux[11][1] = 12;
		
		VoisinsHorizontaux[12][0] = 13;
		VoisinsVerticaux[12][0] = 11;
		
		VoisinsHorizontaux[13][0] = 12;
		VoisinsHorizontaux[13][1] = 14;
		VoisinsVerticaux[13][0] = 5;	
		VoisinsVerticaux[13][1] = 21;
		
		VoisinsHorizontaux[14][0] = 13;
		VoisinsVerticaux[14][0] = 15;
		
		VoisinsHorizontaux[15][0] = 7;
		VoisinsHorizontaux[15][1] = 23;
		VoisinsVerticaux[15][0] = 8;
		VoisinsVerticaux[15][1] = 14;
		
		VoisinsHorizontaux[16][0] = 17;
		VoisinsVerticaux[16][0] = 23;	
		
		VoisinsHorizontaux[17][0] = 16;
		VoisinsHorizontaux[17][1] = 18;
		VoisinsVerticaux[17][0] = 9;
		
		VoisinsHorizontaux[18][0] = 17;
		VoisinsVerticaux[18][0] = 19;
		
		VoisinsHorizontaux[19][0] = 11;
		VoisinsVerticaux[19][0] = 18;			
		VoisinsVerticaux[19][1] = 20;	
		
		VoisinsHorizontaux[20][0] = 21;
		VoisinsVerticaux[20][0] = 19;
		
		VoisinsHorizontaux[21][0] = 20;
		VoisinsHorizontaux[21][1] = 22;
		VoisinsVerticaux[21][0] = 13;
		
		VoisinsHorizontaux[22][0] = 21;
		VoisinsVerticaux[22][0] = 23;			
		
		VoisinsHorizontaux[23][0] = 15;	
		VoisinsVerticaux[23][0] = 16;
		VoisinsVerticaux[23][1] = 22;
	}

	public PlateauMoulin(PlateauMoulin pm) {	
		super(pm.getJoueurActif(),pm.getJoueurNActif());
		nivArbo=pm.nivArbo;
		TourDeJeu=pm.TourDeJeu;
		VoisinsHorizontaux = new int[24][2];
		VoisinsVerticaux = new int[24][2];

		for(int i=0;i<24;i++){
			for(int y=0;y<2;y++){
				VoisinsHorizontaux[i][y] = pm.getVoisinsHorizontaux()[i][y];
				VoisinsVerticaux[i][y] = pm.getVoisinsVerticaux()[i][y];
			}
			getPieces().elementAt(i).setProprietaire(pm.getPieces().elementAt(i).getProprietaire());
		}
	}
	
	/**************/
	/***METHODES***/
	/**************/
	public void affichage(){
		String res="";
		
		
		//Par ligne
		/*
		//Ligne1
		res+=getPieces().elementAt(0).getPossession();
		res+="------------------";
		res+=getPieces().elementAt(1).getPossession();
		res+="------------------";
		res+=getPieces().elementAt(2).getPossession();		
		System.out.println(res);
		
		//Ligne2
		res="\t";
		res+=getPieces().elementAt(3).getPossession();
		res+="-----------";
		res+=getPieces().elementAt(4).getPossession();
		res+="-----------";
		res+=getPieces().elementAt(5).getPossession();			
		System.out.println(res);
		
		//Ligne3
		res="\t\t";
		res+=getPieces().elementAt(6).getPossession();
		res+="---";
		res+=getPieces().elementAt(7).getPossession();
		res+="---";
		res+=getPieces().elementAt(8).getPossession();			
		System.out.println(res);
		
		
		//Ligne4
		res="";
		res+=getPieces().elementAt(9).getPossession();
		res+="-------";
		res+=getPieces().elementAt(10).getPossession();
		res+="-----";
		res+=getPieces().elementAt(11).getPossession();		
		
		System.out.println(res+"          "+getPieces().elementAt(12).getPossession()+"-----"+getPieces().elementAt(13).getPossession()+"-------"+getPieces().elementAt(14).getPossession());
		
		//Ligne5
		res="\t\t";
		res+=getPieces().elementAt(15).getPossession();
		res+="---";
		res+=getPieces().elementAt(16).getPossession();
		res+="---";
		res+=getPieces().elementAt(17).getPossession();			
		System.out.println(res);
		
		//Ligne6
		res="\t";
		res+=getPieces().elementAt(18).getPossession();
		res+="-----------";
		res+=getPieces().elementAt(19).getPossession();
		res+="-----------";
		res+=getPieces().elementAt(20).getPossession();			
		System.out.println(res);
		
		//Ligne7
		res="";
		res+=getPieces().elementAt(21).getPossession();
		res+="------------------";
		res+=getPieces().elementAt(22).getPossession();
		res+="------------------";
		res+=getPieces().elementAt(23).getPossession();		
		System.out.println(res);
		
		*/
		
		//Ligne1
				res+=getPieces().elementAt(0).getPossession();
				res+="------------------";
				res+=getPieces().elementAt(1).getPossession();
				res+="------------------";
				res+=getPieces().elementAt(2).getPossession();		
				System.out.println(res);
				
				//Ligne2
				res="\t";
				res+=getPieces().elementAt(8).getPossession();
				res+="-----------";
				res+=getPieces().elementAt(9).getPossession();
				res+="-----------";
				res+=getPieces().elementAt(10).getPossession();			
				System.out.println(res);
				
				//Ligne3
				res="\t\t";
				res+=getPieces().elementAt(16).getPossession();
				res+="---";
				res+=getPieces().elementAt(17).getPossession();
				res+="---";
				res+=getPieces().elementAt(18).getPossession();			
				System.out.println(res);
				
				
				//Ligne4
				res="";
				res+=getPieces().elementAt(7).getPossession();
				res+="-------";
				res+=getPieces().elementAt(15).getPossession();
				res+="-----";
				res+=getPieces().elementAt(23).getPossession();		
				
				System.out.println(res+"          "+getPieces().elementAt(19).getPossession()+"-----"+getPieces().elementAt(11).getPossession()+"-------"+getPieces().elementAt(3).getPossession());
				
				//Ligne5
				res="\t\t";
				res+=getPieces().elementAt(22).getPossession();
				res+="---";
				res+=getPieces().elementAt(21).getPossession();
				res+="---";
				res+=getPieces().elementAt(20).getPossession();			
				System.out.println(res);
				
				//Ligne6
				res="\t";
				res+=getPieces().elementAt(14).getPossession();
				res+="-----------";
				res+=getPieces().elementAt(13).getPossession();
				res+="-----------";
				res+=getPieces().elementAt(12).getPossession();			
				System.out.println(res);
				
				//Ligne7
				res="";
				res+=getPieces().elementAt(6).getPossession();
				res+="------------------";
				res+=getPieces().elementAt(5).getPossession();
				res+="------------------";
				res+=getPieces().elementAt(4).getPossession();		
				System.out.println(res);
	}
	public void InitResult(int [] r){
		for (int i=0;i<6;i++){
			r[i]=-1;
		}
	}
	
	// Vrai si deux piÃ¨ces sont voisines
	public boolean estVoisin(int piece1, int piece2){
		for (int i=0; i<2; i++){
			if(VoisinsVerticaux[piece1][i]==piece2 || VoisinsHorizontaux[piece1][i]==piece2){
				return true;
			}
		}
		return false;		
	}
	
	//Est-ce qu'on peut poser une piÃ¨ce sur PlaceAJouer
	public boolean CoupValide(int PlaceAJouer){
		if(PlaceAJouer<24){
			if(getPieces().elementAt(PlaceAJouer).getPossession() == 0 ){
				return true;
			}
		}
		return false;
	}
	
	//Ajoute une piÃ¨ce sur PlaceAJouer du joueur actif
	public void AjouterPiece(int PlaceAJouer){
		int []result = new int [6];
		InitResult(result);
		
		if(CoupValide(PlaceAJouer))
		{
			result[0]=1;
			result[2]=PlaceAJouer;
			getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
			if(PresenceMoulin(PlaceAJouer)){
				System.out.println("Moulin");
				result[0]=4;
				if(TourDeJeu<18 && (0==TourDeJeu%2)){
					
					int Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'Ã©liminer la piÃ¨ce en position " + Choix);
					//Placement + Moulin a la case Choix
					this.getPieces().elementAt(Choix).setProprietaire(new Joueur());

					result[5]=Choix;
				}				
			}
			TourDeJeu++;
			ChangerJoueurActif();
		}
		
		updateGrid(result);		
	}
	

	public void DeplacerPiece(int PlaceADeplacer, int PlaceAJouer, int posses)
	{
		
		int []result = new int [6];
		InitResult(result);
		if(this.getPieces().elementAt(PlaceADeplacer).getPossession()==posses)
		{
			if(CoupValide(PlaceAJouer) && estVoisin(PlaceADeplacer,PlaceAJouer)){
				getPieces().elementAt(PlaceADeplacer).setProprietaire(new Joueur());
				getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
				result[0]=2; //Deplacement
				result[3]=PlaceADeplacer;
				result[4]=PlaceAJouer;
				if(PresenceMoulin(PlaceAJouer)){
					result[0]=5; //Deplacement Moulin
					if(0==TourDeJeu%2){ // Ordi joue
						int Choix=CiblePrioritaire();
						System.out.println("\nL'Ordi a choisi d'Ã©liminer la piÃ¨ce en position " + Choix);
						//Placement + Moulin a la case Choix
						this.getPieces().elementAt(Choix).setProprietaire(new Joueur());
						
						result[5]=Choix; //Piece Ã  dÃ©truire
					}
				}
				TourDeJeu++;
				ChangerJoueurActif();
			}
			
		}
		updateGrid(result);
	
	}
	
	// MÃ©thode utilisÃ©e pour les plateaux fils de la mÃ©thode plateauCoupSuivant
	public int DepPiecePlateauSuiv(int PlaceADeplacer, int PlaceAJouer, int posses)
	{
		if(this.getPieces().elementAt(PlaceADeplacer).getPossession()==posses)
		{
			if(CoupValide(PlaceAJouer) && estVoisin(PlaceADeplacer,PlaceAJouer)){
				getPieces().elementAt(PlaceADeplacer).setProprietaire(new Joueur());
				getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
				return 1;
			}
		}
		return 0;
	}
	
	
	//Posse c'est la possession du joueur actif
	public void RetirerPiece(int PlaceARetirer, int posse){
		int []result = new int [6];
		InitResult(result);
		if(this.getPieces().elementAt(PlaceARetirer).getPossession()!=posse || 
				this.getPieces().elementAt(PlaceARetirer).getPossession()!=0	){
			this.getPieces().elementAt(PlaceARetirer).setProprietaire(new Joueur());
			result[0]=3;
			result[5]=PlaceARetirer;	

		}		
		updateGrid(result);
	}
	
	//Possession, 1 pour l'ordi, 2 pour le joueur
	public Vector<Integer> PiecesPossedeesPar(int possession){
		Vector<Integer> V = new Vector<Integer>();
		for(int j=0;j<24;j++)
		{
			if(getPieces().elementAt(j).getPossession()==possession){
				V.addElement(j);
			}
		}
		return V;
		
	}
	
	// Vrai si nouveau moulin
	public boolean PresenceMoulin(int PlaceAverifier){
		//Prï¿½sence Horizontale d'un moulin
		//Si il n'y a qu'un voisin (le second initialisï¿½ ï¿½ 42), on regarde donc les deux voisins de ce voisin
		if(VoisinsHorizontaux[PlaceAverifier][1]==42){
			if(getPieces().elementAt(PlaceAverifier).getPossession() == getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() 
					&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession() 
					&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession()){
				return true;
			}			
		}else{
			if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession() 
					&& getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession()){
				return true;
			}
		}
		
		//PrÃ©sence Verticale d'un moulin
		if(VoisinsVerticaux[PlaceAverifier][1]==42){
			if(getPieces().elementAt(PlaceAverifier).getPossession() == getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() 
					&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession() 
					&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession()){
				return true;
			}			
		}else{
			if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession() 
					&& getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == getPieces().elementAt(PlaceAverifier).getPossession()){
				return true;
			}
		}
		return false;	
			
	}
	
	/**
	 * @return Pointe la piï¿½ce vide reprï¿½sentant la plus grande possibilitï¿½ de crï¿½er un moulin ou empï¿½chant la crï¿½ation d'un moulin adverse
	 */
	public int PlacementPrioritaire()
	{	
		int PrioriteUnVoisin=1;
		int PrioriteDeuxVoisins=5;
		
		int PrioriteUnVoisinAmi=15;
		int PrioriteUnVoisinAdverse=15;
				
		int PrioriteFuturMoulinAmi=100;
		int PrioriteFuturMoulinAdverse=50;
		
		int PrioriteMoulinBloque=-200;
		
	
		Vector<Integer> V = PiecesPossedeesPar(0);
		int[] Priorites = new int[V.size()];
		
		
		for(int i=0;i<V.size();i++){
			Priorites[i]=0;		
			
			if(VoisinsHorizontaux[V.elementAt(i)][1]!=42){
				//Ses deux voisins sont neutres
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 0 &&
						getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 0 )
				{
					Priorites[i]+=PrioriteDeuxVoisins;
				}else{
				//Ses deux voisins sont amis
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 1 &&
						getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 1 )
				{
					Priorites[i]+=PrioriteFuturMoulinAmi;
				}else{				
					//Ses deux voisins sont adverses
					if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 2 &&
							getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 2 )
					{
						Priorites[i]+=PrioriteFuturMoulinAdverse;
					}else{//Un voisin ennemi et un ami
						if((getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 1 &&
								getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 2 ) ||
									(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 2 &&
										getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 1 ))
								
						{
							Priorites[i]+=PrioriteMoulinBloque;
						}else{//Un voisin ami
							if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 1 ||
									getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 1 )
							{
								Priorites[i]+=PrioriteUnVoisinAmi;
							}else{//Un voisin ennemi
								Priorites[i]+=PrioriteUnVoisinAdverse;
							}							
						}
					}
				}
				}
			}else{//Le voisin est ami
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 1){
					//Le voisin du voisin est ami
					if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 1
					|| getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 1){
						Priorites[i]+=PrioriteFuturMoulinAmi;
					}else{//Le voisin du voisin est neutre
						if((getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 0
						&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0] != V.elementAt(i))
						|| (getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 0
						&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
							Priorites[i]+=PrioriteUnVoisinAmi;
						}else{//le voisin du voisin est adverse
							Priorites[i]+=PrioriteMoulinBloque;
						}
					}
				}else{//Le voisin est neutre
					if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 0){
						//Le voisin du voisin est ami
						if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 1
						|| getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 1){
							Priorites[i]+=PrioriteUnVoisinAmi;
						}else{//Le voisin du voisin est neutre
							if((getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 0
							&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0] != V.elementAt(i))
							|| (getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 0
							&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
								Priorites[i]+=PrioriteUnVoisin;
							}else{//le voisin du voisin est adverse
								Priorites[i]+=PrioriteUnVoisinAdverse;
							}
						}
					}else{//Le voisin est adverse
						if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 2){
							//Le voisin du voisin est ami
							if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 1
							|| getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 1){
								Priorites[i]+=PrioriteMoulinBloque;
							}else{//Le voisin du voisin est neutre
								if((getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0]).getPossession() == 0
								&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][0] != V.elementAt(i))
								|| (getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1]).getPossession() == 0
								&& VoisinsHorizontaux[VoisinsHorizontaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
									Priorites[i]+=PrioriteUnVoisinAdverse;
								}else{//le voisin du voisin est adverse
									Priorites[i]+=PrioriteFuturMoulinAdverse;
								}
							}
						}						
					}
				}
			}			
			
			//Cas Verticaux
			if(VoisinsVerticaux[V.elementAt(i)][1]!=42){
				//Ses deux voisins sont neutres
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 0 &&
						getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 0 )
				{
					Priorites[i]+=PrioriteDeuxVoisins;
				}else{
				//Ses deux voisins sont amis
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 1 &&
						getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 1 )
				{
					Priorites[i]+=PrioriteFuturMoulinAmi;
				}else{				
					//Ses deux voisins sont adverses
					if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 2 &&
							getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 2 )
					{
						Priorites[i]+=PrioriteFuturMoulinAdverse;
					}else{//Un voisin ennemi et un ami
						if((getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 1 &&
								getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 2 ) ||
									(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 2 &&
										getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 1 ))
								
						{
							Priorites[i]+=PrioriteMoulinBloque;
						}else{//Un voisin ami
							if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 1 ||
									getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 1 )
							{
								Priorites[i]+=PrioriteUnVoisinAmi;
							}else{//Un voisin ennemi
								Priorites[i]+=PrioriteUnVoisinAdverse;
							}							
						}
					}
				}
				}
			}else{//Le voisin est ami
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 1){
					//Le voisin du voisin est ami
					if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 1
					|| getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 1){
						Priorites[i]+=PrioriteFuturMoulinAmi;
					}else{//Le voisin du voisin est neutre
						if((getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 0
						&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0] != V.elementAt(i))
						|| (getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 0
						&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
							Priorites[i]+=PrioriteUnVoisinAmi;
						}else{//le voisin du voisin est adverse
							Priorites[i]+=PrioriteMoulinBloque;
						}
					}
				}else{//Le voisin est neutre
					if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 0){
						//Le voisin du voisin est ami
						if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 1
						|| getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 1){
							Priorites[i]+=PrioriteUnVoisinAmi;
						}else{//Le voisin du voisin est neutre
							if((getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 0
							&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0] != V.elementAt(i))
							|| (getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 0
							&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
								Priorites[i]+=PrioriteUnVoisin;
							}else{//le voisin du voisin est adverse
								Priorites[i]+=PrioriteUnVoisinAdverse;
							}
						}
					}else{//Le voisin est adverse
						if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 2){
							//Le voisin du voisin est ami
							if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 1
							|| getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 1){
								Priorites[i]+=PrioriteMoulinBloque;
							}else{//Le voisin du voisin est neutre
								if((getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0]).getPossession() == 0
								&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][0] != V.elementAt(i))
								|| (getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1]).getPossession() == 0
								&& VoisinsVerticaux[VoisinsVerticaux[V.elementAt(i)][0]][1] != V.elementAt(i))){
									Priorites[i]+=PrioriteUnVoisinAdverse;
								}else{//le voisin du voisin est adverse
									Priorites[i]+=PrioriteFuturMoulinAdverse;
								}
							}
						}						
					}
				}
			}
		}
		
		//RÃ©cupÃ¨re la PrioritÃ© la plus haute
		int max = 0;
		int maxi = 0;
		for(int i=0;i<V.size();i++){
			if(Priorites[i]>=max){
				max = Priorites[i];
				maxi=V.elementAt(i);
			}			
		}
		//System.out.println("\n"+maxi+" "+max);
		return maxi;
		
	}
	
	/**
	 * @return Pointe un couple d'entier, la piÃ¨ce Ã  bouger et la piÃ¨ce vide reprÃ©sentant la plus grande possibilitÃ© 
	 * de crÃ©er un moulin ou empÃªchant la crÃ©ation d'un moulin adverse et atteignable grÃ¢ce aux pions dÃ©jÃ  posÃ©s
	 */	
/*	public int[] DeplacementPrioritaire(){
		
		int PrioriteMoulin=10;
		int PrioriteBloquerMoulin=9;
		int PrioriteExMoulin=8;
		//Deux Pieces et une case vide
		int Priorite110=7;
		//Deux Pieces et une Piece adverse
		int Priorite112=6;
		//Une piece
		int Priorite010=5;
		
		//Recup de tous les pions de l'ordi
		Vector<Integer> V = PiecesPossedeesPar(1);
		//Les deplacements de [i] vers tous les [j] possibles
		int[][] Deplacement = new int[V.size()][4];
		int[][] Priorites = new int[V.size()][4];
								
								//La piece peut bloquer un moulin
								
								//La piece peut constituer un 110,011
								
								//La piece peut constituer un 112,211
								
								//La piece peut constituer un 010
								
								//La piece est dï¿½jï¿½ dans un moulin
									//Si on peut la dï¿½placer sans que l'autre joueur prenne la place, prioritï¿½ 
									//Sinon, on ne la bouge pas
								
								//La piece peut constituer un 112,211,121
							
								//La piece peut constituer un 010,100,001
								
								//La piece est dï¿½jï¿½ dans un moulin
									//Si on peut la dï¿½placer sans que l'autre joueur prenne la place, prioritï¿½ 3
									//Sinon, on ne la bouge pas
		int max = 0;
		int[] maxi = {0,0};
		for(int i=0;i<V.size();i++){
			for(int y=0;y<4;y++){
				if(Priorites[i][y]>max){
					max = Priorites[i][y];
					maxi[0]=i;
					maxi[1]=y;
					}
			}			
		}
		return maxi;	
	}*/
	
	/**
	 * @return Pointe la piï¿½ce adverse reprï¿½sentant le  plus de danger, la plus grande possibilitï¿½ de crï¿½er un moulin ou empï¿½chant la crï¿½ation dans de nos moulins
	 */
	public int CiblePrioritaire(){
		int Possession = 2;		
		int PrioriteDestructionMoulin=-100; //Detruire un moulin adverse permet Ã  l'adversaire de le recrï¿½er dans la foulï¿½e
		//Pieces possÃ©dÃ©es par le joueur
		Vector<Integer> V = PiecesPossedeesPar(Possession);
		int[] Priorites = new int[V.size()] ;		
		
		for(int i=0;i<V.size();i++){
			Priorites[i]=0;
			if(PresenceMoulin(V.elementAt(i))){
				Priorites[i]+=PrioriteDestructionMoulin;
			}			
			//Si la piÃ¨ce possÃ¨de deux voisins, on incrÃ©mnte sa prioritÃ©
			if(VoisinsHorizontaux[V.elementAt(i)][1]!=42){
				Priorites[i]++;
				//Si la piÃ¨ce possÃ¨de un de ses deux voisins dÃ©jÃ  Ã©tabli, on incrÃ©mente sa prioritÃ©
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == Possession 
				|| getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == Possession)
				{
					Priorites[i]++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == Possession)
				{
					Priorites[i]++;
				}
			}			
			if(VoisinsVerticaux[V.elementAt(i)][1]!=42){
				Priorites[i]++;
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == Possession 
				|| getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == Possession)
				{
					Priorites[i]++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == Possession)
				{
					Priorites[i]++;
				}
			}			
		}
		
		//RÃ©cupÃ¨re la PrioritÃ© la plus haute
		int max = 0;
		int maxi = 0;
		for(int i=0;i<V.size();i++){
			if(Priorites[i]>max){
				max = Priorites[i];
				maxi=V.elementAt(i);
			}			
		}
		return maxi;
	}
	
	
	/**
	 * @return Renvoi un indice reprÃ©sentant l'intÃ©rÃªt d'un plateau, plus il sera haut, plus l'ordi sera en position de force
	 */
	public int EvalPlateau(){
		int Possession = 2;
		Vector<Integer> PiecesOrdi = PiecesPossedeesPar(Possession-1);
		Vector<Integer> PiecesJoueur = PiecesPossedeesPar(Possession);
		
		int IndiceOrdi =  PiecesOrdi.size();
		int IndiceJoueur = PiecesJoueur.size();
		
		
		//Indice Ordi
		for(int i=0;i<PiecesOrdi.size();i++){

			//Si la piÃ¨ce possÃ¨de deux voisins, on incrÃ©mente sa prioritÃ© de 1
			if(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]!=42){
				IndiceOrdi++;
				//Si la piÃ¨ce possÃ¨de un de ses deux voisins dÃ©jÃ  Ã©tabli, on incrÃ©mente sa prioritÃ© de 1
				if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == Possession 
						|| getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == Possession )
				{
					IndiceOrdi++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == Possession)
				{
					IndiceOrdi++;
				}
			}
			
			if(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]!=42){
				IndiceOrdi++;
				if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == Possession 
						|| getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == Possession )
				{
					IndiceOrdi++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == Possession)
				{
					IndiceOrdi++;
				}
			}			
		}
		
		
		//Indice Joueur
				for(int i=0;i<PiecesJoueur.size();i++){

					//Si la piÃ¨ce possÃ¨de deux voisins, on incrÃ©mente sa prioritÃ© de 1
					if(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]!=42){
						IndiceJoueur++;
						//Si la piÃ¨ce possÃ¨de un de ses deux voisins dÃ©jÃ  Ã©tabli, on incrÃ©mente sa prioritÃ© de 1
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == Possession 
								|| getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == Possession )
						{
							IndiceJoueur++;
						}
					}
					else{
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == Possession)
						{
							IndiceJoueur++;
						}
					}
					
					if(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]!=42){
						IndiceJoueur++;
						if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == Possession 
								|| getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == Possession )
						{
							IndiceJoueur++;
						}
					}
					else{
						if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == Possession)
						{
							IndiceJoueur++;
						}
					}			
				}
				
				return IndiceOrdi-IndiceJoueur;		
	}
	// Renvoi un tableau contenant les plateaux fils de pm, ceux qui contiennent un coup suivant. AppelÃ© seulement par ordi
	public Vector<PlateauMoulin> plateauCoupSuivant(int possession){
		Vector<PlateauMoulin> ensFils = new Vector<PlateauMoulin>();
		int nivArbre = this.nivArbo+1; //Profondeur du plateau pere +1
		
		//Pour chaque coups possible on ajoute un plateau dans le vecteur
		for(int i=0; i<24; i++){
			if(getPieces().elementAt(i).getPossession()==possession){
				for(int j=0; j<2; j++){
					
					if(CoupValide(VoisinsHorizontaux[i][j])){
						PlateauMoulin pmDepH = new PlateauMoulin(this); // Plateau pour le deplacement
						pmDepH.nivArbo = nivArbre;
						if(pmDepH.DepPiecePlateauSuiv(i, VoisinsHorizontaux[i][j], possession)==1){
							//Deplacement valide donc on ajoute ce nouveau plateau au vect
							ensFils.addElement(pmDepH);
							pmDepH.ChangerJoueurActif();
						}
					}

					if(CoupValide(VoisinsVerticaux[i][j])){
						PlateauMoulin pmDepV = new PlateauMoulin(this); // Plateau pour le deplacement
						pmDepV.nivArbo = nivArbre;
						if(pmDepV.DepPiecePlateauSuiv(i, VoisinsVerticaux[i][j], possession)==1){
							//Deplacement valide donc on ajoute ce nouveau plateau au vect
							ensFils.addElement(pmDepV);
							pmDepV.ChangerJoueurActif();
						}
					}	
				}
			}
		}
		return ensFils;
	}
	// true si posses =1 c'est un coup pour l'ordi
	public boolean noeudMax(int posses){
		if(posses==1){
			return true;
		}
		return false;
	}
	
	// Vrai si le plateau est une feuille lors de l'Ã©valuaton des coups Ã  jouer
	public boolean EstFeuille(){
		if(this.nivArbo==2){// On fait une profondeur de 2 pour l'instant
			return true;
		}
		return false;
	}
	
	// Renvoi le min ou le max en fonction de niveauHarbo
	public int MinMax(int posses){
		if(this.EstFeuille()){ // Si le noeud est une feuille
			return this.EvalPlateau();
		}
		else{
			Vector<PlateauMoulin> vectPlateau = this.plateauCoupSuivant(posses);
			int minMax=0;
			if(this.noeudMax(posses)){ //Alors noeud max
				for(int i=0; i<vectPlateau.size();i++){
					minMax = Math.max(minMax,vectPlateau.elementAt(i).MinMax(posses));
				}
				return minMax;
			}
			else{ // noeud Min
				for(int i=0; i<vectPlateau.size();i++){
					minMax = Math.min(minMax,vectPlateau.elementAt(i).MinMax(posses));
				}
				return minMax;
			}
		}
	}
	
	
	// Renvoi la meilleur configuration de plateau Ã  jouer
	public PlateauMoulin meilleurCoup(int posses){
		Vector<PlateauMoulin> vectPlat = this.plateauCoupSuivant(posses);

		if(posses==1){ //L'ordi joue donc on passe Ã  l'humain
			posses=2;
		}else{posses=1;}// C'Ã©tait l'humain donc on passe Ã  l'ordi
	
		int Max=0;
		int maxIntermediaire=0;
		int indice=0;

		for(int i=0; i<vectPlat.size();i++){
			maxIntermediaire = Math.max(Max,vectPlat.elementAt(i).MinMax(posses));
			if(maxIntermediaire > Max){
				Max = maxIntermediaire;
				indice = i;
			}	
		}
		PlateauMoulin aJouer = vectPlat.elementAt(indice);
		return aJouer;
	}
	
	// Indique la piÃ¨ce Ã  deplacer pour arriver Ã  la configuration que renvoie meilleurCoup() 
	// avec indice 0 la case dÃ©part et indice 1 la case d'arrivÃ©e
	public int[] coupAJouer(PlateauMoulin pm){
		int[] caj = new int[2];
		for(int i=0; i<24; i++){
			// Si la possession a changÃ© alors la piÃ¨ce a quittÃ© la case ou est venue sur la case
			if(this.getPieces().elementAt(i).getPossession() != pm.getPieces().elementAt(i).getPossession()){
				// Si possession != 0 alors la piÃ¨ce part de cette case
				if(this.getPieces().elementAt(i).getPossession()!=0){
					caj[0]=i;
				}
				else{ // On est sur la case oÃ¹ la piÃ¨ce vient d'Ãªtre dÃ©placÃ©e
					caj[1]=i;
				}
			}
		}
		return caj;
	}
/*
	public void Jouer(){

		
		sc = new Scanner(System.in);
		int Choix = 0,i=0;
		String QuiJoue;
		//PremiÃ¨re partie, Positionnement des 9 pions de chaque joueur
		while(i<18){
			

			QuiJoue = getJoueurActif().getClass().getName();
			switch(QuiJoue){
			
			case "moulin.NonHumain" :
				Choix=PlacementPrioritaire();
				System.out.println("\nOrdi a jouÃ© Ã  "+Choix);
				
				if(CoupValide(Choix)){  //RÃ©pÃ©tition de CoupValide
					AjouterPiece(Choix);					
				}else{System.out.println("Mauvais choix");break;}
				
				
				if(PresenceMoulin(Choix)){
					Vector<Integer> V = PiecesPossedeesPar(2);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println(" - "+V.elementAt(j));
					}
					Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'Ã©liminer la piÃ¨ce en position " + Choix);
					RetirerPiece(Choix);

				}
								
				
				affichage();

				ChangerJoueurActif();
				i++; // Nb de coups pour la pose des pions
				break;
				
				
			case "moulin.Humain" :
				
				
				System.out.println("\nHumain: Choisissez une case : ");
				Choix = sc.nextInt();
				if(CoupValide(Choix) && Choix<24 && Choix>=0){
					AjouterPiece(Choix);
				}else{
					boolean choixOK = false;
					while(!choixOK){
						System.out.println("\nMauvais choix, resaississez une case");
						Choix = sc.nextInt();
						if(CoupValide(Choix) && Choix<24 && Choix>=0){
							choixOK = true;
							AjouterPiece(Choix);
						}
					}
				}
				
				
				
				if(PresenceMoulin(Choix)){
					System.out.println("\nHumain: Choisissez une piÃ¨ce Ã  supprimer parmi : ");
					Vector<Integer> V = PiecesPossedeesPar(1);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println("\n"+V.elementAt(j));
					}
					boolean BonChoix = true;
					while(BonChoix){
						System.out.println("\nChoisissez bien");
						Choix = sc.nextInt();
						for(int j=0;j<V.size();j++)
						{	
							if(Choix == V.elementAt(j)){
								BonChoix=false;
							}
						}
					}
					RetirerPiece(Choix);
				}
				
				affichage();

				ChangerJoueurActif();
				i++;
				break;

				
			 default :
				i=18;
				break;


		}
	}
		
		//Seconde partie, dÃ©placement
		int ChoixABouger,ChoixAAtteindre;
		System.out.println("\nDebut dÃ©placement !\n");
		while(PiecesPossedeesPar(1).size()>3 && PiecesPossedeesPar(2).size()>3 
				&& PiecesPossedeesPar(1).size()<10 && PiecesPossedeesPar(2).size()<10){
			QuiJoue = getJoueurActif().getClass().getName();
			
			switch(QuiJoue){
			
			
			case "moulin.NonHumain" :

				PlateauMoulin bestCoup = this.meilleurCoup(1);

				int[] coupAJ = coupAJouer(bestCoup);
				ChoixABouger = coupAJ[0];
				ChoixAAtteindre = coupAJ[1];

				if((DeplacerPiece(ChoixABouger, ChoixAAtteindre,1))==1){ // Mouvement possible
					System.out.println("\n Ordi dÃ©place la case "+ChoixABouger+" vers la case "+ ChoixAAtteindre);	

				}
				else{
					System.out.println("Ordi: soucis de dÃ©placement ...");
				}
				if(PresenceMoulin(ChoixAAtteindre)){
					Vector<Integer> V = PiecesPossedeesPar(2);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println(" - "+V.elementAt(j));
					}
					Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'Ã©liminer la piÃ¨ce en position " + Choix);
					RetirerPiece(Choix);

				}
				
				//affichage();

				ChangerJoueurActif();
				break;
				
				
			case "moulin.Humain" :

				System.out.println("\nHumain: Choisissez une case Ã  dÃ©placer : ");
				ChoixABouger = sc.nextInt();
				System.out.println("\nHumain: Choisissez une case Ã  atteindre : ");
				ChoixAAtteindre = sc.nextInt();
				
				//VÃ©rification de la possibilitÃ© du coup
				int depPossible = DeplacerPiece(ChoixABouger, ChoixAAtteindre,2);
				while(depPossible==0){ //Pas bon coup
					System.out.println("\n Vous ne pouvez pas jouer ce dÃ©placement, recommencez ");
					affichage();
					System.out.println("\nHumain: Choisissez une case Ã  dÃ©placer : ");
					ChoixABouger = sc.nextInt();
					System.out.println("\nHumain: Choisissez une case Ã  atteindre : ");
					ChoixAAtteindre = sc.nextInt();
					boolean saisieOK = (ChoixABouger<24 && ChoixAAtteindre<24);
					while(!saisieOK){
						System.out.println("\nChoix incorrect\n Humain: Rechoisissez une case Ã  dÃ©placer :");
						ChoixABouger = sc.nextInt();
						System.out.println("\nHumain: Rechoisissez une case Ã  atteindre :");
						ChoixAAtteindre = sc.nextInt();
						saisieOK = (ChoixABouger<24 && ChoixAAtteindre<24);
					}
					DeplacerPiece(ChoixABouger, ChoixAAtteindre,2);

				}
				
				if(PresenceMoulin(ChoixAAtteindre)){
					System.out.println("\nHumain: Choisissez une piÃ¨ce Ã  supprimer parmi : \n");
					Vector<Integer> V = PiecesPossedeesPar(1);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println(" - "+V.elementAt(j));
					}
					boolean BonChoix = false;
					while(!BonChoix){
						System.out.println("\nChoisissez bien");
						Choix = sc.nextInt();
						for(int j=0;j<V.size();j++)
						{	
							if(Choix == V.elementAt(j)){
								BonChoix=true;
							}
						}
					}
					RetirerPiece(Choix);
				}
				
				
				affichage();
				ChangerJoueurActif();			
				break;
			}
		}
		System.out.println("Un des deux joueurs a moins de trois piÃ¨ces ou plus de 9...");
	}
	*/
/***	
	/**Controleurs*
	public int[] ControleurHumain(int[] Proposition){
		
		int Choix,ChoixABouger,ChoixAAtteindre;
		int[] Result = new int[6];
		for(int i=0;i<6;i++){
			Result[i]=-1;
		}
		Result[1]=2;

		
		//Si il y a un moulin
		if(Proposition[2]<0 && Proposition[2]>23){
			if(getPieces().elementAt(Proposition[2]).getPossession() == 1){
				RetirerPiece(Proposition[2]);
				ChangerJoueurActif();
				TourDeJeu++;
				Result[0]=3;
				Result[5]=Proposition[2];
				updateGrid(Result);
				return Result;	
			}			
		}
		//Placement
		if(TourDeJeu<18){	
			
		else{//Deplacement
			Result[0]=2;	
			
			//Si il n'y a pas de proposition de deplacement, on envoie un Result[0] a 2 sans 
			//Result[2] et Result[3] afin de signifier que le deplacement propose est invalide
			if(Proposition[0]<0 && Proposition[0]>23 && Proposition[1]<0 && Proposition[1]>23 ){
					updateGrid(Result);
					return Result;	
			}
					
			
			ChoixABouger = Proposition[0];
			ChoixAAtteindre = Proposition[0];
			if(DeplacerPiece(ChoixABouger, ChoixAAtteindre,2) == 1){
				DeplacerPiece(ChoixABouger, ChoixAAtteindre,2);
				Result[2]=ChoixABouger;
				Result[3]=ChoixAAtteindre;
			}			
					
			//Si il y a presence d'un moulin, on envoie un Result[0] a 4 afin de recevoir en echange la piece a eliminer
			if(PresenceMoulin(ChoixAAtteindre)){
				Result[0] = 4;
				updateGrid(Result);
				return Result;	
			}
			
			
			ChangerJoueurActif();
			TourDeJeu++;
		}	
		
		updateGrid(Result);
		return Result;		
	}		
**/	
	public int[] ControleurOrdi(){
		System.out.println("Tourdejeu modele "+TourDeJeu);
		int Choix;
		int[] Result = new int[6];
		InitResult(Result); // init Ã -1
		
		//Possession a l'ordi
		Result[1]=1;
		
		if(TourDeJeu<18 && (0==TourDeJeu%2)){
			Choix=PlacementPrioritaire();
			System.out.println("\nOrdi a jouÃ© Ã  "+Choix);
			
		
			if(CoupValide(Choix)){  
				AjouterPiece(Choix);
			}else{System.out.println("Mauvais choix ordi");}

		}
		else{ //Deplacement
			if(0==TourDeJeu%2){	
			
				//Deplacement 
				//Result[0]=2;
				
				PlateauMoulin bestCoup = meilleurCoup(1);
	
				int[] coupAJ = coupAJouer(bestCoup);
				int ChoixABouger = coupAJ[0];
				//System.out.println("ChoixABouger"+ChoixABouger);
				int ChoixAAtteindre = coupAJ[1];
				//System.out.println("ChoixAAtteindre"+ChoixAAtteindre);
	
				Result[3]=ChoixABouger;
				Result[4]=ChoixAAtteindre;
				DeplacerPiece(ChoixABouger, ChoixAAtteindre,1);
				System.out.println("\n Ordi dÃ©place la case "+ChoixABouger+" vers la case "+ ChoixAAtteindre);	
			/*		
				if(PresenceMoulin(ChoixAAtteindre)){
					
					Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'Ã©liminer la piÃ¨ce en position " + Choix);
					RetirerPiece(Choix,1);
					Result[0]=5;
					Result[5]=Choix;
				}
				//ChangerJoueurActif();
				//TourDeJeu++;			
			*/
			}
		}
		updateGrid (Result);
		return Result;
	}
	
	
	/* --------- Implementation Observable --------- */
	
	public void updateGrid (int[] Tab)
	{
//		System.out.println("Coucou updategrid !! :D" );
		this.setChanged ();
		this.notifyObservers(Tab);
	}
	
	public void addObserver (Observer obs)
	{
		super.addObserver(obs);
	}

}