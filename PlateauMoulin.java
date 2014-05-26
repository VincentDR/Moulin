package moulin;

import java.util.Observer;
import java.util.Scanner;
import java.util.Vector;


/**
 * Tableau controle :
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
 * 						6 ->	Ordi a moins de 3 cases, fin de partie
 * 						7 -> 	Chargement
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
 *  	Sixième case -> Le moulin
 * 			Result[6] =	-1 ->	Pas de case voisine
 *  					x ->	La case x est un des deux voisins de la case qui a fait un moulin
 *  
 *  	Septième case -> Le moulin
 * 			Result[7] =	-1 ->	Pas de case voisine
 *  					x ->	La case x est le deuxième voisin de la case qui a fait un moulin
 *  	
 *  	Chargement 
 *  		Result[1] -> Le tour de Jeu
 *  		Result[2] -> La phase (1 pour le placement, 2pour le deplacement)
 *  		Result[3] -> Le nombre de pièces perdues par le joueur1/ordi
 *  		Result[4] -> Le nombre de pièces perdues par le joueur2/joueur
 *  		Result[5 à 29] -> La possession des pièces sur le plateau
 *  
 *
 * 
 */


public class PlateauMoulin extends Plateau{
	
	
	int[] dernierCoup = new int[2];
	int[] dernierCoup2 = new int[2];
	boolean changerDernierCoup = true;
	
	/***************/
	/***ATTRIBUTS***/
	/***************/
	
	private int[][] VoisinsHorizontaux;
	private int[][] VoisinsVerticaux;
	private int nivArbo;
	//De 0 a 17 -> Pair l'ordi pose une piece, impair le joueur pose une piece
	//Superieur a 17, Pair l'ordi bouge, impair le joueur bouge
	private int TourDeJeu;
	private boolean ordiVsJoueur; // =false si deux joueurs ou deux ordis, =true si Ordi Vs Joueur
	private boolean ordiVsOrdi; // =false si deux joueurs ou OrdiVsJoueur, =true si Ordi Vs Ordi
	

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
	
	public boolean getordiVsJoueur() {
		return ordiVsJoueur;
	}

	public void setordiVsJoueur(boolean ordiVsJ) {
		ordiVsJoueur = ordiVsJ;
	}
	
	public boolean getOrdiVsOrdi() {
		return ordiVsOrdi;
	}

	public void setOrdiVsOrdi(boolean ordiVsO) {
		this.ordiVsOrdi = ordiVsO;
	}
	
	
	/*******************/
	/***CONSTRUCTEURS***/
	/*******************/
	

	public PlateauMoulin(NonHumain ja,Humain jna) {		
		super(ja,jna);

		nivArbo=0;
		TourDeJeu=0;
		ordiVsJoueur = true;
		ordiVsOrdi = false;
		VoisinsHorizontaux = new int[24][2];
		VoisinsVerticaux = new int[24][2];
		
		for(int i=0;i<24;i++){
			for(int y=0;y<2;y++){
				VoisinsHorizontaux[i][y] = 42;
				VoisinsVerticaux[i][y] = 42;
			}
			
		}
		initialiserVoisinsCercle();
	}
	
	public PlateauMoulin(Humain ja,Humain jna) {		
		super(ja,jna);

		nivArbo=0;
		TourDeJeu=0;
		ordiVsJoueur = false;
		ordiVsOrdi = false;
		VoisinsHorizontaux = new int[24][2];
		VoisinsVerticaux = new int[24][2];
		
		for(int i=0;i<24;i++){
			for(int y=0;y<2;y++){
				VoisinsHorizontaux[i][y] = 42;
				VoisinsVerticaux[i][y] = 42;
			}
			
		}
		initialiserVoisinsCercle();
	}

