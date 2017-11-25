import java.util.*;

public class Jeu 
{
	private Partie partieDeJeu;
	private String[] optionsDeJeu = new String[3];
	
	public Jeu()
	{
		this.initialiserOptionsDeJeuParDefaut();
	}
	
	public void demarrer()
	{
		int choix = choixActionsMenuPrincipal();
		if(choix == 1)
		{
			Variante variante = this.choisirVariante();
			HashSet<Joueur> joueurs = this.enregistrerJoueurs();
			this.partieDeJeu = Partie.creerPartie(this, variante, joueurs);
			this.partieDeJeu.getVarianteCourante().setPartieAssociee(this.partieDeJeu);
			this.creerSalon();
			System.out.println("\nLa partie a été créée avec succès !\n");
			this.partieDeJeu.lancerPartie();
		}
		else
		{
			this.modifierOptions();
			this.demarrer();
		}
	}
	
	public void initialiserOptionsDeJeuParDefaut()
	{
		this.optionsDeJeu[0] = "4"; //how many players
		this.optionsDeJeu[1] = "1"; //how many decks 
		this.optionsDeJeu[2] = "N"; //enable the points system
	}
	
	public int choixActionsMenuPrincipal()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		int reponse;
		texte.append("\n------------- BIENVENUE DANS LE J8-2017 v1.0 -------------\n::::::::::::::::::::: MENU PRINCIPAL :::::::::::::::::::::\n");
		texte.append("1) Jouer une partie\n");
		texte.append("2) Paramètres");
		System.out.println(texte.toString());
		reponse = scanner.nextInt();
		while(reponse != 1 && reponse != 2)
		{
			System.out.println("Vous devez choisir une des propositions (1 ou 2). Veuillez resaisir une action à effectuer :");
			reponse = scanner.nextInt();
		}		
		return(reponse);
	}
	
	public Variante choisirVariante()
	{
		String choixVariante = this.selectionnerVariante();
		switch(choixVariante)
		{
			case "1" : 
				return(new VarianteMinimale(this.partieDeJeu));
			case "2" : 
				return(new VarianteMonclar(this.partieDeJeu));
			case "3" : 
				return(new Variante4(this.partieDeJeu));
			case "4" : 
				return(new Variante5(this.partieDeJeu));
			default : return(new VarianteMinimale(this.partieDeJeu));
		}
	}
	
	public void enregistrerNiveauBot(JoueurVirtuel bot)
	{
		String choixDifficulte = this.selectionnerNiveau(bot);
		switch(choixDifficulte)
		{
			case "1" : 
				bot.setStrategieCourante((new IADebutant()));
			case "2" : 
				bot.setStrategieCourante((new IAAmateur()));
			case "3" : 
				bot.setStrategieCourante((new IAConfirme()));
			default : bot.setStrategieCourante((new IADebutant()));
		}
	}
	
	public String selectionnerNiveau(JoueurVirtuel bot)
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nJouer une partie > Choix de la difficulte pour le " + bot.getPseudo());
		texte.append("\n-\n[ETAPE 1] Veuillez choisir une des difficultés proposées (par défaut, le bot sera débutant :\n");
		texte.append("1) Joueur débutant\n");
		texte.append("2) Joueur amateur\n");
		texte.append("3) Joueur confirme\n");
		texte.append("[Appuyer sur la touche entrée pour continuer]");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(reponse.trim().equals(""))
			return("1");
		else
		{
			while((!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3")) || (!this.partieDeJeu.estUnEntier(reponse)))
			{
				System.out.println("Vous devez choisir une des propositions (un nombre entre [1 et 3]) ou appuyer sur la touche entrée ! Veuillez resaisir la difficulté :");
				reponse = new String(scanner.nextLine());
				if(reponse.trim().equals(""))
					return("1");
			}			
			return(reponse);		
		}
	}
	
	public String selectionnerVariante()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nJouer une partie > Choix de la variante");
		texte.append("\n-\n[ETAPE 1] Veuillez choisir une des variantes proposées (par défaut, la variante Minimale est choisie) :\n");
		texte.append("1) Variante Minimale\n");
		texte.append("2) Variante Monclar\n");
		texte.append("3) Variante 4\n");
		texte.append("4) Variante 5\n");
		texte.append("[Appuyer sur entrer pour continuer]");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(reponse.trim().equals(""))
			return("1");
		else
		{
			while(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3") && !reponse.equals("4"))
			{
				System.out.println("Vous devez choisir une des propositions ! Veuillez resaisir la variante :");
				reponse = new String(scanner.nextLine());
			}			
			return(reponse);		
		}
	}
	
	public HashSet<Joueur> enregistrerJoueurs()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nJouer une partie > Enregistrement du nombres joueur");
		texte.append("\n-\n[ETAPE 3] Veuillez choisir le nombre de joueurs (par défaut, " + this.optionsDeJeu[0] + " joueurs joueront la partie) :");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(!reponse.trim().equals(""))
		{
			while(!this.estUnEntier(reponse) || reponse.equals("1"))
			{
				System.out.println("Vous devez entrer un nombre ! Veuillez resaisir le nombre de joueurs :");
				reponse = new String(scanner.nextLine());
			}	
			this.optionsDeJeu[0] = reponse;
		}
		int nombreJoueurs = Integer.parseInt(this.optionsDeJeu[0]);
		Joueur[] tableauDeJoueurs = new Joueur[nombreJoueurs];
		HashSet<Joueur> joueursEnregistres = new HashSet<Joueur>();
		HashSet<Joueur> adversaires = new HashSet<Joueur>();
		String pseudoJoueur = new String();
		for(int i = 0 ; i < nombreJoueurs ; i++)
		{
			if(i == 0)
				tableauDeJoueurs[i] = JoueurConcret.creerJoueurConcret(this.partieDeJeu, "JoueurConcret_" + i, null, null, null);
			else
			{
				JoueurVirtuel bot = new JoueurVirtuel(this.partieDeJeu, "Bot-" + i, null, null, null);
				this.enregistrerNiveauBot(bot);
				tableauDeJoueurs[i] = bot;
			}
			adversaires.add(tableauDeJoueurs[i]);
		}			
		for(int x = 0 ; x < nombreJoueurs ; x++)
		{
			if(x == 0)
			{
				System.out.println("\n[ETAPE 4] Veuillez saisir votre pseudo :");
				pseudoJoueur = new String(scanner.nextLine());
				while(pseudoJoueur == null || pseudoJoueur.trim().equals(""))
				{
					System.out.println("Vous devez entrer un pseudo (non vide !). Veuillez en resaisir un :");			
					pseudoJoueur = new String(scanner.nextLine());
				}
				//set concrete player's pseudo
				tableauDeJoueurs[x].setPseudo(pseudoJoueur);
			}
			//set the next player for the current one
			if(x + 1 > nombreJoueurs - 1)
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[0]);
			else
				tableauDeJoueurs[x].setJoueurSuivant(tableauDeJoueurs[x + 1]);
			//set the previous player for the current one
			if(x - 1 < 0)
				tableauDeJoueurs[x].setJoueurPrecedent(tableauDeJoueurs[nombreJoueurs - 1]);
			else
				tableauDeJoueurs[x].setJoueurPrecedent(tableauDeJoueurs[x - 1]);
			//set the opponents list for the current player (whether human or bot)
			ArrayList<Joueur> adversairesJoueurX = new ArrayList<Joueur>(adversaires);
			adversairesJoueurX.remove(adversairesJoueurX.indexOf(tableauDeJoueurs[x]));				
			tableauDeJoueurs[x].setAdversaires(adversairesJoueurX);
			joueursEnregistres.add(tableauDeJoueurs[x]);
			System.out.println("joueur added : "+ tableauDeJoueurs[x].getPseudo());
		}	
		return(joueursEnregistres);
	}
	
	public boolean estUnEntier(String valeur)
	{
		try
		{
			Integer.parseInt(valeur);
		}
		catch(NumberFormatException e)
		{
			return(false);
		}
		return(true);
	}
	
	public void creerSalon()
	{
		for(int indexJoueur = 0 ; indexJoueur < this.partieDeJeu.getJoueursDeLaPartie().size() ; indexJoueur++)
			((Joueur)this.partieDeJeu.getJoueursDeLaPartie().toArray()[indexJoueur]).ajouterAUnePartie(this.partieDeJeu);
	}
	
	public void modifierOptions()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\n::::::::::::::::::: PARAMETRES DE JEU ::::::::::::::::::::\n");
		texte.append("1) Nombre de joueurs\n");
		texte.append("2) Nombre de jeux de cartes\n");
		texte.append("3) Système de points\n");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3"))
		{
			System.out.println("Vous devez entrer l'une des trois propositions. Veuillez resaisir l'option que vous souhaitez modifer :");
			reponse = new String(scanner.nextLine());
		}
		texte = new StringBuffer();
		switch(reponse)
		{
			case "1" : 
				texte.append("\nParamètres > Nombre de joueurs");
				texte.append("\n-\nVeuillez choisir le nombre de joueurs (par défaut, " + this.optionsDeJeu[0] + " joueurs sont définis) :");
				System.out.println(texte.toString());
				reponse = scanner.nextLine();
				if(!reponse.trim().equals(""))
				{	
					while(!this.estUnEntier(reponse) || reponse.equals("1"))
					{
						System.out.println("Vous devez entrer un nombre, qui doit être valide (différent de 1) ! Veuillez resaisir le nombre de joueurs :");
						reponse = new String(scanner.nextLine());
					}
					this.optionsDeJeu[0] = reponse;
				}
				break;
			case "2" : 
				texte.append("\nParamètres > Nombre de jeux de cartes");
				texte.append("\n-\nVeuillez choisir le nombre de jeux de cartes :");
				System.out.println(texte.toString());
				reponse = scanner.nextLine();
				while(!this.estUnEntier(reponse))
				{
					System.out.println("Vous devez entrer un nombre ! Veuillez resaisir le nombre de jeux de cartes :");
					reponse = new String(scanner.nextLine());
				}
				this.optionsDeJeu[1] = reponse;			
				break;
			case "3" : 
				texte.append("\nParamètres > Système de points");
				texte.append("\n-\nSouhaitez-vous activer le système de points ? [Y/N]");
				System.out.println(texte.toString());
				reponse = scanner.nextLine();
				while(!(reponse).equals("Y") && !(reponse).equals("N"))
				{
					System.out.println("Vous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :");
					reponse = new String(scanner.nextLine());
				}
				this.optionsDeJeu[2] = reponse;
				if(reponse.equals("Y"))
					System.out.println("Le système de points est désormais activé !\n");
				else
					System.out.println("Le système de points est désormais désactivé !\n");
				break;
		}
	}
	
	//getters and setters	
	public String[] getOptionsDeJeu()
	{
		return(this.optionsDeJeu);
	}

	//main program
	public static void main(String[] args) 
	{
		new Jeu().demarrer();
	}

}
