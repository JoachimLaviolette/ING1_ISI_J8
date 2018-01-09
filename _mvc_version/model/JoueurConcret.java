package _mvc_version.model;

import java.io.*;
import java.util.*;

/**
 * Classe d�finissant un joueur concret (singleton)
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class JoueurConcret extends Joueur
{
	private static JoueurConcret instance;
	
	/**
	 * Cr�e une instance de joueur concret
	 * @param partie Instance de la partie courante
	 * @param pseudoEnregistre Pseudo du bot
	 * @param joueurSuivant Joueur suivant le bot
	 * @param joueurPrecedent Joueur pr�c�dant le bot
	 * @param adversaires Liste des adversaires du bot
	 */
	private JoueurConcret(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
	}
	
	/**
	 * V�rifie si une instance de joueur concret existe d�j� et en cr�e si ce n'est pas le cas
	 * @param partie
	 * @param pseudoEnregistre
	 * @param joueurSuivant
	 * @param joueurPrecedent
	 * @param adversaires
	 * @return Instance de joueur concret
	 */
	public static Joueur creerJoueurConcret(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		if(instance == null)
			instance = new JoueurConcret(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		return(instance);
	}
	
	/**
	 * Retourne le choix de variante effectu� par le joueur
	 * @return Index repr�sentant le choix de variante
	 */
	public String choisirVariante()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);	
	}	
	
	/**
	 * Retourne un index repr�sentant l'action (g�n�rique) � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 */
	public String choisirAction()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);	
	}
	
	/**
	 * Retour l'index de la carte � jouer
	 * @return Index de la carte � jouer 
	 */
	public String choisirCarte()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);
	}
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public String choisirSymboleCarteApresHuit()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);
	}
	
	/**
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur, sous forme d'index {1, 2}
	 */
	public String choisirCouleurCarteApresHuit()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);
	}
		
	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl�
	 * @return R�ponse du joueur {Y, N}
	 */
	public String proposerAjouterCarte()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);
	}

	/**
	 * Retourne la r�ponse du joueur, sous forme d'une cl� 
	 * @return R�ponse du joueur {Y, N}
	 */
	public String proposerAnnoncerCarte()
	{
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		String reponse = new String("");
		try 
		{
			reponse = scanner.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return(reponse);	
	}	
	
}
