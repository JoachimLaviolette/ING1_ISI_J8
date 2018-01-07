package _gui_version.view;

import java.awt.*;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import _gui_version._exceptions.GameException;
import _gui_version.controller.*;
import _gui_version.model.*;

/**
 * Classe représentant le plateau de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class MenuPlateauJeu extends VueGraphique
{
	//GUI components
	private Container conteneur;
	private JPanel panneauTitre, panneauInfos, panneauPlateau, panneauActions, panneauActionsJouer, panneauCartes, panneauVariantes, panneauRetourPC, panneauTerminerTour;
	private JLabel titre;
	private JTextArea infos;
	private JButton bJouer, bPiocher, bChangerVariante;
	private JButton bRetourPC, bRetourPV, bTerminerTour;
	private JButton bVMinimale, bVMonclar, bV4, bV5, bVPerso;
	private JScrollPane panneauDeCartes;
	private ArrayList<ImageCarte> imagesCartesPlateau, imagesCartesJoueur;
	
	//Map collection
	private HashMap<String, Image> bibliotheque;
	
	//Model elements
	private Partie instanceDePartie;
	private Jeu instanceDeJeu;
	
	//Controller
	private ControleurMenuPlateauJeu controleurBoutonJouer, controleurBoutonPiocher, controleurBoutonChangerVariante, controleurBoutonRetourPC, controleurBoutonTerminerTour;
	private ControleurMenuPlateauJeu controleurBoutonVMinimiale, controleurBoutonVMonclar, controleurBoutonV4, controleurBoutonV5, controleurBoutonVPerso, controleurBoutonRetourPV;
	
	/**
	 * Crée le plateau de jeu
	 * @param jeu Instance de jeu
	 */
	public MenuPlateauJeu(Jeu jeu)
	{
		//Initializes the common window's components
		super();	
		this.instanceDePartie = jeu.obtenirPartie(this);
		this.bibliotheque = new HashMap<String, Image>();	
		this.imagesCartesPlateau = new ArrayList<ImageCarte>();	
		this.imagesCartesJoueur = new ArrayList<ImageCarte>();	
		
		this.instanceDeJeu = jeu;
		//The view observes the match instance
		this.instanceDePartie.addObserver(this);
		
		//Initializes the specific window's components
		this.initialiserBibliotheque();
		this.initialiserImagesCartesPlateau();
		this.initialiserImagesCartesMainJoueur();
		this.initialiserFenetre();
		
		//Creating the controllers
		this.controleurBoutonJouer = new ControleurMenuPlateauJeu(this.bJouer, this.instanceDePartie, null);
		this.controleurBoutonPiocher = new ControleurMenuPlateauJeu(this.bPiocher, this.instanceDePartie, null);
		this.controleurBoutonChangerVariante = new ControleurMenuPlateauJeu(this.bChangerVariante, this.instanceDePartie, null);
		this.controleurBoutonTerminerTour = new ControleurMenuPlateauJeu(this.bTerminerTour, this.instanceDePartie, null);
		this.controleurBoutonRetourPC = new ControleurMenuPlateauJeu(this.bRetourPC, this.instanceDePartie, "PC");
		this.controleurBoutonVMinimiale = new ControleurMenuPlateauJeu(this.bVMinimale, this.instanceDePartie, null);
		this.controleurBoutonVMonclar = new ControleurMenuPlateauJeu(this.bVMonclar, this.instanceDePartie, null);
		this.controleurBoutonV4 = new ControleurMenuPlateauJeu(this.bV4, this.instanceDePartie, null);
		this.controleurBoutonV5 = new ControleurMenuPlateauJeu(this.bV5, this.instanceDePartie, null);
		this.controleurBoutonVPerso = new ControleurMenuPlateauJeu(this.bVPerso, this.instanceDePartie, null);
		this.controleurBoutonRetourPV = new ControleurMenuPlateauJeu(this.bRetourPV, this.instanceDePartie, "PV");
	}
	
	/**
	 * Initialise les composants du plateau de jeu
	 */
	protected void initialiserFenetre()
	{
		//Main window
		this.fenetre.setTitle("J8 - Le jeu du 8 Americain");
		
		//Container
		this.conteneur = this.fenetre.getContentPane();
		
		//Layout
		this.fenetre.setLayout(new BorderLayout());
		
		GridLayout surfaceInfos = new GridLayout(1, 1);
		
		GridLayout surfaceActions = new GridLayout(1, 3);
		
		FlowLayout surfaceActionsJouer = new FlowLayout();
		
		GridLayout surfaceVariantes = new GridLayout(1, 6);
		
		//Panels
		this.panneauTitre = new JPanel();
		this.panneauTitre.setBackground(Color.WHITE);	
		this.panneauTitre.setBorder(new LineBorder(Color.WHITE, 2));
		
		this.panneauInfos = new JPanel(surfaceInfos);
		this.panneauInfos.setBackground(Color.BLACK);
		this.panneauInfos.setBorder(new LineBorder(Color.WHITE, 2));
				
		this.panneauPlateau = new PanneauCartesPlateau(this.imagesCartesPlateau);	
		this.panneauPlateau.setBackground(Color.decode("#0B7534"));
		this.panneauPlateau.setBorder(new LineBorder(Color.WHITE, 2));
		
		this.panneauActions = new JPanel(surfaceActions);
		this.panneauActions.setBackground(Color.decode("#0B7534"));
		this.panneauActions.setBorder(new LineBorder(Color.WHITE, 2));
		
		this.panneauVariantes = new JPanel(surfaceVariantes);
		this.panneauVariantes.setBackground(Color.decode("#0B7534"));
		this.panneauVariantes.setBorder(new LineBorder(Color.WHITE, 2));
		
		this.panneauActionsJouer = new JPanel(surfaceActionsJouer);
		this.panneauActionsJouer.setBackground(Color.decode("#0B7534"));
		this.panneauActionsJouer.setPreferredSize(new Dimension(0, 230));
		this.panneauActionsJouer.setBorder(new LineBorder(Color.WHITE, 2));
		
		this.panneauRetourPC = new JPanel();
		this.panneauRetourPC.setBackground(Color.decode("#0B7534"));
		this.panneauRetourPC.setPreferredSize(new Dimension(270, 70));
		
		this.panneauCartes = new PanneauCartesMainJoueur(this.imagesCartesJoueur, this.instanceDePartie);
		this.panneauCartes.setBackground(Color.decode("#0B7534"));

		this.panneauTerminerTour = new JPanel();
		this.panneauTerminerTour.setBackground(Color.decode("#0B7534"));
		this.panneauTerminerTour.setPreferredSize(new Dimension(270, 70));

		//Labels
		this.titre = new JLabel("J8 - LE JEU DU 8 AMERICAIN");
		this.titre.setHorizontalAlignment(SwingConstants.CENTER);
		this.titre.setFont(new Font(Font.DIALOG, Font.BOLD, 35));
		this.titre.setForeground(Color.BLACK);
		
		this.infos = new JTextArea();
		this.rafraichirInfos();
		this.infos.setBackground(Color.BLACK);
		this.infos.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
		this.infos.setForeground(Color.GREEN);
				
		//Buttons
		this.bJouer = new JButton("JOUER");
		this.bJouer.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		this.bJouer.setBackground(Color.decode("#e84833"));
		this.bJouer.setBorderPainted(false);
		this.bJouer.setFocusPainted(false);
		
		this.bPiocher = new JButton("PIOCHER");
		this.bPiocher.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		this.bPiocher.setBackground(Color.decode("#4667aa"));
		this.bPiocher.setBorderPainted(false);
		this.bPiocher.setFocusPainted(false);
		
		this.bChangerVariante = new JButton("CHANGER VARIANTE");
		this.bChangerVariante.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
		this.bChangerVariante.setBackground(Color.decode("#a08cd1"));
		this.bChangerVariante.setBorderPainted(false);
		this.bChangerVariante.setFocusPainted(false);
		
			//Play menu buttons
			this.bRetourPC = new JButton("RETOUR");
			this.bRetourPC.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bRetourPC.setBackground(Color.ORANGE);
			this.bRetourPC.setBorderPainted(true);
			this.bRetourPC.setFocusPainted(false);
			this.bRetourPC.setPreferredSize(new Dimension(160, 50));
			this.bRetourPC.setBorder(new LineBorder(Color.decode("#1e1e1e"), 3));

			this.bTerminerTour = new JButton("TERMINER");
			this.bTerminerTour.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bTerminerTour.setBackground(Color.WHITE);
			this.bTerminerTour.setBorderPainted(true);
			this.bTerminerTour.setFocusPainted(false);
			this.bTerminerTour.setPreferredSize(new Dimension(190, 50));
			this.bTerminerTour.setBorder(new LineBorder(Color.decode("#1e1e1e"), 3));
		
			//Variant buttons
			this.bVMinimale = new JButton("VARIANTE MINIMALE");
			this.bVMinimale.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bVMinimale.setBackground(Color.WHITE);
			this.bVMinimale.setBorderPainted(false);
			this.bVMinimale.setFocusPainted(false);
			
			this.bVMonclar = new JButton("VARIANTE MONCLAR");
			this.bVMonclar.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bVMonclar.setBackground(Color.WHITE);
			this.bVMonclar.setBorderPainted(false);
			this.bVMonclar.setFocusPainted(false);
			
			this.bV4 = new JButton("VARIANTE 4");
			this.bV4.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bV4.setBackground(Color.WHITE);
			this.bV4.setBorderPainted(false);
			this.bV4.setFocusPainted(false);
			
			this.bV5 = new JButton("VARIANTE 5");
			this.bV5.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bV5.setBackground(Color.WHITE);
			this.bV5.setBorderPainted(false);
			this.bV5.setFocusPainted(false);
			
			this.bVPerso = new JButton("VARIANTE PERSO");
			this.bVPerso.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bVPerso.setBackground(Color.WHITE);
			this.bVPerso.setBorderPainted(false);
			this.bVPerso.setFocusPainted(false);
			
			this.bRetourPV = new JButton("RETOUR");
			this.bRetourPV.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
			this.bRetourPV.setBackground(Color.ORANGE);
			this.bRetourPV.setBorderPainted(false);
			this.bRetourPV.setFocusPainted(false);
			
		//Composing
		this.panneauTitre.add(this.titre);
		
		this.panneauInfos.add(this.infos);
		
		this.panneauActions.add(this.bJouer);
		this.panneauActions.add(this.bPiocher);
		this.panneauActions.add(this.bChangerVariante);
		
		this.panneauVariantes.add(this.bRetourPV);
		this.panneauVariantes.add(this.bVMinimale);
		this.panneauVariantes.add(this.bVMonclar);
		this.panneauVariantes.add(this.bV4);
		this.panneauVariantes.add(this.bV5);
		this.panneauVariantes.add(this.bVPerso);
		this.panneauVariantes.repaint();
		
		this.panneauRetourPC.add(this.bRetourPC);
		this.panneauRetourPC.repaint();
		
		this.panneauTerminerTour.add(this.bTerminerTour);
		this.panneauTerminerTour.repaint();
		
		this.panneauDeCartes = new JScrollPane(this.panneauCartes);
		this.panneauDeCartes.setBackground(Color.decode("#0B7534"));
		this.panneauDeCartes.setBorder(new LineBorder(Color.decode("#0B7534")));
		this.panneauDeCartes.setPreferredSize(new Dimension(1280, 220));
		this.panneauDeCartes.repaint();
		
		this.panneauActionsJouer.add(this.panneauRetourPC);
		this.panneauActionsJouer.add(this.panneauDeCartes);
		this.panneauActionsJouer.add(this.panneauTerminerTour);		
		this.panneauActionsJouer.repaint();
				
		this.conteneur.add(this.panneauTitre, BorderLayout.NORTH);
		this.conteneur.add(this.panneauInfos, BorderLayout.WEST);
		this.conteneur.add(this.panneauPlateau, BorderLayout.CENTER);
		this.conteneur.add(this.panneauActions, BorderLayout.SOUTH);
	}

	/**
	 * Initialise la bibliothèque (map) contenant les images de l'ensemble des cartes ainsi que les clés associées
	 */
	private void initialiserBibliotheque()
	{
		String cle = "", valeur = "";
		for(int i = 1 ; i < 14  ; i++)
		{
			switch(i)
			{
				case 1 : valeur = "as"; break; 
				case 2 : valeur = "deux"; break;
				case 3 : valeur = "trois"; break;
				case 4 : valeur = "quatre"; break;
				case 5 : valeur = "cinq"; break;
				case 6 : valeur = "six"; break;
				case 7 : valeur = "sept"; break;
				case 8 : valeur = "huit"; break;
				case 9 : valeur = "neuf"; break;
				case 10 : valeur = "dix"; break;
				case 11 : valeur = "valet"; break;
				case 12 : valeur = "dame"; break;
				case 13 : valeur = "roi"; break;
			}
			cle = valeur.toLowerCase() + "_coeur"; 
			try 
			{
				this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			cle = valeur.toLowerCase() + "_trefle";
			try 
			{
				this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			cle = valeur.toLowerCase() + "_carreau";
			try 
			{
				this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
			} 
			catch (IOException e) 
			{			
				e.printStackTrace();
			}
			cle = valeur.toLowerCase() + "_pique";
			try 
			{
				this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		//joker image
		cle = "joker"; 
		try 
		{
			this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//game pick images
		cle = "pioche"; 
		try 
		{
			this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		cle = "pioche_vide"; 
		try 
		{
			this.bibliotheque.put(cle, ImageIO.read(new BufferedInputStream(new FileInputStream("src/_assets/_cards/" + cle + ".png"))));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialise les images des cartes du plateau de jeu
	 */
	private void initialiserImagesCartesPlateau()
	{
		Carte carteTalon = this.instanceDePartie.getDerniereCarte(this.instanceDePartie.getTalon());
		String valeur, symbole, cle;
		valeur = (carteTalon.getValeur() + "").toLowerCase();
		cle = valeur;
		if(!valeur.equals("joker"))
		{
			symbole = (carteTalon.getSymbole() + "").toLowerCase();		
			cle += "_" + symbole;
		}
		ImageCarte imageCarteTalon = new ImageCarte(this.bibliotheque.get(cle), cle, carteTalon);
		ImageCarte imageCartePioche = new ImageCarte(this.bibliotheque.get("pioche"), "pioche", null);
		this.imagesCartesPlateau.add(imageCarteTalon);
		this.imagesCartesPlateau.add(imageCartePioche);
	}
	
	/**
	 * Initialise les images des cartes de la main du joueur
	 */
	private void initialiserImagesCartesMainJoueur()
	{
		LinkedList<Carte> mainVersionCarte = this.instanceDePartie.getJoueurActif().getMain();
		Iterator<Carte> ite = mainVersionCarte.iterator();
		while(ite.hasNext())
		{
			Carte carte = ite.next();
			String valeur, symbole, cle;
			valeur = (carte.getValeur() + "").toLowerCase();
			cle = valeur;
			if(!valeur.equals("joker"))
			{
				symbole = (carte.getSymbole() + "").toLowerCase();
				cle += "_" + symbole;
			}
			ImageCarte imageCarte = new ImageCarte(this.bibliotheque.get(cle), cle, carte);
			this.imagesCartesJoueur.add(imageCarte);
		}
	}
		
	/**
	 * Récupère les informations quant aux mains des joueurs
	 * @return Informations quant aux mains des joueurs
	 */
	private String getInfosMainJoueurs()
	{
		StringBuffer texte = new StringBuffer();
		for(int x = 0 ; x < this.instanceDePartie.getJoueursDeLaPartie().size() ; x++)
		{
			Joueur joueurX = this.instanceDePartie.getJoueursDeLaPartie().get(x);
			if(joueurX.equals(this.instanceDePartie.getJoueurActif()))
			{
				texte.append("  > ACTIF <\n");
			}
			texte.append("  [");			
			if(joueurX instanceof JoueurConcret)
				texte.append("Vous");
			else
				texte.append(joueurX.getPseudo());
			texte.append(" : " + joueurX.getMain().size() + " carte");
			if(joueurX.getMain().size() > 1)
				texte.append("s");
			texte.append("]");
			if(joueurX.equals(this.instanceDePartie.getJoueurActif().getJoueurSuivant()))
				texte.append(" (joueur suivant)");
			texte.append("\n");
			texte.append("  --\n");
		}
		return(texte.toString());
	}	
	
	/**
	 * Récupère les informations quant aux paquets de cartes (talon et pioche)
	 * @return Informations quant aux paquets de cartes (talon et pioche)
	 */
	private String getInfosPaquets()
	{
		StringBuffer texte = new StringBuffer();
		texte.append("\n  [Pioche : " + this.instanceDePartie.getPioche().size() + " carte");
		if(this.instanceDePartie.getPioche().size() > 1)
			texte.append("s");
		texte.append("]   ");
		texte.append("[Talon : " + this.instanceDePartie.getTalon().size() + " carte");
		if(this.instanceDePartie.getTalon().size() > 1)
			texte.append("s");
		texte.append("]       \n  ---------------------------------------------------\n");
		return(texte.toString());
	}	
	
	/**
	 * Signale qu'une carte spéciale (couleur ou symbole selon la variante) a été demandée au tour précédent
	 */
	private void afficherCarteDemandee()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				StringBuffer texte = new StringBuffer("");
				String elementCarteDemandee = null;
				try
				{
					elementCarteDemandee = instanceDePartie.getVarianteCourante().getElementCarteDemandee();
				}
				catch(GameException e)
				{
					e.printStackTrace();
				}
				if(!elementCarteDemandee.trim().equals(""))
				{	
					texte.append("Le précédent joueur a joué un 8 et a demandé de jouer du " + elementCarteDemandee);
					JOptionPane.showMessageDialog(null, texte.toString(), "Carte spéciale demandée",  JOptionPane.WARNING_MESSAGE);
					//System.out.println(texte.toString());
				}			
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);	
	}
	
	/**
	 * Rafraichit les informations (panel gauche de la GUI) quant aux mains et aux paquets de jeux du plateau
	 */
	private void rafraichirInfos()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				StringBuffer texte = new StringBuffer();	
				texte.append(getInfosPaquets());
				texte.append(getInfosMainJoueurs());	
				infos.setText(texte.toString());
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Rafraichit la main du joueur à la site d'une sélection de carte (action de jouer)
	 */
	private void rafraichirMain()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauActionsJouer);
				panneauActionsJouer.removeAll();
				
				imagesCartesJoueur.removeAll(imagesCartesJoueur);
				initialiserImagesCartesMainJoueur();
				panneauCartes = new PanneauCartesMainJoueur(imagesCartesJoueur, instanceDePartie);
				panneauCartes.setBackground(Color.decode("#0B7534"));
				
				panneauDeCartes = new JScrollPane(panneauCartes);
				panneauDeCartes.setBackground(Color.decode("#0B7534"));
				panneauDeCartes.setBorder(new LineBorder(Color.decode("#0B7534")));
				panneauDeCartes.setPreferredSize(new Dimension(1280, 220));
					
				panneauActionsJouer.add(panneauRetourPC);
				panneauActionsJouer.add(panneauDeCartes);
				panneauActionsJouer.add(panneauTerminerTour);	
				
				conteneur.add(panneauActionsJouer, BorderLayout.SOUTH);		
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Rafraichit le talon de jeu du plateau à la suite d'un dépôt 
	 */
	private void rafraichirTalon()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauPlateau);
				
				imagesCartesPlateau.removeAll(imagesCartesPlateau);
				initialiserImagesCartesPlateau();
				panneauPlateau = new PanneauCartesPlateau(imagesCartesPlateau);	
				panneauPlateau.setBackground(Color.decode("#0B7534"));
				
				conteneur.add(panneauPlateau, BorderLayout.CENTER);
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Rafraichit la pioche lorsque celle-ci est vide
	 */
	private void afficherPiocheVide()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				((PanneauCartesPlateau)panneauPlateau).setImageCartePioche(new ImageCarte(bibliotheque.get("pioche_vide"), "pioche_vide", null));
				panneauPlateau.repaint();
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);		
	}
	
	/**
	 * Désactive les boutons d'action lorsque le tour du joueur concret est terminé
	 */
	private void desactiverActions()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				bJouer.setEnabled(false);
				bPiocher.setEnabled(false);
				bChangerVariante.setEnabled(false);
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Active les boutons d'actions lorsque le tour du joueur concret (re)démarre
	 */
	private void activerActions()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				bJouer.setEnabled(true);
				bPiocher.setEnabled(true);
				bChangerVariante.setEnabled(true);
				if(instanceDePartie.getRetourPossible())
					activerRetourPC();
				else
					desactiverRetourPC();
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Désactive le bouton de retour du panneau de choix de cartes dès qu'au moins un carte a été jouée 
	 */
	private void desactiverRetourPC()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				bRetourPC.setEnabled(false);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
		
	}
	
	/**
	 * Active le bouton de retour du panneau de choix de cartes lorsque le tour du joueur concret est terminé
	 */
	private void activerRetourPC()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				bRetourPC.setEnabled(true);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Change le panneau de choix de cartes par le panneau d'actions de jeu in-game
	 */
	private void afficherPanneauActionsDepuisPC()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauActionsJouer);
				conteneur.add(panneauActions, BorderLayout.SOUTH);
				panneauActions.repaint();
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Change le panneau de choix de variante par le panneau d'actions de jeu in-game
	 */
	private void afficherPanneauActionsDepuisPV()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauVariantes);
				conteneur.add(panneauActions, BorderLayout.SOUTH);
				panneauActions.repaint();
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Change le panneau d'actions de jeu in-game par le panneau de choix de cartes
	 */
	private void afficherPanneauCartes()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauActions);
				conteneur.add(panneauActionsJouer, BorderLayout.SOUTH);
				panneauActionsJouer.repaint();
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);		
	}
	
	/**
	 * Change le panneau d'actions de jeu in-game par le panneau de choix de variante
	 */
	private void afficherPanneauVariante()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				conteneur.remove(panneauActions);
				panneauVariantes.repaint();
				conteneur.add(panneauVariantes, BorderLayout.SOUTH);		
				fenetre.repaint();
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Signale que la variante couante a été changée via une boite de dialogue
	 */
	private void afficherChangementVarianteEffectue()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				JOptionPane.showMessageDialog(null, "La variante courante a été modifiée !", "Modification de variante", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}

	/**
	 * Affiche qu'une erreur de combinaison est survenue via une boite de dialogue
	 */
	private void afficherErreurCombinaison()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				JOptionPane.showMessageDialog(null, "La carte que vous souhaitez jouer ne peut être combinée avec la précédente", "Erreur de combinaison", JOptionPane.ERROR_MESSAGE);	
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Demande la couleur à la suite d'un dépôt de 8, via un boite de dialogue
	 * @return Index représentant la couleur choisie de l'utilisateur
	 */
	private int afficherMenuChoixCouleur()
	{
		String options[] = {"Rouge", "Noire"};
		int choix = JOptionPane.showOptionDialog(null, "Veuillez choisir la couleur que vous souhaitez que l'on joue par la suite (Rouge par défaut) :", "Choix de la couleur", JOptionPane.DEFAULT_OPTION,
		        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);	
		return(choix);
	}
	
	/**
	 * Demande le symbole à la suite d'un dépôt de 8, via un boite de dialogue
	 * @return Index représentant le symbole choisi de l'utilisateur
	 */
	private int afficherMenuChoixSymbole()
	{
		String options[] = {"Carreau", "Coeur", "Pique", "Trèfle"};
		int choix = JOptionPane.showOptionDialog(null, "Veuillez choisir le symbole que vous souhaitez que l'on joue par la suite (Carreau par défaut) :", "Choix du symbole", JOptionPane.DEFAULT_OPTION,
		        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);	
		return(choix);
	}
	
	/**
	 * Demande au joueur concret s'il souhaite annoncer carte, via une boite de dialogue
	 * @return Index représentant la réponse de l'utilisateur
	 */
	private int afficherPropositionAnnoncerCarte()
	{		
		String options[] = {"Oui", "Non"};
		int choix = JOptionPane.showOptionDialog(null, "Il ne vous reste plus qu'une carte ! Souhaitez-vous annoncer carte ? (Oui par défaut)", "Annoncer carte", JOptionPane.DEFAULT_OPTION,
		        JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);	
		return(choix);
	}
	
	/**
	 * Signale que l'utilisateur actif a annoncé carte, via une boite de dialogue
	 */
	private void signalerAnnonceCarte()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				JOptionPane.showMessageDialog(null, instanceDePartie.getJoueurActif().getPseudo() + " a annoncé \"CARTE\" !", "Signal annonce carte", JOptionPane.INFORMATION_MESSAGE);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
	}
	
	/**
	 * Signale la fin de la partie, via une boite de dialogue
	 */
	private void annoncerFinDePartie()
	{
		Runnable code = new Runnable() {
			public void run() 
			{
				StringBuffer texte = new StringBuffer();
				texte.append("--------------------------------------------------------------------------------");
				texte.append("\n                                    VICTOIRE                                     ");
				texte.append("\n--------------------------------------------------------------------------------");
				texte.append("\nLa partie est terminée !\nLe gagnant est... " + instanceDePartie.getJoueurGagnant().getPseudo() + " !\nFélicitations à toi !\n");
				JOptionPane.showMessageDialog(null, texte.toString(), "Partie terminée",  JOptionPane.WARNING_MESSAGE);
			}
		};
		if(EventQueue.isDispatchThread())
			code.run();
		else
			EventQueue.invokeLater(code);
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
	 * Met à jour la vue actuelle en fonction des notifications envoyées par le modèle de partie observé 
	 */
	public void update(Observable o, Object arg) 
	{
		if(o instanceof Partie)
		{
			if(arg instanceof String)
			{
				if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
				{
					switch((String)arg)
					{
						case "Jouer tour" :
							this.activerActions();
							this.rafraichirInfos();
							this.afficherCarteDemandee();
							break;
						case "Jouer" :
							this.afficherPanneauCartes();
							break;
						case "Changer variante" :			
							this.afficherPanneauVariante();
							break;
						case "Variante changée" :			
							this.afficherChangementVarianteEffectue();
							this.afficherPanneauActionsDepuisPV();
							break;
						case "Terminer tour" :
							this.afficherPanneauActionsDepuisPC();
							this.desactiverActions();
							break;						
						case "Retour PC" :
							this.afficherPanneauActionsDepuisPC();
							break;
						case "Retour PV" :
							this.afficherPanneauActionsDepuisPV();
							break;
						case "Mettre à jour main" :
							this.rafraichirMain();
							this.rafraichirInfos();
							break;
						case "Désactiver retour" :
							this.desactiverRetourPC();
							break;
						case "Activer retour" :
							this.activerRetourPC();
							break;
						case "Erreur de combinaison" :
							this.afficherErreurCombinaison();
							break;
					}
				}
				switch((String)arg)
				{
					case "Pioche vide" :
						this.afficherPiocheVide();
						break;
					case "Mettre à jour talon" :
						this.rafraichirTalon();
						this.rafraichirInfos();
						break;
					case "Mettre à jour main" :
						this.rafraichirInfos();
						break;
					case "Demander couleur" :
						if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
						{
							int choixCouleur = this.afficherMenuChoixCouleur(); 
							new ControleurMenuPlateauJeu(this.instanceDePartie, ((choixCouleur + 1) + ""), "Couleur");
						}
						else
							new ControleurMenuPlateauJeu(this.instanceDePartie, null, "Couleur");
						break;
					case "Demander symbole" :
						if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
						{
							int choixSymbole = this.afficherMenuChoixSymbole();
							new ControleurMenuPlateauJeu(this.instanceDePartie, ((choixSymbole + 1) + ""), "Symbole");
						}
						else
							new ControleurMenuPlateauJeu(this.instanceDePartie, null, "Symbole");
						break;
					case "Proposer annoncer carte" :
						if(this.instanceDePartie.getJoueurActif() instanceof JoueurConcret)
						{
							int choixAnnonce = this.afficherPropositionAnnoncerCarte();
							new ControleurMenuPlateauJeu(this.instanceDePartie, ((choixAnnonce + 1) + ""), "Annonce carte");
						}
						else
							new ControleurMenuPlateauJeu(this.instanceDePartie, null, "Annonce carte");
						break;
					case "Annonce carte effectuée" :
						this.signalerAnnonceCarte();
						break;
					case "Carte demandée" :
						//TODO
						break;
					case "Terminer partie" :
						this.annoncerFinDePartie();
						this.ouvrirMenuPrincipal();
						break;
				}
			}
		}
	}
}
