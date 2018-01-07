package _gui_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsque le nombre de joueurs sp�cifi� est insuffisant
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnsufficientNumberOfPlayersException extends SettingsException
{
	/**
	 * Cr�e une nouvelle instance de UnsufficientNumberOfPlayersException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UnsufficientNumberOfPlayersException(String message) 
	{
		super(message);
	}
}
