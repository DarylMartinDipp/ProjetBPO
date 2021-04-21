/**
 * @file Pioche.java
 * @brief D�finit la classe Pioche, qui correspond � la pioche d'un Joueur.
 */

package jeu;

import java.util.*;

public class Pioche {
	
	// ATTRIBUTS ----------------------------------------------------------------------------------
	
	private static final int MAX_CARTES_PIOCHE = 58;

	// Cr�e la pioche, appel�e piocheCartes, sous forme de Stack d'Integer.
	// Cette pioche va contenir des cartes, qui sont consid�r�es dans ce programme comme des Integer.
	private Stack<Integer> piocheCartes = new Stack<>();
	
	// CONSTRUCTEUR -------------------------------------------------------------------------------
	
	public Pioche() {
		initPiocheOrdre();
		// M�lange piocheCartes.
		Collections.shuffle(piocheCartes);
	}
		
	/**
	 * @brief Getters permettant de r�cup�rer la pioche.
	 * @return this.piocheCartes
	 */
	public Stack<Integer> getPiocheCartes() {
		return this.piocheCartes;
	}
	
	// METHODES -----------------------------------------------------------------------------------
	
	/**
	 * @brief Permet d'initialiser la pioche, en mettant dedans les cartes de 2 � MAX_CARTES_PIOCHES
	 * dans l'ordre croissant.
	 */
	private void initPiocheOrdre() {
		for (int i = 0; i < MAX_CARTES_PIOCHE; i++)
			piocheCartes.push(i+2);
	}
	
	/**
	 * @brief V�rifie si la pioche est vide.
	 * @return true si la pioche est vide.
	 * @return false si la pioche n'est pas vide.
	 */
	public boolean piocheVide() {
		return this.piocheCartes.empty();
	}
	
	/**
	 * @brief Permet de conna�tre le nombre de cartes restantes dans la pioche.
	 * @return la taille de la pioche.
	 */
	public int nbCartesPioche() {
		return this.piocheCartes.size();
	}
}