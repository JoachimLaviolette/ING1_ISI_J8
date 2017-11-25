public interface StrategieDeJeu 
{
	public boolean jouer(Carte carteAJouer);
	public int choisirAction();
	public Carte choisirCarte();
	public Carte choisirCarteApresHuit();
	public String choisirCarteSupplement();
}
