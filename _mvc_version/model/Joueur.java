package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe d�finissant l'IA/la strat�gie d�butant
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
	 * Constructeur de joueur initialisant les propri�t�s communes aux joueurs
	 * @param partie Instance de la partie courante
	 * @param pseudoEnregistre Pseudo du bot
	 * @param joueurSuivant Joueur suivant le bot
	 * @param joueurPrecedent Joueur pr�c�dant le bot
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
	 * Permet d'annoncer carte en pla�ant un bool�en d'�tat � vrai ou � faux
	 */
	public void annoncerCarte()
	{
		if(this.main.size() == 1)
			this.annonceCarte = true;
		/**
		 * Si une annonce de carte a �t� effectu�e alors que le joueur poss�dait plus d'une carte, il doit piocher
		 */
		else
		{
			this.piocher();
			this.annonceCarte = false;
		}
	}
	
	/**
	 * Action de jouer une carte <br>
	 * Le joueur demande � la partie de jouer la carte sp�cifi�e <br>
	 * Celle-ci va v�rifier aupr�s de sa variante la conformit� de la carte <br>
	 * Si la conformit� est approuv�e, la partie ajoute a carte au talon et renvoie true <br>
	 * L'algo ci-pr�sent analyse le bool�en <br>
	 * Si vrai, la carte est retir�e de la main du joueur
	 * @param carteAJouer Carte � jouer
	 * @return Bool�en � vrai si le joueur a jou�, faux sinon
	 */
	public boolean jouer(Carte carteAJouer)
	{
		boolean resultat = this.partieEnCours.jouerCarte(carteAJouer);
		if(resultat)
		{
			this.main.remove(carteAJouer);
			this.partieEnCours.notifier("Mettre � jour main");
			return(true);
		}
		else
		{
			return(false);
		}
	}
		
	/**
	 * Action de piocher une carte
	 * @return Bool�en � vrai si le jouur a pioch�, faux sinon
	 */
	public boolean piocher()
	{
		/**
		 * V�rifie avant tout qu'il est possible de piocher
		 */
		if(this.partieEnCours.peutEncorePiocher())
		{
			/**
			 * Si possible de piocher, v�rifie que la pioche n'est pas vide
			 */
			this.partieEnCours.verifierPioche();
			int x = 0;
			int multiplicateurDePioche;
			/**
			 * Dans le cas ou la variante courante serait une variante � multiplicateurs
			 * On v�rifie la valeur du multiplicateur de pioche
			 * Si �gal � 0, c'est qu'aucune carte sp�ciale faisant pioch�e n'a �t� jou�e au tour pr�c�dent
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
				this.partieEnCours.notifier("Pioche effectu�e");
				this.partieEnCours.notifier("Mettre � jour main");
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
	 * Retourne un index repr�sentant l'action (g�n�rique) � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 */
	public abstract String choisirAction();	
	
	/**
	 * Retour l'index de la carte � jouer
	 * @return Index de la carte � jouer 
	 */
	public abstract String choisirCarte();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur, sous forme d'index {1, 2}
	 */
	public abstract String choisirSymboleCarteApresHuit();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public abstract String choisirCouleurCarteApresHuit();
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl�
	 * @return R�ponse du joueur {Y, N}
	 */
	public abstract String proposerAnnoncerCarte();

	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl�
	 * @return R�ponse du joueur {Y, N}
	 */
	public abstract String proposerAjouterCarte();
		
	/**
	 * Retourne l'instance de partie en cours � laquelle participe le joueur
	 * @return Instance de partie en cours
	 */
	public Partie getPartieEnCours() 
	{
		return(this.partieEnCours);
	}
	
	/**
	 * Ajoute le joueur courant � une partie
	 * @param partie Partie � laquelle le joueur courant doit �tre ajout�
	 */
	public void ajouterAUnePartie(Partie partie)
	{
		if(this.partieEnCours == null)
			this.setPartieEnCours(partie);
	}
	
	/**
	 * Modifie l'instance de partie courante
	 * @param partie Instance de partie courante rempla�ante
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
	 * @param pseudo Pseudo rempla�ant
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
	 * @param points Nombre de points rempl�ant
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
	 * @param main Main rempla�ante
	 */
	public void setMain(LinkedList<Carte> main) 
	{
		this.main = main;
	}
	
	/**
	 * Retourne la carte � l'index sp�cifi�
	 * @param index Index de la carte � retourner
	 * @return Instance de la carte � l'index sp�cifi�
	 * @throws NumberFormatException Si l'index sp�cifi� n'est pas un nombre
	 * @throws UnexistingCardException Si aucune carte n'est trouv�e � cet index
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
	 * Retourne l'�tat d'annonce de carte
	 * @return Bool�en � vrai si le joueur a annonc� carte; faux sinon
	 */
	public boolean getAnnonceCarte() 
	{
		return(this.annonceCarte);
	}

	/**
	 * Modifie l'�tat d'annonce de carte
	 * @param annonceCarte Bool�en repr�sentant l'�tat d'annonce de carte
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
	 * @param joueurSuivant Joueur suivant rempla�ant
	 */
	public void setJoueurSuivant(Joueur joueurSuivant) 
	{
		this.joueurSuivant = joueurSuivant;
	}

	/**
	 * Retourne le joueur pr�c�dant le joueur courant
	 * @return Joueur pr�c�dant le joueur courant
	 */
	public Joueur getJoueurPrecedent() 
	{
		return(this.joueurPrecedent);
	}

	/**
	 * Modifie le joueur pr�c�dant le joueur courant
	 * @param joueurPrecedent Joueur pr�c�dant rempla�ant
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
