package dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasse für die Verbindung zur MySQL-Datenbank.
 * Verwendet das Singleton-Designmuster, um sicherzustellen, dass nur eine Instanz der Datenbankverbindung existiert.
 */

public class MySqlDatabaseConnection
{
    // Statische Variable für die einzelne Instanz der Datenbankverbindung
    private static Connection con = null;

    /**
     * Privater Konstruktor, um die direkte Instanzierung zu verhindern.
     */
    private MySqlDatabaseConnection() {
    }

    /**
     * Stellt eine Verbindung zur MySQL-Datenbank her oder gibt die bestehende Verbindung zurück.
     * @param url Der URL zur Datenbank.
     * @param user Der Benutzername für die Datenbank.
     * @param pwd Das Passwort für den Datenbankbenutzer.
     * @return Eine bestehende oder neu erstellte Verbindung.
     * @throws ClassNotFoundException wenn der Datenbanktreiber nicht gefunden wird.
     * @throws SQLException bei Fehlern beim Herstellen der Verbindung.
     */
    public static Connection getConnection(String url, String user, String pwd) throws ClassNotFoundException, SQLException {
        if(con!=null)
        {
            //Gibt die bestehende Verbindung zurück, falls vorhanden.
            return con;
        }
        else
        {
            // Lädt den MySQL-Treiber und stellt eine neue Verbindung her.
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,pwd);
            // Gibt die neu hergestellte Verbindung zurück.
            return con;
        }
    }
}
