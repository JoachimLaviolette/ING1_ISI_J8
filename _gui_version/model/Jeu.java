package _gui_version.model;

import java.io.*;
import java.util.*;

import _gui_version._exceptions.*;
import _gui_version.view.Console;
import _gui_version.view.MenuPlateauJeu;

/**
 * Classe d'une instance de jeu </br>
 * Représente la structure et le comportement d'une instance de jeu </br>
 * Permet de démarrer l'application et de paramétrer une partie au préalable de son lancement
 * @author Joachim Laviolette
 * @version 1.0
 */
public class Jeu extends Observable 
{
	private Partie partieDeJeu;
	private String[] optionsDeJeu = new String[3];
	
	/**
	 * Créer un nouvel objet de type Jeu
	 */	
	public Jeu()
	{
		super();
		this.initialiserOptionsDeJeuParDefaut();
	}
	
	/**
	 * Initialise les options de base du jeu du 8 Américain (nombre de joueurs, d paquets, et l'activation (ou non) du score)
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
	 * Demande aux vues d'afficher le menu de paramétrage de partie
	 */	
	public void jouer()
	{
		this.notifier("Jouer");
	}
	
	/**
	 * Demande aux vues d'afficher le menu des paramètres du jeu
	 */
	public void parametres()
	{
		this.notifier("Paramètres");
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
	 * Démarre la partie	
	 */
	public void demarrer()
	{
		this.notifier("Début de partie");
		this.partieDeJeu.lancerPartie();
	}
	
	/**
	 * Ajoute tous les joueurs de la partie au même salon </br>
	 * Notifie les vues que la partie peut démarrer	
	 */
	public void creerSalon()
	{
		for(int indexJoueur = 0 ; indexJoueur < this.partieDeJeu.getJoueursDeLaPartie().size() ; indexJoueur++)
			((Joueur)this.partieDeJeu.getJoueursDeLaPartie().toArray()[indexJoueur]).ajouterAUnePartie(this.partieDeJeu);
		//notify the observing views the match has been created
		this.notifier("Partie créée");
	}	
			
	/**
	 * Formate tous les paramètres nécessaires à la création de partie en fonction de ce qui a été saisi par l'utilisateur </br>
	 * Enregistre les joueurs en fonction du nombre indiqué </br>
	 * Initialise leurs mains </br>
	 * Crée l'instance de partie ainsi que le salon puis démarre la partie 
	 * @param choixVariante Index représentant la variante choisie
	 * @param nbJ Nombre de joueurs de la partie
	 * @param pseudoJ Pseudo du joueur concret
	 * @param choixStrategie Index représentant la stratégie choisie
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
	 * Demande à l'utilisateur l'action du menu principal à exécuter
	 * @return Index représentant l'action à exécuter
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
	 * Modifie les paramètres de la partie (options de jeu par défaut)
	 * @param nbj Nombre de joueurs de la partie
	 * @param nbp Nombre de paquets de la partie
	 * @param score Booléen à vrai si le système de score doit être activé, faux sinon 
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
		 * Une fois les paramètres modifiés, on notifie les vues de se mettre à jour
		 */
		this.notifier("Paramètres modifiés");
		this.notifier("Menu principal");
	}	
	
