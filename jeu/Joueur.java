package jeu;

import java.util.*;

public class Joueur {
	
	// ATTRIBUTS ----------------------------------------------------------------------------------
	
	private static final int MAX_CARTES_MAIN = 6;
		
	private String nom;
	
	// Ces attributs symbolisent le haut des piles ascendante et descendante du joueur.
	// Ce ne sont que des int, elles ne mémorisent pas les cartes posées en-dessous de la pile.
	private int pileAscendante;
	private int pileDescendante;
	
	// Attribue une pioche à un joueur.
	private Pioche pioche = new Pioche();
	
	// Symbolise les cartes que le joueur à en main, sous forme de List d'Integer.
	// Ces Integer sont donc les cartes.
	private ArrayList<Integer> cartesMain = new ArrayList<Integer>();
	
	// CONSTRUCTEUR -------------------------------------------------------------------------------
	
	public Joueur(String nom) {
		this.nom = nom;
		initBase();
		piocheCarteDepart();
		trieMain();
	}
	
	// GETTERS ------------------------------------------------------------------------------------
	
	/**
	 * @brief Getters permettant de récupérer la pioche du Joueur.
	 * @return this.pioche
	 */
	public Pioche getPioche() {
		return this.pioche;
	}
	
	/**
	 * @brief Getters permettant de récupérer la main du Joueur.
	 * @return this.cartesMain
	 */
	public ArrayList<Integer> getCartesMain() {
		return this.cartesMain;
	}
	
	/**
	 * @brief Getters permettant de récupérer le nom du Joueur.
	 * @return this.nom
	 */
	public String getNom() {
		return this.nom;
	}
	
	// SETTERS ------------------------------------------------------------------------------------

	/**
	 * @brief Setters permettant de définir la carte sur le haut de la pile ascendante.
	 * @param[in] pileAscendante la carte à placer sur la pile ascendante.
	 */
	public void setPileAscendante(int pileAscendante) {
		this.pileAscendante = pileAscendante;
	}
	
	/**
	 * @brief Setters permettant de définir la carte sur le haut de la pile descendante.
	 * @param[in] pileDescendante la carte à placer sur la pile descendante.
	 */
	public void setPileDescendante(int pileDescendante) {
		this.pileDescendante = pileDescendante;
	}
	
	// METHODES -----------------------------------------------------------------------------------
	
	// INITIALISATION -----------------------------------------------------------------------------
	
	/**
	 * @brief Initialise la base du Joueur.
	 * Pose la carte 1 sur sa pile ascendante, et la carte 60 sur sa pile descendante.
	 */
	private void initBase() {
		pileAscendante = 1;
		pileDescendante = 60;
	}
	
	/**
	 * @brief Pioche les cartes de départ dans la main du Joueur.
	 * Les cartes ne sont pas forcément dans l'ordre alphabétique.
	 * @pre la pioche ne doit pas être vide.
	 */
	private void piocheCarteDepart() {
		assert (!pioche.piocheVide());
		
		for (int i = 0; i < MAX_CARTES_MAIN; i++) {
			piocheUneCarte();
		}
	}
	
	/**
	 * @brief Trie la main du Joueur.
	 */
	public void trieMain() {
		Collections.sort(cartesMain);
	}
	
	// POUR JOUER ---------------------------------------------------------------------------------
	
	/**
	 * @brief Pose une carte sur la pile ascendante du Joueur, en l'enlevant de sa main.
	 * @param[in] carteAjoutee la carte à poser sur la pile ascendante.
	 * @pre Le joueur peut jouer la carte sur la pile ascendante.
	 */
	public void poserCarteAscendante(int carteAjoutee) {
		assert peutJouerAscendant(carteAjoutee);
		
		pileAscendante = carteAjoutee;
		// Retire la carte de la main du Joueur.
		cartesMain.remove(cartesMain.indexOf(carteAjoutee));
	}
	
