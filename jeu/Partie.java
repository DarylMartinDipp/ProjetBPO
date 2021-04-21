package jeu;

import java.util.*;

public class Partie {

	// ATTRIBUTS ----------------------------------------------------------------------------------
	
	// Cet attribut, utilis�e dans la m�thode quiJoue, permet de savoir � quel Joueur est le tour.
	// Il est initialis� � 0, car c'est le Joueur NORD qui doit commencer.
	private int numJoueur = 0;
	
	// Les joueurs j et jadv permettent au programme de savoir qui est, � chaque coup, qui est le Joueur
	// qui joue et qui est l'adversaire.
	private Joueur j;
	private Joueur jadv;
	private Joueur j1 = new Joueur("NORD");
	private Joueur j2 = new Joueur("SUD");
	
	// CONSTRUCTEUR -------------------------------------------------------------------------------
	
	public Partie() {
		// On joue d'abord un coup entier, car on est s�r que la partie ne sera pas finie d�s le premier tour.
		jouerUnCoupEntier();
		while(!partieFinie()) {
			jouerUnCoupEntier();
		}

		System.out.println(j1.afficheBase());
		System.out.println(j2.afficheBase());
		System.out.println(jadv.afficheMain());
		afficheGagnant();
	}
	
	// JOUER UN COUP ------------------------------------------------------------------------------
	
	/**
	 * @brief Cette m�thode permet de jouer un coup entier.
	 */
	public void jouerUnCoupEntier() {
		quiJoue(numJoueur);
		affichageDebut();
		saisieEtJoue();
		j.trieMain();
		numJoueur++;
	}
	
	// DETECTION FIN DE PARTIE --------------------------------------------------------------------
	
	/**
	 * @brief Permet de savoir si la partie est finie.
	 * @return true si la partie est finie.
	 * @return false si la partie n'est pas finie.
	 * @see la fonction cartesFinies();
	 * @see la fonction advPeutPasJouer();
	 */
	public boolean partieFinie() {
		return cartesFinies() || advPeutPasJouer();
	}
	
	/**
	 * @brief Permet de savoir si le Joueur a fini toutes ses cartes, dans sa main et dans sa pioche.
	 * @return true si le Joueur a fini toutes ses cartes;
	 * @return false si le Joueur n'a pas fini toutes ses cartes;
	 * @see la fonction partieFinie()
	 */
	public boolean cartesFinies() {
		return j.getPioche().piocheVide() && j.mainJoueurVide();
	}
	
	/**
	 * @brief Permet de savoir si le Joueur adverse peut jouer au moins 2 de ses cartes.
	 * @return true si le Joueur adverse ne peut pas jouer.
	 * @return false si le Joueur adverse peut jouer.
	 * @see la fonction partieFinie()
	 */
	public boolean advPeutPasJouer() {
		int nbCartesPouvantEtreJouees = 0;
		
		// Si le Joueur adverse poss�de moins de 2 cartes dans sa main, la m�thode renvoie true.
		if(jadv.getCartesMain().size() < 2)
			return true;
		
		// Analyse de toutes les cartes de la main du Joueur adverse.
		for(int i = 0; i < jadv.getCartesMain().size(); i++) {
			// V�rifie si la carte analys�e peut �tre pos�e sur au moins une des pile.
			if((jadv.peutJouerAscendant(jadv.getCartesMain().get(i))) ||
					(jadv.peutJouerDescendant(jadv.getCartesMain().get(i))))
				nbCartesPouvantEtreJouees++;
		}

		// Analyse de toutes les cartes de la main du Joueur adverse.
		for(int i = 0; i < jadv.getCartesMain().size(); i++) {
			// V�rifie si la carte analys�e peut �tre pos�e sur au moins une des pile adverses.
			if((j.peutJouerAscAdv(jadv.getCartesMain().get(i))) ||
					(j.peutJouerDescAdv(jadv.getCartesMain().get(i)))) {
				nbCartesPouvantEtreJouees++;
				break;
			}
		}
		
		// Si le joueur adverse peut jouer moins de 2 cartes, la m�thode renvoie true.
		// Sinon, la m�thode renvoie false.
		return nbCartesPouvantEtreJouees < 2;
	}
	
	/**
	 * @brief Affiche le joueur gagnant.
	 */
	public void afficheGagnant() {
		System.out.println("partie finie, " + j.getNom() + " a gagn�");
	}
	
	// INITIALISATION DU TOUR ---------------------------------------------------------------------
	
