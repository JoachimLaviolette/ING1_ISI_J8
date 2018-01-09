package _mvc_version.view;

import javax.swing.*;
import java.util.*;

/**
 * Classe abstraite représentant un panneau de cartes
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class PanneauCartes extends JPanel
{
	protected ArrayList<ImageCarte> imagesCartes;
	
	/**
	 * Crée un panneau de cartes
	 * @param imagesCartes Images des cartes composant ce panneau
	 */
	public PanneauCartes(ArrayList<ImageCarte> imagesCartes)
	{
		super();
		this.imagesCartes = imagesCartes;
	}
	
	/**
	 * Modifie les images des cartes composant le panneau
	 * @param imagesCartes Images des cartes composant le panneau
	 */
	public void setCartes(ArrayList<ImageCarte> imagesCartes)
	{
		this.imagesCartes = imagesCartes;
	}
}
