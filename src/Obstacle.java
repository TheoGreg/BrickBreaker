import java.util.*;

public class Obstacle {
	
	// Attributs
	private int[] position; 
	public int largeur;
	public int hauteur;
	// Limites Obstacle
	public int gauche;
	public int droite;
	public int bas;
	public int haut;
	
	// Constructeurs
	Obstacle(int[] pos, int l, int h){
		position = pos;
		largeur = l;
		hauteur = h;	
		gauche = position[0] - largeur/2;
		droite = position[0] + largeur/2;
		bas = position[1] + hauteur/2;
		haut = position[1] - hauteur/2;
	}
	
	Obstacle(int[] pos, int cote){
		//pour un carré
		position = pos;
		largeur = cote;
		hauteur = cote;
	}
	
	// Méthodes
	
	public int[] renvoyerPosition(){
		return(position);
	}
	
	public int renvoyerLargeur() {
		return(largeur);
	}
	
	public int renvoyerHauteur() {
		return(hauteur);
	}

}
