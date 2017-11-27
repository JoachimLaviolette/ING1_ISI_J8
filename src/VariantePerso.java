import java.util.*;

public class VariantePerso extends VarianteAMultiplicateurs
{	
	public VariantePerso(Partie partieAssociee) 
	{
		super(partieAssociee);
	}
	
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte)
	{
		for(int indexCarte = cartesAJouer.size() - 1 ; indexCarte >= 0 ; indexCarte--)
		{	
			if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.AS))
			{			
				this.multiplicateurDePioche += 2;
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				this.carteDemandee = null;
				System.out.println("carte mise en mémoire : " + this.carteEnMemoire.toString());			
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.JOKER))
			{
				this.multiplicateurDePioche += 5;
				this.carteEnMemoire = cartesAJouer.get(indexCarte);
				this.carteDemandee = null;
				System.out.println("carte mise en mémoire : " + this.carteEnMemoire.toString());
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.HUIT))
			{
				this.multiplicateurDePioche = 0;
				if(indexCarte == 0)
				{
					this.carteDemandee = this.partieAssociee.demanderSymboleCarteJoueur();
					System.out.println("[carte demandée : " + this.carteDemandee.toString() + "]");
				}
					this.carteEnMemoire = cartesAJouer.get(indexCarte);
					System.out.println("[carte spéciale mise en mémoire : " + this.carteEnMemoire.toString() + "]");
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.SEPT))
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
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.NEUF))
			{
				joueurCarte.getJoueurSuivant().piocher();
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.DIX))
			{
				if(joueurCarte.getMain().size() == 0)
					joueurCarte.piocher();
				else
					this.partieAssociee.jouerTour();
			}
			else if(cartesAJouer.get(indexCarte).getValeur().equals(Valeur.VALET))
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
		}
		joueurCarte.terminerTour();
	}	
	
	public boolean verifierConformiteCarte(Carte carteAJouer) 
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
				//else
				//error, see with exceptions
				//a non-special card has been saved when it shouldn't had to
			}			
		}
		return(false);
	}	
	
	public boolean combinaisonAutorisee(Valeur valeurCartePrecedente, Valeur valeurNouvelleCarte)
	{
		if(valeurCartePrecedente.equals(Valeur.AS) || valeurCartePrecedente.equals(Valeur.JOKER) || valeurCartePrecedente.equals(Valeur.HUIT))
			if(valeurNouvelleCarte.equals(Valeur.AS) || valeurNouvelleCarte.equals(Valeur.JOKER) || valeurNouvelleCarte.equals(Valeur.HUIT))
				return(true);
		return(false);
	}
}