	/**
	 * Demande à l'utilisateur l'action du menu paramètres à exécuter
	 * @return Index représentant l'action à exécuter
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
			this.notifier("Menu paramètres > Nombre de joueurs");
		else if(reponse.equals("2"))
			this.notifier("Menu paramètres > Nombre de paquets");
		else if(reponse.equals("3"))
			this.notifier("Menu paramètres > Système de points");
		else if(reponse.trim().equals(""))
			this.notifier("Menu principal");
		else
		{
			StringBuffer texte = new StringBuffer();
			texte.append("Veuillez choisir une des trois actions du menu :");
			texte.append("\n\n                     [Appuyer sur la touche entrée pour annuler]");
			throw new UncompliantChoiceException(texte.toString());
		}
		return(reponse);
	}
	
	/**
	 * Demande à l'utilisateur la variante de la partie
	 * @return Index représentant la variante de la partie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
	 * Demande à l'utilisateur le nombre de joueurs de la partie
	 * @return Nombre de joueurs de la partie représenté sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
			texte.append("\nVous devez entrer un nombre ou appuyer directement sur entrée. Veuillez resaisir le nombre de joueurs :");
			texte.append("\n\n                       [Appuyer sur la touche entrée pour continuer]");
			throw new UncompliantChoiceException(texte.toString());
		}
		return(reponse);
	}
	
	/**
	 * Demande à l'utilisateur son pseudo pour la partie
	 * @return Pseudo saisi par l'utilisateur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
	 * Demande à l'utilisateur la difficulté de la partie
	 * @return Index représentant la difficulté de la partie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
				throw new UncompliantChoiceException("\nVous devez choisir une des propositions entre [1 et 3], ou appuyer sur la touche entrée.\nVeuillez resaisir la difficulté :");
		}
		return(reponse);
	}
	
	/**
	 * Modifie le nombre de joueurs de la partie (interface paramètres)
	 * @return Nombre de joueurs de la partie représenté sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
			throw new UncompliantChoiceException("Le nombre de joueurs saisi doit être un nombre strictement supérieur à 1. Veuillez resaisir votre choix");
		return(reponse);
	}
	
	/**
	 * Modifie le nombre de paquets de la partie
	 * @return Nombre de paquets de la partie représenté sous forme de chaine
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
	 * Modifie l'activation du système de score
	 * @return Index/clé représentant l'état du système de score (Y ou N)
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
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
			throw new UncompliantChoiceException("\\nVous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :");
		return(reponse);
	}
	
	/**
	 * Génère l'instance de variante appropriée en fonction de l'index communiqué	
	 * @param choixVariante Index représentant la variante choisie
	 * @return La variante correspondant à l'index
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
	 * Formate toutes les données de la partie en fonction des informations saisies par l'utilisateur lors du paramétrage	
	 * @param nbJ Nombre de joueurs choisi
	 * @param pseudoJ Pseudo du joueur concret
	 * @param choixStrategie Index représentant la difficulté choisie
	 * @return La liste des joueurs enregistrés pour la partie
	 */
	public ArrayList<Joueur> enregistrerJoueurs(int nbJ, String pseudoJ, String choixStrategie)
	{
		this.optionsDeJeu[0] = nbJ + "";
		int nombreJoueurs = Integer.parseInt(this.optionsDeJeu[0]);
		//crée un tableau de joueurs temporaire
		Joueur[] tableauDeJoueurs = new Joueur[nombreJoueurs];
		//crée une liste de joueurs
		ArrayList<Joueur> joueursEnregistres = new ArrayList<Joueur>();
		//crée une liste d'adversaires
		ArrayList<Joueur> adversaires = new ArrayList<Joueur>();
		/**
		 * Crée les objets représentant les joueurs
		 * Formate leur pseudo et les associe à l'instance de partie courante
		 * Puis définit la stratégie des bots
		 */
		for(int i = 0 ; i < nombreJoueurs ; i++)
		{
			//Le premier joueurs de la liste est le joueur concret (singleton)
			if(i == 0)
				tableauDeJoueurs[i] = JoueurConcret.creerJoueurConcret(this.partieDeJeu, pseudoJ, null, null, null);
			//Ensuite, création des bots
			else
			{
				JoueurVirtuel bot = new JoueurVirtuel(this.partieDeJeu, "Bot-" + i, null, null, null);
				/**
				 * Chaque bot est supposé avoir une stratégie attribuée
				 * En raison de l'implémentation actuelle de l'interface graphique (cf. MenuJeu.java) ne permettant pas de choisir le stratégie des joueurs en fonction de leur nombre
				 * Elle demande une difficulté qui est affectée pour l'ensemble des bots
				 * Une autre version permet néanmoins de sélectionner, pour chaque joueur, une stratégie spécifique
				 * L'algorithme présenté comme ici est élaboré pour le faire
				 */
				this.enregistrerNiveauBot(bot, choixStrategie);
				//ajoute le bot au tablau de joueurs temporaire
				tableauDeJoueurs[i] = bot;
			}
			//on ajoute également le joueur à la liste des adversaires
			adversaires.add(tableauDeJoueurs[i]);
		}	
		for(int x = 0 ; x < nombreJoueurs ; x++)
		{
			//set le joueurs suivant pour le joueur actuel
			if(x + 1 > nombreJoueurs - 1)
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[0]);
			else
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[x + 1]);
			//set le précédent joueur pour le joueur actuel
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
	 * Enregistre la stratégie définie pour un bot spécifique	
	 * @param bot Bot à qui la statégie doit être affectée
	 * @param choixStrategie Index représentant la stratégie qui doit être affectée au bot
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
	 * Retourne le tableau d'options de jeu par défaut	
	 * @return Tableau d'options de jeu par défaut
	 */
	public String[] getOptionsDeJeu()
	{
		return(this.optionsDeJeu);
	}
	
