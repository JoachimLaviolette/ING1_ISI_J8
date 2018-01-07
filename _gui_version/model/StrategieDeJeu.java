package _gui_version.model;

/**
 * Interface d�finissant comment se comporte une strat�gie de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public interface StrategieDeJeu 
{
	/**
	 * Retourne un index repr�sentant l'action (g�n�rique) � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 */
	public String choisirAction();
	
	/**
	 * Retour l'index de la carte � jouer
	 * @return Index de la carte � jouer 
	 */
	public String choisirCarte();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public String choisirSymboleCarteApresHuit();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur, sous forme d'index {1, 2}
	 */
	public String choisirCouleurCarteApresHuit();
	
	/**
	 * Retourne l'index d'une carte � jouer en suppl�ment (pour compl�ter le d�p�t)
	 * @return Index de la carte � jouer en suppl�ment
	 */
	public String choisirCarteSupplement();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl� {Y, N}
	 * @return R�ponse du joueur
	 */
	public String proposerAjouterCarte();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl� {Y, N}
	 * @return R�ponse du joueur
	 */
	public String proposerAnnoncerCarte();
}
