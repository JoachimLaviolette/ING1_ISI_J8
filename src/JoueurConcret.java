import java.util.ArrayList;
import java.util.Scanner;

public class JoueurConcret extends Joueur
{
	private static JoueurConcret instance;
	
	private JoueurConcret(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
	}
	
	public static Joueur creerJoueurConcret(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		if(instance == null)
			instance = new JoueurConcret(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		return(instance);
	}
	
	public void changerVariante(Variante nouvelleVariante)
	{
		this.partieEnCours.setVarianteCourante(nouvelleVariante);
	}
	
	public int choisirVariante()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		int reponse;
		texte.append("Veuillez choisir une des variantes proposées :\n");
		texte.append("1) Variante Minimale\n");
		texte.append("2) Variante Monclar\n");
		texte.append("3) Variante 4\n");
		texte.append("4) Variante 5\n");
		texte.append("5) Variante Personnalisée\n");
		System.out.println(texte.toString());
		reponse = scanner.nextInt();
		while((reponse != 1 && reponse != 2 && reponse != 3 && reponse != 4 && reponse != 5) || (!Partie.estUnEntier(reponse + "")))
		{
			texte.append("Vous devez choisir une des propositions (un nombre compris entre [1 et 5]). Veuillez resaisir une variante :");
			System.out.println(texte.toString());
			reponse = scanner.nextInt();
		}
		return(reponse);	
	}	
	
	//overridden methods	
	public int choisirAction()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse;
		texte.append(this.pseudo + ", veuillez choisir une action à exécuter :\n");
		texte.append("1) Jouer\n");
		texte.append("2) Piocher\n");
		texte.append("3) Changer de variante\n\n");		
		texte.append(this.partieEnCours.getInfosMainJoueur());
		texte.append(this.partieEnCours.getInfosPaquets());
		for(int x = 0 ; x < this.adversaires.size() ; x++)
		{
			Joueur adversaireX = this.adversaires.get(x);
			texte.append("             [" + adversaireX.getPseudo() + " : " + adversaireX.getMain().size() + " cartes]");
			if(this.joueurSuivant.equals(adversaireX))
				texte.append(" [joueur suivant]");
			texte.append("\n");
		}
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(!reponse.trim().equals("") && (Partie.estUnEntier(reponse)) && (Integer.parseInt(reponse) <= 3 && Integer.parseInt(reponse) >= 1)))
		{
			System.out.println("Veuillez choisir une des trois actions proposées :");
			reponse = scanner.nextLine();
		}
		return(Integer.parseInt(reponse));	
	}
	
	public Carte choisirCarte()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("Veuillez choisir une carte à jouer :\n");
		for(int x = 0 ; x < this.main.size(); x++)
			texte.append((x+1) + ") " + ((Carte)this.main.toArray()[x]).toString() + "\n");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(!reponse.trim().equals("") && (Partie.estUnEntier(reponse)) && (Integer.parseInt(reponse) <= this.main.size() && Integer.parseInt(reponse) >= 1)))
		{
			System.out.println("Veuillez choisir une carte de la liste :");
			reponse = scanner.nextLine();
		}
		return((Carte)(this.main.toArray()[Integer.parseInt(reponse) - 1]));
	}
	
	public Carte choisirSymboleCarteApresHuit()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		int reponse;
		Carte carte = null;
		texte.append("\nVeuillez choisir le symbole que vous souhaitez que l'on joue par la suite :\n");
		texte.append("1) Carreau\n");
		texte.append("2) Coeur\n");
		texte.append("3) Pique\n");
		texte.append("4) Trèfle\n");
		System.out.println(texte.toString());
		reponse = scanner.nextInt();
		while(reponse > 4 && reponse < 1)
		{
			System.out.println("Vous devez choisir une réponse entre [1 et 4]. Veuillez resaisir votre réponse :");
			reponse = scanner.nextInt();
		}		
		switch(reponse)
		{
			case 1 : carte = new Carte(null, Symbole.CARREAU, null); break;
			case 2 : carte = new Carte(null, Symbole.COEUR, null); break;		
			case 3 : carte = new Carte(null, Symbole.PIQUE, null); break;
			case 4 : carte = new Carte(null, Symbole.TREFLE, null); break;
		}
		return(carte);
	}
	
	public Carte choisirCouleurCarteApresHuit()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		int reponse;
		Carte carte = null;
		texte.append("\nVeuillez choisir la couleur que vous souhaitez que l'on joue par la suite :\n");
		texte.append("1) Rouge\n");
		texte.append("2) Noire\n");
		System.out.println(texte.toString());
		reponse = scanner.nextInt();
		while(reponse > 2 && reponse < 1)
		{
			System.out.println("Vous devez choisir une réponse entre [1 et 2]. Veuillez resaisir votre réponse :");
			reponse = scanner.nextInt();
		}		
		switch(reponse)
		{
			case 1 : carte = new Carte(null, null, Couleur.ROUGE); break;
			case 2 : carte = new Carte(null, null, Couleur.NOIRE); break;	
		}
		return(carte);
	}
	
	public String choisirCarteSupplement()
	{
		Scanner scanner = new Scanner(System.in);
		StringBuffer texte = new StringBuffer();
		String reponse = new String();
		texte.append("Veuillez choisir une carte en supplément à jouer :\n");
		for(int x = 0 ; x < this.main.size(); x++)
			texte.append((x+1) + ") " + ((Carte)this.main.toArray()[x]).toString() + "\n");
		System.out.println(texte.toString());
		reponse = scanner.nextLine();
		while(!(Partie.estUnEntier(reponse) && (Integer.parseInt(reponse) <= this.main.size() && Integer.parseInt(reponse) >= 1)) && !(reponse.trim().equals("")))
		{
			System.out.println("Veuillez choisir une carte de la liste :\n[Appuyez sur la touche entrée pour continuer sans ajouter de carte]");
			reponse = scanner.nextLine();
		}		
		if(reponse.trim().equals(""))
			return(reponse);
		else
			return(Integer.parseInt(reponse) - 1 + "");
	}
	
	public String proposerAjouterCarte()
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
}
