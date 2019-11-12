import java.awt.Color;
import java.awt.Label;
import java.io.*;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class IHM extends JFrame implements KeyListener {
	
	// Attributs
	Partie p;
	Graphique dessin;
	
    // Constructeurs
    public IHM(Partie partie) {
    		p = partie;
    		this.setTitle(p.niveauEnCours.renvoyerNom().substring(0,p.niveauEnCours.renvoyerNom().length() - 4));
    		Toolkit tk = Toolkit.getDefaultToolkit();
    		int xSize = ((int) tk.getScreenSize().getWidth());
    		int ySize = ((int) tk.getScreenSize().getHeight());
    		this.setSize(xSize,ySize);
    		this.setVisible(true);
    		dessin = new Graphique(p, this);
    		this.add(dessin);
    		
    		addKeyListener(this);

    		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        		if (p.mouvementDroite()) {
        			p.barreEnCours.deplacerDroite();
        		}
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        		if (p.mouvementGauche()) {
        			p.barreEnCours.deplacerGauche();
        		}
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        		if (p.mouvementHaut()) {
        			p.barreEnCours.deplacerHaut();
        		}
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        		if (p.mouvementBas()) {
        			p.barreEnCours.deplacerBas();
        		}
        } 
    }
    
    public void keyTyped(KeyEvent e) { 	
    }
    
    public void keyReleased(KeyEvent e) {    
    }
    
}
