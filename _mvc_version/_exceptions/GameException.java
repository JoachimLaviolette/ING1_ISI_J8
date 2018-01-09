package _mvc_version._exceptions;

/**
 * Classe représentant les exceptions de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class GameException extends Throwable
{
	/**
	 * Crée une nouvelle instance de GameException
	 * @param message Message d'erreur associé à l'exception
	 * @param e Exception à laquelle chainer cette exception 
	 */
	public GameException(String message, Throwable e) 
	{
		super(message);
	}
	
	/**
	 * Crée une nouvelle instance de GameException
	 * @param message Message d'erreur associé à l'exception
	 */
	public GameException(String message) 
	{
		super(message);
	}
}