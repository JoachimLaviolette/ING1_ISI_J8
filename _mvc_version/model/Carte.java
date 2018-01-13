package _mvc_version.model;

/**
 * Classe d'une carte
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class Carte 
{
	/**
	 * Valeur de la carte
	 */
	private Valeur valeur;
	/**
	 * Symbole de la carte
	 */
	private Symbole symbole;
	/**
	 * Couleur de la carte
	 */
	private Couleur couleur;
	
	/**
	 * Crée un instance de carte 
	 * @param valeurCarte Valeur de la carte {AS, DEUX, TROIS... JOKER}
	 * @param symboleCarte Symbole de le carte {TREFLE, PIQUE, CARREAU, COEUR} 
	 * @param couleurCarte Couleur de la carte {ROUGE, NOIRE}
	 */
	public Carte(Valeur valeurCarte, Symbole symboleCarte, Couleur couleurCarte)
	{
		this.valeur = valeurCarte;
		this.symbole = symboleCarte;
		this.couleur = couleurCarte;
	}
	
	/**
	 * Retourne la valeur de la carte
	 * @return Valeur de la carte
	 */
	public Valeur getValeur()
	{
		return(this.valeur);
	}
		
	/**
	 * Retour le symbole de la carte
	 * @return Symbole de la carte
	 */
	public Symbole getSymbole()
	{
		return(this.symbole);
	}
	
	/**
	 * Retourne la couleur de la carte
	 * @return Couleur de la carte
	 */
	public Couleur getCouleur()
	{
		return(this.couleur);
	}
	
	/**
	 * Retourne un représentation/description sous forme de chaine de caractères de la carte
	 * @return Description de la carte sous forme de chaine de caractères
	 */
	public String toString()
	{
		StringBuffer description = new StringBuffer();
		description.append(this.valeur);
		if(this.valeur != Valeur.JOKER)
			description.append(" de " + this.symbole + " [" + this.couleur + "]");		
		return(description.toString());
	}
	
	/**
	 * Compare l'instance courante de carte avec un objet
	 * @param obj Objet à comparer avec l'instance courante
	 * @return Booléen à vrai si l'objet en paramètre correspond à l'objet courant, faux sinon
	 */
	public boolean equals(Object obj)
	{
		if(obj instanceof Carte)
		{
			Carte carte = (Carte)obj;
			if(carte.getValeur().equals(Valeur.JOKER))
			{
				if(carte.getValeur().equals(this.getValeur()))
					return(true);
			}
			else
			{
				if(carte.getValeur().equals(this.getValeur()) && carte.getSymbole().equals(this.getSymbole()) && carte.getCouleur().equals(this.getCouleur()))
					return(true);
			}
		}
		return(false);
	}
}
