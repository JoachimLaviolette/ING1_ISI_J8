package _gui_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsque qu'on tente d'acc�der � une carte inexistante
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UnexistingCardException extends GameException
{
	/**
	 * Cr�e une nouvelle instance de UnexistingCardException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UnexistingCardException(String message) 
	{
		super(message);
	}
}
