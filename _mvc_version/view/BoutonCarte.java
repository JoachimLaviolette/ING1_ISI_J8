package _mvc_version.view;

import javax.swing.*;

import _mvc_version.model.*;

/**
 * Classe représentant une carte cliquable
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class BoutonCarte extends JButton
{
	/**
	 * ImageCarte associé au bouton
	 */
	private ImageCarte imageCarte;
	/**
	 * Carte associée au bouton
	 */
	private Carte carte;
	
	/**
	 * Crée une nouvelle carte cliquable
	 * @param image Image de la carte (elle-même enregistrant la carte qu'elle représente)
	 */
	public BoutonCarte(ImageCarte image)
	{
		super(new ImageIcon(image.getImage()));
		this.imageCarte = image;
		this.carte = this.imageCarte.getCarte();
	}
	
	/**
	 * Retourne la carte représentée par le bouton cliquable
	 * @return Carte représentée par le bouton cliqué
	 */
	public Carte getCarte()
	{
		return(this.carte);
	}	
}
