package _mvc_version.model;

import java.util.*;

/**
 * Classe abstraite d�finissant une variante de jeu utilisant des multiplicateurs 
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class VarianteAMultiplicateurs extends Variante
{
	/**
	 * Mutiplicateur utilis� lorsqu'une carte sp�ciale provoque l'action de pioche au prochain joueur qui ne pourra pas compl�ter le combo
	 */
	protected int multiplicateurDePioche;
	/**
	 * Mutiplicateur utilis� lorsque plusieurs cartes sautant le tour du joueur suivant sont combin�es et que l'auteur des cartes a fait un (ou plusieurs) tour(s) complet(s)
	 */
	protected int multiplicateurDeTour;
	
	/**
	 * Cr�e une instance de variante � multiplicateurs et initialise les propri�t�s communes
	 * @param partieAssociee Instance de partie courante
	 */
	public VarianteAMultiplicateurs(Partie partieAssociee) 
	{
		super(partieAssociee);
		this.ID = 2;
		this.multiplicateurDePioche = 0;
		this.multiplicateurDeTour = 0;
	}	
	
	/**
	 * Retourne la valeur du multiplicateur de pioche et le reset
	 * @return Valeur du multiplicateur de pioche
	 */
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
}
