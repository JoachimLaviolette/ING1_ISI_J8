package _mvc_version.model;

/**
 * Classe d�finissant l'IA/la strat�gie d�butant
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class IADebutant extends IA implements StrategieDeJeu 
{
	/**
	 * Cr�e l'IA d�butant
	 * @param botUtilisateur Joueur virtuel impl�mentant cette strat�gie
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
	 * Retourne l'index de la carte � jouer en suppl�ment
	 * @return Index de la carte � jouer en suppl�ment
	 */
	public String choisirCarteSupplement() 
	{
		return("");
	}
	
	/**
	 * Retourne la r�ponse du bot quant � la compl�tion de son d�p�t
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAjouterCarte()
	{
		return("N");
	}
	
	/**
	 * Retourne la r�ponse du bot quant � l'annonce de carte
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAnnoncerCarte()
	{
		return("N");
	}
}
