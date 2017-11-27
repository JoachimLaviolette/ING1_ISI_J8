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
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("           ... CHARGEMENT ...            La partie a été créée avec succès !");
			System.out.println("--------------------------------------------------------------------------------");
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
		String reponse;
		texte.append("\n------------- BIENVENUE DANS LE J8-2017 v1.0 -------------\n::::::::::::::::::::: MENU PRINCIPAL :::::::::::::::::::::\n\n");
		texte.append("1) Jouer une partie\n");
		texte.append("2) Paramètres");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(!reponse.trim().equals("") && (Partie.estUnEntier(reponse)) && (Integer.parseInt(reponse) <= 2 && Integer.parseInt(reponse) >= 1)))
		{
			System.out.println("Veuillez choisir une des deux actions du menu :");
			reponse = scanner.nextLine();
		}		
		return(Integer.parseInt(reponse));
	}
	
	public Variante choisirVariante()
	{
		String choixVariante = this.selectionnerVariante();
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
	
	public void enregistrerNiveauBot(JoueurVirtuel bot)
	{
		String choixDifficulte = this.selectionnerNiveau(bot);
		switch(choixDifficulte)
		{
			case "1" : 
				bot.setStrategieCourante((new IADebutant(bot)));
			case "2" : 
				bot.setStrategieCourante((new IAAmateur(bot)));
			case "3" : 
				bot.setStrategieCourante((new IAConfirme(bot)));
			default : bot.setStrategieCourante((new IADebutant(bot)));
		}
	}
	
	public String selectionnerNiveau(JoueurVirtuel bot)
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();		
		texte.append("\nJouer une partie > Choix de la difficulte pour le " + bot.getPseudo());
		texte.append("\n-------------------------------------------------------\n[ETAPE 3] Veuillez choisir une des difficultés proposées (par défaut, le bot sera débutant) :\n");
		texte.append("\n1) Joueur débutant\n");
		texte.append("2) Joueur amateur\n");
		texte.append("3) Joueur confirme\n");
		texte.append("                       [Appuyer sur la touche entrée pour continuer]");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(reponse.trim().equals(""))
			reponse =  "1";
		else
		{
			while((!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3")) || (!Partie.estUnEntier(reponse)))
			{
				System.out.println("\nVous devez choisir une des propositions (un nombre entre [1 et 3]), ou appuyer sur la touche entrée.\nVeuillez resaisir la difficulté :");
				reponse = new String(scanner.nextLine());
				if(reponse.trim().equals(""))
					reponse = "1";
			}					
		}
		texte = new StringBuffer("");
		texte.append("\nLa difficulté ");
		if(reponse.equals("1"))
			texte.append("débutant");
		else if(reponse.equals("2"))
			texte.append("amateur");
		else
			texte.append("confirmé");
		texte.append(" a été choisie.\n");
		System.out.println(texte.toString());	
		return(reponse);
	}
	
	public String selectionnerVariante()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\n::::::::::::::::::: LANCEMENT D\'UNE PARTIE ::::::::::::::::::::\n");
		texte.append("\nJouer une partie > Choix de la variante");
		texte.append("\n---------------------------------------\n[ETAPE 1] Veuillez choisir une des variantes proposées (par défaut, la variante Minimale sera choisie) :\n");
		texte.append("\n1) Variante Minimale\n");
		texte.append("2) Variante Monclar\n");
		texte.append("3) Variante 4\n");
		texte.append("4) Variante 5\n");
		texte.append("5) Variante Personnalisée\n\n");
		texte.append("                       [Appuyer sur la touche entrée pour continuer]");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(reponse.trim().equals(""))
			return("1");
		else
		{
			while(!reponse.equals("1") && !reponse.equals("2") && !reponse.equals("3") && !reponse.equals("4") && !reponse.equals("5"))
			{
				System.out.println("Vous devez choisir une des propositions (un nombre entre [1 et 5]).\nVeuillez resaisir la variante :");
				reponse = new String(scanner.nextLine());
			}					
		}
		texte = new StringBuffer("\nLa variante ");
		if(reponse.equals("1"))
			texte.append("Minimale");
		else if(reponse.equals("2"))
			texte.append("Monclar");
		else if(reponse.equals("3"))
			texte.append("4");
		else if(reponse.equals("4"))
			texte.append("5");
		else
			texte.append("Personnalisée");
		texte.append(" a été choisie.\n");
		System.out.println(texte.toString());	
		return(reponse);
	}
	
	public HashSet<Joueur> enregistrerJoueurs()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nJouer une partie > Enregistrement du nombres joueur");
		texte.append("\n---------------------------------------------------\n[ETAPE 2] Veuillez choisir le nombre de joueurs (par défaut, " + this.optionsDeJeu[0] + " joueurs participeront à la partie) :\n");
		texte.append("\n                       [Appuyer sur la touche entrée pour continuer]");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		if(!reponse.trim().equals(""))
		{
			while(!Partie.estUnEntier(reponse) || reponse.equals("1"))
			{
				texte = new StringBuffer("");
				texte.append("\nVous devez entrer un nombre ! Veuillez resaisir le nombre de joueurs :");
				texte.append("\n\n                       [Appuyer sur la touche entrée pour continuer]");
				System.out.println(texte.toString());
				reponse = new String(scanner.nextLine());
			}	
			//if not enough cards for all players, add a new deck
			int nombreDeJeuxAvant = Integer.parseInt(this.optionsDeJeu[1]);	
			int compteur = 0;
			while(!this.verifierCompatibiliteNbJoueursNbCartes(reponse))
			{
				this.optionsDeJeu[1] = (Integer.parseInt(this.optionsDeJeu[1]) + 1) + "";
				compteur++;
			}			
			int nombreDeJeuxApres = Integer.parseInt(this.optionsDeJeu[1]);
			if(nombreDeJeuxAvant != nombreDeJeuxApres)
			{
				texte = new StringBuffer("");
				texte.append("\nNous avons dû ajouter " + compteur + " en raison du nombre de joueurs. \nVous jouez maintenant avec " + this.optionsDeJeu[1] + " paquets de cartes." );
				System.out.println(texte.toString());
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
		texte = new StringBuffer("");
		StringBuffer lobbyText = new StringBuffer("\n[SALON DES JOUEURS - " + nombreJoueurs + " joueurs enregistrés]\n");
		for(int x = 0 ; x < nombreJoueurs ; x++)
		{
			if(x == 0)
			{
				texte.append("\n[ETAPE 3] Veuillez saisir votre pseudo :\n\n");
				texte.append("                       [Appuyer sur la touche entrée pour continuer]\n\n");
				System.out.println(texte.toString());
				pseudoJoueur = new String(scanner.nextLine());
				while(pseudoJoueur == null || pseudoJoueur.trim().equals(""))
				{
					texte = new StringBuffer("");
					texte.append("\nVous devez entrer un pseudo (non vide). Veuillez en resaisir un :");		
					System.out.println(texte.toString());
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
			lobbyText.append("." + tableauDeJoueurs[x].getPseudo() + "\n");			
		}	
		System.out.println(lobbyText.toString());
		return(joueursEnregistres);
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
			texte.append("Vous devez entrer l'une des trois propositions. Veuillez resaisir l'option que vous souhaitez modifer :");
			System.out.println(texte.toString());
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
					while((!Partie.estUnEntier(reponse) || reponse.equals("1")))
					{
						texte.append("\nVous devez entrer un nombre qui doit être valide (différent de 1). Veuillez resaisir le nombre de joueurs :");
						System.out.println(texte.toString());
						reponse = new String(scanner.nextLine());
					}
					//if not enough cards for all players, add a new deck
					while(!this.verifierCompatibiliteNbJoueursNbCartes(reponse))
						this.optionsDeJeu[1] = (Integer.parseInt(this.optionsDeJeu[1]) + 1) + "";
					this.optionsDeJeu[0] = reponse;
				}
				break;
			case "2" : 
				texte.append("\nParamètres > Nombre de jeux de cartes");
				texte.append("\n-\nVeuillez choisir le nombre de jeux de cartes (par défaut, " + this.optionsDeJeu[1]);
				if(Integer.parseInt(this.optionsDeJeu[1]) == 1)
					texte.append(" jeu de cartes est défini) :");
				else
					texte.append(" jeux de cartes sont définis) :");
				System.out.println(texte.toString());
				reponse = scanner.nextLine();
				boolean valide = false;
				if(!reponse.trim().equals(""))
				{
					if(Partie.estUnEntier(reponse))
						if(this.verifierCompatibiliteNbCartesNbJoueurs(reponse))
							valide = true;
				}
				else
					valide = true;
				while(!valide)
				{
					System.out.println("\nVous devez entrer un nombre et le nombre de jeux de cartes (comptant 54 cartes) doit au moins pouvoir servir chaque joueur. Veuillez resaisir le nombre de jeux de cartes :");
					reponse = new String(scanner.nextLine());
					if(!reponse.trim().equals(""))
					{
						if(Partie.estUnEntier(reponse))
							if(this.verifierCompatibiliteNbCartesNbJoueurs(reponse))
								valide = true;
					}
					else
						valide = true;								
				}
				if(!reponse.trim().equals(""))
					this.optionsDeJeu[1] = reponse;			
				break;
			case "3" : 
				texte.append("\nParamètres > Système de points");
				texte.append("\n-\nSouhaitez-vous activer le système de points ? [Y/N]");
				System.out.println(texte.toString());
				reponse = scanner.nextLine();
				while(!(reponse).equals("Y") && !(reponse).equals("N"))
				{
					texte.append("\nVous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :");
					System.out.println(texte.toString());
					reponse = new String(scanner.nextLine());
				}
				this.optionsDeJeu[2] = reponse;
				if(reponse.equals("Y"))
					texte.append("\nLe système de points est désormais activé !\n");
				else
					texte.append("\nLe système de points est désormais désactivé !\n");
				System.out.println(texte.toString());
				break;
		}
	}
	
	public boolean verifierCompatibiliteNbJoueursNbCartes(String nombreJoueurs)
	{
		if((Integer.parseInt(nombreJoueurs) * 8) - (54 * Integer.parseInt(this.optionsDeJeu[1])) < 0)
			return(true);
		else
			return(false);
	}
	
	public boolean verifierCompatibiliteNbCartesNbJoueurs(String nombreJeuxDeCartes)
	{
		if((Integer.parseInt(nombreJeuxDeCartes) * 54) - (8 * Integer.parseInt(this.optionsDeJeu[0])) > 0)
			return(true);
		else
			return(false);
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