	public PlateauMoulin(NonHumain ja,NonHumain jna) {		
		super(ja,jna);

		nivArbo=0;
		TourDeJeu=0;
		ordiVsJoueur = false;
		ordiVsOrdi = true;
		VoisinsHorizontaux = new int[24][2];
		VoisinsVerticaux = new int[24][2];
		
		for(int i=0;i<24;i++){
			for(int y=0;y<2;y++){
				VoisinsHorizontaux[i][y] = 42;
				VoisinsVerticaux[i][y] = 42;
			}
			
		}
		initialiserVoisinsCercle();
	}
	
	
	/**
	 * Création des liens entre cases afin de créer les voisins
	 * Le plateau est vu comme une spirale partant de la place en haut à gauche
	 */
	public void initialiserVoisinsCercle(){
		
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
	
	
	// Initialise le tableau envoyé à la vue pour update
	public void InitResult(int [] r){
		for (int i=0;i<8;i++){
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
	
	//Ajoute une pièce sur PlaceAJouer pour le joueur actif et vérifie la présence d'un moulin
	public void AjouterPiece(int PlaceAJouer){
		int []result = new int [8];
		InitResult(result);
		
		if(CoupValide(PlaceAJouer))
		{
			result[0]=1; //Placement
			result[2]=PlaceAJouer;
			getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
			if(PresenceMoulin(PlaceAJouer,result,2)){
				result[0]=4; // Placement + Moulin
				if( (0==TourDeJeu%2 && ordiVsJoueur) || ordiVsOrdi){ // 
				// Ordi dans jeu et c'est le tour de la machine (JvsO) ou deux ordi en jeu (OvsO)
					
					int Choix=CiblePrioritaire(getJoueurActif().getNiveau());
					this.getPieces().elementAt(Choix).setProprietaire(new Joueur());
					result[5]=Choix; // Pièce à détruire
				}
			}
			TourDeJeu++;
			ChangerJoueurActif();
		}
		updateGrid(result);		
	}
	
	//Déplace une pièce de PlaceADeplacer vers PlaceAJouer et vérifie la présence d'un moulin grâce à posses
	public void DeplacerPiece(int PlaceADeplacer, int PlaceAJouer, int posses)
	{
		
		int []result = new int [8];
		InitResult(result);
		if(this.getPieces().elementAt(PlaceADeplacer).getPossession()==posses)
		{
			if(CoupValide(PlaceAJouer) && estVoisin(PlaceADeplacer,PlaceAJouer)){
				getPieces().elementAt(PlaceADeplacer).setProprietaire(new Joueur());
				getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
				result[0]=2; // Déplacement
				result[3]=PlaceADeplacer;
				result[4]=PlaceAJouer;
				if(PresenceMoulin(PlaceAJouer,result,4)){
					result[0]=5; //Déplacement + Moulin
					if((0==TourDeJeu%2 && ordiVsJoueur) || ordiVsOrdi ){
					// Ordi dans jeu et c'est le tour de l'ordi(JvsO) ou deux ordi en jeu (OvsO)

						int Choix=CiblePrioritaire(getJoueurActif().getNiveau());
						this.getPieces().elementAt(Choix).setProprietaire(new Joueur());
						result[5]=Choix; // Pièce à détruire
					}
				}
				TourDeJeu++;
				ChangerJoueurActif();
			}
		}
		updateGrid(result);
	
	}
	
	// Méthode utilisée pour les plateaux fils de la méthode plateauCoupSuivant
	public boolean DepPiecePlateauSuiv(int PlaceADeplacer, int PlaceAJouer, int posses)
	{
		if(this.getPieces().elementAt(PlaceADeplacer).getPossession()==posses)
		{
			if(CoupValide(PlaceAJouer) && estVoisin(PlaceADeplacer,PlaceAJouer)){
				getPieces().elementAt(PlaceADeplacer).setProprietaire(new Joueur());
				getPieces().elementAt(PlaceAJouer).setProprietaire(getJoueurActif());
				return true;
			}
		}
		return false;
	}
	
	
	//Retire la pièce à la case PlaceARetirer, PosseAdv est la possession du joueur Non-Actif
	public void RetirerPiece(int PlaceARetirer, int posseAdv)
	{
		int []result = new int [8];
		InitResult(result);
		
		if(this.getPieces().elementAt(PlaceARetirer).getPossession()==posseAdv ){
			if(!PresenceMoulin(PlaceARetirer)){ // La pièce n'est pas dans un moulin, on la retire
				this.getPieces().elementAt(PlaceARetirer).setProprietaire(new Joueur());
				result[0]=3;
				result[5]=PlaceARetirer;
			}
			else{ // La pièce forme un moulin, on cherche une pièce sans moulin
				Vector<Integer> V = PiecesPossedeesPar(posseAdv); 
				boolean trouveCase = false;
				int compt = 0;
				while(trouveCase == false && compt < V.size()){
					// Si il y a au moins une pièce hors moulin, on ne fait rien
					if(!PresenceMoulin(V.elementAt(compt))){
						trouveCase=true;
					}
					compt++;
				}
				if(trouveCase==false){ // Tous les pions adverses sont dans un moulin
					// On supprime la case sélectionnée au début
					this.getPieces().elementAt(PlaceARetirer).setProprietaire(new Joueur());
					result[0]=3;
					result[5]=PlaceARetirer;
				}
			}
		}		
		updateGrid(result);
	}
	
	//Renvoie les pièces possédées par  1 pour l'ordi ou le joueur1, 2 pour le joueur ou le joueur2
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

	// Vrai si la case PlaceAverifier crée un nouveau moulin
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
	
	
	//Renvoie vrai si il existe AU MOINS un déplacement possible sur le plateau pour le joueurActif, faux sinon
	public boolean ExisteDeplacement(){
		Vector<Integer> V = PiecesPossedeesPar(getJoueurActif().getNumJoueur());
		int[][] Deplacement = new int[V.size()][4];
		for(int i=0;i<V.size();i++){
			for(int j=0;j<4;j++){
				Deplacement[i][j]=-1;
			}
		}
		
		int j=0;
		for(int i=0;i< V.size();i++){
			j=0;
			
			if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 0){
				Deplacement[i][j]=VoisinsHorizontaux[V.elementAt(i)][0];
				j++;
			}
			if(VoisinsHorizontaux[V.elementAt(i)][1] !=42){
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 0){
					Deplacement[i][j]=VoisinsHorizontaux[V.elementAt(i)][1];
					j++;
				}
			}
			
			if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 0){
				Deplacement[i][j]=VoisinsVerticaux[V.elementAt(i)][0];
				j++;
			}
			if(VoisinsVerticaux[V.elementAt(i)][1] !=42){
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 0){
					Deplacement[i][j]=VoisinsVerticaux[V.elementAt(i)][1];
					j++;
				}	
			}
		}
				
