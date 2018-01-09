package _mvc_version.view;

import java.awt.*;
import java.util.*;

import _mvc_version.controller.*;
import _mvc_version.model.Partie;

/**
 * Classe du panneau de cartes représentant la main graphique du joueur
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class PanneauCartesMainJoueur extends PanneauCartes
{	
	private LinkedList<BoutonCarte> listeCartes;
		
	/**
	 * Crée le panneau de cartes représentant la main graphique du joueur
	 * @param imagesCartes Images des cartes composant le panneau de cartes
	 * @param partie Instance de partie
	 */
	public PanneauCartesMainJoueur(ArrayList<ImageCarte> imagesCartes, Partie partie)
	{
		super(imagesCartes);
		this.listeCartes = new LinkedList<BoutonCarte>();
		Iterator<ImageCarte> iteImages = this.imagesCartes.iterator();
		while(iteImages.hasNext())
		{
			ImageCarte imageCarte = iteImages.next();
			this.listeCartes.add(new BoutonCarte(imageCarte));
		}
		Iterator<BoutonCarte> iteBoutons = this.listeCartes.iterator();
		while(iteBoutons.hasNext())
		{
			BoutonCarte boutonCarte = iteBoutons.next();
			new ControleurPanneauMainJoueur(boutonCarte, partie);
			boutonCarte.setBorderPainted(false);
			boutonCarte.setBackground(Color.decode("#0B7534"));
			this.add(boutonCarte);
		}
	}
	
	/**
	 * Dessine des composants graphiques
	 */
	public void paintComponent(Graphics g)
	{		
		this.setBackground(Color.decode("#0B7534"));
	}	
}
