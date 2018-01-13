package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe d�finissant une variante de jeu type Minimale
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class VarianteMinimale extends Variante
{
	/**
	 * Cr�e une instance de variante de type Minimale
	 * @param partieAssociee Instance de partie courante
	 */
	public VarianteMinimale(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
		
	/**
	 * Applique l'effet des cartes qui ont �t� d�pos�es juste avant de terminer le tour
	 * @param cartesAJouer Ensemble de cartes repr�sentant le d�p�t effectu� par le joueur actif
	 * @param joueurCarte Joueur actif
	 */
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte)
	{	
		for(int indexCarte = cartesAJouer.size() - 1 ; indexCarte >= 0 ; indexCarte--)
		{	
			if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER))
			{	
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("[carte sp�ciale mise en m�moire : " + this.carteEnMemoire.toString() + "]");	
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.HUIT))
			{
				if(indexCarte == 0)
				{
					//ask the view to display the content asking the player to choose the color
					this.partieAssociee.notifier("Demander couleur");
				}
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("[carte sp�ciale mise en m�moire : " + this.carteEnMemoire.toString() + "]");
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.DIX))
			{
				if(joueurCarte.getMain().size() == 0)
					joueurCarte.piocher();
				else
				{	
					this.partieAssociee.terminerTour(false);
					return;
				}
			}
		}
		this.partieAssociee.terminerTour(true);
	}
	
	/**
	 * V�rifie si la carte sp�cifi�e (celle qui souhaite �tre jou�e) est conforme avec celle du talon
	 * @param carteAJouer Carte � jouer
	 * @return Bool�en � vrai si la carte est conforme, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-sp�ciale a �t� enregistr�e
	 */
	public boolean verifierConformiteCarte(Carte carteAJouer) throws UncompliantSpecialCardSaved
	{
		if(carteAJouer.getValeur().equals(Valeur.HUIT) || carteAJouer.getValeur().equals(Valeur.JOKER))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				Carte carteTalon = this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon());
				if(carteTalon.getValeur().equals(Valeur.JOKER) || carteTalon.getValeur().equals(Valeur.HUIT) || carteTalon.getValeur().equals(Valeur.AS))
					return(true);
				else
				{
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
				}
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.JOKER))
				{
					this.carteEnMemoire = null;
					return(true);
				}
				else if(cartePrecedente.getValeur().equals(Valeur.HUIT))
				{
					if(carteAJouer.getCouleur().equals(this.carteDemandee.getCouleur()))
					{
						this.carteDemandee = null;
						this.carteEnMemoire = null;
						return(true);
					}
				}
				else
					throw new UncompliantSpecialCardSaved("Une carte non-sp�ciale a �t� enregistr�e.");
			}	
		}
		return(false);
	}
	
	/**
	 * M�thode utilis�e pour l'IA uniquement. V�rifie si la carte sp�cifi�e est jouable en fonction de la carte du talon
	 * @param carteAJouer Carte � jouer
	 * @return Bool�en � vrai si la carte est jouable, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-sp�ciale a �t� enregistr�e
	 */
	public boolean estJouable(Carte carteAJouer) throws UncompliantSpecialCardSaved
	{
		if(carteAJouer.getValeur().equals(Valeur.HUIT) || carteAJouer.getValeur().equals(Valeur.JOKER))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				Carte carteTalon = this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon());
				if(carteTalon.getValeur().equals(Valeur.JOKER) || carteTalon.getValeur().equals(Valeur.HUIT) || carteTalon.getValeur().equals(Valeur.AS))
					return(true);
				else
				{
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
				}
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.JOKER))
					return(true);
				else if(cartePrecedente.getValeur().equals(Valeur.HUIT))
				{
					if(carteAJouer.getCouleur().equals(this.carteDemandee.getCouleur()))
						return(true);
				}
				else
					throw new UncompliantSpecialCardSaved("Une carte non-sp�ciale a �t� enregistr�e.");
			}	
		}
		return(false);
	}	
}
