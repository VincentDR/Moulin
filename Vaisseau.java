package moulin;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public  class Vaisseau extends JButton  implements Runnable
{

	// Les images de l'explosion du vaisseau
    private BufferedImage [] imagesExplosion;
	private ImageIcon imageOriginale;
	// Equipe Ã  laquelle le bouton appartient
	//private int equipe;
	 		 // 0 : equipe 1 | 1 : equipe 2
	int etat = 0;
			 // Aucun |Â Deplacement | Explosion
	private Direction direction = Direction.HAUT;
	 // Aucun |Â Deplacement | Explosion
	
	// DÃ©placement du vaisseau (en pixel)
	int deplacement;
	// Rotation avant dÃ©placement (en degree)
    int rotation;
	// Boolean permettant de savoir si le bouton a Ã©tÃ© sÃ©lectionnÃ©
    boolean selectionne;
	// gyy
    int nbr_anim;
    // Angle du vaisseau par rapport Ã  l'axe vertical
	double angle;
	// INITIALISATION DES FICHIERS IMAGE
    private File [] filesVaisseau;
	//private File fileVaisseau = new File("Images/Animations/Explosions/Xplo1.png");

	// Les images du vaisseau
    private BufferedImage imageVaisseau;
    // L'image rotative
    private RotatingImage rotatingImage;
	
	public Vaisseau(int equipe)
	{


		
		if(equipe==0)
		{
			imageOriginale = new ImageIcon("Images/Animations/Explosions/Xplo1.png");
			//filesVaisseau = new File("Images/Animations/Explosions/Xplo1.png");
			nbr_anim = Constantes.NB_ANIMATIONS_W;
		}
		else
		{
			imageOriginale = new ImageIcon("Images/Animations/Explosions/Tplo1.png");
			//filesVaisseau = new File("Images/Animations/Explosions/Tplo1.png");
			nbr_anim = Constantes.NB_ANIMATIONS_T;
		}
		
		// Initialisation des fichiers et des images
		filesVaisseau = new File[nbr_anim];
		imagesExplosion = new BufferedImage[nbr_anim];
		// Initialisation de l'animation explosion
		for(int i=0;i<nbr_anim;i++)
		{
			String chemin="";
			if(equipe==0)
			    chemin = "Images/Animations/Explosions/Xplo"+(i+1)+".png";
			else
			    chemin = "Images/Animations/Explosions/Tplo"+(i+1)+".png";
			    //explosionXwing[i-1] = new ImageIcon(rotatingImage);
			filesVaisseau[i] = new File(chemin);
			    	
			try {

			    imagesExplosion[i] = ImageIO.read(filesVaisseau[i]);
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

			imageVaisseau = ImageIO.read(filesVaisseau[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		angle = 0.0;
		rotatingImage = new RotatingImage(imageVaisseau);
				//this.setIcon(rotatingImage);
				//setImage(rotatingImage);
		rotatingImage.setAngleD(angle);
				
			
		// Initialisation de l'animation explosion
		/*imagesExplosion = new ImageIcon[nbr_anim];
	    for(int i=1;i<nbr_anim;i++)
	    {
	    	String chemin="";
	    	if(equipe==0)
	    		chemin = "Images/Animations/Explosions/Xplo"+i+".png";
	    	else
	    		chemin = "Images/Animations/Explosions/Tplo"+i+".png";
	    	//explosionXwing[i-1] = new ImageIcon(rotatingImage);
	    	imagesExplosion[i-1] = new ImageIcon(chemin);
	    }*/

	    imageOriginale.setImage(rotatingImage);
	    
	    // On initialise l'imageIcon
		this.setIcon(imageOriginale);
		
		
		// test
	    deplacement = 0;
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
	
    // implÃ©mentation de la mÃ©thode run() de l'interface Runnable
    public  void run()
    {

		System.out.println("Debut fonction run(), x = "+getX());
      if(etat == 1) // etat = deplacement
      {
	       int i =  0 ; 
	       rotation = 20;
	       while (i <  rotation) {
	       try{
				  double oldAngle=0;
				if(rotatingImage!=null)
					oldAngle=rotatingImage.getAngleD();
					
				rotatingImage = new RotatingImage(imageVaisseau);
				System.out.println(oldAngle);
					
				rotatingImage.setAngleD(5 + oldAngle);

				imageOriginale.setImage(rotatingImage);
	        	 //this.setIcon(imagesExplosion[0]); 
	        	 //this.setIcon(imageOriginale); 
	        	 //explosionXwing[i].setImage(imageVaisseau); // L'ERREUR VIENT D'ICI
	        	 // peut etre comparer les tailles des 2 images au dessus. Si elles sont differentes, Ã§a peut poser probleme et l'affichage peut se remetre a jour.. ceci est une supposition. Et aussi une longue phrase.
	        	 // Jusque la
	        	 
	        	 
			//	animation[1].setImage(rotatingImage);
				//this.validate();
			//	boutonVaisseau.repaint();
				//repaint();
				//	repaint(this.getVisibleRect());
				repaint();
				System.out.println("On tourne");
				Thread.sleep(30);//sleep for 20 ms
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
	       	i++;
	       }
	       
	       i=0;
	       //while (n++ <  100) {
	       while (i <  deplacement) {
	          try {
	        	  //setLocation(getX()+4, getY());
	        	 //this.setIcon(explosionXwing[i]);
	        	 setLocation(getX()+5,getY()+2);
	        	 repaint();
	        	//repaint(getX(), getY(), getWidth()+20, getHeight()+20);
	            Thread.sleep(90) ;
	         }  catch (InterruptedException e) {
	         
	             // gestion de l'erreur
	         }
	          i++;
	       }
      }
      else if(etat == 2)  // etat = explosion
      {
    	  
    	  int i =  0 ; 
	       //while (n++ <  100) {
	       while (i < nbr_anim) {
	          try {
	        	  //ImageIcon imageTest = new ImageIcon("Images/Animations/blanc.png");
	        	  // test
	        	  
		     	   //this.setIcon(imagesExplosion[i]);
	        	  //  imageOriginale = imagesExplosion[i]; 
				   imageOriginale.setImage(imagesExplosion[i]);
		     	   repaint();

		     	//repaint(getX(), getY(), getWidth()+20, getHeight()+20);
	            Thread.sleep(70) ;
		     	  
	         }  catch (InterruptedException e) {
	         
	             // gestion de l'erreur
	         }
	     	  i++;
	       }

	       // On fait disparaitre le vaisseau ? Comment ?
	       this.setLocation(1200,1200);
      }

			System.out.println("Fin fonction run(), x = "+getX());
   }
    
  
    public int getDeplacement()
    {
    	return deplacement;
    }
    
    public void setDeplacement(int deplacement)
    {
    	this.deplacement = deplacement;
    }
  
    public int getRotation()
    {
    	return rotation;
    }
    
    public void setRotation(int rotation)
    {
    	this.rotation = rotation;
    }

	public boolean isSelectionne() {
		return selectionne;
	}

	public void setSelectionne(boolean selectionne) {
		this.selectionne = selectionne;
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
		imageOriginale.setImage(rotatingImage);
		//this.setIcon(xWing);
	}

	public void setEtat(int etat)
	{
		this.etat = etat;
	}
	
	
	public int getEtat() {
		return etat;
	}
}
