package _mvc_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsqu'une mauvaise saisie est effectuée
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantChoiceException extends GameException
{
	/**
	 * Crée une nouvelle instance de UncompliantChoiceException
	 * @param message Message d'erreur associé à l'exception
	 */
	public UncompliantChoiceException(String message) 
	{
		super(message);
	}
}
