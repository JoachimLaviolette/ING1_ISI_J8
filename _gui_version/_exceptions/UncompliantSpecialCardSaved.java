package _gui_version._exceptions;

/**
 * Classe repr�sentant les exceptions lev�es lorsqu'une carte non-sp�ciale a �t� enregistr�e
 * @version 1.0
 * @since 1.0
 * @author Joachim Laviolette
 */
public class UncompliantSpecialCardSaved extends GameException
{
	/**
	 * Cr�e une nouvelle instance de UncompliantSpecialCardSaved
	 * @param message Message d'erreur associ� � l'exception
	 */
	public UncompliantSpecialCardSaved(String message) 
	{
		super(message);
	}
}
