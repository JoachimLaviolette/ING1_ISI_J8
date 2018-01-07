package _gui_version.view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

import _gui_version.model.*;
import _gui_version.controller.*;

/**
 * Classe représentant le menu de paramètres
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class MenuParametres extends VueGraphique
{
	//GUI components
	private Container conteneur;
	private JPanel panneau, sousPanneau, panneauChoix, panneauBoutons;
	private JPanel panneauChoixNbJoueurs, panneauChoixNbPaquets;
	private JLabel titre, sousTitreNbJoueurs, sousTitreNbPaquets, sousTitreActivationScore;
	private JTextField champChoixNbJoueurs, champChoixNbPaquets;
	private JCheckBox boxScore;
	private JButton bValider;	
	
	//Model elements
	private Jeu instanceDeJeu;
	
	//Controllers
	private ControleurMenuParametres controleurMenuParametres;
	
	/**
	 * Crée le menu de paramètres
	 * @param jeu Instance de jeu
	 */
	public MenuParametres(Jeu jeu)
	{
		//Initializes the common window's components
		super();
		this.instanceDeJeu = jeu;
		
		//The view observes the game instance
		this.instanceDeJeu.addObserver(this);
				
		//Initializes the specific window's components
		this.initialiserFenetre();
		
		//Creating the controllers
		this.controleurMenuParametres = new ControleurMenuParametres(this.bValider, jeu, this);
	}
	
	/**
	 * Initialise les composants du menu de paramètres
	 */
	protected void initialiserFenetre()
	{
		//Main window
		this.fenetre.setTitle("J8 > Paramètres");
		
		//Container
		this.conteneur = this.fenetre.getContentPane();
		
		//Main panel
		this.panneau = new JPanel();
		this.panneau.setBackground(Color.decode("#0B7534"));
		
		//Sub panel's layout
		GridLayout surface1 = new GridLayout(4, 1); 
		surface1.setVgap(40);
		surface1.setHgap(0);
		
		//Sub panel
		this.sousPanneau = new JPanel(surface1);
		this.sousPanneau.setBackground(Color.decode("#0B7534"));
		
		//Choices panel's layout
		GridLayout surface2 = new GridLayout(2, 2);
		surface2.setVgap(40);
		surface2.setHgap(40);
		
		//Choices panel
		this.panneauChoix = new JPanel(surface2);
		this.panneauChoix.setBackground(Color.decode("#0B7534"));
			
		//Choices sub panel layout
		GridLayout surface3 = new GridLayout(2, 1);
		surface3.setVgap(10);
		
		//Choices sub panels
		this.panneauChoixNbJoueurs = new JPanel(surface3);
		this.panneauChoixNbJoueurs.setBackground(Color.decode("#0B7534"));
		
		this.panneauChoixNbPaquets = new JPanel(surface3);
		this.panneauChoixNbPaquets.setBackground(Color.decode("#0B7534"));
		
		//Buttons panel's layout
		GridLayout surface4 = new GridLayout(2, 1);
		surface4.setHgap(20);
		
		//Buttons panel
		this.panneauBoutons = new JPanel(surface4);
		this.panneauBoutons.setBackground(Color.decode("#0B7534"));
						
		//Labels
		this.titre = new JLabel("- MENU PARAMETRES -");
		this.titre.setHorizontalAlignment(SwingConstants.CENTER);
		this.titre.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		this.titre.setForeground(Color.WHITE);
		
		this.sousTitreNbJoueurs = new JLabel("Modifier le nombre de joueurs :");			
		this.sousTitreNbJoueurs.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreNbJoueurs.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreNbJoueurs.setForeground(Color.WHITE);
		
		this.sousTitreNbPaquets = new JLabel("Modifier le nombre de jeux de cartes :");			
		this.sousTitreNbPaquets.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreNbPaquets.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreNbPaquets.setForeground(Color.WHITE);
		
		this.sousTitreActivationScore = new JLabel("Activer/désactiver le système de score :");			
		this.sousTitreActivationScore.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreActivationScore.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreActivationScore.setForeground(Color.WHITE);		
		
		//Text fields
		this.champChoixNbJoueurs = new JTextField(this.instanceDeJeu.getOptionsDeJeu()[0]);
		this.champChoixNbJoueurs.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
		this.champChoixNbJoueurs.setBorder(null);
		this.champChoixNbJoueurs.setBackground(Color.WHITE);
		this.champChoixNbJoueurs.setForeground(Color.BLACK);

		this.champChoixNbPaquets = new JTextField(this.instanceDeJeu.getOptionsDeJeu()[1]);	
		this.champChoixNbPaquets.setFont(new Font(Font.DIALOG, Font.BOLD, 17));
		this.champChoixNbPaquets.setBorder(null);
		this.champChoixNbPaquets.setBackground(Color.WHITE);
		this.champChoixNbPaquets.setForeground(Color.BLACK);
						
		//Score box
		this.boxScore = new JCheckBox("   ACTIVER");
		if(this.instanceDeJeu.getOptionsDeJeu()[2].equals("Y"))
			this.boxScore.setSelected(true);
		else
			this.boxScore.setSelected(false);
		this.boxScore.setBackground(Color.decode("#0B7534"));
		this.boxScore.setHorizontalAlignment(SwingConstants.CENTER);
		this.boxScore.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		this.boxScore.setForeground(Color.WHITE);
		this.boxScore.setFocusable(false);
		
		//Buttons
		this.bValider = new JButton("VALIDER");
		this.bValider.setBackground(Color.WHITE);
		this.bValider.setBorderPainted(false);
		this.bValider.setFocusPainted(false);
		
		//Disabled buttons for the blank spaces (deprecated)
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);
		
		//Composing		
		this.panneauBoutons.add(this.bValider);
		this.panneauBoutons.add(bouton_i2);
		
		this.panneauChoixNbPaquets.add(this.sousTitreNbPaquets);
		this.panneauChoixNbPaquets.add(this.champChoixNbPaquets);
		
		this.panneauChoixNbJoueurs.add(this.sousTitreNbJoueurs);
		this.panneauChoixNbJoueurs.add(this.champChoixNbJoueurs);
		
		this.panneauChoix.add(this.panneauChoixNbJoueurs);
		this.panneauChoix.add(this.panneauChoixNbPaquets);
		this.panneauChoix.add(this.boxScore);
		
		this.sousPanneau.add(bouton_i1);
		this.sousPanneau.add(this.titre);
		this.sousPanneau.add(this.panneauChoix);		
		this.sousPanneau.add(this.panneauBoutons);
		
		this.panneau.add(this.sousPanneau);
		
		this.conteneur.add(this.panneau);
	}
	
	/**
	 * Retourne le champ de texte du choix du nombre de joueurs
	 * @return Champ de texte du choix du nombre de joueurs
	 */
	public JTextField getChampChoixNbJoueurs()
	{
		return(this.champChoixNbJoueurs);
	}
	
	/**
	 * Retourne le champ de texte du choix du nombre de paquets de cartes
	 * @return Champ de texte du choix du nombre de paquets de cartes
	 */
	public JTextField getChampChoixNbPaquets()
	{
		return(this.champChoixNbPaquets);
	}
	
	/**
	 * Retourne la boite à cocher indiquant l'état du système de score
	 * @return Boite à cocher indiquant l'état du système de score
	 */
	public JCheckBox getBoxScore()
	{
		return(this.boxScore);
	}
	
	/**
	 * Ferme la fenêtre actuelle et ouvre celle du menu de paramètres
	 */
	private void ouvrirMenuPrincipal()
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
	 * Signale que les changements ont été effectués via une boite de dialogue
	 */
	private void afficherChangementsParametresEffectues()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				JOptionPane.showMessageDialog(null, "Vos changements ont été pris en compte !", "Changement d'options effectué", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Met à jour la vue actuelle en fonction des notifications envoyées par le modèle de jeu observé
	 */
	public void update(Observable o, Object arg) 
	{
		if(o instanceof Jeu)
		{
			if(arg instanceof String)
			{
				switch((String)arg)
				{
					case "Menu principal" :
						this.ouvrirMenuPrincipal();
						break;
					case "Paramètres modifiés" :
						this.afficherChangementsParametresEffectues();
						break;
				}
			}
		}		
	}
}
