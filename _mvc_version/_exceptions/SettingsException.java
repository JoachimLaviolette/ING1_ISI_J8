package _mvc_version._exceptions;

/**
 * Classe représentant les exceptions liées aux paramètres de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class SettingsException extends Throwable
{
	/**
	 * Crée une nouvelle instance de SettingsException
	 * @param message Message d'erreur associé à l'exception
	 * @param e Exception à laquelle chainer cette exception 
	 */
	public SettingsException(String message, Throwable e) 
	{
		super(message);
	}
	
	/**
	 * Crée une nouvelle instance de SettingsException
	 * @param message Message d'erreur associé à l'exception
	 */
	public SettingsException(String message) 
	{
		super(message);
	}
}
