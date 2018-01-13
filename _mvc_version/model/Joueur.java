package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe définissant l'IA/la stratégie débutant
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class Joueur extends Observable
{
	/**
	 * Instance de la partie courante
	 */
	protected Partie partieEnCours;
	/**
	 * Pseudo du joueur
	 */
	protected String pseudo;
	/**
	 * Nombre de points du joueur
	 */
	protected int points;
	/**
	 * Main du joueur
	 */
	protected LinkedList<Carte> main;
	/**
	 * Etat de l'annonce carte du joueur
	 */
	protected boolean annonceCarte;
	/**
	 * Successeur du joueur
	 */
	protected Joueur joueurSuivant;
	/**
	 * Predecesseur du joueur
	 */
	protected Joueur joueurPrecedent;
	/**
	 * Adversaires du joueur
	 */
	protected ArrayList<Joueur> adversaires;
		
	/**
	 * Constructeur de joueur initialisant les propriétés communes aux joueurs
	 * @param partie Instance de la partie courante
	 * @param pseudoEnregistre Pseudo du bot
	 * @param joueurSuivant Joueur suivant le bot
	 * @param joueurPrecedent Joueur précédant le bot
	 * @param adversaires Liste des adversaires du bot
	 */
	public Joueur(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires)
	{
		super();
		this.partieEnCours = partie;
		this.pseudo = pseudoEnregistre;
		this.points = 0;
		this.main = new LinkedList<Carte>();
		this.annonceCarte = false;
		this.joueurSuivant = joueurSuivant;
		this.joueurPrecedent = joueurPrecedent;
		this.adversaires = adversaires;
	}
	
	/**
	 * Permet d'annoncer carte en plaçant un booléen d'état à vrai ou à faux
	 */
	public void annoncerCarte()
	{
		if(this.main.size() == 1)
			this.annonceCarte = true;
		/**
		 * Si une annonce de carte a été effectuée alors que le joueur possédait plus d'une carte, il doit piocher
		 */
		else
		{
			this.piocher();
			this.annonceCarte = false;
		}
	}
	
	/**
	 * Action de jouer une carte <br>
	 * Le joueur demande à la partie de jouer la carte spécifiée <br>
	 * Celle-ci va vérifier auprès de sa variante la conformité de la carte <br>
	 * Si la conformité est approuvée, la partie ajoute a carte au talon et renvoie true <br>
	 * L'algo ci-présent analyse le booléen <br>
	 * Si vrai, la carte est retirée de la main du joueur
	 * @param carteAJouer Carte à jouer
	 * @return Booléen à vrai si le joueur a joué, faux sinon
	 */
	public boolean jouer(Carte carteAJouer)
	{
		boolean resultat = this.partieEnCours.jouerCarte(carteAJouer);
		if(resultat)
		{
			this.main.remove(carteAJouer);
			this.partieEnCours.notifier("Mettre à jour main");
			return(true);
		}
		else
		{
			return(false);
		}
	}
		
	/**
	 * Action de piocher une carte
	 * @return Booléen à vrai si le jouur a pioché, faux sinon
	 */
	public boolean piocher()
	{
		/**
		 * Vérifie avant tout qu'il est possible de piocher
		 */
		if(this.partieEnCours.peutEncorePiocher())
		{
			/**
			 * Si possible de piocher, vérifie que la pioche n'est pas vide
			 */
			this.partieEnCours.verifierPioche();
			int x = 0;
			int multiplicateurDePioche;
			/**
			 * Dans le cas ou la variante courante serait une variante à multiplicateurs
			 * On vérifie la valeur du multiplicateur de pioche
			 * Si égal à 0, c'est qu'aucune carte spéciale faisant piochée n'a été jouée au tour précédent
			 * Sinon, fait piocher le joueur autant de fois qu'il le faut
			 */
			if(this.partieEnCours.getVarianteCourante() instanceof VarianteAMultiplicateurs)
				multiplicateurDePioche = ((VarianteAMultiplicateurs)this.partieEnCours.getVarianteCourante()).verifierMultiplicateurPioche();
			else
				multiplicateurDePioche = 0;
			do
			{
				if(this.main.size() == 1)
					this.annonceCarte = false;
				this.main.add(this.partieEnCours.getDerniereCarte(this.partieEnCours.getPioche())); //take the last card of the game's pick and adds it to the player's deck
				this.partieEnCours.getPioche().remove(this.partieEnCours.getDerniereCarte(this.partieEnCours.getPioche()));
				this.partieEnCours.notifier("Pioche effectuée");
				this.partieEnCours.notifier("Mettre à jour main");
				x++;
				if(!this.partieEnCours.peutEncorePiocher())
					this.partieEnCours.notifier("Pioche vide");
			} while (x < multiplicateurDePioche);
		}
		else
			this.partieEnCours.notifier("Pioche vide");
		return(true);
	}
	
	/**
	 * Retourne un index représentant l'action (générique) à exécuter
	 * @return Index représentant l'action à exécuter
	 */
	public abstract String choisirAction();	
	
	/**
	 * Retour l'index de la carte à jouer
	 * @return Index de la carte à jouer 
	 */
	public abstract String choisirCarte();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur, sous forme d'index {1, 2}
	 */
	public abstract String choisirSymboleCarteApresHuit();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public abstract String choisirCouleurCarteApresHuit();
	
	/**
	 * Retourne la réponse du joueur, sous forme d'une clé
	 * @return Réponse du joueur {Y, N}
	 */
	public abstract String proposerAnnoncerCarte();

	/**
	 * Retourne la réponse du joueur, sous forme d'une clé
	 * @return Réponse du joueur {Y, N}
	 */
	public abstract String proposerAjouterCarte();
		
	/**
	 * Retourne l'instance de partie en cours à laquelle participe le joueur
	 * @return Instance de partie en cours
	 */
	public Partie getPartieEnCours() 
	{
		return(this.partieEnCours);
	}
	
	/**
	 * Ajoute le joueur courant à une partie
	 * @param partie Partie à laquelle le joueur courant doit être ajouté
	 */
	public void ajouterAUnePartie(Partie partie)
	{
		if(this.partieEnCours == null)
			this.setPartieEnCours(partie);
	}
	
	/**
	 * Modifie l'instance de partie courante
	 * @param partie Instance de partie courante remplaçante
	 */
	private void setPartieEnCours(Partie partie)
	{
		this.partieEnCours = partie;
	}

	/**
	 * Retourne le pseudo du joueur
	 * @return Pseudo du joueur
	 */
	public String getPseudo() 
	{
		return(this.pseudo);
	}
	
	/**
	 * Modifie le pseudo du joueur
	 * @param pseudo Pseudo remplaçant
	 */
	public void setPseudo(String pseudo)
	{
		this.pseudo = pseudo;
	}

	/**
	 * Retourne le nombre de points du joueur
	 * @return Nombre de points du joueur
	 */
	public int getPoints() 
	{
		return(this.points);
	}

	/**
	 * Modifie le nombre de points du joueur
	 * @param points Nombre de points remplçant
	 */
	public void setPoints(int points) 
	{
		this.points = points;
	}

	/**
	 * Retourne la main du joueur
	 * @return Main du joueur
	 */
	public LinkedList<Carte> getMain()
	{
		return(this.main);
	}

	/**
	 * Modifie la main courante du joueur
	 * @param main Main remplaçante
	 */
	public void setMain(LinkedList<Carte> main) 
	{
		this.main = main;
	}
	
	/**
	 * Retourne la carte à l'index spécifié
	 * @param index Index de la carte à retourner
	 * @return Instance de la carte à l'index spécifié
	 * @throws NumberFormatException Si l'index spécifié n'est pas un nombre
	 * @throws UnexistingCardException Si aucune carte n'est trouvée à cet index
	 */
	public Carte getCarte(String index) throws NumberFormatException, UnexistingCardException
	{
		Carte carte = null;
		try
		{
			carte = ((Carte)(this.main.get(Integer.parseInt(index) - 1)));
		}
		catch(IndexOutOfBoundsException e)
		{
			throw new UnexistingCardException("Index de carte incorrect (carte inexistante");
		}
		return(carte);		
	}

	/**
	 * Retourne l'état d'annonce de carte
	 * @return Booléen à vrai si le joueur a annoncé carte; faux sinon
	 */
	public boolean getAnnonceCarte() 
	{
		return(this.annonceCarte);
	}

	/**
	 * Modifie l'état d'annonce de carte
	 * @param annonceCarte Booléen représentant l'état d'annonce de carte
	 */
	public void setAnnonceCarte(boolean annonceCarte) 
	{
		this.annonceCarte = annonceCarte;
	}

	/**
	 * Retourne le joueur suivant le joueur courant
	 * @return Joueur suivant le joueur courant
	 */
	public Joueur getJoueurSuivant() 
	{
		return(this.joueurSuivant);
	}

	/**
	 * Modifie le joueur suivant le joueur courant
	 * @param joueurSuivant Joueur suivant remplaçant
	 */
	public void setJoueurSuivant(Joueur joueurSuivant) 
	{
		this.joueurSuivant = joueurSuivant;
	}

	/**
	 * Retourne le joueur précédant le joueur courant
	 * @return Joueur précédant le joueur courant
	 */
	public Joueur getJoueurPrecedent() 
	{
		return(this.joueurPrecedent);
	}

	/**
	 * Modifie le joueur précédant le joueur courant
	 * @param joueurPrecedent Joueur précédant remplaçant
	 */
	public void setJoueurPrecedent(Joueur joueurPrecedent) 
	{
		this.joueurPrecedent = joueurPrecedent;
	}

	/**
	 * Retourne la liste d'adversaires du joueurs courant
	 * @return Liste d'adversaires du joueur courant
	 */
	public ArrayList<Joueur> getAdversaires() 
	{
		return(this.adversaires);
	}

	/**
	 * Modifie la liste d'adversaires du joueurs courant
	 * @param adversaires Liste d'adversaires du joueurs courant
	 */
	public void setAdversaires(ArrayList<Joueur> adversaires) 
	{
		this.adversaires = adversaires;
	}	
}
