package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import utils.PasswordHasher;

public class AccountRepository 
{
    private final String connectionString = "jdbc:sqlite:src/sql/featurehubdb.sqlite3";
    
    public boolean isValidAccount(String username, String password)
    {
        String hashedPassword = PasswordHasher.hashPassword(password);
        
        String query = "SELECT * FROM Accounts WHERE username = ? AND hashed_password = ?";

        try (Connection connection = DriverManager.getConnection(connectionString);
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // If there's a matching user, authentication succeeds

        } 
        catch (SQLException e) {
            System.err.println("Error executing query: " + e.getMessage());
            return false;
        }
    }
}
