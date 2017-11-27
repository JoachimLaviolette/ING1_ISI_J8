public class IADebutant implements StrategieDeJeu 
{
	private JoueurVirtuel bot;
	
	public IADebutant(JoueurVirtuel botUtilisateur)
	{
		this.bot = botUtilisateur;
	}

	public int choisirAction() 
	{
		int indexAction = this.determinerIndexActionAExecuterAleatoirement();
		return(indexAction);
	}

	public Carte choisirCarte() 
	{
		int indexCarte = this.determinerIndexCarteAJouerAleatoirement();
		Carte carteChoisie = (Carte)this.bot.getMain().toArray()[indexCarte];
		return(carteChoisie);
	}

	public Carte choisirSymboleCarteApresHuit() 
	{
		int indexSymbole = this.determinerSymboleCarteAJouerAleatoirement();
		Carte carte = null;
		switch(indexSymbole)
		{
			case 1 : carte = new Carte(null, Symbole.CARREAU, null); break;
			case 2 : carte = new Carte(null, Symbole.COEUR, null); break;		
			case 3 : carte = new Carte(null, Symbole.PIQUE, null); break;
			case 4 : carte = new Carte(null, Symbole.TREFLE, null); break;
		}
		return(carte);
	}
	
	public Carte choisirCouleurCarteApresHuit() 
	{
		int indexCouleur = this.determinerCouleurCarteAJouerAleatoirement();
		Carte carte = null;
		switch(indexCouleur)
		{
			case 1 : carte = new Carte(null, null, Couleur.ROUGE); break;
			case 2 : carte = new Carte(null, null, Couleur.NOIRE); break;			
		}
		return(carte);
	}

	public String choisirCarteSupplement() 
	{
		return("");
	}
	
	public int determinerIndexActionAExecuterAleatoirement()
	{
		int indexAleatoire = (int)(1 + (Math.random() * (3 - 1)));	
		return(indexAleatoire);
	}
	
	public int determinerIndexCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(Math.random() * (this.bot.getMain().size() - 0));	
		return(indexAleatoire);
	}
	
	public int determinerSymboleCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(Math.random() * (5 - 1));	
		return(indexAleatoire);
	}
	
	public int determinerCouleurCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(1 + (Math.random() * (3 - 1)));	
		return(indexAleatoire);
	}
	
	public String proposerAjouterCarte()
	{
		return("N");
	}
}
