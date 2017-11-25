import java.util.*;

public class Partie 
{
	private static Partie instance;
	private Jeu instanceDeJeu;
	private Variante varianteCourante;
	private Joueur joueurActif;
	private Joueur joueurGagnant;
	private HashSet<Joueur> joueursAyantFini;
	private HashSet<Joueur> joueursDeLaPartie;
	private LinkedList<Carte> talon;
	private LinkedList<Carte> pioche;
	
	private Partie(Jeu instanceDeJeu, Variante varianteChoisie, HashSet<Joueur> joueursEnregistres)
	{
		this.instanceDeJeu = instanceDeJeu;
		this.varianteCourante = varianteChoisie;
		this.joueursDeLaPartie = joueursEnregistres;
		this.joueurActif = this.trouverJoueurConcret();
		this.joueurGagnant = null;
		this.joueursAyantFini = new HashSet<Joueur>();
		this.talon = new LinkedList<Carte>();
		this.pioche = new LinkedList<Carte>();
		this.initialiserPioche(this.instanceDeJeu.getOptionsDeJeu());
		this.melanger(this.pioche);
		this.initialiserMains();
		this.initialiserTalon();
	}
	
	public static Partie creerPartie(Jeu instanceDeJeu, Variante varianteChoisie, HashSet<Joueur> joueursEnregistres)
	{
		if(instance == null)
			instance = new Partie(instanceDeJeu, varianteChoisie, joueursEnregistres);
		return(instance);
	}
	
	public void lancerPartie()
	{
		if(instance == null)
			System.out.println("Aucune partie n'a été créée.");
		else
		{
			//TODO create a function which check that everything is ready and conform to launch the game
			// ....
			
			while(this.verifierNombreCartesJoueurs())
				this.jouerTour();
			this.annoncerFinDePartie();
		}			
	}
	
	private boolean verifierNombreCartesJoueurs()
	{	
		for(int x = 0 ; x < this.joueursDeLaPartie.size() ; x++)
		{
			if(((Joueur)this.joueursDeLaPartie.toArray()[x]).getMain().size() == 0)
			{
				if(this.joueursAyantFini.size() == 0)
					this.joueurGagnant = (Joueur)this.joueursDeLaPartie.toArray()[x];
				if(!this.joueursAyantFini.contains((Joueur)this.joueursDeLaPartie.toArray()[x]))
				{
					this.joueursAyantFini.add((Joueur)this.joueursDeLaPartie.toArray()[x]);
					//remove the player from the opponents list of other players
					Joueur joueurAEnlever = (Joueur)this.joueursDeLaPartie.toArray()[x];
					Iterator<Joueur> iteAdversaireX = ((Joueur)this.joueursDeLaPartie.toArray()[x]).getAdversaires().iterator();
					Joueur adversaireX;
					while(iteAdversaireX.hasNext())
					{
						adversaireX = iteAdversaireX.next();
						adversaireX.getAdversaires().remove(joueurAEnlever);
					}
					//reset de next and previous players for all others players
					//((Joueur)this.joueursDeLaPartie.toArray()[x]).set
				}
			}
		}
		if(this.joueursAyantFini.size() == this.joueursDeLaPartie.size() - 1)
			return(false);
		return(true);
	}
	
