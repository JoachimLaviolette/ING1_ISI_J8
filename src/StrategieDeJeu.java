public interface StrategieDeJeu 
{
	public int choisirAction();
	public Carte choisirCarte();
	public Carte choisirCarteApresHuit();
	public String choisirCarteSupplement();
	public String proposerAjouterCarte();
}
