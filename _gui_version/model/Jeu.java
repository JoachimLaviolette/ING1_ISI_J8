package _gui_version.model;

import java.io.*;
import java.util.*;

import _gui_version._exceptions.*;
import _gui_version.view.Console;
import _gui_version.view.MenuPlateauJeu;

/**
 * Classe d'une instance de jeu </br>
 * Repr�sente la structure et le comportement d'une instance de jeu </br>
 * Permet de d�marrer l'application et de param�trer une partie au pr�alable de son lancement
 * @author Joachim Laviolette
 * @version 1.0
 */
public class Jeu extends Observable 
{
	private Partie partieDeJeu;
	private String[] optionsDeJeu = new String[3];
	
	/**
	 * Cr�er un nouvel objet de type Jeu
	 */	
	public Jeu()
	{
		super();
		this.initialiserOptionsDeJeuParDefaut();
	}
	
	/**
	 * Initialise les options de base du jeu du 8 Am�ricain (nombre de joueurs, d paquets, et l'activation (ou non) du score)
	 */	
	public void initialiserOptionsDeJeuParDefaut()
	{
		this.optionsDeJeu[0] = "3"; //how many players
		this.optionsDeJeu[1] = "1"; //how many decks 
		this.optionsDeJeu[2] = "N"; //disable or enable the scoring system
	}
	
	/**
	 * Demande aux vues d'afficher le menu principal	
	 */
	public void demarrerJeu()
	{
		this.notifier("Menu principal");
	}
	
	/**
	 * Demande aux vues d'afficher le menu de param�trage de partie
	 */	
	public void jouer()
	{
		this.notifier("Jouer");
	}
	
	/**
	 * Demande aux vues d'afficher le menu des param�tres du jeu
	 */
	public void parametres()
	{
		this.notifier("Param�tres");
	}
	/**
	 * Quitte l'application
	 */
	public void quitter()
	{
		this.notifier("Quitter");
		System.exit(0);
	}
	
	/**
	 * Demande aux vues d'afficher l'interface de jeu </br>
	 * D�marre la partie	
	 */
	public void demarrer()
	{
		this.notifier("D�but de partie");
		this.partieDeJeu.lancerPartie();
	}
	
	/**
	 * Ajoute tous les joueurs de la partie au m�me salon </br>
	 * Notifie les vues que la partie peut d�marrer	
	 */
	public void creerSalon()
	{
		for(int indexJoueur = 0 ; indexJoueur < this.partieDeJeu.getJoueursDeLaPartie().size() ; indexJoueur++)
			((Joueur)this.partieDeJeu.getJoueursDeLaPartie().toArray()[indexJoueur]).ajouterAUnePartie(this.partieDeJeu);
		//notify the observing views the match has been created
		this.notifier("Partie cr��e");
	}	
			
	/**
	 * Formate tous les param�tres n�cessaires � la cr�ation de partie en fonction de ce qui a �t� saisi par l'utilisateur </br>
	 * Enregistre les joueurs en fonction du nombre indiqu� </br>
	 * Initialise leurs mains </br>
	 * Cr�e l'instance de partie ainsi que le salon puis d�marre la partie 
	 * @param choixVariante Index repr�sentant la variante choisie
	 * @param nbJ Nombre de joueurs de la partie
	 * @param pseudoJ Pseudo du joueur concret
	 * @param choixStrategie Index repr�sentant la strat�gie choisie
	 */
		
	public void preparerPartie(String choixVariante, int nbJ, String pseudoJ, String choixStrategie)
	{
		Variante varianteChoisie = this.selectionnerVariante(choixVariante);
		this.partieDeJeu = Partie.creerPartie(this, varianteChoisie, null);
		ArrayList<Joueur> joueurs = this.enregistrerJoueurs(nbJ, pseudoJ, choixStrategie);
		this.partieDeJeu.setJoueursDeLaPartie(joueurs);
		this.partieDeJeu.setJoueurActif(this.partieDeJeu.trouverJoueurConcret());
		this.partieDeJeu.initialiserMains();
		this.creerSalon();
		this.demarrer();
	}
	
