package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.UncompliantSpecialCardSaved;

/**
 * Classe d�finissant l'IA/la strat�gie confirm�
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class IAConfirme extends IA implements StrategieDeJeu
{	
	/**
	 * Carte enregistr�e pour l'algorithme de d�termination de cartes jouables
	 */
	private Carte carteEnregistree;
	/**
	 * Ensemble des cartes jouables par le bot
	 */
	private ArrayList<Carte> cartesJouables;
	
	/**
	 * Cr�e l'IA confirm�
	 * @param botUtilisateur Joueur virtuel impl�mentant cette strat�gie
	 */
	public IAConfirme(JoueurVirtuel botUtilisateur)
	{
		super(botUtilisateur);
	}	
	
	/**
	 * Evalue en fonction des cartes en main si le bot peut jouer ou non
	 * @return Bool�en � vrai si le bot peut jouer, faux sinon
	 */
	public boolean peutJouer()
	{
		Carte derniereCartePosee = this.bot.getPartieEnCours().getDerniereCarte(this.bot.getPartieEnCours().getTalon());
		this.cartesJouables = new ArrayList<Carte>();
		this.recupererCartesValides(derniereCartePosee);
		if(cartesJouables.isEmpty())
		{
			return(false);
		}
		return(true);
	}
	
	/**
	 * Retourne l'action choisie  par le bot
	 * @return Action choisie par le bot
	 */
	public String choisirAction() 
	{
		if(this.peutJouer())
			return("1");
		else
		{
			/*if(this.carteEnregistree == null)
				return("2");
			return("3");*/
			return("2");
		}
	}		
	
	/**
	 * Retourne l'index de la carte choisie par le bot
	 * @return Index de la carte choisie par le bot
	 */
	public String choisirCarte() 
	{
		Carte carteChoisie = this.determinerCarteAJouer();
		return(this.determinerIndexCarte(carteChoisie));
	}

	/**
	 * D�termine de fa�on al�atoire une carte � jouer parmi l'ensemble des cartes jouables
	 * @return Carte choisie
	 */
	public Carte determinerCarteAJouer()
	{
		//if 3 cards playable then possible indexes are : 0,1,2 (so O .. [size - 1])
		int index = (int)(Math.random() * (this.cartesJouables.size() - 0));
		Carte carteChoisie = this.cartesJouables.get(index);
		return(carteChoisie);
	}
	
	/**
	 * R�cup�re l'ensemble des cartes valides en fonction de la carte de r�f�rence
	 * @param derniereCartePosee Carte de r�f�rence
	 */
	public void recupererCartesValides(Carte derniereCartePosee)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			/**
			 * Cette IA prend en compte les cartes sp�ciales
			 */
			if(this.carteEnregistree != null)
			{
				if(carteX.getValeur().equals(this.carteEnregistree.getValeur()))
					this.cartesJouables.add(carteX);		
			}
			else
			{
				try
				{
					if(this.bot.getPartieEnCours().getVarianteCourante().estJouable(carteX))
						this.cartesJouables.add(carteX);			
				}
				catch(UncompliantSpecialCardSaved e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Retourne l'index de la carte � jouer en suppl�ment
	 * @return Index de la carte � jouer en suppl�ment
	 */
	public String choisirCarteSupplement() 
	{
		if(this.peutJouer())
			return(this.choisirCarte());
		else
			return("");
	}
	
	/**
	 * Retourne la r�ponse du bot quant � la compl�tion de son d�p�t
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAjouterCarte()
	{
		if(this.peutJouer())
			return("Y");
		return("N");
	}
	
	/**
	 * Retourne la r�ponse du bot quant � l'annonce de carte
	 * @return R�ponse du bot (Y ou N)
	 */
	public String proposerAnnoncerCarte()
	{
		return("Y");
	}

	/**
	 * Modifie la carte enregistr�e
	 * @param carte Carte enregistr�e
	 */
	public void setCarteEnregistree(Carte carte)
	{
		this.carteEnregistree = carte;
	}
	
	/**
	 * Retourne la carte carte enregistr�e
	 * @return Carte enregistr�e
	 */
	public Carte getCarteEnregistree()
	{
		return(this.carteEnregistree);
	}
}


