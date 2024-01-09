package domain;

import javax.management.InvalidAttributeValueException;

/**
 * Eine abstrakte Basis-Klasse für alle Entitäten.
 * Definiert eine gemeinsame Eigenschaft für alle Entitäten, nämlich die ID.
 */
public abstract class BaseEntity {
    private Long id;

    /**
     * Konstruktor für BaseEntity.
     * @param id Die eindeutige ID der Entität. Sollte null oder eine positive Zahl sein.
     */
    public BaseEntity(Long id)
    {
        setId(id);
    }

    /**
     * Getter für die ID der Entität.
     * @return Die ID der Entität.
     */
    public Long getId()
    {
        return this.id;
    }

    /**
     * Setter für die ID der Entität.
     * Überprüft, ob die ID entweder null oder eine positive Zahl ist.
     * @param id Die zu setzende ID.
     * @throws InvalidValueException wenn die ID ungültig ist (kleiner als 0).
     */
    public void setId(Long id)
    {
        if(id==null || id >= 0)
        {
            this.id = id;
        }
        else
        {
            throw new InvalidValueException("Kurs-ID muss groeßer gleich 0 sein!");
        }
    }

    /**
     * Gibt eine String-Repräsentation der Entität zurück.
     * @return Eine String-Darstellung, die die ID der Entität enthält.
     */
    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
