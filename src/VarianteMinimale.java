import java.util.*;

public class VarianteMinimale extends Variante
{
	public VarianteMinimale(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
		
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte) 
	{	
		for(int indexCarte = cartesAJouer.size() - 1 ; indexCarte >= 0 ; indexCarte--)
		{	
			if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER))
			{	
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				System.out.println("carte mise en mémoire : " + this.carteEnMemoire.toString());	
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.HUIT))
			{
				if(indexCarte == 0)
				{
					this.carteDemandee = this.partieAssociee.demanderCouleurCarteJoueur();
					System.out.println("[carte demandée : " + this.carteDemandee.toString() + "]");
				}
					this.carteEnMemoire = cartesAJouer.get(indexCarte);
					System.out.println("[carte spéciale mise en mémoire : " + this.carteEnMemoire.toString() + "]");
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.DIX))
			{
				if(joueurCarte.getMain().size() == 0)
					joueurCarte.piocher();
				else
					this.partieAssociee.jouerTour();
			}
		}
		joueurCarte.terminerTour();
	}
	
	public boolean verifierConformiteCarte(Carte carteAJouer)
	{
		if(carteAJouer.getValeur().equals(Valeur.HUIT) || carteAJouer.getValeur().equals(Valeur.JOKER))
			return(true);
		else
		{
			if(this.carteEnMemoire == null)	
			{
				if(carteAJouer.getSymbole().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getSymbole()) || carteAJouer.getValeur().equals(this.partieAssociee.getDerniereCarte(this.partieAssociee.getTalon()).getValeur()))
					return(true);
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
				//else
				//error, see with exceptions
				//a non-special card has been saved when it shouldn't had to
			}	
		}
		return(false);
	}		
}
