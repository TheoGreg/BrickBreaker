
public class Bille {

	// Attributs
	private int rayon;
	private int[] vitesse;
	private int[] position;
	
	// Constructeurs
	Bille(int r, int[] v, int[] pos){
		rayon = r;
		vitesse = v.clone();
		position = pos.clone();
	}
	
	Bille(int r, int[] pos){
		rayon = r;
		vitesse = new int[2];
		vitesse[0] = 0;
		vitesse[1] = -1;
		position = pos;	
	}
	
	// Méthodes	
	public int[] renvoyerPosition(){
		return(position);
	}

	public int[] renvoyerVitesse(){
		return(vitesse);
	}
	
	public int renvoyerRayon() {
		return(rayon);
	}
	
	public void deplacer() {
		position[0] = position[0] + vitesse[0];
		position[1] = position[1] + vitesse[1];
	}
	
	public void changeVitesse(int[] v) {
		vitesse[0] = v[0];
		vitesse[1] = v[1];
	}
	
	public void changePosition(int[] p) {
		position[0] = p[0];
		position[1] = p[1];
	}
	
	public void demiTour() {
		int a = - vitesse[0];
		int b = - vitesse[1];
		vitesse[0] = a;
		vitesse[1] = b;
	}
	
	
	//Huit points stratégiques de la bille après son déplacement
	
	public int[] MH() {
		int[] a = new int[2];
		a[0] = position[0];
		a[1] = position[1] - rayon;		
		return a;
	}
	
	public int[] MB() {
		int[] a = new int[2];
		a[0] = position[0];
		a[1] = position[1] + rayon;
		return a;
	}
	
	public int[] DM() {
		int[] a = new int[2];
		a[0] = position[0] + rayon;
		a[1] = position[1];
		return a;
	}
	
	public int[] GM() {
		int[] a = new int[2];
		a[0] = position[0] - rayon;
		a[1] = position[1];
		return a;
	}
	
	public double[] GH() {
		double[] a = new double[2];
		a[0] = position[0] - rayon/Math.sqrt(2);
		a[1] = position[1] - rayon/Math.sqrt(2);
		return a;
	}
	
	public double[] GB() {
		double[] a = new double[2];
		a[0] = position[0] - rayon/Math.sqrt(2);
		a[1] = position[1] + rayon/Math.sqrt(2);
		return a;
	}
	
	public double[] DH() {
		double[] a = new double[2];
		a[0] = position[0] + rayon/Math.sqrt(2);
		a[1] = position[1] - rayon/Math.sqrt(2);
		return a;
	}
	
	public double[] DB() {
		double[] a = new double[2];
		a[0] = position[0] + rayon/Math.sqrt(2);
		a[1] = position[1] + rayon/Math.sqrt(2);
		return a;
	}
	

}
