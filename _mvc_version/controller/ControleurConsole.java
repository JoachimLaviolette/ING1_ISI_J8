package _mvc_version.controller;

import _mvc_version._exceptions.*;
import _mvc_version.model.*;
import _mvc_version.view.*;

/**
 * Classe du controleur de console
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurConsole 
{
	private Console console;
	private Jeu instanceDeJeu;
	private Partie instanceDePartie;
	
	/**
	 * Instancie le contrôleur de console
	 * @param c Vue console 
	 * @param jeu Instance de jeu
	 */
	public ControleurConsole(Console c, Jeu jeu)
	{
		this.console = c;
		this.instanceDeJeu = jeu;
	}	
	
	/**
	 * Demande à la vue console d'afficher les indications du menu principal et demande la saisie d'action au joueur
	 */
	public void actionMenuPrincipal()
	{
		this.console.afficherMenuPrincipal();
		String choixAction = null;
		do
		{
			try
			{
				choixAction = this.instanceDeJeu.choisirActionMenu();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixAction == null);
		switch(Integer.parseInt(choixAction))
		{
			case 1 :
				this.instanceDeJeu.jouer(); 
				break;
			case 2 :
				this.instanceDeJeu.parametres(); 
				break;	
			case 3 :
				this.instanceDeJeu.quitter();
				break;
		}
	}
	
	/**
	 * Passe au menu de paramétrage de partie en mode console
	 */	
	public void jouer()
	{
		this.parametrerPartie();	
	}
	
	/**
	 * Passe au menu de paramètres du jeu en mode console
	 */
	public void parametres()
	{
		this.modifierParametres();
	}
	
	/**
	 * Demande à la vue d'afficher les indications concernant les paramètres et demande la saisie de paramètre au joueur
	 */
	public void modifierParametres()
	{
		this.console.afficherMenuParametres();
		this.choisirParametre();
	}

	/**
	 * Demande la saisie du choix de paramètre à l'utilisateur
	 */
	public void choisirParametre()
	{
		String choixParametre = null;
		do
		{
			try
			{
				choixParametre = this.instanceDeJeu.choisirActionMenuParametres();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixParametre == null);
	}
	
	/**
	 * Demande à la vue d'afficher les indications de tour de jeu et demande la saisie d'action au joueur
	 */
	public void demarrerTour()
	{
		this.console.afficherMenuTour();
		String choixAction = this.choisirActionMenuTour();
		switch(Integer.parseInt(choixAction))
		{
			case 1 :
				this.instanceDePartie.jouer();
				break;
			case 2 :
				this.instanceDePartie.piocher();
				break;
			case 3 :
				this.instanceDePartie.changerVariante();
				break;
		}	
	}
	
	/**
	 * Demande à la vue d'afficher les cartes du joueur et demande la saisie de carte au joueur
	 */
	public void jouerTour()
	{
		this.console.afficherCartesMenuJouer();
		String choixCarte = null;
		Carte carte = null;
		do
		{
			choixCarte = this.choisirCarteMenuJouer();
			if(!choixCarte.trim().equals(""))
			{
				try
				{
					carte = this.instanceDePartie.getJoueurActif().getCarte(choixCarte);
				}
				catch(GameException e)
				{
					if(e instanceof UnexistingCardException)
						this.console.afficherErreur(e.getMessage());
				}
			}
		} while(choixCarte == null);
		this.instanceDePartie.jouer(carte);
	}
	
	/**
	 * Demande à la vue d'afficher les indications de changement de variante et demande la saisie de variante au joueur
	 */
	public void changerVariante()
	{
		this.console.afficherMenuChangementVariante();
		String choixVariante = this.choisirNouvelleVariante();
		this.instanceDePartie.changerVariante(choixVariante);
	}
	
	/**
	 * Demande la saisie de l'action du tour de jeu au joueur
	 * @return Réponse du joueur sous forme d'index numérique
	 */
	public String choisirActionMenuTour()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderActionTour();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choix == null);
		return(choix);
	}
	
	/**
	 * Demande la saisie de carte au joueur
	 * @return Index de la carte choisie
	 */
	public String choisirCarteMenuJouer()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderCarte();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choix == null);
		if(choix.trim().equals(""))
		{
			if(this.instanceDePartie.getRetourPossible())
				this.instanceDePartie.retourDepuisPC();
			else
			{
				this.instanceDePartie.terminerActionTour();
			}
		}
		return(choix);
	}
	
	/**
	 * Demande la saisie de la nouvelle variante au joueur
	 * @return Index de la variante choisie
	 */
	public String choisirNouvelleVariante()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderVariante();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choix == null);
		return(choix);
	}
	
	/**
	 * Demande la saisie de la couleur après dépôt de 8 au joueur
	 */
	public void choisirCouleurCarte()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderCouleurCarte();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}		
		} while(choix == null);
	}
	
	/**
	 * Demande la saisie du symbole après dépît de 8 au joueur
	 */
	public void choisirSymboleCarte()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderSymboleCarte();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}	
		} while(choix == null);
	}
	
	/**
	 * Demande au joueur s'il souhaite annoncer carte 
	 */
	public void choisirAnnoncerCarte()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.instanceDePartie.demanderAnnoncerCarte();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choix == null);
	}
	
	/**
	 * Demande à la vue d'afficher les indications concernant la demande de couleur et demande au joueur saisir la couleur
	 */
	public void demanderCouleurCarte()
	{
		this.console.afficherMenuChoixCouleur();
		this.choisirCouleurCarte();
	}
	
	/**
	 * Demande à la vue d'afficher les indications concernant la demande de symbole et demande au joueur de saisir le symbole
	 */
	public void demanderSymboleCarte()
	{
		this.console.afficherMenuChoixSymbole();
		this.choisirSymboleCarte();
	}
	
	public void demanderAnnoncerCarte()
	{
		this.console.afficherPropositionAnnoncerCarte();
		this.choisirAnnoncerCarte();		
	}
	
	/*
	 * Formate les données de partie et les transmet au modèle 
	 */
	public void parametrerPartie()
	{
		this.console.afficherMenuParametragePartie();
		this.console.afficherMenuParametrageVariante();
		String choixVariante = this.parametrerVariante();
		this.console.afficherMenuParametrageNombreJoueurs();
		int nbJ = this.parametrerNombreJoueurs();
		this.console.afficherMenuParametragePseudo();
		String pseudo = this.parametrerPseudo();
		this.console.afficherMenuParametrageDifficulte();		
		String choixStrategie = this.parametrerDifficulte();
		this.instanceDeJeu.preparerPartie(choixVariante, nbJ, pseudo, choixStrategie);	
	}
	
	/**
	 * Demande la saisie de la variante au joueur
	 * @return Index de la variante choisie
	 */
	public String parametrerVariante()
	{
		String choixVariante = null;
		do
		{
			try
			{
				choixVariante = this.instanceDeJeu.choisirVariante();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixVariante == null);
		return(choixVariante);
	}
	
	/**
	 * Demande la saisie du nombre de joueurs au joueur
	 * @return Nmbre de joueurs saisi
	 */
	public int parametrerNombreJoueurs()
	{	
		//save the actual number of decks 
		int nombreDeJeuxAvant = Integer.parseInt(this.instanceDeJeu.getOptionsDeJeu()[1]);	
		String choixNbJoueurs = null;	
		do
		{
			try
			{
				choixNbJoueurs = this.instanceDeJeu.choisirNombreJoueurs();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixNbJoueurs == null);
		int nombreDeJeuxApres = Integer.parseInt(this.instanceDeJeu.getOptionsDeJeu()[1]);	
		//check if the number of decks has been changed due to the number of players
		if(nombreDeJeuxAvant != nombreDeJeuxApres) //if yes, notice the user
			this.console.afficherChangementNombreDecks(nombreDeJeuxAvant, nombreDeJeuxApres);
		return(Integer.parseInt(choixNbJoueurs));
	}
	
	/**
	 * Demande la saisie du pseudo du joueur concret au joueur
	 * @return Pseudo du joueur concret
	 */
	public String parametrerPseudo()
	{		
		String choixPseudo = null;	
		do
		{
			try
			{
				choixPseudo = this.instanceDeJeu.choisirPseudo();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixPseudo == null);
		return(choixPseudo);
	}
	
	/**
	 * Demande la saisie de la difficulté au joueur
	 * @return Index de la difficulté
	 */
	public String parametrerDifficulte()
	{
		String choixStrategie = null;	
		do
		{
			try
			{
				choixStrategie = this.instanceDeJeu.choisirDifficulte();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}
		} while(choixStrategie == null);
		return(choixStrategie);
	}	
	
	/**
	 * Demande à la vue d'afficher que les paramètres ont été modifiés et met le thread en attente
	 */
	public void confirmerParametresModifies()
	{
		this.console.afficherConfirmationParametresModifies();
		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * Demande la saisie du nombre de joueurs (paramètres)
	 * @return Nombre de joueurs 
	 */
	public String choisirNombreJoueurs()
	{
		String reponse = null;
		do
		{
			try
			{
				reponse = this.instanceDeJeu.modifierNombreJoueurs();
			}
			catch(GameException e)
			{
				if(e instanceof UncompliantChoiceException)
					this.console.afficherErreur(e.getMessage());
			}	
		} while(reponse == null);
		return(reponse);
	}
	
	/**
	 * Demande la saisie du nombre de paquets de cartes (paramètres)
	 * @return Nombre de paquets de cartes
	 */
	public String choisirNombrePaquets()
	{	
		String reponse = null;
		do
		{
			try
			{
				reponse = this.instanceDeJeu.modifierNombrePaquetsCartes();
			}
			catch(GameException e)
			{
				this.console.afficherErreur(e.getMessage());
			}			
		} while(reponse == null);	
		return(reponse);
	}
	
	/**
	 * Demande a saisie de l'état du système de score (paramètres)
	 * @return Clé représentant l'état du système de score
	 */
	public String choisirActivationSystemeScore()
	{
		String reponse = null;
		do
		{
			try
			{
				reponse = this.instanceDeJeu.modifierSystemeScore();
			}
			catch(GameException e)
			{
				this.console.afficherErreur(e.getMessage());
			}			
		} while(reponse == null);
		return(reponse);
	}
	
	/**
	 * Demande à la vue d'afficher les indications de modification du nombre de joueurs </br>
	 * Demande la saisie du nombre de joueurs (paramètres)
	 */
	public void modificationNombreJoueurs()
	{
		this.console.afficherMenuModificationNombreJoueurs();
		String nvNbj = this.choisirNombreJoueurs();
		String nvScore = this.instanceDeJeu.getOptionsDeJeu()[2];
		if(nvScore != null)
		{
			if(nvScore.equals("Y"))
				this.instanceDeJeu.changerParametres(nvNbj, null, true);
			else
				this.instanceDeJeu.changerParametres(nvNbj, null, false);			
		}
	}
	
	/**
	 * Demande à la vue d'afficher les indications de modification du nombre de paquets de cartes </br>
	 * Demande la saisie du nombre de paquets de cartes (paramètres)
	 */
	public void modificationNombrePaquetsCartes()
	{
		this.console.afficherMenuModificationNombrePaquets();
		String nvNbp = this.choisirNombrePaquets();
		String nvScore = this.instanceDeJeu.getOptionsDeJeu()[2];
		if(nvScore != null)
		{
			if(nvScore.equals("Y"))
				this.instanceDeJeu.changerParametres(null, nvNbp, true);
			else
				this.instanceDeJeu.changerParametres(null, nvNbp, false);			
		}
	}
	
	/**
	 * Demande à la vue d'afficher les indications de modification de l'état du système de score </br>
	 * Demande la saisie de l'état du système de score (paramètres)
	 */
	public void modificationSystemeScore()
	{
		this.console.afficherMenuModificationSystemeScore();
		String nvScore = this.choisirActivationSystemeScore();
		if(nvScore != null)
		{
			if(nvScore.equals("Y"))
				this.instanceDeJeu.changerParametres(null, null, true);
			else
				this.instanceDeJeu.changerParametres(null, null, false);			
		}
	}

	/**
	 * Modifie l'instance de la partie courante	
	 * @param p Nouvelle instance de partie courante
	 */
	public void setPartie(Partie p)
	{
		this.instanceDePartie = p;
	}

}
