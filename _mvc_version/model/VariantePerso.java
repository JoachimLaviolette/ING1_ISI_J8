package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe d�finissant une variante de jeu type Personnalis�e
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class VariantePerso extends VarianteAMultiplicateurs
{	
	/**
	 * Cr�e une instance de variante de type personnalis�e
	 * @param partieAssociee Instance de partie courante
	 */
	public VariantePerso(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
	
	/**
	 * V�rifie si les deux cartes sp�cifi�es peuvent �tre combin�es
	 * @param valeurCartePrecedente Premi�re carte � combiner
	 * @param valeurNouvelleCarte Deuxi�me carte � combiner
	 * @return Bool�en � vrai si les deux cartes peuvent �tre combin�es, faux sinon
	 */
	public boolean combinaisonAutorisee(Valeur valeurCartePrecedente, Valeur valeurNouvelleCarte)
	{
		if(valeurCartePrecedente.equals(Valeur.AS) || valeurCartePrecedente.equals(Valeur.JOKER) || valeurCartePrecedente.equals(Valeur.HUIT))
		{
			if(valeurNouvelleCarte.equals(Valeur.AS) || valeurNouvelleCarte.equals(Valeur.JOKER) || valeurNouvelleCarte.equals(Valeur.HUIT))
				return(true);
			else
				return(false);
		}
		else
			return(super.combinaisonAutorisee(valeurCartePrecedente, valeurNouvelleCarte));
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
			if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.AS))
			{			
				this.multiplicateurDePioche += 2;
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				this.carteDemandee = null;
				System.out.println("carte mise en m�moire : " + this.carteEnMemoire.toString());			
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER))
			{
				this.multiplicateurDePioche += 5;
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				this.carteDemandee = null;
				System.out.println("carte mise en m�moire : " + this.carteEnMemoire.toString());
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.HUIT))
			{
				this.multiplicateurDePioche = 0;
				if(indexCarte == 0)
				{
					//ask the view to display the content asking the player to choose the color
					this.partieAssociee.notifier("Demander symbole");
				}
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("[carte sp�ciale mise en m�moire : " + this.carteEnMemoire.toString() + "]");
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.SEPT))
			{
				Joueur joueurX = joueurCarte;  
				Joueur joueurSuivantX = joueurX.getJoueurSuivant(); 
				for(int nombreCartes = 0 ; nombreCartes < cartesAJouer.size() ; nombreCartes++)
				{
					if(!joueurSuivantX.getJoueurSuivant().equals(joueurX))
					{
						joueurSuivantX = joueurSuivantX.getJoueurSuivant(); 
					}
					else
					{
						joueurSuivantX = joueurSuivantX.getJoueurSuivant().getJoueurSuivant();
						this.multiplicateurDeTour++;
					}
				}
				for(int x = 0 ; x < this.multiplicateurDeTour ; x++)
					this.partieAssociee.terminerTour(false);
				this.multiplicateurDeTour = 0;
				this.partieAssociee.setJoueurActif(joueurSuivantX);
				this.partieAssociee.terminerTour(false);
				return;
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.NEUF))
			{
				joueurCarte.getJoueurSuivant().piocher();
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
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.VALET))
			{
				if(cartesAJouer.size() % 2 != 0)
				{
					for(int i = 0 ; i < this.partieAssociee.getJoueursDeLaPartie().size() ; i++)
					{
						Joueur joueurPrecedentX = ((Joueur)this.partieAssociee.getJoueursDeLaPartie().get(i)).getJoueurPrecedent();
						((Joueur)this.partieAssociee.getJoueursDeLaPartie().get(i)).setJoueurPrecedent(((Joueur)this.partieAssociee.getJoueursDeLaPartie().get(i)).getJoueurSuivant());
						((Joueur)this.partieAssociee.getJoueursDeLaPartie().get(i)).setJoueurSuivant(joueurPrecedentX);
					}
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
		if(carteAJouer.getValeur().equals(Valeur.JOKER) || carteAJouer.getValeur().equals(Valeur.HUIT))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				if(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur().equals(Valeur.JOKER))
					return(true);
				else 
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.HUIT))
				{
					if(carteAJouer.getSymbole().equals(this.carteDemandee.getSymbole()))
					{
						this.carteDemandee = null;
						this.carteEnMemoire = null;
						return(true);
					}
				}
				else if(cartePrecedente.getValeur().equals(Valeur.AS))
				{
					if(carteAJouer.getValeur().equals(Valeur.AS))			
						return(true);
				}
				else
				{
					if(!cartePrecedente.getValeur().equals(Valeur.JOKER))
						throw new UncompliantSpecialCardSaved("Une carte non-sp�ciale a �t� enregistr�e.");
				}
			}			
		}
		return(false);
	}	

	/**
	 * M�thode utilis�e pour l'IA uniquement. V�rifie si la carte sp�cifi�e est jouable en fonction de la carte du talon
	 * @param carteAjouer Carte � jouer
	 * @return Bool�en � vrai si la carte est jouable, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-sp�ciale a �t� enregistr�e
	 */
	public boolean estJouable(Carte carteAJouer) throws UncompliantSpecialCardSaved
	{
		if(carteAJouer.getValeur().equals(Valeur.JOKER) || carteAJouer.getValeur().equals(Valeur.HUIT))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				if(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur().equals(Valeur.JOKER))
					return(true);
				else 
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.HUIT))
				{
					if(carteAJouer.getSymbole().equals(this.carteDemandee.getSymbole()))
						return(true);
				}
				else if(cartePrecedente.getValeur().equals(Valeur.AS))
				{
					if(carteAJouer.getValeur().equals(Valeur.AS))			
						return(true);
				}
				else
				{
					if(!cartePrecedente.getValeur().equals(Valeur.JOKER))
						throw new UncompliantSpecialCardSaved("Une carte non-sp�ciale a �t� enregistr�e.");
				}
			}			
		}
		return(false);
	}			
}