	public void jouerTour()
	{
		int action = this.joueurActif.choisirAction();
		switch(action)
		{
			case 1 : 
				this.afficherCarteTalon();
				LinkedList<Carte> cartesAJouer = new LinkedList<Carte>();
				String reponse = "Y", reponseX = "x";
				int indexCarte;
				Carte carteChoisie;
				boolean aJoue;
				carteChoisie = this.joueurActif.choisirCarte();
				cartesAJouer.add(carteChoisie);
				aJoue = this.joueurActif.jouer(carteChoisie);
				if(aJoue)
					reponse = this.proposerChoixPlusieursCartes();
				else
					reponse = "N";
				while(reponse.trim().equals("Y"))
				{
					this.afficherCarteTalon();
					reponseX = this.joueurActif.choisirCarteSupplement();
					if(!reponseX.trim().equals(""))
					{
						while(!this.estUnEntier(reponseX))
						{
							System.out.println("Vous devez entrer un nombre pour indiquer quelle carte choisir ! Allez-y :\n");
							reponseX = this.joueurActif.choisirCarteSupplement();
						}
						indexCarte = Integer.parseInt(reponseX);
						carteChoisie = (Carte)this.joueurActif.getMain().toArray()[indexCarte];	
						if(!this.combinaisonAutorisee(this.getDerniereCarte(cartesAJouer).getValeur(), (carteChoisie.getValeur())))
							while(!this.getDerniereCarte(cartesAJouer).getValeur().equals(carteChoisie.getValeur()))
							{
								System.out.println("La carte indiquée ne peut pas être combinée avec la première renseignée (deux valeurs différentes !). Veuillez en choisir une autre :\n");
								reponseX = this.joueurActif.choisirCarteSupplement();
								if(!reponseX.trim().equals(""))
								{
									indexCarte = Integer.parseInt(reponseX);
									carteChoisie = (Carte)this.joueurActif.getMain().toArray()[indexCarte];
								}
								else
									break;
							}							
					}
					if(!reponseX.trim().equals(""))
					{
						cartesAJouer.add(carteChoisie);
						reponse = this.proposerChoixPlusieursCartes();
					}
					else
						reponse = "N";
				}
				if(aJoue)
				{
					if(this.joueurActif.getMain().size() == 1)
						this.proposerAnnoncerCarte();
					this.varianteCourante.appliquerEffetCarte(cartesAJouer, this.joueurActif);
				}
				else
					this.joueurActif.terminerTour();
				break;
			case 2 : 
				boolean aPioche = this.joueurActif.piocher(); 
				if(aPioche)
					this.joueurActif.terminerTour();
				break;
			case 3 : 
				if(this.joueurActif instanceof JoueurConcret)
				{
					int choix = ((JoueurConcret)this.joueurActif).choisirVariante(); 
					Variante varianteChoisie = null;
					switch(choix)
					{
						case 1 : 
							varianteChoisie = new VarianteMinimale(instance);
							break;
						case 2 : 
							varianteChoisie = new VarianteMonclar(instance);
							break;
						case 3 : 
							varianteChoisie = new Variante4(instance);
							break;
						case 4 : 
							varianteChoisie = new Variante5(instance);
							break;
					}
					((JoueurConcret)this.joueurActif).changerVariante(varianteChoisie);
					//see what to do here, if we have to re-launch the game or not
				}
					break;
		}
	}
	
