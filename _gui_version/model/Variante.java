package _gui_version.model;

import java.util.*;

import _gui_version._exceptions.*;

/**
 * Classe abstraite d�finissant une variante de jeu
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
	 * Cr�e une instance de variante et initialise les propri�t�s communes
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
	 * Initialise la pioche de jeu, par d�faut 54 cartes choisies pour chacune des variantes, la m�thode est donc commune � toute variante
	 * @param pioche Pioche de jeu
	 * @param nombreJeuxDeCartes Nombre de paquets de cartes utilis�s pour la partie
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
	 * Si une carte sp�ciale a �t� demand�e suite � un 8 (ie. couleur, symbole), la m�thode analyse la nature de l'�l�ment demand� et le retourne
	 * @return El�ment de la carte demand��
	 * @throws GameException Si une carte qui ne devrait pas �tre en m�moire l'est ou si aucune carte demand�e n'est enregistr�e
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
					throw new GameException("La carte en m�moire n'est pas un 8");
			}
		}
		catch(NullPointerException e)
		{
			new GameException("Aucune carte demand�e n'a �t� enregistr�e");
		}
		return(reponse);
	}
	
	/**
	 * Retourne la carte demand�e enregistr�e
	 * @return Carte demand�e enregistr�e
	 */
	public Carte getCarteDemandee()
	{
		return(this.carteDemandee);
	}
	
	/**
	 * Modifie la carte demand�e courante enregistr�e
	 * @param c enregistr�e rempla�ante
	 */
	public void setCarteDemandee(Carte c)
	{
		this.carteDemandee = c;
		this.partieAssociee.notifier("Carte demand�e");
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
	 * V�rifie si les deux cartes sp�cifi�es peuvent �tre combin�es
	 * @param valeurCartePrecedente Premi�re carte � combiner
	 * @param valeurNouvelleCarte Deuxi�me carte � combiner
	 * @return Bool�en � vrai si les deux cartes peuvent �tre combin�es, faux sinon
	 */
	public boolean combinaisonAutorisee(Valeur valeurCartePrecedente, Valeur valeurNouvelleCarte)
	{
		if(valeurCartePrecedente.equals(valeurNouvelleCarte))
			return(true);
		else
			return(false);
	}

	/**
	 * Applique l'effet des cartes qui ont �t� d�pos�es juste avant de terminer le tour
	 * @param cartesAJouer Ensemble de cartes repr�sentant le d�p�t effectu� par le joueur actif
	 * @param joueurCarte Joueur actif
	 */
	public abstract void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte);
	
	/**
	 * V�rifie si la carte sp�cifi�e (celle qui souhaite �tre jou�e) est conforme avec celle du talon
	 * @param carteAJouer Carte � jouer
	 * @return Bool�en � vrai si la carte est conforme, faux sinon
	 */
	public abstract boolean verifierConformiteCarte(Carte carteAJouer) throws UncompliantSpecialCardSaved;
	
	/**
	 * M�thode utilis�e pour l'IA uniquement. V�rifie si la carte sp�cifi�e est jouable en fonction de la carte du talon
	 * @param carteAjouer Carte � jouer
	 * @return Bool�en � vrai si la carte est jouable, faux sinon
	 * @throws UncompliantSpecialCardSaved Si une carte non-sp�ciale a �t� enregistr�e
	 */
	public abstract boolean estJouable(Carte carteAjouer) throws UncompliantSpecialCardSaved;
	
}
