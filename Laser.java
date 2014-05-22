package moulin;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Laser extends JButton  implements Runnable{

	private Panneau monPanneau;
	private ImageIcon imageOriginale;
	private int etat = Constantes.ET_NULL;
			 // Aucun | Deplacement | Explosion
	//private Direction direction = Direction.HAUT;
	 // Aucun | Deplacement | Explosion

	// Equipe du vaisseau : 0 : Rebelle | 1 : Imperiaux
	private int equipe;
	private int numero;
	
	private boolean sens;
	private int x, y;
	 		// 0 : Haut | 1 : Bas | Gauche | Droite
	// Déplacement du vaisseau verticalement (en pixel)
	//private int deplacementY;
	private int nbr_anim;
    // Angle du vaisseau par rapport à l'axe vertical
	private double angle;
	// INITIALISATION DES FICHIERS IMAGE
    private File fileLaser;
	//private File fileVaisseau = new File("Images/Animations/Explosions/Xplo1.png");

	// Les images du laser
    private BufferedImage imageLaser;
    // L'image rotative
    private RotatingImage rotatingImage;
    

    public Laser(){}
    
    public Laser(Panneau monPanneau, int equipe, int x, int y, int numero)
    {
    	this.numero = numero;
    	this.setPreferredSize(new Dimension(200,200));
		
		this.monPanneau = monPanneau;

		if(equipe==Constantes.FACTION_REBELLE)
		{
			imageOriginale = new ImageIcon("Images/laserRebelleDouble.png");
		}
		else // equipe==1
		{
			imageOriginale = new ImageIcon("Images/laserEmpireDouble.png");
		}
		this.setFocusPainted( false ); // enleve la bordure de l'image
		this.setBorderPainted(false); // enleve la bordure du bouton
		//vaisseau[i].setOpaque(false); // enleve la bordure du bouton
		this.setContentAreaFilled(false);
		
		// Initialisation des fichiers et des images
		
			String chemin="";
			if(equipe==0)
			    chemin = "Images/laserRebelleDouble.png";
			else
			    chemin = "Images/laserEmpireDouble.png";
			    //explosionXwing[i-1] = new ImageIcon(rotatingImage);
			fileLaser = new File(chemin);


			// Initialisation de l'image rotative
			try {

				imageLaser= ImageIO.read(fileLaser);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			rotatingImage = new RotatingImage(imageLaser);
					//this.setIcon(rotatingImage);
					//setImage(rotatingImage);
			rotatingImage.setAngleD(angle);
			this.setIcon(imageOriginale);
			
    }
    
	@Override
	public void run() {
		// TODO Auto-generated method stub

		System.out.println("On déplace le laser");
		
		int i=0;
		while (i < Constantes.NBR_TIKS_DEP_LASER)
		{
		    try{
				//this.setLocation(this.getX()+x, posYDebut+(int)(coefD*i*x));
				//this.setLocation(this.getX()+x, posYDebut+(int)(coefD*i*x));
		    	
		    	this.setLocation(this.getX()+x, this.getY()+y);
		    	
				repaint();
				Thread.sleep(Constantes.VITESSE_DEP_LASER);
		    }
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		    i++;
		 }

	    this.monPanneau.threadDeplacementLaserTermine(numero);
	}

	public void setSens(boolean sens)
	{
		this.sens = sens;
	}
	
	public boolean getSens()
	{
		return this.sens;
	}
	
	public void calculerEcart(int x2, int y2)
	{
		x = (x2-getX())/Constantes.NBR_TIKS_DEP_LASER;
		y = (y2-getY())/Constantes.NBR_TIKS_DEP_LASER;
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
		
		//actualiserAngle();
		rotatingImage = new RotatingImage(imageLaser);
		//this.setIcon(rotatingImage);
		//setImage(rotatingImage);
		rotatingImage.setAngleD(angle);
		
		//////((ImageIcon)this.getIcon()).setImage(rotatingImage);
		imageOriginale.setImage(rotatingImage);
		//this.setIcon(xWing);
		repaint();
		
		//this.setIcon(xWing);
		
	}

}
