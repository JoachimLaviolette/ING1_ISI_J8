import java.util.*;

public class VarianteMinimale extends Variante
{
	public VarianteMinimale(Partie partieAssociee) 
	{
		super(partieAssociee);
	}

	public void initialiserPioche(LinkedList<Carte> pioche, int nombreJeuxDeCartes) 
	{
		for(int loop = 0 ; loop < nombreJeuxDeCartes ; loop++)
			for(int valeur = 1 ; valeur < 15 ; valeur++)
				switch(valeur)
				{
					case 1 :  
						pioche.add(new Carte(Valeur.AS, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.AS, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.AS, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.AS, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 2 :  
						pioche.add(new Carte(Valeur.DEUX, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DEUX, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DEUX, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.DEUX, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 3 :  
						pioche.add(new Carte(Valeur.TROIS, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.TROIS, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.TROIS, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.TROIS, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 4 :  
						pioche.add(new Carte(Valeur.QUATRE, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.QUATRE, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.QUATRE, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.QUATRE, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 5 :  
						pioche.add(new Carte(Valeur.CINQ, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.CINQ, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.CINQ, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.CINQ, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 6 :  
						pioche.add(new Carte(Valeur.SIX, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.SIX, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.SIX, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.SIX, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 7 :  
						pioche.add(new Carte(Valeur.SEPT, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.SEPT, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.SEPT, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.SEPT, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 8 :  
						pioche.add(new Carte(Valeur.HUIT, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.HUIT, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.HUIT, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.HUIT, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 9 :  
						pioche.add(new Carte(Valeur.NEUF, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.NEUF, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.NEUF, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.NEUF, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 10 :  
						pioche.add(new Carte(Valeur.DIX, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DIX, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DIX, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.DIX, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 11 :  
						pioche.add(new Carte(Valeur.VALET, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.VALET, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.VALET, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.VALET, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 12 :  
						pioche.add(new Carte(Valeur.DAME, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DAME, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.DAME, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.DAME, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 13 :  
						pioche.add(new Carte(Valeur.ROI, Symbole.CARREAU, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.ROI, Symbole.COEUR, Couleur.ROUGE));
						pioche.add(new Carte(Valeur.ROI, Symbole.PIQUE, Couleur.NOIRE));
						pioche.add(new Carte(Valeur.ROI, Symbole.TREFLE, Couleur.NOIRE));
						break;
					case 14 : 
						pioche.add(new Carte(Valeur.JOKER, null, null)); 
						pioche.add(new Carte(Valeur.JOKER, null, null));
				}	
	}
	
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte) 
	{	
		if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.AS))
		{
			for(int nombreCartes = 0 ; nombreCartes < cartesAJouer.size() ; nombreCartes++)
				this.multiplicateurDePioche += 2;
			this.carteEnMemoire = this.partieAssociee.getDerniereCarte(cartesAJouer);
			System.out.println("carte mise en mémoire : " + this.carteEnMemoire.toString());
		}
		else if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.JOKER))
		{
			for(int nombreCartes = 0 ; nombreCartes < cartesAJouer.size() ; nombreCartes++)
				this.multiplicateurDePioche += 5;
			this.carteEnMemoire = this.partieAssociee.getDerniereCarte(cartesAJouer);
			System.out.println("carte mise en mémoire : " + this.carteEnMemoire.toString());
		}
		else if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.HUIT))
		{
			this.multiplicateurDePioche = 0;
			this.carteDemandee = this.partieAssociee.demanderCarteJoueur();
			System.out.println("[carte demandée : " + this.carteDemandee.toString() + "]");
			this.carteEnMemoire = this.partieAssociee.getDerniereCarte(cartesAJouer);
			System.out.println("[carte spéciale mise en mémoire : " + this.carteEnMemoire.toString() + "]");
		}
		else if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.SEPT))
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
		else if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.DIX))
			this.partieAssociee.jouerTour();
		else if(this.partieAssociee.getDerniereCarte(cartesAJouer).getValeur().equals(Valeur.VALET))
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
		joueurCarte.terminerTour();
	}
	
	public boolean verifierConformiteCarte(Carte carteAJouer)
	{
		if(carteAJouer.getValeur().equals(Valeur.JOKER) || carteAJouer.getValeur().equals(Valeur.AS) || carteAJouer.getValeur().equals(Valeur.HUIT))
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
				if(cartePrecedente.getValeur().equals(Valeur.HUIT))
				{
					if(carteAJouer.getSymbole().equals(this.carteDemandee.getSymbole()))
					{
						this.carteDemandee = null;
						this.carteEnMemoire = null;
						return(true);
					}
				}					
			}	
		}
		return(false);
	}
	
	public int verifierMultiplicateurPioche()
	{
		if(this.multiplicateurDePioche > 0)
		{
			int multiplicateurDePioche = this.multiplicateurDePioche;
			this.multiplicateurDePioche = 0;
			if(!this.carteEnMemoire.getValeur().equals(Valeur.AS))
				this.carteEnMemoire = null;
			return(multiplicateurDePioche);		
		}
		else
			return(0);
	}
	
	public Carte getDerniereCarteSpecialePosee()
	{
		return(this.carteEnMemoire);
	}
}
