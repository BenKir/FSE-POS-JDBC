package dataaccess;

/**
 * Benutzerdefinierte Ausnahme für Fehler, die bei Datenbankoperationen auftreten.
 * Erbt von RuntimeException, was bedeutet, dass es sich um eine unchecked Exception handelt.
 */
public class DatabaseException extends RuntimeException
{
    /**
     * Konstruktor für DatabaseException.
     * Nimmt eine Nachricht an, die die Ursache der Ausnahme beschreibt.
     * @param message Die Fehlermeldung, die die Details des Fehlers angibt.
     */
    public DatabaseException(String message)
    {
        super(message);
    }
}
