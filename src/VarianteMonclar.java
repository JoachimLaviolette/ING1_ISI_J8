import java.util.*;

public class VarianteMonclar extends Variante
{
	public VarianteMonclar(Partie partieAssociee) 
	{
		super(partieAssociee);
	}

	public void initialiserPioche(LinkedList<Carte> pioche, int nombreJeuxDeCartes) 
	{
				
	}
	
	public void appliquerEffetCarte(LinkedList<Carte> cartesAJouer, Joueur joueurCarte)
	{
		
	}	
	
	public int verifierMultiplicateurPioche()
	{
		return(0);
	}
	
	public Carte getDerniereCarteSpecialePosee()
	{
		return(null);
	}
	
	public boolean verifierConformiteCarte(Carte carteAJouer) 
	{
		return(false);
	}
}