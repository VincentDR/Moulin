package moulin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;


public class Save implements Serializable
{
	private Vector<Tuple> plateaux;
	private  static  final  long serialVersionUID =  1351993981346723535L;
	
	/** Constructeur privé */
	private Save()
	{
		this.plateaux = new Vector <Tuple> ();
	}
 
 	public static Save getInstance () throws FileNotFoundException, IOException, ClassNotFoundException
 	{
 		File f = new File("sauvegarde.ser");
		if(f.length() > 0)//f.exists() && !f.isDirectory())
		{
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(f)) ;
			return (Save)ois.readObject() ;
		}
		else
		{
			return new Save ();
		}
 	}
 	
	/** Point d'accès pour l'instance unique du singleton */
	
	public boolean sauvegarder (String str)
	{
		if (!in_save (str))
		{
		//	this.plateaux.addElement (str);
			return true;
		}
		return false;
	}
	
	public boolean in_save (String str)
	{
		for (Tuple pla : this.plateaux)
		{
			if (pla.getNom() == str)
				return true;
		}
		return false;
	}
	
	public Plateau getSave(String nomPartie)
	{
		for (Tuple pla : this.plateaux)
		{
			if (pla.getNom() == nomPartie)
			{
				this.plateaux.remove (pla);
				return pla.getPlateau();
			}
		}
		return null;
		
	}
	
	public Plateau getPlateau(String nomPartie){
		int i = 0; //Parcours du vecteur de plateau
		boolean trouve =false;
		while(trouve == false && i<plateaux.size()){
			if(plateaux.elementAt(i).getNom() == nomPartie){
				trouve = true;
			}
			i++;
		}
		Plateau plateau = new Plateau();
		if(trouve == true){
			plateau = plateaux.elementAt(i).getPlateau();
		}
		return plateau;
	}
	
	public void finalize()
    {
       File fichier =  new File("sauvegarde.ser");

		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichier, false));
			try {
				oos.writeObject(this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 // sérialization de l'objet
		
    }
}
