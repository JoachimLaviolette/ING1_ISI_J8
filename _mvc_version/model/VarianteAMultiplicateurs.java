package _mvc_version.model;

import java.util.*;

/**
 * Classe abstraite définissant une variante de jeu utilisant des multiplicateurs 
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class VarianteAMultiplicateurs extends Variante
{
	/**
	 * Mutiplicateur utilisé lorsqu'une carte spéciale provoque l'action de pioche au prochain joueur qui ne pourra pas compléter le combo
	 */
	protected int multiplicateurDePioche;
	/**
	 * Mutiplicateur utilisé lorsque plusieurs cartes sautant le tour du joueur suivant sont combinées et que l'auteur des cartes a fait un (ou plusieurs) tour(s) complet(s)
	 */
	protected int multiplicateurDeTour;
	
	/**
	 * Crée une instance de variante à multiplicateurs et initialise les propriétés communes
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
