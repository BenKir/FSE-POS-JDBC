package dataaccess;

import domain.Course;
import domain.CourseType;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementierung des MyCourseRepository, das speziell für die Interaktion mit einer MySQL-Datenbank konzipiert ist.
 */
public class MySqlCourseRepository implements MyCourseRepository {

    // Die Verbindung zur Datenbank.
    private Connection con;

    /**
     * Konstruktor der MySqlCourseRepository-Klasse.
     * Stellt eine Verbindung zur MySQL-Datenbank her.
     * @throws SQLException bei einem Fehler während der Herstellung der Datenbankverbindung.
     * @throws ClassNotFoundException wenn der JDBC-Treiber nicht gefunden wird.
     */
    public MySqlCourseRepository() throws SQLException, ClassNotFoundException
    {
        // Herstellung der Verbindung zur Datenbank mit den angegebenen Zugangsdaten.
        this.con = MySqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem","root","");
    }

    /**
     * Fügt einen neuen Kurs in die Datenbank ein.
     * Diese Methode bereitet einen SQL-Befehl vor, um die Daten des Kurs-Objekts in die 'courses'-Tabelle einzufügen.
     * Sie führt mehrere Schritte aus:
     * 1. Validierung: Überprüft, ob das übergebene Kurs-Objekt nicht null ist, um die Integrität der Daten zu gewährleisten.
     * 2. SQL-Preparation: Erstellt ein PreparedStatement, um SQL-Injection zu verhindern und fügt Werte des Kurses in den SQL-Befehl ein.
     * 3. Ausführung und Überprüfung: Führt den SQL-Befehl aus und überprüft, ob die Einfügung erfolgreich war.
     *    Wenn keine Zeile betroffen ist, wird ein leeres Optional zurückgegeben.
     * 4. Rückgabe: Bei erfolgreicher Einfügung wird die generierte ID des Kurses abgerufen und der Kurs mit dieser ID zurückgegeben.
     * 5. Fehlerbehandlung: Bei SQL-Fehlern wird eine DatabaseException geworfen, um das Problem zu signalisieren.
     *
     * @param entity Das Kurs-Objekt, das eingefügt werden soll.
     * @return Ein Optional, das den eingefügten Kurs enthält, falls die Einfügung erfolgreich war, andernfalls ein leeres Optional.
     * @throws DatabaseException bei Fehlern während der Ausführung des SQL-Befehls.
     */
    @Override
    public Optional<Course> insert(Course entity)
    {
        //stellt sicher, dass das übergebene Objekt nicht null ist.
        Assert.notNull(entity);

        try
        {
            // SQL-Befehl zum Einfügen eines neuen Kurses in die Datenbank.
            // tipp kopieren aus MySQL-DAtenbank um schreibfehler zu vermeiden!!
            String sql = "INSERT INTO `courses` (`name`, `description`, `hours`, `begindate`, `enddate`, `coursetype`) VALUES (?,?,?,?,?,?)";

            // Vorbereiten des SQL-Befehls und Festlegen der Parameter.
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setInt(3, entity.getHours());
            preparedStatement.setDate(4, entity.getBeginDate());
            preparedStatement.setDate(5, entity.getEndDate());
            preparedStatement.setString(6, entity.getCourseType().toString());

            // Ausführen des SQL-Befehls und Überprüfen, ob Zeilen betroffen sind.
            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows == 0)
            {
                return Optional.empty();
            }

            // Abrufen der generierten Schlüssel, um die ID des neu eingefügten Kurses zu erhalten.
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next())
            {
                // Rückgabe des eingefügten Kurses, basierend auf der generierten ID.
                return this.getById(generatedKeys.getLong(1));
            }
            else
            {
                return Optional.empty();
            }
        }
        catch(SQLException sqlException)
        {
            // Werfen einer benutzerdefinierten Datenbankausnahme bei einem SQL-Fehler.
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    /**
     * Ruft einen Kurs anhand seiner ID aus der Datenbank ab.
     * Diese Methode übernimmt mehrere Schritte:
     * 1. Validierung: Überprüft, ob die übergebene ID nicht null ist.
     * 2. Existenzüberprüfung: Überprüft zuerst, ob ein Kurs mit der angegebenen ID in der Datenbank existiert.
     *    Wenn kein Kurs gefunden wird, gibt die Methode ein leeres Optional zurück.
     * 3. Datenabruf: Wenn ein Kurs vorhanden ist, wird eine SQL-Abfrage durchgeführt, um die Kursdaten zu erhalten.
     * 4. Kursobjekt-Erstellung: Die Daten aus dem ResultSet werden verwendet, um ein neues Course-Objekt zu erstellen.
     * 5. Rückgabe: Gibt ein Optional des Kurses zurück, wenn er gefunden wird.
     * 6. Fehlerbehandlung: Bei SQL-Fehlern wird eine DatabaseException geworfen.
     *
     * @param id Die ID des Kurses, der abgerufen werden soll.
     * @return Ein Optional, das den abgerufenen Kurs enthält, falls er vorhanden ist, sonst ein leeres Optional.
     * @throws DatabaseException bei Fehlern während der Ausführung der SQL-Abfrage.
     */
    @Override
    public Optional<Course> getById(Long id)
    {
        //stellt sicher, dass das übergebene Objekt nicht null ist.
        Assert.notNull(id);

        // Überprüft zuerst, ob ein Kurs mit der angegebenen ID in der Datenbank existiert.
        if(countCoursesInDbWithId(id)==0)
        {
            // Wenn kein Kurs gefunden wird, wird ein leeres Optional zurückgegeben.
            return Optional.empty();
        }
        else
        {
            // Wenn ein Kurs gefunden wird, wird versucht, diesen aus der Datenbank zu lesen.
            try{
                String sql = "SELECT * FROM `courses` WHERE `id`=?";
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1,id);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Verarbeitet das Ergebnis des SQL-Befehls.
                resultSet.next();
                // Erstellt ein Kurs-Objekt aus den Daten der Ergebnismenge.
                Course course = new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                );
                // Gibt ein Optional des Kurses zurück.
                return Optional.of(course);
                //return Optional.of(course.getId());
            }
            catch(SQLException sqlException)
            {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
        //return Optional.empty();
    }

    private int countCoursesInDbWithId(Long id)
    {
        try
        {
            String countSql = "SELECT COUNT(*) FROM `courses` WHERE `id`=?";
            PreparedStatement preparedStatementCount = con.prepareStatement(countSql);
            preparedStatementCount.setLong(1,id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int courseCount = resultSetCount.getInt(1);
            return courseCount;
        }
        catch(SQLException sqlException)
        {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Course> getAll() {
        String sql = "SELECT * FROM courses";
        try
        {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while(resultSet.next())
            {
                courseList.add(new Course(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("hours"),
                        resultSet.getDate("begindate"),
                        resultSet.getDate("enddate"),
                        CourseType.valueOf(resultSet.getString("coursetype"))
                        )
                );
            }
            //System.out.println("XXXX");
            return courseList;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database error occured!");
        }
        //return null;
    }

    @Override
    public Optional<Course> update(Course entity)
    {
        Assert.notNull(entity);

        String sql = "UPDATE `courses` SET `name` = ?, `description` = ?, `hours` = ?, `begindate` = ?, `enddate` = ?, `coursetype` = ? WHERE `courses`.`id` = ?";

        if(countCoursesInDbWithId(entity.getId())==0)
        {
            return Optional.empty();
        }
        else
        {
            try
            {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getName());
                preparedStatement.setString(2, entity.getDescription());
                preparedStatement.setInt(3,entity.getHours());
                preparedStatement.setDate(4,entity.getBeginDate());
                preparedStatement.setDate(5,entity.getEndDate());
                preparedStatement.setString(6,entity.getCourseType().toString());
                preparedStatement.setLong(7,entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if(affectedRows==0)
                {
                    return Optional.empty();
                }
                else
                {
                    return this.getById(entity.getId());
                }
            }
            catch(SQLException sqlException)
            {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public boolean deleteById(Long id)
    {
        Assert.notNull(id);
        String sql = "DELETE FROM `courses` WHERE `id` = ?";

        //hier wäre noch besser einen boolschen wert (ja/nein) zurückzugeben um zu kontrollieren ob ein wert da ist zum löschen

        try
        {
            if(countCoursesInDbWithId(id)==1)
            {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1,id);

                //preparedStatement.executeUpdate();

                int affectedRows = preparedStatement.executeUpdate(); // Gibt die Anzahl der betroffenen Zeilen zurück.

                // Wenn eine Zeile betroffen ist, wurde der Kurs erfolgreich gelöscht.
                return affectedRows > 0;
            }
        }
        catch(SQLException sqlException)
        {
            throw new DatabaseException(sqlException.getMessage());
        }
        return false;
    }

    @Override
    public List<Course> findAllCoursesByName(String name) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByDescription(String description) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByNameOrDescription(String searchText)
    {
        try
        {
            String sql = "SELECT * FROM `courses` WHERE LOWER(`description`) LIKE LOWER(?) OR LOWER(`name`) LIKE LOWER(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1,"%"+searchText+"%");
            preparedStatement.setString(2,"%"+searchText+"%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();
            while(resultSet.next())
            {
                courseList.add(new Course(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getInt("hours"),
                                resultSet.getDate("begindate"),
                                resultSet.getDate("enddate"),
                                CourseType.valueOf(resultSet.getString("coursetype"))
                        )
                );
            }
            return courseList;
        }
        catch(SQLException sqlException)
        {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Course> findAllCoursesByCourseType(CourseType courseType) {
        return null;
    }

    @Override
    public List<Course> findAllCoursesByStartDate(Date startDate) {
        return null;
    }

    @Override
    public List<Course> findAllRunningCourses() {

        String sql = "SELECT * FROM `courses` WHERE NOW()<`enddate`";
        try
        {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Course> courseList = new ArrayList<>();

            while(resultSet.next())
            {
                courseList.add(new Course(
                                resultSet.getLong("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getInt("hours"),
                                resultSet.getDate("begindate"),
                                resultSet.getDate("enddate"),
                                CourseType.valueOf(resultSet.getString("coursetype"))
                        )
                );
            }
            return courseList;
        }
        catch(SQLException sqlException)
        {
            throw new DatabaseException("Datenbankfehler: " +sqlException.getMessage());
        }
    }
}
