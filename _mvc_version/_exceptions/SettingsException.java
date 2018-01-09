package _mvc_version._exceptions;

/**
 * Classe repr�sentant les exceptions li�es aux param�tres de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class SettingsException extends Throwable
{
	/**
	 * Cr�e une nouvelle instance de SettingsException
	 * @param message Message d'erreur associ� � l'exception
	 * @param e Exception � laquelle chainer cette exception 
	 */
	public SettingsException(String message, Throwable e) 
	{
		super(message);
	}
	
	/**
	 * Cr�e une nouvelle instance de SettingsException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public SettingsException(String message) 
	{
		super(message);
	}
}
