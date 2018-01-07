package _gui_version.model;

/**
 * Interface définissant comment se comporte une stratégie de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public interface StrategieDeJeu 
{
	/**
	 * Retourne un index représentant l'action (générique) à exécuter
	 * @return Index représentant l'action à exécuter
	 */
	public String choisirAction();
	
	/**
	 * Retour l'index de la carte à jouer
	 * @return Index de la carte à jouer 
	 */
	public String choisirCarte();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public String choisirSymboleCarteApresHuit();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur, sous forme d'index {1, 2}
	 */
	public String choisirCouleurCarteApresHuit();
	
	/**
	 * Retourne l'index d'une carte à jouer en supplément (pour compléter le dépôt)
	 * @return Index de la carte à jouer en supplément
	 */
	public String choisirCarteSupplement();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'une clé {Y, N}
	 * @return Réponse du joueur
	 */
	public String proposerAjouterCarte();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'une clé {Y, N}
	 * @return Réponse du joueur
	 */
	public String proposerAnnoncerCarte();
}
