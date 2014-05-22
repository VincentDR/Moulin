package moulin;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public  class Vaisseau extends JButton  implements Runnable
{
	private Panneau monPanneau;
	// Les images de l'explosion du vaisseau
    private BufferedImage [] imagesExplosion;
	// Les images du placement du vaisseau
    private BufferedImage [] imagesPlacement;
	// Les images de l'animation moteur du vaisseau
    private BufferedImage [] imagesMoteur;
    
	private ImageIcon imageOriginale, imageAnimations, imageSelectionne;
	// Equipe à laquelle le bouton appartient
	//private int equipe;
	 		 // 0 : equipe 1 | 1 : equipe 2
	private int etat = Constantes.ET_NULL;
			 // Aucun | Deplacement | Explosion
	//private Direction direction = Direction.HAUT;
	 // Aucun | Deplacement | Explosion

	// Equipe du vaisseau : 0 : Rebelle | 1 : Imperiaux
	private int equipe;
	// numero du vaisseau
	private int numero;
	// Ecart entre le vaisseau et la position finale : 1, 2 ou 3
	private int ecart;
	private int direction;
	 		// 0 : Haut | 1 : Bas | Gauche | Droite
	// Déplacement du vaisseau verticalement (en pixel)
	//private int deplacementY;
	// Rotation avant déplacement (en degree)
	private int rotation;
	// Boolean permettant de savoir si le bouton a été sélectionné
	private boolean selectionne;
	// permet de savoir si le vaisseau fait partie d'un moulin : 0-Rien 1-Moulin 2-Nouveau moulin
	private int moulin;
	//..
	private int nbrAnimExplosion, nbrAnimPlacement;
    // Angle du vaisseau par rapport à l'axe vertical
	private double angle;
	// INITIALISATION DES FICHIERS IMAGE
    private File [] filesVaisseauExpl;
    private File [] filesVaisseauPlac;
	//private File fileVaisseau = new File("Images/Animations/Explosions/Xplo1.png");

	// Les images du vaisseau
    private BufferedImage imageVaisseau;
    // L'image rotative
    private RotatingImage rotatingImage;
	
	public Vaisseau(Panneau monPanneau, int equipe, int numero)
	{
		 this.monPanneau = monPanneau;
		 this.equipe = equipe;
		 this.numero = numero;
		 
		if(equipe==0)
		{
			imageOriginale = new ImageIcon("Images/Xwing.png");
			imageAnimations = new ImageIcon("Images/Xwing.png");
			imageSelectionne = new ImageIcon("Images/Animations/XwingSelect.png");
			//filesVaisseau = new File("Images/Animations/Explosions/Xplo1.png");
			nbrAnimExplosion = Constantes.NB_ANIMATIONS_EXPL_W;
			nbrAnimPlacement = Constantes.NB_ANIMATIONS_PLAC_W;
		}
		else
		{
			imageOriginale = new ImageIcon("Images/TIE.png");
			imageAnimations = new ImageIcon("Images/Xwing.png");
			imageSelectionne = new ImageIcon("Images/Animations/TIEselect.png");
			//filesVaisseau = new File("Images/Animations/Explosions/Tplo1.png");
			nbrAnimExplosion = Constantes.NB_ANIMATIONS_EXPL_T;
			nbrAnimPlacement = Constantes.NB_ANIMATIONS_PLAC_T;
		}
		
		// Initialisation des fichiers et des images
		filesVaisseauExpl = new File[nbrAnimExplosion];
		filesVaisseauPlac = new File[nbrAnimPlacement];
		imagesExplosion = new BufferedImage[nbrAnimExplosion];
		imagesPlacement = new BufferedImage[nbrAnimPlacement];
		
		// Initialisation de l'animation explosion
		for(int i=0;i<nbrAnimExplosion;i++)
		{
			String chemin="";
			if(equipe==0)
			    chemin = "Images/Animations/Explosions/Xplo"+(i+1)+".png";
			else
			    chemin = "Images/Animations/Explosions/Tplo"+(i+1)+".png";
			    //explosionXwing[i-1] = new ImageIcon(rotatingImage);
			filesVaisseauExpl[i] = new File(chemin);
			    	
			try {

			    imagesExplosion[i] = ImageIO.read(filesVaisseauExpl[i]);
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Initialisation de l'animation placement
		for(int i=0;i<nbrAnimPlacement;i++)
		{
			String chemin="";
			if(equipe==0)
			    chemin = "Images/Animations/Teleportations/Xtp"+i+".png";
			else
			    chemin = "Images/Animations/Teleportations/Ttp"+i+".png";
			
			filesVaisseauPlac[i] = new File(chemin);
			    	
			try {

			    imagesPlacement[i] = ImageIO.read(filesVaisseauPlac[i]);
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/// FIN TEST
		///
			    
		//super(iI);


		// Initialisation de l'image rotative
		try {

			imageVaisseau = ImageIO.read(filesVaisseauExpl[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		angle = 0.0;
		rotatingImage = new RotatingImage(imageVaisseau);
				//this.setIcon(rotatingImage);
				//setImage(rotatingImage);
		rotatingImage.setAngleD(angle);
				
	    //imageOriginale.setImage(rotatingImage);
	    
	    // On initialise l'imageIcon
		this.setIcon(imageOriginale);
		
		
		// test
	    //deplacement = 0;
	    direction = 0;
	    rotation = 0;
	    selectionne = false;
	    

/*
	    for(int i=0;i<14;i++)
	    {
	    	explosionXwing[i].setImage(rotatingImage);
	    }*/
	}
	

	public void addActionListener(ActionListener l)
	{
		//super(l);
		super.addActionListener(l);
	}
	
    // implémentation de la méthode run() de l'interface Runnable
    public  void run()
    {
    	
    	this.setIcon(imageAnimations);
		System.out.println("Debut fonction run(), x = "+getX());
		

		if(etat == Constantes.ET_PLAC_1) // etat = placement
	    {
			int i = 0 ;
			System.out.println("nbrAnimPlacement = " + nbrAnimPlacement);
			while (i < nbrAnimPlacement)
		       {
		          try {
		        	  	
		        	  
		        	  	imageAnimations.setImage(imagesPlacement[i]);
				     	repaint();
			            Thread.sleep(60) ; // 70
		          	}  
		          	catch (InterruptedException e) {
		         
		             // gestion de l'erreur
		          	}
					i++;
		       }
		       this.monPanneau.threadPlacementPhase1Termine(/*numero*/);
			
	    }
		else if(etat == Constantes.ET_PLAC_2) // etat = placement
	    {
			int i = nbrAnimPlacement-1 ;
			System.out.println("nbrAnimPlacement = " + nbrAnimPlacement);
			while (i >= 0)
		       {
		          try {
		        	  	
		        	  
		        	  	imageAnimations.setImage(imagesPlacement[i]);
				     	repaint();
			            Thread.sleep(60) ; // 70
		          	}  
		          	catch (InterruptedException e) {
		         
		             // gestion de l'erreur
		          	}
					i--;
		       }
		       this.monPanneau.threadPlacement2Termine(/*numero*/);
			
	    }
		else if(etat == Constantes.ET_DEPL) // etat = deplacement
		{
	    	this.setIcon(imageOriginale);
			int i =  0 ; 
			int deplacementX = 0, deplacementY = 0;
	       
			while (i <  rotation) 
			{
				try{
					double oldAngle=0;
					if(rotatingImage!=null)
						oldAngle=rotatingImage.getAngleD();
					
					if(direction==Constantes.DIR_GAUCHE)
					{
						setAngle(oldAngle-Constantes.NBR_TIKS_ROT);
					}
					else
					{
						setAngle(oldAngle+Constantes.NBR_TIKS_ROT);
					}
					
					repaint();
					System.out.println("On tourne");
					Thread.sleep(Constantes.VITESSE_ROT);//sleep for 20 ms
				}
				catch(InterruptedException ie){
					ie.printStackTrace();
				}
		       	i++;
	       }
	       
	       i=0;
	       
	       // On deplace
	       while (i < ecart*(Constantes.ECART/Constantes.NBR_TIKS_DEP))
	       {
	          try {
	        	  //setLocation(getX()+4, getY());
	        	 //this.setIcon(explosionXwing[i]);
	        	 //setLocation(getX()+deplacementX,getY()+deplacementY);
					if(direction==Constantes.DIR_GAUCHE){
						deplacementX = -Constantes.NBR_TIKS_DEP;}
					else if(direction==Constantes.DIR_DROITE){
						deplacementX = Constantes.NBR_TIKS_DEP;}
					else if(direction==Constantes.DIR_HAUT){
						deplacementY = -Constantes.NBR_TIKS_DEP;}
					else if(direction==Constantes.DIR_BAS){
						deplacementY = Constantes.NBR_TIKS_DEP;}
					
					setLocation(getX()+deplacementX,getY()+deplacementY);
					
					repaint();
					//repaint(getX(), getY(), getWidth()+20, getHeight()+20);
					Thread.sleep(Constantes.VITESSE_DEP) ;
	          	}  
	          	catch (InterruptedException e) {
	         
	             // gestion de l'erreur
	          	}
				System.out.println("On se déplace");
				i++;
	       }
	       
	       i=0;
	       // On remet droit le vaisseau
	       while (i <  rotation)
	       {
		       try{
					double oldAngle=0;
					if(rotatingImage!=null)
						oldAngle=rotatingImage.getAngleD();
					
					if(direction==Constantes.DIR_GAUCHE)
					{
						setAngle(oldAngle+Constantes.NBR_TIKS_ROT);
					}
					else
					{
						setAngle(oldAngle-Constantes.NBR_TIKS_ROT);
					}
					
					repaint();
					System.out.println("On retourne");
					Thread.sleep(Constantes.VITESSE_ROT);
				}
				catch(InterruptedException ie){
					ie.printStackTrace();
				}
		       	i++;
		   }
	       
	       etat =  Constantes.ET_NULL;
	       this.monPanneau.threadDeplacementTermine();
      }
      else if(etat == Constantes.ET_EXPL)  // etat = explosion
      {
    	  
    	  int i =  0 ; 
	       //while (n++ <  100) {
	       while (i < nbrAnimExplosion) {
	          try {
	        	  //ImageIcon imageTest = new ImageIcon("Images/Animations/blanc.png");
	        	  // test
	        	  
		     	   //this.setIcon(imagesExplosion[i]);
	        	  //  imageOriginale = imagesExplosion[i]; 
	        	   imageAnimations.setImage(imagesExplosion[i]);
		     	   repaint();
		     	//repaint(getX(), getY(), getWidth()+20, getHeight()+20);
	            Thread.sleep(70) ;
	         }  catch (InterruptedException e) {
	         
	             // gestion de l'erreur
	         }
	     	  i++;
	       }

	       // On fait disparaitre le vaisseau ? Comment ?
	       //this.setLocation(1200,1200);
	       // simplement en envoyant son numero ici
	       this.monPanneau.threadExplosionTermine(numero);
      }

			System.out.println("Fin fonction run(), x = "+getX());
			
	 this.setIcon(imageOriginale);
   }
    
  
    /*public int getDeplacement()
    {
    	return deplacement;
    }
    
    public void setDeplacement(int deplacement)
    {
    	this.deplacement = deplacement;
    }*/
  
    public int getRotation()
    {
    	return rotation;
    }
    
    public void setRotation(int rotation)
    {
    	this.rotation = rotation;
    }
  
    public int getMoulin()
    {
    	return moulin;
    }
    
    public void setMoulin(int moulin)
    {
    	this.moulin = moulin;
    }

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
		if(selectionne == true)
		{
			this.setIcon(imageSelectionne);
		}
		else
		{
			this.setIcon(imageOriginale);
		}
	}
	
	public void setAngle(double a, double b)
	{
		
		double x1,x2,y1,y2;
		x1=a;
		y1=b;
		x2=getX()+getWidth()/2;
		y2=getY()+getHeight()/2;
		//test = Math.toDegrees(Math.atan((x1-x2)/(y2-y1)));
		if((y2-y1)>=0)
			angle = Math.toDegrees(Math.atan((x1-x2)/(y2-y1)));
		else
			angle = Math.toDegrees(Math.atan((x1-x2)/(y2-y1)))+180;
		
		actualiserAngle();
		//this.setIcon(xWing);
		
	}
	
	public void setAngle(double angle)
	{
		this.angle = angle;
		actualiserAngle();
	}
	
	public void actualiserAngle()
	{
		rotatingImage = new RotatingImage(imageVaisseau);
		//this.setIcon(rotatingImage);
		//setImage(rotatingImage);
		rotatingImage.setAngleD(angle);
		
		//////((ImageIcon)this.getIcon()).setImage(rotatingImage);
		imageOriginale.setImage(rotatingImage);
		//this.setIcon(xWing);
		repaint();
	}

	public void setEtat(int etat)
	{
		this.etat = etat;
	}
	
	
	public int getEtat() {
		return etat;
	}

	public void setEquipe(int equipe)
	{
		this.equipe = equipe;
	}
	
	
	public int getEquipe() {
		return equipe;
	}
	
	public void calculerRotation(int caseX, int caseY)
	{
		//caseDestination
		// Si le deplacement est à gauche
		if(getX() > caseX)
		{
			direction = Constantes.DIR_GAUCHE;
			ecart = (getX()-caseX)/Constantes.ECART;
			rotation = 90/Constantes.NBR_TIKS_ROT;
		}
			// Si le deplacement est à droite
		else if(getX() < caseX)
		{
			direction = Constantes.DIR_DROITE;
			ecart = (caseX-getX())/Constantes.ECART;
			rotation = 90/Constantes.NBR_TIKS_ROT;
		}
			// Si le deplacement est en haut
		else if(getY() > caseY)
		{
			direction = Constantes.DIR_HAUT;
			ecart = (getY()-caseY)/Constantes.ECART;
			rotation = 0;
		}
			// Si le deplacement est en bas
		else if(getY() < caseY)
		{
			direction = Constantes.DIR_BAS;
			ecart = (caseY-getY())/Constantes.ECART;
			rotation = 180/Constantes.NBR_TIKS_ROT;
		}
	}
	
	public void raz()
	{
		this.setIcon(imageOriginale);
		this.etat = Constantes.ET_NULL;
		
		this.setSelectionne(false);
		this.setMoulin(0);
	}
}
