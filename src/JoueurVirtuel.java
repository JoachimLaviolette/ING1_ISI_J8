import java.util.ArrayList;

public class JoueurVirtuel extends Joueur
{
	private StrategieDeJeu strategieCourante;

	public JoueurVirtuel(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		this.strategieCourante = new IADebutant();
	}

	public boolean jouer(Carte carteAJouer) 
	{
		return(this.strategieCourante.jouer(carteAJouer));
	}

	public int choisirAction() 
	{
		return(this.strategieCourante.choisirAction());
	}

	public Carte choisirCarte() 
	{
		return(this.strategieCourante.choisirCarte());
	}

	public Carte choisirCarteApresHuit() 
	{
		return(this.strategieCourante.choisirCarteApresHuit());
	}

	public String choisirCarteSupplement() 
	{
		return(this.strategieCourante.choisirCarteSupplement());
	}

	//getters and setters
	public StrategieDeJeu getStrategieCourante() 
	{
		return(this.strategieCourante);
	}

	public void setStrategieCourante(StrategieDeJeu strategieCourante) 
	{
		this.strategieCourante = strategieCourante;
	}
}