	/**
	 * @brief Pose une carte sur la pile descendante du Joueur, en l'enlevant de sa main.
	 * @param[in] carteAjoutee la carte à poser sur la pile descendante.
	 * @pre Le joueur peut jouer la carte sur la pile descendante.
	 */
	public void poserCarteDescendante(int carteAjoutee) {
		assert peutJouerDescendant(carteAjoutee);
		
		pileDescendante = carteAjoutee;
		// Retire la carte de la main du Joueur.
		cartesMain.remove(cartesMain.indexOf(carteAjoutee));
	}
	
	/**
	 * @brief Pioche une carte et la place dans la main du Joueur.
	 * @pre La pioche ne doit pas être vide.
	 */
	public void piocheUneCarte() {
		assert(!pioche.piocheVide());
		cartesMain.add(pioche.getPiocheCartes().pop());
	}
	
	// VERIFICATIONS ------------------------------------------------------------------------------
	
	/**
	 * @brief Le joueur peut jouer la carte sur sa pile ascendante.
	 * @param carte la carte à poser sur la pile ascendante.
	 * @return true si la carte peut être jouée.
	 * @return false si la carte ne peut pas être jouée.
	 */
	public boolean peutJouerAscendant(int carte) {
		return carte > pileAscendante || carte == pileAscendante - 10;
	}
	
	/**
	 * @brief Le joueur peut jouer la carte sur sa pile descendante.
	 * @param carte la carte à poser sur la pile descendante.
	 * @return true si la carte peut être jouée.
	 * @return false si la carte ne peut pas être jouée.
	 */
	public boolean peutJouerDescendant(int carte) {
		return carte < pileDescendante || carte == pileDescendante + 10;
	}
	
	/**
	 * @brief Le joueur peut jouer la carte sur la pile ascendante de son adversaire.
	 * @param carte la carte à poser sur la pile ascendante de son adversaire.
	 * @return true si la carte peut être jouée.
	 * @return false si la carte ne peut pas être jouée.
	 */
	public boolean peutJouerAscAdv(int carte) {
		return carte < pileAscendante;
	}
	
	/**
	 * @brief Le joueur peut jouer la carte sur la pile descendante de son adversaire.
	 * @param carte la carte à poser sur la pile descendante de son adversaire.
	 * @return true si la carte peut être jouée.
	 * @return false si la carte ne peut pas être jouée.
	 */
	public boolean peutJouerDescAdv(int carte) {
		return carte > pileDescendante;
	}
	
	/**
	 * @brief Permet de savoir si la main du Joueur est vide.
	 * @return true si la main du Joueur est vide.
	 * @return false si la main du Joueur n'est pas vide.
	 */
	public boolean mainJoueurVide() {
		return cartesMain.isEmpty();
	}
	
	/**
	 * @brief Permet de connaître le nombre de cartes dans la main du Joueur.
	 * @return la taille de la liste cartesMain.
	 */
	public int nbCartesMain() {
		return this.cartesMain.size();
	}
	
	// AFFICHAGE ----------------------------------------------------------------------------------
	
	/**
	 * @brief Permet de renvoyer l'affichage voulue de la base du Joueur
	 * @return String s
	 */
	public String afficheBase() {
		String s;
		s = this.nom + " ^[";
		if(pileAscendante < 10)
			s += "0";
		s += pileAscendante + "] v[";
		if (pileDescendante < 10)
			s += "0";
		s += pileDescendante + "] (m" + this.nbCartesMain() + "p" + pioche.nbCartesPioche() + ")";
		return s;
	}
	
	/**
	 * @brief Permet de renvoyer l'affichage voulue de la main du Joueur
	 * @return String s
	 */
	public String afficheMain() {
		String s;
		s = "cartes " + this.nom + " { ";
		for (int i = 0; i < cartesMain.size(); i++) {
			if (cartesMain.get(i) < 10) {
				s += "0";
			}
			s += cartesMain.get(i) + " ";
		}
		s += "}";
		return s;
	}
}
