package _mvc_version.model;

import java.util.ArrayList;
import java.util.Iterator;

import _mvc_version._exceptions.UncompliantSpecialCardSaved;

/**
 * Classe abstraite définissant une intelligence artificielle, dictant le comportement/la stratégie d'un joueur virtuel (bot)
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class IA 
{
	/**
	 * Joueur virtuel associé à l'IA courante
	 */
	protected JoueurVirtuel bot;
	
	/**
	 * Crée une nouvelle instance d'IA
	 * @param botUtilisateur Joueur virtuel implémentant cette IA
	 */
	public IA(JoueurVirtuel botUtilisateur)
	{
		this.bot = botUtilisateur;
	}
	
	/**
	 * Détermine l'index d'une carte dans la main du joueur 
	 * @param carteChoisie Carte dont il faut déterminer l'index
	 * @return Index de la carte spécifiée
	 */
	public String determinerIndexCarte(Carte carteChoisie)
	{
		Iterator<Carte> ite = this.bot.getMain().iterator();
		int i = 1;
		while(ite.hasNext())
		{
			if(!ite.next().equals(carteChoisie))
				i++;
			else
				return(i + "");
		}
		return(i + "");
	}
	
	/**
	 * Détermine aléatoirement l'action à effectuer entre 2 choix (ie. JOUER/PIOCHER, OUI/NON)
	 * @return Index représentant la réponse
	 */
	public int determinerIndexActionAExecuterAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (3 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * Détermine aléatoirement l'index de la carte à jouer dans la main du joueur
	 * @return Index de la carte à jouer
	 */
	public int determinerIndexCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(Math.random() * (this.bot.getMain().size() - 0));	
		return(indexAleatoire);
	}
	
	/**
	 * Détermine aléatoiremen l'index de la carte à jouer dans un paquet de cartes spécifié
	 * @param paquet Paquet de cartes dans lequel une carte est à choisir aléatoirement
	 * @return Index de la carte choisie
	 */
	public int determinerIndexCarteAJouerAleatoirement(ArrayList<Carte> paquet)
	{
		int indexAleatoire = (int)(Math.random() * (paquet.size() - 0));	
		return(indexAleatoire);
	}
	
	/**
	 * Détermine aléatoirement le symbole à jouer après le dépôt d'un huit
	 * @return Index représentant la réponse
	 */
	public int determinerSymboleCarteAJouerAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (5 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * Détermine aléatoirement la couleur à jouer après le dépôt d'un huit
	 * @return Index représentant la réponse
	 */
	public int determinerCouleurCarteAJouerAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (3 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * Retourne l'index de symbole déterminer aléatoirement sous forme de chaine de caractères
	 * @return Index de symbole
	 */
	public String choisirSymboleCarteApresHuit() 
	{
		int indexSymbole = this.determinerSymboleCarteAJouerAleatoirement();		
		return(indexSymbole + "");
	}
	
	/**
	 * Retourne l'index de couleur déterminer aléatoirement sous forme de chaine de caractères
	 * @return Index de couleur
	 */
	public String choisirCouleurCarteApresHuit() 
	{
		int indexCouleur = this.determinerCouleurCarteAJouerAleatoirement();		
		return(indexCouleur + "");
	}
	
	/**
	 * Récupère l'ensemble des cartes valides en fonction de la carte de référence
	 * @param derniereCartePosee Carte de référence
	 * @param cartes Liste des cartes valides à remplir
	 */
	public void recupererCartesValides(Carte derniereCartePosee, ArrayList<Carte> cartes)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			/**
			 * La méthode de cette classe abstraite ne compatabilise pas les cartes spéciales
			 * A la différence de la méthode de l'IA Confirme par exemple
			 */
			if(!carteX.getValeur().equals(Valeur.AS) && !carteX.getValeur().equals(Valeur.JOKER) && !carteX.getValeur().equals(Valeur.HUIT))
			{
				try
				{
					if(this.bot.getPartieEnCours().getVarianteCourante().estJouable(carteX))
						cartes.add(carteX);
				}
				catch(UncompliantSpecialCardSaved e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Récupère l'ensemble des cartes non-valides en fonction de la carte de référence
	 * @param derniereCartePosee Carte de référence
	 * @param cartes Liste des cartes non-valides à remplir
	 */
	public void recupererCartesNonValides(Carte derniereCartePosee, ArrayList<Carte> cartes)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			if(!carteX.getValeur().equals(Valeur.AS) && !carteX.getValeur().equals(Valeur.JOKER) && !carteX.getValeur().equals(Valeur.HUIT))
			{
				if(!carteX.getValeur().equals(derniereCartePosee.getValeur()) && !carteX.getSymbole().equals(derniereCartePosee.getSymbole()))
					cartes.add(carteX);				
			}
		}
	}
}
