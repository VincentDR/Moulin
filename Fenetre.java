package moulin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Fenetre{

	//private Controleur controleur;
	protected Panneau panneau;
	private JFrame f;
	public Fenetre(Controleur controleur)
	{
		
		
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width  = (int)dimension.getWidth();
		int height = (int)dimension.getHeight();
		
		f=new JFrame("Ton moulin va trop vite");
		//setTitle("Ton moulin va trop vite");
		f.setSize(1000,750+25);
		f.setResizable(false);
		//setSize(1100,870); Chick mind
		f.setLocation(width/2-f.getWidth()/2,height/2-f.getHeight()/2); // par default, coins superieur gauche de l'ecran
		

		panneau = new Panneau(controleur);

		//this.getContentPane().removeAll();
		f.getContentPane().add(panneau, BorderLayout.CENTER); 


		
	    f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture correct de la fenetre	
	
	}
	
	
	public static void main(String[] args)
	{
		Controleur controleur = new Controleur();
	}
	
	public Panneau getPanneau()
	{
		return panneau;
	}
	

}
