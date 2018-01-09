package _mvc_version.view;

import java.awt.Image;

import _mvc_version.model.Carte;

/**
 * Classe repr�sentant une image de carte
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class ImageCarte
{
	private Image image;
	private String cle;
	private Carte carte;
	
	/**
	 * Cr�e une image de carte
	 * @param image Image source de la carte repr�sent�e
	 * @param cle Cl� de la carte repr�sent�e
	 * @param carte Carte repr�sent�e
	 */
	public ImageCarte(Image image, String cle, Carte carte)
	{
		this.image = image;
		this.cle = cle;
		this.carte = carte;
	}
	
	/**
	 * Retourne l'image source de la carte
	 * @return Image source de la carte
	 */
	public Image getImage()
	{
		return(this.image);
	}
	
	/**
	 * Retourne la cl� de la carte
	 * @return Cl� de la carte
	 */
	public String getCle()
	{
		return(this.cle);
	}
	
	/**
	 * Retourne la carte repr�sent�e
	 * @return Carte repr�sent�e
	 */
	public Carte getCarte()
	{
		return(this.carte);
	}
}
