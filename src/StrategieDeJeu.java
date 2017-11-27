public interface StrategieDeJeu 
{
	public int choisirAction();
	public Carte choisirCarte();
	public Carte choisirSymboleCarteApresHuit();
	public Carte choisirCouleurCarteApresHuit();
	public String choisirCarteSupplement();
	public String proposerAjouterCarte();
}
