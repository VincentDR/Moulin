package moulin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;



public class Panneau extends JPanel implements MouseListener, MouseMotionListener, Observer{
	
	
	//////////////////////
	//// DEBUT TEST //////
	//////////////////////
	// NORMALEMENT : ALLER CHERCHER LES VRAIES VALEURS
	
	private int [] cmptVaiss = new int[2];
	//cmptVaiss[0] = 0;
	private int phase = 1;
	private boolean phaseDeTir = false;
	private int tourDeJeu = 0;
	
	private Thread thread;
	//////////////////////
	///// FIN TEST ///////
	//////////////////////
	
	/// AUTRE TEST
	//

	private Vaisseau [] plateau;
	
	//
	//// AUTRE TEST FIN
	  /**
	 * 
	 */
	// tst
	public int x=0;
	public int y=0;
	
	//private JPanel panelPionsTop, panelPionsBot, panelJeu;
	private Programme monProgramme;

	private Timer declencheur;
	private Image timer;
	
	// INITIALISATION DES FICHIER IMAGE
	private File fileFond = new File("Images/Fond/fond.png");
	private File fileVaisseau = new File("Images/Animations/bleu.png");
	
	// INITIALISATION DES IMAGES
	 
	private BufferedImage imageFond;
	private ImageIcon [] animation;
	private ImageIcon gif;
	private ImageIcon [] explosionXwing;
	// TEST
	private ImageIcon cases;
	// FIN TEST
	private int cmpt_anim;
	
	private JLabel labelAnimation, labelAnimation2;
	private JLabel labelGif;
	
	private JButton [] casesVide;
	private Vaisseau [][] vaisseau;
	private BufferedImage imageVaisseau;
	private RotatingImage rotatingImage;
	