		boolean trouve = false;
		for(int i=0;i<V.size();i++){
			for(int k=0;k<4;k++){
				if(Deplacement[i][k]!=-1){
					trouve=true;
				}
			}
		}				
		return trouve;
	}
	
	
	
	
	
	
	
	
	/*  Vrai si la case PlaceAverifier crée un nouveau moulin
	* 	Ajoute les voisins moulin de result [numCaseResult] dans result[3] et result[4]
	*	numCaseResult = 2 si ajouterPiece, numCaseResult=4 si DeplacerPiece
	*/
	public boolean PresenceMoulin(int PlaceAverifier,int [] result,int numCaseResult){
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
							if(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1] != result[numCaseResult]){
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
						if(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][1]][1] != result[numCaseResult]){
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
						result[7]=VoisinsVerticaux[PlaceAverifier][1];
						return true;
					}
				}	
				else{ //VV[0] à nous et VV[1] n'existe pas
					if(getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == possess){
							result[6]=VoisinsVerticaux[PlaceAverifier][0];
							if(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0] != result[numCaseResult]){
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
						if(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][1]][0] != result[numCaseResult]){
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
	
	
	// Vrai si la case PlaceAverifier crée un nouveau moulin tout en excluant PlaceAIgnorer
	//A utiliser pour les déplacements prévus mais non effectués
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
	
	
	//Si la PlaceAVerifier bloquer un moulin du joueur Adverse
	//Possess est la possession du joueur non actif
	public boolean BloquerMoulin(int PlaceAverifier, int possess){
		//Présence Horizontale d'un moulin
			if(voisinHorizontalExiste(PlaceAverifier,1)){ //VH[0] existe ?
				if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess 
					&& getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess  ){
						if(voisinVerticalExiste(PlaceAverifier,1)){
							if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess 									
								|| getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess  ){
									return true;
							}
						}else{
							if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess){return true;}
						}
					}		
				}				
				else{
					if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess 
						&& ((getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][0] != PlaceAverifier)
						|| (getPieces().elementAt(VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1]).getPossession() == possess 
						&& VoisinsHorizontaux[VoisinsHorizontaux[PlaceAverifier][0]][1] != PlaceAverifier))){
							if(voisinVerticalExiste(PlaceAverifier,1)){
								if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess 									
										|| getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess  ){
											return true;
								}
							}else{
								if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess){return true;}
							}
						}					
				}			
				
				//Vertical
				if(voisinVerticalExiste(PlaceAverifier,1)){ //VH[0] existe ?
					if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess 
						&& getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][1]).getPossession() == possess  ){
							if(voisinHorizontalExiste(PlaceAverifier,1)){
								if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess 									
									|| getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess  ){
										return true;
									}
							}else{
								if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess){return true;}
						}
					}
				}
				else{
					if(getPieces().elementAt(VoisinsVerticaux[PlaceAverifier][0]).getPossession() == possess 
						&& ((getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0]).getPossession() == possess 
						&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][0] != PlaceAverifier)
						|| (getPieces().elementAt(VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1]).getPossession() == possess 
						&& VoisinsVerticaux[VoisinsVerticaux[PlaceAverifier][0]][1] != PlaceAverifier))){
							if(voisinHorizontalExiste(PlaceAverifier,1)){
								if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess 									
									|| getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][1]).getPossession() == possess  ){
											return true;
								}
							}else{
								if(getPieces().elementAt(VoisinsHorizontaux[PlaceAverifier][0]).getPossession() == possess){return true;}
							}
					}					
				}
		return false; 			
	}
	
	//Vrai si on peut sortir d'un moulin pour le récréer juste après
	//Possess est la possession adverse, PlaceDepart la place de départ (dans le moulin), PlaceAverifier la place hors moulin
	public boolean ReMoulin(int PlaceDepart,int PlaceAverifier, int posseA, int posse){
		if(PresenceMoulinD(PlaceAverifier,PlaceDepart,posse)){
			System.out.println("\n\n"+PlaceDepart+"\n\n");
			if(voisinHorizontalExiste(PlaceDepart,1)){
				if(getPieces().elementAt(VoisinsHorizontaux[PlaceDepart][0]).getPossession() != posseA
						&& getPieces().elementAt(VoisinsHorizontaux[PlaceDepart][1]).getPossession() != posseA){
					return true;					
				}				
			}else{return true;}
			
			if(voisinVerticalExiste(PlaceDepart,1)){
				if(getPieces().elementAt(VoisinsVerticaux[PlaceDepart][0]).getPossession() != posseA 
					&& getPieces().elementAt(VoisinsVerticaux[PlaceDepart][1]).getPossession() != posseA ){
					return true;					
				}				
			}else{return true;}
			
		}
		return false;
	}
	
	
	

	//Utilisé pour le mode OvsJ avec difficulté == 1 (facile)
	public int PlacementRandom(){
		int random=0;
		boolean trouve = false;
		while(!trouve){
			random = (int)(Math.random() * (24-0)) + 0;
			if(getPieces().elementAt(random).getPossession()==0){
				trouve=true;			
			}
		}			
		return random;
	}
	
	//Utilisé pour le mode OvsJ avec difficulté == 1 (facile)
	public int[] DeplacementRandom(){
		Vector<Integer> V = PiecesPossedeesPar(getJoueurActif().getNumJoueur());
		int[][] Deplacement = new int[V.size()][4];
		for(int i=0;i<V.size();i++){
			for(int j=0;j<4;j++){
				Deplacement[i][j]=-1;
			}
		}
		
		int j=0;
		for(int i=0;i< V.size();i++){
			j=0;
			
			if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == 0){
				Deplacement[i][j]=VoisinsHorizontaux[V.elementAt(i)][0];
				j++;
			}
			if(VoisinsHorizontaux[V.elementAt(i)][1] !=42){
				if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == 0){
					Deplacement[i][j]=VoisinsHorizontaux[V.elementAt(i)][1];
					j++;
				}
			}
			
			if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == 0){
				Deplacement[i][j]=VoisinsVerticaux[V.elementAt(i)][0];
				j++;
			}
			if(VoisinsVerticaux[V.elementAt(i)][1] !=42){
				if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == 0){
					Deplacement[i][j]=VoisinsVerticaux[V.elementAt(i)][1];
					j++;
				}	
			}
		}
		
		int [] res = new int [2];
		
		boolean trouve = false;
		while(!trouve){
			int random = (int)(Math.random() * (V.size()-0)) + 0;
			int random2 = (int)(Math.random() * (4-0)) + 0;	
			if(Deplacement[random][random2] !=-1){
				res[0]=V.elementAt(random);
				res[1]= Deplacement[random][random2];
				trouve=true;	
			}
		}	
		
		return res;
	}
	
	

	// Pointe la pièce vide reprèsentant la plus grande possibilité de créer un moulin ou empéchant la création d'un moulin adverse
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
		
		if(getJoueurActif().getNiveau() == 3 || getJoueurActif().getNiveau()==0 ){
			PrioriteMoulinBloque=0;
			PrioriteFuturMoulinAdverse=0;
		}
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
		for(int i=0;i<V.size();i++){
			if(Priorites[i]>=max){
				max = Priorites[i];
			}			
		}
		int Maxis[] = new int[10];
		for(int i=0;i<10;i++){
				Maxis[i]=-1;
		}

		int j=0;

		for(int i=0;i<V.size();i++){

			if(Priorites[i]==max){			
				Maxis[j] = V.elementAt(i);
				j++;
			}			
	}

		int lower = 0;
		int higher = j;
		int random = (int)(Math.random() * (higher-lower)) + lower;


			return Maxis[random];
	}
	

	//Pointe la pièce adverse représentant le  plus de danger, la plus grande possibilité de créer un moulin ou 
	//empéchant la création d'un de nos moulins
	public int CiblePrioritaireRandom(){
		int possessionAdv = getJoueurNActif().getNumJoueur();
		Vector<Integer> V = PiecesPossedeesPar(possessionAdv);
		
		
		int lower = 0;
		int higher = V.size();
		int random=0;
		boolean trouve=false;
		while(!trouve){
			random = (int)(Math.random() * (higher-lower)) + lower;
			if(!PresenceMoulin(V.elementAt(random))){trouve=true;}
		}
		
		return V.elementAt(random);
	}
	
	
	//Pointe la pièce adverse représentant le  plus de danger, la plus grande possibilité de créer un moulin ou 
	//empéchant la création d'un de nos moulins
	public int CiblePrioritaire(int difficulte){
		int possessionAdv = getJoueurNActif().getNumJoueur();	//Si tour de jeu==0 (joueur 1) possessionAdversaire=2, sinon 1
		// On ne doit pas détruire de moulin adverse sauf si il ne reste que ça
		int PrioriteDestructionMoulin=-100; //Detruire un moulin adverse permet à l'adversaire de le recréer dans la foulée
		//Pieces possédées par le joueur
		Vector<Integer> V = PiecesPossedeesPar(possessionAdv);
		int[] Priorites = new int[V.size()];		
		
		
		if(difficulte == 1){
			return CiblePrioritaireRandom();
		}else{
			if(difficulte == 3 || difficulte == 0){
				int PrioriteDeuxVoisins = 10;
				int PrioriteUnVoisinEtabli = 5;
						
				for(int i=0;i<V.size();i++){
					Priorites[i]=0;
					
					if(PresenceMoulin(V.elementAt(i))){
						Priorites[i]=PrioriteDestructionMoulin;
					}			
					//Si la pièce possède deux voisins, on incrémente sa priorité
					if(VoisinsHorizontaux[V.elementAt(i)][1]!=42){
						Priorites[i] += PrioriteDeuxVoisins;
						//Si la pièce possède un de ses deux voisins déjà établi, on incrémente sa priorité
						if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == possessionAdv 
						|| getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][1]).getPossession() == possessionAdv)
						{
							Priorites[i] += PrioriteUnVoisinEtabli;
						}
					}
					else{
						if(getPieces().elementAt(VoisinsHorizontaux[V.elementAt(i)][0]).getPossession() == possessionAdv)
						{
							Priorites[i] += PrioriteUnVoisinEtabli;
						}
					}			
					if(VoisinsVerticaux[V.elementAt(i)][1]!=42){
						Priorites[i]++;
						if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == possessionAdv 
						|| getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][1]).getPossession() == possessionAdv)
						{
							Priorites[i] += PrioriteUnVoisinEtabli;
						}
					}
					else{
						if(getPieces().elementAt(VoisinsVerticaux[V.elementAt(i)][0]).getPossession() == possessionAdv)
						{
							Priorites[i] += PrioriteUnVoisinEtabli;
						}
					}			
				}
			}else{
				for(int i=0;i<V.size();i++){
					Priorites[i]=1;
					
					if(PresenceMoulin(V.elementAt(i))){
						Priorites[i]=PrioriteDestructionMoulin;
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
			}
		}
		
		//Récupère la Priorité la plus haute
		int max = -10;
		int maxi = 0;
		for(int i=0;i<V.size();i++){
			if(Priorites[i]>max){
				max = Priorites[i];
				maxi=V.elementAt(i);
			}			
		}
		System.out.println("maxi : " + maxi);
		return maxi;
	}
	

	

	//Renvoi un indice représentant l'intérêt d'un plateau, plus il sera haut, plus l'ordi sera en position de force
	public int EvalPlateau(){
		//On est pas en fin de jeu, sinon return 0;	
		
			int UnVoisin=5;
			int DeuxVoisins=10;
			
			int UnVoisinAmi=50;
					
			int MoulinEtabli=10000;			
			
			int possJoueur1 = 1;
			int possJoueur2 = 2;
			Vector<Integer> PiecesOrdi = PiecesPossedeesPar(possJoueur1);
			Vector<Integer> PiecesJoueur = PiecesPossedeesPar(possJoueur2);
			
			int IndiceOrdi =  PiecesOrdi.size()*10;
			int IndiceJoueur = PiecesJoueur.size()*10;
			
			
			//Indice Ordi
			for(int i=0;i<PiecesOrdi.size();i++){
	
				//Si la piÃ¨ce possÃ¨de deux voisins
				if(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]!=42){
					IndiceOrdi+=DeuxVoisins;
					
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1
							&& getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == possJoueur1)
					{
						IndiceOrdi+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1
							|| getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][1]).getPossession() == possJoueur1 )
						{
							IndiceOrdi+=UnVoisinAmi;
						}					
					}
				}
				else{
						IndiceOrdi+=UnVoisin;
						if(PresenceMoulin(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0])){
							IndiceOrdi+=MoulinEtabli;
						}
						else{
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1)
					{
						IndiceOrdi+=UnVoisinAmi;
					}	
						}
				}
			
				
				
				if(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]!=42){
					IndiceOrdi+=DeuxVoisins;
					
					//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
					if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1
							&& getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == possJoueur1)
					{
						IndiceOrdi+=MoulinEtabli;
					}else{//Si la piece possede un seul voisin ami
						if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1
							|| getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][1]).getPossession() == possJoueur1 )
						{
							IndiceOrdi+=UnVoisinAmi;
						}					
					}
				}
				else{
					IndiceOrdi+=UnVoisin;
					if(PresenceMoulin(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0])){
						IndiceOrdi+=MoulinEtabli;
					}
					else{
				if(getPieces().elementAt(VoisinsVerticaux[PiecesOrdi.elementAt(i)][0]).getPossession() == possJoueur1)
				{
					IndiceOrdi+=UnVoisinAmi;
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
						if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2
								&& getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == possJoueur2)
						{
							IndiceJoueur+=MoulinEtabli;
						}else{//Si la piece possede un seul voisin ami
							if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2
								|| getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][1]).getPossession() == possJoueur2 )
							{
								IndiceJoueur+=UnVoisinAmi;
							}					
						}
					}
					else{
						IndiceJoueur+=UnVoisin;
						if(PresenceMoulin(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0])){
							IndiceJoueur+=MoulinEtabli;
						}
						else{
					if(getPieces().elementAt(VoisinsHorizontaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2)
					{
						IndiceJoueur+=UnVoisinAmi;
					}	
						}
				}
				
					
					
					if(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]!=42){
						IndiceJoueur+=DeuxVoisins;
						
						//Si la piÃ¨ce possÃ¨de ses deux voisins dÃ©jÃ  Ã©tabli
						if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2
								&& getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == possJoueur2)
						{
							IndiceJoueur+=MoulinEtabli;
						}else{//Si la piece possede un seul voisin ami
							if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2
								|| getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][1]).getPossession() == possJoueur2 )
							{
								IndiceJoueur+=UnVoisinAmi;
							}					
						}
					}
					else{
						IndiceJoueur+=UnVoisin;
						if(PresenceMoulin(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0])){
							IndiceJoueur+=MoulinEtabli;
						}
						else{
					if(getPieces().elementAt(VoisinsVerticaux[PiecesJoueur.elementAt(i)][0]).getPossession() == possJoueur2)
					{
						IndiceJoueur+=UnVoisinAmi;
					}	
						}
				}
				}
				
			
			return IndiceOrdi-IndiceJoueur;			
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
						pmDepH.setNivHarbo(nivArbre);
						if(pmDepH.DepPiecePlateauSuiv(i, VoisinsHorizontaux[i][j], possession)){
							//Deplacement valide donc on ajoute ce nouveau plateau au vect
							ensFils.addElement(pmDepH);
							pmDepH.ChangerJoueurActif();
						}
					}

					if(CoupValide(VoisinsVerticaux[i][j])){
						PlateauMoulin pmDepV = new PlateauMoulin(this); // Plateau pour le deplacement
						pmDepV.setNivHarbo(nivArbre);
						if(pmDepV.DepPiecePlateauSuiv(i, VoisinsVerticaux[i][j], possession)){
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
	
	
	// true si posses = posses du joueur actif
	public boolean noeudMax(int posses){
		if(posses== getJoueurActif().getNumJoueur()){
			return true;
		}
		return false;
	}
	
	
	// Vrai si le plateau est une feuille lors de l'évaluaton des coups à jouer
	public boolean EstFeuille(int niveauOrdi){
		if(niveauOrdi == 2){
			if(this.nivArbo==2){
				return true;
			}
		}
		else{
			if(niveauOrdi == 3){
				if(this.nivArbo==5){
					return true;
				}
			}
			else{
				if(this.nivArbo==6){
					return true;
				}	
			}
		}	
		return false;
	}
	
	
	/* Renvoi le min ou le max en fonction de niveauHarbo, avec coupure alpha-beta
	* possess représente la possession du joueur actif (son numéro de joueur)
	*/
	public int MinMax(int posses,int niveauOrdi,int alpha, int beta){
		
		int possesAdv = posses==1 ? 2 : 1;
		
		//TEST fin de partie pour le joueur adverse
		Vector<Integer> piecesJNActif = PiecesPossedeesPar(possesAdv);
		if(piecesJNActif.size()<=3 && TourDeJeu >=18){ 
			return (10000000);
		}		
		
		// Si le noeud est une feuille
		if(this.EstFeuille(niveauOrdi)){
			int eval = this.EvalPlateau();
			return eval;
		}
		else{
			Vector<PlateauMoulin> vectPlateau = this.plateauCoupSuivant(posses);
			// On va utiliser la possession de l'adversaire pour le minMax des "sous plateaux"
			
			if(this.noeudMax(posses)){ // noeud Max si possess égal à la possession du joueur actif
				for(int i=0; i<vectPlateau.size();i++){
					alpha = Math.max(alpha,vectPlateau.elementAt(i).MinMax(possesAdv,niveauOrdi,alpha,beta));
					if(alpha>=beta){
						return beta;
					}
				}
				return alpha;
			}
			else{ // noeud Min
				for(int i=0; i<vectPlateau.size();i++){
					beta = Math.min(beta,vectPlateau.elementAt(i).MinMax(possesAdv,niveauOrdi,alpha,beta));
					if(alpha>=beta){
						return alpha;
					}
				}
				return beta;
			}
		}
	}
	
	
	

	// Renvoi la meilleur configuration de plateau à jouer
	public PlateauMoulin meilleurCoup(int posses,int niveauOrdi){
		this.setNivHarbo(0);
		Vector<PlateauMoulin> vectPlat = this.plateauCoupSuivant(posses);

		// On va utiliser la possession de l'adversaire pour le minMax des "sous plateaux"
		int possesAdv = posses==1 ? 2 : 1; 
	
		int Max=-1000000;
		int maxIntermediaire=-1000000;
		int indice=0;

		for(int i=0; i<vectPlat.size();i++){
			//System.out.println("\nAppel minmax n° "+i);
//System.out.println("Max avant :"+Max);
//vectPlat.elementAt(i).affichage();
//System.out.println("Eval du plateau :"+vectPlat.elementAt(i).MinMax(possesAdv));
			maxIntermediaire = Math.max(Max,vectPlat.elementAt(i).MinMax(possesAdv,niveauOrdi,-10000, 100000));
//System.out.println("maxInter:"+maxIntermediaire +" \n");
			
			
			
			//Cas particulier dont la priorité doit être augmentée
			//Bricolage pour lancer directement un moulin
			if(vectPlat.elementAt(i).PresenceMoulinD(coupAJouer(vectPlat.elementAt(i))[0],coupAJouer(vectPlat.elementAt(i))[1],getJoueurActif().getNumJoueur())){
				//System.out.println("TestPresenceMoulin dans MeilleurCoup");
				maxIntermediaire=1000000000;
				
			}
			//Possibilité de faire bloquer la création d'un moulin 
			if(vectPlat.elementAt(i).BloquerMoulin(coupAJouer(vectPlat.elementAt(i))[1],getJoueurNActif().getNumJoueur())){
				//System.out.println("TestBloquerMoulin dans MeilleurCoup");
				maxIntermediaire=100000000;
		
			}
			//Possibilité de continuer à bloquer un moulin
			if(vectPlat.elementAt(i).BloquerMoulin(coupAJouer(vectPlat.elementAt(i))[0],getJoueurNActif().getNumJoueur())){
				//System.out.println("TestContinuerBloquerMoulin dans MeilleurCoup");
				maxIntermediaire=0;
				
			}
			//Possibilité de sortir d'un moulin pour le recréer dans la foulée
			if(vectPlat.elementAt(i).ReMoulin(coupAJouer(vectPlat.elementAt(i))[0],coupAJouer(vectPlat.elementAt(i))[1],getJoueurNActif().getNumJoueur(),getJoueurActif().getNumJoueur())){
				System.out.println("TestSortirMoulin dans MeilleurCoup");
				maxIntermediaire=1000000;
				
			}
			
			
			if((coupAJouer(vectPlat.elementAt(i))[0] == dernierCoup[1] && coupAJouer(vectPlat.elementAt(i))[1]== dernierCoup[0])
			 || (coupAJouer(vectPlat.elementAt(i))[0] == dernierCoup2[1] && coupAJouer(vectPlat.elementAt(i))[1]== dernierCoup2[0])
			 ||(coupAJouer(vectPlat.elementAt(i))[0] == dernierCoup[0] && coupAJouer(vectPlat.elementAt(i))[1]== dernierCoup[1])
			 || (coupAJouer(vectPlat.elementAt(i))[0] == dernierCoup2[0] && coupAJouer(vectPlat.elementAt(i))[1]== dernierCoup2[1])) {
				System.out.println("\n\nSAmeMouvement\n\n");
				maxIntermediaire -= 10000000;
			}
			
			
			if(maxIntermediaire > Max){
				Max = maxIntermediaire;
				indice = i;
			}	
			

		}
		//System.out.println("max obtenu :"+Max);
		PlateauMoulin aJouer = vectPlat.elementAt(indice);
		
		if(changerDernierCoup){
			dernierCoup[0]= coupAJouer(vectPlat.elementAt(indice))[0];
			dernierCoup[1] = coupAJouer(vectPlat.elementAt(indice))[1];
			changerDernierCoup = !changerDernierCoup;
		}else{
			dernierCoup2[0]= coupAJouer(vectPlat.elementAt(indice))[0];
			dernierCoup2[1] = coupAJouer(vectPlat.elementAt(indice))[1];
			changerDernierCoup = !changerDernierCoup;
		}


		//System.out.println("\n Plateau bon :");
		//aJouer.affichage();
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
	

	// Méthode appelée par la vue, quand c'est au tour d'un ordi de jouer
	public int[] ControleurOrdi(){
			
		int Choix;
		int[] Result = new int[8];
		InitResult(Result);
			
		if((0==TourDeJeu%2 && ordiVsJoueur) || ordiVsOrdi){ //On vérifie que c'est bien au tour d'une machine
			int numJActif = getJoueurActif().getNumJoueur();
			int niveauJActif = getJoueurActif().getNiveau();
			Result[1]=numJActif;
			
			if(TourDeJeu<18){ //Phase de placement
				if(niveauJActif == 1){//Niveau facile
					Choix = PlacementRandom();
				}
				else { // Niveau égal à 2 ou 3 pour OrdivsJoueur et 0 pour OrdivsOrdi
					Choix=PlacementPrioritaire(); 
				}
				
				if(CoupValide(Choix)){  
					AjouterPiece(Choix);
				}else{}
	
			}
			else{ //Deplacement
				
				Vector<Integer> piecesOrdi = PiecesPossedeesPar(numJActif);
				if(piecesOrdi.size()>=3){ //Si l'ordi actif a au moins trois pièces

					int[] coupAJ;
					if(niveauJActif == 1){
						coupAJ = DeplacementRandom();
					}
					else{ // niveau égal à 2 ou 3 pour OrdivsJoueur et 0 pour OrdivsOrdi
						PlateauMoulin bestCoup = meilleurCoup(numJActif,niveauJActif);
						coupAJ = coupAJouer(bestCoup);
					}	
					int ChoixABouger = coupAJ[0];
					int ChoixAAtteindre = coupAJ[1];
					
					Result[3]=ChoixABouger;
					Result[4]=ChoixAAtteindre;
					DeplacerPiece(ChoixABouger, ChoixAAtteindre,numJActif);
				}
				else{
					Result[0]=6; // Fin de partie
				}
			}
		}	
		updateGrid (Result);
		return Result;
	}
	
	
	public int[] charger(){
		int[] Result = new int[30];
		Result = super.charger();		
		
		Result[1] = this.getTourDeJeu();
		
		if(Result[1]<18){
			Result[2] = 1;
		}else{
			Result[2] = 2;
		}		
		
		int nbPiecesJoueur1 = 0;
		int nbPiecesJoueur2 = 0;
		
		for(int i=0;i<24;i++){
			if(this.getPieces().elementAt(i).getPossession() == 1){
				nbPiecesJoueur1++;
			}
			if(this.getPieces().elementAt(i).getPossession() == 2){
				nbPiecesJoueur2++;
			}
			Result[5 + i] = this.getPieces().elementAt(i).getPossession();
		}
		
		if(Result[1]<18){
			Result[3] = 9 - (nbPiecesJoueur1+ (9 - (this.getTourDeJeu()+1/2)));
			Result[4] = 9 - (nbPiecesJoueur2+ (9 - (this.getTourDeJeu()/2)));
		}else{
		}else{
			Result[3] = 9-nbPiecesJoueur1;
			Result[4] = 9-nbPiecesJoueur2;
		}
		
		if(getJoueurActif().getClass().getName() =="moulin.Humain" && getJoueurNActif().getClass().getName()=="moulin.Humain")
		{
			Result[29]=2;
		}else{
			if(getJoueurActif().getClass().getName() =="moulin.Humain" || getJoueurNActif().getClass().getName()=="moulin.Humain"){
				Result[29]=1;
			}else{
				Result[29]=0;
			}

		}
		
		updateGrid(Result);
		return Result;
	}
	
	
	/* --------- Implementation Observable --------- */
	
	public void updateGrid (int[] Tab)
	{
		this.setChanged ();
		this.notifyObservers(Tab);
	}
	
	public void addObserver (Observer obs)
	{
		super.addObserver(obs);
	}

}

