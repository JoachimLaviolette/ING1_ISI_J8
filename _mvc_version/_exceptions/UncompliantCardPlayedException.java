package _mvc_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsqu'une mauvaise carte est jou�e
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantCardPlayedException extends GameException
{
	/**
	 * Cr�e une nouvelle instance de UncompliantCardPlayedException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UncompliantCardPlayedException(String message) 
	{
		super(message);
	}
}
