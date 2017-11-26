import java.util.ArrayList;

public class JoueurVirtuel extends Joueur
{
	private StrategieDeJeu strategieCourante;

	public JoueurVirtuel(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		this.strategieCourante = new IADebutant(this);
	}

	public int choisirAction() 
	{
		int action = this.strategieCourante.choisirAction();
		if(action == 1)
			System.out.println(this.pseudo + " a choisi de jouer");
		else
			System.out.println(this.pseudo + " a choisi de piocher");
		return(action);
	}

	public Carte choisirCarte() 
	{
		Carte carteChoisie = this.strategieCourante.choisirCarte();
		System.out.println(this.pseudo + " a choisi une carte.");
		return(carteChoisie);
	}

	public Carte choisirCarteApresHuit() 
	{
		Carte carteDemandee = this.strategieCourante.choisirCarteApresHuit();
		System.out.println(this.pseudo + " a choisi le symbole a jouer.");
		return(carteDemandee);
	}

	public String choisirCarteSupplement() 
	{
		String indexCarte = this.strategieCourante.choisirCarteSupplement();
		System.out.println(this.pseudo + " a complété son dépôt.");
		return(indexCarte);
	}
	
	public String proposerAjouterCarte()
	{
		String decision = this.strategieCourante.proposerAjouterCarte();
		System.out.println(this.pseudo + " a choisi de compléter son dépôt.");
		return(decision);
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
