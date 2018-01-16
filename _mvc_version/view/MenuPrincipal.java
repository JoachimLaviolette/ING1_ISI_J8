package _mvc_version.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import _mvc_version.controller.*;
import _mvc_version.model.*;

/**
 * Classe repr�sentant le menu principal
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class MenuPrincipal extends VueGraphique
{
	/**
	 * Conteneur graphique contenant les composants de la fen�tre
	 */
	private Container conteneur;
	/**
	 * Panneau graphique princpal incopor� � la fen�tre
	 */
	private JPanel panneau;
	/**
	 * Panneau graphique secondaire incopor� � la fen�tre
	 */
	private JPanel sousPanneau;
	/**
	 * Label repr�sentant le titre du jeu
	 */
	private JLabel titre;
	/**
	 * Lable repr�sentant le sous titre du menu principal
	 */
	private JLabel sousTitre;
	/**
	 * Bouton permettant de jouer
	 */
	private JButton bJouer;
	/**
	 * Bouton permettant d'acc�der aux param�tres
	 */
	private JButton bParametres;
	/**
	 * Bouton permttant de quitter le jeu
	 */
	private JButton bQuitter;
	
	/**
	 * Instance du jeu
	 */
	private Jeu instanceDeJeu;
	
	/**
	 * Contr�leur du bouton associ�
	 */
	private ControleurMenuPrincipal controleurBoutonJouer, controleurBoutonParametres, controleurBoutonQuitter;
	
	/**
	 * Cr�e le menu principal
	 * @param jeu Instance de jeu
	 */
	public MenuPrincipal(Jeu jeu)
	{
		super();
		this.instanceDeJeu = jeu; 
		
		//La vue observe l'instance de jeu
		this.instanceDeJeu.addObserver(this);
		
		//Initialise les composants sp�cifiques � la fen�tre
		this.initialiserFenetre();
		
		//Cr�ation des contr�leurs
		this.controleurBoutonJouer = new ControleurMenuPrincipal(this.bJouer, jeu);
		this.controleurBoutonParametres = new ControleurMenuPrincipal(this.bParametres, jeu);
		this.controleurBoutonQuitter = new ControleurMenuPrincipal(this.bQuitter, jeu);
	}
	
	/**
	 * Initialise les composants du menu principal
	 */
	protected void initialiserFenetre()
	{
		//Main window
		this.fenetre.setTitle("J8 - Le jeu du 8 Americain");
		
		//Container
		this.conteneur = this.fenetre.getContentPane();
		
		//Main panel
		this.panneau = new JPanel();
		this.panneau.setBackground(Color.decode("#0B7534"));
		
		//Layout
		GridLayout surface = new GridLayout(7, 1); 
		surface.setVgap(40);
		surface.setHgap(10);
		
		//Panel
		this.sousPanneau = new JPanel(surface);
		this.sousPanneau.setBackground(Color.decode("#0B7534"));
				
		//Labels
		this.titre = new JLabel("J8 - LE JEU DU 8 AMERICAIN");
		this.titre.setHorizontalAlignment(SwingConstants.CENTER);
		this.titre.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
		this.titre.setForeground(Color.BLACK);
		
		this.sousTitre = new JLabel("- MENU PRINCIPAL -");			
		this.sousTitre.setHorizontalAlignment(SwingConstants.CENTER);
		this.sousTitre.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		this.sousTitre.setForeground(Color.WHITE);
		
		//Buttons
		this.bJouer = new JButton("JOUER");
		this.bJouer.setBackground(Color.WHITE);
		this.bJouer.setBorderPainted(false);
		this.bJouer.setFocusPainted(false);
		
		this.bParametres = new JButton("PARAMETRES");
		this.bParametres.setBackground(Color.WHITE);
		this.bParametres.setBorderPainted(false);
		this.bParametres.setFocusPainted(false);
		
		this.bQuitter = new JButton("QUITTER");
		this.bQuitter.setBackground(Color.WHITE);
		this.bQuitter.setBorderPainted(false);
		this.bQuitter.setFocusPainted(false);
		
		//Disabled buttons for the blank spaces (deprecated)
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);
		
		//Composing
		this.sousPanneau.add(bouton_i1);
		this.sousPanneau.add(bouton_i2);
		this.sousPanneau.add(this.titre);
		this.sousPanneau.add(this.sousTitre);		
		this.sousPanneau.add(this.bJouer);
		this.sousPanneau.add(this.bParametres);
		this.sousPanneau.add(this.bQuitter);
		
		this.panneau.add(this.sousPanneau);
		
		this.conteneur.add(this.panneau);
	}
	
	/**
	 * Ferme la fen�tre actuelle et ouvre celle du menu de jeu
	 */
	private void ouvrirMenuJeu()
	{
		this.instanceDeJeu.deleteObserver(this);
		Runnable code = new Runnable() {
			public void run() 
			{				
				fermer();
				new MenuJeu(instanceDeJeu);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Ferme la fen�tre actuelle et ouvre celle du menu de param�tres
	 */
	private void ouvrirMenuParametres()
	{
		this.instanceDeJeu.deleteObserver(this);
		Runnable code = new Runnable() {
			public void run() 
			{
				fermer();
				new MenuParametres(instanceDeJeu);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Met � jour la vue actuelle en fonction des notifications envoy�es par le mod�le de jeu observ� 
	 */
	public void update(Observable o, Object arg) 
	{
		if(o instanceof Jeu)
		{
			if(arg instanceof String)
			{
				switch((String)arg)
				{
					case "Jouer" :
						this.ouvrirMenuJeu();
						break;
					case "Param�tres" :
						this.ouvrirMenuParametres();
						break;
					case "Quitter" :			
						this.fermer();
						break;
				}
			}
		}		
	}
}