	/**
	 * @brief Permet de savoir � quel Joueur est le tour.
	 * @param[in] numJoueur la variable qui d�finit � quel Joueur est le tour.
	 */
	public void quiJoue(int numJoueur) {
		if (numJoueur % 2 == 0) {
			j = j1;
			jadv = j2;
		}
		else {
			j = j2;
			jadv = j1;
		}
	}
	
	/**
	 * @brief Affiche la base des deux Joueurs, ainsi que la main du Joueur qui joue ce tour.
	 */
	public void affichageDebut() {
		System.out.println(j1.afficheBase());
		System.out.println(j2.afficheBase());
		System.out.println(j.afficheMain());
	}
	
	// SAISIE -------------------------------------------------------------------------------------
	
	/**
	 * @brief Permet la saisie du coup entier, tant que le coup entier entr� est valable.
	 * Si le coup entier est valable, la fonction joue le coup.
	 * @see la fonction saisieDebut()
	 * @see la fonction saisiePasOK()
	 * @see la fonction verifEtJoue()
	 */
	public void saisieEtJoue() {
		// Bool�en permettant de savoir si le coup � �t� jou�, pour sortir de la boucle.
		boolean coupJoue = false;
		// Permet la saisie initiale.
		String coupEntier = saisieDebut();
		while (!coupJoue) {
			if (verifEtJoue(coupEntier))
				// Si le coup entier est bon et a �t� jou�, on sort de la boucle.
				coupJoue = true;
			else
				// Permet la saisie en cas de non-valabilit� du coup entier jou�.
				coupEntier = saisiePasOK();
		}
	}
	
	/**
	 * @brief Permet la saisie initiale, et renvoie la ligne suivante entr�e par l'utilisateur.
	 * @return la ligne suivante entr�e par l'utilisateur.
	 */
	public String saisieDebut() {
		Scanner sc = new Scanner(System.in);
		System.out.print("> ");
		return sc.nextLine();
	}
	
	/**
	 * @brief Permet la saisie en cas d'erreur, et renvoie la ligne suivante entr�e par l'utilisateur.
	 * @return la ligne suivante entr�e par l'utilisateur.
	 */
	public String saisiePasOK() {
		Scanner sc = new Scanner(System.in);
		System.out.print("#> ");
		return sc.nextLine();
	}
	
	// VERIFICATIONS + JEU ------------------------------------------------------------------------
	
	// BLOC PRINCIPAL -----------------------------------------------------------------------------
	
