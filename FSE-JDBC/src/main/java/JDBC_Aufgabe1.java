import java.sql.*;

public class JDBC_Aufgabe1
{
    public static void main(String[] args) {
        System.out.println("JDBC-Test");
        //INSERT INTO
        // `student` (`id`, `name`, `email`) VALUES (NULL, 'Benjamin Kirschner', 'bkirschner@tsn.at'),(NULL, 'Matthias Leiter', 'mleiter@tsn.at');

        selectAllDemo();
        insertStudentDemo("Name des Studenten", "Email@Prov.at");
        selectAllDemo();
        updateStudentDemo(4,"Neuer Name", "neueemail@provider.at");
        selectAllDemo();
        deleteStudentDemo(10);
        selectAllDemo();
        findAllByNameLike("kirsch");
    }

    private static void findAllByNameLike(String pattern)
    {
        System.out.println("FIND ALL BY NAME");
        //StringselectAllPersonString="SELECT*FROM`student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection conn=DriverManager.getConnection(connectionURL,user,pwd))
        {
            System.out.println("VerbindungzurDatenbankhergestelltJuhuu:-)");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `student` WHERE `student`.`name` LIKE ?");

            preparedStatement.setString(1,"%"+pattern+"%");

            ResultSet res = preparedStatement.executeQuery();

            while (res.next())
            {
                int id = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString("email");

                System.out.println("StundentausderDB[ID:]" + id + "[NAME:]" + name + "[EMAIL:]" + email);
            }
        }
        catch(SQLException e)
        {
            System.out.println("FehlerbeimAufbauderVerbindung!" + e.getMessage());
        }
    }

    public static void deleteStudentDemo(int stundentID)
    {
        System.out.println("DELETE Demo mit JDBC:");
        //String selectAllPersonString = "SELECT * FROM `student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionURL, user, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt Juhuu :-)");

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM student WHERE `student`.`id` = ?"
            );

            try
            {
                preparedStatement.setInt(1,stundentID);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println("Anzahl der geloeschten Datensaetze: " + rowAffected);
                //normalerweise müsste man die verbindung immer closen
                //con.close()
                //in diesem try catch block ist das nicht notwendig,
                //weil wird automatisch geclosed!
            }
            catch (SQLException ex)
            {
                System.out.println("Fehler im SQL Update Statement" + ex.getMessage());
            };
        }
        catch (SQLException e)
        {
            System.out.println("Fehler beim Aufbau der Verbindung!" + e.getMessage());
        };
    };
    public static void updateStudentDemo(int id, String neuerName, String neueEmail)
    {
        System.out.println("UPDATE Demo mit JDBC:");
        //String selectAllPersonString = "SELECT * FROM `student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionURL, user, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt Juhuu :-)");

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "UPDATE `student` SET `name` = ?, `email`= ? WHERE `student`.`id` = ?;"
            );

            try
            {
                preparedStatement.setString(1, neuerName);
                preparedStatement.setString(2, neueEmail);
                preparedStatement.setInt(3,id);
                int affectedRows = preparedStatement.executeUpdate();
                System.out.println("Anzahl der aktualisierten Datensaetze: " +affectedRows);
                //normalerweise müsste man die verbindung immer closen
                //con.close()
                //in diesem try catch block ist das nicht notwendig,
                //weil wird automatisch geclosed!
            }
            catch (SQLException ex)
            {
                System.out.println("Fehler im SQL Update Statement" + ex.getMessage());
            };
        }
        catch (SQLException e)
        {
            System.out.println("Fehler beim Aufbau der Verbindung!" + e.getMessage());
        };
    };

    public static void insertStudentDemo(String name, String email)
    {
        System.out.println("Select Demo mit JDBC:");
        //String selectAllPersonString = "SELECT * FROM `student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection conn = DriverManager.getConnection(connectionURL, user, pwd)) {
            System.out.println("Verbindung zur Datenbank hergestellt Juhuu :-)");

            PreparedStatement preparedStatement = conn.prepareStatement(
                    "INSERT INTO student(`id`, `name`, `email`) VALUES (NULL, ?, ?)");

            try
            {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                int rowAffected = preparedStatement.executeUpdate();
                System.out.println("Datensätze eingefügt: " + rowAffected);
                //normalerweise müsste man die verbindung immer closen
                //con.close()
                //in diesem try catch block ist das nicht notwendig,
                //weil wird automatisch geclosed!
            }
            catch (SQLException ex)
            {
                System.out.println("Fehler im SQL insert Statement" + ex.getMessage());
            };
        }
        catch (SQLException e)
        {
            System.out.println("Fehler beim Aufbau der Verbindung!" + e.getMessage());
        };
    };

    public static void selectAllDemo()
    {
        System.out.println("SelectDemomitJDBC:");
        //StringselectAllPersonString="SELECT*FROM`student`";
        String connectionURL = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection conn=DriverManager.getConnection(connectionURL,user,pwd))
        {
            System.out.println("VerbindungzurDatenbankhergestelltJuhuu:-)");

            PreparedStatement preparedStatement = conn.prepareStatement("SELECT*FROM`student`");

            ResultSet res = preparedStatement.executeQuery();

            while (res.next())
            {
                int id = res.getInt("id");
                String name = res.getString("name");
                String email = res.getString("email");

                System.out.println("StundentausderDB[ID:]" + id + "[NAME:]" + name + "[EMAIL:]" + email);
            }
        }
        catch(SQLException e)
        {
                System.out.println("FehlerbeimAufbauderVerbindung!" + e.getMessage());
        }
    }
}
