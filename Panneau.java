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
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;


public class Panneau extends JPanel implements MouseListener, MouseMotionListener, KeyListener, Observer{
	
	
	private int [] cmptVaiss = new int[2];
	private int numCasePostDepl;
	private int positionDuVaissVise, positionCaseVisee;
	//cmptVaiss[0] = 0;
	private int phase = 1;
	private int actionEnCoursVaisseau = Constantes.PHASE_DE_JEU;
	private int tourDeJeu = 0;
	private int tourOrdi = 0;
	private int [] faction = new int[2];
	//private int tourCommencement = 0;
	// 0 : Rebelle commence ,  1 : Empire commence
	// Le mode de jeu choisi avec : MODE_JVSJ , MODE_JVSO, MODE_OVSO
	private int modeDeJeu = Constantes.MODE_JVSO;
	
	private boolean retourPartie = false;
	private int modePoney = 0;
	private boolean m = false, l = false;
	private boolean reprendrePartieCliquable = false;
	
	

	// GESTION E/S FICHIER
	JFileChooser fc;
	
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
	private JPanel panelJeu,panelPlateauJeu, panelTop, panelWest, panelEast, panelPionsTop, panelPionsBot;
	private JPanel panelNouvPartieP1, panelNouvPartieP2, panelNouvPartieP3;
	public JButton boutonMenu, boutonCommencerPartie;
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
	// Attributs des pages concernant les reglages de la nouvelle partie
	private JButton btnOrdiFacile, btnOrdiMoyen, btnOrdiDifficile;
	private JButton btnRetourP0, btnRetourP1, btnRetourP2, btnRetourP3;
	private JButton btnSuivantP1 ,btnSuivantP2, btnSuivantP3;
	private JLabel labelJoueur, labelJoueur1, labelJoueur2, labelFaction, labelDifficulte;
	private JLabel labelFactionJ1, labelFactionJ2;
	private JButton boutonFactionEmpire, boutonFactionRebelle , boutonEchangerFactions;
	private ImageIcon imgXwing, imgXwingSelect, imgTIE, imgTIESelect;
	private JTextField pseudo, pseudo1, pseudo2;

	// Bordures des boutons dans le menu
	private Color jauneSW = new Color(255,241,31);
	private Color orangeSW = new Color(255,177,9);
	
    private Border bordureJaune = BorderFactory.createMatteBorder(
            3, 3, 3, 3, jauneSW);
    //6, 6, 3, 3, new ImageIcon("Images/Rebelle/Xplo7.png"));
    
    private Border bordureOrange = BorderFactory.createMatteBorder(
            3, 3, 3, 3, orangeSW);
    
	// la police star wars
	private Font policeStarWars, policeSpaceAge;
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
	
	//private JPanel panelPionsTop, panelPionsBot, panelPlateauJeu;
	//private Fenetre maFenetre;

	private Timer declencheur;
	private Image timer;
	
	// DECLARATION DES FICHIERS IMAGE
	private File fileFond = new File("Images/Fond/fond.png");
	private File fileFondPoney = new File("Images/Fond/fond4.png");
	private File fileFondPoney2 = new File("Images/Fond/fond5.png");
	private File fileFondMenu= new File("Images/Fond/fondMenu.png");
	private File fileFondMenuPoney= new File("Images/Fond/fondMenuPoney.png");
	private File fileFondRegles= new File("Images/Fond/fondRegles.png");
	private File fileFondVictoireEmpire= new File("Images/Fond/fondVictoireEmpire.png");
	private File fileFondVictoireRebelle = new File("Images/Fond/fondVictoireRebelle.png");
	//private File fileFondVictoireRebelle = new File("Images/Fond/fondVictoireRebelle.png");
	private File fileFondAPropos= new File("Images/Fond/fondtest.png");
	// test
	private File fileFondTest = new File("Images/Fond/fondtest.png");
	private BufferedImage imageFondTest;
	private File fileVaisseau = new File("Images/Animations/bleu.png");
	
	// DECLARATION DES IMAGES
	 
	private BufferedImage imageFond, imageFondPoney, imageFondPoney2, imageFondRegles;
	private BufferedImage imageFondMenu, imageFondMenuPoney, imageFondVictoireEmpire, imageFondVictoireRebelle;
	private BufferedImage imageFondAPropos;
	private ImageIcon gifAPropos;
	private ImageIcon [] imgCocardeEmpire;
	private ImageIcon [] imgCocardeRebelle;

	private JLabel cocardeEmpire;
	private JLabel cocardeRebelle;
	
	private ImageIcon cases;
	
	private int cmpt_anim;
	
	private JLabel labelGifAPropos;
	
	private JButton [] casesVide;
	private Vaisseau [][] vaisseau;
	private Laser [] laser;
	private Laser laserTest;
	private Vaisseau vaisseauTest;
	private JLabel pseudoJ1, pseudoJ2;
	
