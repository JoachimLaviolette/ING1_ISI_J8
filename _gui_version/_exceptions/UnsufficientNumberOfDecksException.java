package _gui_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsque le nombre de jeux de cartes est insuffisant par rapport au nombre de joueurs
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnsufficientNumberOfDecksException extends SettingsException
{
	/**
	 * Cr�e une nouvelle instance de UnsufficientNumberOfDecksException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UnsufficientNumberOfDecksException(String message) 
	{
		super(message);
	}
}
