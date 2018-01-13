package _mvc_version.controller;

import java.awt.event.*;
import javax.swing.*;

import _mvc_version._exceptions.*;
import _mvc_version.model.*;
import _mvc_version.view.*;

/**
 * Classe du contrôleur du menu de paramètres
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurMenuParametres
{
	/**
	 * Vue associée au contrôleur
	 */
	private MenuParametres vue;
	
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les composants du menu de paramètres
	 * @param bouton Bouton avec lequel on interragit
	 * @param jeu Instance du jeu	
	 * @param vue Vue du menu de paramètres
	 */
	public ControleurMenuParametres(JButton bouton, Jeu jeu, MenuParametres vue)
	{		
		this.vue = vue;
		bouton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					verifierFormatValeurs(jeu);
					String nvNbj, nvNbp;
					boolean scoreActive;
					nvNbj = ((MenuParametres) vue).getChampChoixNbJoueurs().getText();
					nvNbp = ((MenuParametres) vue).getChampChoixNbPaquets().getText();
					scoreActive = ((MenuParametres) vue).getBoxScore().isSelected();
					jeu.changerParametres(nvNbj, nvNbp, scoreActive);
				}
				catch(SettingsException e1)
				{
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreur de configuration", JOptionPane.ERROR_MESSAGE);
				}
				catch(NumberFormatException e2)
				{
					JOptionPane.showMessageDialog(null, e2.getMessage(), "Erreur de format", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	/**
	 * Vérifie les format de donénes des entrées pour le nombre de joueurs et le nombre de jeux de cartes
	 * @param jeu Instance de jeu
	 * @throws UnsufficientNumberOfPlayersException Si le nombre de joueurs n'est pas conforme
	 * @throws UnsufficientNumberOfDecksException Si le nombre de paquets de cartes n'est pas conforme
	 * @throws NumberFormatException Si le format des données numériques n'est pas respecté
	 */
	public void verifierFormatValeurs(Jeu jeu) throws UnsufficientNumberOfPlayersException, UnsufficientNumberOfDecksException, NumberFormatException
	{
		String nbj = this.vue.getChampChoixNbJoueurs().getText();
		String nbp = this.vue.getChampChoixNbPaquets().getText();
		
		//Si rien est rempli, le modèle de jeu garde les options existantes
		if(nbj.trim().equals("") && nbp.trim().equals(""))
			return;
		//Si le nombre de joueur est rempli, on vérifie le format de la donnée entrée
		if(!nbj.trim().equals(""))
		{
			try
			{
				Integer.parseInt(nbj);
			}
			catch(NumberFormatException e)
			{
				throw new NumberFormatException("La valeur renseignée pour le nombre de joueurs n'est pas un nombre");
			}
		}
		else
			nbj = jeu.getOptionsDeJeu()[0];
		//Si le nombre de paquets de cartes est rempli, on vérifie le format de la donnée entrée
		if(!nbp.trim().equals(""))
		{
			try
			{
				Integer.parseInt(nbp);
			}
			catch(NumberFormatException e)
			{
				throw new NumberFormatException("La valeur renseignée pour le nombre de paquets n'est pas un nombre");
			}
		}
		else
			nbp = jeu.getOptionsDeJeu()[1];
		//On vérifie que le nombre de joueur est strictement supérieur à 1
		if(Integer.parseInt(nbj) < 2)
		{
			throw new UnsufficientNumberOfPlayersException("Le nombre de joueurs doit être strictement supérieur à 1");
		}
		//Enregistre le nombre de joueurs actuellement enregistré 
		int nbJ = Integer.parseInt(jeu.getOptionsDeJeu()[0]);
		//Enregistre le nombre de joueurs actuellement erengistré, dans une variable temporaire pour le test qui suit
		int nbJ_tmp = Integer.parseInt(jeu.getOptionsDeJeu()[0]);
		//Enregistre le nombre de jeux de cartes actuellement enregistré
		int nbP = Integer.parseInt(jeu.getOptionsDeJeu()[1]);
		//Si la donnée entrée pour le nombre d ejoueurs n'est pas vide, on remplace le nombre de joueurs temporaire
		if(!nbj.trim().equals(""))
			nbJ_tmp = Integer.parseInt(nbj);
		//Idem pour le nombre de paquets de cartes
		if(!nbp.trim().equals(""))
			nbP = Integer.parseInt(nbp);
		//On remplace le nombre de joueurs enregistrés par la valeur temporaire
		jeu.getOptionsDeJeu()[0] = nbJ_tmp + "";
		/**
		 * On teste la compatibilité
		 * En cas d'incomptabilité, on annule la modification
		 * Et on génère une exception
		 */
		try
		{
			jeu.verifierCompatibiliteNbCartesNbJoueurs(nbP);
		}
		catch(GameException e)
		{
			jeu.getOptionsDeJeu()[0] = nbJ + "";
			throw new UnsufficientNumberOfDecksException("Le nombre de paquets est insuffisant pour servir la totalité des joueurs");
		}
	}
}