	/**
	 * @brief Permet de v�rifier la validit� du coup entier et, le cas �ch�ant, le joue.
	 * @param s la saisie de l'utilisateur
	 * @return true si le coup entier peut et donc a �t� jou�.
	 * @return false si le coup ne peut pas �tre jou�.
	 * @see la fonction verifDeuxCoupsMin(String[] coupEntier)
	 * @see la fonction verifNombreDeuxfois(String[] coupEntier)
	 * @see la fonction verifLongueurCoup (String coup)
	 * @see la fonction verifCoupAdv(int carteJoue, String caractere)
	 * @see la fonction rempliTableauCoupsDesc(int tailleCoupsDesc, Integer[] coupsDesc)
	 * @see la fonction verifCoupsAsc(int[] coupsAsc, int tailleCoupsAsc)
	 * @see la fonction verifCoupsDesc(Integer[] coupsDesc, int tailleCoupsDesc)
	 * @see la fonction joueCoupsSurSoi(int[] coupsAsc, int tailleCoupsAsc, Integer[] coupsDesc, int tailleCoupsDesc)
	 * @see la fonction joueCoupSurAdv(String[] coupSurAdv)
	 * @see la fonction piocheEtAffiche(int tailleCoupsAsc, int tailleCoupsDesc, boolean coupJoueSurAdv)
	 */
	public boolean verifEtJoue(String s) {
		// Booleen permettant de savoir si un coup sur la base adverse a �t� jou�
		boolean coupJoueSurAdv = false;
		
		// S�pare les saisie et les range dans un tableau.
		s = s.trim();
		String[] coupEntier = s.split("\\s+");	
		
		// Cr�ation de trois tableau : coupsAsc, coupsDesc coupSurAdv, qui serviront � stocker
		// s�par�ment les coups. Ce sont des tableaux de taille 6, car ils peuvent stocker au maximum 6 coups.
		// Leur taille permet de conna�tre le nombre de coups stock�s � l'int�rieur.
		
		// Ce tableau d'int permet de stocker la valeur des cartes voulant �tre jou�es sur
		// sa pile ascendante.
		int[] coupsAsc = new int[6];
		int tailleCoupsAsc = 0;
		// Ici, j'ai utilis� un tableau d'Integer pour pouvoir ensuite le trier dans l'ordre d�croissant,
		// gr�ce � la fonction Collections.reverseOrder(), qui s'utilise qu'avec des Integer.
		Integer[] coupsDesc = new Integer[6];
		int tailleCoupsDesc = 0;
		// Ce tableau va stocker le coup entier voulant �tre jou� sur l'une des piles de l'adversaire.
		// Gr�ce � cela, nous pourrons retrouver dans ce tableau le caract�re "v" ou "^", pour ensuite
		// faire les v�rification.
		String[] coupSurAdv = new String[1];
		int tailleCoupSurAdv = 0;
		
		if (!verifDeuxCoupsMin(coupEntier))
			return false;
		
		// Trie le tableau coupEntier, pour faciliter la v�rification suivante.
		Arrays.sort(coupEntier);
		
		if (!verifNombreDeuxfois(coupEntier))
			return false;
		
		// Une fois que les v�rifications primaires sont faites sur le coup entier, on va trier et v�rifier chaque coup distinct.
		for (int i = 0; i < coupEntier.length; i++) {
						
			if(!verifLongueurCoup(coupEntier[i]))
				return false;
			
			// Convertit la valeur des deux premiers caract�res d'un coup en int.
			String stringCarteJoue = "" + coupEntier[i].charAt(0) + coupEntier[i].charAt(1);
			// V�rifie si cela est possible.
			if (!stringCarteJoue.matches("-?\\d+"))
				return false;
			int carteJoue = Integer.parseInt(stringCarteJoue);
			
			// Enregistre le caract�re � la troisi�me position.
			String caractere = "" + coupEntier[i].charAt(2);
			
			// V�rifie que ce caract�re est bel et bien "^" ou "v", car il ne peut pas �tre autre chose.
			if (!caractere.equals("v") && !caractere.equals("^"))
				return false;
			
			// V�rifie le coup jou� sur l'adversaire
			
			if (coupEntier[i].length() == 4) {
				String caractereAdv = "" + coupEntier[i].charAt(3);
				// V�rifie que le caract�re en 4�me position soit bien "'"
				if (caractereAdv.equals("'")) {
					// V�rifie qu'aucun autre coup a �t� jou� sur l'adversaire 
					if (!coupJoueSurAdv) {
						if (verifCoupAdv(carteJoue, caractere)) {
							// Place la carte dans le tableau coupSurAdv
							coupSurAdv[tailleCoupSurAdv] = coupEntier[i];
							// Passe le booleen � true, car un coup � maintant �t� jou� sur l'adversaire
							coupJoueSurAdv = true;
							tailleCoupSurAdv++;
						}
						else
							return false;
					}
					else
						return false;
				}
				else
					return false;
			}
			
			// Si la carte doit �tre pos�e sur la base du Joueur, on la place dans le tableau correspondant.
			else if (caractere.equals("^")) {
				coupsAsc[tailleCoupsAsc] = carteJoue;
				tailleCoupsAsc++;
			}
			else if (caractere.equals("v")) {
				coupsDesc[tailleCoupsDesc] = carteJoue;
				tailleCoupsDesc++;
			}
			// Retourne faux si la carte ne peut �tre plac�e dans aucun tableau.
			else
				return false;
		}
		
		// Le tableau coupsDesc est un tableau d'Integer.
		// Les cases non utilis�es ont don cun pointeur null.
		// On remplace alors ces cases avec des 0.
		rempliTableauCoupsDesc(tailleCoupsDesc, coupsDesc);
		
		// Triage des tableaux
		Arrays.sort(coupsAsc);
		Arrays.sort(coupsDesc, Collections.reverseOrder());
		
		// VERIFICATION DES VALEURS SAISIES DANS CHAQUE TABLEAU
		
		if (!verifCoupsAsc(coupsAsc, tailleCoupsAsc))
			return false;
		
		// Pour coupsDesc
		if (!verifCoupsDesc(coupsDesc, tailleCoupsDesc))
			return false;
		
		// SI TOUT EST BON, ON JOUE LES CARTES.
		
		joueCoupsSurSoi(coupsAsc, tailleCoupsAsc, coupsDesc, tailleCoupsDesc);
		
		if (coupJoueSurAdv) {
			joueCoupSurAdv(coupSurAdv);
		}
		
		piocheEtAffiche(tailleCoupsAsc, tailleCoupsDesc, coupJoueSurAdv);
		
		return true;
	}
	
