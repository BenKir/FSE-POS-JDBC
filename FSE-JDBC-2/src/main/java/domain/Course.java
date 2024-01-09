package domain;

import java.sql.Date;

/**
 * Diese Klasse repräsentiert einen Kurs in der Anwendung.
 * Sie erbt von BaseEntity und enthält verschiedene Attribute, die einen Kurs beschreiben.
 */
public class Course extends BaseEntity{

    private String name;    //Name des Kurses
    private String description; // Beschreibung des Kurses
    private int hours; // Anzahl der Stunden
    private Date beginDate; //Anfangsdatum
    private Date endDate; //Enddatum
    private CourseType courseType;  // Typ des Kurses, repräsentiert durch das Enum 'CourseType'.

    // Getter für den Namen des Kurses.
    public String getName() {
        return name;
    }

    /**
     * Konstruktor für die Klasse Course mit ID.
     * Erzeugt einen neuen Kurs mit einer gegebenen ID und weiteren Attributen.
     * @param id Die eindeutige ID des Kurses.
     * @param name Der Name des Kurses.
     * @param description Die Beschreibung des Kurses.
     * @param hours Die Anzahl der Stunden des Kurses.
     * @param beginDate Das Anfangsdatum des Kurses.
     * @param endDate Das Enddatum des Kurses.
     * @param courseType Der Typ des Kurses.
     * @throws InvalidValueException Wenn die Werte ungültig sind.
     */
    public Course(Long id, String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType) throws InvalidValueException{
        super(id);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(courseType);
    }

    /**
     * Konstruktor für die Klasse Course ohne ID.
     * Erzeugt einen neuen Kurs ohne eine explizite ID, die anderen Attribute sind wie im anderen Konstruktor.
     * @throws InvalidValueException Wenn die Werte ungültig sind.
     */
    public Course(String name, String description, int hours, Date beginDate, Date endDate, CourseType courseType) throws InvalidValueException{
        super(null);
        this.setName(name);
        this.setDescription(description);
        this.setHours(hours);
        this.setBeginDate(beginDate);
        this.setEndDate(endDate);
        this.setCourseType(courseType);
    }

    /**
     * Setzt den Namen des Kurses.
     * Überprüft, ob der Name gültig ist (nicht null und länger als 1 Zeichen).
     * @param name Der Name des Kurses.
     * @throws InvalidValueException Wenn der Name ungültig ist.
     */
    public void setName(String name) throws InvalidValueException {
        if (name != null && name.length() > 1) {
            this.name = name;
        } else {
            throw new InvalidValueException("Kursname muss mindestens 2 Zeichen lang sein!");
        }
    }

    // Getter für die Beschreibung des Kurses.
    public String getDescription() {
        return description;
    }

    /**
     * Setzt die Beschreibung des Kurses.
     * Überprüft, ob die Beschreibung gültig ist (nicht null und länger als 10 Zeichen).
     * @param description Die Beschreibung des Kurses.
     * @throws InvalidValueException Wenn die Beschreibung ungültig ist.
     */
    public void setDescription(String description) throws InvalidValueException {
        if (description != null && description.length() > 1) {
            this.description = description;
        } else {
            throw new InvalidValueException("Kursbeschreibung muss mindestens 10 Zeichen lang sein!");
        }
    }

    // Getter für die Anzahl der Stunden des Kurses.
    public int getHours() {
        return hours;
    }

    /**
     * Setzt die Anzahl der Stunden des Kurses.
     * Überprüft, ob die Stundenanzahl gültig ist (zwischen 1 und 10).
     * @param hours Die Anzahl der Stunden des Kurses.
     * @throws InvalidValueException Wenn die Stundenanzahl ungültig ist.
     */
    public void setHours(int hours) throws InvalidValueException {
        if (hours > 0 && hours < 10) {
            this.hours = hours;
        } else {
            throw new InvalidValueException("Anzahl der Kursstunden pro Kurs darf nur zwischen 1 und 10 liegen!");
        }

    }

    // Getter für das Anfangsdatum des Kurses.
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * Setzt das Anfangsdatum des Kurses.
     * Überprüft, ob das Datum gültig ist (nicht null und vor dem Enddatum, falls vorhanden).
     * @param beginDate Das Anfangsdatum des Kurses.
     * @throws InvalidValueException Wenn das Startdatum ungültig ist.
     */
    public void setBeginDate(Date beginDate) throws InvalidValueException {
        if (beginDate != null) {
            if (this.endDate != null) {
                if (beginDate.before(this.endDate)) {
                    this.beginDate = beginDate;
                } else {
                    throw new InvalidValueException("Kursbeginn muss VOR Kursende sein!");
                }
            } else {
                this.beginDate = beginDate;
            }
        } else {
            throw new InvalidValueException("Startdatum darf nicht null / leer sein!");
        }
    }

    /**
     * Getter für das Enddatum des Kurses.
     * @return Das Enddatum des Kurses.
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Setzt das Enddatum des Kurses.
     * Überprüft, ob das Datum gültig ist (nicht null und nach dem Anfangsdatum, falls vorhanden).
     * @param endDate Das Enddatum des Kurses.
     * @throws InvalidValueException Wenn das Enddatum ungültig ist.
     */
    public void setEndDate(Date endDate) throws InvalidValueException {
        if (endDate != null) {
            if (this.beginDate != null) {
                if (endDate.after(this.beginDate)) {
                    this.endDate = endDate;
                } else {
                    throw new InvalidValueException("Kursende muss Nach Kursbeginn sein!");
                }
            } else {
                this.endDate = endDate;
            }
        } else {
            throw new InvalidValueException("Enddatum darf nicht null / leer sein!");
        }
    }

    /**
     * Getter für den Kurstyp.
     * @return Der Typ des Kurses.
     */
    public CourseType getCourseType() {
        return courseType;
    }

    /**
     * Setzt den Typ des Kurses.
     * Überprüft, ob der Kurstyp gültig ist (nicht null).
     * @param courseType Der Typ des Kurses.
     * @throws InvalidValueException Wenn der Kurstyp ungültig ist.
     */
    public void setCourseType(CourseType courseType) throws InvalidValueException {
        if (courseType != null)
        {
            this.courseType = courseType;
        }
        else
        {
            throw new InvalidValueException("Kurstyp darf nicht null / leer sein!");
        }
    }

    /**
     * Gibt eine String-Repräsentation des Kurses zurück.
     * @return Eine String-Darstellung, die alle Eigenschaften des Kurses enthält.
     */
    @Override
    public String toString() {
        return "Course{" +
                "id=" + this.getId() + '\'' +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", hours=" + hours +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", courseType=" + courseType +
                '}';
    }
}
