package moulin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;


public class Panneau extends JPanel implements MouseListener, MouseMotionListener, Observer{
	
	
	private int [] cmptVaiss = new int[2];
	private int numCasePostDepl;
	private int positionDuVaissVise, positionCaseVisee;
	//cmptVaiss[0] = 0;
	private int phase = 1;
	private int phase2 = Constantes.PHASE_DE_JEU;
	private int tourDeJeu = 0;
	
	private Thread thread;
	//////////////////////
	///// FIN TEST ///////
	//////////////////////
	
	/// AUTRE TEST
	//

	private Vaisseau [] plateau;
	
	private CardLayout cl;
	//Liste des noms de nos conteneurs pour la pile de cartes
	private String[] listContent = {"Menu", "NouvellePartie", "Options", "Regles", "Jeu", "APropos", "VictoireEmpire", "VictoireRebelle"};
	private int indice = 0;
	private JPanel panelMenu, panelNouvPartie, panelOptions, panelRegles, panelAPropos, panelVictoireEmpire, panelVictoireRebelle;
	private JPanel panelJeu,panelPlateauJeu, panelPionsTop, panelPionsBot;
	private JPanel panelNouvPartieP1, panelNouvPartieP2, panelNouvPartieP3;
	public JButton boutonMenu;
	//
	//// AUTRE TEST FIN
	  /**
	 * 
	 */
	////
	// Composants du menu
	////
	
	private ImageIcon [] imgNouvPartie;
	private ImageIcon [] imgReprPartie;
	private ImageIcon [] imgChargerPartie; 
	private ImageIcon [] imgSauvPartie;
	private ImageIcon [] imgOptions;
	private ImageIcon [] imgRegles;
	private ImageIcon [] imgQuitter;
	private ImageIcon [] imgAPropos;
	
	// Boutons du menu
	private JButton btnNouvPartie, btnReprPartie, 
					 btnChargerPartie, btnSauvPartie, btnOptions, btnRegles, btnQuitter, btnAPropos;
	
	// Boutons de la page Nouvelle Partie
	private JButton btnJvsJ, btnJvsO, btnOvsO; 
	// Attributs des pages concernant le reglage de la nouvelle partie
	private JButton btnOrdiFacile, btnOrdiMoyen, btnOrdiDifficile;
	private JButton btnRetourP0, btnRetourP1, btnRetourP2, btnRetourP3;
	private JButton btnSuivantP1 ,btnSuivantP2, btnSuivantP3;
	private JLabel labelJoueur, labelJoueur1, labelJoueur2, labelDifficulte;
	private JTextField pseudo, pseudo1, pseudo2;
	
	// la police star wars
	private Font policeStarWars;
	private GridBagConstraints gbc;
	////
	// Fin des composants menu
	////
	
	// Souris
	Toolkit tk = Toolkit.getDefaultToolkit();
	//Image img = toolKit.Image("Images/caseF.png");
	//Image imageViseur=tk.getImage(getClass().getResource("caseF.png"));
	Image imageViseur=tk.getImage("Images/viseur.png");
	Cursor curseurTir = tk.createCustomCursor(imageViseur, new Point(16, 16), "curseur");
	
	// test
	public int x=0;
	public int y=0;
	
	//private JPanel panelPionsTop, panelPionsBot, panelPlateauJeu;
	//private Fenetre maFenetre;

	private Timer declencheur;
	private Image timer;
	
	// INITIALISATION DES FICHIER IMAGE
	private File fileFond = new File("Images/Fond/fond.png");
	private File fileFondMenu= new File("Images/Fond/fondMenu.png");
	private File fileFondVictoireEmpire= new File("Images/Fond/fondVictoireEmpire.png");
	private File fileFondVictoireRebelle = new File("Images/Fond/fondVictoireRebelle.png");
	//private File fileFondVictoireRebelle = new File("Images/Fond/fondVictoireRebelle.png");
	private File fileFondAPropos= new File("Images/Fond/fondtest.png");
	// test
	private File fileFondTest = new File("Images/Fond/fondtest.png");
	private BufferedImage imageFondTest;
	private File fileVaisseau = new File("Images/Animations/bleu.png");
	
	// INITIALISATION DES IMAGES
	 
	private BufferedImage imageFond;
	private BufferedImage imageFondMenu, imageFondVictoireEmpire, imageFondVictoireRebelle;
	private BufferedImage imageFondAPropos;
	private ImageIcon gifAPropos;
	private ImageIcon [] explosionXwing;
	private ImageIcon [] imgCocardeEmpire;
	private ImageIcon [] imgCocardeRebelle;

	private JLabel cocardeEmpire;
	private JLabel cocardeRebelle;
	
	private ImageIcon cases;
	
	private int cmpt_anim;
	
	private JLabel labelAnimation, labelAnimation2;
	private JLabel labelGifAPropos;
	
	private JButton [] casesVide;
	private Vaisseau [][] vaisseau;
	private Laser [] laser;
	private Laser laserTest;
	private Vaisseau vaisseauTest;
	
