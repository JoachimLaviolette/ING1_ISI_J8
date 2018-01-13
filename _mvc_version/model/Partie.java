package _mvc_version.model;

import java.util.*;

import _mvc_version._exceptions.*;

/**
 * Classe d'une partie de jeu (singleton) <br>
 * Repr�sente la structure et le comportement d'une partie 
 * @author Joachim Laviolette
 * @version 1.0
 */
public class Partie extends Observable
{
	/**
	 * Instance de partie courante enregistr�e
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
	 * Joueurs ayant vid� leur main
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
	 * Liste de cartes contenant les cartes du d�p�t du joueur actif
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
	 * Etat de retour (possible si aucune carte n'a encore �t� jou�e)
	 */
	private boolean retourPossible;
	
	/**
	 * Constructeur de la classe
	 * @param instanceDeJeu Instance de jeu li�e � la partie
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
	 * M�thode publique interrogeant la classe <br>
	 * Permet d'initialiser une partie de jeu <br> 
	 * Si aucun objet de la classe existe
	 * @param instanceDeJeu Instance du jeu
	 * @param varianteChoisie Vartiante choisie pour la partie
	 * @param joueursEnregistres Joueurs enregistr�s pour la partie
	 * @return Instance de la classe Partie
	 */	
	public static Partie creerPartie(Jeu instanceDeJeu, Variante varianteChoisie, ArrayList<Joueur> joueursEnregistres)
	{
		if(instance == null)
			instance = new Partie(instanceDeJeu, varianteChoisie, joueursEnregistres);
		return(instance);
	}
	
	/**
	 * Initialise la main de chaque joueur depuis le jeu de cartes m�lang�
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
	 * Cette m�thode interroge la variante courante <br>
	 * Selon la variante, certaines cartes peuvent �tre exclues (ie. JOKER)
	 * @param optionsDeJeu Tableau contenant les options basiques du jeu (nombre de joueurs, nombre de paquets de cartes, activation de score)
	 */
	public void initialiserPioche(String[] optionsDeJeu)
	{
		int nombreJeuxDeCartes = Integer.parseInt(optionsDeJeu[1]);
		this.varianteCourante.initialiserPioche(this.pioche, nombreJeuxDeCartes);
	}
	
	/**
	 * Notifie les vues observatrices qu'elles doivent afficher le menu de paramt�rage de partie
	 */
	
	public void jouer()
	{
		this.notifier("Jouer");
	}
	
