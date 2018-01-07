package _gui_version.model;

import java.util.*;

import _gui_version._exceptions.*;

/**
 * Classe définissant une variante de jeu type 4
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class Variante4 extends VarianteAMultiplicateurs
{	
	/**
	 * Crée une instance de variante de type 4
	 * @param partieAssociee Instance de partie courante
	 */
	public Variante4(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
	
	/**
	 * Applique l'effet des cartes qui ont été déposées juste avant de terminer le tour
	 * @param cartesAJouer Ensemble de cartes représentant le dépôt effectué par le joueur actif
	 * @param joueurCarte Joueur actif
	 */
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte)
	{
		for(int indexCarte = cartesAJouer.size() - 1 ; indexCarte >= 0 ; indexCarte--)
		{	
			if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.AS))
			{			
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("[carte mise en mémoire : " + this.carteEnMemoire.toString() + "]");
				if(indexCarte == 0)
				{
					this.carteDemandee = new Carte(null, null, cartesAJouer.get(indexCarte).getCouleur());
				}
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.DEUX))
			{
				this.multiplicateurDePioche = 2;
				if(cartesAJouer.get(indexCarte).getSymbole().equals(Symbole.PIQUE))
					this.multiplicateurDePioche = 4;
				for(int i = 0 ; i < this.multiplicateurDePioche ; i++)
					joueurCarte.getJoueurSuivant().piocher();
				this.multiplicateurDePioche = 0;
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER) || cartesAJouer.get(indexCarte).getValeur().equals(Valeur.HUIT))
			{
				if(indexCarte == 0)
				{
					//ask the view to display the content asking the player to choose the color
					this.partieAssociee.notifier("Demander couleur");
				}
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("[carte mise en mémoire : " + this.carteEnMemoire.toString() + "]");
				if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER))
				{
					this.multiplicateurDePioche = 5;
					for(int i = 0 ; i < this.multiplicateurDePioche ; i++)
						joueurCarte.getJoueurSuivant().piocher();
					this.multiplicateurDePioche = 0;
				}				
			}	
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.DIX))
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
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.VALET))
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
		}
		this.partieAssociee.terminerTour(true);
	}	
	
	/**
	 * Vérifie si la carte spécifiée (celle qui souhaite être jouée) est conforme avec celle du talon
	 * @param carteAJouer Carte à jouer
	 * @return Booléen à vrai si la carte est conforme, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-spéciale a été enregistrée
	 */
	public boolean verifierConformiteCarte(Carte carteAJouer) throws UncompliantSpecialCardSaved
	{
		if(carteAJouer.getValeur().equals(Valeur.JOKER))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				if(carteAJouer.getValeur().equals(Valeur.JOKER))
					return(true);
				else
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.AS) || cartePrecedente.getValeur().equals(Valeur.HUIT) || cartePrecedente.getValeur().equals(Valeur.JOKER))
				{
					if(carteAJouer.getCouleur().equals(this.carteDemandee.getCouleur()))
					{
						this.carteDemandee = null;
						this.carteEnMemoire = null;
						return(true);
					}
				}
				else
					throw new UncompliantSpecialCardSaved("Une carte non-spéciale a été enregistrée.");
			}
		}
		return(false);
	}
	
	/**
	 * Méthode utilisée pour l'IA uniquement. Vérifie si la carte spécifiée est jouable en fonction de la carte du talon
	 * @param carteAjouer Carte à jouer
	 * @return Booléen à vrai si la carte est jouable, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-spéciale a été enregistrée
	 */
	public boolean estJouable(Carte carteAJouer) throws UncompliantSpecialCardSaved
	{
		if(carteAJouer.getValeur().equals(Valeur.JOKER))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				if(carteAJouer.getValeur().equals(Valeur.JOKER))
					return(true);
				else
					if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
						return(true);
			}
			else
			{
				Carte cartePrecedente = this.carteEnMemoire;
				if(cartePrecedente.getValeur().equals(Valeur.AS) || cartePrecedente.getValeur().equals(Valeur.HUIT) || cartePrecedente.getValeur().equals(Valeur.JOKER))
				{
					if(carteAJouer.getCouleur().equals(this.carteDemandee.getCouleur()))
						return(true);
				}
				else
					throw new UncompliantSpecialCardSaved("Une carte non-spéciale a été enregistrée.");
			}
		}
		return(false);
	}
}