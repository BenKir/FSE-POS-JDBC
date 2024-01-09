package dataaccess;

import domain.Course;
import domain.CourseType;
import java.sql.Date;
import java.util.List;

/**
 * Interface für das Kurs-Repository, das das BaseRepository für Kurse erweitert.
 * Es definiert zusätzliche Methoden speziell für das Arbeiten mit Kursen.
 */
public interface MyCourseRepository extends BaseRepository<Course,Long>
{
    /**
     * Findet alle Kurse basierend auf einem gegebenen Namen.
     * @param name Der Name der Kurse, nach denen gesucht wird.
     * @return Eine Liste von Kursen, die dem angegebenen Namen entsprechen.
     */
    List<Course> findAllCoursesByName(String name);


    /**
     * Findet alle Kurse basierend auf einer gegebenen Beschreibung.
     * @param description Die Beschreibung der Kurse, nach denen gesucht wird.
     * @return Eine Liste von Kursen, die der angegebenen Beschreibung entsprechen.
     */
    List<Course> findAllCoursesByDescription(String description);


    /**
     * Findet alle Kurse, deren Name oder Beschreibung einen bestimmten Suchtext enthalten.
     * @param searchText Der Text, nach dem in Namen und Beschreibungen der Kurse gesucht wird.
     * @return Eine Liste von Kursen, die dem Suchtext entsprechen.
     */
    List<Course> findAllCoursesByNameOrDescription(String searchText);


    /**
     * Findet alle Kurse eines bestimmten Kurstyps.
     * @param courseType Der Kurstyp, nach dem gesucht wird.
     * @return Eine Liste von Kursen des angegebenen Kurstyps.
     */
    List<Course> findAllCoursesByCourseType(CourseType courseType);


    /**
     * Findet alle Kurse, die zu einem bestimmten Startdatum beginnen.
     * @param startDate Das Startdatum, nach dem gesucht wird.
     * @return Eine Liste von Kursen, die zu diesem Datum beginnen.
     */
    List<Course> findAllCoursesByStartDate(Date startDate);


    /**
     * Findet alle aktuell laufenden Kurse.
     * @return Eine Liste von Kursen, die aktuell laufen.
     */
    List<Course> findAllRunningCourses();
}