	private Controleur controleur;
	public Panneau(Controleur controleur)
	{		
		// initialisation de la police star wars
		//policeStarWars = new Font("Times New Roman", Font.BOLD, 30);
		//policeStarWars = new Font();
		//policeStarWars = Font.createFont(Font.TRUETYPE_FONT, "Starjhol.ttf");
		try {
			policeStarWars = Font.createFont(Font.TRUETYPE_FONT, new File("Starjhol.ttf"));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//policeStarWars = policeStarWars.deriveFont((float)30);
		policeStarWars = policeStarWars.deriveFont((float)30.0);
		
		// couleur jaune : 255 , 241, 31
		// couleur orange : 255 , 177, 9
		
		
		//policeStarWars = new Font("Starjhol.ttf", Font.TRUETYPE_FONT, 30);
		////
		// Initialisation des composants du menu
		////
		
		imgNouvPartie = new ImageIcon[2];
		imgReprPartie = new ImageIcon[2];
		imgChargerPartie = new ImageIcon[2];
		imgSauvPartie = new ImageIcon[2];
		imgOptions = new ImageIcon[2];
		imgRegles = new ImageIcon[2];
		imgQuitter = new ImageIcon[2];
		imgAPropos = new ImageIcon[2];
		
		//for(int i=0;i<2;i++)
		//{
			String cheminMenu = "Images/Menu/";
		/*	String chemin = "Images/Menu/test"+(i+1)+".png";
			imgNouvPartie[i] = new ImageIcon(chemin);
			imgReprPartie[i] = new ImageIcon(chemin);
			imgChargerPartie[i] = new ImageIcon(chemin);
			imgSauvPartie[i] = new ImageIcon(chemin);
			imgOptions[i] = new ImageIcon(chemin);
			imgRegles[i] = new ImageIcon(chemin);
			imgQuitter[i] = new ImageIcon(chemin);
			imgAPropos[i] = new ImageIcon(chemin);*/
			
			for(int i=0;i<2;i++)
			{
				imgNouvPartie[i] = new ImageIcon(cheminMenu+"nouvellePartie"+(i+1)+".png");
				imgReprPartie[i] = new ImageIcon(cheminMenu+"reprendrePartie"+(i+1)+".png");
				imgChargerPartie[i] = new ImageIcon(cheminMenu+"chargerPartie"+(i+1)+".png");
				imgSauvPartie[i] = new ImageIcon(cheminMenu+"sauvegarderPartie"+(i+1)+".png");
				imgOptions[i] = new ImageIcon(cheminMenu+"options"+(i+1)+".png");
				imgRegles[i] = new ImageIcon(cheminMenu+"regles"+(i+1)+".png");
				imgQuitter[i] = new ImageIcon(cheminMenu+"quitter"+(i+1)+".png");
				imgAPropos[i] = new ImageIcon(cheminMenu+"aPropos"+(i+1)+".png");
			}
			
			
		//}
		
		
		btnNouvPartie = new JButton(imgNouvPartie[0]);
		btnReprPartie = new JButton(imgReprPartie[0]);
		btnChargerPartie = new JButton(imgChargerPartie[0]);
		btnSauvPartie = new JButton(imgSauvPartie[0]);
		btnOptions = new JButton(imgOptions[0]);
		
		btnRegles = new JButton(imgRegles[0]);
		btnQuitter = new JButton(imgQuitter[0]);
		btnAPropos = new JButton(imgAPropos[0]);
		

		/*btnNouvPartie = new JButton("Nouvelle Partie");
		btnReprPartie = new JButton("Reprendre Partie");
		btnChargerPartie = new JButton("Charger Partie");
		btnSauvPartie = new JButton("sdfzefzefzffezftghf retger  ere");
		btnOptions = new JButton("5");
		btnRegles = new JButton(imgRegles[0]);
		btnQuitter = new JButton(imgQuitter[0]);
		btnAPropos = new JButton(imgAPropos[0]);*/
		
		
		

		btnNouvPartie.setFocusPainted( false ); // enleve la bordure de l'image
		btnNouvPartie.setBorderPainted(false); // enleve la bordure du bouton
		//btnNouvPartie.setOpaque(false); // enleve la bordure du bouton
		btnNouvPartie.setContentAreaFilled(false); // Enleve le fond du bouton (-> transparent)
		
		btnReprPartie.setFocusPainted( false );
		btnReprPartie.setBorderPainted(false);
		btnReprPartie.setContentAreaFilled(false);
		
		btnChargerPartie.setFocusPainted( false );
		btnChargerPartie.setBorderPainted(false);
		btnChargerPartie.setContentAreaFilled(false);
		
		btnSauvPartie.setFocusPainted( false );
		btnSauvPartie.setBorderPainted(false);
		btnSauvPartie.setContentAreaFilled(false);
		
		btnOptions.setFocusPainted( false );
		btnOptions.setBorderPainted(false);
		btnOptions.setContentAreaFilled(false);
		
		btnRegles.setFocusPainted( false );
		btnRegles.setBorderPainted(false);
		btnRegles.setContentAreaFilled(false);
		
		btnQuitter.setFocusPainted( false );
		btnQuitter.setBorderPainted(false);
		btnQuitter.setContentAreaFilled(false);
		
		btnAPropos.setFocusPainted( false );
		btnAPropos.setBorderPainted(false);
		btnAPropos.setContentAreaFilled(false);
		
		
		// test 
		// fi ntest
		//panelMenu = new JPanel(new BorderLayout());
		 //panelMenuBoutons = new JPanel();
		panelMenu = new JPanel(new GridBagLayout());
		//panelMenu.setBackground(Color.red); 
		
		
		gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        //gbc.insets = new Insets(10, 10, 10, 10);
        
        //gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.ipady = 0 ;
        gbc.weightx = 0.0 ;
        gbc.weighty = 0.0 ;
        gbc.gridwidth = 1 ;
        
        // insets : marges : haut , gauche, bas, droite
        gbc.insets = new Insets(0, 420, 5, 10);
        panelMenu.add(btnReprPartie, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 420, 5, 10);
		//gbc.gridheight = 2;
        panelMenu.add(btnNouvPartie, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
		gbc.gridheight = 1;
        panelMenu.add(btnChargerPartie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelMenu.add(btnSauvPartie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelMenu.add(btnOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panelMenu.add(btnRegles, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panelMenu.add(btnQuitter, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 420, 30, 10);
        panelMenu.add(btnAPropos, gbc);
		 
		// On ajoute tous les boutons au panel menu
		//panelMenu.add
		
        // On ajoute les boutons au listener
        btnNouvPartie.addMouseListener(this);
        btnReprPartie.addMouseListener(this);
        btnChargerPartie.addMouseListener(this);
        btnSauvPartie.addMouseListener(this);
        btnOptions.addMouseListener(this);
        btnRegles.addMouseListener(this);
        btnQuitter.addMouseListener(this);
        btnAPropos.addMouseListener(this);
		
        // On initialise les attributs concernant la page Nouvelle Partie
        
        panelNouvPartie = new JPanel(new GridBagLayout());

		btnJvsJ = new JButton(new ImageIcon("Images/Menu/nouvellePartie1.png"));
		btnJvsO = new JButton(new ImageIcon("Images/Menu/nouvellePartie1.png"));
		btnOvsO = new JButton(new ImageIcon("Images/Menu/nouvellePartie1.png"));
		btnRetourP0 = new JButton();
		btnRetourP0.setPreferredSize(new Dimension(200,50));


		btnJvsJ.setFont(policeStarWars);
		btnJvsJ.setText("joueur vs joueur");
		btnJvsJ.setPreferredSize(new Dimension(380,120));
		btnJvsJ.setVerticalTextPosition(SwingConstants.CENTER);
		btnJvsJ.setHorizontalTextPosition(SwingConstants.CENTER); 
		
		btnJvsO.setFont(policeStarWars);
		btnJvsO.setText("joueur vs ordi");
		//btnJvsJ.setForeground(new Color(255,241,31));
		// couleur jaune : 255 , 241, 31
		btnJvsO.setVerticalTextPosition(SwingConstants.CENTER);
		btnJvsO.setHorizontalTextPosition(SwingConstants.CENTER); 

		btnOvsO.setFont(policeStarWars);
		btnOvsO.setText("ordi vs ordi");
		btnOvsO.setPreferredSize(new Dimension(380,120));
		btnOvsO.setVerticalTextPosition(SwingConstants.CENTER);
		btnOvsO.setHorizontalTextPosition(SwingConstants.CENTER); 

		btnRetourP0.setFont(policeStarWars);
		btnRetourP0.setText("retour");

		btnJvsJ.setPreferredSize(new Dimension(380,120));
		btnJvsO.setPreferredSize(new Dimension(380,120));
		btnOvsO.setPreferredSize(new Dimension(380,120));
		
	/*	btnJvsJ.setFocusPainted( false );
		btnJvsJ.setBorderPainted(false);
		btnJvsJ.setContentAreaFilled(false);*/
		
		gbc = null;
		gbc = new GridBagConstraints();
	
        gbc.anchor = GridBagConstraints.CENTER;
        
    	
        gbc.ipady = 0 ;
        gbc.weightx = 0.0 ;
        gbc.weighty = 0.0 ;
        gbc.gridwidth = 1 ;
        
        // insets : marges : haut , gauche, bas, droite
        gbc.insets = new Insets(5, 420, 5, 10);
        panelNouvPartie.add(btnJvsJ, gbc);
        
        gbc.gridy = 1;
        panelNouvPartie.add(btnJvsO, gbc);
        gbc.gridy = 2;
        panelNouvPartie.add(btnOvsO, gbc);
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.LAST_LINE_START;
        //gbc.insets = new Insets(100, 420, 5, 10);
        panelNouvPartie.add(btnRetourP0, gbc);

		
        // On ajoute les boutons au listener
        btnJvsJ.addMouseListener(this);
        btnJvsO.addMouseListener(this);
        btnOvsO.addMouseListener(this);
        btnRetourP0.addMouseListener(this);
        
        // On s'occupe de la Page 1 : Joueur vs Joueur
        panelNouvPartieP1 = new JPanel(new GridBagLayout());
		
		gbc = null;
		gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
        gbc.insets = new Insets(5, 420, 5, 10);
        // Fin Page 1
        
        // On s'occupe de la Page 2 : Joueur vs Ordi
        panelNouvPartieP2 = new JPanel(new GridBagLayout());
		
        
		policeStarWars = policeStarWars.deriveFont((float)25.0);
        labelJoueur = new JLabel("joueur : ");
        labelJoueur.setFont(policeStarWars);
        //labelJoueur.getFont() = labelJoueur.getFont().deriveFont((float)5.0);

		policeStarWars = policeStarWars.deriveFont((float)20.0);
        
        pseudo = new JTextField("  pseudo  ", 10);
        pseudo.setFont(policeStarWars);
        // Permet de limiter le nombre de caractere a 10 apres saisi
        pseudo.addKeyListener(new KeyListener() {
      
     			public void keyPressed(KeyEvent e) {}
     			public void keyReleased(KeyEvent e) {}
     			public void keyTyped(KeyEvent e) {
     				if(pseudo.getText().length() >= 10) {
     					System.out.println("limite atteinte");
     					try {
     						pseudo.setText(pseudo.getText(0, 9));
     					} catch (BadLocationException ble) { ble.printStackTrace(); }
     				}
     			}
     		});

        //pseudo.getFont().deriveFont((float)20.0);
		policeStarWars = policeStarWars.deriveFont((float)30.0);

        labelDifficulte = new JLabel("difficulte :");
        labelDifficulte.setFont(policeStarWars);
        labelDifficulte.setPreferredSize(new Dimension(380,40));
        
        btnOrdiFacile = new JButton();
        btnOrdiFacile.setFont(policeStarWars);
        btnOrdiFacile.setText("facile");
        btnOrdiFacile.setPreferredSize(new Dimension(380,40));
        
        btnOrdiMoyen = new JButton();
        btnOrdiMoyen.setFont(policeStarWars);
        btnOrdiMoyen.setText("moyen");
        btnOrdiMoyen.setPreferredSize(new Dimension(380,40));
        
        btnOrdiDifficile = new JButton();
        btnOrdiDifficile.setFont(policeStarWars);
        btnOrdiDifficile.setText("difficile");
        btnOrdiDifficile.setPreferredSize(new Dimension(380,40));
        
        btnRetourP2 = new JButton();
		btnRetourP2.setFont(policeStarWars);
		btnRetourP2.setText("retour");
		btnRetourP2.setPreferredSize(new Dimension(200,50));
        
        btnSuivantP2 = new JButton();
        btnSuivantP2.setFont(policeStarWars);
        btnSuivantP2.setText("suivant");
        btnSuivantP2.setPreferredSize(new Dimension(200,50));
        
		gbc = null;
		gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
		gbc.gridx = 0;
		gbc.gridy = 0;
        gbc.insets = new Insets(5, 420, 5, 5);
		panelNouvPartieP2.add(labelJoueur, gbc);
		gbc.gridx=1;
        gbc.insets = new Insets(5, 0, 5, 5);
		panelNouvPartieP2.add(pseudo, gbc);
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 420, 5, 10);
		panelNouvPartieP2.add(labelDifficulte, gbc);
		gbc.gridx=0;
		gbc.gridy=3;
		panelNouvPartieP2.add(btnOrdiFacile, gbc);
		gbc.gridx=0;
		gbc.gridy=4;
		panelNouvPartieP2.add(btnOrdiMoyen, gbc);
		gbc.gridx=0;
		gbc.gridy=5;
		panelNouvPartieP2.add(btnOrdiDifficile, gbc);
		

		gbc.gridx=0;
		gbc.gridy=8;
		gbc.gridwidth = 1;
        gbc.insets = new Insets(50, 420, 5, 10);
		gbc.anchor=GridBagConstraints.LAST_LINE_START;
		panelNouvPartieP2.add(btnRetourP2, gbc);
		gbc.gridx=1;
		gbc.gridy=8;
        gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor=GridBagConstraints.LAST_LINE_END;
		panelNouvPartieP2.add(btnSuivantP2, gbc);
		
        // On ajoute les boutons au listener
        pseudo.addMouseListener(this);
        btnOrdiFacile.addMouseListener(this);
        btnOrdiMoyen.addMouseListener(this);
        btnOrdiDifficile.addMouseListener(this);
        btnRetourP2.addMouseListener(this);
        btnSuivantP2.addMouseListener(this);
        //btnSuivantP2.addMouseListener(this);
        
        // Fin Page 2
		
        // On s'occupe de la Page 3 : Ordi vs Ordi
        panelNouvPartieP3 = new JPanel(new GridBagLayout());
		
		gbc = null;
		gbc = new GridBagConstraints();
        // Fin Page 3
        // Fin Nouvelle Partie
        
        //  On initialise les attributs concernant la page Options
        
        panelOptions = new JPanel();
        
        // Fin Options
        
        //  On initialise les attributs concernant la page Regle
        
        panelRegles = new JPanel();
        
        // Fin Regles
        
        // On initialise les attributs concernant la page A Propos
        
        panelAPropos = new JPanel();
        gifAPropos = new ImageIcon("Images/Fond/fondAPropos.gif");
        labelGifAPropos = new JLabel(gifAPropos);
        panelAPropos.add(labelGifAPropos);
        // Fin A Propos
		////
		// Fin des composants menu
		////
        

        // On initialise les écrans de victoire
        
        panelVictoireEmpire = new JPanel();
        panelVictoireRebelle = new JPanel();
        
        //labelVictoireEmpire = new JLabel(gifAPropos);
        // Fin A Propos
		
        // Initialisation des variables concernant le déroulement du jeu
		cmptVaiss[0] = 0;
		cmptVaiss[1]= 0;
		
		boutonMenu = new JButton("Menu");
		//boutonMenu.addMouseListener(this);
		plateau = new Vaisseau[24];
		// On initialise le plateau
		for(int c=0;c<Constantes.NB_CASES;c++)
		{
			plateau[c]=null;
		}
		
		// initialisation des cocardes
		imgCocardeEmpire = new ImageIcon[2];
		imgCocardeRebelle = new ImageIcon[2];
		for(int i=0;i<2;i++)
		{
			imgCocardeEmpire[i] = new ImageIcon("Images/"+"cocardeEmpire"+(i+1)+".png");
			imgCocardeRebelle[i] = new ImageIcon("Images/"+"cocardeRebelle"+(i+1)+".png");
		}
		
		cocardeEmpire = new JLabel(imgCocardeEmpire[0]);
		cocardeRebelle = new JLabel(imgCocardeRebelle[0]);
		
		
		// Choix des panneaux
		cl = new CardLayout();
	
		this.setLayout(cl);
		//this.setLayout(new BorderLayout());
	       
		// On initialise les panneaux se situant en haut et en bas du panneau de jeu
		panelJeu = new JPanel(new BorderLayout());
		
		panelPlateauJeu = new JPanel();
		panelPlateauJeu.setLayout(null);
		
		//panelPionsTop = new JPanel(new GridLayout(1, 10, 0,0));
		panelPionsTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		panelPionsBot = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPionsBot.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		/*
		laserTest = new Laser(this, tourDeJeu, 50, 50);
		panelPionsBot.add(laserTest);*/
		
		
		// Redimensionnement des panneaux
		// test
		//panelPionsTop.setBackground(Color.red);
		//panelPionsBot.setBackground(Color.blue);
		panelPlateauJeu.setOpaque(false);
		panelPionsTop.setOpaque(false);
		panelPionsBot.setOpaque(false);
	//	panelJeu.setBackground(Color.white);
		//this.setBackground(Color.blue);
		//this.setSize(new Dimension(1000,1000));
		
		//this.setPreferredSize(new Dimension(1000,1000));
		//this.setPreferredSize(new Dimension(1000,1000));
		//this.setMaximumSize(new Dimension(this.get));
		//panelPionsTop.setPreferredSize(new Dimension(Constantes.FENETRE_LARGEUR, Constantes.HAUTEUR_PANEL_TOP_BOT));
		//panelJeu.setPreferredSize(new Dimension(800, 500));
		//panelPionsBot.setPreferredSize(new Dimension(Constantes.FENETRE_LARGEUR, Constantes.HAUTEUR_PANEL_TOP_BOT));
		
		// Ajout des panneaux au panneau jeu
		panelJeu.add(panelPionsTop, "North");
		panelJeu.add(panelPlateauJeu, "Center");
		panelJeu.add(panelPionsBot, "South");
		//panelJeu.add(boutonMenu);
		
		
		
		
	    //On ajoute les cartes à la pile avec un nom pour les retrouver
	    this.add(panelMenu, "Menu");
	    this.add(panelJeu, "Jeu");
	    this.add(panelNouvPartie, "NouvellePartie");
	    this.add(panelNouvPartieP1, "JvsJ");
	    this.add(panelNouvPartieP2, "JvsO");
	    this.add(panelNouvPartieP3, "OvsO");
	    this.add(panelOptions, "Options");
	    this.add(panelRegles, "Regles");
	    this.add(panelAPropos, "APropos");
	    this.add(panelVictoireEmpire, "VictoireEmpire");
	    this.add(panelVictoireRebelle, "VictoireRebelle");
	    // On montre la premiere carte
	    cl.show(this, "Menu");
		////////
		//// FIN TEST
		//////////:
		this.controleur = controleur;
		//this.setSize());
		// Initialisation du panneau de jeu
		//this.setLayout(new BorderLayout());
		// A mieux positionner
	    //this.add(panelJeu, listContent[0]);
		//this.add(panelMenu);
	    //this.add(panelJeu);
	    //this.add(panelMenu, "Center");
	    
	    //panelMenu.setVisible(false);
	    //cl.show(this, listContent[0]);
		panelMenu.setOpaque(false);
		panelNouvPartie.setOpaque(false);
		panelNouvPartieP1.setOpaque(false);
		panelNouvPartieP2.setOpaque(false);
		panelNouvPartieP3.setOpaque(false);
		panelRegles.setOpaque(false);
		panelOptions.setOpaque(false);
		panelJeu.setOpaque(false);
		panelVictoireEmpire.setOpaque(false);
		panelVictoireRebelle.setOpaque(false);
		panelAPropos.setOpaque(false);
		// On initialise sa taille
		//panelJeu.setSize(new Dimension(1000,750));
		//panelJeu.setPreferredSize(new Dimension(1000,750));
		//panelJeu.setMaximumSize(new Dimension(Integer.MAX_VALUE,
        //Integer.MAX_VALUE));
		// On ajoute le panneau de jeu au panneau principal
		// 	a enlever
		/////////this.add(panelJeu, "Center");
		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// Initialisation des panneaux top et bot du panelJeu
		
		/*panelPionsTop.setOpaque(false);
		panelPlaeauJeu.setOpaque(false);
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

			imageFond = ImageIO.read(fileFond);
			imageFondMenu = ImageIO.read(fileFondMenu);
			imageFondVictoireEmpire = ImageIO.read(fileFondVictoireEmpire);
			imageFondVictoireRebelle = ImageIO.read(fileFondVictoireRebelle);
			imageFondAPropos = ImageIO.read(fileFondAPropos);
			imageFondTest = ImageIO.read(fileFondTest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        cases = new ImageIcon("Images/caseVide.png"); // caseVide / caseF

        // On initialise le tableau de vaisseau, 9 vaisseaux par joueur (2)
        vaisseau = new Vaisseau[2][Constantes.NB_VAISSEAUX/2];
        // Pour les 2 joueurs
        for(int j=0;j<2;j++)
        {
	        for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
	        {
	        	//vaisseau[i] = new JButton(animation[1]);
	        	
	        	int a= j==0?1:0;
	        	a= j==0?0:1;
	        	vaisseau[j][i] = new Vaisseau(this, a, i);
	
	        	vaisseau[j][i].setFocusPainted( false ); // enleve la bordure de l'image
	    		vaisseau[j][i].setBorderPainted(false); // enleve la bordure du bouton
	    		//vaisseau[i].setOpaque(false); // enleve la bordure du bouton
	    		vaisseau[j][i].setContentAreaFilled(false);
	        	//vaisseau[j][i].setSize(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE) ;
	    		vaisseau[j][i].setPreferredSize(new Dimension(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE));
	        	
	        	// On ajoute le listener du boutton
	        	vaisseau[j][i].addMouseListener(this);
	        	//this.add(vaisseau[i]);
	        	
	        	// On ajoute le listener
	    		vaisseau[j][i].addMouseMotionListener(this);
	    		
	    		// On les ajoute au panneau top / bot
	    		if(j==0){
	    			//panelPionsTop.add(vaisseau[j][i]);

	    	        panelPionsTop.add(vaisseau[j][i]);
	    	        //gbc.gridx++;
	    	        }
	    		else{
	    			panelPionsBot.add(vaisseau[j][i]);}
	        }
        }
        // On ajoute les cocardes
        panelPionsTop.add(cocardeRebelle);
        panelPionsBot.add(cocardeEmpire);

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
		
		// test
		// On ajoute le curseur

		//this.setCursor(null);
	}
	
	/*public void tourner()
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
				//panelJeu.repaint();
				System.out.println("On tourne");
			  Thread.sleep(40);//sleep for 20 ms
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
	}*/
	
	  public void paintComponent(Graphics g)
	  {

			//System.out.println("On repaint ! ");
		/*  BufferedImage image;
		try {

			if(panelJeu.isVisible())
			{
				System.out.println("le panel jeu est visible");
				image = ImageIO.read(fileFond);
			}
			else
				image = ImageIO.read(fileFondTest);*/
			super.paintComponent(g); 
			if(panelMenu.isVisible() || panelNouvPartie.isVisible() || panelRegles.isVisible()
					|| panelOptions.isVisible() || panelNouvPartieP1.isVisible() 
					|| panelNouvPartieP2.isVisible() || panelNouvPartieP3.isVisible())
			{
				g.drawImage(imageFondMenu, 0, 0, null);
			}
			else if(panelAPropos.isVisible())
			{
				//g.drawImage(imageFondAPropos, 0, 0, null);
			}
			else if(panelJeu.isVisible())
			{
				 //System.out.println("on affiche le jeu");
				g.drawImage(imageFond, 0, 0, null);
			}
			else if(panelVictoireEmpire.isVisible())
			{
				 //System.out.println("on affiche le jeu");
				g.drawImage(imageFondVictoireEmpire, 200, 140, null);
			}
			else if(panelVictoireRebelle.isVisible())
			{
				 //System.out.println("on affiche le jeu");
				g.drawImage(imageFondVictoireRebelle, 200, 140, null);
			}
			
		/*} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		  
		  
		  
		  
		  
	  // On choisit une couleur de fond pour le rectangle
	    //g.setColor(Color.white);
	   //g.setColor(Color.lightGray);
	    //On le dessine de sorte qu'il occupe toute la surface
	    //g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    //On redéfinit une couleur pour le rond
	    //On le dessine aux coordonnées souhaitées
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
		
	if(panelMenu.isVisible())
	{
		// Clic sur "Options"
		if(event.getSource() == btnReprPartie && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Jeu");
		}
		// Clic sur "Nouvelle Partie"
		else if(event.getSource() == btnNouvPartie && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "NouvellePartie");
		}
		// Clic sur "Options"
		else if(event.getSource() == btnOptions && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Options");
		}
		// Clic sur "Regles"
		else if(event.getSource() == btnRegles && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Regles");
		}
		// Clic sur "A Propos"
		else if(event.getSource() == btnAPropos && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "APropos");

		       // validate();
		        //repaint();
		        //panelMenu.revalidate();
				
				// s'en inspirer pour le panel jeu
				/*btnAPropos.setLocation(btnAPropos.getX(), btnAPropos.getY()-60);
				panelMenu.remove(btnRegles);
				gbc.gridx = 0;
		        gbc.gridy = 5;
		        gbc.insets = new Insets(5, 420, 5, 10);
		        panelMenu.add(btnAPropos, gbc);
		       // panelMenu.remove

			gbc.gridx = 0;
	        gbc.gridy = 6;
	        gbc.insets = new Insets(5, 420, 30, 10);
	        panelMenu.add(btnQuitter, gbc);
	        repaint();
	        validate();*/
			//btnAPropos.setIcon(null);
		        /*panelMenu.remove(btnQuitter);
				gbc.gridx = 0;
		        gbc.gridy = 8;
		        gbc.insets = new Insets(5, 420, 30, 10);
		        panelMenu.add(btnQuitter, gbc);*/
		}
		// Clic sur "Quitter"
		else if(event.getSource() == btnQuitter && SwingUtilities.isLeftMouseButton(event) )
		{
			Window window = SwingUtilities.windowForComponent(this);
			if (window instanceof JFrame) {
				JFrame frame = (JFrame) window;
		 
				frame.setVisible(false);
				frame.dispose();
			}
		}
	}
	else if(panelNouvPartie.isVisible())
	{
		// Clic sur "Joueur contre Joueur"
		if(event.getSource() == btnJvsJ && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "JvsJ");
		}
		// Clic sur "Joueur contre Ordi"
		if(event.getSource() == btnJvsO && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "JvsO");
		}
		// Clic sur "Ordi contre Ordi"
		if(event.getSource() == btnOvsO && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Jeu");
		}
		// Clic sur "Retour"
		if(event.getSource() == btnRetourP0 && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Menu");
		}
	}
	else if(panelNouvPartieP1.isVisible())
	{
		// Clic sur "Joueur contre Joueur"
		//if(event.getSource() == btnJvsJ && SwingUtilities.isLeftMouseButton(event) )
		//{
			 cl.show(this, "Jeu");
		//}
	}
	else if(panelNouvPartieP2.isVisible())
	{
		// Clic sur "Retour"
		if(event.getSource() == btnRetourP2 && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "NouvellePartie");
		}
		// Clic sur "Suivant"
		else if(event.getSource() == btnSuivantP2 && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Jeu");
		}
		else if(event.getSource() == pseudo && SwingUtilities.isLeftMouseButton(event) )
		{
			pseudo.setText("");
			
		}
		
	}
	else if(panelOptions.isVisible())
	{
		// Clic dans l'option
			 cl.show(this, "Menu");
	}
	else if(panelRegles.isVisible())
	{
		// Clic dans le menu
			 cl.show(this, "Menu");
	}
	else if(panelAPropos.isVisible())
	{
		// Clic dans l'a propos
			 cl.show(this, "Menu");
	}
	else if(panelVictoireEmpire.isVisible())
	{
		// Clic apres victoire empire
			 cl.show(this, "Menu");
	}
	else if(panelVictoireRebelle.isVisible())
	{
		// Clic apres victoire rebelle
			 cl.show(this, "Menu");
	}
	else if(panelJeu.isVisible())
	{
		if(event.getSource() == this && SwingUtilities.isMiddleMouseButton(event) )
		{
		                /** Bouton du milieu */
		        cl.show(this, "Menu");
		}
		    	
		if (event.getSource() == this && SwingUtilities.isRightMouseButton(event) ) 
		{
	            /** Bouton DROIT */

	        for(int c=0;c<Constantes.NB_CASES;c++)
	        	panelPlateauJeu.add(casesVide[c]);
			 initialisation();
			 // a l'ordi de commencer
		     controleur.ordi();
	    } 
		
		//else if(SwingUtilities.isRightMouseButton(event)) {
	            /** Bouton DROIT */
	    	
		boolean boutonSelectionne = false;
		int numBoutonSelectionne = 0;
		// On parcourt toutes les cases
		for(int i=0;i<Constantes.NB_CASES;i++)
		{
			
			// Si on a cliqué sur une case vide (postion i)
			if(event.getSource() == casesVide[i] && (phase2==Constantes.PHASE_DE_JEU))
			{
				  System.out.println("clique sur caseVide num " + i);
				  
				  
				// Test si on est dans la premiere phase ou seconde
				if(phase == 1) // Premiere phase de jeu
				{
					// Test a enlever
					if(cmptVaiss[tourDeJeu] < Constantes.NB_VAISSEAUX/2)
					{
						
							/*ajouterVaisseau(i);
							// On simule un mouvement de la souris (pour un eventuel tir)
							// Test si un moulin est crée
							if(false)
							{
								phaseDeTir = true;
								mouseMoved(event); // Simulation de mouvement de la souris
							}
							else
							{
								// On change le tour de jeu
								changerTourDeJeu();
							}*/
						System.out.println("On ajoute une piece");
						controleur.AjouterPiece(i);
					}
					
					// On change de phase si tout le monde a joué
					if(cmptVaiss[0] + cmptVaiss[1] == Constantes.NB_VAISSEAUX)
					{
						phase = 2;
						cmptVaiss[0]=Constantes.NB_VAISSEAUX/2;
						cmptVaiss[1]=Constantes.NB_VAISSEAUX/2;
					}
					
				} // Fin de la condition phase 1
				else if(phase == 2) // Test si on est dans la 2 eme phase
				{
					// On deplace le pion selectionné dans cette case
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
									
									//vaisseau[tourDeJeu][indice].setMoulin(false);
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
							// On met à jour le plateau
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
			} // Fin de la selection de la cases cliquée
			
			
			/////////////:
		} // Fin de parcours des cases
			
		

			// Permet de savoir le numero du tour de l'autre joueur
			int tourAutreJoueur = tourDeJeu==0 ? 1 : 0;
			
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				
				
				// ==> Si on a fait un moulin et qu'on doit detruire un vaisseau ennemi
				//if(event.getSource() == vaisseau[tourDeJeu][i] && SwingUtilities.isLeftMouseButton(event))
				if(phase2==Constantes.PHASE_DE_CHOIX_CIBLE && event.getSource() == vaisseau[tourAutreJoueur][i])
				{
					/*vaisseauTest = new Vaisseau(0, this);
					panelPlateauJeu.add(vaisseauTest);
					//panelPionsTop.add(vaisseauTest);
					vaisseauTest.setLocation(200, 200);
					//vaisseauTest.setPreferredSize(new Dimension(200, 200));
					vaisseauTest.setSize(new Dimension(200, 200));
					
					for(int t=0;t<Constantes.NB_VAISSEAUX/2;t++)
					{
						panelPlateauJeu.remove(vaisseau[0][t]);
						panelPlateauJeu.remove(vaisseau[1][t]);
					}*/
					//panelPlateauJeu.repaint();
					//panelPlateauJeu.revalidate();
					
					
					
					
					for(int c=0;c<Constantes.NB_CASES;c++)
					{
						if(plateau[c]==vaisseau[tourAutreJoueur][i])
						{
							// On stock la position du vaisseau visé
							positionDuVaissVise = c;
						}
					}
					
					phase2 = Constantes.PHASE_DE_TIR;
					this.setCursor(Cursor.getDefaultCursor());
					viserCible();
					tirerLasers();
					
					
				} 
				//else if(event.getSource() == vaisseau[tourDeJeu][i])
				//&& SwingUtilities.isRightMouseButton(event)
				// Si on est dans la seconde phase
				else if(phase == 2 && event.getSource() == vaisseau[tourDeJeu][i] 
						&& SwingUtilities.isLeftMouseButton(event) && phase2==Constantes.PHASE_DE_JEU)
				{ // Si on clique sur un de nos vaisseau, on le selectionne pour le deplacer
					
					// On test si le bouton a été sélectionné
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
						// construction d'un Thread en passant cette instance de Runnable en paramètre
						//Thread thread =  new Thread(vaisseau[tourDeJeu][i]) ;
						
				    	 // lancement de ce thread par appel à sa méthode start()
				    	
						//thread.start() ;
				    	 // cette méthode rend immédiatement la main
					}
					
					
				}
			}
	}
		
	
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
			//JButton temp = (JButton)event.getSource();
		if(event.getSource() == btnReprPartie)
		{
			btnReprPartie.setIcon(imgReprPartie[1]);
		}
		else if(event.getSource() == btnNouvPartie)
		{
			btnNouvPartie.setIcon(imgNouvPartie[1]);
		}
		else if(event.getSource() == btnChargerPartie)
		{
			btnChargerPartie.setIcon(imgChargerPartie[1]);
		}
		else if(event.getSource() == btnSauvPartie)
		{
			btnSauvPartie.setIcon(imgSauvPartie[1]);
		}
		else if(event.getSource() == btnOptions)
		{
			btnOptions.setIcon(imgOptions[1]);
		}
		else if(event.getSource() == btnRegles)
		{
			btnRegles.setIcon(imgRegles[1]);
		}
		else if(event.getSource() == btnQuitter)
		{
			btnQuitter.setIcon(imgQuitter[1]);
		}
		else if(event.getSource() == btnAPropos)
		{
			btnAPropos.setIcon(imgAPropos[1]);
		}
		//event.getSource()
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		//JButton temp = (JButton)event.getSource();
		if(event.getSource() == btnReprPartie)
		{
			btnReprPartie.setIcon(imgReprPartie[0]);
		}
		else if(event.getSource() == btnNouvPartie)
		{
			btnNouvPartie.setIcon(imgNouvPartie[0]);
		}
		else if(event.getSource() == btnChargerPartie)
		{
			btnChargerPartie.setIcon(imgChargerPartie[0]);
		}
		else if(event.getSource() == btnSauvPartie)
		{
			btnSauvPartie.setIcon(imgSauvPartie[0]);
		}
		else if(event.getSource() == btnOptions)
		{
			btnOptions.setIcon(imgOptions[0]);
		}
		else if(event.getSource() == btnRegles)
		{
			btnRegles.setIcon(imgRegles[0]);
		}
		else if(event.getSource() == btnQuitter)
		{
			btnQuitter.setIcon(imgQuitter[0]);
		}
		else if(event.getSource() == btnAPropos)
		{
			btnAPropos.setIcon(imgAPropos[0]);
		}
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub
		if(phase2==Constantes.PHASE_DE_CHOIX_CIBLE)
		{
			System.out.println("phase de tir activé !");
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				//System.out.println(tourDeJeu);
				if(vaisseau[tourDeJeu][i].getMoulin() == 2)
				{
					//vaisseau[i].setLocation(vaisseau[i].getX()+2, vaisseau[i].getY()+1);
					if(event.getSource() == this){
						vaisseau[tourDeJeu][i].setAngle(event.getX(), event.getY()-Constantes.HAUTEUR_PANEL_TOP_BOT);
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
					/*Point p = casesVide[positionFin].getLocation();
					// On supprime la case vide et la remet au dernier plan
					panelPlateauJeu.remove(casesVide[positionFin]);
					panelPlateauJeu.add(casesVide[positionFin]);
					casesVide[positionFin].setLocation(p);
					
					vaisseau[j][i].setLocation(p);
					plateau[positionFin] = vaisseau[j][i];
					vaisseau[j][i].setSelectionne(false);*/
					
					
					// On garde en memoire la case sur lequel le vaisseau va
					numCasePostDepl = positionFin;
					
					vaisseau[j][i].setEtat(Constantes.ET_DEPL);
					vaisseau[j][i].calculerRotation(casesVide[positionFin].getX(), casesVide[positionFin].getY());
					thread =  new Thread(vaisseau[j][i]) ;
			    	 // lancement de ce thread par appel à sa méthode start()
					thread.start() ;
					
					vaisseau[j][i].setSelectionne(false);
					// On met à jour le plateau
					plateau[positionDebut] = null;
					plateau[positionFin] = vaisseau[j][i];

					// On remet droit le vaisseau
					//vaisseau[j][i].setAngle(0);
				}
			}
		}
		
		// A tester // marche surement pas
		// On met à jour le plateau
		/*plateau[positionDebut].setLocation(casesVide[positionFin].getLocation());
		plateau[positionFin] = plateau[positionDebut];
		plateau[positionDebut] = null;
		//plateau[positionFin].setSelectionne(false);
		
		// On supprime la case vide et remet au dernier plan
		Point p = casesVide[positionFin].getLocation();
		panelPlateauJeu.remove(casesVide[positionFin]);
		panelPlateauJeu.add(casesVide[positionFin]);
		casesVide[positionFin].setLocation(p);*/
		
	}
	
