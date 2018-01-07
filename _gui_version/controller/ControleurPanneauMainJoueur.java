package _gui_version.controller;

import java.awt.event.*;

import _gui_version.model.Partie;
import _gui_version.view.*;

/**
 * Classe du contrôleur du panneau représentant la main du joueur
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurPanneauMainJoueur 
{
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les cartes de la main du joueur (sélection de carte)
	 * @param bouton Bouton (représentant une carte) avec lequel on interragit 
	 * @param partie Instance de la partie courante
	 */
	public ControleurPanneauMainJoueur(BoutonCarte bouton, Partie partie)
	{
		bouton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				partie.jouer(bouton.getCarte());
			}
		});
	}
}
