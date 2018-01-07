package _gui_version.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;

import _gui_version.model.*;
import _gui_version.controller.*;

/**
 * Classe repr�sentant le menu principal
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class MenuPrincipal extends VueGraphique
{
	//GUI components
	private Container conteneur;
	private JPanel panneau, sousPanneau;
	private JLabel logo, sousTitre;
	private JButton bJouer, bParametres, bQuitter;
	
	//Model elements
	private Jeu instanceDeJeu;
	
	//Controller
	private ControleurMenuPrincipal controleurBoutonJouer, controleurBoutonParametres, controleurBoutonQuitter;
	
	/**
	 * Cr�e le menu principal
	 * @param jeu Instance de jeu
	 */
	public MenuPrincipal(Jeu jeu)
	{
		//Initializes the common window's components
		super();
		this.instanceDeJeu = jeu; 
		
		//The view observes the game instance
		this.instanceDeJeu.addObserver(this);
		
		//Initializes the specific window's components
		this.initialiserFenetre();
		
		//Creating the controllers
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
		this.logo = new JLabel("J8 - LE JEU DU 8 AMERICAIN");
		this.logo.setHorizontalAlignment(SwingConstants.CENTER);
		this.logo.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
		this.logo.setForeground(Color.BLACK);
		
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
		this.sousPanneau.add(this.logo);
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
