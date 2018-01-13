package _mvc_version.view;

import java.awt.*;
import java.util.*;

/**
 * Classe du panneau de cartes représentant le plateau de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class PanneauCartesPlateau extends PanneauCartes
{	
	/**
	 * Instance de l'imageCarte de la carte du talon de jeu
	 */
	private ImageCarte imageCarteTalon;
	/**
	 * Instance de l'imageCarte de la carte de la pioche (vue de dos, ou vide)
	 */
	private ImageCarte imageCartePioche;
	
	/**
	 * Crée le panneau de cartes représentant le plateau de jeu
	 * @param imagesCartes Images des cartes composants le plateau de jeu (image de la dernière carte du talon et de la pioche)
	 */
	public PanneauCartesPlateau(ArrayList<ImageCarte> imagesCartes)
	{
		super(imagesCartes);
		this.imageCarteTalon = this.imagesCartes.get(0); 
		this.imageCartePioche = this.imagesCartes.get(1);
	}
	
	/**
	 * Dessine des composants graphiques
	 */
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		this.setBackground(Color.decode("#0B7534"));
		g.drawImage(this.imageCarteTalon.getImage(), (this.getWidth()/4)+60, (this.getHeight()/2)-90, 120, 180, this);
		g.drawImage(this.imageCartePioche.getImage(), (this.getWidth()/2)+90, (this.getHeight()/2)-90, 120, 180, this);
	}	
	
	/**
	 * Modifie l'image du talon
	 * @param img Image de la nouvelle carte du talon
	 */
	public void setImageCarteTalon(ImageCarte img)
	{
		this.imageCarteTalon = img;
	}
	
	/**
	 * Modifie l'image de la pioche
	 * @param img Image de la pioche (pleine ou vide)
	 */
	public void setImageCartePioche(ImageCarte img)
	{
		this.imageCartePioche = img;
	}
}
