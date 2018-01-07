package _gui_version.model;

import java.util.*;

import _gui_version._exceptions.*;

/**
 * Classe abstraite définissant une variante de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class Variante 
{
	protected Partie partieAssociee;
	protected Carte carteEnMemoire;
	protected Carte carteDemandee;
	protected int ID;
	
	/**
	 * Crée une instance de variante et initialise les propriétés communes
	 * @param partieAssociee Instance de partie courante
	 */
	public Variante(Partie partieAssociee)
	{
		this.partieAssociee = partieAssociee;
		this.carteEnMemoire = null;
		this.carteEnMemoire = null;
		this.ID = 1;
	}
	
	/**
	 * Initialise la pioche de jeu, par défaut 54 cartes choisies pour chacune des variantes, la méthode est donc commune à toute variante
	 * @param pioche Pioche de jeu
	 * @param nombreJeuxDeCartes Nombre de paquets de cartes utilisés pour la partie
	 */
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
	
	/**
	 * Si une carte spéciale a été demandée suite à un 8 (ie. couleur, symbole), la méthode analyse la nature de l'élément demandé et le retourne
	 * @return Elément de la carte demandéé
	 * @throws GameException Si une carte qui ne devrait pas être en mémoire l'est ou si aucune carte demandée n'est enregistrée
	 */
	public String getElementCarteDemandee() throws GameException
	{
		String reponse = new String("");
		try
		{
			if(this.carteDemandee != null)
			{
				if(this.carteEnMemoire.getValeur().equals(Valeur.HUIT))
				{
					if(this.carteDemandee.getValeur() != null)
						reponse = this.carteDemandee.getValeur() + "";
					else if(this.carteDemandee.getSymbole() != null)
						reponse = this.carteDemandee.getSymbole() + "";
					else if(this.carteDemandee.getCouleur() != null)
						reponse = this.carteDemandee.getCouleur() + "";
				}
				else
					throw new GameException("La carte en mémoire n'est pas un 8");
			}
		}
		catch(NullPointerException e)
		{
			new GameException("Aucune carte demandée n'a été enregistrée");
		}
		return(reponse);
	}
	
	/**
	 * Retourne la carte demandée enregistrée
	 * @return Carte demandée enregistrée
	 */
	public Carte getCarteDemandee()
	{
		return(this.carteDemandee);
	}
	
	/**
	 * Modifie la carte demandée courante enregistrée
	 * @param c enregistrée remplaçante
	 */
	public void setCarteDemandee(Carte c)
	{
		this.carteDemandee = c;
		this.partieAssociee.notifier("Carte demandée");
	}
	
	/**
	 * Retourne l'instance de partie 
	 * @param partie Instance de la partie courante
	 */
	public void setPartieAssociee(Partie partie)
	{
		this.partieAssociee = partie;
	}
	
	/**
	 * Retourne l'ID de la variante
	 * @return ID de la variante
	 */
	public int getID()
	{
		return(this.ID);
	}
	
	/**
	 * Vérifie si les deux cartes spécifiées peuvent être combinées
	 * @param valeurCartePrecedente Première carte à combiner
	 * @param valeurNouvelleCarte Deuxième carte à combiner
	 * @return Booléen à vrai si les deux cartes peuvent être combinées, faux sinon
	 */
	public boolean combinaisonAutorisee(Valeur valeurCartePrecedente, Valeur valeurNouvelleCarte)
	{
		if(valeurCartePrecedente.equals(valeurNouvelleCarte))
			return(true);
		else
			return(false);
	}

	/**
	 * Applique l'effet des cartes qui ont été déposées juste avant de terminer le tour
	 * @param cartesAJouer Ensemble de cartes représentant le dépôt effectué par le joueur actif
	 * @param joueurCarte Joueur actif
	 */
	public abstract void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte);
	
	/**
	 * Vérifie si la carte spécifiée (celle qui souhaite être jouée) est conforme avec celle du talon
	 * @param carteAJouer Carte à jouer
	 * @return Booléen à vrai si la carte est conforme, faux sinon
	 */
	public abstract boolean verifierConformiteCarte(Carte carteAJouer) throws UncompliantSpecialCardSaved;
	
	/**
	 * Méthode utilisée pour l'IA uniquement. Vérifie si la carte spécifiée est jouable en fonction de la carte du talon
	 * @param carteAjouer Carte à jouer
	 * @return Booléen à vrai si la carte est jouable, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-spéciale a été enregistrée
	 */
	public abstract boolean estJouable(Carte carteAjouer) throws UncompliantSpecialCardSaved;
	
}
