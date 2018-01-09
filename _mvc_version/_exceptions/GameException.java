package _mvc_version._exceptions;

/**
 * Classe repr�sentant les exceptions de jeu
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class GameException extends Throwable
{
	/**
	 * Cr�e une nouvelle instance de GameException
	 * @param message Message d'erreur associ� � l'exception
	 * @param e Exception � laquelle chainer cette exception 
	 */
	public GameException(String message, Throwable e) 
	{
		super(message);
	}
	
	/**
	 * Cr�e une nouvelle instance de GameException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public GameException(String message) 
	{
		super(message);
	}
}