	public void detruireVaisseau(int position)
	{
		// On parcourt les vaisseaux des 2 joueurs
		for(int j=0;j<2;j++)
		{
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				// Si on trouve le vaisseau qui se trouve a cette position
				if(plateau[position] == vaisseau[j][i])
				{
					vaisseau[j][i].setEtat(Constantes.ET_EXPL);
					
					//vaisseau[j][i].setEtat(1);
					//vaisseau[j][i].setRotation(20);
					
					// construction d'un Thread
					thread =  new Thread(vaisseau[j][i]) ;
			    	 // lancement de ce thread par appel à sa méthode start()
					thread.start() ;
					
					
			    	 // cette méthode rend immédiatement la main
					/*try {
						thread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					// On met à jour le plateau
					plateau[position] = null;
					// On décrémente le nombre de vaisseau restant
					//cmptVaiss[j]--;
					
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
			}
		}

		// On remet le curseur
		this.setCursor(Cursor.getDefaultCursor());
	}
	
	public void ajouterVaisseau(int position)
	{
		// On stock la position
		positionCaseVisee = position;
		
		// On enleve le vaisseau de son panel d'origine
		if(tourDeJeu==0) // Tour de l'adversaire
		{
			panelPionsTop.remove(vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]]);
			//panelPionsTop.validate();
		}
		else // Tour du joueur
		{
			panelPionsBot.remove(vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]]);
			//panelPionsTop.repaint();
		}
		
		// On met à jour l'état du vaisseau
		vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]].setEtat(Constantes.ET_PLAC);

		
		
		// On l'ajoute dans le jeu
		panelPlateauJeu.add(vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]]);
		Point p = casesVide[position].getLocation();
		panelPlateauJeu.remove(casesVide[position]);
		panelPlateauJeu.add(casesVide[position]);
		casesVide[position].setLocation(p);
		
		// On positionne le vaisseau à l'endroit de la case
		vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]].setLocation(p);//vaisseau[i].setDeplacement(20);
		// On met à jour le plateau
		plateau[position] = vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]];
		
		// On lance le thread
		thread =  new Thread(vaisseau[tourDeJeu][cmptVaiss[tourDeJeu]]) ;
    	 // lancement de ce thread par appel à sa méthode start()
		thread.start() ;

		// On ajoute 1 au compteur de vaisseau en fonction du joueur
		cmptVaiss[tourDeJeu]++;
		
		panelJeu.repaint();
		panelJeu.validate();
		
	}
	  

	public void tirerLasers()
	{
		// On crée les lasers
		laser = new Laser[3];
		int indiceLaser=0;
		
		for(int c=0;c<Constantes.NB_CASES;c++)
		{
			
			if(plateau[c]!=null && plateau[c].getMoulin()==2)
			{
				System.out.println("indiceLaser = " + indiceLaser);
				laser[indiceLaser] = new Laser(this, tourDeJeu, plateau[c].getX(), plateau[c].getY(), indiceLaser);

				laser[indiceLaser].setSize(new Dimension(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE));
				//panelPlateauJeu.add(laser[indiceLaser]);
				panelPlateauJeu.add(laser[indiceLaser]);
				laser[indiceLaser].setLocation(plateau[c].getX(), plateau[c].getY());
				// On calcule et lui envoie le coef directeur
				int x1=laser[indiceLaser].getX();
				int y1=laser[indiceLaser].getY();
				System.out.println("positionDuVaissVise = " + positionDuVaissVise);
				
				for(int t=0;t<Constantes.NB_CASES;t++)
				{
					if(plateau[t]==null)
						System.out.println("plateau["+t+"] = null");
					else
						System.out.println("plateau["+t+"] = vaisseau");
				}
				/*int x2= vaisseau[tourAutreJoueur][i].getX();
				int y2= vaisseau[tourAutreJoueur][i].getY();*/
				int x2= plateau[positionDuVaissVise].getX();
				int y2= plateau[positionDuVaissVise].getY();
				
				// On lui indique le sens
				laser[indiceLaser].setSens((x2-x1)>=0?true:false);
				// On calcule l'écart entre la position initiale et finale
				laser[indiceLaser].calculerEcart(x2, y2);
				laser[indiceLaser].setAngle(x2+Constantes.TAILLE_CASE/2, y2+Constantes.TAILLE_CASE/2);
				
				indiceLaser++;
				
				//laserTest = new Laser(this, 0,200,200);

				
				//panelPionsBot.add(laserTest);
				//laserTest.setLocation(200, 200);

				//panelJeu.repaint();
				//panelJeu.revalidate();

				panelPlateauJeu.repaint();
				panelPlateauJeu.revalidate();
				
			}
		}

		
		
		phase2=Constantes.PHASE_DE_TIR;
		// On deplace les lasers
		for(int l=0;l<3;l++)
		{
			thread =  new Thread(laser[l]) ;
	    	 // lancement de ce thread par appel à sa méthode start()
			thread.start() ;
		}
	}
	
