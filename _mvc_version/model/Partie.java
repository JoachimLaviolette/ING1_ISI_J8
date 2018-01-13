package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe d'une partie de jeu (singleton) <br>
 * Représente la structure et le comportement d'une partie 
 * @author Joachim Laviolette
 * @version 1.0
 */
public class Partie extends Observable
{
	/**
	 * Instance de partie courante enregistrée
	 */
	private static Partie instance;
	/**
	 * Instance du jeu
	 */
	private Jeu instanceDeJeu;
	/**
	 * Variante courante de la partie
	 */
	private Variante varianteCourante;
	/**
	 * Joueur actif (jouant le tour courant)
	 */
	private Joueur joueurActif;
	/**
	 * Joueur gagant de la partie
	 */
	private Joueur joueurGagnant;
	/**
	 * Joueurs ayant vidé leur main
	 */
	private ArrayList<Joueur> joueursAyantFini;
	/**
	 * Joueurs de la partie
	 */
	private ArrayList<Joueur> joueursDeLaPartie;
	/**
	 * Talon de carte
	 */
	private LinkedList<Carte> talon;
	/**
	 * Pioche de jeu
	 */
	private LinkedList<Carte> pioche;
	/**
	 * Liste de cartes contenant les cartes du dépôt du joueur actif
	 */
	private LinkedList<Carte> cartesAJouer;
	/**
	 * Etat de la partie courante
	 */
	private boolean estLancee;
	/**
	 * Etat du tour courant
	 */	
	private boolean tourTermine = true;	
	/**
	 * Etat de retour (possible si aucune carte n'a encore été jouée)
	 */
	private boolean retourPossible;
	
	/**
	 * Constructeur de la classe
	 * @param instanceDeJeu Instance de jeu liée à la partie
	 * @param varianteChoisie Variante choisie pour la partie
	 * @param joueursEnregistres Liste des joueurs de la partie
	 */	
	private Partie(Jeu instanceDeJeu, Variante varianteChoisie, ArrayList<Joueur> joueursEnregistres)
	{
		super();
		this.instanceDeJeu = instanceDeJeu;
		this.varianteCourante = varianteChoisie;
		this.varianteCourante.setPartieAssociee(this);		
		if(joueursEnregistres != null)
		{
			this.joueursDeLaPartie = joueursEnregistres;
			this.joueurActif = this.trouverJoueurConcret();
			this.initialiserMains();
		}
		this.joueurGagnant = null;
		this.joueursAyantFini = new ArrayList<Joueur>();
		this.talon = new LinkedList<Carte>();
		this.pioche = new LinkedList<Carte>();
		this.initialiserPioche(this.instanceDeJeu.getOptionsDeJeu());
		this.melanger(this.pioche);		
		this.initialiserTalon();
		this.estLancee = true;
		this.cartesAJouer = new LinkedList<Carte>();
		this.retourPossible = true;
	}
	
	/**
	 * Méthode publique interrogeant la classe <br>
	 * Permet d'initialiser une partie de jeu <br> 
	 * Si aucun objet de la classe existe
	 * @param instanceDeJeu Instance du jeu
	 * @param varianteChoisie Vartiante choisie pour la partie
	 * @param joueursEnregistres Joueurs enregistrés pour la partie
	 * @return Instance de la classe Partie
	 */	
	public static Partie creerPartie(Jeu instanceDeJeu, Variante varianteChoisie, ArrayList<Joueur> joueursEnregistres)
	{
		if(instance == null)
			instance = new Partie(instanceDeJeu, varianteChoisie, joueursEnregistres);
		return(instance);
	}
	
	/**
	 * Initialise la main de chaque joueur depuis le jeu de cartes mélangé
	 */	
	public void initialiserMains()
	{
		for(int distribution = 0 ; distribution < 8 ; distribution++)
		{
			Iterator<Joueur> iteJoueurX = this.joueursDeLaPartie.iterator();
			while(iteJoueurX.hasNext())
				((Joueur)iteJoueurX.next()).getMain().add(this.pioche.pollLast());
		}
	}
	
	/**
	 * Initialise le talon avec la carte du dessus de la pioche
	 */
	public void initialiserTalon()
	{
		this.talon.add(this.pioche.pollLast());
	}

	/**
	 * Initialise la pioche <br>
	 * Cette méthode interroge la variante courante <br>
	 * Selon la variante, certaines cartes peuvent être exclues (ie. JOKER)
	 * @param optionsDeJeu Tableau contenant les options basiques du jeu (nombre de joueurs, nombre de paquets de cartes, activation de score)
	 */
	public void initialiserPioche(String[] optionsDeJeu)
	{
		int nombreJeuxDeCartes = Integer.parseInt(optionsDeJeu[1]);
		this.varianteCourante.initialiserPioche(this.pioche, nombreJeuxDeCartes);
	}
	
	/**
	 * Notifie les vues observatrices qu'elles doivent afficher le menu de paramtérage de partie
	 */
	
