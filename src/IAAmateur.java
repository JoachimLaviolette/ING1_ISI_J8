import java.util.*;

public class IAAmateur implements StrategieDeJeu 
{
	private JoueurVirtuel bot;
	
	public IAAmateur(JoueurVirtuel botUtilisateur)
	{
		this.bot = botUtilisateur;
	}

	public int choisirAction() 
	{
		return(1);
	}

	public Carte choisirCarte() 
	{
		Carte derniereCartePosee = this.bot.getPartieEnCours().getDerniereCarte(this.bot.getPartieEnCours().getTalon());
		ArrayList<Carte> cartesJouables = new ArrayList<Carte>();
		ArrayList<Carte> cartesNonJouables = new ArrayList<Carte>();
		this.recupererCartesValides(derniereCartePosee, cartesJouables);
		this.recupererCartesNonValides(derniereCartePosee, cartesNonJouables);
		int indexSousPaquet = this.determinerIndexActionAExecuterAleatoirement();
		Carte carteChoisie;
		if(indexSousPaquet == 1)			
			carteChoisie = this.determinerCarteAJouerAleatoirement(cartesJouables);
		else
			carteChoisie = this.determinerCarteAJouerAleatoirement(cartesNonJouables);
		return(carteChoisie);
	}
	
	public String choisirCarte(boolean oblige) 
	{
		if(!oblige)
			return("");
		Carte derniereCartePosee = this.bot.getPartieEnCours().getDerniereCarte(this.bot.getPartieEnCours().getTalon());
		ArrayList<Carte> cartesJouables = new ArrayList<Carte>();
		this.recupererCartesValides(derniereCartePosee, cartesJouables);
		if(cartesJouables.isEmpty())
			return("");
		else
		{
			Carte carteChoisie = this.determinerCarteAJouerAleatoirement(cartesJouables);
			Iterator<Carte> iteCarteX = this.bot.getMain().iterator();
			int x = 0;
			boolean stop = false;
			while(iteCarteX.hasNext() && !stop)
			{
				Carte carteX = iteCarteX.next();
				if(carteX.equals(carteChoisie))
					stop = true;
				else
					x++;
			}
			return(x + "");
		}
	}

	public Carte choisirCarteApresHuit() 
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

	public String choisirCarteSupplement() 
	{
		int indexChoix = this.determinerIndexActionAExecuterAleatoirement();
		if(indexChoix == 1)
			return(this.choisirCarte(true));
		else
			return(this.choisirCarte(false));
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
	
	public int determinerIndexCarteAJouerAleatoirement(ArrayList<Carte> paquet)
	{
		int indexAleatoire = (int)(Math.random() * (paquet.size() - 0));	
		return(indexAleatoire);
	}
	
	public int determinerSymboleCarteAJouerAleatoirement()
	{
		int indexAleatoire = (int)(1 + (Math.random() * (5 - 1)));	
		return(indexAleatoire);
	}
	
	public void recupererCartesValides(Carte derniereCartePosee, ArrayList<Carte> cartes)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			//won't play a special/combo card
			if((carteX.getValeur().equals((derniereCartePosee.getValeur())) || carteX.getSymbole().equals(derniereCartePosee.getSymbole())) && 
				(!carteX.getValeur().equals(Valeur.AS) && !carteX.getValeur().equals(Valeur.JOKER) && !carteX.getValeur().equals(Valeur.HUIT)))
				cartes.add(carteX);				
		}
	}
	
	public void recupererCartesNonValides(Carte derniereCartePosee, ArrayList<Carte> cartes)
	{
		Iterator<Carte> iteCarte = this.bot.getMain().iterator();
		while(iteCarte.hasNext())
		{
			Carte carteX = (Carte)iteCarte.next();
			//won't play a special/combo card
			if((!carteX.getValeur().equals((derniereCartePosee.getValeur())) && !carteX.getSymbole().equals(derniereCartePosee.getSymbole())) && 
				(!carteX.getValeur().equals(Valeur.AS) && !carteX.getValeur().equals(Valeur.JOKER) && !carteX.getValeur().equals(Valeur.HUIT)))
				cartes.add(carteX);				
		}
	}
	
	public Carte determinerCarteAJouerAleatoirement(ArrayList<Carte> paquet)
	{
		int indexCarte = determinerIndexCarteAJouerAleatoirement(paquet);
		return(paquet.get(indexCarte));
	}
	
	public String proposerAjouterCarte()
	{
		int indexChoix = this.determinerIndexActionAExecuterAleatoirement();
		if(indexChoix == 1)
			return("Y");
		else
			return("N");
	}
}
