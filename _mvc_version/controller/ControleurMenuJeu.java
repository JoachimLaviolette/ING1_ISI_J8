package _mvc_version.controller;

import java.awt.event.*;
import javax.swing.*;

import _mvc_version._exceptions.UncompliantChoiceException;
import _mvc_version.model.*;
import _mvc_version.view.*;

/**
 * Classe du contrôleur du menu de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurMenuJeu 
{
	/**
	 * Vue associée au contrôleur
	 */
	private MenuJeu vue;
	
	/**
	 * Gère les événements déclenchés lorsque l'on interragit avec les composants du menu de jeu
	 * @param bouton Bouton avec lequel on interragit
	 * @param jeu Instance du jeu
	 * @param vue Vue du menu de jeu
	 */
	public ControleurMenuJeu(JButton bouton, Jeu jeu, MenuJeu vue)
	{
		this.vue = vue;
		bouton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(bouton.getText().equals("SUIVANT"))
				{
					try
					{
						verifierFormatNombreJoueurs();
						/*
						 * Formate les données de partie et les transmet au modèle 
						 */
						String varianteChoisie = determinerVariante((String)vue.getChampChoixVariante().getSelectedItem());
						int nbJoueurs = Integer.parseInt(vue.getChampChoixNbJoueurs().getText());
						String pseudoJoueur = vue.getChampChoixPseudo().getText();
						String choixStrategie = determinerStrategie((String)vue.getChampChoixDifficulte().getSelectedItem());
						jeu.preparerPartie(varianteChoisie, nbJoueurs, pseudoJoueur, choixStrategie);
					}
					catch(Throwable e1)
					{
						if(e1 instanceof NumberFormatException || e1 instanceof UncompliantChoiceException)
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreur lors du paramétrage de la partie", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(bouton.getText().equals("RETOUR"))
					jeu.demarrerJeu();
			}
		});
	}
	
	/**
	 * Vérifie le bon format de la donnée entrée pour le nombre de joueurs
	 * @throws NumberFormatException En cas de saisie d'une donnée non-numérique pour le nombre de joueurs 
	 * @throws UncompliantChoiceException En cas de saisie erronée 
	 */
	public void verifierFormatNombreJoueurs() throws UncompliantChoiceException, NumberFormatException
	{
		try
		{
			Integer.parseInt(this.vue.getChampChoixNbJoueurs().getText());
		}
		catch(NumberFormatException e)
		{
			throw new NumberFormatException();
		}
		if(!(Integer.parseInt(this.vue.getChampChoixNbJoueurs().getText()) >= 2 
				&& !this.vue.getChampChoixPseudo().getText().trim().equals("")))
			throw new UncompliantChoiceException("Un des champs n'est pas au bon format");
	}
	
	/**
	 * Convertit un titre de variante en index la représentant
	 * @param titreVariante Titre de la variante à convertir
	 * @return Index représentant la variante convertie
	 */
	public String determinerVariante(String titreVariante)
	{
		switch(titreVariante)
		{
			case "Minimale" : return "1";
			case "Monclar" : return "2";
			case "4" : return "3";
			case "5" : return "4";
			case "Personnalisée" : return "5";
			default : return "1";
		}
	}
	
	/**
	 * Convertit un titre de stratégie en index la représenant
	 * @param titreStrategie Titre de la stratégie à convertir
	 * @return Index représentant la stratégie convertie
	 */
	public String determinerStrategie(String titreStrategie)
	{
		switch(titreStrategie)
		{
			case "Débutant" : return "1";
			case "Amateur" : return "2";
			case "Confirmé" : return "3";
			default : return "1";
		}
	}
}
