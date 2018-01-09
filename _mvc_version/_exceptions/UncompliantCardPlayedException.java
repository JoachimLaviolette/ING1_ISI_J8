package _mvc_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsqu'une mauvaise carte est jouée
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantCardPlayedException extends GameException
{
	/**
	 * Crée une nouvelle instance de UncompliantCardPlayedException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UncompliantCardPlayedException(String message) 
	{
		super(message);
	}
}
