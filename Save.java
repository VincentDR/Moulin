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
	private Vector<Plateau> plateaux;
	private  static  final  long serialVersionUID =  1351993981346723535L;
	
	/** Constructeur privé */
	private Save()
	{
		this.plateaux = new Vector <Plateau> ();
	}
 
 	public static Save getInstance () throws FileNotFoundException, IOException, ClassNotFoundException
 	{
 		File f = new File("savegarde.ser");
		if(f.exists() && !f.isDirectory())
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
	
	public boolean sauvegarder (Plateau plateau)
	{
		if (!in_save (plateau))
		{
			this.plateaux.addElement (plateau);
			return true;
		}
		return false;
	}
	
	public boolean in_save (Joueur j)
	{
		for (Plateau pla : this.plateaux)
		{
			if (pla.isJoueur (j))
				return true;
		}
		return false;
	}
	
	public Plateau getSave(Joueur joueur)
	{
		for (Plateau pla : this.plateaux)
		{
			if (pla.isJoueur (joueur))
			{
				this.plateaux.remove (pla);
				return pla;
			}
		}
		return null;
		
	}
	
	public void finalize()
    {
       File fichier =  new File("savegarde.ser");

		 // ouverture d'un flux sur un fichier
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichier));
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