	private Controleur controleur;
	public Panneau(Controleur controleur)
	{		

		faction[tourOrdi] = 1;
		faction[1] = 0;
		
		// initialisation de la police star wars
		//policeStarWars = new Font("Times New Roman", Font.BOLD, 30);
		//policeStarWars = new Font();
		//policeStarWars = Font.createFont(Font.TRUETYPE_FONT, "Starjhol.ttf");
		try {
			policeStarWars = Font.createFont(Font.TRUETYPE_FONT, new File("Starjhol.ttf"));
			policeSpaceAge = Font.createFont(Font.TRUETYPE_FONT, new File("Police/spaceAge.ttf"));
		} catch (FontFormatException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//policeStarWars = policeStarWars.deriveFont((float)30);
		policeStarWars = policeStarWars.deriveFont((float)30.0);
		policeSpaceAge = policeSpaceAge.deriveFont((float)25.0);
		
		// couleur jaune : 255 , 241, 31
		// couleur orange : 255 , 177, 9

		// GESTION FICHIER
		//Create a file chooser
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File("Sauvegardes"));
		
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
		
	       
		btnRetourP0 = new JButton(" retour ");
		btnRetourP0.setFont(policeStarWars);
		btnRetourP0.setFocusPainted( false );
	    // btnRetourP2.setBorderPainted(false);
		btnRetourP0.setContentAreaFilled(false);
		btnRetourP0.setForeground(jauneSW);
		btnRetourP0.setBorder(bordureJaune);
		
		//btnRetourP0.setPreferredSize(new Dimension(200,50));

		btnJvsJ.setFont(policeStarWars);
		btnJvsJ.setText("joueur vs joueur");
		btnJvsJ.setPreferredSize(new Dimension(380,120));
		btnJvsJ.setVerticalTextPosition(SwingConstants.CENTER);
		btnJvsJ.setHorizontalTextPosition(SwingConstants.CENTER); 
		
		btnJvsO.setFont(policeStarWars);
		btnJvsO.setText("joueur vs ordi");
		//btnJvsJ.setForeground(jauneSW);
		// couleur jaune : 255 , 241, 31
		btnJvsO.setVerticalTextPosition(SwingConstants.CENTER);
		btnJvsO.setHorizontalTextPosition(SwingConstants.CENTER); 

		btnOvsO.setFont(policeStarWars);
		btnOvsO.setText("ordi vs ordi");
		btnOvsO.setPreferredSize(new Dimension(380,120));
		btnOvsO.setVerticalTextPosition(SwingConstants.CENTER);
		btnOvsO.setHorizontalTextPosition(SwingConstants.CENTER); 

		btnRetourP0.setFont(policeStarWars);
		btnRetourP0.setText("   retour   ");

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
        
        // Attributs communs des pages 1, 2 et 3
        imgXwing = new ImageIcon("Images/Xwing.png");
        imgTIE = new ImageIcon("Images/TIE.png");
        
        
        // On s'occupe de la Page 1 : Joueur vs Joueur
        
        panelNouvPartieP1 = new JPanel(new GridBagLayout());
		policeStarWars = policeStarWars.deriveFont((float)30.0);

        labelJoueur1 = new JLabel("joueur 1 : ");
        labelJoueur1.setFont(policeStarWars);

        labelJoueur2 = new JLabel("joueur 2 : ");
        labelJoueur2.setFont(policeStarWars);

		policeStarWars = policeStarWars.deriveFont((float)20.0);
        boutonEchangerFactions = new JButton(" echanger ");
        boutonEchangerFactions.setFont(policeStarWars);
        boutonEchangerFactions.setFocusPainted( false );
        boutonEchangerFactions.setContentAreaFilled(false);
        boutonEchangerFactions.setForeground(jauneSW);
        boutonEchangerFactions.setBorder(bordureOrange);
        //boutonEchangerFactions.setSelected(false);

        // On change la police pour les pseudos
		policeStarWars = policeStarWars.deriveFont((float)25.0);
        
		pseudo1 = new JTextField("pseudo 1", 10);
        pseudo1.setFont(policeSpaceAge);
        pseudo1.addKeyListener(this);
        
		pseudo2 = new JTextField("pseudo 2", 10);
		pseudo2.setFont(policeSpaceAge);
		pseudo2.addKeyListener(this);
		
        labelFactionJ1 = new JLabel(imgXwing);
        labelFactionJ2 = new JLabel(imgTIE);
        

		policeStarWars = policeStarWars.deriveFont((float)30.0);
        btnRetourP1 = new JButton(" retour ");
        btnRetourP1.setFont(policeStarWars);
        btnRetourP1.setFocusPainted( false );
       // btnOrdiFacile.setBorderPainted(false);
        btnRetourP1.setContentAreaFilled(false);
        btnRetourP1.setForeground(jauneSW);
        btnRetourP1.setBorder(bordureJaune);
        
        btnSuivantP1 = new JButton(" suivant ");
        btnSuivantP1.setFont(policeStarWars);
        btnSuivantP1.setFocusPainted( false );
       // btnOrdiFacile.setBorderPainted(false);
        btnSuivantP1.setContentAreaFilled(false);
        btnSuivantP1.setForeground(jauneSW);
        btnSuivantP1.setBorder(bordureJaune);
        
        
		
		gbc = null;
		gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor=GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 5, 5, 10);
		panelNouvPartieP1.add(labelJoueur1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 420, 5, 5);
		panelNouvPartieP1.add(labelFactionJ1, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
		panelNouvPartieP1.add(pseudo1, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.anchor=GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 420, 5, 5);
		panelNouvPartieP1.add(boutonEchangerFactions, gbc);

		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor=GridBagConstraints.SOUTH;
        gbc.insets = new Insets(5, 5, 5, 5);
		panelNouvPartieP1.add(labelJoueur2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
        gbc.insets = new Insets(5, 420, 5, 5);
		gbc.anchor=GridBagConstraints.CENTER;
		panelNouvPartieP1.add(labelFactionJ2, gbc);

		gbc.gridx = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
		panelNouvPartieP1.add(pseudo2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
        gbc.insets = new Insets(50, 420, 5, 5);
		gbc.anchor=GridBagConstraints.LAST_LINE_START;
		panelNouvPartieP1.add(btnRetourP1, gbc);
		gbc.gridx=1;
		gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor=GridBagConstraints.LAST_LINE_END;
		panelNouvPartieP1.add(btnSuivantP1, gbc);
		
		
        // On ajoute les boutons au listener
        pseudo1.addMouseListener(this);
        pseudo2.addMouseListener(this);
        boutonEchangerFactions.addMouseListener(this);
        btnRetourP1.addMouseListener(this);
        btnSuivantP1.addMouseListener(this);
		
		
        // Fin Page 1
        
        // On s'occupe de la Page 2 : Joueur vs Ordi
        panelNouvPartieP2 = new JPanel(new GridBagLayout());

        imgXwingSelect = new ImageIcon("Images/XwingSelect.png");
        imgTIESelect = new ImageIcon("Images/TIESelect.png");
        
		policeStarWars = policeStarWars.deriveFont((float)25.0);
        labelJoueur = new JLabel("joueur : ");
        //labelJoueur.setForeground(jauneSW);
        labelJoueur.setFont(policeStarWars);
        //labelJoueur.getFont() = labelJoueur.getFont().deriveFont((float)5.0);

        labelFaction = new JLabel("faction : ");
        labelFaction.setFont(policeStarWars);
        //labelFaction.setForeground(jauneSW);

		policeStarWars = policeStarWars.deriveFont((float)20.0);
        
        pseudo = new JTextField("  pseudo  ", 10);
        pseudo.setFont(policeSpaceAge);
        //pseudo.setBorder(bordureJaune);
        //pseudo.setForeground(orangeSW);
        
        pseudo.addKeyListener(this);
        

        boutonFactionEmpire = new JButton(imgTIESelect);
        boutonFactionEmpire.setSelected(true);
        boutonFactionEmpire.setFocusPainted( false );
        boutonFactionEmpire.setBorderPainted(false);
        boutonFactionEmpire.setContentAreaFilled(false);
		
        boutonFactionRebelle = new JButton(imgXwing);
        boutonFactionRebelle.setFocusPainted( false );
        boutonFactionRebelle.setBorderPainted(false);
        boutonFactionRebelle.setContentAreaFilled(false);

        //pseudo.getFont().deriveFont((float)20.0);
		policeStarWars = policeStarWars.deriveFont((float)30.0);

        labelDifficulte = new JLabel("difficulte :");
        labelDifficulte.setFont(policeStarWars);
        labelDifficulte.setPreferredSize(new Dimension(380,40));
        

        
        btnOrdiFacile = new JButton("facile");
        btnOrdiFacile.setFont(policeStarWars);
        btnOrdiFacile.setPreferredSize(new Dimension(380,40));
       // btnOrdiFacile.setPreferredSize(new Dimension(380,40));
        btnOrdiFacile.setFocusPainted( false );
       // btnOrdiFacile.setBorderPainted(false);
        btnOrdiFacile.setContentAreaFilled(false);
        btnOrdiFacile.setForeground(jauneSW);
        btnOrdiFacile.setBorder(bordureJaune);
        
        
        btnOrdiMoyen = new JButton("moyen");
        btnOrdiMoyen.setFont(policeStarWars);
        btnOrdiMoyen.setPreferredSize(new Dimension(380,40));
        btnOrdiMoyen.setFocusPainted( false );
       // btnOrdiDifficile.setBorderPainted(false);
        btnOrdiMoyen.setContentAreaFilled(false);
        btnOrdiMoyen.setForeground(orangeSW);
        btnOrdiMoyen.setBorder(bordureOrange);
        btnOrdiMoyen.setSelected(true);
        
        btnOrdiDifficile = new JButton("difficile");
        btnOrdiDifficile.setFont(policeStarWars);
        btnOrdiDifficile.setPreferredSize(new Dimension(380,40)); 
        btnOrdiDifficile.setFocusPainted( false );
       // btnOrdiDifficile.setBorderPainted(false);
        btnOrdiDifficile.setContentAreaFilled(false);
        btnOrdiDifficile.setForeground(jauneSW);
        btnOrdiDifficile.setBorder(bordureJaune);
       
		btnRetourP2 = new JButton(" retour ");
		btnRetourP2.setFont(policeStarWars);
		btnRetourP2.setFocusPainted( false );
       // btnRetourP2.setBorderPainted(false);
		btnRetourP2.setContentAreaFilled(false);
		btnRetourP2.setForeground(jauneSW);
		btnRetourP2.setBorder(bordureJaune);
	       
		btnSuivantP2 = new JButton(" suivant ");
		btnSuivantP2.setFont(policeStarWars);
		btnSuivantP2.setFocusPainted( false );
	    //btnSuivantP2.setBorderPainted(false);
		btnSuivantP2.setContentAreaFilled(false);
		btnSuivantP2.setForeground(jauneSW);
		btnSuivantP2.setBorder(bordureJaune);
        
        
		gbc = null;
		gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
		gbc.gridx = 0;
		gbc.gridy = 0;
        gbc.insets = new Insets(5, 420, 5, 10);
		panelNouvPartieP2.add(labelJoueur, gbc);
		gbc.gridx=1;
		gbc.gridwidth=2;
        gbc.insets = new Insets(5, 0, 5, 5);
		panelNouvPartieP2.add(pseudo, gbc);
		
		gbc.gridwidth=1;
		gbc.gridx=0;
		gbc.gridy=2;
        gbc.insets = new Insets(5, 420, 5, 5);
		panelNouvPartieP2.add(labelFaction, gbc);
		gbc.gridx=1;
        gbc.insets = new Insets(5, 0, 5, 5);
		panelNouvPartieP2.add(boutonFactionEmpire, gbc);
		gbc.gridx=2;
		panelNouvPartieP2.add(boutonFactionRebelle, gbc);
		
		
		gbc.gridx=0;
		gbc.gridy=3;
		gbc.gridwidth = 3;
        gbc.insets = new Insets(5, 420, 5, 5);
		panelNouvPartieP2.add(labelDifficulte, gbc);
		gbc.gridx=0;
		gbc.gridy=4;
		panelNouvPartieP2.add(btnOrdiFacile, gbc);
		gbc.gridx=0;
		gbc.gridy=5;
		panelNouvPartieP2.add(btnOrdiMoyen, gbc);
		gbc.gridx=0;
		gbc.gridy=6;
		panelNouvPartieP2.add(btnOrdiDifficile, gbc);
		

		gbc.gridx=0;
		gbc.gridy=7;
		gbc.gridwidth = 1;
        gbc.insets = new Insets(50, 420, 5, 5);
		gbc.anchor=GridBagConstraints.LAST_LINE_START;
		panelNouvPartieP2.add(btnRetourP2, gbc);
		gbc.gridx=1;
		gbc.gridy=7;
		gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor=GridBagConstraints.LAST_LINE_END;
		panelNouvPartieP2.add(btnSuivantP2, gbc);
		
        // On ajoute les boutons au listener
        pseudo.addMouseListener(this);
        boutonFactionEmpire.addMouseListener(this);
        boutonFactionRebelle.addMouseListener(this);
        btnOrdiFacile.addMouseListener(this);
        btnOrdiMoyen.addMouseListener(this);
        btnOrdiDifficile.addMouseListener(this);
        btnRetourP2.addMouseListener(this);
        btnSuivantP2.addMouseListener(this);
        
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
		
		boutonMenu = new JButton();
		//boutonMenu.setSize(200, 100);
		boutonMenu.setPreferredSize(new Dimension(200, 100));
		//boutonMenu.setFocusPainted( false ); // enleve la bordure de l'image
		boutonMenu.setBorderPainted(false); // enleve la bordure du bouton
		boutonMenu.setOpaque(false); // enleve la bordure du bouton
		boutonMenu.setContentAreaFilled(false);
		boutonMenu.addMouseListener(this);
		
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
		
		
		
		panelPionsTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//panelPionsTop.setPreferredSize(new Dimension(755, 80));
		panelPionsBot = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panelPionsBot.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		//panelPionsTop.setSize(new Dimension(755, 80));
		
		//panelPionsTop = new JPanel(new GridLayout(1, 10, 0,0));
		//panelTop = new JPanel(new GridBagLayout());
		
		panelTop = new JPanel(new BorderLayout());
		panelTop.add(boutonMenu, BorderLayout.WEST);
		panelTop.add(panelPionsTop, BorderLayout.EAST);
		
		panelWest = new JPanel(new BorderLayout());
		panelWest.setPreferredSize(new Dimension(Constantes.LARGEUR_PANEL_WEST, Constantes.FENETRE_HAUTEUR));
		panelEast = new JPanel(new BorderLayout());
		panelEast.setPreferredSize(new Dimension(Constantes.LARGEUR_PANEL_EAST, Constantes.FENETRE_HAUTEUR));
		
		
		
		
		boutonCommencerPartie = new JButton("start");

		//boutonCommencerPartie = new JButton(new ImageIcon("Images/Menu/nouvellePartie1.png"));
		policeStarWars = policeStarWars.deriveFont((float)36.0);
		boutonCommencerPartie.setFont(policeStarWars);
		boutonCommencerPartie.setFocusPainted( false );
        boutonCommencerPartie.setContentAreaFilled(false);
        boutonCommencerPartie.setForeground(orangeSW);
        boutonCommencerPartie.setBorder(bordureOrange);
		boutonCommencerPartie.setPreferredSize(new Dimension(380,40));
		boutonCommencerPartie.setSize(new Dimension(Constantes.TAILLE_CASE*2,Constantes.TAILLE_CASE));
		boutonCommencerPartie.addMouseListener(this);
		boutonCommencerPartie.setLocation(Constantes.ECART*2+250 + Constantes.TAILLE_CASE -22-Constantes.LARGEUR_PANEL_WEST
				, Constantes.ECART*2+120-Constantes.HAUTEUR_PANEL_TOP_BOT+Constantes.TAILLE_CASE+10);
		panelPlateauJeu.add(boutonCommencerPartie);
		
		/*gbc.gridx = 1;
		gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 0);
		panelTop.add(panelPionsTop, gbc);*/
		
		
		// Redimensionnement des panneaux
		panelPlateauJeu.setOpaque(false);
		panelPionsTop.setOpaque(false);
		panelTop.setOpaque(false);
		panelWest.setOpaque(false);
		panelEast.setOpaque(false);
		panelPionsBot.setOpaque(false);

		// Initialisation des pseudos
		pseudoJ1 = new JLabel("",JLabel.CENTER);
		pseudoJ1.setFont(policeSpaceAge);
		pseudoJ1.setForeground(jauneSW);
		pseudoJ2 = new JLabel("",JLabel.CENTER);
		pseudoJ2.setFont(policeSpaceAge);
		pseudoJ2.setForeground(jauneSW);
		
		// Ajout des panneaux au panneau jeu
		//panelJeu.add(panelPionsTop, "North");
		panelJeu.add(panelTop, BorderLayout.NORTH);
		panelJeu.add(panelPlateauJeu, BorderLayout.CENTER);
		panelJeu.add(panelPionsBot, BorderLayout.SOUTH);
		panelJeu.add(panelWest, BorderLayout.WEST);
		panelJeu.add(panelEast, BorderLayout.EAST);
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
	    panelMenu.setFocusable(true);
	    panelMenu.requestFocus();
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
			imageFondPoney = ImageIO.read(fileFondPoney);
			imageFondPoney2 = ImageIO.read(fileFondPoney2);
			imageFondMenu = ImageIO.read(fileFondMenu);
			imageFondRegles = ImageIO.read(fileFondRegles);
			imageFondMenuPoney = ImageIO.read(fileFondMenuPoney);
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
	        	
	        	
	        	vaisseau[j][i] = new Vaisseau(this, j, i);
	
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
        	//panelPlateauJeu.add(casesVide[i]);
        }

        
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
		panelMenu.addKeyListener(this);
		panelJeu.addKeyListener(this);
		
	    panelMenu.requestFocus();
		//panel.addKeyListener(this);
		
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
			if(panelMenu.isVisible() || panelNouvPartie.isVisible()
					|| panelOptions.isVisible() || panelNouvPartieP1.isVisible() 
					|| panelNouvPartieP2.isVisible() || panelNouvPartieP3.isVisible())
			{
				if(modePoney>0){
					g.drawImage(imageFondMenuPoney, 0, 0, null);}
				else{
				g.drawImage(imageFondMenu, 0, 0, null);}
			}
			else if(panelRegles.isVisible())
			{
				g.drawImage(imageFondRegles, 0, 0, null);
			}
			else if(panelAPropos.isVisible())
			{
				//g.drawImage(imageFondAPropos, 0, 0, null);
			}
			else if(panelJeu.isVisible())
			{
				 //System.out.println("on affiche le jeu");
				if(modePoney==1){
					g.drawImage(imageFondPoney, 0, 0, null);}
				else if(modePoney==2){
					g.drawImage(imageFondPoney2, 0, 0, null);}
				else{
				g.drawImage(imageFond, 0, 0, null);}
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
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
			//JButton temp = (JButton)event.getSource();
		if(event.getSource() == btnReprPartie && reprendrePartieCliquable)
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
		if(event.getSource() == btnRetourP0 || event.getSource() == btnRetourP1 
				|| event.getSource() == btnRetourP2 || event.getSource() == btnRetourP3 
				|| event.getSource() == btnSuivantP1 || event.getSource() == btnSuivantP2 
				/*|| event.getSource() == btnSuivantP3*/)
		{
			JButton b = (JButton)event.getSource();
			b.setForeground(orangeSW);
			b.setBorder(bordureOrange);
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
		if(event.getSource() == btnRetourP0 || event.getSource() == btnRetourP1 
				|| event.getSource() == btnRetourP2 || event.getSource() == btnRetourP3 
				|| event.getSource() == btnSuivantP1 || event.getSource() == btnSuivantP2 
				/*|| event.getSource() == btnSuivantP3*/)
		{
			JButton b = (JButton)event.getSource();
			b.setForeground(jauneSW);
			b.setBorder(bordureJaune);
		}
	}
	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

		
	
	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub
		
	if(panelMenu.isVisible())
	{
		// Clic sur "Options"
		if(event.getSource() == btnReprPartie && SwingUtilities.isLeftMouseButton(event) && reprendrePartieCliquable)
		{
			 cl.show(this, "Jeu");
			 panelJeu.requestFocus();
		}
		// Clic sur "Nouvelle Partie"
		else if(event.getSource() == btnNouvPartie && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "NouvellePartie");
		}
		// Clic sur "Charger Partie"
		else if(event.getSource() == btnChargerPartie && SwingUtilities.isLeftMouseButton(event) )
		{
			 //cl.show(this, "NouvellePartie");
			charger();
		}
		// Clic sur "Sauvegarder Partie"
		else if(event.getSource() == btnSauvPartie && SwingUtilities.isLeftMouseButton(event) )
		{
			//controleur.save(nomPartie)
			sauvegarder();
		}
		// Clic sur "Options"
		else if(event.getSource() == btnOptions && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Options");

			 panelOptions.requestFocus();
		}
		// Clic sur "Regles"
		else if(event.getSource() == btnRegles && SwingUtilities.isLeftMouseButton(event) )
		{
			cl.show(this, "Regles");

		    panelRegles.requestFocus();
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
			controleur.newPartieOO();
			modeDeJeu = Constantes.MODE_OVSO;
			raz();
			cl.show(this, "Jeu");
		    panelJeu.requestFocus();
		}
		// Clic sur "Retour"
		if(event.getSource() == btnRetourP0 && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
		}
	}
	else if(panelNouvPartieP1.isVisible())
	{
		// Page "Joueur contre Joueur"
		
		

		// Clic sur "Retour"
		if(event.getSource() == btnRetourP1 && SwingUtilities.isLeftMouseButton(event) )
		{
			 cl.show(this, "NouvellePartie");
		}
		// Clic sur "Suivant"
		else if(event.getSource() == btnSuivantP1 && SwingUtilities.isLeftMouseButton(event) )
		{
			if(pseudo1.getText().length() != 0 && pseudo2.getText().length() != 0)
			{
				controleur.newPartieJJ(pseudo1.getText(), pseudo2.getText());
				modeDeJeu = Constantes.MODE_JVSJ;
				raz();

				pseudoJ1.setText(pseudo1.getText());
				pseudoJ2.setText(pseudo2.getText());
				System.out.println("pseudo 1 = " + pseudoJ1.getText());
				System.out.println("pseudo 2 = " + pseudoJ2.getText());
				if(boutonEchangerFactions.isSelected())
				{
					faction[0] = Constantes.FACTION_EMPIRE; // faction du joueur 1
					faction[1] = Constantes.FACTION_REBELLE; // faction du joueur 2
					// modifier l'emplacement du pseudo
					panelWest.add(pseudoJ1, BorderLayout.NORTH);
					panelEast.add(pseudoJ2, BorderLayout.SOUTH);
				}
				else
				{
					faction[0] = Constantes.FACTION_REBELLE; // faction du joueur 1
					faction[1] = Constantes.FACTION_EMPIRE; // faction du joueur 2
					// modifier l'emplacement du pseudo
					panelEast.add(pseudoJ1, BorderLayout.NORTH);
					panelWest.add(pseudoJ2, BorderLayout.SOUTH);
				}
				cl.show(this, "Jeu");
			    panelJeu.requestFocus();
			}
		}
		else if(event.getSource() == pseudo1 && SwingUtilities.isLeftMouseButton(event) )
		{
			pseudo1.setText("");
		}
		else if(event.getSource() == pseudo2 && SwingUtilities.isLeftMouseButton(event) )
		{
			pseudo2.setText("");
		}
		else if(event.getSource() == boutonEchangerFactions && SwingUtilities.isLeftMouseButton(event) )
		{
			if(boutonEchangerFactions.isSelected())
				boutonEchangerFactions.setSelected(false);
			else
				boutonEchangerFactions.setSelected(true);
			
			ImageIcon tmp = (ImageIcon)labelFactionJ1.getIcon();
			labelFactionJ1.setIcon(labelFactionJ2.getIcon());
			labelFactionJ2.setIcon(tmp);
			
		}
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
			if(pseudo.getText().length() != 0)
			{
				int difficulte=0;
				if(btnOrdiFacile.isSelected()){
					difficulte = 1;}
				else if(btnOrdiDifficile.isSelected()){
					difficulte = 3;}
				else{
					difficulte = 2;}
				
				controleur.newPartieJO(pseudo.getText(), difficulte);
				modeDeJeu = Constantes.MODE_JVSO;
				raz();
				pseudoJ1.setText(pseudo.getText());
				pseudoJ2.setText("R2D2");
				if(boutonFactionEmpire.isSelected())
				{
					faction[tourOrdi] = Constantes.FACTION_REBELLE; // faction de l'ordi
					faction[1] = Constantes.FACTION_EMPIRE; // faction du joueur
					
					// modifier l'emplacement du pseudo
					panelWest.add(pseudoJ1, BorderLayout.SOUTH);
					panelEast.add(pseudoJ2, BorderLayout.NORTH);
				}
				else
				{
					faction[tourOrdi] = Constantes.FACTION_EMPIRE; // faction de l'ordi
					faction[1] = Constantes.FACTION_REBELLE; // faction du joueur
					
					// modifier l'emplacement du pseudo
					panelWest.add(pseudoJ2, BorderLayout.SOUTH);
					panelEast.add(pseudoJ1, BorderLayout.NORTH);
				}
				cl.show(this, "Jeu");
			    panelJeu.requestFocus();
			}
		}
		else if(event.getSource() == pseudo && SwingUtilities.isLeftMouseButton(event) )
		{
				pseudo.setText("");
		}
		else if(event.getSource() == boutonFactionEmpire && SwingUtilities.isLeftMouseButton(event) )
		{
			boutonFactionEmpire.setSelected(true);
			boutonFactionEmpire.setIcon(imgTIESelect);
			if(boutonFactionRebelle.isSelected())
			{
				boutonFactionRebelle.setSelected(false);
				boutonFactionRebelle.setIcon(imgXwing);
			}
		}
		else if(event.getSource() == boutonFactionRebelle && SwingUtilities.isLeftMouseButton(event) )
		{
			boutonFactionRebelle.setSelected(true);
			boutonFactionRebelle.setIcon(imgXwingSelect);
			if(boutonFactionEmpire.isSelected())
			{
				boutonFactionEmpire.setSelected(false);
				boutonFactionEmpire.setIcon(imgTIE);
			}
		}
		else if((event.getSource() == btnOrdiFacile || event.getSource() == btnOrdiMoyen || event.getSource() == btnOrdiDifficile)  && SwingUtilities.isLeftMouseButton(event) )
		{
			

			if(btnOrdiFacile.isSelected())
			{
				btnOrdiFacile.setForeground(jauneSW);
				btnOrdiFacile.setSelected(false);
				btnOrdiFacile.setBorder(bordureJaune);
			}
			if(btnOrdiMoyen.isSelected())
			{
				btnOrdiMoyen.setForeground(jauneSW);
				btnOrdiMoyen.setSelected(false);
				btnOrdiMoyen.setBorder(bordureJaune);
			}
			if(btnOrdiDifficile.isSelected())
			{
				btnOrdiDifficile.setForeground(jauneSW);
				btnOrdiDifficile.setSelected(false);
				btnOrdiDifficile.setBorder(bordureJaune);
			}
			
			((AbstractButton) event.getSource()).setSelected(true);
			((AbstractButton) event.getSource()).setBorder(bordureOrange);
			((AbstractButton) event.getSource()).setForeground(orangeSW);
		}
		
	}
	else if(panelOptions.isVisible())
	{
		// Clic dans l'option
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
	}
	else if(panelRegles.isVisible())
	{
		// Clic dans le menu
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
	}
	else if(panelAPropos.isVisible())
	{
		// Clic dans l'a propos
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
	}
	else if(panelVictoireEmpire.isVisible())
	{
		// Clic apres victoire empire
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
	}
	else if(panelVictoireRebelle.isVisible())
	{
		// Clic apres victoire rebelle
			 cl.show(this, "Menu");
			 panelMenu.requestFocus();
	}
	else if(panelJeu.isVisible())
	{
		/*if(event.getSource() == this && SwingUtilities.isMiddleMouseButton(event) )
		{
		                // Bouton du milieu 
		        cl.show(this, "Menu");
		}*/
		if(event.getSource() == boutonMenu && SwingUtilities.isLeftMouseButton(event) )
		{
		        cl.show(this, "Menu");
				 panelMenu.requestFocus();
		} 	
		else if(event.getSource() == this && SwingUtilities.isMiddleMouseButton(event) )
		{
		        cl.show(this, "Menu");
				 panelMenu.requestFocus();
		} 	
		else if (event.getSource() == boutonCommencerPartie ) 
		{
			reprendrePartieCliquable = true;
			panelPlateauJeu.remove(boutonCommencerPartie);
	        for(int c=0;c<Constantes.NB_CASES;c++)
	        	panelPlateauJeu.add(casesVide[c]);
	        initialisation();
			panelJeu.repaint();
			panelJeu.validate();
			
			 // a l'ordi de commencer
			 if(modeDeJeu == Constantes.MODE_OVSO)
		     {
				 controleur.ordi();
		     }
			 else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi)
		     {
				 controleur.ordi();
		     }
	    }
		
		//else if(SwingUtilities.isRightMouseButton(event)) {
	            /** Bouton DROIT */
	    	
		boolean boutonSelectionne = false;
		int numBoutonSelectionne = 0;
		// On parcourt toutes les cases
		for(int i=0;i<Constantes.NB_CASES;i++)
		{
			
			// Si on a cliqué sur une case vide (postion i)
			if(event.getSource() == casesVide[i] && (actionEnCoursVaisseau==Constantes.PHASE_DE_JEU))
			{
				  System.out.println("clique sur caseVide num " + i);
				  
				  
				// Test si on est dans la premiere phase ou seconde
				if(phase == 1) // Premiere phase de jeu
				{
					//System.out.println("cmptVaiss["+tourDeJeu+"] = " + cmptVaiss[tourDeJeu]);
					System.out.println("tourDeJeu =" + tourDeJeu);
					// Test a enlever
					if(cmptVaiss[faction[tourDeJeu]] < Constantes.NB_VAISSEAUX/2)
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
					
				} // Fin de la condition phase 1
				else if(phase == 2) // Test si on est dans la 2 eme phase
				{
					// On deplace le pion selectionné dans cette case
					for(int indice=0;indice<Constantes.NB_VAISSEAUX/2;indice++)
					{
						if(vaisseau[faction[tourDeJeu]][indice].isSelectionne())
						{
							// On cherche le numero de la case initiale
							for(int debut=0;debut<Constantes.NB_CASES;debut++)
							{
								if(plateau[debut]==vaisseau[faction[tourDeJeu]][indice])
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
				if(actionEnCoursVaisseau==Constantes.PHASE_DE_CHOIX_CIBLE && event.getSource() == vaisseau[faction[tourAutreJoueur]][i])
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
					
					
					
					// On recherche le numero de la case
					for(int c=0;c<Constantes.NB_CASES;c++)
					{
						if(plateau[c]==vaisseau[faction[tourAutreJoueur]][i])
						{
							// On stock la position du vaisseau visé
							positionDuVaissVise = c;
						}
					}
					
					controleur.RetirerPiece(positionDuVaissVise, tourAutreJoueur+1);
					
					
					
					
				} 
				//else if(event.getSource() == vaisseau[tourDeJeu][i])
				//&& SwingUtilities.isRightMouseButton(event)
				// Si on est dans la seconde phase
				else if(phase == 2 && event.getSource() == vaisseau[faction[tourDeJeu]][i] 
						&& SwingUtilities.isLeftMouseButton(event) && actionEnCoursVaisseau==Constantes.PHASE_DE_JEU)
				{ // Si on clique sur un de nos vaisseau, on le selectionne pour le deplacer
					
					// On test si le bouton a été sélectionné
					if(vaisseau[faction[tourDeJeu]][i].isSelectionne())
					{
						vaisseau[faction[tourDeJeu]][i].setSelectionne(false);
						// le remetre droit
					}
					else
					{
						// On deselectionne les autres vaisseaux
						for(int j=0;j<cmptVaiss[faction[tourDeJeu]];j++)
						{
							if(vaisseau[faction[tourDeJeu]][j].isSelectionne())
								vaisseau[faction[tourDeJeu]][j].setSelectionne(false);
						}
						// A MODIFIER
						vaisseau[faction[tourDeJeu]][i].setSelectionne(true);//vaisseau[i].setDeplacement(20);
						//mouseMoved(event);
						// A UTILISER QUAND ON VEUT SE DEPLACER (ou exploser)
						// construction d'un Thread en passant cette instance de Runnable en paramètre
						//Thread thread =  new Thread(vaisseau[faction[tourDeJeu]][i]) ;
						
				    	 // lancement de ce thread par appel à sa méthode start()
				    	
						//thread.start() ;
				    	 // cette méthode rend immédiatement la main
					}
					
					
				}
			}

		    panelJeu.setFocusable(true);
		    panelJeu.requestFocus();
	}
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
		if(actionEnCoursVaisseau==Constantes.PHASE_DE_CHOIX_CIBLE)
		{
			System.out.println("phase de tir activé !");
			for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
			{
				//System.out.println(tourDeJeu);
				if(vaisseau[faction[tourDeJeu]][i].getMoulin() == 2)
				{
					//vaisseau[i].setLocation(vaisseau[i].getX()+2, vaisseau[i].getY()+1);
					if(event.getSource() == this){
						vaisseau[faction[tourDeJeu]][i].setAngle(event.getX()-Constantes.LARGEUR_PANEL_WEST, event.getY()-Constantes.HAUTEUR_PANEL_TOP_BOT);
					}
					else if(event.getComponent().getParent() == panelPionsBot || event.getComponent().getParent() == panelPionsTop){
						vaisseau[faction[tourDeJeu]][i].setAngle(0,0);
						System.out.println("erger");
					}
					else
					{
						// On recuperer la position du composant par rapport a la fenetre
						// On lui ajoute la position de la souris par rapport au composant
						System.out.println("eventX = " + event.getComponent().getX());
						int x = event.getComponent().getX() + event.getX();
						int y = event.getComponent().getY() + event.getY();
						vaisseau[faction[tourDeJeu]][i].setAngle(x,y);
						
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
		// On met à jour le plateau
		plateau[position] = vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]];
		// On stock la position
		positionCaseVisee = position;
		
		// On met à jour l'état du vaisseau
		vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]].setEtat(Constantes.ET_PLAC_1);
		
		// On lance le thread
		thread =  new Thread(vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]]) ;
    	 // lancement de ce thread par appel à sa méthode start()
		thread.start() ;
		
		
		
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
				laser[indiceLaser] = new Laser(this, faction[tourDeJeu], plateau[c].getX(), plateau[c].getY(), indiceLaser);

				laser[indiceLaser].setSize(new Dimension(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE));
				//panelPlateauJeu.add(laser[indiceLaser]);
				panelPlateauJeu.add(laser[indiceLaser]);
				laser[indiceLaser].setLocation(plateau[c].getX(), plateau[c].getY());
				// On calcule et lui envoie le coef directeur
				int x1=laser[indiceLaser].getX();
				int y1=laser[indiceLaser].getY();
				
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

		

		actionEnCoursVaisseau=Constantes.PHASE_DE_TIR;
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
        int positionPlateauX=250-panelWest.getWidth();//event.getX(); // 280 pour ecart = 70
        int positionPlateauY=120-panelTop.getHeight();//Constantes.HAUTEUR_PANEL_TOP_BOT;//event.getY(); // 140 pour ecart = 70
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
		

		System.out.println("tab[0] = " + tab[0]);
		switch(tab[0])
		{
		 	// Placement
			case 1:
				//tourDeJeu=tab[1]-1;
				actionEnCoursVaisseau=Constantes.PHASE_DE_PLACEMENT;
				ajouterVaisseau(tab[2]);
				
				System.out.println("tourDejeu = " + tourDeJeu);
				break;
			// Deplacement
			case 2:
				actionEnCoursVaisseau=Constantes.PHASE_DE_DEPLACEMENT;
				deplacerVaisseau(tab[3], tab[4]);
				// On change le tour de jeu
				//changerTourDeJeu();
				break;
			// Moulin
			case 3:
				// Non obligatoire
				positionDuVaissVise = tab[5];
				actionEnCoursVaisseau = Constantes.PHASE_DE_TIR;
				this.setCursor(Cursor.getDefaultCursor());
				viserCible();
				tirerLasers();
				// On remet le curseur
				//this.setCursor(curseurTir);
				// On change le tour de jeu
				///////////changerTourDeJeu();
				break;
			// Placement+Moulin
			case 4:
				actionEnCoursVaisseau=Constantes.PHASE_DE_PLACEMENT;
				ajouterVaisseau(tab[2]);
				// test
				System.out.println("Moulin position : " + tab[2] + " , " + tab[6] + " et " + tab[7]);
				plateau[tab[2]].setMoulin(2);
				plateau[tab[6]].setMoulin(2);
				plateau[tab[7]].setMoulin(2);
				System.out.println("placement+moulin");
				// On stock la position du vaisseau ciblé par l'ordi
				if(modeDeJeu == Constantes.MODE_OVSO)
				{
					positionDuVaissVise = tab[5];
				}
				else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi)
				{
					positionDuVaissVise = tab[5];
				}
				// On attend un prochain clic si joueur
				break;
			case 5:
			// Deplacement+Moulin
				actionEnCoursVaisseau=Constantes.PHASE_DE_DEPLACEMENT;
				System.out.println("deplacement+moulin");
				System.out.println("Moulin position : " + tab[4] + " , " + tab[6] + " et " + tab[7]);
				deplacerVaisseau(tab[3],tab[4]);
				plateau[tab[4]].setMoulin(2);
				plateau[tab[6]].setMoulin(2);
				plateau[tab[7]].setMoulin(2);
				//actionEnCoursVaisseau=Constantes.PHASE_DE_TIR;
				if(modeDeJeu == Constantes.MODE_OVSO)
				{
					positionDuVaissVise = tab[5];
					this.setCursor(Cursor.getDefaultCursor());
				}
				else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi)
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
			case 7:
				System.out.println("case 7");
				raz();
		        for(int c=0;c<Constantes.NB_CASES;c++)
		        {
		        	panelPlateauJeu.add(casesVide[c]);
		        }
				initialisation();
				panelPlateauJeu.remove(boutonCommencerPartie);
				// Chargement
				tourDeJeu = tab[1]%2;
				if(tab[2]==1) phase=1;
				else phase = 2;
				// difficulte = tab[3];
				for(int i=0;i<Constantes.NB_VAISSEAUX;i++)
				{
					if(tab[5+i] == 1)
					{
						panelPionsTop.remove(vaisseau[0][cmptVaiss[0]]);
						panelPlateauJeu.add(vaisseau[0][cmptVaiss[0]]);
						Point p = casesVide[i].getLocation();
						/*panelPlateauJeu.remove(casesVide[positionCaseVisee]);
						panelPlateauJeu.add(casesVide[positionCaseVisee]);
						casesVide[positionCaseVisee].setLocation(p);*/

						// On positionne le vaisseau à l'endroit de la case
						vaisseau[0][cmptVaiss[0]].setLocation(p);//vaisseau[i].setDeplacement(20);
						
						// On met à jour le plateau
						plateau[i] = vaisseau[0][cmptVaiss[0]];
						cmptVaiss[0]++;
					}
					else if(tab[5+i] == 2)
					{
						panelPionsTop.remove(vaisseau[1][cmptVaiss[1]]);
						panelPlateauJeu.add(vaisseau[1][cmptVaiss[1]]);
						Point p = casesVide[i].getLocation();
						/*panelPlateauJeu.remove(casesVide[positionCaseVisee]);
						panelPlateauJeu.add(casesVide[positionCaseVisee]);
						casesVide[positionCaseVisee].setLocation(p);*/

						// On positionne le vaisseau à l'endroit de la case
						vaisseau[1][cmptVaiss[1]].setLocation(p);//vaisseau[i].setDeplacement(20);
						// On met à jour le plateau
						plateau[i] = vaisseau[1][cmptVaiss[1]];
						cmptVaiss[1]++;
					}
					
				}
				
				panelJeu.validate();
				panelJeu.repaint();
				
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
		// Si c'est aux rebelles de jouer
		if(faction[tourDeJeu]==0)
		{
			// On allume sa cocarde
			cocardeRebelle.setIcon(imgCocardeRebelle[1]);
			// On eteint l'autre
			cocardeEmpire.setIcon(imgCocardeEmpire[0]);
		}
		// Sinon c'est à l'empire de jouer
		else
		{
			// On allume sa cocarde
			cocardeRebelle.setIcon(imgCocardeRebelle[0]);
			// On eteint l'autre
			cocardeEmpire.setIcon(imgCocardeEmpire[1]);
		}
		

		// A l'ordi de jouer
		if(modeDeJeu == Constantes.MODE_OVSO)
		{
			controleur.ordi(); 
		}
		// A l'ordi de jouer
		else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi)
		{
			controleur.ordi(); 
		}
	}
	
	public void viserCible()
	{
		for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
		{
			if(vaisseau[faction[tourDeJeu]][i].getMoulin()==2)
			{
				int a = vaisseau[faction[tourDeJeu]][i].getMoulin();
				vaisseau[faction[tourDeJeu]][i].setAngle(plateau[positionDuVaissVise].getX()+Constantes.TAILLE_CASE/2
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

				reprendrePartieCliquable = false;
			}
			if(nbPionsRebelle<3)
			{
				cl.show(this, "VictoireEmpire");

				reprendrePartieCliquable = false;
			}
		}
		
	}

	public void threadPlacementPhase1Termine()
	{

		System.out.println("phase fin thread : " + actionEnCoursVaisseau);
		// On enleve le vaisseau de son panel d'origine
		if(faction[tourDeJeu]==0) // Tour des Rebelles
		{
			panelPionsTop.remove(vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]]);
			//panelPionsTop.validate();
		}
		else // Tour de l'empire
		{
			panelPionsBot.remove(vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]]);
			//panelPionsTop.repaint();
		}
		

		// On met à jour l'état du vaisseau
		vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]].setEtat(Constantes.ET_PLAC_2);
		
		
		// On l'ajoute dans le jeu
		panelPlateauJeu.add(vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]]);
		Point p = casesVide[positionCaseVisee].getLocation();
		panelPlateauJeu.remove(casesVide[positionCaseVisee]);
		panelPlateauJeu.add(casesVide[positionCaseVisee]);
		casesVide[positionCaseVisee].setLocation(p);
		
		
		// On positionne le vaisseau à l'endroit de la case
		vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]].setLocation(p);//vaisseau[i].setDeplacement(20);
		
		// On lance le thread
		thread =  new Thread(vaisseau[faction[tourDeJeu]][cmptVaiss[faction[tourDeJeu]]]) ;
    	 // lancement de ce thread par appel à sa méthode start()
		thread.start() ;
		
		// On ajoute 1 au compteur de vaisseau en fonction du joueur
		cmptVaiss[faction[tourDeJeu]]++;
		// On change de phase si tout le monde a joué
		if(cmptVaiss[0] + cmptVaiss[1] == Constantes.NB_VAISSEAUX)
		{
			phase = 2;
			cmptVaiss[0]=Constantes.NB_VAISSEAUX/2;
			cmptVaiss[1]=Constantes.NB_VAISSEAUX/2;
		}
				
		
	}
	
	public void threadPlacement2Termine()
	{
		System.out.println("Placement terminé");
		// Si moulin
		if(plateau[positionCaseVisee].getMoulin() == 2)
		{
			if(modeDeJeu == Constantes.MODE_OVSO)
			{
				actionEnCoursVaisseau=Constantes.PHASE_DE_TIR;
				//detruireVaisseau(tab[5]);
				viserCible();
				tirerLasers();
				// On remet le curseur
				this.setCursor(Cursor.getDefaultCursor());
			}
			else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi)
			{
				actionEnCoursVaisseau=Constantes.PHASE_DE_TIR;
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
				actionEnCoursVaisseau=Constantes.PHASE_DE_CHOIX_CIBLE;
				// On change le curseur en mode tir
				this.setCursor(curseurTir);
			}
		}
		else
		{
			actionEnCoursVaisseau = Constantes.PHASE_DE_JEU;
			// On change le tour de jeu
			changerTourDeJeu();
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

			
			// Mode ordi vs ordi
			if(modeDeJeu == Constantes.MODE_OVSO)
			{
				//actionEnCoursVaisseau = Constantes.PHASE_D_EXPLOSION;
				actionEnCoursVaisseau = Constantes.PHASE_DE_TIR;
				viserCible();
				tirerLasers();
				//detruireVaisseau(positionDuVaissVise);
			}
			// Si c'est à l'ordi de jouer et qu'il a fait un moulin 
			// (+ mode JvsIA activé)
			else if(modeDeJeu == Constantes.MODE_JVSO && tourDeJeu==tourOrdi) // true en atendant
			{
				//actionEnCoursVaisseau = Constantes.PHASE_D_EXPLOSION;
				actionEnCoursVaisseau = Constantes.PHASE_DE_TIR;
				viserCible();
				tirerLasers();
				//detruireVaisseau(positionDuVaissVise);
			}
			else
			{
				actionEnCoursVaisseau=Constantes.PHASE_DE_CHOIX_CIBLE;
				// On change le curseur en mode tir
				this.setCursor(curseurTir);
			}
		}
		else
		{
			actionEnCoursVaisseau = Constantes.PHASE_DE_JEU;
			changerTourDeJeu();
		}
		
		if(actionEnCoursVaisseau != Constantes.PHASE_DE_CHOIX_CIBLE)
		{
		}
		else
		{
			
		}
	}
	
	public void threadExplosionTermine(int num)
	{
		// Permet de savoir le numero du tour de l'autre joueur
		int tourAutreJoueur = tourDeJeu==0 ? 1 : 0;
		
		// On enleve le vaisseau
		panelPlateauJeu.remove(vaisseau[faction[tourAutreJoueur]][num]);
		
		testFinDePartie();
		
		// On remet droit les vaisseaux
		for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
		{
			if(vaisseau[faction[tourDeJeu]][i].getMoulin() == 2)
			{
	
				System.out.println("on replace le vaisseau");
				vaisseau[faction[tourDeJeu]][i].setAngle(0);
				vaisseau[faction[tourDeJeu]][i].setMoulin(1);
			}
		}
		actionEnCoursVaisseau = Constantes.PHASE_DE_JEU;
		changerTourDeJeu();
	}
	
	public void threadDeplacementLaserTermine(int i)
	{

		// On retire le laser du jeu
		//for(int l=0;l<3;l++)
		//{
			panelPlateauJeu.remove(laser[i]);
		//}
		panelJeu.repaint();
		panelJeu.validate();
		// Explosion
		if(actionEnCoursVaisseau==Constantes.PHASE_DE_TIR)
		{
			actionEnCoursVaisseau=Constantes.PHASE_D_EXPLOSION;
			System.out.println("Moulin : destruction du vaisseau");
			detruireVaisseau(positionDuVaissVise);
			//if(tourDeJeu==1){ // Si c'est le tour du joueur
				//controleur.RetirerPiece(positionDuVaissVise, tourAutreJoueur+1);
			//}
			//else // tour de l'ordi
			//{
				//detruireVaisseau(positionDuVaissVise);
			//}
		}
	}

	public void keyTyped(KeyEvent e) {

		
		repaint();
		
	    // Permet de limiter le nombre de caractere a 10 apres saisi
		if(e.getSource() == pseudo || e.getSource() == pseudo1 || e.getSource() == pseudo2)
		{

			JTextField tf = (JTextField) e.getSource();
			if(tf.getText().length() >= 10) {
				System.out.println("limite atteinte");
				try {
					tf.setText(tf.getText(0, 9));
				} catch (BadLocationException ble) { ble.printStackTrace(); }
			}
		}
		/*else
		{
			System.out.println(m + " " + l + " " + p);
			char c = e.getKeyChar();
			System.out.println("c = " + c);
			if(c == 'm'){ m =true;
			}
			else if(c == 'l') l = true;
			else if(c == 'p') p = true;
			
			if(m&&l&&p)
			{
				if(modePoney)
				{
					modePoney = false;
				}
				else
				{
					modePoney = true;
				}
			}
		}*/
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	//	if(panelMenu.isVisible() || panelJeu.isVisible())
		//{
			System.out.println(".");
			char c = e.getKeyChar();
			if(c == 'm') m =true;
			else if(c == 'l' && m) l = true;
			else if(c == 'p' && m && l)
			{
				System.out.println("sdfsdfd");
				if(modePoney>0)
				{
					modePoney = 0;
					repaint();
				}
				else
				{
					modePoney = 1;
					repaint();
				}
				
			}
			else if(c == ' ' && modePoney==1)
			{
				modePoney = 2;
				repaint();
			}
			else if(c == ' ' && modePoney==2)
			{
				modePoney = 1;
				repaint();
			}
			else
			{
				m=false;
				l=false;
			}
		}
		
		
	//}
	
	public void raz()
	{
		
		System.out.println("On remet tout à zero");
		// On enleve tous les vaisseaux du top, bot et jeu
		panelPionsTop.removeAll();
		panelPionsBot.removeAll();
		panelPlateauJeu.removeAll();
		
		// On réinitialise les attributs
		cmptVaiss[0] = 0;
		cmptVaiss[1]= 0;
		actionEnCoursVaisseau=Constantes.PHASE_DE_JEU;
		tourDeJeu = 0;
		phase = 1;
		
		// On ajoute les cases vides et reinitialise le plateau
        for(int c=0;c<Constantes.NB_CASES;c++)
        {
        	plateau[c]=null;
        } 
        
		//initialisation();
		
		// On place le bouon start
		panelPlateauJeu.add(boutonCommencerPartie);
		boutonCommencerPartie.setLocation(Constantes.ECART*2+250 + Constantes.TAILLE_CASE -22-Constantes.LARGEUR_PANEL_WEST
				, Constantes.ECART*2+120-Constantes.HAUTEUR_PANEL_TOP_BOT+Constantes.TAILLE_CASE+10);
		
		for(int j=0;j<2;j++)
        {
	        for(int i=0;i<Constantes.NB_VAISSEAUX/2;i++)
	        {
	        	//vaisseau[i] = new JButton(animation[1]);
	        	
	        	int a= j==0?1:0;
	        	a= j==0?0:1;
	        	
	        	//vaisseau[j][i] = new Vaisseau(this, a, i);// On met à jour l'état du vaisseau
	        	vaisseau[j][i].raz();
	
	        	/*vaisseau[j][i].setFocusPainted( false ); // enleve la bordure de l'image
	    		vaisseau[j][i].setBorderPainted(false); // enleve la bordure du bouton
	    		//vaisseau[i].setOpaque(false); // enleve la bordure du bouton
	    		vaisseau[j][i].setContentAreaFilled(false);
	        	//vaisseau[j][i].setSize(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE) ;*/
	    		//vaisseau[j][i].setPreferredSize(new Dimension(Constantes.TAILLE_CASE, Constantes.TAILLE_CASE));
	        	
	    		
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

		
		//panelPlateauJeu.
	}
	
	public void sauvegarder()
	{
				String adresseDuFichier ="";
				
	            int returnVal = fc.showSaveDialog(Panneau.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fc.getSelectedFile();

	                adresseDuFichier = file.getPath();
	                if((file.getPath().indexOf(".moulin"))==-1)
	                	adresseDuFichier = file.getPath()+".moulin";
	                
	                
	                // SERIALISE L'OBJET
		            controleur.sauvegarder(adresseDuFichier);
		            
	            } 
	            else 
	            {
	                System.out.println("Sauvegarde annulée par l'utilisateur");
	            }
	       
	}
	
	public void charger()
	{
		
			// GESTION FICHIER
	        //Handle open button action.
	            int returnVal = fc.showOpenDialog(Panneau.this);
	            String adresseDuFichier="";
	            
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            {
	                File file = fc.getSelectedFile();
	                adresseDuFichier = file.getPath();

	                if((file.getPath().indexOf(".moulin"))!=-1)
	                {
		                System.out.println("On charge le fichier " + adresseDuFichier);
						controleur.charger(adresseDuFichier);
	                }
	                else
	                {
		                System.out.println("Fichier incorrect");
	                }
	                	
				
	            } 
	            else 
	            {
	                System.out.println("Ouverture annulée par l'utilisateur");
	            }
	            
		}
}