	/**
	 * Retourne l'instance de partie courante si le demandeur est une vue autorisée 
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
	 * Modifie le nombre de joueurs (paramètres)	
	 * @param val Nombre de joueurs
	 */
	public void setNombreJoueurs(String val)
	{
		this.getOptionsDeJeu()[0] = val;		
	}
	
	/**
	 * Modifie le nombre de paquets de jeux de cartes (paramètres)
	 * @param val Nombre de paquets de jeux de cartes
	 */
	public void setNombrePaquets(String val)
	{
		this.getOptionsDeJeu()[1] = val;		
	}
	
	/**
	 * Modifie l'état du système de score (activé ou non)
	 * @param val Booléen à vrai si le système de score doit être activé, faux sinon
	 */
	public void setActivationScore(boolean val)
	{
		if(val)
			this.getOptionsDeJeu()[2] = "Y";
		else
			this.getOptionsDeJeu()[2] = "N";
	}
	
	/**
	 * Vérifie si le nombre de jeux de cartes est suffisant pour servir l'ensemble des joueurs </br>
	 * Si le nombre de joueurs est trop important par rapport au nombre de jeux de cartes enregistré, l'algorithme appelant ajuste automatiquement le nombre de paquets
	 * @param nombreJoueurs Nombre de joueurs spécifié 
	 * @return Booléen à vrai si le nombre de jeux de cartes suffit à servir la totalité des joueurs, faux sinon
	 */
	public boolean verifierCompatibiliteNbJoueursNbCartes(int nombreJoueurs)
	{
		if((54 * Integer.parseInt(this.optionsDeJeu[1])) - (nombreJoueurs * 8) > 0)
			return(true);
		else
			return(false);
	}
	
	/**
	 * Vérifie si le nombre de joueurs actuellement enregistré n'est pas trop élevé par rapport au nombre de jeux de cartes que l'on souhaite enregistré
	 * @param nombreJeuxDeCartes Nombre de jeux de cartes que l'on souhaite enregistré
	 * @return Booléen à vrai si le nombre de joueurs et le nombre de jeux de cartes sont compatibles, faux sinon
	 * @throws GameException Si le nombre de joueurs et le nombre de jeux de cartes demandé ne sont pas compatibles
	 */
	public boolean verifierCompatibiliteNbCartesNbJoueurs(int nombreJeuxDeCartes) throws GameException
	{
		if((nombreJeuxDeCartes * 54) - (8 * Integer.parseInt(this.optionsDeJeu[0])) > 0)
			return(true);
		else
			throw new UncompliantChoiceException("\nVous devez entrer un nombre de jeux de cartes (comptant 54 cartes) qui doit au moins pouvoir servir entièrement chaque chaque joueur (8 cartes/joueur).");
	}
	
	/**
	 * Notifie les vues observatrices de se mettre à jour en fonction du message spécifié
	 * @param message Notification adressée aux vues concurrentes qui observent le modèle
	 */
	public void notifier(String message)
	{
		this.setChanged();
		this.notifyObservers(message);
	}
}
