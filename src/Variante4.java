import java.util.*;

public class Variante4 extends VarianteAMultiplicateurs
{	
	public Variante4(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
	
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
					System.out.println("[carte demandée : " + this.carteDemandee.toString() + "]");
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
					this.carteDemandee = this.partieAssociee.demanderCouleurCarteJoueur();
					System.out.println("[carte demandée : " + this.carteDemandee.toString() + "]");
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
						Joueur joueurPrecedentX = ((Joueur)this.partieAssociee.getJoueursDeLaPartie().toArray()[i]).getJoueurPrecedent();
						((Joueur)this.partieAssociee.getJoueursDeLaPartie().toArray()[i]).setJoueurPrecedent(((Joueur)this.partieAssociee.getJoueursDeLaPartie().toArray()[i]).getJoueurSuivant());
						((Joueur)this.partieAssociee.getJoueursDeLaPartie().toArray()[i]).setJoueurSuivant(joueurPrecedentX);
					}
				}
			}		
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.VALET))
			{
				Joueur joueurX = joueurCarte;  
				Joueur joueurSuivantX = joueurX.getJoueurSuivant(); 
				for(int nombreCartes = 0 ; nombreCartes < cartesAJouer.size() ; nombreCartes++)
				{
					if(!joueurSuivantX.getJoueurSuivant().equals(joueurSuivantX))
						joueurSuivantX = joueurSuivantX.getJoueurSuivant(); 
					else
					{
						joueurSuivantX = joueurSuivantX.getJoueurSuivant().getJoueurSuivant();
						this.multiplicateurDeTour++;
					}
				}
				for(int x = 0 ; x < this.multiplicateurDeTour ; x++)
					this.partieAssociee.jouerTour();
				this.partieAssociee.setJoueurActif(joueurSuivantX);
				this.partieAssociee.jouerTour();
			}						
		}
		joueurCarte.terminerTour();
	}	
	
	public boolean verifierConformiteCarte(Carte carteAJouer) 
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
				//else
				//error, see with exceptions
				//a non-special card has been saved when it shouldn't had to
			}
		}
		return(false);
	}
}