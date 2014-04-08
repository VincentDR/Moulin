package moulin;

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
 * 	Sixieme case -> Un voisin de la case moulin
 * 			Result[6] =	-1 ->	Mauvaise case
 *  					x ->	La case x voisine de la case qui a fait un moulin
 * 
 * 	Septieme case -> Le moulin
 * 			Result[7] =	-1 ->	Mauvaise case
 *  					x ->	La deuxieme case x  voisine de la case qui a fait un moulin
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
	
	// Vrai si deux pièces sont voisines
	public boolean estVoisin(int piece1, int piece2){
		for (int i=0; i<2; i++){
			if(VoisinsVerticaux[piece1][i]==piece2 || VoisinsHorizontaux[piece1][i]==piece2){
				return true;
			}
		}
		return false;		
	}

	//Si le voisin à l'indice "indice" (0 ou 1) existe à l'horizontale de la case "caseATester"
	public boolean voisinHorizontalExiste(int caseATester,int indice){
		return VoisinsHorizontaux[caseATester][indice]!=42;		
	}
	
	//Si le voisin à l'indice "indice" (0 ou 1) existe à la vertictale de la case "caseATester"
	public boolean voisinVerticalExiste(int caseATester,int indice){
		return VoisinsVerticaux[caseATester][indice]!=42;		
	}
	
	//Est-ce qu'on peut poser une pièce sur PlaceAJouer
	public boolean CoupValide(int PlaceAJouer){
		if(PlaceAJouer<24){
			if(getPieces().elementAt(PlaceAJouer).getPossession() == 0 ){
				return true;
			}
		}
		return false;
	}
	
	//Ajoute une pièce sur PlaceAJouer du joueur actif
	public void AjouterPiece(int PlaceAJouer){
		int []result = new int [8];
		InitResult(result);
		
		if(CoupValide(PlaceAJouer))
		{
			result[0]=1;
			result[2]=PlaceAJouer;
			getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
			if(PresenceMoulin(PlaceAJouer,result)){
				System.out.println("Moulin");
				result[0]=4;
				if(TourDeJeu<18 && (0==TourDeJeu%2)){
					
					int Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'éliminer la pièce en position " + Choix);
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
		
		int []result = new int [8];
		InitResult(result);
		if(this.getPieces().elementAt(PlaceADeplacer).getPossession()==posses)
		{
			if(CoupValide(PlaceAJouer) && estVoisin(PlaceADeplacer,PlaceAJouer)){
				getPieces().elementAt(PlaceADeplacer).setProprietaire(new Joueur());
				getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
				result[0]=2; //Deplacement
				result[3]=PlaceADeplacer;
				result[4]=PlaceAJouer;
				if(PresenceMoulin(PlaceAJouer,result)){
					result[0]=5; //Deplacement Moulin
					if(0==TourDeJeu%2){ // Ordi joue
						int Choix=CiblePrioritaire();
						System.out.println("\nL'Ordi a choisi d'éliminer la pièce en position " + Choix);
						//Placement + Moulin a la case Choix
						this.getPieces().elementAt(Choix).setProprietaire(new Joueur());
						
						result[5]=Choix; //Piece à détruire
					}
				}
				TourDeJeu++;
				ChangerJoueurActif();
			}
			
		}
		updateGrid(result);
	
	}
	
	// Méthode utilisée pour les plateaux fils de la méthode plateauCoupSuivant
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

	// Vrai si nouveau moulin pour la case PlaceAverifier
	public boolean PresenceMoulin(int PlaceAverifier){
		int possess = getPieces().elementAt(PlaceAverifier).getPossession();
		
		
		//Présence Horizontale d'un moulin, aussi on met dans result[3] et result[4] les voisins de la case qui fait le moulin
		if(voisinHorizontalExiste(PlaceAverifier,0)){ //VH[0] existe ?
			if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess ){
				if(voisinHorizontalExiste(PlaceAverifier,1)){ // 
					if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess){
						return true;
					}
				}	
				else{ //VH[0] à nous et VH[1] n'existe pas
					if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1]).getPossession() == possess){
							return true;
					}
				}
			}
		}
		else{ // VH[0] n'existe pas donc forcement VH[1] existe,  if(VH[1] à nous)
			if(	getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess){
				if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][0]).getPossession() == possess 
					&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1]).getPossession() == possess){
						return true;
				}
			}
		}	
			
		//Présence Verticale d'un moulin
		if(voisinVerticalExiste(PlaceAverifier,0)){ //VV[0] existe ?
			if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess ){
				if(voisinVerticalExiste(PlaceAverifier,1)){ // 
					if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess){
						return true;
					}
				}	
				else{ //VV[0] à nous et VV[1] n'existe pas
					if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == possess){
							return true;
					}
				}
			}
		}
		else{ // VV[0] n'existe pas,donc forcement VV[1] existe  if(VV[1] à nous)
			if(	getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess){
				if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0]).getPossession() == possess 
					&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][1]).getPossession() == possess){
						return true;
				}
			}
		}	
			
		//Aucun cas bon donc pas de moulin
		return false; 
	}
	
	// Vrai si nouveau moulin pour la case PlaceAverifier, 
	// ajoute les voisins moulin de result [2] dans result[3] et result[4]
	public boolean PresenceMoulin(int PlaceAverifier,int [] result){
		int possess = getPieces().elementAt(PlaceAverifier).getPossession();
		
		
		//Présence Horizontale d'un moulin, aussi on met dans result[3] et result[4] les voisins de la case qui fait le moulin
		if(voisinHorizontalExiste(PlaceAverifier,0)){ //VH[0] existe ?
			if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess ){
				if(voisinHorizontalExiste(PlaceAverifier,1)){ // 
					if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess){
						result[6]=VoisinsHorizontaux[PlaceAverifier][0];
						result[7]=VoisinsHorizontaux[PlaceAverifier][1];
						return true;
					}
				}	
				else{ //VH[0] à nous et VH[1] n'existe pas
					if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1]).getPossession() == possess){
							result[6]=VoisinsHorizontaux[PlaceAverifier][0];
							if(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1] != result[2]){
								result[7]=VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1];
							}
							else {
								result[7]=VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0];
							}
							return true;
					}
				}
			}
		}
		else{ // VH[0] n'existe pas donc forcement VH[1] existe,  if(VH[1] à nous)
			if(	getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess){
				if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][0]).getPossession() == possess 
					&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1]).getPossession() == possess){
						result[6]=VoisinsHorizontaux[PlaceAverifier][1];
						if(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1] != result[2]){
							result[7]=VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1];
						}
						else {
							result[7]=VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][0];
						}
						return true;
				}
			}
		}	
			
		//Présence Verticale d'un moulin
		if(voisinVerticalExiste(PlaceAverifier,0)){ //VV[0] existe ?
			if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess ){
				if(voisinVerticalExiste(PlaceAverifier,1)){ // 
					if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess){
						result[6]=VoisinsVerticaux[PlaceAverifier][0];
						result[7]=VoisinsVerticaux[PlaceAverifier][0];
						return true;
					}
				}	
				else{ //VV[0] à nous et VV[1] n'existe pas
					if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == possess){
							result[6]=VoisinsVerticaux[PlaceAverifier][0];
							if(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0] != result[2]){
								result[7]=VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0];
							}
							else {
								result[7]=VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1];
							}
							return true;
					}
				}
			}
		}
		else{ // VV[0] n'existe pas,donc forcement VV[1] existe  if(VV[1] à nous)
			if(	getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess){
				if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0]).getPossession() == possess 
					&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][1]).getPossession() == possess){
						result[6]=VoisinsVerticaux[PlaceAverifier][1];
						if(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0] != result[2]){
							result[7]=VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0];
						}
						else {
							result[7]=VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][1];
						}
						return true;
				}
			}
		}	
			
		//Aucun cas bon donc pas de moulin
		return false; 
	}		
	
	// Vrai si nouveau moulin pour la case PlaceAverifier
		public boolean PresenceMoulinD(int PlaceAIgnorer,int PlaceAverifier, int possess){		
			
			//Présence Horizontale d'un moulin
			if(voisinHorizontalExiste(PlaceAverifier,0)){ //VH[0] existe ?
				if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess 
					&& VoisinsHorizontaux[PlaceAverifier][0] != PlaceAIgnorer  ){
					if(voisinHorizontalExiste(PlaceAverifier,1)){ // 
						if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess 
						&& VoisinsHorizontaux[PlaceAverifier][1] != PlaceAIgnorer){
						return true;
						}
					}	
					else{ //VH[0] à nous et VH[1] n'existe pas
						if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0]).getPossession() == possess 
							&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1]).getPossession() == possess
							&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0] != PlaceAIgnorer
							&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1] != PlaceAIgnorer){
								return true;
						}
					}
				}
			}
			else{ // VH[0] n'existe pas donc forcement VH[1] existe,  if(VH[1] à nous)
				if(	getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess 
						&& VoisinsHorizontaux[PlaceAverifier][1] != PlaceAIgnorer){
					if(getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1]).getPossession() == possess
						&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0] != PlaceAIgnorer
						&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1] != PlaceAIgnorer){
							return true;
					}
				}
			}	
				
			//Présence Verticale d'un moulin
			if(voisinVerticalExiste(PlaceAverifier,0)){ //VH[0] existe ?
				if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess 
					&& VoisinsVerticaux[PlaceAverifier][0] != PlaceAIgnorer  ){
					if(voisinVerticalExiste(PlaceAverifier,1)){ // 
						if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess 
						&& VoisinsVerticaux[PlaceAverifier][1] != PlaceAIgnorer){
						return true;
						}
					}	
					else{ //VH[0] à nous et VH[1] n'existe pas
						if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == possess 
							&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == possess
							&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0] != PlaceAIgnorer
							&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1] != PlaceAIgnorer){
								return true;
						}
					}
				}
			}
			else{ // VH[0] n'existe pas donc forcement VH[1] existe,  if(VH[1] à nous)
				if(	getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess 
						&& VoisinsVerticaux[PlaceAverifier][1] != PlaceAIgnorer){
					if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][1]).getPossession() == possess
						&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0] != PlaceAIgnorer
						&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1] != PlaceAIgnorer){
							return true;
					}
				}
			}
			//Aucun cas bon donc pas de moulin
			return false; 
		}	

	
	/**
	 * @return Pointe la pi�ce vide repr�sentant la plus grande possibilit� de cr�er un moulin ou emp�chant la cr�ation d'un moulin adverse
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
		
		//Récupère la Priorité la plus haute
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
	 * @return Pointe un couple d'entier, la pièce à bouger et la pièce vide représentant la plus grande possibilité 
	 * de créer un moulin ou empêchant la création d'un moulin adverse et atteignable grâce aux pions déjà posés
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
								
								//La piece est d�j� dans un moulin
									//Si on peut la d�placer sans que l'autre joueur prenne la place, priorit� 
									//Sinon, on ne la bouge pas
								
								//La piece peut constituer un 112,211,121
							
								//La piece peut constituer un 010,100,001
								
								//La piece est d�j� dans un moulin
									//Si on peut la d�placer sans que l'autre joueur prenne la place, priorit� 3
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
	 * @return Pointe la pi�ce adverse repr�sentant le  plus de danger, la plus grande possibilit� de cr�er un moulin ou emp�chant la cr�ation dans de nos moulins
	 */
	public int CiblePrioritaire(){
		int possessionAdv = TourDeJeu%2==0 ? 2 : 1;	//Si tour de jeu==0(ordi) possessionAdversaire=2, sinon 1
		int PrioriteDestructionMoulin=-100; //Detruire un moulin adverse permet à l'adversaire de le recréer dans la foulée
		//Pieces possédées par le joueur
		Vector<Integer> V = PiecesPossedeesPar(possessionAdv);
		int[] Priorites = new int[V.size()];		
		
		for(int i=0;i<V.size();i++){
			Priorites[i]=1;
			
			if(PresenceMoulin(V.elementAt(i))){
				Priorites[i]+=PrioriteDestructionMoulin;
			}			
			//Si la pièce possède deux voisins, on incrémente sa priorité
			if(VoisinsHorizontaux[V.elementAt(i)][1]!=42){
				Priorites[i]++;
				//Si la pièce possède un de ses deux voisins déjà établi, on incrémente sa priorité
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == possessionAdv 
				|| getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == possessionAdv)
				{
					Priorites[i]++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == possessionAdv)
				{
					Priorites[i]++;
				}
			}			
			if(VoisinsVerticaux[V.elementAt(i)][1]!=42){
				Priorites[i]++;
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == possessionAdv 
				|| getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == possessionAdv)
				{
					Priorites[i]++;
				}
			}
			else{
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == possessionAdv)
				{
					Priorites[i]++;
				}
			}			
		}
		
		//Récupère la Priorité la plus haute
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
	 * @return Renvoi un indice représentant l'intérêt d'un plateau, plus il sera haut, plus l'ordi sera en position de force
	 */
	public int EvalPlateau(){
		Vector<Integer> piecesOrdi = PiecesPossedeesPar(1);
		Vector<Integer> piecesJoueur = PiecesPossedeesPar(2);
		
		//On est pas en fin de jeu, sinon return 0;	
		if(piecesOrdi.size()>=3 && piecesJoueur.size()>=3){ 
			int UnVoisin=1;
			int DeuxVoisins=5;
			
			int UnVoisinAmi=15;
					
			int FuturMoulin=1000;
			int MoulinEtabli=50;			
			
			int PossOrdi = 1;
			int PossJoueur = 2;
			Vector<Integer> PiecesOrdi = PiecesPossedeesPar(PossOrdi);
			Vector<Integer> PiecesJoueur = PiecesPossedeesPar(PossJoueur);
			
			int IndiceOrdi =  PiecesOrdi.size();
			int IndiceJoueur = PiecesJoueur.size();
			
			
			//Indice Ordi
			for(int i=0;i<PiecesOrdi.size();i++){
	
				//Si la piÃ¨ce possÃ¨de deux voisins
				if(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]!=42){
					IndiceOrdi+=DeuxVoisins;
					
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi
							&& getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == PossOrdi)
					{
						IndiceOrdi+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi
							|| getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == PossOrdi )
						{
							IndiceOrdi+=UnVoisinAmi;
						}					
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == 0){
						if(PresenceMoulinD(PiecesOrdi.elementAt(i),VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1],PossOrdi)){
							IndiceOrdi+=FuturMoulin;System.out.println("FuturMoulinOrdi");
						}
					}
				}
				else{
						IndiceOrdi+=UnVoisin;
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi)
					{
						IndiceOrdi+=UnVoisinAmi;
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == 0){
						if(PresenceMoulinD(PiecesOrdi.elementAt(i),VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0],PossOrdi)){
							IndiceOrdi+=FuturMoulin;System.out.println("FuturMoulinOrdi");
						}				
					}
				}
				
				
				
				if(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]!=42){
					IndiceOrdi+=DeuxVoisins;
					
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi 
							&& getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == PossOrdi )
					{
						IndiceOrdi+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi
							|| getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == PossOrdi )
						{
							IndiceOrdi+=UnVoisinAmi;
						}					
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == 0){
						if(PresenceMoulinD(PiecesOrdi.elementAt(i),VoisinsVerticaux[PiecesOrdi.elementAt(i)][1],PossOrdi)){
							IndiceOrdi+=FuturMoulin;System.out.println("FuturMoulinOrdi");
						}				
					}
				}
				else{
						IndiceOrdi+=UnVoisin;
					if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == PossOrdi)
					{
						IndiceOrdi+=UnVoisinAmi;
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == 0){
						if(PresenceMoulinD(PiecesOrdi.elementAt(i),VoisinsVerticaux[PiecesOrdi.elementAt(i)][0],PossOrdi)){
							IndiceOrdi+=FuturMoulin;System.out.println("FuturMoulinOrdi");
						}			
					}
				}					
			}
			
			
			
			//Indice Joueur
			for(int i=0;i<PiecesJoueur.size();i++){
	
				//Si la piÃ¨ce possÃ¨de deux voisins
				if(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]!=42){
					IndiceJoueur+=DeuxVoisins;
					
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == PossJoueur
							&& getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == PossJoueur)
					{
						IndiceJoueur+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == PossJoueur
							|| getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == PossJoueur )
						{
							IndiceJoueur+=UnVoisinAmi;
						}					
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == 0){
						if(PresenceMoulinD(PiecesJoueur.elementAt(i),VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1],PossJoueur)){
							IndiceJoueur+=FuturMoulin;System.out.println("FuturMoulinJoueur");
						}				
					}
				}
				else{
					IndiceJoueur+=UnVoisin;
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == PossJoueur)
					{
						IndiceJoueur+=UnVoisinAmi;
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == 0){
						if(PresenceMoulinD(PiecesJoueur.elementAt(i),VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0],PossJoueur)){
							IndiceJoueur+=FuturMoulin;System.out.println("FuturMoulinJoueur");
						}						
					}
				}
						
						
						
				if(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]!=42){
					IndiceJoueur+=DeuxVoisins;
								
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() ==PossJoueur 
						&& getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == PossJoueur){
							IndiceJoueur+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == PossJoueur 
							|| getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == PossJoueur){
								IndiceJoueur+=UnVoisinAmi;
						}					
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == 0){
						if(PresenceMoulinD(PiecesJoueur.elementAt(i),VoisinsVerticaux[PiecesJoueur.elementAt(i)][1],PossJoueur)){
							IndiceJoueur+=FuturMoulin;System.out.println("FuturMoulinJoueur");
						}						
					}
				}
				else{
					IndiceJoueur+=UnVoisin;
					if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == PossJoueur)
					{
						IndiceJoueur+=UnVoisinAmi;
					}
					//Si le prochain deplacement donne un moulin
					if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == 0){
						if(PresenceMoulinD(PiecesJoueur.elementAt(i),VoisinsVerticaux[PiecesJoueur.elementAt(i)][0],PossJoueur)){
							IndiceJoueur+=FuturMoulin;System.out.println("FuturMoulinJoueur");
						}					
					}
				}					
			}
			
			return IndiceOrdi-IndiceJoueur;		
		}//fin test (!fin de partie ?)
		else{
			return 0;
		}
	}
	// Renvoi un tableau contenant les plateaux fils de pm, ceux qui contiennent un coup suivant. Appelé seulement par ordi
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
	
	// Vrai si le plateau est une feuille lors de l'évaluaton des coups à jouer
	public boolean EstFeuille(){
		if(this.nivArbo==2){// On fait une profondeur de 2 pour l'instant
			return true;
		}
		return false;
	}
	
	// Renvoi le min ou le max en fonction de niveauHarbo
	public int MinMax(int posses){
		if(this.EstFeuille()){ // Si le noeud est une feuille
			int eval = this.EvalPlateau();
//System.out.println("Dans minMax est feuille + nivharbo :"+this.nivArbo+" eval :"+eval);

			return eval;
		}
		else{
			Vector<PlateauMoulin> vectPlateau = this.plateauCoupSuivant(posses);
			// On va utiliser la possession de l'adversaire pour le minMax des "sous plateaux"
			int possesAdv = posses==1 ? 2 : 1;
			
			int minMax;
			if(this.noeudMax(posses)){ // posses ==1
//System.out.println("Dans minMax noeud max + nivharbo :"+this.nivArbo);

				
				minMax=-10000;
				for(int i=0; i<vectPlateau.size();i++){
					minMax = Math.max(minMax,vectPlateau.elementAt(i).MinMax(possesAdv));
				}
//System.out.println("Result max:"+minMax);
				
				return minMax;
			}
			else{ // noeud Min
//System.out.println("Dans minMax noeud min + nivharbo :"+this.nivArbo);

				
				minMax=10000000;
				for(int i=0; i<vectPlateau.size();i++){
					minMax = Math.min(minMax,vectPlateau.elementAt(i).MinMax(possesAdv));
				}
//System.out.println("Result min:"+minMax);

				return minMax;
			}
		}
	}
	
	
	// Renvoi la meilleur configuration de plateau à jouer
	public PlateauMoulin meilleurCoup(int posses){
		Vector<PlateauMoulin> vectPlat = this.plateauCoupSuivant(posses);

		// On va utiliser la possession de l'adversaire pour le minMax des "sous plateaux"
		int possesAdv = posses==1 ? 2 : 1; 
	
		int Max=0;
		int maxIntermediaire=0;
		int indice=0;

		for(int i=0; i<vectPlat.size();i++){
//System.out.println("\nAppel minmax n° "+i);
			vectPlat.elementAt(i).affichage();
			maxIntermediaire = Math.max(Max,vectPlat.elementAt(i).MinMax(possesAdv));
//System.out.println("maxInter:"+maxIntermediaire);
			if(maxIntermediaire > Max){
				Max = maxIntermediaire;
				indice = i;
			}	
		

		}
//System.out.println("max obtenu :"+Max);
		PlateauMoulin aJouer = vectPlat.elementAt(indice);
		System.out.println("\n Plateau bon :");
		aJouer.affichage();
		return aJouer;
	}
	
	// Indique la pièce à deplacer pour arriver à la configuration que renvoie meilleurCoup() 
	// avec indice 0 la case départ et indice 1 la case d'arrivée
	public int[] coupAJouer(PlateauMoulin pm){
		int[] caj = new int[2];
		for(int i=0; i<24; i++){
			// Si la possession a changé alors la pièce a quitté la case ou est venue sur la case
			if(this.getPieces().elementAt(i).getPossession() != pm.getPieces().elementAt(i).getPossession()){
				// Si possession != 0 alors la pièce part de cette case
				if(this.getPieces().elementAt(i).getPossession()!=0){
					caj[0]=i;
				}
				else{ // On est sur la case où la pièce vient d'être déplacée
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
		//Première partie, Positionnement des 9 pions de chaque joueur
		while(i<18){
			

			QuiJoue = getJoueurActif().getClass().getName();
			switch(QuiJoue){
			
			case "moulin.NonHumain" :
				Choix=PlacementPrioritaire();
				System.out.println("\nOrdi a joué à "+Choix);
				
				if(CoupValide(Choix)){  //Répétition de CoupValide
					AjouterPiece(Choix);					
				}else{System.out.println("Mauvais choix");break;}
				
				
				if(PresenceMoulin(Choix)){
					Vector<Integer> V = PiecesPossedeesPar(2);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println(" - "+V.elementAt(j));
					}
					Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'éliminer la pièce en position " + Choix);
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
					System.out.println("\nHumain: Choisissez une pièce à supprimer parmi : ");
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
		
		//Seconde partie, déplacement
		int ChoixABouger,ChoixAAtteindre;
		System.out.println("\nDebut déplacement !\n");
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
					System.out.println("\n Ordi déplace la case "+ChoixABouger+" vers la case "+ ChoixAAtteindre);	

				}
				else{
					System.out.println("Ordi: soucis de déplacement ...");
				}
				if(PresenceMoulin(ChoixAAtteindre)){
					Vector<Integer> V = PiecesPossedeesPar(2);
					for(int j=0;j<V.size();j++)
					{	
						System.out.println(" - "+V.elementAt(j));
					}
					Choix=CiblePrioritaire();
					System.out.println("\nL'Ordi a choisi d'éliminer la pièce en position " + Choix);
					RetirerPiece(Choix);

				}
				
				//affichage();

				ChangerJoueurActif();
				break;
				
				
			case "moulin.Humain" :

				System.out.println("\nHumain: Choisissez une case à déplacer : ");
				ChoixABouger = sc.nextInt();
				System.out.println("\nHumain: Choisissez une case à atteindre : ");
				ChoixAAtteindre = sc.nextInt();
				
				//Vérification de la possibilité du coup
				int depPossible = DeplacerPiece(ChoixABouger, ChoixAAtteindre,2);
				while(depPossible==0){ //Pas bon coup
					System.out.println("\n Vous ne pouvez pas jouer ce déplacement, recommencez ");
					affichage();
					System.out.println("\nHumain: Choisissez une case à déplacer : ");
					ChoixABouger = sc.nextInt();
					System.out.println("\nHumain: Choisissez une case à atteindre : ");
					ChoixAAtteindre = sc.nextInt();
					boolean saisieOK = (ChoixABouger<24 && ChoixAAtteindre<24);
					while(!saisieOK){
						System.out.println("\nChoix incorrect\n Humain: Rechoisissez une case à déplacer :");
						ChoixABouger = sc.nextInt();
						System.out.println("\nHumain: Rechoisissez une case à atteindre :");
						ChoixAAtteindre = sc.nextInt();
						saisieOK = (ChoixABouger<24 && ChoixAAtteindre<24);
					}
					DeplacerPiece(ChoixABouger, ChoixAAtteindre,2);

				}
				
				if(PresenceMoulin(ChoixAAtteindre)){
					System.out.println("\nHumain: Choisissez une pièce à supprimer parmi : \n");
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
		System.out.println("Un des deux joueurs a moins de trois pièces ou plus de 9...");
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
		InitResult(Result); // init à-1
			
		if(0==TourDeJeu%2){	
			//Possession a l'ordi
			Result[1]=1;
			
			if(TourDeJeu<18){
				Choix=PlacementPrioritaire();
				System.out.println("\nOrdi a joué à "+Choix);
				
			
				if(CoupValide(Choix)){  
					AjouterPiece(Choix);
				}else{System.out.println("Mauvais choix ordi");}
	
			}
			else{ //Deplacement
				Vector<Integer> piecesOrdi = PiecesPossedeesPar(1);
				if(piecesOrdi.size()>=3){ //Si l'ordi a au moins trois pièces
				
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
					System.out.println("\n Ordi déplace la case "+ChoixABouger+" vers la case "+ ChoixAAtteindre);	
				/*		
					if(PresenceMoulin(ChoixAAtteindre)){
						
						Choix=CiblePrioritaire();
						System.out.println("\nL'Ordi a choisi d'éliminer la pièce en position " + Choix);
						RetirerPiece(Choix,1);
						Result[0]=5;
						Result[5]=Choix;
					}
					//ChangerJoueurActif();
					//TourDeJeu++;			
				*/
				}
				else{
					Result[0]=6; // Fin de partie
				}
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