	public String proposerChoixPlusieursCartes()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nSouhaitez-vous jouer une autre carte en supplément ? [Y/N]\n");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(reponse).equals("Y") && !(reponse).equals("N"))
		{
			System.out.println("Vous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :\n");
			reponse = new String(scanner.nextLine());
		}
		return(reponse);
	}	
	
	private void afficherCarteTalon()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n>> Carte du talon --> " + this.getDerniereCarte(this.talon).toString() + " <<\n");
		if(this.varianteCourante.getCarteDemandee() != null)
			texte.append("Le précédent joueur a joué un 8 et a demandé de jouer du " + this.varianteCourante.getCarteDemandee().getSymbole() +"\n");
		System.out.println(texte.toString());
	}
	
	private boolean verifierConformiteCarte(Carte carteAJouer)
	{
		return(this.varianteCourante.verifierConformiteCarte(carteAJouer));
	}
	
	public boolean jouerCarte(Carte carteAJouer)
	{
		boolean verifiee = this.verifierConformiteCarte(carteAJouer);
		if(verifiee)
		{
			this.talon.add(carteAJouer);
			return(true);
		}
		else
			return(false);
	}

	public void initialiserPioche(String[] optionsDeJeu)
	{
		int nombreJeuxDeCartes = Integer.parseInt(optionsDeJeu[1]);
		this.varianteCourante.initialiserPioche(this.pioche, nombreJeuxDeCartes);
	}
	
	public void melanger(LinkedList<Carte> paquet)
	{
		int indexAleatoire;
		Carte carteTmp;
		for(int indexCarte = 0 ; indexCarte < paquet.size() ; indexCarte++)
		{
			indexAleatoire = (int)Math.floor(Math.random() * (paquet.size() - 0));
			carteTmp = (Carte)paquet.toArray()[indexCarte];			
			paquet.set(indexCarte, (Carte)paquet.toArray()[indexAleatoire]);
			paquet.set(indexAleatoire, carteTmp);			
		}
	}
	
	public void initialiserMains()
	{
		for(int distribution = 0 ; distribution < 8 ; distribution++)
		{
			Iterator<Joueur> iteJoueurX = this.joueursDeLaPartie.iterator();
			while(iteJoueurX.hasNext())
				((Joueur)iteJoueurX.next()).getMain().add(this.pioche.pollLast());
		}
	}
	
	public void initialiserTalon()
	{
		this.talon.add(this.pioche.pollLast());
	}
	
	public void proposerAnnoncerCarte()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("\nIl ne vous reste plus qu'une carte ! Souhaitez-vous annoncer carte ? [Y/N]\n");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(reponse).equals("Y") && !(reponse).equals("N"))
		{
			System.out.println("Vous devez choisir entre [Y] et [N]. Veuillez resaisir votre réponse :");
			reponse = new String(scanner.nextLine());
		}
		if(reponse.equals("Y"))
			this.joueurActif.annoncerCarte();
	}
	
	public Carte getDerniereCarte(LinkedList<Carte> paquet)
	{
		return(paquet.peekLast());
	}
	
	public void verifierPioche()
	{
		if(this.pioche.isEmpty())
		{
			Carte premiereCarteTalon = this.talon.pollLast();
			this.melanger(this.talon);
			this.pioche.addAll(this.talon);
			this.talon.removeAll(this.talon);
			this.talon.add(premiereCarteTalon);
		}
	}
	
	public boolean combinaisonAutorisee(Valeur valeurCartePrecedente, Valeur valeurNouvelleCarte)
	{
		if(valeurCartePrecedente.equals(Valeur.AS) || valeurCartePrecedente.equals(Valeur.JOKER) || valeurCartePrecedente.equals(Valeur.HUIT))
			if(valeurNouvelleCarte.equals(Valeur.AS) || valeurNouvelleCarte.equals(Valeur.JOKER) || valeurNouvelleCarte.equals(Valeur.HUIT))
				return(true);
		return(false);
	}
	
	public void annoncerFinDePartie()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n------------- VICTOIRE -------------\n");
		texte.append("La partie est terminée ! Le gagnant est... " + this.joueurGagnant.getPseudo() + "! Félicitations à toi !\n)");
		texte.append(">>               [Appuyez sur entrée pour revenir au menu principal] <<");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		this.instanceDeJeu.demarrer();
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

	public Carte demanderCarteJoueur()
	{
		return(this.joueurActif.choisirCarteApresHuit());
	}
	
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
	
	//getters and setters	
	public Variante getVarianteCourante() 
	{
		return this.varianteCourante;
	}

	public void setVarianteCourante(Variante varianteCourante) 
	{
		this.varianteCourante = varianteCourante;
	}

	public Joueur getJoueurActif() 
	{
		return this.joueurActif;
	}

	public void setJoueurActif(Joueur joueurActif) 
	{
		this.joueurActif = joueurActif;
	}

	public HashSet<Joueur> getJoueursDeLaPartie() 
	{
		return this.joueursDeLaPartie;
	}
	
	public void setJoueursDeLaPartie(HashSet<Joueur> joueursDeLaPartie) 
	{
		this.joueursDeLaPartie = joueursDeLaPartie;
	}

	public LinkedList<Carte> getTalon() 
	{
		return this.talon;
	}

	public LinkedList<Carte> getPioche() 
	{
		return this.pioche;
	}
}
