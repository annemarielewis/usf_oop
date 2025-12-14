import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// Sample code from https://github.com/xerial/sqlite-jdbc?tab=readme-ov-file#download
// Code adapted for CS514: Lab 7

public class SQLSample {
    public static void main(String[] args) {
        // NOTE: Connection and Statement are AutoCloseable.
        //       Don't forget to close them both in order to avoid leaks.
        // TRY-WITH-RESOURCES: create a database connection
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:wildlife_tracker.db");
                Statement statement = connection.createStatement();
                ) {
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from species");
            while(rs.next()) {
                // read the result set
                System.out.println("id = " + rs.getInt("species_id"));
                System.out.println("name = " + rs.getString("common_name"));
            }

            // Use executeUpdate for INSERT, UPDATE, DELETE
            // We'll add one more for test purposes
            // ('Sumatran Rhino', 'Dicerorhinus sumatrensis', 'Critically Endangered', 'Rainforest', 80);
            statement.executeUpdate("INSERT into species values('16','Sumatran Rhino','Dicerorhinus sumatrensis','Critically Endangered','Rainforest',80)");

            //let's print this out again
            rs = statement.executeQuery("select * from species");
            while(rs.next()) {
                // read the second result set
                System.out.println("id = " + rs.getInt("species_id"));
                System.out.println("name = " + rs.getString("common_name"));
            }
        } catch(SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace(System.err);
        }
    }
}