public class IAConfirme implements StrategieDeJeu
{
	private JoueurVirtuel bot;
	
	public IAConfirme(JoueurVirtuel botUtilisateur)
	{
		this.bot = botUtilisateur;
	}
	
	public boolean jouer(Carte carteAJouer) 
	{
		return false;
	}

	public int choisirAction() 
	{
		return 0;
	}

	public Carte choisirCarte() 
	{
		return null;
	}

	public Carte choisirCarteApresHuit() 
	{
		return null;
	}

	public String choisirCarteSupplement() 
	{
		return null;
	}
	
	public String proposerAjouterCarte()
	{
		return("Y");
	}
}
