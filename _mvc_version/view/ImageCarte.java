package _mvc_version.view;

import java.awt.Image;

import _mvc_version.model.Carte;

/**
 * Classe représentant une image de carte
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
	 * Crée une image de carte
	 * @param image Image source de la carte représentée
	 * @param cle Clé de la carte représentée
	 * @param carte Carte représentée
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
	 * Retourne la clé de la carte
	 * @return Clé de la carte
	 */
	public String getCle()
	{
		return(this.cle);
	}
	
	/**
	 * Retourne la carte représentée
	 * @return Carte représentée
	 */
	public Carte getCarte()
	{
		return(this.carte);
	}
}
