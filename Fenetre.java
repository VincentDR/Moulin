package moulin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Fenetre {


	//private Controleur controleur;
	protected Panneau panneau;
	
	public Fenetre(Controleur controleur)
	{
		
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width  = (int)dimension.getWidth();
		int height = (int)dimension.getHeight();
		
		JFrame f=new JFrame("Ton moulin va trop vite");
		//setTitle("Ton moulin va trop vite");
		f.setSize(1000,750+25);
		//setSize(1100,870); Chick mind
		f.setLocation(width/2-f.getWidth()/2,height/2-f.getHeight()/2); // par default, coins superieur gauche de l'ecran
		
		// Choix des panneaux
		CardLayout cl = new CardLayout();
		JPanel content = new JPanel();
		//Liste des noms de nos conteneurs pour la pile de cartes
		String[] listContent = {"CARD_1", "CARD_2", "CARD_3"};
		// http://fr.openclassrooms.com/informatique/cours/apprenez-a-programmer-en-java/positionner-son-composant-les-layout-managers
		// a metre dans le panel
		//On crÃ©e 2 conteneurs de couleur diffÃ©rente
	    JPanel card1 = new JPanel();
	    card1.setBackground(Color.blue);        
	    JPanel card2 = new JPanel();
	    card2.setBackground(Color.red);    
	    
		// Icone
		//Toolkit tk = Toolkit.getDefaultToolkit();
		//Image img = tk.getImage("Images/Autre/ico.png");
		//this.setIconImage(img);
		
		panneau = new Panneau(controleur);
		
		/*panneau.setBackground(Color.red);
		panneau2.setBackground(Color.blue);
		//panneau.setLocationRelativeTo(null);    
		panneau.setLayout(new BorderLayout());
		//panneau.setLayout(new GridLayout(rows, columns));
		panneau.add(panneau2);*/
		//panneau2.setSize(new Dimension(1000,1000));
		//panneau2.setPreferredSize(new Dimension(5000,5000));
		
		//this.getContentPane().removeAll();
		//this.getContentPane().add(panneau, BorderLayout.CENTER);
		f.getContentPane().add(panneau, BorderLayout.CENTER);
	 // this.getContentPane().removeAll();
	    
	    //this.getContentPane().add(panneau, BorderLayout.CENTER);
	    f.setVisible(true);
	    
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture correct de la fenetre	
	
	}
	
	public Panneau getPanneau()
	{
		return panneau;
	}

}
