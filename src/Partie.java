import java.util.*;
import java.awt.event.KeyListener;
import java.io.*;

public class Partie {

	// Paramètres initiaux de jeu 
	public static int limiteDroite = 1000;
	public static int limiteHaut = 1000;
	public static int rayonBille = 10;
	public static int largeurBarre = 80;
	public static int hauteurBarre = 20;
	public static int vitesseBarre = 40;
	private final static int[] posBarreInitiale = {500, 800};
	private final static int[] posBilleInitiale = {500, 700};
	private final static int[] vitBilleInitiale = {0, -4};
	private static int viesInitiales = 5;
	private static int vitesseChuteBonus = 1;


	// Attributs
	public int nombreVies;
	public Bille billeEnCours;
	public Barre barreEnCours;
	public Niveau niveauEnCours;
	private int[] positionInitialeBarre; 
	private int[] positionInitialeBille; 
	private int[] vitesseInitialeBille;

	// Constructeurs
	Partie(int vies, int[] posBarre, int[] posBille, int[] vBille) throws IOException{
		nombreVies = vies;
		positionInitialeBarre = posBarre.clone();
		positionInitialeBille = posBille.clone();
		vitesseInitialeBille = vBille.clone();
		niveauEnCours = new Niveau("Niveau_1.txt");
		billeEnCours = new Bille(rayonBille, vitesseInitialeBille, positionInitialeBille);
		barreEnCours = new Barre(hauteurBarre, largeurBarre, vitesseBarre, positionInitialeBarre);
	}

	// Méthodes
	public int renvoyerVies() {
		return(nombreVies);
	}

	public void perteDeVie() {
		nombreVies--;
	}

	public void gainDeVie() {
		nombreVies++;
	}
	
	//Donne le droit à la barre de bouger vers la gauche si elle n'est pas bloquée par les obstacles
	public boolean mouvementGauche() {
		ListIterator <Obstacle> rep = niveauEnCours.grilleObstacles.listIterator(0);
		boolean resultat = true;
		while (rep.hasNext()) {
			Obstacle ob = rep.next();
			if (barreEnCours.gauche() - ob.droite < vitesseBarre){
				if (barreEnCours.position[1] > ob.haut && barreEnCours.position[1] < ob.bas) {
				resultat = false;
				}
			}	
		}
		return(resultat);
	}
	
	//Donne le droit à la barre de bouger vers la droite si elle n'est pas bloquée par les obstacles
	public boolean mouvementDroite() {
		ListIterator <Obstacle> rep = niveauEnCours.grilleObstacles.listIterator(0);
		boolean resultat = true;
		while (rep.hasNext()) {
			Obstacle ob = rep.next();
			if (ob.gauche - barreEnCours.droite() < vitesseBarre) {
				if (barreEnCours.position[1] > ob.haut && barreEnCours.position[1] < ob.bas) {
					resultat = false;
				}
			}	
		}
		return(resultat);
	}
	
	//Donne le droit à la barre de bouger vers le haut si elle n'est pas bloquée par les obstacles
	public boolean mouvementHaut() {
		ListIterator <Obstacle> rep = niveauEnCours.grilleObstacles.listIterator(0);
		boolean resultat = true;
		while (rep.hasNext()) {
			Obstacle ob = rep.next();
			if (ob.bas - barreEnCours.haut() < vitesseBarre) {
				if(barreEnCours.position[0] > ob.gauche && barreEnCours.position[0] < ob.droite){
						resultat = false;
				}
			}	
		}
		return(resultat);
	}
	
	//Donne le droit à la barre de bouger vers le haut si elle n'est pas bloquée par les obstacles
	public boolean mouvementBas() {
		ListIterator <Obstacle> rep = niveauEnCours.grilleObstacles.listIterator(0);
		boolean resultat = true;
		while (rep.hasNext()) {
			Obstacle ob = rep.next();
			if (ob.haut - barreEnCours.bas() < vitesseBarre) {
				if (barreEnCours.position[0] > ob.gauche && barreEnCours.position[0] < ob.droite){
						resultat = false;
				}
			}	
		}
		return(resultat);
	}
	
	

