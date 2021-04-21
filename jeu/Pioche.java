/**
 * @file Pioche.java
 * @brief Définit la classe Pioche, qui correspond à la pioche d'un Joueur.
 */

package jeu;

import java.util.*;

public class Pioche {
	
	// ATTRIBUTS ----------------------------------------------------------------------------------
	
	private static final int MAX_CARTES_PIOCHE = 58;

	// Crée la pioche, appelée piocheCartes, sous forme de Stack d'Integer.
	// Cette pioche va contenir des cartes, qui sont considérées dans ce programme comme des Integer.
	private Stack<Integer> piocheCartes = new Stack<>();
	
	// CONSTRUCTEUR -------------------------------------------------------------------------------
	
	public Pioche() {
		initPiocheOrdre();
		// Mélange piocheCartes.
		Collections.shuffle(piocheCartes);
	}
		
	/**
	 * @brief Getters permettant de récupérer la pioche.
	 * @return this.piocheCartes
	 */
	public Stack<Integer> getPiocheCartes() {
		return this.piocheCartes;
	}
	
	// METHODES -----------------------------------------------------------------------------------
	
	/**
	 * @brief Permet d'initialiser la pioche, en mettant dedans les cartes de 2 à MAX_CARTES_PIOCHES
	 * dans l'ordre croissant.
	 */
	private void initPiocheOrdre() {
		for (int i = 0; i < MAX_CARTES_PIOCHE; i++)
			piocheCartes.push(i+2);
	}
	
	/**
	 * @brief Vérifie si la pioche est vide.
	 * @return true si la pioche est vide.
	 * @return false si la pioche n'est pas vide.
	 */
	public boolean piocheVide() {
		return this.piocheCartes.empty();
	}
	
	/**
	 * @brief Permet de connaître le nombre de cartes restantes dans la pioche.
	 * @return la taille de la pioche.
	 */
	public int nbCartesPioche() {
		return this.piocheCartes.size();
	}
}