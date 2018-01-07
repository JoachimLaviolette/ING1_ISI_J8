package _gui_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsque le nombre de jeux de cartes est insuffisant par rapport au nombre de joueurs
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnsufficientNumberOfDecksException extends SettingsException
{
	/**
	 * Crée une nouvelle instance de UnsufficientNumberOfDecksException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UnsufficientNumberOfDecksException(String message) 
	{
		super(message);
	}
}
