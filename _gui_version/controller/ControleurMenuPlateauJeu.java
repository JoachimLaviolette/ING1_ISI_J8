package _gui_version.controller;

import java.awt.event.*;
import javax.swing.*;

import _gui_version.model.*;

/**
 * Classe du contrôleur du plateau de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurMenuPlateauJeu 
{
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les boites de dialogues du plateau de jeu
	 * @param partie Instance de la partie courante
	 * @param choixOption Choix sélectionné par l'utilisateur
	 * @param typeOption Type de l'option de la boite de dialogue (ie. annoncer carte, demander couleur, demander symbole)
	 */
	public ControleurMenuPlateauJeu(Partie partie, String choixOption, String typeOption)
	{
		switch(typeOption)
		{
			case "Couleur" :
				partie.choisirCouleurCarte(choixOption);
				break;
			case "Symbole" :
				partie.choisirSymboleCarte(choixOption);
				break;
			case "Annonce carte" :
				String res = null;
				if(choixOption != null)
				{
					if(choixOption.equals("1"))
						res = "Y";
					else
						res = "N";
				}
				partie.choisirAnnoncerCarte(res);
				break;
		}
	}
	
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les composants du plateau de jeu
	 * @param bouton Bouton avec lequel on interragit
	 * @param partie Instance de la partie courante
	 * @param panneau Composant (JPanel) d'où provient le bouton (dans le cas d'un retour) 
	 */
	public ControleurMenuPlateauJeu(JButton bouton, Partie partie, String panneau)
	{
		bouton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				switch(bouton.getText())
				{
					//actions
					case "JOUER" :
						partie.jouer();
						break;
					case "PIOCHER" :
						partie.piocher();
						break;
					case "CHANGER VARIANTE" :
						partie.changerVariante();
						break;
					case "RETOUR" :
						if(panneau.equals("PC"))
							partie.retourDepuisPC();
						else
							partie.retourDepuisPV();
						break;
					case "TERMINER" :
						partie.terminerActionTour();
						break;
					//variants
					case "VARIANTE MINIMALE" :
						partie.changerVariante("1");
						break;
					case "VARIANTE MONCLAR" :
						partie.changerVariante("2");
						break;
					case "VARIANTE 4" :
						partie.changerVariante("3");
						break;
					case "VARIANTE 5" :
						partie.changerVariante("4");
						break;
					case "VARIANTE PERSO" :
						partie.changerVariante("5");
						break;
				}
			}
		});
	}
}