	// VERIFICATIONS ------------------------------------------------------------------------------
	
	/**
	 * @brief V�rifie que le coup entier soit constitu� d'au moins 2 coups.
	 * @param[in] coupsEntier le tableau qui stocke tous les coups.
	 * @return true si le tableau a une longueur sup�rieure � 1,
	 * c'est-�-dire que le coup entier soit constitu� d'au moins 2 coups.
	 * @return false si le tableau a une longueur inf�rieure � 1,
	 * c'est-�-dire que le coup entier ne soit pas constitu� d'au moins 2 coups.
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifDeuxCoupsMin(String[] coupEntier) {
		return coupEntier.length > 1;
	}
	
	/**
	 * @brief V�rifie que toutes les cartes n'ont �t� jou�es qu'une seule fois.
	 * @param[in] coupsEntier le tableau qui stocke tous les coups.
	 * @return true si aucune carte n'a �t� jou�e deux fois
	 * @return false si au moins une carte a �t� jou�e deux fois
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifNombreDeuxfois(String[] coupEntier) {
		for (int i = 1; i < coupEntier.length; i++)
			// Pour chaque coup du tableau coupEntier, v�rifie si la carte (constitu� de charAt(0) + charAt(1)) n'est pas
			// pr�sente deux fois � la suite, le tableau �tant tri�.
			if (coupEntier[i].charAt(0) == coupEntier[i-1].charAt(0) && coupEntier[i].charAt(1) == coupEntier[i-1].charAt(1))
				return false;
		return true;
	}
	
	/**
	 * @brief V�rifie si le coup fait 3 caract�res ou 4 caract�res.
	 * @param coup le coup � v�rifier.
	 * @return true si le coup � une bonne longueur.
	 * @return false si le coup n'a pas une bonne longueur.
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifLongueurCoup (String coup) {
		return coup.length() == 3 || coup.length() == 4;
	}
	
	/**
	 * @brief V�rifie si la carte peut �tre jou�e sur la base adverse.
	 * @param[in] carteJoue la carte � v�rifier.
	 * @param[in] caractere le caract�re d�finissant la pile.
	 * @return true si la carte peut �tre jou�e.
	 * @return false si la carte ne peut pas �tre jou�e.
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifCoupAdv(int carteJoue, String caractere) {
		// V�rifie, en fonction du caract�re, si le Joueur peut jouer la carte, et si le Joueur a bien la
		// carte dans sa main.
		
		if (caractere.equals("^") && jadv.peutJouerAscAdv(carteJoue) && j.getCartesMain().contains(carteJoue))
			return true;
		
		if (caractere.equals("v") && jadv.peutJouerDescAdv(carteJoue) && j.getCartesMain().contains(carteJoue))
			return true;
		
		return false;
	}
	
	/**
	 * @brief Termine de remplir le tableau coupsDesc avec des 0.
	 * @param[in] tailleCoupsDesc la taille du tableau, pour savoir � quel indice commencer.
	 * @param[in-out] coupsDesc le tableau d'Integer � remplir.
	 * @see la fonction verifEtJoue(String s)
	 */
	public void rempliTableauCoupsDesc(int tailleCoupsDesc, Integer[] coupsDesc) {
		while (tailleCoupsDesc < coupsDesc.length) {
			coupsDesc[tailleCoupsDesc] = 0;
			tailleCoupsDesc++;
		}
	}
	