	public void initialisation()
	{
		// Initialisations des positions
        int ecart=Constantes.ECART; //70 correct
        int positionPlateauX=250;//event.getX(); // 280 pour ecart = 70
        int positionPlateauY=120-Constantes.HAUTEUR_PANEL_TOP_BOT;//event.getY(); // 140 pour ecart = 70
        int x=0, y=0;
        //for(int c=0;c<Constantes.NB_CASES;c++)
        //	panelPlateauJeu.add(casesVide[c]);
        // Variable indiquant le vaisseau à afficher
        int k=0;
        // 3 carrés imbriqués
        for(int j=0;j<3;j++)
        {

        	// Origines du carré courant
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
	}
	
	
/*/////////////////////////
 *  ajouter implements MouseMotionLisener
 
	public void mouseDragged(MouseEvent e) {

		// TODO Auto-generated method stub
		// Quand on bouge la souris avec le clic appuyé
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
		
		int tab [] = (int []) arg;
		
		
		switch(tab[0])
		{
		 	// Placement
			case 1:
				//tourDeJeu=tab[1]-1;
				ajouterVaisseau(tab[2]);
				
				System.out.println("tourDejeu = " + tourDeJeu);
				break;
			// Deplacement
			case 2:
				phase2=Constantes.PHASE_DE_DEPLACEMENT;
				deplacerVaisseau(tab[3], tab[4]);
				// On change le tour de jeu
				//changerTourDeJeu();
				break;
			// Moulin
			case 3:
				System.out.println("Moulin : destruction du vaisseau");
				phase2=Constantes.PHASE_D_EXPLOSION;
				detruireVaisseau(tab[5]);
				// On remet le curseur
				//this.setCursor(curseurTir);
				// On change le tour de jeu
				///////////changerTourDeJeu();
				break;
			// Placement+Moulin
			case 4:
				ajouterVaisseau(tab[2]);
				// test
				plateau[tab[2]].setMoulin(2);
				plateau[tab[6]].setMoulin(2);
				plateau[tab[7]].setMoulin(2);
				System.out.println("placement+moulin");
				// On stock la position du vaisseau ciblé par l'ordi
				if(tourDeJeu==0)
				{
					positionDuVaissVise = tab[5];
				}
				// On attend un prochain clic si joueur
				break;
			case 5:
			// Deplacement+Moulin
				phase2=Constantes.PHASE_DE_DEPLACEMENT;
				System.out.println("deplacement+moulin");
				System.out.println("Moulin position : " + tab[4] + " , " + tab[6] + " et " + tab[7]);
				deplacerVaisseau(tab[3],tab[4]);
				plateau[tab[4]].setMoulin(2);
				plateau[tab[6]].setMoulin(2);
				plateau[tab[7]].setMoulin(2);
				//phase2=Constantes.PHASE_DE_TIR;
				if(tourDeJeu==0)
				{
					positionDuVaissVise = tab[5];
					// On remet le curseur
					this.setCursor(Cursor.getDefaultCursor());
					// On change le tour de jeu
					///////////changerTourDeJeu();
				}
				break;
			case 6:
				// Fin de partie
				//cl.show(this, "VictoireEmpire");
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
		 * 				6 -> 	Ordi a moins de 3 pièces
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
		 *		Sixième case -> Voisin pièce moulin
		 * 			Result[6] =	-1 ->	Pas de case voisine
		 *  					x ->	La case x est un des deux voisins de la case qui a fait un moulin
		 *  
		 *		Septième case -> Voisin pièce moulin
		 * 			Result[7] =	-1 ->	Pas de case voisine
		 *  					x ->	La case x est le deuxième voisin de la case qui a fait un moulin*/
		
	}
	public void changerTourDeJeu()
	{

		tourDeJeu = tourDeJeu==0 ? 1 : 0;
		// Si c'est à l'ordi de jouer
		if(tourDeJeu==0)
		{
			// On allume sa cocarde
			cocardeRebelle.setIcon(imgCocardeRebelle[1]);
			// On eteint l'autre
			cocardeEmpire.setIcon(imgCocardeEmpire[0]);
			
			// A l'ordi de jouer
			if(true)
			{
				controleur.ordi(); 
			}
			
		}
		else
		{
			// On allume sa cocarde
			cocardeRebelle.setIcon(imgCocardeRebelle[0]);
			// On eteint l'autre
			cocardeEmpire.setIcon(imgCocardeEmpire[1]);
		}
	}
	
