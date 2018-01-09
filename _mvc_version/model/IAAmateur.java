package _mvc_version.model;

import java.util.*;

/**
 * Classe d�finissant l'IA/la strat�gie amateur
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class IAAmateur extends IA implements StrategieDeJeu 
{
	private boolean doitTrouver;
	
	/**
	 * Cr�e l'IA amateur
	 * @param botUtilisateur Joueur virtuel impl�mentant cette strat�gie
	 */
	public IAAmateur(JoueurVirtuel botUtilisateur)
	{
		super(botUtilisateur);
		this.doitTrouver = false;
	}

	/**
	 * Retourne l'action choisie  par le bot
	 * @return Action choisie par le bot
	 */
	public String choisirAction() 
	{
		return("1");
	}

	/**
	 * Retourne l'index de la carte choisie par le bot
	 * @return Index de la carte choisie par le bot
	 */
	public String choisirCarte() 
	{
		Carte derniereCartePosee = this.bot.getPartieEnCours().getDerniereCarte(this.bot.getPartieEnCours().getTalon());
		ArrayList<Carte> cartesJouables = new ArrayList<Carte>();
		ArrayList<Carte> cartesNonJouables = new ArrayList<Carte>();
		this.recupererCartesValides(derniereCartePosee, cartesJouables);
		this.recupererCartesNonValides(derniereCartePosee, cartesNonJouables);
		int indexSousPaquet;
		if(this.doitTrouver)
			indexSousPaquet = 1;
		else
			indexSousPaquet = this.determinerIndexActionAExecuterAleatoirement();
		Carte carteChoisie;
		if(indexSousPaquet == 1)			
			carteChoisie = this.determinerCarteAJouerAleatoirement(cartesJouables);
		else
			carteChoisie = this.determinerCarteAJouerAleatoirement(cartesNonJouables);
		String index = this.determinerIndexCarte(carteChoisie);
		return(index);
	}
	
	/**
	 * D�termine un index al�atoirement </br>
	 * Si cet index vaut 1, l'IA va tenter de compl�ter le d�pot avec une carte conforme </br>
	 * Sinon, elle s�lectionnera une carte non-conforme
	 */
	public String choisirCarteSupplement() 
	{
		int indexChoix = this.determinerIndexActionAExecuterAleatoirement();
		if(indexChoix == 1)
			this.doitTrouver = true;
		else
			this.doitTrouver = false;
		return(this.choisirCarte());
	}
	
	/**
	 * Retourne une carte en fonction d'un index d�termin� al�atoirement
	 * @param paquet Paquet de cartes sp�cifi�
	 * @return Carte provenant du paquet de cartes sp�cifi�
	 */
	public Carte determinerCarteAJouerAleatoirement(ArrayList<Carte> paquet)
	{
		int indexCarte = determinerIndexCarteAJouerAleatoirement(paquet);
		return(paquet.get(indexCarte));
	}
	
	/**
	 * Retourne la r�ponse du bot quant � la compl�tion de son d�p�t
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAjouterCarte()
	{
		int indexChoix = this.determinerIndexActionAExecuterAleatoirement();
		if(indexChoix == 1)
			return("Y");
		else
			return("N");
	}
	
	/**
	 * Retourne la r�ponse du bot quant � l'annonce de carte
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAnnoncerCarte()
	{
		int indexChoix = this.determinerIndexActionAExecuterAleatoirement();
		if(indexChoix == 1)
			return("Y");
		else
			return("N");
	}
}
