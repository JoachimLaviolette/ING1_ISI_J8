public class Carte 
{
	private Valeur valeur;
	private Symbole symbole;
	private Couleur couleur;
	
	public Carte(Valeur valeurCarte, Symbole symboleCarte, Couleur couleurCarte)
	{
		this.valeur = valeurCarte;
		this.symbole = symboleCarte;
		this.couleur = couleurCarte;
	}
	
	public Valeur getValeur()
	{
		return(this.valeur);
	}
		
	public Symbole getSymbole()
	{
		return(this.symbole);
	}
	
	public Couleur getCouleur()
	{
		return(this.couleur);
	}
	
	public String toString()
	{
		StringBuffer description = new StringBuffer();
		description.append(this.valeur);
		if(this.valeur != Valeur.JOKER)
			description.append(" de " + this.symbole + " [" + this.couleur + "]");		
		return(description.toString());
	}
}