	public void jouer()
	{
		this.notifier("Jouer");
	}
	
	/**
	 * Demande au joueur de piocher (action indépendante du type de joueur, ou de la variante) <br>
	 * Termine automatiquement le tour par la suite
	 */
	public void piocher()
	{
		this.joueurActif.piocher();
		this.terminerTour(true);
	}
	
	/**
	 * Notifie les vues observatrices qu'elles doivent afficher le menu de choix de variante
	 */
	public void changerVariante()
	{
		this.notifier("Changer variante");
	}
	
	/**
	 * Permet de changer la variante de la partie <br>
	 * Si le paramètre en entrée est une chaine vide (correspond à la touche entrée de la saisie utilisateur), aucun changement produit <br>
	 * On notifie explicitement les vues qu'il faut revenir au menu de jeu in-game seulement dans ce cas <br>
	 * Dans le cas où une variante est spéciifiée, à la réception de la notification, la vue conséquente se chargera elle-même de ré-afficher le menu <br>
	 * Le traitement est le même uniquement pour l'affichage console
	 * @param nomNouvelleVariante Nom/clé de la nouvelle variante choisie
	 */
	public void changerVariante(String nomNouvelleVariante)
	{
		if(!nomNouvelleVariante.trim().equals(""))
		{
			this.executerActionChangerVariante(nomNouvelleVariante);
			this.notifier("Variante changée");
		}
		else
			this.notifier("Retour PV");
	}
	
	/**
	 * Notifie les vues qu'il faut passer du menu du choix de cartes au menu de jeu in-game
	 */
	public void retourDepuisPC()
	{
		this.notifier("Retour PC");
	}
	
	/**
	 * Notifie les vues qu'il faut passer du menu de changement de variante au menu de jeu in-game
	 */
	public void retourDepuisPV()
	{
		this.notifier("Retour PV");
	}
		
	/**
	 * Termine un tour en changeant le joueur actif actuel par son successeur <br>
	 * si vrai, alors le joueur actif doit être changé <br>
	 * sinon, le joueur actif reste le même <br>
	 * Utile notamment pour l'application de l'effet faisant rejouer le joueur par exemple <br>
	 * cf. la méthode appliquerEffetCartes() des sous-classes de variantes
	 * @param terminerTour Booléen indiquant s'il faut modifier ou non le joueur actif
	 */
	public synchronized void terminerTour(boolean terminerTour) 
	{
		this.notifier("Terminer tour");
		this.cartesAJouer.removeAll(this.cartesAJouer);
		if(this.joueurActif instanceof JoueurVirtuel)
			this.attendre(3000);
		if(terminerTour)
			this.joueurActif = this.joueurActif.getJoueurSuivant();
		this.tourTermine = true;
		/**
		 * Notifie le thread de la méthode lancerPartie() qu'il peut reprendre son activité
		 */
		this.notifyAll();	
	}	
	
