import dataaccess.MySqlCourseRepository;
import dataaccess.MySqlDatabaseConnection;
import ui.Cli;

import java.sql.Connection;
import java.sql.SQLException;

public class main {
    public static void main(String[] args)
    {
        //System.out.println("Test");

        // 'MySqlDatabaseConnection' kann hier nicht instanziiert werden (kein new),
        // da ihr Konstruktor private ist.
        //MySqlDatabaseConnection myConnection = new ..

        try
        {
            // Erstellung eines 'Cli'-Objekts (Command Line Interface), das mit einem 'MySqlCourseRepository' arbeitet.
            // Das 'MySqlCourseRepository' ist verantwortlich für die Interaktion mit der Datenbank.
            Cli myCli = new Cli(new MySqlCourseRepository());

            // Startet das CLI. Dies ist der Haupt-Einstiegspunkt für Benutzerinteraktionen.
            myCli.start();
        }
        catch (SQLException e)
        {
            // Fängt und behandelt SQL-Ausnahmen, die während der Datenbankoperationen auftreten können.
            // Gibt eine Fehlermeldung mit Details zur Ausnahme aus.
            System.out.println("Datenbankfehler:" + e.getMessage() + "SQL State:" + e.getSQLState());
        }
        catch (ClassNotFoundException e)
        {
            // Diese Ausnahme wird geworfen, wenn eine benötigte Datenbanktreiberklasse nicht gefunden wird.
            // Gibt eine Fehlermeldung aus, die auf das Problem mit dem Datenbanktreiber hinweist.
            System.out.println("Datenbankfehler:" + e.getMessage());
        }
    }
}
