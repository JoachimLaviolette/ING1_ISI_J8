package _gui_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsque le nombre de joueurs spécifié est insuffisant
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnsufficientNumberOfPlayersException extends SettingsException
{
	/**
	 * Crée une nouvelle instance de UnsufficientNumberOfPlayersException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UnsufficientNumberOfPlayersException(String message) 
	{
		super(message);
	}
}
