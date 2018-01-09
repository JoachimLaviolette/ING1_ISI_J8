package _mvc_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsqu'une carte inexistante est jou�e
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnexistingCardPlayedException extends GameException
{
	/**
	 * Cr�e une nouvelle instance de UnexistingCardPlayedException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UnexistingCardPlayedException(String message) 
	{
		super(message);
	}
}