	/**
	 * Demande au joueur de piocher (action ind�pendante du type de joueur, ou de la variante) <br>
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
	 * Si le param�tre en entr�e est une chaine vide (correspond � la touche entr�e de la saisie utilisateur), aucun changement produit <br>
	 * On notifie explicitement les vues qu'il faut revenir au menu de jeu in-game seulement dans ce cas <br>
	 * Dans le cas o� une variante est sp�ciifi�e, � la r�ception de la notification, la vue cons�quente se chargera elle-m�me de r�-afficher le menu <br>
	 * Le traitement est le m�me uniquement pour l'affichage console
	 * @param nomNouvelleVariante Nom/cl� de la nouvelle variante choisie
	 */
	public void changerVariante(String nomNouvelleVariante)
	{
		if(!nomNouvelleVariante.trim().equals(""))
		{
			this.executerActionChangerVariante(nomNouvelleVariante);
			this.notifier("Variante chang�e");
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
	 * si vrai, alors le joueur actif doit �tre chang� <br>
	 * sinon, le joueur actif reste le m�me <br>
	 * Utile notamment pour l'application de l'effet faisant rejouer le joueur par exemple <br>
	 * cf. la m�thode appliquerEffetCartes() des sous-classes de variantes
	 * @param terminerTour Bool�en indiquant s'il faut modifier ou non le joueur actif
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
		 * Notifie le thread de la m�thode lancerPartie() qu'il peut reprendre son activit�
		 */
		this.notifyAll();	
	}	
	
	/**
	 * D�marre une partie en lan�ant un tour <br>
	 * M�thode rappel�e par la m�thode de terminaison de tour <br>
	 * Si un joueur gagnant est trouv�, alors termine la partie <br>
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
						 * Si le joueur actif est un bot, alors la partie ex�cute un algo automatis�
						 * cf. la m�thode demarrerTour() de cette classe
						 */
						this.faireJouerBot();
					}
					/**
					 * Tant que le tour n'est pas termin�, le thread se met en attente
					 * C'est l'action de terminer tour qui notifiera ce thred qu'il peut reprendre son activit�
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
	 * Notifie les vues que la partie est termin�e pour qu'elles se mettent � jour en cons�quence
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
			 * Ici le choix num�ro 3 est exclu car un joueur virtuel ne peut pas changer la variante courante
			 */
		}
		/**
		 * Tant que le joueur virtuel choisit de jouer, on lui repropose automatiquement de compl�ter son d�pot
		 * S'arr�te lorsqu'il choisit de ne plus le compl�ter
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
	 * Interroge le bot quant � l'action qu'il souhaite effectuer
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
	 * @return Bool�en � vrai si le joueur a v�ritablement jou�, faux sinon
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
	 * R�cup�re de la main du bot l'index de la carte qu'il souhaite jouer  
	 * @return Index de la carte s�lectionn�e
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
	 * V�rifie que la carte souhait�e �tre jou�e est conforme et la d�pose si c'est le cas <br>
	 * Si la carte n'est pas conforme, le joueur pioche automatiquement et son tour est termin� <br>
	 * Si la carte n'est pas conforme mais qu'il s'agit d'une compl�tion de d�p�t, une erreur de combinaison est notif�e et le joueur doit r�it�rer son choix
	 * @param carte Carte que le joueur souhaite jouer
	 * @return Bool�en � vrai si le joueur a v�ritablement jou�, faux sinon
	 */
	public boolean jouer(Carte carte)
	{
		/**
		 * S'il s'agit d'une compl�tion de d�p�t
		 * On v�rifie que la carte jou�e est conforme (m�me valeur !) que la carte pr�c�dente
		 * Si c'est le cas, le bool�en valide reste � vrai, sinon, on notifie une erreur de combinaison, et le bool�en est mis � faux
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
		 * S'il s'agit d'une compl�tion de d�p�t VALIDEE ou s'il s'agit d'une premi�re carte jou�e VALIDEE
		 * Si c'est le cas, alors on it�re l'action de d�p�t sur le talon
		 * Puis on compl�te le d�p�t temporaire 
		 */
		/**
		 * Action qui d�pose la carte sur le talon si conforme et la retire de la main du joueur
		 * Mais l'effet n'est pas encore apppliqu�
		 */
		boolean resultat = this.executerActionJouer(carte);
		if(resultat)
		{
			this.cartesAJouer.add(carte); //d�p�t temporaire, compl�t� tant que le tour n'est pas termin� et que le joueur souhaite jouer
			/**
			 * S'il s'agit d'une IA confirm�, et s'il s'agit de la premi�re carte jou�e par le bot, on enregistre celle-ci
			 * Utile pour que le bot puisse par la suite red�finir ses cartes jouables pour compl�ter son d�p�t	
			 * cf. m�thode recupererCartesValides() de la classe IAConfirme			
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
			 * On d�sactive le retour au menu de choix in-game (choix de programmation)
			 * Le joueur, une fois sa premi�re carte jouer, ne peut plus revenir en arri�re
			 * Il lui est ensuite demander de rejouer 
			 */
			else
			{
				this.desactiverRetourPossible();
				this.notifier("D�sactiver retour");
			}
			/**
			 * On notifie qu'une carte a �t� jou�e, en passant en param�tre l'objet la repr�sentant 
			 * On v�rifie le nombre de cartes en main du joueur, pour lui proposer �ventuellement d'annoncer carte
			 */
			Object[] arg = {"Carte jou�e", carte};
			this.notifier(arg);
			this.verifierNombreCartesEnMain();
		}
		/**
		 * S'il s'agit de la premi�re carte jou�e par le joueur et que celle-ci n'est pas conforme, alors il doit piocher et son tour est termin�
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
	 * V�rifie que la carte souhait�e �tre jou�e est conforme <br>
	 * Si c'est le cas, la carte est ajout�e au talon <br>
	 * Le retrait de la main du joueur est effectu� par la m�thode jouerCarte de la classe joueur
	 * @param carteAJouer La carte que le joueur souhaite jouer
	 * @return Bool�en � vrai si la carte souhait�e est conforme et a �t� d�pos�e, faux sinon
	 */
	public boolean jouerCarte(Carte carteAJouer)
	{
		boolean verifiee = this.verifierConformiteCarte(carteAJouer);
		if(verifiee)
		{
			this.talon.add(carteAJouer);
			/**
			 * Notifie les vues (notamment la GUI) pour qu'elle mette � jour le talon de cartes
			 */
			this.notifier("Mettre � jour talon");
			return(true);
		}
		else
			return(false);
	}
	
	/**
	 * M�thode appel�e une fois que le joueur choisit de terminer son tour <br>
	 * Sa compl�tion de d�p�t est termin�e, cette m�thode demande � la variante courante d'appliquer les effets des cartes d�pos�es <br>
	 */
	public void terminerActionTour()
	{
		if(!this.cartesAJouer.isEmpty())
		{
			/**
			 * Si le joueur actif qui termine son tour est un bot, on efface la carte enregistr�e temporaire
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
			 * On r�active le retour du menu de choix de cartes au menu de choix d'actions in-game
			 * Les vues (notamment la GUI) se mettent � jour (ie. bouton RETOUR qui redevient cliquable)
			 */
			this.activerRetourPossible();
			this.notifier("Activer retour");
			/**
			 * On applique l'effet des cartes d�pos�es en fonction de la variante courante
			 */
			this.executerActionAppliquerEffetsCartes(this.cartesAJouer);			
		}
		/**
		 * Si le joueur termine son tour sans avoir jou� de carte (appui sur bouton (en mode GUI) ou saisie vide (en mode Console))
		 * Cela �quivaut � retourner dans le menu de choix d'actions in-game
		 */
		else
			this.notifier("Retour PC");
	}
	
	/**
	 * V�rifie le nombre de cartes en main du joueurs <br>
	 * S'il n'en poss�de plus qu'une, on lui propose d'annoncer carte
	 */
	public void verifierNombreCartesEnMain()
	{
		if(this.joueurActif.getMain().size() == 1)
			this.notifier("Proposer annoncer carte");
	}
	
	/**
	 * Ex�cute l'action "jouer" du c�t� joueur <br>
	 * Le joueur interroge la m�thode jouerCarte() de cette classe qui retourne un bool�en <br>
	 * Si vrai, la m�thode jouer() de la classe ex�cute son traitement (retrait de la carte de la main)
	 * @param carte Carte que le joueur souhaite jouer
	 * @return Bool�en � vrai si la carte a pu �tre jou�e (si conforme etc.), faux sinon
	 */
	public boolean executerActionJouer(Carte carte)
	{
		boolean aJoue = this.joueurActif.jouer(carte);
		return(aJoue);
	}	
	
	/**
	 * Demande � la variante courante d'appliquer les effets des cartes d�pos�es
	 * @param cartesAJouer D�p�t de cartes du joueur actif
	 */
	public void executerActionAppliquerEffetsCartes(LinkedList<Carte> cartesAJouer)
	{
		this.varianteCourante.appliquerEffetCarte(cartesAJouer, this.joueurActif);
	}
	
	/**
	 * Exc�ute l'action de changement de variante
	 * @param indexVarianteChoisie Index de la variante rempla�ante choisie
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
	 * M�lange un paquet de carte pass� en param�tre
	 * @param paquetsCartes Paquet de cartes qui doit �tre m�lang�
	 */
	public void melanger(LinkedList<Carte> paquetsCartes)
	{
		int indexAleatoire;
		Carte carteTmp;
		/**
		 * Resitue al�toirement chacune des cartes du paquet
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
	 * Premi�re m�thode de d�tection de fin de partie <br>
	 * V�rifie si un joueur de la partie ne poss�de plus de cartes (automatiquement le premier trouv�)
	 * @return Bool�en � vrai si un joueur ne poss�de plus de cartes, faux sinon
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
	 * Deuxi�me m�thode de d�tection de fin de partie <br>
	 * V�rifie le nombre de joueurs ayant termin� (n'ayant donc plus de carte en main) <br>
	 * Une fois chaque joueur analys�, la m�thode compare le nombre de joueurs total de la partie et le nombre de joueurs aant termin� <br>
	 * Si la diff�rence est de 1, c'est qu'il ne reste qu'une seul joueur ayant des cartes en main, la partie est donc termin�e
	 * @return Bool�en � vrai s'il ne reste plus qu'un seul joueur ayant des cartes en main, faux sinon
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
					 * Supprime le joueur ayant termin� de la liste des adversaires des autres joueurs
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
					 * Red�finit les joueurs pr�c�dent et suivant de chacun des joueurs toujours en liste
					 */
					Joueur joueurAyantFini = (Joueur)this.joueursDeLaPartie.get(x);
					Joueur predecesseurJAF = joueurAyantFini.getJoueurPrecedent();
					joueurAyantFini.getJoueurPrecedent().setJoueurSuivant(joueurAyantFini.getJoueurSuivant());
					joueurAyantFini.getJoueurSuivant().setJoueurPrecedent(predecesseurJAF);
					/**
					 * Ajoute enfin le joueur ayant termin� � la liste des joueurs ayant fini
					 */
					this.joueursAyantFini.add((Joueur)this.joueursDeLaPartie.get(x));
				}
			}
		}
		/**
		 * Compare le nombre de joueurs ayant fini et le nombre de joueurs total de la partie
		 * NB : La liste des joueurs de la partie ne change pas, on ne supprime pas les joueurs ayant termin� de cette liste !
		 */
		if(this.joueursAyantFini.size() == this.joueursDeLaPartie.size() - 1)
			return(true);
		return(false);
	}

	/**
	 * Demande � la variante courante de v�rifier la conformit� d'une carte
	 * @param carteAJouer Carte souhait�e �tre d�pos�e/jou�e
	 * @return Bool�en � vrai si la carte est d�finit comme conforme, faux sinon
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
	 * Permet d'obtenir la derni�re carte d'un paquet (ie. main du joueur, talon, pioche) 
	 * @param paquetCartes Paquet de cartes dont on souhaite connaitre la derni�re carte
	 * @return Derni�re carte du paquet sp�cifi�
	 */
	public Carte getDerniereCarte(LinkedList<Carte> paquetCartes)
	{
		return(paquetCartes.peekLast());
	}
	
	/**
	 * V�rifie si la pioche est vide et la regarnit si c'est le cas
	 */
	public void verifierPioche()
	{
		if(this.pioche.isEmpty())
		{
			//On r�cup�re la derni�re carte du talon (du dessus)
			Carte derniereCarteTalon = this.talon.pollLast();
			//On m�lange le reste du talon
			this.melanger(this.talon);
			//On convertit ce tas en pioche
			this.pioche.addAll(this.talon);
			//On vide le talon
			this.talon.removeAll(this.talon);
			//On ajoute la premi�re carte r�cup�r�e � celui-ci, qui forme le nouveau talon
			this.talon.add(derniereCarteTalon);
		}
	}	

	/**
	 * V�rifie s'il est toujours possible de piocher une carte <br>
	 * Si le talon ne comporte qu'une seule carte, et que la pioche est vide, alors il n'est plus possible de piocher 
	 * @return Bool�ean � vrai s'il est toujours possible de piocher, faux sinon
	 */
	public boolean peutEncorePiocher()
	{
		int nombreCartesPossedees = this.joueurActif.getMain().size();		
		Iterator<Joueur> iteAdversaires = this.joueurActif.getAdversaires().iterator();
		while(iteAdversaires.hasNext())
			nombreCartesPossedees += iteAdversaires.next().getMain().size();
		/**
		 * Ici, le nombre de cartes poss�d�es est une condition suppl�mentaire mais n�gligeable
		 * Car le nombre de cartes n'est pas forc�ment de 54, il peut �tre fix� selon la variante et �galement par le joueur
		 * La condition suffisante est que la pioche doit �tre vide et que le talon ne doit comporter qu'une seule carte
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
	 * Enregistre le symbole demand� � la suite d'un d�p�t de 8 (si l'effet produit est la demande de symbole) <br>
	 * Analyse l'index de choix en param�tre, et convertit cela en carte temporaire qui est enregistr�e au niveau de la variante courante
	 * @param choix Index de choix repr�sentant le symbole demand�
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
	 * Enregistre la couleur demand�e � la suite d'un d�p�t de 8 (si l'effet produit est la demande de couleur) <br>
	 * Analyse l'index de choix en param�tre, et convertit cela en carte temporaire qui est enregistr�e au niveau de la variante courante
	 * @param choix Index de choix repr�sentant la couleur demand�e
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
	 * Demande au joueur actif l'action � ex�cuter
	 * @return Index repr�sentant l'action choisie par le joueur {1,2,3} si joueur concret, {1,2} si joueur virtuel 
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String demanderActionTour() throws UncompliantChoiceException
	{
		String choix = this.joueurActif.choisirAction();
		if(!(!choix.trim().equals("") && (Partie.estUnEntier(choix)) && (Integer.parseInt(choix) <= 3 && Integer.parseInt(choix) >= 1)))
		{
			throw new UncompliantChoiceException("Veuillez choisir une des trois actions propos�es :");
		}
		return(choix);
	}	
	
	/**
	 * Demande au joueur actif la carte � jouer
	 * @return Index repr�sentant la carte choisie par le joueur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
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
	 * @return Index repr�sentant la variante choisie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e par le joueur virtuel (ie. action 3)
	 */
	public String demanderVariante() throws UncompliantChoiceException
	{
		String choix = (((JoueurConcret)this.joueurActif).choisirVariante());
		if(this.joueurActif instanceof JoueurConcret)
		{
			if(!choix.trim().equals(""))
			{
				if(!choix.equals("1") && !choix.equals("2") && !choix.equals("3") && !choix.equals("4") && !choix.equals("5"))
					throw new UncompliantChoiceException("Vous devez choisir une r�ponse r�ponse entre [1 et 5], ou une chaine vide.\nVeuillez resaisir votre r�ponse :");
			}
		}
		else
			throw new UncompliantChoiceException("Variante demand�e � un joueur virtuel");
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant � la couleur souhait�e � la suite d'un d�p�t de 8 <br>
	 * M�thode appel�e par le controleur console
	 * @return Index caract�risant la couleur choisie
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String demanderCouleurCarte() throws UncompliantChoiceException
	{
		String choix = this.choisirCouleurCarte(null);
		if(!choix.equals("1") && !choix.equals("2"))
		{
			throw new UncompliantChoiceException("Vous devez choisir une r�ponse entre [1 et 2]. Veuillez resaisir votre r�ponse :");
		}
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant au symbole souhait� � la suite d'un d�p�t de 8 <br>
	 * M�thode appel�e par le controleur console 
	 * @return Index caract�risant le symbole choisi
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String demanderSymboleCarte() throws UncompliantChoiceException
	{ 
		String choix = this.choisirSymboleCarte(null);
		if(!choix.equals("1") && !choix.equals("2") && !choix.equals("3") && !choix.equals("4"))
		{
			throw new UncompliantChoiceException("Vous devez choisir une r�ponse entre [1 et 4]. Veuillez resaisir votre r�ponse :");
		}
		return(choix);
	}
	
	/**
	 * Interroge le joueur quant � l'annonce de carte s'il ne lui en reste qu'une en main <br>
	 * M�thode appel�e par le controleur console
	 * @return R�ponse du joueur
	 * @throws UncompliantChoiceException Si une mauvaise saisie est r�alis�e
	 */
	public String demanderAnnoncerCarte() throws UncompliantChoiceException
	{
		String choix = this.choisirAnnoncerCarte(null);
		if(!(choix).equals("Y") && !(choix).equals("N"))
		{
			throw new UncompliantChoiceException("Vous devez choisir entre [Y] et [N]. Veuillez resaisir votre r�ponse :");
		}
		return(choix);
	}
	
	/**
	 * Si un index de couleur est sp�cifi� en param�tre, la m�thode enregistre directement la couleur demand�e <br>
	 * Sinon, elle demande au joueur de la choisir
	 * @param choixCouleur Index de la couleur choisie qui peut �tre null
	 * @return Index de la couleur choisie (utile pour le controleur console, si la saisie est erron�e)
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
	 * Si un index de symbole est sp�cifi� en param�tre, la m�thode enregistre directement le symbole demand� <br>
	 * Sinon, elle demande au joueur de le choisir
	 * @param choixSymbole Index du symbole choisi qui peut �tre null
	 * @return Index du symbole choisi (utile pour le controleur console, si la saisie est erron�e)
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
	 * Si une cl� de choix d'annonce est sp�cifi�e, la m�thode l'�value <br>
	 * Si "Y", alors le joueur annonce carte <br>
	 * Sinon (si "N" ou autre saisie), aucun traitement, on retourne la cl� pour le controleur console qui se charge de v�rifier la conformit�
	 * @param choixAnnonce Index du choix d'annoncer carte, saisi par le joueur (Y ou N)
	 * @return Cl� du choix d'annonce
	 */
	public String choisirAnnoncerCarte(String choixAnnonce)
	{	
		String choix = choixAnnonce;
		if(choix == null)
			choix = this.joueurActif.proposerAnnoncerCarte();	
		if(choix.equals("Y"))
		{
			this.joueurActif.annoncerCarte();
			this.notifier("Annonce carte effectu�e");
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
	 * Retourne le joueur gagnant (le premier ayant termin�)
	 * @return Le premier joueur ayant termin� la partie
	 */
	public Joueur getJoueurGagnant()
	{
		return this.joueurGagnant;
	}
	
	/**
	 * Modifie le joueur gagnant
	 * @param joueurGagnant Joueur ayant termin� la partie
	 */
	public void setJoueurGagnant(Joueur joueurGagnant)
	{
		this.joueurGagnant = joueurGagnant;
	}

	/**
	 * Retourne le joueur actif
	 * @return Joueur �tant en train de jouer
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
	 * Retourne l'�tat de la partie
	 * @return Bool�en � vrai si la partie est lanc�e, faux sinon
	 */
	public boolean getEstLancee()
	{
		return(this.estLancee);
	}
	
	/**
	 * Modifie l'�tat de la partie
	 * @param valeur Bool�en � vrai si la partie est lanc�e, faux sinon
	 */
	public void setEstLancee(boolean valeur)
	{
		this.estLancee = valeur;
	}
	
	/**
	 * Retourne l'�tat du retour s'il est possible ou non
	 * @return Bool�en � vrai si le retour au menu de choix d'actions in-game est possible, faux sinon
	 */
	public boolean getRetourPossible()
	{
		return(this.retourPossible);
	}
	
	/**
	 * D�sactive le retour possible au menu de choix d'actions in-game
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
	 * Notifie les vues observatrices du mod�le de Partie qu'un changement a �t� effectu� <br>
	 * A chaque fois qu'un changement est effectu�, concr�tement ou abstraitement, les vues sont notifi�es 
	 * @param arg Objet de notification (ie. message, instance etc.)
	 */
	
	public void notifier(Object arg)
	{
		this.setChanged();
		this.notifyObservers(arg);
	}	
	
	/**
	 * M�thode statique permettant de tester si une chaine de caract�res repr�sente un entier ou non
	 * @param valeur Chaine de caract�res � tester
	 * @return Bool�en � vrai si la chaine repr�sente un entier, faux sinon
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
