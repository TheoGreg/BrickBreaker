import java.util.*;
import java.io.*;

public class Barre {

	// Attributs
	public int[] position;
	public int largeur;
	public int hauteur;
	private int normeVitesse;

	
	// Constructeurs
	Barre(int h, int l, int normeV, int[] pos){
		position = pos;
		largeur = l;
		hauteur = h;
		normeVitesse = normeV;
	}
	
	// Méthodes
	public int[] renvoyerPosition() {
		return(position);
	}
	
	public int renvoyerLargeur() {
		return(largeur);
	}
	
	public int renvoyerHauteur() {
		return(hauteur);
	}
	
	public int renvoyerNormeVitesse() {
		return(normeVitesse);
	}
	
	public void changePosition(int[] p) {
		position[0] = p[0];
		position[1] = p[1];
	}
	
	public void deplacerGauche() {
		position[0] = position[0] - normeVitesse ;
	}
	
	public void deplacerDroite() {
		position[0] = position[0] + normeVitesse ;
	}
	
	public void deplacerBas() {
		position[1] = position[1] + normeVitesse ;
	}
	
	public void deplacerHaut() {
		position[1] = position[1] - normeVitesse ;
	}
	
	public int bas(){
		return(position[1] + hauteur/2);
	}
	
	public int haut(){
		return(position[1] - hauteur/2);
	}
	
	public int gauche(){
		return(position[0] - largeur/2);
	}
	public int droite(){
		return(position[0] + largeur/2);
	}
	
}