	/**
	 * Demande � l'utilisateur l'action du menu principal � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirActionMenu() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3"))
			throw new UncompliantChoiceException("Veuillez choisir une des trois actions du menu :");
		return(reponse);
	}
	
	/**
	 * Modifie les param�tres de la partie (options de jeu par d�faut)
	 * @param nbj Nombre de joueurs de la partie
	 * @param nbp Nombre de paquets de la partie
	 * @param score Bool�en � vrai si le syst�me de score doit �tre activ�, faux sinon 
	 */
	public void changerParametres(String nbj, String nbp, boolean score)
	{
		if(nbj != null && !nbj.trim().equals(""))
			this.optionsDeJeu[0] = nbj;
		if(nbp != null && !nbp.trim().equals(""))	
			this.optionsDeJeu[1] = nbp;
		if(score)
			this.optionsDeJeu[2] = "Y";
		else
			this.optionsDeJeu[2] = "N";
		/**
		 * Une fois les param�tres modifi�s, on notifie les vues de se mettre � jour
		 */
		this.notifier("Param�tres modifi�s");
		this.notifier("Menu principal");
	}	
	
	/**
	 * Demande � l'utilisateur l'action du menu param�tres � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirActionMenuParametres() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();
		try 
		{
			reponse = scanner.readLine();
		}
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		//notify the observing views
		if(reponse.equals("1"))
			this.notifier("Menu param�tres > Nombre de joueurs");
		else if(reponse.equals("2"))
			this.notifier("Menu param�tres > Nombre de paquets");
		else if(reponse.equals("3"))
			this.notifier("Menu param�tres > Syst�me de points");
		else if(reponse.trim().equals(""))
			this.notifier("Menu principal");
		else
		{
			StringBuffer texte = new StringBuffer();
			texte.append("Veuillez choisir une des trois actions du menu :");
			texte.append("\n\n                     [Appuyer sur la touche entr�e pour annuler]");
			throw new UncompliantChoiceException(texte.toString());
		}
		return(reponse);
	}
	
	/**
	 * Demande � l'utilisateur la variante de la partie
	 * @return Index repr�sentant la variante de la partie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirVariante() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();
		try 
		{
			reponse = scanner.readLine();
		}
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		//notify the observing views
		if(reponse.trim().equals(""))
			reponse = "1";
		else
		{
			if(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3") && !reponse.equals("4") && !reponse.equals("5"));
				throw new UncompliantChoiceException("Vous devez choisir une des propositions entre [1 et 5].\nVeuillez resaisir la variante :");
		}
		return(reponse);
	}
	
	/**
	 * Demande � l'utilisateur le nombre de joueurs de la partie
	 * @return Nombre de joueurs de la partie repr�sent� sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirNombreJoueurs() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();		
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}							
		if(reponse.trim().equals(""))
			reponse = this.optionsDeJeu[0];	
		if((Partie.estUnEntier(reponse) && !reponse.equals("1")))
		{
			//adjust the deck according to the number of players
			while(!this.verifierCompatibiliteNbJoueursNbCartes(Integer.parseInt(reponse))) //if not enough cards for all players, add a new deck
				this.optionsDeJeu[1] = (Integer.parseInt(this.optionsDeJeu[1]) + 1) + "";
		}
		else
		{
			StringBuffer texte = new StringBuffer();
			texte.append("\nVous devez entrer un nombre ou appuyer directement sur entr�e. Veuillez resaisir le nombre de joueurs :");
			texte.append("\n\n                       [Appuyer sur la touche entr�e pour continuer]");
			throw new UncompliantChoiceException(texte.toString());
		}
		return(reponse);
	}
	
	/**
	 * Demande � l'utilisateur son pseudo pour la partie
	 * @return Pseudo saisi par l'utilisateur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirPseudo() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(reponse == null || reponse.trim().equals(""))
		{
			throw new UncompliantChoiceException("\nVous devez entrer un pseudo (non vide). Veuillez en resaisir un :");
		}
		return(reponse);
	}
	
	/**
	 * Demande � l'utilisateur la difficult� de la partie
	 * @return Index repr�sentant la difficult� de la partie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String choisirDifficulte() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();	
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		if(reponse.trim().equals(""))
			reponse = "1";
		else
		{
			if((!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3") || (!Partie.estUnEntier(reponse))))
				throw new UncompliantChoiceException("\nVous devez choisir une des propositions entre [1 et 3], ou appuyer sur la touche entr�e.\nVeuillez resaisir la difficult� :");
		}
		return(reponse);
	}
	
	/**
	 * Modifie le nombre de joueurs de la partie (interface param�tres)
	 * @return Nombre de joueurs de la partie repr�sent� sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String modifierNombreJoueurs() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();		
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
		//if not enough cards for all players, add a new deck
		if((Partie.estUnEntier(reponse) && (!reponse.equals("1") && Integer.parseInt(reponse) > 1)))
		{
			//adjust the deck according to the number of players
			while(!this.verifierCompatibiliteNbJoueursNbCartes(Integer.parseInt(reponse))) //if not enough cards for all players, add a new deck
				this.optionsDeJeu[1] = (Integer.parseInt(this.optionsDeJeu[1]) + 1) + "";
			this.setNombreJoueurs(reponse);
		}
		else if(reponse.trim().equals(""))
			reponse = this.optionsDeJeu[0];
		else
			throw new UncompliantChoiceException("Le nombre de joueurs saisi doit �tre un nombre strictement sup�rieur � 1. Veuillez resaisir votre choix");
		return(reponse);
	}
	
	/**
	 * Modifie le nombre de paquets de la partie
	 * @return Nombre de paquets de la partie repr�sent� sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String modifierNombrePaquetsCartes() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();		
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		if(reponse.trim().equals(""))
			reponse = this.optionsDeJeu[1];		
		else
		{
			if(Partie.estUnEntier(reponse))
			{
				if(this.verifierCompatibiliteNbCartesNbJoueurs(Integer.parseInt(reponse)))
					this.setNombrePaquets(reponse);	
			}
			else
				throw new UncompliantChoiceException("Vous devez entrer un nombre entier :");
		}	
		return(reponse);
	}
	
	/**
	 * Modifie l'activation du syst�me de score
	 * @return Index/cl� repr�sentant l'�tat du syst�me de score (Y ou N)
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String modifierSystemeScore() throws GameException
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String();
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
		if(reponse.trim().equals(""))
			reponse = this.optionsDeJeu[2];
		else if(reponse.equals("Y"))
			this.setActivationScore(true);
		else if(reponse.equals("N"))
			this.setActivationScore(false);
		else
			throw new UncompliantChoiceException("\\nVous devez choisir entre [Y] et [N]. Veuillez resaisir votre r�ponse :");
		return(reponse);
	}
	
	/**
	 * G�n�re l'instance de variante appropri�e en fonction de l'index communiqu�	
	 * @param choixVariante Index repr�sentant la variante choisie
	 * @return La variante correspondant � l'index
	 */
	public Variante selectionnerVariante(String choixVariante)
	{
		Variante varianteChoisie;		
		switch(choixVariante)
		{
			case "1" : 
				varianteChoisie = new VarianteMinimale(this.partieDeJeu); break;
			case "2" : 
				varianteChoisie = new VarianteMonclar(this.partieDeJeu); break;
			case "3" : 
				varianteChoisie = new Variante4(this.partieDeJeu); break;
			case "4" : 
				varianteChoisie = new Variante5(this.partieDeJeu); break;
			case "5" : 
				varianteChoisie = new VariantePerso(this.partieDeJeu); break;
			default : 
				varianteChoisie = new VarianteMinimale(this.partieDeJeu); break;
		}
		return(varianteChoisie);
	}
	
