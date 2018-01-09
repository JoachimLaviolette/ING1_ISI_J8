package _mvc_version.view;

import java.util.*;

import _mvc_version._exceptions.GameException;
import _mvc_version.controller.*;
import _mvc_version.model.*;

/**
 * Classe de la vue console
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class Console implements Observer, Runnable 
{
	//Model elements
	private Jeu instanceDeJeu;	
	private Partie instanceDePartie;
	private Thread threadConsole;
	
	//Controller
	private ControleurConsole controleur;		
	
	/**
	 * Cr�e la vue console
	 * @param jeu Instance de jeu
	 */
	public Console(Jeu jeu)
	{
		this.instanceDeJeu = jeu;
		
		/**
		 * La vue observe l'instance de jeu
		 */
		this.instanceDeJeu.addObserver(this);
		
		/**
		 * Cr�ation du contr�leur g�rant la vue console
		 */
		this.controleur = new ControleurConsole(this, this.instanceDeJeu);
				
		/**
		 * La vue est g�r�e par un thread 
		 */
		this.threadConsole = new Thread(this);
		this.threadConsole.start();
	}

	/**
	 * Code exc�ut� par le thread console
	 */
	public void run() 
	{
		this.controleur.actionMenuPrincipal();
	}
	
	/**
	 * Affiche une erreur survenue en fonction du message d'erreur
	 * @param messageErreur Message d'erreur r�cup�r� de l'exception lev�e � la suite du probl�me
	 */
	public void afficherErreur(String messageErreur)
	{
		if(this.instanceDePartie == null ||
			(this.instanceDePartie != null && this.instanceDePartie.getJoueurActif() instanceof JoueurConcret))
		{
			StringBuffer texte = new StringBuffer();
			texte.append(messageErreur);		
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche les indications du menu principal
	 */	
	public void afficherMenuPrincipal()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n------------- BIENVENUE DANS LE J8-2017 v1.0 -------------\n::::::::::::::::::::: MENU PRINCIPAL :::::::::::::::::::::\n\n");
		texte.append("1) Jouer une partie\n");
		texte.append("2) Param�tres\n");
		texte.append("3) Quitter");
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche le header du menu de param�trage de partie
	 */
	public void afficherMenuParametragePartie()
	{
		//display the game menu title
		StringBuffer texte = new StringBuffer();
		texte.append("\n::::::::::::::::::: PARAMETRAGE DE LA PARTIE ::::::::::::::::::::\n");
		System.out.println(texte.toString());	
	}
	
	/**
	 * Affiche les indications concernant le choix de variante
	 */
	public void afficherMenuParametrageVariante()
	{
		//display the variant selection indications
		StringBuffer texte = new StringBuffer();
		texte.append("Jouer une partie > Choix de la variante");
		texte.append("\n---------------------------------------\n[ETAPE 1] Veuillez choisir une des variantes propos�es (par d�faut, la variante Minimale sera choisie) :\n");
		texte.append("\n1) Variante Minimale\n");
		texte.append("2) Variante Monclar\n");
		texte.append("3) Variante 4\n");
		texte.append("4) Variante 5\n");
		texte.append("5) Variante Personnalis�e\n\n");
		texte.append("                       [Appuyer sur la touche entr�e pour continuer]");
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le choix de variante a �t� effectu�
	 * @param choixVariante Choix de la variante
	 */
	public void afficherChoixVarianteEffectue(String choixVariante)
	{
		StringBuffer texte = new StringBuffer("\nLa variante ");
		if(choixVariante.equals("1"))
			texte.append("Minimale");
		else if(choixVariante.equals("2"))
			texte.append("Monclar");
		else if(choixVariante.equals("3"))
			texte.append("4");
		else if(choixVariante.equals("4"))
			texte.append("5");
		else
			texte.append("Personnalis�e");
		texte.append(" a �t� choisie.\n");
		System.out.println(texte.toString());	
	}
	
	/**
	 * Affiche les indications concernant le choix du nombre de joueurs
	 */
	public void afficherMenuParametrageNombreJoueurs()
	{
		//display the players number selection indications
		StringBuffer texte = new StringBuffer();
		texte.append("\nJouer une partie > Enregistrement du nombres joueur");
		texte.append("\n---------------------------------------------------\n[ETAPE 2] Veuillez choisir le nombre de joueurs (par d�faut, " + this.instanceDeJeu.getOptionsDeJeu()[0] + " joueurs participeront � la partie) :\n");
		texte.append("\n                       [Appuyer sur la touche entr�e pour continuer]");
		System.out.println(texte.toString());	
	}

	/**
	 * Affiche les indications concernant le choix du pseudo
	 */
	public void afficherMenuParametragePseudo()
	{
		//display the pseudo selection indications
		StringBuffer texte = new StringBuffer();
		texte.append("\nJouer une partie > Choix du pseudo");
		texte.append("\n---------------------------------------------------\n[ETAPE 3] Veuillez saisir votre pseudo :\n\n");
		texte.append("                       [Appuyer sur la touche entr�e pour continuer]\n\n");
		System.out.println(texte.toString());
	}
	
	public void afficherMenuParametrageDifficulte()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\nJouer une partie > Choix de la difficulte");
		texte.append("\n---------------------------------------------------\n[ETAPE 4] Veuillez choisir une des difficult�s propos�es (par d�faut, les bot seront d�butants) :\n");
		texte.append("\n1) D�butant\n");
		texte.append("2) Amateur\n");
		texte.append("3) Confirm�\n");
		texte.append("                       [Appuyer sur la touche entr�e pour continuer]");
		System.out.println(texte.toString());
	}
	
	public void afficherChargementSalon()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n--------------------------------------------------------------------------------");
		texte.append("\n           ... LOADING ...                           Cr�ation du salon...       ");
		texte.append("\n--------------------------------------------------------------------------------");
		System.out.println(texte.toString());
		this.attendre(1500);
		texte = new StringBuffer();
		texte.append("\n--------------------------------------------------------------------------------");
		texte.append("\n           ... DONE ! ...                La partie a �t� cr�� avec succ�s !     ");
		texte.append("\n--------------------------------------------------------------------------------");
		this.attendre(1500);
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le nombre de jeux de cartes a d$u �tre ajust� en raison du nombre de joueurs
	 * @param nbJeuxAvant Nombre de jeux de cartes avant la modification
	 * @param nbJeuxApres Nombre de jeux de cartes apr�s la modification
	 */
	public void afficherChangementNombreDecks(int nbJeuxAvant, int nbJeuxApres)
	{
		StringBuffer texte = new StringBuffer("");
		texte.append("\nNous avons d� ajouter " + (nbJeuxApres - nbJeuxAvant) + " en raison du nombre de joueurs. \nVous jouez maintenant avec " + this.instanceDeJeu.getOptionsDeJeu()[1] + " paquets de cartes." );
		System.out.println(texte.toString());	
	}
	
	/**
	 * Affiche les indications concernant les param�tres du jeu
	 */		
	public void afficherMenuParametres()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n::::::::::::::::::: PARAMETRES DE JEU ::::::::::::::::::::\n\n");
		texte.append("1) Nombre de joueurs\n");
		texte.append("2) Nombre de jeux de cartes\n");
		texte.append("3) Syst�me de points\n");
		texte.append("                       [Appuyer sur la touche entr�e pour revenir au menu principal]\n\n");
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche les indications concernant la modification du nombre de joueurs (param�tres)
	 */
	public void afficherMenuModificationNombreJoueurs()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\nParam�tres > Nombre de joueurs");
		texte.append("\n-\nVeuillez choisir le nombre de joueurs (par d�faut, " + this.instanceDeJeu.getOptionsDeJeu()[0] + " joueurs sont d�finis) :");
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche les indications concernant la modification du nombre de paquets de cartes (param�tres)
	 */
	public void afficherMenuModificationNombrePaquets()
	{ 
		StringBuffer texte = new StringBuffer();
		texte.append("\nParam�tres > Nombre de jeux de cartes");
		texte.append("\n-\nVeuillez choisir le nombre de jeux de cartes (par d�faut, " + this.instanceDeJeu.getOptionsDeJeu()[1]);
		if(Integer.parseInt(this.instanceDeJeu.getOptionsDeJeu()[1]) == 1)
			texte.append(" jeu de cartes est d�fini) :");
		else
			texte.append(" jeux de cartes sont d�finis) :\n\n");
		texte.append("                       [Appuyer sur la touche entr�e pour annuler]\n");
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche les indications concernant la modification de l'�tat du syst�me de score (param�tres)
	 */
	public void afficherMenuModificationSystemeScore()
	{ 
		StringBuffer texte = new StringBuffer();
		texte.append("\nParam�tres > Syst�me de points");
		texte.append("\n-\nSouhaitez-vous activer le syst�me de points ? [Y/N]");
		if(this.instanceDeJeu.getOptionsDeJeu()[2].equals("Y"))
			texte.append("\n[Actuellement : ACTIF]");
		else
			texte.append("\n[Actuellement : NON ACTIF]");
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que l'�tat du syst�me de score a chang�
	 * @param etat Etat du syst�me de score
	 */
	public void afficherActivationSystemeScore(String etat)
	{
		StringBuffer texte = new StringBuffer();
		if(etat.equals("Y"))
			texte.append("\nLe syst�me de points est d�sormais activ� !\n");
		else
			texte.append("\nLe syst�me de points est d�sormais d�sactiv� !\n");
		System.out.println(texte.toString());
	}	
	
	/**
	 * Affiche les indications concernant le menu in-game	
	 */
	public void afficherMenuTour()
	{
		this.afficherCarteTalon();
		this.afficherCarteDemandee();
		this.afficherActionsMenuTour();	
	}
	
	/**
	 * Affiche la carte du talon
	 */
	public void afficherCarteTalon()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("\n-----------------------------------------------------\n");
			texte.append(">> Carte du talon --> " + this.instanceDePartie.getDerniereCarte(this.instanceDePartie.getTalon()).toString() + " <<\n");
			texte.append("-----------------------------------------------------");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Signale si une carte a �t� demand�e
	 */
	public void afficherCarteDemandee()
	{
		StringBuffer texte = new StringBuffer();
		String elementCarteDemandee = "";
		try
		{
			elementCarteDemandee = this.instanceDePartie.getVarianteCourante().getElementCarteDemandee();
		}
		catch(GameException e)
		{
			e.printStackTrace();
		}	
		if(!elementCarteDemandee.equals(""))
			texte.append("__ [ATTENTION] __ Le pr�c�dent joueur a jou� un 8 et a demand� de jouer du " + elementCarteDemandee);
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche les indications concernant les actions du menu in-game
	 */
	public void afficherActionsMenuTour()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + ", veuillez choisir une action � ex�cuter :\n");
			texte.append("1) Jouer\n");
			texte.append("2) Piocher\n");
			texte.append("3) Changer de variante\n\n");		
			texte.append(this.getInfosMainJoueur());
			texte.append(this.getInfosPaquets());
			texte.append(this.getInfosAdversaires());
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche le menu de jeu avec les cartes � jouer
	 */
	public void afficherCartesMenuJouer()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("Veuillez choisir une carte � jouer :\n");
			for(int x = 0 ; x < this.instanceDePartie.getJoueurActif().getMain().size(); x++)
				texte.append((x+1) + ") " + ((Carte)this.instanceDePartie.getJoueurActif().getMain().get(x)).toString() + "\n");
			texte.append("\n                       [Appuyer sur la touche entr�e pour annuler]");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche les indications concernant la s�lection de symbole apr�s 8
	 */
	public void afficherMenuChoixSymbole()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("\nVeuillez choisir le symbole que vous souhaitez que l'on joue par la suite :\n");
			texte.append("1) Carreau\n");
			texte.append("2) Coeur\n");
			texte.append("3) Pique\n");
			texte.append("4) Tr�fle\n");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche les indications concernant la s�lection de couleur apr�s 8
	 */
	public void afficherMenuChoixCouleur()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("\nVeuillez choisir la couleur que vous souhaitez que l'on joue par la suite :\n");
			texte.append("1) Rouge\n");
			texte.append("2) Noire\n");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche les indications concernant le changement de variante
	 */
	public void afficherMenuChangementVariante()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("Veuillez choisir une des variantes propos�es :\n");
			texte.append("1) Variante Minimale\n");
			texte.append("2) Variante Monclar\n");
			texte.append("3) Variante 4\n");
			texte.append("4) Variante 5\n");
			texte.append("5) Variante Personnalis�e\n");
			texte.append("\n                       [Appuyer sur la touche entr�e pour annuler]");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Affiche les indications concernant la demande d'annonce de carte
	 */
	public void afficherPropositionAnnoncerCarte()
	{
		if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
		{
			StringBuffer texte = new StringBuffer();
			texte.append("\nIl ne vous reste plus qu'une carte ! Souhaitez-vous annoncer carte ? [Y/N]\n");
			System.out.println(texte.toString());
		}
	}
	
	/**
	 * Signale que la modification des param�tres a �t� effectu�e
	 */
	public void afficherConfirmationParametresModifies()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("Vos changements ont �t� pris en compte !");
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le changement de variante a �t� effectu�
	 */
	public void afficherChangementVarianteEffectue()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("La variante courante a �t� modifi�e !");
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le joueur actif a pioch�
	 */
	public void afficherPiocheEffectuee()
	{
		StringBuffer texte = new StringBuffer();
		texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + " a pioch�.");	
		System.out.println(texte.toString());
	}
	
	/**
	 * Affiche que le joueur actif a jou� une carte
	 * @param carteJouee Carte jou�e par le joueur
	 */
	public void afficherCarteJouee(Carte carteJouee)
	{
		StringBuffer texte = new StringBuffer();
		texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + " a jou� un : " + carteJouee.toString());	
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le joueur n'a pas pu piocher d� � la pioche vide
	 */
	public void afficherImpossibiliteDePiocher()
	{
		StringBuffer texte = new StringBuffer();
		texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + " n'a pas pu piocher.\nIl ne reste qu'une carte sur le talon et la pioche est vide.");	
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le joueur actif a termin� son tour
	 */
	public void signalerTerminerTour()
	{
		StringBuffer texte = new StringBuffer();
		texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + " a termin� son tour.\n");	
		System.out.println(texte.toString());
	}
	
	/**
	 * Signale que le joueur actif a annonc� "CARTE"
	 */
	public void signalerAnnonceCarte()
	{
		StringBuffer texte = new StringBuffer();
		texte.append(this.instanceDePartie.getJoueurActif().getPseudo() + " a annonc� \"CARTE\" !");
		System.out.println(texte.toString());
	}
	
	/**
	 * Annonce la fin de la partie
	 */
	public void annoncerFinDePartie()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("--------------------------------------------------------------------------------");
		texte.append("\n                               ::: VICTOIRE :::                                 ");
		texte.append("\n--------------------------------------------------------------------------------");
		texte.append("\nLa partie est termin�e !\nLe gagnant est... " + this.instanceDePartie.getJoueurGagnant().getPseudo() + " !\nF�licitations � toi !\n");
		System.out.println(texte.toString());
	}
	
	/**
	 * R�cup�re les informations quant � la main du joueur concret
	 * @return Informations quant � la main du joueur concret
	 */
	public String getInfosMainJoueur()
	{
		StringBuffer texte = new StringBuffer("");
		texte.append("             [Vous : " + this.instanceDePartie.getJoueurActif().getMain().size() + " carte");
		if(this.instanceDePartie.getJoueurActif().getMain().size() > 1)
			texte.append("s");
		texte.append("]                    ");
		return(texte.toString());
	}	
	
	/**
	 * R�cup�re les informations quant aux paquets de cartes (talon et pioche)
	 * @return Informations quant aux paquets de cartes (talon et pioche)
	 */
	public String getInfosPaquets()
	{
		StringBuffer texte = new StringBuffer("");
		texte.append("[Pioche : " + this.instanceDePartie.getPioche().size() + " carte");
		if(this.instanceDePartie.getPioche().size() > 1)
			texte.append("s");
		texte.append("] ");
		texte.append("[Talon : " + this.instanceDePartie.getTalon().size() + " carte");
		if(this.instanceDePartie.getTalon().size() > 1)
			texte.append("s");
		texte.append("]\n");
		return(texte.toString());
	}
	
	/**
	 * R�cup�re les informations quant aux adversaires
	 * @return Informations quant aux adversaires
	 */
	public String getInfosAdversaires()
	{
		StringBuffer texte = new StringBuffer(""); 
		for(int x = 0 ; x < this.instanceDePartie.getJoueurActif().getAdversaires().size() ; x++)
		{
			Joueur adversaireX = this.instanceDePartie.getJoueurActif().getAdversaires().get(x);
			texte.append("             [" + adversaireX.getPseudo() + " : " + adversaireX.getMain().size() + " cartes]");
			if(this.instanceDePartie.getJoueurActif().getJoueurSuivant().equals(adversaireX))
				texte.append(" (joueur suivant)");
			texte.append("\n");
		}
		return(texte.toString());
	}
	
	/**
	 * Met le thread courant en attente durant une dur�e sp�cifi�e
	 * @param duree Dur�e d'attente durant lequel endormir le thread courant
	 */
	public void attendre(int duree)
	{
		try
		{
			Thread.sleep(duree);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Met � jour la vue actuelle en fonction des notifications envoy�es par les mod�les de jeu et de partie observ�s
	 */
	public void update(Observable o, Object arg) 
	{
		//if game 
		if(o instanceof Jeu)
		{
			if(arg instanceof String)
			{
				switch((String)arg)
				{
					/////////// Main menu
					case "Jouer" :
						this.controleur.jouer();
						break;
					case "Param�tres" :
						this.controleur.parametres();
						break;
						
					/////////// Settings menu
					case "Menu principal" :
						this.controleur.actionMenuPrincipal();
						break;
					case "Menu param�tres > Nombre de joueurs" :
						this.controleur.modificationNombreJoueurs();
						break;
					case "Menu param�tres > Nombre de paquets" :
						this.controleur.modificationNombrePaquetsCartes();
						break;
					case "Menu param�tres > Syst�me de points" :
						this.controleur.modificationSystemeScore();
						break;						
					case "Param�tres modifi�s" :
						this.controleur.confirmerParametresModifies();
						break;
						
					/////////// Game setup menu
					case "D�but de partie" :
						this.afficherChargementSalon();
						break;
					case "Partie cr��e" :
						if(this.instanceDePartie == null)
						{
							this.instanceDePartie = ((Jeu)o).obtenirPartie(this);
							if(this.instanceDePartie != null)
							{
								this.controleur.setPartie(this.instanceDePartie);
								//the view now observes the match
								this.instanceDePartie.addObserver(this);
							}
						}
						break;
				}
			}
		}
		
		//if match		
		if(o instanceof Partie)
		{
			if(arg instanceof String)
			{
				if(((Partie)o).getJoueurActif() instanceof JoueurConcret)
				{
					switch((String)arg)
					{
						case "Jouer tour" :
							this.controleur.demarrerTour();
							break;
						case "Jouer" :
							this.controleur.jouerTour();
							break;
						case "Changer variante" :			
							this.controleur.changerVariante();
							break;
						case "Variante chang�e" :			
							this.afficherChangementVarianteEffectue();
							this.controleur.demarrerTour();
							break;
						case "Retour PC" :
							this.controleur.demarrerTour();
							break;
						case "Retour PV" :
							this.controleur.demarrerTour();
							break;
						case "Erreur de combinaison" :
							this.afficherErreur("La carte indiqu�e ne peut pas �tre combin�e avec la premi�re renseign�e (deux valeurs diff�rentes).\n");
							break;
					}
				}
				switch((String)arg)
				{
					case "Pioche vide" :
						this.afficherImpossibiliteDePiocher();
						break;
					case "Mettre � jour talon" :
						//this.rafraichirTalon();
						//this.rafraichirInfos();
						break;
					case "Mettre � jour main" :
						//this.rafraichirInfos();
						break;
					case "Demander couleur" :
						this.controleur.demanderCouleurCarte();
						break;
					case "Demander symbole" :
						this.controleur.demanderSymboleCarte();
						break;
					case "Proposer annoncer carte" :
						this.controleur.demanderAnnoncerCarte();
						break;
					case "Annonce carte effectu�e" :
						this.signalerAnnonceCarte();
						break;
					case "Pioche effectu�e" :
						this.afficherPiocheEffectuee();
						break;
					case "Carte demand�e" :
						this.afficherCarteDemandee();
						break;
					case "Terminer tour" :
						this.signalerTerminerTour();
						break;
					case "Terminer partie" :
						this.annoncerFinDePartie();
						this.controleur.actionMenuPrincipal();
						break;
				}
			}
			else if(arg instanceof Object[])
			{
				if(((Object[])arg)[0] instanceof String)
				{
					if(((Object[])arg)[1] instanceof Carte)
					{
						switch((String)((Object[])arg)[0])
						{
							case "Carte jou�e" :
								this.afficherCarteJouee((Carte)((Object[])arg)[1]);
								break;
						}
					}
				}
			}
		}
	}
}
