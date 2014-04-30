package moulin;

import java.util.Calendar;

public class Tuple {
	private String nomPartieSave;
	//static Date dateSave;
	private Plateau plateau;
	
	
	Tuple(String string, Plateau plat){
		nomPartieSave = string;
		plateau = plat;
	}
	
	public String getNom(){
		return nomPartieSave;
	}

	public Plateau getPlateau(){
		return plateau;
	}
}