	public void viserCible()
	{
		for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
		{
			if(vaisseau[tourDeJeu][i].getMoulin()==2)
			{
				vaisseau[tourDeJeu][i].setAngle(plateau[positionDuVaissVise].getX()+Constantes.TAILLE_CASE/2
						,plateau[positionDuVaissVise].getY()+Constantes.TAILLE_CASE/2);
			}
				
		}
			
	}
	
	public void testFinDePartie()
	{
		if(phase==2)
		{
			int nbPionsRebelle=0, nbPionsEmpire=0;
			for(int i=0;i<Constantes.NB_CASES;i++)
			{
				if(plateau[i] != null && plateau[i].getEquipe()==0)	
					nbPionsRebelle++;
				else if(plateau[i] != null && plateau[i].getEquipe()==1)	
					nbPionsEmpire++;
					
			}
			
			if(nbPionsEmpire<3)
			{
				cl.show(this, "VictoireRebelle");
			}
			if(nbPionsRebelle<3)
			{
				cl.show(this, "VictoireEmpire");
			}
		}
		
	}

	
	public void threadPlacementTermine()
	{
		// Si moulin
		if(plateau[positionCaseVisee].getMoulin() == 2)
		{
			if(tourDeJeu==0)
			{
				phase2=Constantes.PHASE_DE_TIR;
				//detruireVaisseau(tab[5]);
				viserCible();
				tirerLasers();
				// On remet le curseur
				this.setCursor(Cursor.getDefaultCursor());
				// On change le tour de jeu
				////////////////changerTourDeJeu();
			}
			else
			{
				phase2=Constantes.PHASE_DE_CHOIX_CIBLE;
				// On change le curseur en mode tir
				this.setCursor(curseurTir);
			}
		}
		else
		{
			// On change le tour de jeu
			changerTourDeJeu();
			phase2 = Constantes.PHASE_DE_JEU;
		}
		
		
	}
	
