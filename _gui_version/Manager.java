package _gui_version;

import java.awt.EventQueue;

import _gui_version.model.*;
import _gui_version.view.*;

/**
 * Classe du manager qui exécute l'application en instanciant l'instance de jeu et les vues concurrentes
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class Manager
{	
	/*
	 * Programme principal 
	 */
	public static void main(String[] args)
	{
		Jeu jeu = new Jeu();
		//GUI
		Runnable codeGUI = new Runnable() {
			public void run() 
			{
				new MenuPrincipal(jeu);
			}
		};
		if(EventQueue.isDispatchThread())
			codeGUI.run();
		else
			EventQueue.invokeLater(codeGUI);
		//Console
		new Thread(() -> 
		{
			new Console(jeu);
		}).start();
	}
}