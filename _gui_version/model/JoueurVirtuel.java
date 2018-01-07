package _gui_version.model;

import java.util.*;

/**
 * Classe d�finissant un joueur virtuel (bot)
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class JoueurVirtuel extends Joueur
{
	private StrategieDeJeu strategieCourante;

	/**
	 * Cr�e une instance de joueur virtuel (bot)
	 * @param partie Instance de la partie courante
	 * @param pseudoEnregistre Pseudo du bot
	 * @param joueurSuivant Joueur suivant le bot
	 * @param joueurPrecedent Joueur pr�c�dant le bot
	 * @param adversaires Liste des adversaires du bot
	 */
	public JoueurVirtuel(Partie partie, String pseudoEnregistre, Joueur joueurSuivant, Joueur joueurPrecedent, ArrayList<Joueur> adversaires) 
	{
		super(partie, pseudoEnregistre, joueurSuivant, joueurPrecedent, adversaires);
		this.strategieCourante = new IADebutant(this);
	}

	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * La m�thode de choix d'action diff�rant selon l'IA impl�ment�e </br>
	 * Retourne un index repr�sentant l'action (g�n�rique) � ex�cuter
	 * @return Index repr�sentant l'action � ex�cuter
	 */
	public String choisirAction() 
	{
		String action = this.strategieCourante.choisirAction();
		return(action);
	}

	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * La m�thode de carte d'action diff�rant selon l'IA impl�ment�e </br>
	 * Retour l'index de la carte � jouer
	 * @return Index de la carte � jouer 
	 */
	public String choisirCarte() 
	{
		String carteChoisie = this.strategieCourante.choisirCarte();
		return(carteChoisie);
	}

	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * La m�thode de choix de symbole diff�rant selon l'IA impl�ment�e </br>
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur sous forme d'index {1, 2, 3, 4}
	 */
	public String choisirSymboleCarteApresHuit() 
	{
		String symboleCarteDemandee = this.strategieCourante.choisirSymboleCarteApresHuit();
		return(symboleCarteDemandee);
	}
	
	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * La m�thode de choix de couleur diff�rant selon l'IA impl�ment�e </br>
	 * Retourne la r�ponse du joueur, sous forme d'index
	 * @return R�ponse du joueur, sous forme d'index {1, 2}
	 */
	public String choisirCouleurCarteApresHuit() 
	{
		String couleurCarteDemandee = this.strategieCourante.choisirCouleurCarteApresHuit();
		return(couleurCarteDemandee);
	}

	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * Retourne l'index d'une carte � jouer en suppl�ment (pour compl�ter le d�p�t)
	 * @return Index de la carte � jouer en suppl�ment
	 */
	public String choisirCarteSupplement() 
	{
		String indexCarte = this.strategieCourante.choisirCarteSupplement();
		return(indexCarte);
	}
	
	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * Retourne la r�ponse du joueur, sous forme d'une cl�
	 * @return R�ponse du joueur {Y, N}
	 */
	public String proposerAjouterCarte()
	{
		String decision = this.strategieCourante.proposerAjouterCarte();
		return(decision);
	}
	
	/**
	 * Interroge l'algo de l'IA r�elle qu'impl�mente le joueur virtuel </br>
	 * Retourne la r�ponse du joueur, sous forme d'une cl� 
	 * @return R�ponse du joueur {Y, N}
	 */
	public String proposerAnnoncerCarte() 
	{
		String decision = this.strategieCourante.proposerAnnoncerCarte();
		return(decision);
	}	

	/**
	 * Retourne la strat�gie de jeu du bot
	 * @return Strat�gie de jeu du bot
	 */
	public StrategieDeJeu getStrategieCourante() 
	{
		return(this.strategieCourante);
	}

	/**
	 * Modifie la strat�gie de jeu courante du bot
	 * @param strategieCourante Strat�gie de jeu rempla�ante
	 */
	public void setStrategieCourante(StrategieDeJeu strategieCourante) 
	{
		this.strategieCourante = strategieCourante;
	}
}