package _mvc_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsqu'une mauvaise saisie est effectu�e
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantChoiceException extends GameException
{
	/**
	 * Cr�e une nouvelle instance de UncompliantChoiceException
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UncompliantChoiceException(String message) 
	{
		super(message);
	}
}
