package _mvc_version.model;

/**
 * Classe définissant l'IA/la stratégie débutant
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class IADebutant extends IA implements StrategieDeJeu 
{
	/**
	 * Crée l'IA débutant
	 * @param botUtilisateur Joueur virtuel implémentant cette stratégie
	 */
	public IADebutant(JoueurVirtuel botUtilisateur)
	{
		super(botUtilisateur);
	}

	/**
	 * Retourne l'action choisie  par le bot
	 * @return Action choisie par le bot
	 */
	public String choisirAction() 
	{
		int indexAction = this.determinerIndexActionAExecuterAleatoirement();
		return(indexAction + "");
	}

	/**
	 * Retourne l'index de la carte choisie par le bot
	 * @return Index de la carte choisie par le bot
	 */
	public String choisirCarte() 
	{
		int indexCarte = this.determinerIndexCarteAJouerAleatoirement();
		Carte carteChoisie = (Carte)this.bot.getMain().toArray()[indexCarte];
		return(this.determinerIndexCarte(carteChoisie));
	}

	/**
	 * Retourne l'index de la carte à jouer en supplément
	 * @return Index de la carte à jouer en supplément
	 */
	public String choisirCarteSupplement() 
	{
		return("");
	}
	
	/**
	 * Retourne la réponse du bot quant à la complétion de son dépôt
	 * @return Réponse du bot (Y ou N)
	 */
	public String proposerAjouterCarte()
	{
		return("N");
	}
	
	/**
	 * Retourne la réponse du bot quant à l'annonce de carte
	 * @return Réponse du bot (Y ou N)
	 */
	public String proposerAnnoncerCarte()
	{
		return("N");
	}
}
