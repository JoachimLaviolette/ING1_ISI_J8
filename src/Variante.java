import java.util.*;

public abstract class Variante 
{
	protected Partie partieAssociee;
	protected Carte carteEnMemoire;
	protected Carte carteDemandee;
	protected int multiplicateurDePioche;
	protected int multiplicateurDeTour;
	
	public Variante(Partie partieAssociee)
	{
		this.partieAssociee = partieAssociee;
		this.carteEnMemoire = null;
		this.carteEnMemoire = null;
		this.multiplicateurDePioche = 0;
		this.multiplicateurDeTour = 0;
	}
	
	public void setPartieAssociee(Partie partie)
	{
		this.partieAssociee = partie;
	}
	
	public int getMultiplicateurDePioche()
	{
		return(this.multiplicateurDePioche);
	}
	
	public Carte getCarteDemandee()
	{
		return(this.carteDemandee);
	}
	
	public Partie getPartieAssociee() 
	{
		return(this.partieAssociee);
	}

	public Carte getCarteEnMemoire() 
	{
		return(this.carteEnMemoire);
	}	

	public abstract void initialiserPioche(LinkedList<Carte> pioche, int nombreJeuxDeCartes);
	public abstract void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte);
	public abstract int verifierMultiplicateurPioche();
	public abstract Carte getDerniereCarteSpecialePosee();
	public abstract boolean verifierConformiteCarte(Carte carteAJouer);
	
}