	// Gestion des contacts Bille - Briques : renvoie false si changement de vitesse de la bille
	public void billeBriques() {

		// Décomposition de la bille
		//   2      3      4
		//   1             5
		//   8      7      6    

		// Parcours des briques pour vérifier les contacts
		ListIterator <Brique> iter = niveauEnCours.grilleBriques.listIterator(0);
		int contact = 0;

		while (iter.hasNext() && contact == 0) {
			Brique b = iter.next();

			// Étude des contacts avec les 8 points de la bille

			// Contact GaucheHaut de la bille
			double[] q = billeEnCours.GH();
			if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
				contact = 2;
				b.coup();
				if (b.estDetruite()) {
					niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
					niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
				}
			}

			// Contact DroiteHaut de la bille
			if (contact == 0) {
				q = billeEnCours.DH();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 4;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact GaucheBas de la bille
			if (contact == 0) {
				q = billeEnCours.GB();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 8;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact DroiteBas de la bille
			if (contact == 0) {
				q = billeEnCours.DB();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 6;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact haut de la bille
			int[] p = billeEnCours.MH();
			if (contact == 0) {
				if (p[1] <= b.bas && p[1] >= b.haut && p[0] <= b.droite && p[0] >= b.gauche) {
					contact = 3;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact bas de la bille
			if (contact == 0) {
				p = billeEnCours.MB();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 7;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact droite de la bille
			if (contact == 0) {
				p = billeEnCours.DM();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 5;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}

			// Contact gauche de la bille
			if (contact == 0) {
				p = billeEnCours.GM();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 1;
					b.coup();
					if (b.estDetruite()) {
						niveauEnCours.bonusEnChute.add(b.renvoyerBonus());
						niveauEnCours.grilleBriques.remove(iter.nextIndex()-1);
					}
				}
			}
		}

			// Mise à jour de la vitesse 

			// Rencontre d'un coin
			if ((((contact == 2) || (contact == 4))) || ((contact == 8) || (contact == 6))) {
				billeEnCours.demiTour();
			}

			// Contact Classique
			// Gauche - Droite
			if ((contact == 1) || (contact == 5)) {
				int[] nv = {- billeEnCours.renvoyerVitesse()[0], billeEnCours.renvoyerVitesse()[1]};
				billeEnCours.changeVitesse(nv);
			}

			// Haut - Bas
			if ((contact == 3) || (contact == 7)) {
				int[] nv = {billeEnCours.renvoyerVitesse()[0], - billeEnCours.renvoyerVitesse()[1]};
				billeEnCours.changeVitesse(nv);
			}			

	}

	// Gestion des contacts Bille - Murs : renvoie false si changement de vitesse de la bille	
	public void billeMurs() {														

		// Décomposition de la bille
		//   2      3      4
		//   1             5
		//   8      7      6       

		int mur = 0;

		// Contact Gauche de la bille
		if (billeEnCours.GM()[0] <= 0) {
			mur = 1;
		}

		// Contact Droite de la bille
		if (mur == 0) {
			if (billeEnCours.DM()[0] >= limiteDroite) {
				mur = 5;
			}
		}

		// Contact Haut de la bille
		if (mur == 0) {
			if (billeEnCours.MH()[1] <= 0) {
				mur = 3;
			}
		}

		// Contact Bas de la bille
		if (mur == 0) {
			if (billeEnCours.MB()[1] >= limiteHaut) {
				mur = 7;
				perteDeVie();
				billeEnCours.changeVitesse(vitBilleInitiale);
				billeEnCours.changePosition(posBilleInitiale);
				barreEnCours.changePosition(posBarreInitiale);
			}
		}

		// Mise à jour de la vitesse

		if ((mur == 1)||(mur == 5)) {
			int[] nv = {- billeEnCours.renvoyerVitesse()[0], billeEnCours.renvoyerVitesse()[1]};
			billeEnCours.changeVitesse(nv);
		}

		if (mur == 3) {
			int[] nv = {billeEnCours.renvoyerVitesse()[0], - billeEnCours.renvoyerVitesse()[1]};
			billeEnCours.changeVitesse(nv);
		}

	}


	// Gestion des contacts Bille - Obstacles
	public void billeObstacles() {
		// Décomposition de la bille
		//   2      3      4
		//   1             5
		//   8      7      6    

		// Parcours des briques pour vérifier les contacts
		ListIterator <Obstacle> iter = niveauEnCours.grilleObstacles.listIterator(0);
		int contact = 0;

		while (iter.hasNext() && contact == 0) {
			Obstacle b = iter.next();

			// Étude des contacts avec les 8 points de la bille
			// Contact GaucheHaut de la bille
			double[] q = billeEnCours.GH();
			if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
				contact = 2;
			}

			// Contact DroiteHaut de la bille
			if (contact == 0) {
				q = billeEnCours.DH();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 4;
				}
			}

		    // Contact GaucheBas de la bille
			if (contact == 0) {
				q = billeEnCours.GB();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 8;
				}
			}

			// Contact DroiteBas de la bille
			if (contact == 0) {
				q = billeEnCours.DB();
				if (((q[1] <= b.bas) && (q[1] >= b.haut)) && ((q[0] <= b.droite) && (q[0] >= b.gauche))) {
					contact = 6;
				}
			}

			// Contact haut de la bille
			int[] p = billeEnCours.MH();
			if (contact == 0) {
				if (p[1] <= b.bas && p[1] >= b.haut && p[0] <= b.droite && p[0] >= b.gauche) {
					contact = 3;
				}
			}

			// Contact bas de la bille
			if (contact == 0) {
				p = billeEnCours.MB();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 7;						
				}
			}

			// Contact droite de la bille
			if (contact == 0) {
				p = billeEnCours.DM();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 5;
				}
			}

			// Contact gauche de la bille
			if (contact == 0) {
				p = billeEnCours.GM();
				if (((p[1] <= b.bas) && (p[1] >= b.haut)) && ((p[0] <= b.droite) && (p[0] >= b.gauche))) {
					contact = 1;							
				}
			}
		}

