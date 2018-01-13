package _mvc_version.model;

import java.util.ArrayList;
import java.util.Iterator;

import _mvc_version._exceptions.UncompliantSpecialCardSaved;

/**
 * Classe abstraite d�finissant une intelligence artificielle, dictant le comportement/la strat�gie d'un joueur virtuel (bot)
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class IA 
{
	/**
	 * Joueur virtuel associ� � l'IA courante
	 */
	protected JoueurVirtuel bot;
	
	/**
	 * Cr�e une nouvelle instance d'IA
	 * @param botUtilisateur Joueur virtuel impl�mentant cette IA
	 */
	public IA(JoueurVirtuel botUtilisateur)
	{
		this.bot = botUtilisateur;
	}
	
	/**
	 * D�termine l'index d'une carte dans la main du joueur 
	 * @param carteChoisie Carte dont il faut d�terminer l'index
	 * @return Index de la carte sp�cifi�e
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
	 * D�termine al�atoirement l'action � effectuer entre 2 choix (ie. JOUER/PIOCHER, OUI/NON)
	 * @return Index repr�sentant la r�ponse
	 */
	public int determinerIndexActionAExecuterAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (3 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * D�termine al�atoirement l'index de la carte � jouer dans la main du joueur
	 * @return Index de la carte � jouer
	 */
	public int determinerIndexCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(Math.random() * (this.bot.getMain().size() - 0));	
		return(indexAleatoire);
	}
	
	/**
	 * D�termine al�atoiremen l'index de la carte � jouer dans un paquet de cartes sp�cifi�
	 * @param paquet Paquet de cartes dans lequel une carte est � choisir al�atoirement
	 * @return Index de la carte choisie
	 */
	public int determinerIndexCarteAJouerAleatoirement(ArrayList<Carte> paquet)
	{
		int indexAleatoire = (int)(Math.random() * (paquet.size() - 0));	
		return(indexAleatoire);
	}
	
	/**
	 * D�termine al�atoirement le symbole � jouer apr�s le d�p�t d'un huit
	 * @return Index repr�sentant la r�ponse
	 */
	public int determinerSymboleCarteAJouerAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (5 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * D�termine al�atoirement la couleur � jouer apr�s le d�p�t d'un huit
	 * @return Index repr�sentant la r�ponse
	 */
	public int determinerCouleurCarteAJouerAleatoirement()
	{
		int indexAleatoire = 1 + (int)(Math.random() * (3 - 1));	
		return(indexAleatoire);
	}
	
	/**
	 * Retourne l'index de symbole d�terminer al�atoirement sous forme de chaine de caract�res
	 * @return Index de symbole
	 */
	public String choisirSymboleCarteApresHuit() 
	{
		int indexSymbole = this.determinerSymboleCarteAJouerAleatoirement();		
		return(indexSymbole + "");
	}
	
	/**
	 * Retourne l'index de couleur d�terminer al�atoirement sous forme de chaine de caract�res
	 * @return Index de couleur
	 */
	public String choisirCouleurCarteApresHuit() 
	{
		int indexCouleur = this.determinerCouleurCarteAJouerAleatoirement();		
		return(indexCouleur + "");
	}
	
	/**
	 * R�cup�re l'ensemble des cartes valides en fonction de la carte de r�f�rence
	 * @param derniereCartePosee Carte de r�f�rence
	 * @param cartes Liste des cartes valides � remplir
	 */
	public void recupererCartesValides(Carte derniereCartePosee, ArrayList<Carte> cartes)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			/**
			 * La m�thode de cette classe abstraite ne compatabilise pas les cartes sp�ciales
			 * A la diff�rence de la m�thode de l'IA Confirme par exemple
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
	 * R�cup�re l'ensemble des cartes non-valides en fonction de la carte de r�f�rence
	 * @param derniereCartePosee Carte de r�f�rence
	 * @param cartes Liste des cartes non-valides � remplir
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
