package _mvc_version.controller;

import java.awt.event.*;
import javax.swing.*;

import _mvc_version.model.*;
import _mvc_version.view.*;

/**
 * Classe du contrôleur du menu principal
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurMenuPrincipal
{
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les composants du menu principal
	 * @param bouton Bouton avec lequel on interragit
	 * @param jeu Instance du jeu
	 */
	public ControleurMenuPrincipal(JButton bouton, Jeu jeu)
	{
		bouton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(bouton.getText().equals("JOUER"))
				{
					jeu.jouer();			
				}
				else if(bouton.getText().equals("PARAMETRES"))
				{
					jeu.parametres();						
				}
				else
				{		
					jeu.quitter();				
				}
			}
		});
	}
}
