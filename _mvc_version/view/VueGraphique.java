package _mvc_version.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Classe abstraite repr�sentant une vue de l'interface graphique
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public abstract class VueGraphique implements Observer
{
	protected Toolkit toolkit;
	protected JFrame fenetre;
	
	/**
	 * Cr�e une vue graphique et fixe ses propri�t�s de base
	 */
	public VueGraphique() 
	{
		//here are the basic settings for every subclasses window
		this.fenetre = new JFrame();
		this.toolkit = Toolkit.getDefaultToolkit();
		this.fenetre.setSize(this.toolkit.getScreenSize().width/2, this.toolkit.getScreenSize().height/2);
		this.fenetre.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.fenetre.setVisible(true);
		this.fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}	
	
	/**
	 * Ferme la fen�tre de la vue courante
	 */
	public void fermer()
	{
		this.fenetre.dispose();
	}
	
	/**
	 * Initialise les composants graphiques de la fen�tre sp�cifique
	 */
	protected abstract void initialiserFenetre();	
	
	/**
	 * Met � jour la vue actuelle en fonction des notifications envoy�es par le mod�le observ�
	 */
	public abstract void update(Observable o, Object arg);
}
