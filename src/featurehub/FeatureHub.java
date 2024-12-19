
package featurehub;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class FeatureHub {

    
    public static void main(String[] args) {
        // Create and display the LoginFrame
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create the connection
            String url = "jdbc:sqlite:src/sql/featurehubdb.sqlite3";
            Connection conn = DriverManager.getConnection(url);

            if (conn != null) {
                System.out.println("Connection established successfully!");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        
        LoginFrame loginFrame = new LoginFrame();
        loginFrame.setVisible(true);
    }
    
}