	/**
	 * @brief V�rifie si toutes les cartes voulant �tre jou�es sur la base ascendante du Joueur peuvent l'�tre.
	 * @param[in] coupsAsc le tableau contenant les cartes � v�rifier.
	 * @param[in] tailleCoupsAsc la taille du tableau.
	 * @return true si toutes les cartes peuvent �tre jou�es.
	 * @return false si au moins une carte ne peut pas �tre jou�e.
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifCoupsAsc(int[] coupsAsc, int tailleCoupsAsc) {
		
		// On a coupsAsc.length - tailleCoupsAsc pour pas qu'il prenne en compte les 0.
		for (int i = coupsAsc.length - tailleCoupsAsc; i < coupsAsc.length; i++) {
			// V�rifie si le Joueur a la carte dans sa main, et qu'il peut la jouer.
			if (!j.getCartesMain().contains(coupsAsc[i]) || !j.peutJouerAscendant(coupsAsc[i]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @brief V�rifie si toutes les cartes voulant �tre jou�es sur la base descendante du Joueur peuvent l'�tre.
	 * @param[in] coupsDesc le tableau contenant les cartes � v�rifier.
	 * @param[in] tailleCoupsDesc la taille du tableau.
	 * @return true si toutes les cartes peuvent �tre jou�es.
	 * @return false si au moins une carte ne peut pas �tre jou�e.
	 * @see la fonction verifEtJoue(String s)
	 */
	public boolean verifCoupsDesc(Integer[] coupsDesc, int tailleCoupsDesc) {
		for (int i = 0; i < tailleCoupsDesc; i++) {
			// V�rifie si le Joueur a la carte dans sa main, et qu'il peut la jouer.
			if (!j.getCartesMain().contains(coupsDesc[i]) || !j.peutJouerDescendant(coupsDesc[i]))
				return false;
		}
		
		return true;
	}
	
	/**
	 * @brief Joue les cartes sur la ou les piles du Joueur.
	 * @param[in] coupsAsc le tableau des coups ascendants.
	 * @param[in] tailleCoupsAsc sa taille.
	 * @param[in] coupsDesc le tableau des coups descendants.
	 * @param[in] tailleCoupsDesc sa taille.
	 * @see la fonction verifEtJoue(String s)
	 */
	public void joueCoupsSurSoi(int[] coupsAsc, int tailleCoupsAsc, Integer[] coupsDesc, int tailleCoupsDesc) {
		for (int i = coupsAsc.length - tailleCoupsAsc; i < coupsAsc.length; i++) {
			j.poserCarteAscendante(coupsAsc[i]);
		}
			
		for (int i = 0; i < tailleCoupsDesc; i++) {
			j.poserCarteDescendante(coupsDesc[i]);
		}
	}
	
	/**
	 * @brief Joue la carte (s'il y en a un) sur la base du Joueur adverse.
	 * @param[in] coupSurAdv le tableau du coup sur l'adversaire.
	 * @see la fonction verifEtJoue(String s)
	 */
	public void joueCoupSurAdv(String[] coupSurAdv) {
		// Convertit la valeur de la carte en int.
		String stringCarteJoue = "" + coupSurAdv[0].charAt(0) + coupSurAdv[0].charAt(1);
		int carteJoue = Integer.parseInt(stringCarteJoue);
		String caractere = "" + coupSurAdv[0].charAt(2);
		
		if (caractere.equals("^")) {
			jadv.setPileAscendante(carteJoue);
			// Enl�ve la carte de la main du Joueur.
			j.getCartesMain().remove(j.getCartesMain().indexOf(carteJoue));
		}
		else {
			jadv.setPileDescendante(carteJoue);
			j.getCartesMain().remove(j.getCartesMain().indexOf(carteJoue));
		}
	}
	
	/**
	 * @brief Pioche les cartes n�cessaires et affiche le message d'information.
	 * @param[in] tailleCoupsAsc la taille du tableau des coups ascendants.
	 * @param[in] tailleCoupsDesc la taille du tableau des coups ascendants.
	 * @param[in] coupJoueSurAdv le booleen qui permet de savoir si un coup a �t� jou� sur les bases adverses.
	 */
	public void piocheEtAffiche(int tailleCoupsAsc, int tailleCoupsDesc, boolean coupJoueSurAdv) {
		int cartesPiochees = 0;
		
		// Compte le nombre total de cartes jou�es.
		int cartesJouees = tailleCoupsAsc + tailleCoupsDesc;
		if (coupJoueSurAdv)
			cartesJouees++;
		
		// Pioche si un coup a �t� jou� sur les bases adverses.
		if (coupJoueSurAdv) {
			while (j.nbCartesMain() < 6) {
				if(!j.getPioche().piocheVide()) {
					j.piocheUneCarte();
					cartesPiochees++;
				}
				else
					break;
			}
		}
		// Sinon, pioche deux cartes.
		else {
			for (int i = 0; i < 2; i++) {
				if(!j.getPioche().piocheVide()) {
					j.piocheUneCarte();
					cartesPiochees++;
				}
			}
		}
		System.out.println(cartesJouees + " cartes pos�es, " + cartesPiochees + " cartes pioch�es");
	}
}
