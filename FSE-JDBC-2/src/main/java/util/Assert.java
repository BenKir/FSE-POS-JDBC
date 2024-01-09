package util;

/**
 * Eine Utility-Klasse, die häufige Überprüfungen (Assertions) bereitstellt.
 */
public class Assert
{
    /**
     * Überprüft, ob ein gegebenes Objekt nicht null ist.
     * Diese Methode wird typischerweise verwendet, um sicherzustellen, dass Parameter, die an Methoden übergeben werden,
     * nicht null sind, was helfen kann, Fehler frühzeitig zu erkennen und NullPointerExceptions zu vermeiden.
     *
     * @param o Das zu überprüfende Objekt.
     * @throws IllegalArgumentException wenn das übergebene Objekt null ist.
     */
    public static void notNull(Object o)
    {
        if(o==null) throw new IllegalArgumentException("Reference must not be null");
    }
}
