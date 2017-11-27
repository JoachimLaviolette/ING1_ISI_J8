import java.util.*;

public abstract class Variante 
{
	protected Partie partieAssociee;
	protected Carte carteEnMemoire;
	protected Carte carteDemandee;
	
	public Variante(Partie partieAssociee)
	{
		this.partieAssociee = partieAssociee;
		this.carteEnMemoire = null;
		this.carteEnMemoire = null;
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
	
	public String afficherCarteDemandee()
	{
		String reponse = new String("");
		if(this.carteDemandee != null && this.carteEnMemoire.getValeur().equals(Valeur.HUIT))
		{
			if(this.carteDemandee.getValeur() != null)
				reponse = "Le précédent joueur a joué un 8 et a demandé de jouer un " + this.carteDemandee.getValeur();
			else if(this.carteDemandee.getSymbole() != null)
				reponse = "Le précédent joueur a joué un 8 et a demandé de jouer du " + this.carteDemandee.getSymbole();
			else if(this.carteDemandee.getCouleur() != null)
				reponse = "Le précédent joueur a joué un 8 et a demandé de jouer une carte " + this.carteDemandee.getCouleur();
			//else
			//error, see with exceptions
		}
		return(reponse);
	}
	
	public void setPartieAssociee(Partie partie)
	{
		this.partieAssociee = partie;
	}

	//abstract methods
	public abstract void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte);
	public abstract boolean verifierConformiteCarte(Carte carteAJouer);
	
}