	private JPanel panelJeu;
	private Controleur controleur;
	public Panneau(Controleur controleur)
	{		
		
		/////////
		// RESERVE AU TEST -> NORMALEMENT ALLER CHERCHER LES VALEURS
		///////
		
		cmptVaiss[0] = 0;
		cmptVaiss[1]= 0;
		
		// AUTRE

		plateau = new Vaisseau[24];
		// On initialise le plateau
		for(int c=0;c<Constantes.NB_CASES;c++)
		{
			plateau[c]=null;
		}
		////////
		//// FIN TEST
		//////////:
		this.controleur = controleur;
		//this.setSize());
		// Initialisation du panneau de jeu
		this.setLayout(new BorderLayout());
		panelJeu = new JPanel();
		panelJeu.setOpaque(false);
		// On initialise sa taille
		//panelJeu.setSize(new Dimension(1000,750));
		//panelJeu.setPreferredSize(new Dimension(1000,750));
		panelJeu.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        Integer.MAX_VALUE));
		// On ajoute le panneau de jeu au panneau principal
		this.add(panelJeu, "Center");
		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// Initialisation des panneaux
		/*panelJeu = new JPanel();
		panelPionsTop = new JPanel();
		panelPionsBot = new JPanel();*/
		
		// Redimensionnement des panneaux
	//	panelPionsTop.setBackground(Color.red);
		//panelPionsBot.setBackground(Color.blue);
	//	panelJeu.setBackground(Color.white);
		//this.setBackground(Color.blue);
		//this.setSize(new Dimension(1000,1000));
		//this.setPreferredSize(new Dimension(1000,1000));
		//this.setPreferredSize(new Dimension(1000,1000));
		//this.setMaximumSize(new Dimension(this.get));
		/*panelPionsTop.setPreferredSize(new Dimension(1000, 100));
		panelPionsTop.setMaximumSize(new Dimension(this.getWidth(), 100));
		//panelJeu.setPreferredSize(new Dimension(800, 500));
		panelPionsBot.setPreferredSize(new Dimension(this.getWidth(), 100));
		panelPionsBot.setMaximumSize(new Dimension(this.getWidth(), 100));*/
		
		// Ajout des panneaux au panneau principal
		/*this.add(panelPionsTop, "NORD");
		this.add(panelJeu, "CENTER");
		this.add(panelPionsBot, "SUD");
		
		panelPionsTop.setOpaque(false);
		panelJeu.setOpaque(false);
		panelPionsBot.setOpaque(false);*/
		
	/*
	double x1,x2,y1,y2;
	x1=a;
	y1=b;
	x2=getX();
	y2=getY();
	test = Math.toDegrees(Math.atan((x1-x2)/(y2-y1)));
	//test = Math.toDegrees(Math.atan((x1-x2)/(y2-y1)));
	System.out.println(test);*/
	    
			  
			
		try {

			imageVaisseau = ImageIO.read(fileVaisseau);
			imageFond = ImageIO.read(fileFond);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rotatingImage = new RotatingImage(imageVaisseau);
		
		rotatingImage.setAngleD(0);
		
		cmpt_anim = 0;
		animation = new ImageIcon[Constantes.NB_ANIMATIONS_W];
		animation[0] = new ImageIcon("Images/Animations/Explosions/Xplo8.png" );
		animation[1] = new ImageIcon("Images/Animations/rouge.png" );
		animation[2] = new ImageIcon("Images/Animations/vert.png" );
		animation[3] = new ImageIcon("Images/Animations/jaune.png" );
		animation[4] = new ImageIcon("Images/Animations/rose.png" );
		animation[5] = new ImageIcon("Images/Animations/violet.png" );
		animation[6] = new ImageIcon("Images/Animations/blanc.png" );
		animation[7] = new ImageIcon("Images/Animations/noir.png" );
		

		gif = new ImageIcon("Images/Animations/tonpere.gif" );

		labelAnimation2 = new JLabel(animation[1]);
		labelAnimation = new JLabel(animation[0]);
		labelGif = new JLabel(gif);
		animation[1].setImage(rotatingImage);
		//animation[1].set
		//JLabel lab = new JLabel(rotatingImage);
		
        // BONNE VERSION
        explosionXwing = new ImageIcon[14];
        for(int i=1;i<15;i++)
        {
        	String num = Integer.toString(i);
        	String chemin = "Images/Animations/Explosions/Xplo"+num+".png";
        	explosionXwing[i-1] = new ImageIcon(chemin);
        }

        cases = new ImageIcon("Images/caseVide.png"); // caseVide

        // On initialise le tableau de vaisseau, 9 vaisseaux par joueur (2)
        vaisseau = new Vaisseau[2][Constantes.NB_VAISSEAUX/2];
        // Pour les 2 joueurs
        for(int j=0;j<2;j++)
        {
	        for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
	        {
	        	//vaisseau[i] = new JButton(animation[1]);
	        	vaisseau[j][i] = new Vaisseau(j);
	
	        	vaisseau[j][i].setFocusPainted( false ); // enleve la bordure de l'image
	    		vaisseau[j][i].setBorderPainted(false); // enleve la bordure du bouton
	    		//vaisseau[i].setOpaque(false); // enleve la bordure du bouton
	    		vaisseau[j][i].setContentAreaFilled(false);
	        	vaisseau[j][i].setSize(animation[0].getIconWidth(), animation[0].getIconWidth()) ;
	    		vaisseau[j][i].setPreferredSize(vaisseau[j][i].getSize());
	        	
	        	// On ajoute le listener du boutton
	        	vaisseau[j][i].addMouseListener(this);
	        	//this.add(vaisseau[i]);
	        	
	        	// On ajoute le listener
	    		vaisseau[j][i].addMouseMotionListener(this);
	        }
        }

		// Pour les cases vide initiales
        casesVide = new JButton[Constantes.NB_CASES];
        for(int i=0;i<Constantes.NB_CASES;i++)
        {
    		casesVide[i] = new JButton(cases);
    		//casesVide[i] = new JButton();

    		casesVide[i].setFocusPainted( false ); // enleve la bordure de l'image
        	casesVide[i].setBorderPainted(false); // enleve la bordure du bouton
    		//vaisseau[i].setOpaque(false); // enleve la bordure du bouton
    		casesVide[i].setContentAreaFilled(false);
    		casesVide[i].setSize(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE) ;
    		casesVide[i].setPreferredSize(casesVide[i].getSize()); // A Modifier peut etre
        	
        	// On ajoute le listener du boutton
    		casesVide[i].addMouseListener(this);
        	
        	// On ajoute le listener
        	casesVide[i].addMouseMotionListener(this);
        }
        
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		// Les panneaux c'est la merde quand mÃªme...
		/*panelJeu.addMouseListener(this);
		panelPionsTop.addMouseListener(this);
		panelPionsBot.addMouseListener(this);
		panelJeu.addMouseMotionListener(this);
		panelPionsTop.addMouseMotionListener(this);
		panelPionsBot.addMouseMotionListener(this);*/
	}
	
	public void tourner()
	{
		try{
			  double oldAngle=0;
				if(rotatingImage!=null)
					oldAngle=rotatingImage.getAngleD();
				
				rotatingImage = new RotatingImage(imageVaisseau);
				System.out.println(oldAngle);
				
				rotatingImage.setAngleD(5 + oldAngle);
				animation[1].setImage(rotatingImage);
				//this.validate();
				//repaint();
			//	repaint(this.getVisibleRect());
				//repaint();
				panelJeu.repaint();
				System.out.println("On tourne");
			  Thread.sleep(40);//sleep for 20 ms
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
	}
	
	  public void paintComponent(Graphics g)
	  {
		    
		  BufferedImage image;
		try {

			image = ImageIO.read(fileFond);
				super.paintComponent(g); 
				g.drawImage(image, 0, 0, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  
		  
	  // On choisit une couleur de fond pour le rectangle
	    //g.setColor(Color.white);
	   //g.setColor(Color.lightGray);
	    //On le dessine de sorte qu'il occupe toute la surface
	    //g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    //On redÃ©finit une couleur pour le rond
	    //On le dessine aux coordonnÃ©es souhaitÃ©es
	   // g.fillOval(posX, posY, 50, 50);
	    // Desine l'image
	    	
	  }
	  //Efface le contenu
	/*  public void erase(){
	    this.erasing = true;
	    this.points = new ArrayList<Point>();
	    repaint();
	  }*/

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub
	if (event.getSource() == this && SwingUtilities.isRightMouseButton(event) ) 
	{
            /** Bouton DROIT */
		 initialisation();
    } 
	
	//else if(SwingUtilities.isRightMouseButton(event)) {
            /** Bouton DROIT */
    	
	boolean boutonSelectionne = false;
	int numBoutonSelectionne = 0;
	// On parcourt toutes les cases
	for(int i=0;i<Constantes.NB_CASES;i++)
	{
		
		// Si on a cliquÃ© sur une case vide (postion i)
		if(event.getSource() == casesVide[i])
		{
			  System.out.println("clique sur caseVide num " + i);
			  
			  
			// Test si on est dans la premiere phase ou seconde
			if(phase == 1) // Premiere phase de jeu
			{
				// Test a enlever
				if(cmptVaiss[tourDeJeu] < Constantes.NB_VAISSEAUX)
				{
					
						/*ajouterVaisseau(i);
						// On simule un mouvement de la souris (pour un eventuel tir)
						// Test si un moulin est crÃ©e
						if(false)
						{
							phaseDeTir = true;
							mouseMoved(event); // Simulation de mouvement de la souris
						}
						else
						{
							// On change le tour de jeu
							tourDeJeu = tourDeJeu==0 ? 1 : 0;
						}*/
					System.out.println("On ajoute une piece");
					controleur.AjouterPiece(i);
				}
				
				// On change de phase si tout le monde a jouÃ©
				if(cmptVaiss[0] + cmptVaiss[1] == Constantes.NB_VAISSEAUX)
					phase = 2;
				
			} // Fin de la condition phase 1
			else if(phase == 2) // Test si on est dans la 2 eme phase
			{
				// On deplace le pion selectionnÃ© dans cette case
				for(int indice=0;indice<Constantes.NB_VAISSEAUX/2;indice++)
				{
					if(vaisseau[tourDeJeu][indice].isSelectionne())
					{
						// On cherche le numero de la case initiale
						for(int debut=0;debut<Constantes.NB_CASES;debut++)
						{
							if(plateau[debut]==vaisseau[tourDeJeu][indice])
							{
								controleur.DeplacerPiece(debut, i, tourDeJeu+1);
								
								//vaisseau[tourDeJeu][indice].setSelectionne(false);
								debut=Constantes.NB_CASES;
							}
						}
					
						
						///
						// FIN TEST METHODE DEPLACER
						
						// i = indice de la future case
						
						// On deplace le vaisseau
						/*int xTemp = casesVide[i].getX();
						int yTemp = casesVide[i].getY();
						// On supprime la case vide et remet au dernier plan
						this.remove(casesVide[i]);
						this.add(casesVide[i]);
						casesVide[i].setLocation(xTemp, yTemp);
						// On met Ã  jour le plateau
						for(int c=0;c<Constantes.NB_CASES;c++)
						{
							if(plateau[c]==vaisseau[tourDeJeu][indice])
								plateau[c] = null;
						}
						vaisseau[tourDeJeu][indice].setLocation(xTemp,yTemp);
						plateau[i] = vaisseau[tourDeJeu][indice];*/
						
						// On remet droit le vaisseau
						//vaisseau[tourDeJeu][indice].setAngle(180);
						
						// test
						//casesVide[i].
						
						
					}
				}
				
			} // Fin condition phase 2
		} // Fin de la selection de la cases cliquÃ©e
		
		///// TEST ELODIE
		//////
		
		// PROVOQUE QUELQUES ERREURS D'ACCES AU TABLEAU
		/*else{ //on clique su un vaisseau car case non vide
			if((event.getSource()==plateau[i]) && plateau[i]!=null){
				System.out.println("selection de cette case non vide "+i);;

				if(phaseDeTir)
				{
					controleur.RetirerPiece(i, 2);
				} 
				else{ //phase == 2
					
					if(phaseDeTir){ // Deplacement moulin
						System.out.println("destruc case  "+i);
						controleur.RetirerPiece(i, 2);
					}
					else if(phase==2)
					{ // Case Ã  deplacer
						// On test si le bouton a Ã©tÃ© sÃ©lectionnÃ©
						if(vaisseau[tourDeJeu][i].isSelectionne())
						{
							vaisseau[tourDeJeu][i].setSelectionne(false);
						}
						else
						{
							// On deselectionne les autres vaisseaux
							for(int j=0;j<cmptVaiss[tourDeJeu];j++)
							{
								if(vaisseau[tourDeJeu][j].isSelectionne())
									vaisseau[tourDeJeu][j].setSelectionne(false);
							}
							vaisseau[tourDeJeu][i].setSelectionne(true);//vaisseau[i].setDeplacement(20);
						}
					}
				}
			}	

	} //end elsse case non vide*/
		
		
		/// FIN TEST ELODIE
		/////////////:
	} // Fin de parcours des cases
		
	

		// Permet de savoir le numero du tour de l'autre joueur
		int tourAutreJoueur = tourDeJeu==0 ? 1 : 0;
		
		for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
		{
			
			
			// ==> Si on a fait un moulin et qu'on doit detruire un vaisseau ennemi
			//if(event.getSource() == vaisseau[tourDeJeu][i] && SwingUtilities.isLeftMouseButton(event))
			if(phaseDeTir && event.getSource() == vaisseau[tourAutreJoueur][i])
			{
				// Explosion
				for(int c=0;c<Constantes.NB_CASES;c++)
				{
					if(plateau[c]==vaisseau[tourAutreJoueur][i])
						controleur.RetirerPiece(c, tourAutreJoueur+1);
				}
			} 
			//else if(event.getSource() == vaisseau[tourDeJeu][i])
			//&& SwingUtilities.isRightMouseButton(event)
			// Si on est dans la seconde phase
			else if(phase == 2 && event.getSource() == vaisseau[tourDeJeu][i]  && SwingUtilities.isLeftMouseButton(event))
			{ // Si on clique sur un de nos vaisseau, on le selectionne pour le deplacer
				
				// On test si le bouton a Ã©tÃ© sÃ©lectionnÃ©
				if(vaisseau[tourDeJeu][i].isSelectionne())
				{
					vaisseau[tourDeJeu][i].setSelectionne(false);
					// le remetre droit
				}
				else
				{
					// On deselectionne les autres vaisseaux
					for(int j=0;j<cmptVaiss[tourDeJeu];j++)
					{
						if(vaisseau[tourDeJeu][j].isSelectionne())
							vaisseau[tourDeJeu][j].setSelectionne(false);
					}
					// A MODIFIER
					vaisseau[tourDeJeu][i].setSelectionne(true);//vaisseau[i].setDeplacement(20);
					//mouseMoved(event);
					// A UTILISER QUAND ON VEUT SE DEPLACER (ou exploser)
					// construction d'un Thread en passant cette instance de Runnable en paramÃ¨tre
					//Thread thread =  new Thread(vaisseau[tourDeJeu][i]) ;
					
			    	 // lancement de ce thread par appel Ã  sa mÃ©thode start()
			    	
					//thread.start() ;
			    	 // cette mÃ©thode rend immÃ©diatement la main
				}
				
				
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		if(phaseDeTir)
		{
			System.out.println("phase de tir activÃ© !");
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				//System.out.println(tourDeJeu);
				if(vaisseau[tourDeJeu][i].isSelectionne())
				{
					//vaisseau[i].setLocation(vaisseau[i].getX()+2, vaisseau[i].getY()+1);
					if(event.getSource() == this){
						vaisseau[tourDeJeu][i].setAngle(event.getX(), event.getY());
					}
					else
					{
						// On recuperer la position du composant par rapport a la fenetre
						// On lui ajoute la position de la souris par rapport au composant
						int x = event.getComponent().getX() + event.getX();
						int y = event.getComponent().getY() + event.getY();
						vaisseau[tourDeJeu][i].setAngle(x,y);
						
					}
					repaint();
				}
			}
		}
			
	}
	
	public void deplacerVaisseau(int positionDebut, int positionFin)
	{
		// On parcourt les vaisseaux des 2 joueurs
		for(int j=0;j<2;j++)
		{
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				// Si on trouve le vaisseau qui se trouve a cette position
				if(plateau[positionDebut] == vaisseau[j][i])
				{
					//plateau[positionDebut]
					// On deplace le vaisseau
					Point p = casesVide[positionFin].getLocation();
					// On supprime la case vide et la remet au dernier plan
					panelJeu.remove(casesVide[positionFin]);
					panelJeu.add(casesVide[positionFin]);
					casesVide[positionFin].setLocation(p);
					
					// On met Ã  jour le plateau
					plateau[positionDebut] = null;
					vaisseau[j][i].setLocation(p);
					plateau[positionFin] = vaisseau[j][i];
					vaisseau[j][i].setSelectionne(false);
					

					// On remet droit le vaisseau
					//vaisseau[j][i].setAngle(0);
				}
			}
		}
		
		// A tester // marche surement pas
		// On met Ã  jour le plateau
		/*plateau[positionDebut].setLocation(casesVide[positionFin].getLocation());
		plateau[positionFin] = plateau[positionDebut];
		plateau[positionDebut] = null;
		//plateau[positionFin].setSelectionne(false);
		
		// On supprime la case vide et remet au dernier plan
		Point p = casesVide[positionFin].getLocation();
		panelJeu.remove(casesVide[positionFin]);
		panelJeu.add(casesVide[positionFin]);
		casesVide[positionFin].setLocation(p);*/
		
	}
	
	public void detruireVaisseau(int position)
	{
		System.out.println("position = " + position);
		// On parcourt les vaisseaux des 2 joueurs
		for(int j=0;j<2;j++)
		{
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				// Si on trouve le vaisseau qui se trouve a cette position
				if(plateau[position] == vaisseau[j][i])
				{
					vaisseau[j][i].setEtat(Constantes.ET_EXPL);
					//detruireVaisseau(position);
					
					// construction d'un Thread
					thread =  new Thread(vaisseau[j][i]) ;
			    	 // lancement de ce thread par appel Ã  sa mÃ©thode start()
					thread.start() ;
			    	 // cette mÃ©thode rend immÃ©diatement la main
					/*try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					// On met Ã  jour le plateau
					plateau[position] = null;
					
					//vaisseau[j][i].setAngle(0);
					
					/*try {
						// On attend la fin de l'explosion
						if(vaisseau[j][i].getEquipe()==0)
						{
							for(int t=0;t<50;t++)
							Thread.sleep(20);
						}
						else
						{
							for(int t=0;t<50;t++)
							Thread.sleep(20);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					//else
						
				}

				// On remet droit les vaisseaux
				if(vaisseau[tourDeJeu][i].isSelectionne())
				{

					System.out.println("on replace le vaisseau");
					vaisseau[tourDeJeu][i].setAngle(0);
					vaisseau[tourDeJeu][i].setSelectionne(false);
				}
			}
		}
		phaseDeTir = false;
		// On change le tour de jeu
		//tourDeJeu = tourDeJeu==0 ? 1 : 0;
	}
	
	public void ajouterVaisseau(int position)
	{
		panelJeu.add(vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]]);
		Point p = casesVide[position].getLocation();
		this.remove(casesVide[position]);
		panelJeu.add(casesVide[position]);
		casesVide[position].setLocation(p);
		
		// On positionne le vaisseau Ã  l'endroit de la case
		vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]].setLocation(p);//vaisseau[i].setDeplacement(20);
		// On met Ã  jour le plateau
		plateau[position] = vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]];
		// On ajoute 1 au compteur de vaisseau en fonction du joueur
		cmptVaiss[tourDeJeu]++;

	}
	  
	
	public void initialisation()
	{
		// Initialisations des positions
        int ecart=80; //70 correct
        int positionPlateauX=250;//event.getX(); // 280 pour ecart = 70
        int positionPlateauY=120;//event.getY(); // 140 pour ecart = 70
        int x=0, y=0;
        for(int c=0;c<Constantes.NB_CASES;c++)
        	panelJeu.add(casesVide[c]);
        // Variable indiquant le vaisseau Ã  afficher
        int k=0;
        // 3 carrÃ©s imbriquÃ©s
        for(int j=0;j<3;j++)
        {

        	// Origines du carrÃ© courant
        	x = ecart*j+positionPlateauX;
        	y = ecart*j+positionPlateauY;
        	
        	//vaisseau[k++].setLocation(x,y);
        	
        	for(int i=0;i<2;i++)
        	{
        		casesVide[k++].setLocation((3-j)*ecart*i+x,y);
        	}
        	for(int i=0;i<2;i++)
        	{
        		casesVide[k++].setLocation((3-j)*2*ecart+x, (3-j)*ecart*i+y);
        	}
        	for(int i=2;i>0;i--)
        	{
        		casesVide[k++].setLocation((3-j)*ecart*i+x, (3-j)*2*ecart+y);
        	}
        	for(int i=2;i>0;i--)
        	{
        		casesVide[k++].setLocation(x, (3-j)*i*ecart+y);
        	}
        }
        controleur.ordi();
	}
	
	
/*/////////////////////////
 *  ajouter implements MouseMotionLisener
 
	public void mouseDragged(MouseEvent e) {

		// TODO Auto-generated method stub
		// Quand on bouge la souris avec le clic appuyÃ©
		posX = e.getX();
		posY = e.getY();
	
		repaint();
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
*
*/

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		System.out.println("Ceci est un test rÃ©ussi !!!");
		int tab [] = (int []) arg;
		
		
		switch(tab[0])
		{
		 	// Placement
			case 1:
				//tourDeJeu=tab[1]-1;
				ajouterVaisseau(tab[2]);

				// On change le tour de jeu
				tourDeJeu = tourDeJeu==0 ? 1 : 0;
				System.out.println("tourDejeu = " + tourDeJeu);
				if(tourDeJeu==0)
				{
					controleur.ordi();
				}
				break;
			// Deplacement
			case 2:
				deplacerVaisseau(tab[3], tab[4]);
				// On change le tour de jeu
				tourDeJeu = tourDeJeu==0 ? 1 : 0;
				if(tourDeJeu==0)
				{
					controleur.ordi();
				};
				break;
			// Moulin
			case 3:
				System.out.println("Moulin : destruction du vaisseau");
				detruireVaisseau(tab[5]);
				phaseDeTir=false;
				// On change le tour de jeu
				tourDeJeu = tourDeJeu==0 ? 1 : 0;
				if(tourDeJeu==0)
				{
					controleur.ordi();
				}
				break;
			// Placement+Moulin
			case 4:
				ajouterVaisseau(tab[2]);
				// test
				plateau[tab[2]].setSelectionne(true);
				System.out.println("placement+moulin");
				phaseDeTir=true;
				if(tourDeJeu==0)
				{
					System.out.println("Tab5  "+tab[5]);
					detruireVaisseau(tab[5]);
					phaseDeTir=false;
					// On change le tour de jeu
					tourDeJeu = tourDeJeu==0 ? 1 : 0;
				}
				// On attend un prochain clic si joueur
				break;
			case 5:
			// Deplacement+Moulin
				System.out.println("deplacement+moulin");
				deplacerVaisseau(tab[3],tab[4]);
				plateau[tab[4]].setSelectionne(true);
				phaseDeTir=true;
				if(tourDeJeu==0)
				{
					detruireVaisseau(tab[5]);
					phaseDeTir=false;
					// On change le tour de jeu
					tourDeJeu = tourDeJeu==0 ? 1 : 0;
				}
				break;
			default:
				
				break;
		}
		
		/* 
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
		 *  					x ->	La case x ou la piece sera detruite*/
	}
	  
}
