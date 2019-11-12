import java.util.*;
import java.io.*;

// Contient le type  TypeBrique intrinsèquement lié à la classe Brique

enum TypeBrique{
	Classic, ImprovedClassic, Steel
}

public class Brique {
	
	// Paramètres initiaux
	public final int hauteurBrique = 40;
	public final int largeurBrique = 100;

	// Attributs
	private int limiteCoups;
	private int coupsRecus;
	private int[] position; 
	private Bonus bonus;
	private TypeBrique type;
		// Limites Brique
	public int gauche;
	public int droite;
	public int bas;
	public int haut;
	
	
	// Constructeurs
	Brique(String a, TypeBrique t, int[] pos){
		type = t;
		bonus = new Bonus(a, pos);
		position = pos;
		coupsRecus = 0;
		gauche = position[0] - largeurBrique/2;
		droite = position[0] + largeurBrique/2;
		bas = position[1] - hauteurBrique/2;
		haut = position[1] + hauteurBrique/2;
		
		// Solidité de la brique
		if (t == TypeBrique.Classic) {
			limiteCoups = 2;
		}
		else if (t == TypeBrique.ImprovedClassic) {
			limiteCoups = 3;
		}
		else if (t == TypeBrique.Steel) {
			limiteCoups = 5;
		}
	}
	
	Brique(TypeBrique t, int[] pos){
		type = t;
		position = pos;
		coupsRecus = 0;
		gauche = position[0] - largeurBrique/2;
		droite = position[0] + largeurBrique/2;
		bas = position[1] + hauteurBrique/2;
		haut = position[1] - hauteurBrique/2;
		
		// Solidité de la brique
		if (t == TypeBrique.Classic) {
			limiteCoups = 2;
		}
		else if (t == TypeBrique.ImprovedClassic) {
			limiteCoups = 3;
		}
		else if (t == TypeBrique.Steel) {
			limiteCoups = 5;
		}
		
		// Bonus au hasard
		double a = Math.random();
		if (a < 0.4) {bonus = new Bonus("Without", position);}
		else if (a < 0.6) {bonus = new Bonus("UpLife", position);}
		else if (a < 0.8) {bonus = new Bonus("BigBarre", position);}
		else {bonus = new Bonus("SlowBall", position);}
	}
	
	Brique(int[] pos){
		this(TypeBrique.Classic, pos);		
	}
	
	// Méthodes
	
	public int[] renvoyerPosition(){
		return(position);
	}
	
	public int renvoyerCoups() {
		return(coupsRecus);
	}
	
	public Boolean estDetruite() {
		return(coupsRecus == limiteCoups);
	}
	
	public Bonus renvoyerBonus() {
		return(bonus);
	}
	
	public TypeBrique renvoyerType() {
		return(type);
	}
	
	public void coup() {
		coupsRecus++;
	}
	
}
