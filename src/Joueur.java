import java.util.*;

public abstract class Joueur 
{
	protected Partie partieEnCours;
	protected String pseudo;
	protected int points;
	protected HashSet<Carte> main;
	protected boolean annonceCarte;
	protected Joueur joueurSuivant;
	protected Joueur joueurPrecedent;
	protected ArrayList<Joueur> adversaires;
		
	public Joueur(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires)
	{
		this.partieEnCours = partie;
		this.pseudo = pseudoEnregistre;
		this.points = 0;
		this.main = new HashSet<Carte>();
		this.annonceCarte = false;
		this.joueurSuivant = joueurSuivant;
		this.joueurPrecedent = joueurPrecedent;
		this.adversaires = adversaires;
	}
	
	public void annoncerCarte()
	{
		if(this.main.size() == 1)
			this.annonceCarte = true;
		else
		{
			this.piocher();
			this.annonceCarte = false;
		}
	}
	
	public boolean jouer(Carte carteAJouer)
	{
		boolean resultat = this.partieEnCours.jouerCarte(carteAJouer);
		if(resultat)
		{
			this.main.remove(carteAJouer);
			return(true);
		}
		else //make the player pick a card
		{
			System.out.println(this.pseudo + " a joué une mauvaise carte et a du pioché.");
			this.piocher();
			return(false);
		}
		//if error (see with future exceptions), return false
	}
		
	public boolean piocher()
	{
		this.partieEnCours.verifierPioche();
		int multiplicateurDePioche = this.partieEnCours.getVarianteCourante().verifierMultiplicateurPioche();
		int x = 0;
		do
		{
			this.main.add(this.partieEnCours.getDerniereCarte(this.partieEnCours.getPioche())); //take the last card of the game's pick and adds it to the player's deck
			this.partieEnCours.getPioche().remove(this.partieEnCours.getDerniereCarte(this.partieEnCours.getPioche()));
			x++;
		} while (x < multiplicateurDePioche);
		return(true);
		//if a problem occurs, return false (see later with the exceptions)
	}
	
	public void terminerTour() //useful for the future GUI
	{
		this.partieEnCours.setJoueurActif(this.joueurSuivant);
	}
	
	//abstract methods 
	public abstract int choisirAction();	
	public abstract Carte choisirCarte();
	public abstract Carte choisirCarteApresHuit();
	public abstract String choisirCarteSupplement();
	public abstract String proposerAjouterCarte();
		
	//getters and setters
	public Partie getPartieEnCours() 
	{
		return(this.partieEnCours);
	}
	
	public void ajouterAUnePartie(Partie partie)
	{
		if(this.partieEnCours == null)
			this.setPartieEnCours(partie);
	}
	
	private void setPartieEnCours(Partie partie)
	{
		this.partieEnCours = partie;
	}

	public String getPseudo() 
	{
		return(this.pseudo);
	}
	
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
	}

	public int getPoints() 
	{
		return(this.points);
	}

	public void setPoints(int points) 
	{
		this.points = points;
	}

	public HashSet<Carte> getMain()
	{
		return(this.main);
	}

	public void setMain(HashSet<Carte> main) 
	{
		this.main = main;
	}

	public boolean getAnnonceCarte() 
	{
		return(this.annonceCarte);
	}

	public void setAnnonceCarte(boolean annonceCarte) 
	{
		this.annonceCarte = annonceCarte;
	}

	public Joueur getJoueurSuivant() 
	{
		return(this.joueurSuivant);
	}

	public void setJoueurSuivant(Joueur joueurSuivant) 
	{
		this.joueurSuivant = joueurSuivant;
	}

	public Joueur getJoueurPrecedent() 
	{
		return(this.joueurPrecedent);
	}

	public void setJoueurPrecedent(Joueur joueurPrecedent) 
	{
		this.joueurPrecedent = joueurPrecedent;
	}

	public ArrayList<Joueur> getAdversaires() 
	{
		return(this.adversaires);
	}

	public void setAdversaires(ArrayList<Joueur> adversaires) 
	{
		this.adversaires = adversaires;
	}
}
