package _gui_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsque qu'on tente d'accéder à une carte inexistante
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnexistingCardException extends GameException
{
	/**
	 * Crée une nouvelle instance de UnexistingCardException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UnexistingCardException(String message) 
	{
		super(message);
	}
}
