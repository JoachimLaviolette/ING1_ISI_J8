package _gui_version.view;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;

import _gui_version.model.*;
import _gui_version.controller.*;

/**
 * Classe représentant le menu de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class MenuJeu extends VueGraphique
{
	//GUI components
	private Container conteneur;
	private JPanel panneau, sousPanneau, panneauChoix, panneauBoutons;
	private JPanel panneauChoixVariante, panneauChoixNbJoueurs, panneauChoixPseudo, panneauChoixDifficulte;
	private JLabel titre, sousTitreVariante, sousTitreNbJoueurs, sousTitrePseudo, sousTitreDifficulte;
	private JComboBox<JTextField> selectChoixVariante, selectChoixDifficulte;
	private JTextField champChoixNbJoueurs, champChoixPseudo;
	private JButton bSuivant;
	private JButton bRetour;
	
	//Model elements
	private Jeu instanceDeJeu;
	
	//Controllers
	private ControleurMenuJeu controleurBoutonSuivant;
	private ControleurMenuJeu controleurBoutonRetour;
	
	/**
	 * Crée le menu de jeu
	 * @param jeu Instance de jeu
	 */
	public MenuJeu(Jeu jeu)
	{
		//Initializes the common window's components
		super();
		this.instanceDeJeu = jeu;
		
		//The view observes the game instance
		this.instanceDeJeu.addObserver(this);
		
		//Initializes the specific window's components
		this.initialiserFenetre();
		
		//Creating the controllers
		this.controleurBoutonSuivant = new ControleurMenuJeu(this.bSuivant, jeu, this);
		this.controleurBoutonRetour = new ControleurMenuJeu(this.bRetour, jeu, this);
	}

	/**
	 * Initialise les composants de la fenêtre
	 */
	protected void initialiserFenetre()
	{
		//Main window
		this.fenetre.setTitle("J8 > Paramétrage de la partie");
		
		//Container
		this.conteneur = this.fenetre.getContentPane();
		
		//Main panel
		this.panneau = new JPanel();
		this.panneau.setBackground(Color.decode("#0B7534"));
		
		//Sub panel's layout
		GridLayout surface1 = new GridLayout(4, 1); 
		surface1.setVgap(40);
		surface1.setHgap(10);
		
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
		
		//Choices sub panels
		this.panneauChoixVariante = new JPanel(surface3);
		this.panneauChoixVariante.setBackground(Color.decode("#0B7534"));
		
		this.panneauChoixNbJoueurs = new JPanel(surface3);
		this.panneauChoixNbJoueurs.setBackground(Color.decode("#0B7534"));
		
		this.panneauChoixPseudo = new JPanel(surface3);
		this.panneauChoixPseudo.setBackground(Color.decode("#0B7534"));
		
		this.panneauChoixDifficulte = new JPanel(surface3);
		this.panneauChoixDifficulte.setBackground(Color.decode("#0B7534"));	
		
		//Buttons panel's layout
		GridLayout surface4 = new GridLayout(2, 2);
		surface4.setVgap(20);
		surface4.setHgap(80);
		
		//Buttons panel
		this.panneauBoutons = new JPanel(surface4);
		this.panneauBoutons.setBackground(Color.decode("#0B7534"));
		
		//Labels
		this.titre = new JLabel("- MENU JEU -");
		this.titre.setHorizontalAlignment(SwingConstants.CENTER);
		this.titre.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		this.titre.setForeground(Color.WHITE);
		
		this.sousTitreVariante = new JLabel("Veuillez choisir une variante de jeu :");			
		this.sousTitreVariante.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreVariante.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreVariante.setForeground(Color.WHITE);
		
		this.sousTitreNbJoueurs = new JLabel("Veuillez choisir le nombre de joueurs :");			
		this.sousTitreNbJoueurs.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreNbJoueurs.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreNbJoueurs.setForeground(Color.WHITE);
		
		this.sousTitrePseudo = new JLabel("Veuillez entrer votre pseudo :");			
		this.sousTitrePseudo.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitrePseudo.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitrePseudo.setForeground(Color.WHITE);
		
		this.sousTitreDifficulte = new JLabel("Veuillez choisir la difficulté (stratégie des bots) :");			
		this.sousTitreDifficulte.setHorizontalAlignment(SwingConstants.LEFT);
		this.sousTitreDifficulte.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.sousTitreDifficulte.setForeground(Color.WHITE);
		
		//Combo boxes
		Object[] valeursVariantes = new Object[]{"Minimale", "Monclar", "4", "5", "Personnalisée"};
		Object[] valeursDifficulte = new Object[]{"Débutant", "Amateur", "Confirmé"};
		
		this.selectChoixVariante = new JComboBox(valeursVariantes);
		this.selectChoixVariante.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.selectChoixVariante.setBorder(null);
		this.selectChoixVariante.setBackground(Color.WHITE);	
		
		this.selectChoixDifficulte = new JComboBox(valeursDifficulte);		
		this.selectChoixDifficulte.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.selectChoixDifficulte.setBorder(null);
		this.selectChoixDifficulte.setBackground(Color.WHITE);
		
		//Text fields
		this.champChoixNbJoueurs = new JTextField(this.instanceDeJeu.getOptionsDeJeu()[0]);
		this.champChoixNbJoueurs.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.champChoixNbJoueurs.setBorder(null);
		this.champChoixNbJoueurs.setBackground(Color.WHITE);
		this.champChoixNbJoueurs.setForeground(Color.BLACK);

		this.champChoixPseudo = new JTextField();	
		this.champChoixPseudo.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
		this.champChoixPseudo.setBorder(null);
		this.champChoixPseudo.setBackground(Color.WHITE);
		this.champChoixPseudo.setForeground(Color.BLACK);
		
		//Buttons
		this.bSuivant = new JButton("SUIVANT");
		this.bSuivant.setBackground(Color.WHITE);
		this.bSuivant.setBorderPainted(false);
		this.bSuivant.setFocusPainted(false);
		
		this.bRetour = new JButton("RETOUR");
		this.bRetour.setBackground(Color.WHITE);
		this.bRetour.setBorderPainted(false);
		this.bRetour.setFocusPainted(false);
		
		//Disabled buttons for the blank spaces (deprecated)
		JButton bouton_i1 = new JButton("");
		bouton_i1.setVisible(false);
		
		JButton bouton_i2 = new JButton("");
		bouton_i2.setVisible(false);
		
		JButton bouton_i3 = new JButton("");
		bouton_i3.setVisible(false);
		
		//Composing
		this.panneauBoutons.add(bouton_i2);
		this.panneauBoutons.add(bouton_i3);
		this.panneauBoutons.add(this.bRetour);
		this.panneauBoutons.add(this.bSuivant);
		
		this.panneauChoixVariante.add(this.sousTitreVariante);
		this.panneauChoixVariante.add(this.selectChoixVariante);
		
		this.panneauChoixNbJoueurs.add(this.sousTitreNbJoueurs);
		this.panneauChoixNbJoueurs.add(this.champChoixNbJoueurs);
		
		this.panneauChoixPseudo.add(this.sousTitrePseudo);
		this.panneauChoixPseudo.add(this.champChoixPseudo);
		
		this.panneauChoixDifficulte.add(this.sousTitreDifficulte);
		this.panneauChoixDifficulte.add(this.selectChoixDifficulte);
		
		this.panneauChoix.add(this.panneauChoixVariante);
		this.panneauChoix.add(this.panneauChoixNbJoueurs);
		this.panneauChoix.add(this.panneauChoixPseudo);
		this.panneauChoix.add(this.panneauChoixDifficulte);
		
		this.sousPanneau.add(bouton_i1);
		this.sousPanneau.add(this.titre);
		this.sousPanneau.add(this.panneauChoix);		
		this.sousPanneau.add(this.panneauBoutons);
		
		this.panneau.add(this.sousPanneau);
		
		this.conteneur.add(this.panneau);
	}
	
	/**
	 * Retourne la liste de sélection du choix de variante
	 * @return Liste de sélection du choix de variante
	 */
	public JComboBox<JTextField> getChampChoixVariante()
	{
		return(this.selectChoixVariante);
	}
	
	/**
	 * Retourne la liste de sélection du choix de difficulté
	 * @return Liste de sélection du choix de difficulté
	 */
	public JComboBox<JTextField> getChampChoixDifficulte()
	{
		return(this.selectChoixDifficulte);
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
	 * Retourne le champ de texte du choix du pseudo
	 * @return Champ de texte du choix du pseudo
	 */
	public JTextField getChampChoixPseudo()
	{
		return(this.champChoixPseudo);
	}
	
	/**
	 * Ferme la fenêtre actuelle et ouvre celle du plateau de jeu
	 */
	private void ouvrirMenuPlateauJeu()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				fermer();
				new MenuPlateauJeu(instanceDeJeu);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Ferme la fenêtre actuelle et ouvre celle du menu principal
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
					case "Début de partie" :
						this.ouvrirMenuPlateauJeu();
						break;
					case "Menu principal" :
						this.ouvrirMenuPrincipal();
						break;
				}
			}
		}		
	}
}