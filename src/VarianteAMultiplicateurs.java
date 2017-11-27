import java.util.LinkedList;

public abstract class VarianteAMultiplicateurs extends Variante
{
	protected int multiplicateurDePioche;
	protected int multiplicateurDeTour;
	
	public VarianteAMultiplicateurs(Partie partieAssociee) 
	{
		super(partieAssociee);
		this.multiplicateurDePioche = 0;
		this.multiplicateurDeTour = 0;
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
	
	//abstract methods
	public abstract void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte);
	public abstract boolean verifierConformiteCarte(Carte carteAJouer);
}
