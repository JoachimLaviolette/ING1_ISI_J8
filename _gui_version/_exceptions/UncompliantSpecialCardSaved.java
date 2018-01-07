package _gui_version._exceptions;

/**
 * Classe représentant les exceptions levées lorsqu'une carte non-spéciale a été enregistrée
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantSpecialCardSaved extends GameException
{
	/**
	 * Crée une nouvelle instance de UncompliantSpecialCardSaved
	 * @param message Message d'erreur associé à l'exception
	 */
	public UncompliantSpecialCardSaved(String message) 
	{
		super(message);
	}
}
