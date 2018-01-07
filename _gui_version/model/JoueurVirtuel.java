package _gui_version.model;

import java.util.*;

/**
 * Classe définissant un joueur virtuel (bot)
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class JoueurVirtuel extends Joueur
{
	private StrategieDeJeu strategieCourante;

	/**
	 * Crée une instance de joueur virtuel (bot)
	 * @param partie Instance de la partie courante
	 * @param pseudoEnregistre Pseudo du bot
	 * @param joueurSuivant Joueur suivant le bot
	 * @param joueurPrecedent Joueur précédant le bot
	 * @param adversaires Liste des adversaires du bot
	 */
	public JoueurVirtuel(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		this.strategieCourante = new IADebutant(this);
	}

	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * La méthode de choix d'action diffèrant selon l'IA implémentée </br>
	 * Retourne un index représentant l'action (générique) à exécuter
	 * @return Index représentant l'action à exécuter
	 */
	public String choisirAction() 
	{
		String action = this.strategieCourante.choisirAction();
		return(action);
	}

	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * La méthode de carte d'action diffèrant selon l'IA implémentée </br>
	 * Retour l'index de la carte à jouer
	 * @return Index de la carte à jouer 
	 */
	public String choisirCarte() 
	{
		String carteChoisie = this.strategieCourante.choisirCarte();
		return(carteChoisie);
	}

	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * La méthode de choix de symbole diffèrant selon l'IA implémentée </br>
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public String choisirSymboleCarteApresHuit() 
	{
		String symboleCarteDemandee = this.strategieCourante.choisirSymboleCarteApresHuit();
		return(symboleCarteDemandee);
	}
	
	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * La méthode de choix de couleur diffèrant selon l'IA implémentée </br>
	 * Retourne la réponse du joueur, sous forme d'index
	 * @return Réponse du joueur, sous forme d'index {1, 2}
	 */
	public String choisirCouleurCarteApresHuit() 
	{
		String couleurCarteDemandee = this.strategieCourante.choisirCouleurCarteApresHuit();
		return(couleurCarteDemandee);
	}

	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * Retourne l'index d'une carte à jouer en supplément (pour compléter le dépôt)
	 * @return Index de la carte à jouer en supplément
	 */
	public String choisirCarteSupplement() 
	{
		String indexCarte = this.strategieCourante.choisirCarteSupplement();
		return(indexCarte);
	}
	
	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * Retourne la réponse du joueur, sous forme d'une clé
	 * @return Réponse du joueur {Y, N}
	 */
	public String proposerAjouterCarte()
	{
		String decision = this.strategieCourante.proposerAjouterCarte();
		return(decision);
	}
	
	/**
	 * Interroge l'algo de l'IA réelle qu'implémente le joueur virtuel </br>
	 * Retourne la réponse du joueur, sous forme d'une clé 
	 * @return Réponse du joueur {Y, N}
	 */
	public String proposerAnnoncerCarte() 
	{
		String decision = this.strategieCourante.proposerAnnoncerCarte();
		return(decision);
	}	

	/**
	 * Retourne la stratégie de jeu du bot
	 * @return Stratégie de jeu du bot
	 */
	public StrategieDeJeu getStrategieCourante() 
	{
		return(this.strategieCourante);
	}

	/**
	 * Modifie la stratégie de jeu courante du bot
	 * @param strategieCourante Stratégie de jeu remplaçante
	 */
	public void setStrategieCourante(StrategieDeJeu strategieCourante) 
	{
		this.strategieCourante = strategieCourante;
	}
}