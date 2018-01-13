package _mvc_version.controller;

import java.awt.event.*;
import javax.swing.*;

import _mvc_version._exceptions.UncompliantChoiceException;
import _mvc_version.model.*;
import _mvc_version.view.*;

/**
 * Classe du contr�leur du menu de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ControleurMenuJeu 
{
	/**
	 * Vue associ�e au contr�leur
	 */
	private MenuJeu vue;
	
	/**
	 * G�re les �v�nements d�clench�s lorsque l'on interragit avec les composants du menu de jeu
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
						 * Formate les donn�es de partie et les transmet au mod�le 
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
							JOptionPane.showMessageDialog(null, e1.getMessage(), "Erreur lors du param�trage de la partie", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(bouton.getText().equals("RETOUR"))
					jeu.demarrerJeu();
			}
		});
	}
	
	/**
	 * V�rifie le bon format de la donn�e entr�e pour le nombre de joueurs
	 * @throws NumberFormatException En cas de saisie d'une donn�e non-num�rique pour le nombre de joueurs 
	 * @throws UncompliantChoiceException En cas de saisie erron�e 
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
	 * Convertit un titre de variante en index la repr�sentant
	 * @param titreVariante Titre de la variante � convertir
	 * @return Index repr�sentant la variante convertie
	 */
	public String determinerVariante(String titreVariante)
	{
		switch(titreVariante)
		{
			case "Minimale" : return "1";
			case "Monclar" : return "2";
			case "4" : return "3";
			case "5" : return "4";
			case "Personnalis�e" : return "5";
			default : return "1";
		}
	}
	
	/**
	 * Convertit un titre de strat�gie en index la repr�senant
	 * @param titreStrategie Titre de la strat�gie � convertir
	 * @return Index repr�sentant la strat�gie convertie
	 */
	public String determinerStrategie(String titreStrategie)
	{
		switch(titreStrategie)
		{
			case "D�butant" : return "1";
			case "Amateur" : return "2";
			case "Confirm�" : return "3";
			default : return "1";
		}
	}
}
