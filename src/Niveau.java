import java.util.*;
import java.io.*;

// gros travail sur la lecture du fichier qui crée le niveau

public class Niveau {

	// Attributs
	private String nomDeFichier; // nom du fichier texte contenant les informations du niveau
	private String suivant; // nom du fichier texte contenant les informations du niveau suivant
	public LinkedList<Brique> grilleBriques;
	public LinkedList<Obstacle> grilleObstacles;
	public LinkedList<Bonus> bonusEnChute;
	
	// Constructeurs
	Niveau(String nom) throws IOException{
		nomDeFichier = nom;
		grilleBriques = new LinkedList<Brique>();
		grilleObstacles = new LinkedList<Obstacle>();
		bonusEnChute = new LinkedList<Bonus>();
		
		FileReader fichier = new FileReader(nom);
		Scanner sc = new Scanner(fichier);
		suivant = sc.next();
		while (sc.hasNextLine()) {
			
			//Type de la brique
			int t = sc.nextInt();
			
			//Position de la brique
			int[] pos = new int[2];
			pos[0] = sc.nextInt();
			pos[1] = sc.nextInt();
			
			//Ajout de la brique ou de l'obstacle à la grille
			if (t==0) {
				int l = sc.nextInt();
				int h = sc.nextInt();
				grilleObstacles.add(new Obstacle(pos, l, h));
			}
			if (t==1) {
				grilleBriques.add(new Brique(pos));
			}
			if (t==2) {
				grilleBriques.add(new Brique(TypeBrique.ImprovedClassic, pos));
			}
			if (t==3) {
				grilleBriques.add(new Brique(TypeBrique.Steel, pos));
			}
			
		}
		
		fichier.close();
				
	}
	
	
	// Méthodes
	
	public String renvoyerSuivant() {
		return(suivant);
	}
	
	public String renvoyerNom() {
		return(nomDeFichier);
	}
	
	public LinkedList <Brique> renvoyerGrilleBriques(){
		return(grilleBriques);
	}
	
	public LinkedList <Obstacle> renvoyerGrilleObstacles(){
		return(grilleObstacles);
	}
	
	
}
