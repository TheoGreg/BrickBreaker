import java.util.*;
import java.io.*;

// bonus dont on devra gérer la chute

public class Bonus {
	
	// Attributs
	private int[] position;
	private String type;
	private int vitesseChute;
	public int cote;
	
	// Constructeurs
	Bonus(String t, int[] pos){
		position = pos;
		type = t;
		vitesseChute = 1;
		cote = 40;
	}
	
	// Méthodes
	public String renvoyerType() {
		return(type);
	}
	
	public int[] renvoyerPosition() {
		return(position);
	}
	
	public void chute() {
		position[1] = position[1] + vitesseChute;
	}
	
	public int[] GH() {
		int[] a = new int[2];
		a[0] = position[0] - cote/2;
		a[1] = position[1] - cote/2;
		return(a);
	}
	
	public int[] DH() {
		int[] a = new int[2];
		a[0] = position[0] + cote/2;
		a[1] = position[1] - cote/2;
		return(a);
	}
	
	public int[] DB() {
		int[] a = new int[2];
		a[0] = position[0] + cote/2;
		a[1] = position[1] + cote/2;
		return(a);
	}
	
	public int[] GB() {
		int[] a = new int[2];
		a[0] = position[0] - cote/2;
		a[1] = position[1] + cote/2;
		return(a);
	}
		
}