			// Mise à jour de la vitesse 

			// Rencontre d'un coin
			if ((((contact == 2) || (contact == 4))) || ((contact == 8) || (contact == 6))) {
				billeEnCours.demiTour();
			}

			// Contact Classique
			// Gauche - Droite
			if ((contact == 1) || (contact == 5)) {
				int[] nv = {- billeEnCours.renvoyerVitesse()[0], billeEnCours.renvoyerVitesse()[1]};
				billeEnCours.changeVitesse(nv);
			}

			// Haut - Bas
			if ((contact == 3) || (contact == 7)) {
				int[] nv = {billeEnCours.renvoyerVitesse()[0], - billeEnCours.renvoyerVitesse()[1]};
				billeEnCours.changeVitesse(nv);
			}			
	}

	// Gestion des contacts Bille - Barre
	public void billeBarre() {  											
		int contact = 0;

		// Décomposition de la bille
		//   2      3      4
		//   1             5
		//   8      7      6      

		// Étude des contacts avec les 8 points de la bille
		// Contact GaucheHaut de la bille

		// Contact haut de la bille
		int[] p = billeEnCours.MH();
		if (p[1] <= barreEnCours.bas() && p[1] >= barreEnCours.haut() && p[0] <= barreEnCours.droite() && p[0] >= barreEnCours.gauche()) {
			contact = 3;
		}

		// Contact bas de la bille
		if (contact == 0) {
			p = billeEnCours.MB();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				contact = 7;
			}
		}

		// Contact droite de la bille
		if (contact == 0) {
			p = billeEnCours.DM();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				contact = 5;
			}
		}

		// Contact gauche de la bille
		if (contact == 0) {
			p = billeEnCours.GM();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				contact = 1;
			}
		}

		// Contact GaucheHaut de la bille
		if (contact == 0) {
			double[] q = billeEnCours.GH();
			if (((q[1] <= barreEnCours.bas()) && (q[1] >= barreEnCours.haut())) && ((q[0] <= barreEnCours.droite()) && (q[0] >= barreEnCours.gauche()))) {
				contact = 2;
			}
		}

		// Contact DroiteHaut de la bille
		if (contact == 0) {
			double[] q = billeEnCours.DH();
			if (((q[1] <= barreEnCours.bas()) && (q[1] >= barreEnCours.haut())) && ((q[0] <= barreEnCours.droite()) && (q[0] >= barreEnCours.gauche()))) {
				contact = 4;
			}
		}

		// Contact GaucheBas de la bille
		if (contact == 0) {
			double[] q = billeEnCours.GB();
			if (((q[1] <= barreEnCours.bas()) && (q[1] >= barreEnCours.haut())) && ((q[0] <= barreEnCours.droite()) && (q[0] >= barreEnCours.gauche()))) {
				contact = 8;
			}
		}

		// Contact DroiteBas de la bille
		if (contact == 0) {
			double[] q = billeEnCours.DB();
			if (((q[1] <= barreEnCours.bas()) && (q[1] >= barreEnCours.haut())) && ((q[0] <= barreEnCours.droite()) && (q[0] >= barreEnCours.gauche()))) {
				contact = 6;
			}
		}

		// Mise à jour de la vitesse 

		// Rencontre d'un coin
		if ((((contact == 2) || (contact == 4))) || ((contact == 8) || (contact == 6))) {
			billeEnCours.demiTour();
		}

		// Contact Classique
		// Gauche - Droite
		if ((contact == 1) || (contact == 5)) {
			int[] nv = {- billeEnCours.renvoyerVitesse()[0], billeEnCours.renvoyerVitesse()[1]};
			billeEnCours.changeVitesse(nv);
		}

		// Haut - Bas : Déviation de la balle
		if ((contact == 3) || (contact == 7)) {
			int[] nv = new int[] {1,1};
			if (billeEnCours.renvoyerPosition()[0] >= barreEnCours.renvoyerPosition()[0] - barreEnCours.renvoyerLargeur()/4 && billeEnCours.renvoyerPosition()[0] <= barreEnCours.renvoyerPosition()[0] + barreEnCours.renvoyerLargeur()/4) {
				nv = new int[] {billeEnCours.renvoyerVitesse()[0], - billeEnCours.renvoyerVitesse()[1]};
			}
			if (billeEnCours.renvoyerPosition()[0] <= barreEnCours.renvoyerPosition()[0] - barreEnCours.renvoyerLargeur()/4) {
				nv = new int[] {billeEnCours.renvoyerVitesse()[0] - 1, - billeEnCours.renvoyerVitesse()[1]};
			}
			if (billeEnCours.renvoyerPosition()[0] >= barreEnCours.renvoyerPosition()[0] + barreEnCours.renvoyerLargeur()/4) {
				nv = new int[] {billeEnCours.renvoyerVitesse()[0] + 1, - billeEnCours.renvoyerVitesse()[1]};
			}
			billeEnCours.changeVitesse(nv);
		}			


	}
	
	
	public void gestionBonus() {
		ListIterator <Bonus> iter = niveauEnCours.bonusEnChute.listIterator(0);
		while (iter.hasNext()) {
			Bonus b = iter.next();
			b.chute();
			boolean touch = false;
			
			//Coin DB du Bonus
			int[] p = b.DB();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				touch = true;
			}
			
			//Coin GB du Bonus
			p = b.GB();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				touch = true;
			}
			
			//Coin DH du Bonus
			p = b.DH();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				touch = true;
			}
			
			//Coin GH du Bonus
			p = b.GH();
			if (((p[1] <= barreEnCours.bas()) && (p[1] >= barreEnCours.haut())) && ((p[0] <= barreEnCours.droite()) && (p[0] >= barreEnCours.gauche()))) {
				touch = true;
			}
			
			//Gestion des Bonus
			if (touch) {
				if (b.renvoyerType().equals("UpLife")) {
					gainDeVie();
				}
				if (b.renvoyerType().equals("BigBarre")) {
					barreEnCours.largeur = 2*largeurBarre;
				}
				if (b.renvoyerType().equals("SlowBall")) {
					int[] lent = new int[] {0,-2};
					billeEnCours.changeVitesse(lent);
				}
				niveauEnCours.bonusEnChute.remove(iter.nextIndex()-1);
			}	
		}
	}

	public void actualiser() {		
		billeBriques();
		billeMurs();
		billeObstacles();
		billeBarre();
		gestionBonus();
		billeEnCours.deplacer();
	}



	// Mise en place du jeu
	public static void main (String[] args) throws IOException, InterruptedException {
		// Création de partie		
		Partie jeu = new Partie(viesInitiales, posBarreInitiale, posBilleInitiale, vitBilleInitiale);
		IHM ihm = new IHM(jeu);

		ihm.setVisible(true);

		while (jeu.nombreVies != 0) {
			jeu.actualiser();
			ihm.dessin.repaint();
			Thread.sleep(10);

			// Passage au niveau suivant 
			if (jeu.niveauEnCours.grilleBriques.isEmpty()) {
				jeu.niveauEnCours = new Niveau(jeu.niveauEnCours.renvoyerSuivant());
				ihm.setTitle(jeu.niveauEnCours.renvoyerNom().substring(0,jeu.niveauEnCours.renvoyerNom().length() - 4));

			//Écran de passage de niveau
			ihm.dessin.nextlevel = true;
			ihm.dessin.revalidate();
			ihm.dessin.repaint();
			Thread.sleep(3000);
			ihm.dessin.nextlevel = false;
			ihm.dessin.revalidate();
			ihm.dessin.repaint();


			//Réinitialisation des paramètres initiaux
			jeu.billeEnCours.changeVitesse(vitBilleInitiale);
			jeu.billeEnCours.changePosition(posBilleInitiale);
			jeu.barreEnCours.changePosition(posBarreInitiale);	
			jeu.barreEnCours.largeur = largeurBarre;
			}
		}

		//Fin de la partie	
		ihm.dessin.fin = true;
		ihm.dessin.repaint();
	}


}
