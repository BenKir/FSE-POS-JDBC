package domain;

/**
 * Benutzerdefinierte Ausnahme für ungültige Werte in der Anwendung.
 * Erbt von RuntimeException, was bedeutet, dass es sich um eine unchecked Exception handelt.
 * Diese Art von Ausnahme wird typischerweise verwendet, um auf ungültige Zustände hinzuweisen,
 * die durch fehlerhafte Benutzereingaben oder falsche Daten verursacht werden können.
 */
public class InvalidValueException extends RuntimeException
{
    /**
     * Konstruktor für InvalidValueException.
     * Nimmt eine Nachricht an, die die Ursache der Ausnahme beschreibt.
     * @param message Die Fehlermeldung, die die Details des Fehlers angibt.
     */
    public InvalidValueException(String message)
    {
        super(message);
    }
}
