package _gui_version.view;

import javax.swing.*;

import _gui_version.model.*;

/**
 * Classe repr�sentant une carte cliquable
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class BoutonCarte extends JButton
{
	private ImageCarte imageCarte;
	private Carte carte;
	
	/**
	 * Cr�e une nouvelle carte cliquable
	 * @param image Image de la carte (elle-m�me enregistrant la carte qu'elle repr�sente)
	 */
	public BoutonCarte(ImageCarte image)
	{
		super(new ImageIcon(image.getImage()));
		this.imageCarte = image;
		this.carte = this.imageCarte.getCarte();
	}
	
	/**
	 * Retourne la carte repr�sent�e par le bouton cliquable
	 * @return Carte repr�sent�e par le bouton cliqu�
	 */
	public Carte getCarte()
	{
		return(this.carte);
	}	
}
