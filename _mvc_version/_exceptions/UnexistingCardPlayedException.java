package _mvc_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsqu'une carte inexistante est jouée
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnexistingCardPlayedException extends GameException
{
	/**
	 * Crée une nouvelle instance de UnexistingCardPlayedException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UnexistingCardPlayedException(String message) 
	{
		super(message);
	}
}