	public void threadDeplacementTermine()
	{

		Point p = casesVide[numCasePostDepl].getLocation();
		// On supprime la case vide et la remet au dernier plan
		panelPlateauJeu.remove(casesVide[numCasePostDepl]);
		panelPlateauJeu.add(casesVide[numCasePostDepl]);
		casesVide[numCasePostDepl].setLocation(p);
		
		// On modifie la phase
		if(plateau[numCasePostDepl].getMoulin() == 2)
		{

			
			
			// Si c'est à l'ordi de jouer et qu'il a fait un moulin 
			// (+ mode JvsIA activé)
			if(tourDeJeu==0 && true) // true en atendant
			{
				//phase2 = Constantes.PHASE_D_EXPLOSION;
				phase2 = Constantes.PHASE_DE_TIR;
				viserCible();
				tirerLasers();
				//detruireVaisseau(positionDuVaissVise);
			}
			else
			{
				phase2=Constantes.PHASE_DE_CHOIX_CIBLE;
				// On change le curseur en mode tir
				this.setCursor(curseurTir);
			}
		}
		else
		{
			phase2 = Constantes.PHASE_DE_JEU;
			changerTourDeJeu();
		}
		
		if(phase2 != Constantes.PHASE_DE_CHOIX_CIBLE)
		{
		}
		else
		{
			
		}
	}
	