	/**
	 * Formate toutes les donn�es de la partie en fonction des informations saisies par l'utilisateur lors du param�trage	
	 * @param nbJ Nombre de joueurs choisi
	 * @param pseudoJ Pseudo du joueur concret
	 * @param choixStrategie Index repr�sentant la difficult� choisie
	 * @return La liste des joueurs enregistr�s pour la partie
	 */
	public ArrayList<Joueur> enregistrerJoueurs(int nbJ, String pseudoJ, String choixStrategie)
	{
		this.optionsDeJeu[0] = nbJ + "";
		int nombreJoueurs = Integer.parseInt(this.optionsDeJeu[0]);
		//cr�e un tableau de joueurs temporaire
		Joueur[] tableauDeJoueurs = new Joueur[nombreJoueurs];
		//cr�e une liste de joueurs
		ArrayList<Joueur> joueursEnregistres = new ArrayList<Joueur>();
		//cr�e une liste d'adversaires
		ArrayList<Joueur> adversaires = new ArrayList<Joueur>();
		/**
		 * Cr�e les objets repr�sentant les joueurs
		 * Formate leur pseudo et les associe � l'instance de partie courante
		 * Puis d�finit la strat�gie des bots
		 */
		for(int i = 0 ; i < nombreJoueurs ; i++)
		{
			//Le premier joueurs de la liste est le joueur concret (singleton)
			if(i == 0)
				tableauDeJoueurs[i] = JoueurConcret.creerJoueurConcret(this.partieDeJeu, pseudoJ, null, null, null);
			//Ensuite, cr�ation des bots
			else
			{
				JoueurVirtuel bot = new JoueurVirtuel(this.partieDeJeu, "Bot-" + i, null, null, null);
				/**
				 * Chaque bot est suppos� avoir une strat�gie attribu�e
				 * En raison de l'impl�mentation actuelle de l'interface graphique (cf. MenuJeu.java) ne permettant pas de choisir le strat�gie des joueurs en fonction de leur nombre
				 * Elle demande une difficult� qui est affect�e pour l'ensemble des bots
				 * Une autre version permet n�anmoins de s�lectionner, pour chaque joueur, une strat�gie sp�cifique
				 * L'algorithme pr�sent� comme ici est �labor� pour le faire
				 */
				this.enregistrerNiveauBot(bot, choixStrategie);
				//ajoute le bot au tablau de joueurs temporaire
				tableauDeJoueurs[i] = bot;
			}
			//on ajoute �galement le joueur � la liste des adversaires
			adversaires.add(tableauDeJoueurs[i]);
		}	
		for(int x = 0 ; x < nombreJoueurs ; x++)
		{
			//set le joueurs suivant pour le joueur actuel
			if(x + 1 > nombreJoueurs - 1)
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[0]);
			else
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[x + 1]);
			//set le pr�c�dent joueur pour le joueur actuel
			if(x - 1 < 0)
				tableauDeJoueurs[x].setJoueurPrecedent(tableauDeJoueurs[nombreJoueurs - 1]);
			else
				tableauDeJoueurs[x].setJoueurPrecedent(tableauDeJoueurs[x - 1]);
			//set la liste d'adversaires pour le joueur actuel
			ArrayList<Joueur> adversairesJoueurX = new ArrayList<Joueur>(adversaires);
			adversairesJoueurX.remove(adversairesJoueurX.indexOf(tableauDeJoueurs[x]));				
			tableauDeJoueurs[x].setAdversaires(adversairesJoueurX);
			joueursEnregistres.add(tableauDeJoueurs[x]);		
		}	
		return(joueursEnregistres);		
	}
	
	/**
	 * Enregistre la strat�gie d�finie pour un bot sp�cifique	
	 * @param bot Bot � qui la stat�gie doit �tre affect�e
	 * @param choixStrategie Index repr�sentant la strat�gie qui doit �tre affect�e au bot
	 */
	public void enregistrerNiveauBot(JoueurVirtuel bot, String choixStrategie)
	{
		String choixDifficulte = choixStrategie;
		switch(choixDifficulte)
		{
			case "1" : 
				bot.setStrategieCourante((new IADebutant(bot)));
				break;
			case "2" : 
				bot.setStrategieCourante((new IAAmateur(bot)));
				break;
			case "3" : 
				bot.setStrategieCourante((new IAConfirme(bot)));
				break;
		}
	}	
	
	/**
	 * Retourne le tableau d'options de jeu par d�faut	
	 * @return Tableau d'options de jeu par d�faut
	 */
	public String[] getOptionsDeJeu()
	{
		return(this.optionsDeJeu);
	}
	
	/**
	 * Retourne l'instance de partie courante si le demandeur est une vue autoris�e 
	 * @return Instance de la partie courante
	 */
	public Partie obtenirPartie(Object demandeur)
	{
		if(demandeur instanceof Console || demandeur instanceof MenuPlateauJeu)
		{
			return(this.partieDeJeu);
		}
		return(null);
	}
	
	/**
	 * Modifie le nombre de joueurs (param�tres)	
	 * @param val Nombre de joueurs
	 */
	public void setNombreJoueurs(String val)
	{
		this.getOptionsDeJeu()[0] = val;		
	}
	
	/**
	 * Modifie le nombre de paquets de jeux de cartes (param�tres)
	 * @param val Nombre de paquets de jeux de cartes
	 */
	public void setNombrePaquets(String val)
	{
		this.getOptionsDeJeu()[1] = val;		
	}
	
	/**
	 * Modifie l'�tat du syst�me de score (activ� ou non)
	 * @param val Bool�en � vrai si le syst�me de score doit �tre activ�, faux sinon
	 */
	public void setActivationScore(boolean val)
	{
		if(val)
			this.getOptionsDeJeu()[2] = "Y";
		else
			this.getOptionsDeJeu()[2] = "N";
	}
	
	/**
	 * V�rifie si le nombre de jeux de cartes est suffisant pour servir l'ensemble des joueurs </br>
	 * Si le nombre de joueurs est trop important par rapport au nombre de jeux de cartes enregistr�, l'algorithme appelant ajuste automatiquement le nombre de paquets
	 * @param nombreJoueurs Nombre de joueurs sp�cifi� 
	 * @return Bool�en � vrai si le nombre de jeux de cartes suffit � servir la totalit� des joueurs, faux sinon
	 */
	public boolean verifierCompatibiliteNbJoueursNbCartes(int nombreJoueurs)
	{
		if((54 * Integer.parseInt(this.optionsDeJeu[1])) - (nombreJoueurs * 8) > 0)
			return(true);
		else
			return(false);
	}
	
	/**
	 * V�rifie si le nombre de joueurs actuellement enregistr� n'est pas trop �lev� par rapport au nombre de jeux de cartes que l'on souhaite enregistr�
	 * @param nombreJeuxDeCartes Nombre de jeux de cartes que l'on souhaite enregistr�
	 * @return Bool�en � vrai si le nombre de joueurs et le nombre de jeux de cartes sont compatibles, faux sinon
	 * @throws GameException Si le nombre de joueurs et le nombre de jeux de cartes demand� ne sont pas compatibles
	 */
	public boolean verifierCompatibiliteNbCartesNbJoueurs(int nombreJeuxDeCartes) throws GameException
	{
		if((nombreJeuxDeCartes * 54) - (8 * Integer.parseInt(this.optionsDeJeu[0])) > 0)
			return(true);
		else
			throw new UncompliantChoiceException("\nVous devez entrer un nombre de jeux de cartes (comptant 54 cartes) qui doit au moins pouvoir servir enti�rement chaque chaque joueur (8 cartes/joueur).");
	}
	
	/**
	 * Notifie les vues observatrices de se mettre � jour en fonction du message sp�cifi�
	 * @param message Notification adress�e aux vues concurrentes qui observent le mod�le
	 */
	public void notifier(String message)
	{
		this.setChanged();
		this.notifyObservers(message);
	}
}
