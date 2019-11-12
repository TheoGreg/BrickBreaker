import java.awt.Color;
import java.io.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ListIterator;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Graphique extends JPanel{

	// Attributs
	static Partie p;
	static Dimension screenSize;
	IHM ihm;
	boolean fin;
	boolean nextlevel;
	JTextField aff = new JTextField();
	
	// Constructeurs
	public Graphique(Partie partie, IHM i) {
		p = partie;
		this.ihm = i;
		screenSize = ihm.getContentPane().getSize();
		fin = false;
		nextlevel = false;
		
	}
	
	// Méthodes
	public void paintComponent(Graphics g) {
		
		//Fond noir de jeu
		g.setColor(Color.BLACK);
		g.fillRect(0,0,screenSize.width,screenSize.height);
		
		//Dessin des obstacles
		ListIterator <Obstacle> rep = p.niveauEnCours.grilleObstacles.listIterator(0);
		while (rep.hasNext()) {
			Obstacle ob = rep.next();
			int[] omega = ob.renvoyerPosition().clone();
			omega[0] = omega[0] - ob.largeur/2 ;
			omega[1] -= ob.hauteur/2;
			int[] b_prime = transfo(omega);
			g.setColor(Color.WHITE);
			g.fillRect(b_prime[0], b_prime[1], tLarge(ob.largeur), tHaut(ob.hauteur));
		}
		
		// Dessin des briques
		ListIterator <Brique> iter = p.niveauEnCours.grilleBriques.listIterator(0);
		while (iter.hasNext()) {
			Brique b = iter.next();
			int[] gamma = b.renvoyerPosition().clone();
			gamma[0] = gamma[0] - b.largeurBrique/2 ;
			gamma[1] -= b.hauteurBrique/2;
			int[] b_prime = transfo(gamma);
			g.setColor(Color.BLACK);
			g.drawRect(b_prime[0], b_prime[1], tLarge(b.largeurBrique), tHaut(b.hauteurBrique));
			
			//Brique Classic
			if (b.renvoyerType() == TypeBrique.Classic) {
				if (b.renvoyerCoups() == 0) {
					g.setColor(Color.decode("#04a301"));
				}
				if (b.renvoyerCoups() == 1) {
					g.setColor(Color.decode("#06d802"));
				}
			}
			
			//Brique Improved
			if (b.renvoyerType() == TypeBrique.ImprovedClassic) {
				if (b.renvoyerCoups() == 0) {
					g.setColor(Color.decode("#db0700"));
				}
				if (b.renvoyerCoups() == 1) {
					g.setColor(Color.decode("#f96800"));
				}
				if (b.renvoyerCoups() == 2) {
					g.setColor(Color.decode("#ffe900"));
				}
			}
			
			//Brique Steel
			if (b.renvoyerType() == TypeBrique.Steel) {
				if (b.renvoyerCoups() == 0) {
					g.setColor(Color.decode("#2800db"));
				}
				if (b.renvoyerCoups() == 1) {
					g.setColor(Color.decode("#323dff"));
				}
				if (b.renvoyerCoups() == 2) {
					g.setColor(Color.decode("#448cff"));
				}
				if (b.renvoyerCoups() == 3) {
					g.setColor(Color.decode("#00c7ff"));
				}
				if (b.renvoyerCoups() == 4) {
					g.setColor(Color.decode("#00ffff"));
				}
			}
		
			g.fillRect(b_prime[0], b_prime[1], tLarge(b.largeurBrique), tHaut(b.hauteurBrique));			
		}
		
		//Affichage du nombre de vies
		g.setColor(Color.WHITE);
		g.setFont(new Font("Roboto", Font.PLAIN, 15));
		g.drawString("Vies = " + p.renvoyerVies(), 10, 30);

		
		//Dessin des bonus
		ListIterator <Bonus> ind = p.niveauEnCours.bonusEnChute.listIterator(0);
		while (ind.hasNext()) {
			Bonus bo = ind.next();
			int[] eps = bo.renvoyerPosition().clone();
			eps[0] = eps[0] - bo.cote/2 ;
			eps[1] -= bo.cote/2;
			int[] b_prime = transfo(eps);
			if (bo.renvoyerType().equals("BigBarre")) {
				g.setColor(Color.RED);
				g.fillRect(b_prime[0], b_prime[1], tLarge(bo.cote), tHaut(bo.cote));
			}
			if (bo.renvoyerType().equals("UpLife")) {
				g.setColor(Color.WHITE);
				g.fillRect(b_prime[0], b_prime[1], tLarge(bo.cote), tHaut(bo.cote));				
			}
			if (bo.renvoyerType().equals("SlowBall")) {
				g.setColor(Color.BLUE);
				g.fillRect(b_prime[0], b_prime[1], tLarge(bo.cote), tHaut(bo.cote));	
			}
		}
		
		
		// Dessin de la bille
		g.setColor(Color.BLACK);
		int[] alpha = p.billeEnCours.renvoyerPosition().clone();
		alpha[0] -= p.billeEnCours.renvoyerRayon()/2;
		alpha[1] -= p.billeEnCours.renvoyerRayon()/2;
		int[] bille_prime = transfo(alpha);
		g.setColor(Color.decode("#f7db04"));
		g.fillOval(bille_prime[0] - tLarge(p.billeEnCours.renvoyerRayon()), bille_prime[1] - tLarge(p.billeEnCours.renvoyerRayon()), 2*tLarge(p.billeEnCours.renvoyerRayon()), 2*tLarge(p.billeEnCours.renvoyerRayon()));
		
		
		// Dessin de la barre
		g.setColor(Color.BLACK);
		int[] beta = p.barreEnCours.renvoyerPosition().clone();
		beta[0] -= p.barreEnCours.largeur/2;
		beta[1] -= p.barreEnCours.hauteur/2;
		int[] barre_prime = transfo(beta);
		g.drawRect(barre_prime[0], barre_prime[1], tLarge(p.barreEnCours.largeur), tHaut(p.barreEnCours.hauteur));
		g.setColor(Color.decode("#686864"));
		g.fillRect(barre_prime[0], barre_prime[1], tLarge(p.barreEnCours.largeur), tHaut(p.barreEnCours.hauteur));
		
		//Passage niveau
		if (nextlevel) {
			aff.setText(p.niveauEnCours.renvoyerNom().substring(0,p.niveauEnCours.renvoyerNom().length() - 4));
		    aff.setBackground(Color.RED);
		    aff.setForeground(Color.WHITE);
		    aff.setHorizontalAlignment(JTextField.CENTER);
		    aff.setFont(new Font("Roboto", Font.PLAIN, 70));
		    aff.setBounds(0,0,screenSize.width,screenSize.height);
		    this.add(aff); 
		    aff.setVisible(true);
		}
		
		if (!nextlevel) {
			aff.setText("");
		    aff.setVisible(false);		    
		}
		
		
		//Jeu perdu 
		if (fin) {
		    JTextField area = new JTextField("GAME OVER");
		    area.setBackground(Color.BLACK);
		    area.setForeground(Color.WHITE);
		    area.setHorizontalAlignment(JTextField.CENTER);
		    area.setFont(new Font("Roboto", Font.PLAIN, 70));
		    area.setBounds(0,0,screenSize.width,screenSize.height);
		    this.add(area); 
		}
	}
	
	// Adaptation de la taille du jeu de 1000 * 1000 à la qualité de l'écran
	
	public static int[] transfo(int[] b) {
		int[] c = new int[2];
		c[0] = (int) ( (double) (b[0] * screenSize.width) / (double) p.limiteDroite );
		c[1] = (int) ( (double) (b[1] * screenSize.height) / (double) p.limiteHaut );
		return(c);
	}
	
	public static int tLarge(int b) {
		return ((int) ( (double) (b * screenSize.width) / (double) p.limiteDroite) );
	}
	
	public static int tHaut(int b) {
		return ((int) ( (double) (b * screenSize.height) / (double) p.limiteHaut) );
	}
	
	
}