	public void threadExplosionTermine()
	{
		testFinDePartie();
		
		// On remet droit les vaisseaux
		for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
		{
			if(vaisseau[tourDeJeu][i].getMoulin() == 2)
			{
	
				System.out.println("on replace le vaisseau");
				vaisseau[tourDeJeu][i].setAngle(0);
				vaisseau[tourDeJeu][i].setMoulin(1);
			}
		}
		phase2 = Constantes.PHASE_DE_JEU;
		changerTourDeJeu();
	}
	
	public void threadDeplacementLaserTermine(int i)
	{
		// Permet de savoir le numero du tour de l'autre joueur
		int tourAutreJoueur = tourDeJeu==0 ? 1 : 0;

		// On retire le laser du jeu
		//for(int l=0;l<3;l++)
		//{
			panelPlateauJeu.remove(laser[i]);
		//}
		panelJeu.repaint();
		panelJeu.validate();
		// Explosion
		if(phase2==Constantes.PHASE_DE_TIR)
		{
			phase2=Constantes.PHASE_D_EXPLOSION;
			if(tourDeJeu==1){ // Si c'est le tour du joueur
				controleur.RetirerPiece(positionDuVaissVise, tourAutreJoueur+1);
			}
			else // tour de l'ordi
			{
				detruireVaisseau(positionDuVaissVise);
			}
		}
	}
}