	/**
	 * Démarre une partie en lançant un tour <br>
	 * Méthode rappelée par la méthode de terminaison de tour <br>
	 * Si un joueur gagnant est trouvé, alors termine la partie <br>
	 * Sinon, fait jouer le joueur actif		
	 */
	public void lancerPartie()
	{	
		new Thread(() -> 
		{
			synchronized(this) 
			{
				if(!this.joueurGagnantTrouve())
				{
					this.tourTermine = false;
					if(this.joueurActif instanceof JoueurConcret)
					{
						/**
						 * Notifie les vues que d'afficher le menu de choix d'action de jeu in-game
						 * Interactions via boutons pour l'interface graphique
						 * Interactions via saisie pour l'interface console
						 */
						this.notifier("Jouer tour");
					}
					else
					{
						/**
						 * Si le joueur actif est un bot, alors la partie exécute un algo automatisé
						 * cf. la méthode demarrerTour() de cette classe
						 */
						this.faireJouerBot();
					}
					/**
					 * Tant que le tour n'est pas terminé, le thread se met en attente
					 * C'est l'action de terminer tour qui notifiera ce thred qu'il peut reprendre son activité
					 */
					while(!this.tourTermine)
					{
						try
						{
							this.wait();
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
					this.lancerPartie();
				}
				else
					terminerPartie();
			}
		}).start();
	}
	
	/**
	 * Notifie les vues que la partie est terminée pour qu'elles se mettent à jour en conséquence
	 */
	public void terminerPartie()
	{
		this.estLancee = false;
		this.notifier("Terminer partie");
	}
	
	/**
	 * Fait jouer un joueur virtuel (bot) 
	 */	
	public void faireJouerBot()
	{
		this.demarrerTour();
	}
	
	/**
	 * Ordonnance l'ensemble des actions de jeux in-game possibles pour le joueur virtuel
	 */
	public void demarrerTour()
	{
		String choixAction = this.choisirActionMenuTour();
		boolean reproposer = false;
		switch(choixAction)
		{
			case "1" :
				reproposer = this.jouerTour();
				break;
			case "2" :
				this.piocher();
				break;
			/**
			 * Ici le choix numéro 3 est exclu car un joueur virtuel ne peut pas changer la variante courante
			 */
		}
		/**
		 * Tant que le joueur virtuel choisit de jouer, on lui repropose automatiquement de compléter son dépot
		 * S'arrête lorsqu'il choisit de ne plus le compléter
		 */
		while(reproposer)
		{	
			choixAction = this.choisirActionMenuTour();
			switch(choixAction)
			{
				case "1" :
					reproposer = this.jouerTour();
					break;
				case "2" :
					this.terminerActionTour();
					reproposer = false;
					break;
			}
		}
	}
	
	/**
	 * Interroge le bot quant à l'action qu'il souhaite effectuer
	 * @return Index de l'action choisie par le bot
	 */
	public String choisirActionMenuTour()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.demanderActionTour();
			}
			catch(GameException e)
			{
				e.printStackTrace();
			}
		} while(choix == null);
		return(choix);
	}
	
	/**
	 * Demande au bot quelle carte il souhaite jouer, puis la joue
	 * @return Booléen à vrai si le joueur a véritablement joué, faux sinon
	 */
	public boolean jouerTour()
	{
		String choixCarte = this.choisirCarteMenuJouer();
		Carte carte = null;
		try
		{
			carte = this.getJoueurActif().getCarte(choixCarte);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch(UnexistingCardException e1) // si mauvaise saisie de la part du bot mais normalement impossible
		{
			e1.printStackTrace();
		}
		return(this.jouer(carte));
	}
	
	/**
	 * Récupère de la main du bot l'index de la carte qu'il souhaite jouer  
	 * @return Index de la carte sélectionnée
	 */
	public String choisirCarteMenuJouer()
	{
		String choix = null;
		do
		{
			try
			{
				choix = this.demanderCarte();
			}
			catch(GameException e)
			{
				e.printStackTrace();
			}
		} while(choix == null);
		return(choix);
	}
	
	/**
	 * Vérifie que la carte souhaitée être jouée est conforme et la dépose si c'est le cas <br>
	 * Si la carte n'est pas conforme, le joueur pioche automatiquement et son tour est terminé <br>
	 * Si la carte n'est pas conforme mais qu'il s'agit d'une complétion de dépôt, une erreur de combinaison est notifée et le joueur doit réitérer son choix
	 * @param carte Carte que le joueur souhaite jouer
	 * @return Booléen à vrai si le joueur a véritablement joué, faux sinon
	 */
	public boolean jouer(Carte carte)
	{
		/**
		 * S'il s'agit d'une complétion de dépôt
		 * On vérifie que la carte jouée est conforme (même valeur !) que la carte précédente
		 * Si c'est le cas, le booléen valide reste à vrai, sinon, on notifie une erreur de combinaison, et le booléen est mis à faux
		 */
		if(!this.cartesAJouer.isEmpty())
		{
			// saisie vide (console) ou appui bouton "terminer tour" (GUI)
			if(carte == null)
			{
				this.terminerActionTour();
				return(false);
			}
			else
			{
				if(!(this.varianteCourante.combinaisonAutorisee(this.cartesAJouer.getLast().getValeur(), carte.getValeur())))
				{
					this.notifier("Erreur de combinaison");
					return(false);
				}
			}
		}
		else
		{
			if(carte == null)
				return(false);
		}
		/**
		 * S'il s'agit d'une complétion de dépôt VALIDEE ou s'il s'agit d'une première carte jouée VALIDEE
		 * Si c'est le cas, alors on itère l'action de dépôt sur le talon
		 * Puis on complète le dépôt temporaire 
		 */
		/**
		 * Action qui dépose la carte sur le talon si conforme et la retire de la main du joueur
		 * Mais l'effet n'est pas encore apppliqué
		 */
		boolean resultat = this.executerActionJouer(carte);
		if(resultat)
		{
			this.cartesAJouer.add(carte); //dépôt temporaire, complété tant que le tour n'est pas terminé et que le joueur souhaite jouer
			/**
			 * S'il s'agit d'une IA confirmé, et s'il s'agit de la première carte jouée par le bot, on enregistre celle-ci
			 * Utile pour que le bot puisse par la suite redéfinir ses cartes jouables pour compléter son dépôt	
			 * cf. méthode recupererCartesValides() de la classe IAConfirme			
			 */
			if(this.joueurActif instanceof JoueurVirtuel)
			{
				if(((JoueurVirtuel)this.joueurActif).getStrategieCourante() instanceof IAConfirme)
				{
					if(((IAConfirme)((JoueurVirtuel)this.joueurActif).getStrategieCourante()).getCarteEnregistree() == null)
						((IAConfirme)((JoueurVirtuel)this.joueurActif).getStrategieCourante()).setCarteEnregistree(carte);
				}
			}
			/**
			 * Si le joueur actif est le joueur concret
			 * On désactive le retour au menu de choix in-game (choix de programmation)
			 * Le joueur, une fois sa première carte jouer, ne peut plus revenir en arrière
			 * Il lui est ensuite demander de rejouer 
			 */
			else
			{
				this.desactiverRetourPossible();
				this.notifier("Désactiver retour");
			}
			/**
			 * On notifie qu'une carte a été jouée, en passant en paramètre l'objet la représentant 
			 * On vérifie le nombre de cartes en main du joueur, pour lui proposer éventuellement d'annoncer carte
			 */
			Object[] arg = {"Carte jouée", carte};
			this.notifier(arg);
			this.verifierNombreCartesEnMain();
		}
		/**
		 * S'il s'agit de la première carte jouée par le joueur et que celle-ci n'est pas conforme, alors il doit piocher et son tour est terminé
		 */
		else
		{
			if(this.cartesAJouer.isEmpty())
			{
				this.piocher();
				return(false);
			}			
		}
		return(true);
	}
	
	/**
	 * Vérifie que la carte souhaitée être jouée est conforme <br>
	 * Si c'est le cas, la carte est ajoutée au talon <br>
	 * Le retrait de la main du joueur est effectué par la méthode jouerCarte de la classe joueur
	 * @param carteAJouer La carte que le joueur souhaite jouer
	 * @return Booléen à vrai si la carte souhaitée est conforme et a été déposée, faux sinon
	 */
	public boolean jouerCarte(Carte carteAJouer)
	{
		boolean verifiee = this.verifierConformiteCarte(carteAJouer);
		if(verifiee)
		{
			this.talon.add(carteAJouer);
			/**
			 * Notifie les vues (notamment la GUI) pour qu'elle mette à jour le talon de cartes
			 */
			this.notifier("Mettre à jour talon");
			return(true);
		}
		else
			return(false);
	}
	
	/**
	 * Méthode appelée une fois que le joueur choisit de terminer son tour <br>
	 * Sa complétion de dépôt est terminée, cette méthode demande à la variante courante d'appliquer les effets des cartes déposées <br>
	 */
	public void terminerActionTour()
	{
		if(!this.cartesAJouer.isEmpty())
		{
			/**
			 * Si le joueur actif qui termine son tour est un bot, on efface la carte enregistrée temporaire
			 */
			if(this.joueurActif instanceof JoueurVirtuel)
			{
				if(((JoueurVirtuel)this.joueurActif).getStrategieCourante() instanceof IAConfirme)
				{
					if(((IAConfirme)((JoueurVirtuel)this.joueurActif).getStrategieCourante()).getCarteEnregistree() != null)
						((IAConfirme)((JoueurVirtuel)this.joueurActif).getStrategieCourante()).setCarteEnregistree(null);
				}
			}
			/**
			 * On réactive le retour du menu de choix de cartes au menu de choix d'actions in-game
			 * Les vues (notamment la GUI) se mettent à jour (ie. bouton RETOUR qui redevient cliquable)
			 */
			this.activerRetourPossible();
			this.notifier("Activer retour");
			/**
			 * On applique l'effet des cartes déposées en fonction de la variante courante
			 */
			this.executerActionAppliquerEffetsCartes(this.cartesAJouer);			
		}
		/**
		 * Si le joueur termine son tour sans avoir joué de carte (appui sur bouton (en mode GUI) ou saisie vide (en mode Console))
		 * Cela équivaut à retourner dans le menu de choix d'actions in-game
		 */
		else
			this.notifier("Retour PC");
	}
	
	/**
	 * Vérifie le nombre de cartes en main du joueurs <br>
	 * S'il n'en possède plus qu'une, on lui propose d'annoncer carte
	 */
	public void verifierNombreCartesEnMain()
	{
		if(this.joueurActif.getMain().size() == 1)
			this.notifier("Proposer annoncer carte");
	}
	
	/**
	 * Exécute l'action "jouer" du côté joueur <br>
	 * Le joueur interroge la méthode jouerCarte() de cette classe qui retourne un booléen <br>
	 * Si vrai, la méthode jouer() de la classe exécute son traitement (retrait de la carte de la main)
	 * @param carte Carte que le joueur souhaite jouer
	 * @return Booléen à vrai si la carte a pu être jouée (si conforme etc.), faux sinon
	 */
	public boolean executerActionJouer(Carte carte)
	{
		boolean aJoue = this.joueurActif.jouer(carte);
		return(aJoue);
	}	
	
	/**
	 * Demande à la variante courante d'appliquer les effets des cartes déposées
	 * @param cartesAJouer Dépôt de cartes du joueur actif
	 */
	public void executerActionAppliquerEffetsCartes(LinkedList<Carte> cartesAJouer)
	{
		this.varianteCourante.appliquerEffetCarte(cartesAJouer, this.joueurActif);
	}
	
	/**
	 * Excéute l'action de changement de variante
	 * @param indexVarianteChoisie Index de la variante remplaçante choisie
	 */
	public void executerActionChangerVariante(String indexVarianteChoisie)
	{
		if(this.joueurActif instanceof JoueurConcret)
		{
			String choix = indexVarianteChoisie;
			Variante varianteChoisie = this.getVarianteCourante();
			switch(choix)
			{
				case "1" : 
					varianteChoisie = new VarianteMinimale(instance);
					break;
				case "2" : 
					varianteChoisie = new VarianteMonclar(instance);
					break;
				case "3" : 
					varianteChoisie = new Variante4(instance);
					break;
				case "4" : 
					varianteChoisie = new Variante5(instance);
					break;
				case "5" : 
					varianteChoisie = new VariantePerso(instance);
					break;
			}
			this.setVarianteCourante(varianteChoisie);
		}
	}
	
	/**
	 * Mélange un paquet de carte passé en paramètre
	 * @param paquetsCartes Paquet de cartes qui doit être mélangé
	 */
	public void melanger(LinkedList<Carte> paquetsCartes)
	{
		int indexAleatoire;
		Carte carteTmp;
		/**
		 * Resitue alétoirement chacune des cartes du paquet
		 */
		for(int indexCarte = 0 ; indexCarte < paquetsCartes.size() ; indexCarte++)
		{
			indexAleatoire = (int)Math.floor(Math.random() * (paquetsCartes.size() - 0));
			carteTmp = (Carte)paquetsCartes.toArray()[indexCarte];			
			paquetsCartes.set(indexCarte, (Carte)paquetsCartes.toArray()[indexAleatoire]);
			paquetsCartes.set(indexAleatoire, carteTmp);			
		}
	}	
	
	/**
	 * Première méthode de détection de fin de partie <br>
	 * Vérifie si un joueur de la partie ne possède plus de cartes (automatiquement le premier trouvé)
	 * @return Booléen à vrai si un joueur ne possède plus de cartes, faux sinon
	 */
	public boolean joueurGagnantTrouve()
	{	
		for(int x = 0 ; x < this.joueursDeLaPartie.size() ; x++)
		{
			if(this.joueursDeLaPartie.get(x).getMain().size() == 0)
			{
				this.joueurGagnant = this.joueursDeLaPartie.get(x);
				return(true);
			}
		}
		return(false);
	}
	
	/**
	 * Deuxième méthode de détection de fin de partie <br>
	 * Vérifie le nombre de joueurs ayant terminé (n'ayant donc plus de carte en main) <br>
	 * Une fois chaque joueur analysé, la méthode compare le nombre de joueurs total de la partie et le nombre de joueurs aant terminé <br>
	 * Si la différence est de 1, c'est qu'il ne reste qu'une seul joueur ayant des cartes en main, la partie est donc terminée
	 * @return Booléen à vrai s'il ne reste plus qu'un seul joueur ayant des cartes en main, faux sinon
	 */
	public boolean joueurGagnantTrouve2()
	{	
		for(int x = 0 ; x < this.joueursDeLaPartie.size() ; x++)
		{
			if(this.joueursDeLaPartie.get(x).getMain().size() == 0)
			{
				if(!this.joueursAyantFini.contains((Joueur)this.joueursDeLaPartie.get(x)))
				{
					/**
					 * Supprime le joueur ayant terminé de la liste des adversaires des autres joueurs
					 */
					Joueur joueurAEnlever = (Joueur)this.joueursDeLaPartie.get(x);
					Iterator<Joueur> iteAdversaireX = ((Joueur)this.joueursDeLaPartie.get(x)).getAdversaires().iterator();
					Joueur adversaireX;
					while(iteAdversaireX.hasNext())
					{
						adversaireX = iteAdversaireX.next();
						adversaireX.getAdversaires().remove(joueurAEnlever);
					}
					/**
					 * Redéfinit les joueurs précédent et suivant de chacun des joueurs toujours en liste
					 */
					Joueur joueurAyantFini = (Joueur)this.joueursDeLaPartie.get(x);
					Joueur predecesseurJAF = joueurAyantFini.getJoueurPrecedent();
					joueurAyantFini.getJoueurPrecedent().setJoueurSuivant(joueurAyantFini.getJoueurSuivant());
					joueurAyantFini.getJoueurSuivant().setJoueurPrecedent(predecesseurJAF);
					/**
					 * Ajoute enfin le joueur ayant terminé à la liste des joueurs ayant fini
					 */
					this.joueursAyantFini.add((Joueur)this.joueursDeLaPartie.get(x));
				}
			}
		}
		/**
		 * Compare le nombre de joueurs ayant fini et le nombre de joueurs total de la partie
		 * NB : La liste des joueurs de la partie ne change pas, on ne supprime pas les joueurs ayant terminé de cette liste !
		 */
		if(this.joueursAyantFini.size() == this.joueursDeLaPartie.size() - 1)
			return(true);
		return(false);
	}

	/**
	 * Demande à la variante courante de vérifier la conformité d'une carte
	 * @param carteAJouer Carte souhaitée être déposée/jouée
	 * @return Booléen à vrai si la carte est définit comme conforme, faux sinon
	 */
	private boolean verifierConformiteCarte(Carte carteAJouer)
	{
		boolean res = false;
		try
		{
			res = this.varianteCourante.verifierConformiteCarte(carteAJouer);
		}
		catch(UncompliantSpecialCardSaved e)
		{
			e.printStackTrace();
		}
		return(res);
	}
		
	/**
	 * Permet d'obtenir la dernière carte d'un paquet (ie. main du joueur, talon, pioche) 
	 * @param paquetCartes Paquet de cartes dont on souhaite connaitre la dernière carte
	 * @return Dernière carte du paquet spécifié
	 */
	public Carte getDerniereCarte(LinkedList<Carte> paquetCartes)
	{
		return(paquetCartes.peekLast());
	}
	
	/**
	 * Vérifie si la pioche est vide et la regarnit si c'est le cas
	 */
	public void verifierPioche()
	{
		if(this.pioche.isEmpty())
		{
			//On récupère la dernière carte du talon (du dessus)
			Carte derniereCarteTalon = this.talon.pollLast();
			//On mélange le reste du talon
			this.melanger(this.talon);
			//On convertit ce tas en pioche
			this.pioche.addAll(this.talon);
			//On vide le talon
			this.talon.removeAll(this.talon);
			//On ajoute la première carte récupérée à celui-ci, qui forme le nouveau talon
			this.talon.add(derniereCarteTalon);
		}
	}	

	/**
	 * Vérifie s'il est toujours possible de piocher une carte <br>
	 * Si le talon ne comporte qu'une seule carte, et que la pioche est vide, alors il n'est plus possible de piocher 
	 * @return Booléean à vrai s'il est toujours possible de piocher, faux sinon
	 */
	public boolean peutEncorePiocher()
	{
		int nombreCartesPossedees = this.joueurActif.getMain().size();		
		Iterator<Joueur> iteAdversaires = this.joueurActif.getAdversaires().iterator();
		while(iteAdversaires.hasNext())
			nombreCartesPossedees += iteAdversaires.next().getMain().size();
		/**
		 * Ici, le nombre de cartes possédées est une condition supplémentaire mais négligeable
		 * Car le nombre de cartes n'est pas forcément de 54, il peut être fixé selon la variante et également par le joueur
		 * La condition suffisante est que la pioche doit être vide et que le talon ne doit comporter qu'une seule carte
		 */
		if(nombreCartesPossedees == 53 && this.pioche.isEmpty() && this.talon.size() == 1)
			return(false);
		else
			return(true);
	}	
	
	/**
	 * Cherche le joueur concret dans la liste des joueurs de la partie
	 * @return Le joueur concret de la liste des participants
	 */
	public JoueurConcret trouverJoueurConcret()
	{
		Iterator<Joueur> ite = this.joueursDeLaPartie.iterator();
		while(ite.hasNext())
		{
			Joueur joueurX = ite.next();
			if(joueurX instanceof JoueurConcret)
				return((JoueurConcret)(joueurX));
		}
		return(null);
	}
	
	/**
	 * Enregistre le symbole demandé à la suite d'un dépôt de 8 (si l'effet produit est la demande de symbole) <br>
	 * Analyse l'index de choix en paramètre, et convertit cela en carte temporaire qui est enregistrée au niveau de la variante courante
	 * @param choix Index de choix représentant le symbole demandé
	 */
	public void enregistrerSymboleCarteDemandee(String choix)
	{
		switch(choix)
		{
			case "1" :
				this.varianteCourante.setCarteDemandee(new Carte(null, Symbole.CARREAU, null));
				break;
			case "2" :
				this.varianteCourante.setCarteDemandee(new Carte(null, Symbole.COEUR, null));
				break;
			case "3" :
				this.varianteCourante.setCarteDemandee(new Carte(null, Symbole.PIQUE, null));
				break;
			case "4" : 
				this.varianteCourante.setCarteDemandee(new Carte(null, Symbole.TREFLE, null));
				break;
		}
	}
	
	/**
	 * Enregistre la couleur demandée à la suite d'un dépôt de 8 (si l'effet produit est la demande de couleur) <br>
	 * Analyse l'index de choix en paramètre, et convertit cela en carte temporaire qui est enregistrée au niveau de la variante courante
	 * @param choix Index de choix représentant la couleur demandée
	 */
	public void enregistrerCouleurCarteDemandee(String choix)
	{
		switch(choix)
		{
			case "1" :
				this.varianteCourante.setCarteDemandee(new Carte(null, null, Couleur.ROUGE));
				break;
			case "2" : 
				this.varianteCourante.setCarteDemandee(new Carte(null, null, Couleur.NOIRE));
				break;
		}
	}
	
	/**
	 * Endort le thread courant et permet d'attendre un certain temps avant entre la succession de tour
	 * @param duree Laps de temps d'attente (en secondes)
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
	 * Demande au joueur actif l'action à exécuter
	 * @return Index représentant l'action choisie par le joueur {1,2,3} si joueur concret, {1,2} si joueur virtuel 
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
	 */
	public String demanderActionTour() throws UncompliantChoiceException
	{
		String choix = this.joueurActif.choisirAction();
		if(!(!choix.trim().equals("") && (Partie.estUnEntier(choix)) && (Integer.parseInt(choix) <= 3 && Integer.parseInt(choix) >= 1)))
		{
			throw new UncompliantChoiceException("Veuillez choisir une des trois actions proposées :");
		}
		return(choix);
	}	
	
	/**
	 * Demande au joueur actif la carte à jouer
	 * @return Index représentant la carte choisie par le joueur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
	 */
	public String demanderCarte() throws UncompliantChoiceException 
	{
		String choix = this.joueurActif.choisirCarte();
		if(!choix.trim().equals("") && !((Partie.estUnEntier(choix)) && (Integer.parseInt(choix) <= this.joueurActif.getMain().size() && Integer.parseInt(choix) >= 1)))
		{
			throw new UncompliantChoiceException("Veuillez choisir une carte de la liste :");
		}
		return(choix);
	}
	
	/**
	 * Demande au joueur actif la variante
	 * @return Index représentant la variante choisie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée par le joueur virtuel (ie. action 3)
	 */
	public String demanderVariante() throws UncompliantChoiceException
	{
		String choix = (((JoueurConcret)this.joueurActif).choisirVariante());
		if(this.joueurActif instanceof JoueurConcret)
		{
			if(!choix.trim().equals(""))
			{
				if(!choix.equals("1") && !choix.equals("2") && !choix.equals("3") && !choix.equals("4") && !choix.equals("5"))
					throw new UncompliantChoiceException("Vous devez choisir une réponse réponse entre [1 et 5], ou une chaine vide.\nVeuillez resaisir votre réponse :");
			}
		}
		else
			throw new UncompliantChoiceException("Variante demandée à un joueur virtuel");
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant à la couleur souhaitée à la suite d'un dépôt de 8 <br>
	 * Méthode appelée par le controleur console
	 * @return Index caractérisant la couleur choisie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
	 */
	public String demanderCouleurCarte() throws UncompliantChoiceException
	{
		String choix = this.choisirCouleurCarte(null);
		if(!choix.equals("1") && !choix.equals("2"))
		{
			throw new UncompliantChoiceException("Vous devez choisir une réponse entre [1 et 2]. Veuillez resaisir votre réponse :");
		}
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant au symbole souhaité à la suite d'un dépôt de 8 <br>
	 * Méthode appelée par le controleur console 
	 * @return Index caractérisant le symbole choisi
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
	 */
	public String demanderSymboleCarte() throws UncompliantChoiceException
	{ 
		String choix = this.choisirSymboleCarte(null);
		if(!choix.equals("1") && !choix.equals("2") && !choix.equals("3") && !choix.equals("4"))
		{
			throw new UncompliantChoiceException("Vous devez choisir une réponse entre [1 et 4]. Veuillez resaisir votre réponse :");
		}
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant à l'annonce de carte s'il ne lui en reste qu'une en main <br>
	 * Méthode appelée par le controleur console
	 * @return Réponse du joueur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est réalisée
	 */
	public String demanderAnnoncerCarte() throws UncompliantChoiceException
	{
		String choix = this.choisirAnnoncerCarte(null);
		if(!(choix).equals("Y") && !(choix).equals("N"))
		{
			throw new UncompliantChoiceException("Vous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :");
		}
		return(choix);
	}
	
	/**
	 * Si un index de couleur est spécifié en paramètre, la méthode enregistre directement la couleur demandée <br>
	 * Sinon, elle demande au joueur de la choisir
	 * @param choixCouleur Index de la couleur choisie qui peut être null
	 * @return Index de la couleur choisie (utile pour le controleur console, si la saisie est erronée)
	 */
	public String choisirCouleurCarte(String choixCouleur)
	{
		String choix = choixCouleur;
		if(choix == null)
			choix = this.joueurActif.choisirCouleurCarteApresHuit();	
		this.enregistrerCouleurCarteDemandee(choix);
		return(choix);
	}
	
	/**
	 * Si un index de symbole est spécifié en paramètre, la méthode enregistre directement le symbole demandé <br>
	 * Sinon, elle demande au joueur de le choisir
	 * @param choixSymbole Index du symbole choisi qui peut être null
	 * @return Index du symbole choisi (utile pour le controleur console, si la saisie est erronée)
	 */
	public String choisirSymboleCarte(String choixSymbole)
	{
		String choix = choixSymbole;
		if(choix == null)
			choix = this.joueurActif.choisirSymboleCarteApresHuit();	
		this.enregistrerSymboleCarteDemandee(choix);
		return(choix);
	}
	
	/**
	 * Si une clé de choix d'annonce est spécifiée, la méthode l'évalue <br>
	 * Si "Y", alors le joueur annonce carte <br>
	 * Sinon (si "N" ou autre saisie), aucun traitement, on retourne la clé pour le controleur console qui se charge de vérifier la conformité
	 * @param choixAnnonce Index du choix d'annoncer carte, saisi par le joueur (Y ou N)
	 * @return Clé du choix d'annonce
	 */
	public String choisirAnnoncerCarte(String choixAnnonce)
	{	
		String choix = choixAnnonce;
		if(choix == null)
			choix = this.joueurActif.proposerAnnoncerCarte();	
		if(choix.equals("Y"))
		{
			this.joueurActif.annoncerCarte();
			this.notifier("Annonce carte effectuée");
		}
		return(choix);
	}
	
	/**
	 * Retourne la variante courante
	 * @return Variante courante
	 */
	public Variante getVarianteCourante() 
	{
		return this.varianteCourante;
	}

	/**
	 * Modifie la variante courante
	 * @param varianteCourante Nouvelle variante
	 */
	public void setVarianteCourante(Variante varianteCourante) 
	{
		this.varianteCourante = varianteCourante;
	}
	
	/**
	 * Retourne le joueur gagnant (le premier ayant terminé)
	 * @return Le premier joueur ayant terminé la partie
	 */
	public Joueur getJoueurGagnant()
	{
		return this.joueurGagnant;
	}
	
	/**
	 * Modifie le joueur gagnant
	 * @param joueurGagnant Joueur ayant terminé la partie
	 */
	public void setJoueurGagnant(Joueur joueurGagnant)
	{
		this.joueurGagnant = joueurGagnant;
	}

	/**
	 * Retourne le joueur actif
	 * @return Joueur étant en train de jouer
	 */
	public Joueur getJoueurActif() 
	{
		return this.joueurActif;
	}

	/**
	 * Modifie le joueur actif
	 * @param joueurActif Joueur qui doit jouer
	 */
	public void setJoueurActif(Joueur joueurActif) 
	{
		this.joueurActif = joueurActif;
	}
	
	/**
	 * Retour l'ensemble des joueurs de la partie sous forme de liste
	 * @return Liste des joueurs de la partie
	 */
	public ArrayList<Joueur> getJoueursDeLaPartie() 
	{
		return this.joueursDeLaPartie;
	}
	
	/**
	 * Modifie la liste des joueurs de la partie
	 * @param joueursDeLaPartie Liste des joueurs de la partie
	 */
	public void setJoueursDeLaPartie(ArrayList<Joueur> joueursDeLaPartie) 
	{
		this.joueursDeLaPartie = joueursDeLaPartie;
	}

	/**
	 * Retourne le talon de cartes
	 * @return Talon de cartes de la partie
	 */
	public LinkedList<Carte> getTalon() 
	{
		return this.talon;
	}
	
	/**
	 * Retourne la pioche de cartes
	 * @return Pioche de cartes de la partie
	 */
	public LinkedList<Carte> getPioche() 
	{
		return this.pioche;
	}
	
	/**
	 * Retourne l'état de la partie
	 * @return Booléen à vrai si la partie est lancée, faux sinon
	 */
	public boolean getEstLancee()
	{
		return(this.estLancee);
	}
	
	/**
	 * Modifie l'état de la partie
	 * @param valeur Booléen à vrai si la partie est lancée, faux sinon
	 */
	public void setEstLancee(boolean valeur)
	{
		this.estLancee = valeur;
	}
	
	/**
	 * Retourne l'état du retour s'il est possible ou non
	 * @return Booléen à vrai si le retour au menu de choix d'actions in-game est possible, faux sinon
	 */
	public boolean getRetourPossible()
	{
		return(this.retourPossible);
	}
	
	/**
	 * Désactive le retour possible au menu de choix d'actions in-game
	 */
	public void desactiverRetourPossible()
	{
		this.retourPossible = false;
	}	
	
	/**
	 * Active le retour possible au menu de choix d'actions in-game
	 */
	public void activerRetourPossible()
	{
		this.retourPossible = true;
	}
	
	/**
	 * Notifie les vues observatrices du modèle de Partie qu'un changement a été effectué <br>
	 * A chaque fois qu'un changement est effectué, concrètement ou abstraitement, les vues sont notifiées 
	 * @param arg Objet de notification (ie. message, instance etc.)
	 */
	
	public void notifier(Object arg)
	{
		this.setChanged();
		this.notifyObservers(arg);
	}	
	
	/**
	 * Méthode statique permettant de tester si une chaine de caractères représente un entier ou non
	 * @param valeur Chaine de caractères à tester
	 * @return Booléen à vrai si la chaine représente un entier, faux sinon
	 */
	
	public static boolean estUnEntier(String valeur)
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
}
