package dataaccess;

import domain.Course;
import java.util.List;
import java.util.Optional;

/**
 * Generisches Interface für CRUD-Operationen in einem Repository.
 * @param <T> Der Typ der Entität.
 * @param <I> Der Typ des Identifikators (z.B. Long für IDs).
 */

public interface BaseRepository<T,I>
{
    /**
     * Fügt eine neue Entität in das Repository ein.
     * @param entity Die zu speichernde Entität.
     * @return Ein Optional, das die eingefügte Entität enthält, falls erfolgreich.
     */
    Optional<T> insert(T entity);

    /**
     * Findet eine Entität anhand ihrer ID.
     * @param id Die ID der Entität.
     * @return Ein Optional, das die gefundene Entität enthält, falls vorhanden.
     */
    Optional<Course> getById(I id);

    /**
     * Gibt eine Liste aller Entitäten im Repository zurück.
     * @return Eine Liste aller Entitäten.
     */
    List<T> getAll();

    /**
     * Aktualisiert eine bestehende Entität im Repository.
     * @param entity Die zu aktualisierende Entität.
     * @return Ein Optional, das die aktualisierte Entität enthält, falls erfolgreich.
     */
    Optional<T> update(T entity);

    /**
     * Löscht eine Entität anhand ihrer ID.
     * @param id Die ID der zu löschenden Entität.
     * @return True, wenn die Entität erfolgreich gelöscht wurde, sonst False.
     */
    boolean deleteById(I id);
}
