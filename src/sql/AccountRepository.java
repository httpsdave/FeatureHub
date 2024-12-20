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
    public boolean isUsernameExisting(String username) 
    {
        String query = "SELECT 1 FROM Accounts WHERE username = ?";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // If a result is returned, the username exists
            return resultSet.next();

        } catch (SQLException e) {
            System.err.println("Error checking username existence: " + e.getMessage());
            return false; // Return false or handle this appropriately
        }
    }
    public void signup(
            String firstname,
            String lastname,
            String username,
            String password,
            String role) 
    {
        String hashedPassword = PasswordHasher.hashPassword(password);

        // SQL query to insert a new account
        String query = "INSERT INTO Accounts (firstname, lastname, username, hashed_password, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Set the values for the prepared statement
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, username);
            statement.setString(4, hashedPassword);
            statement.setString(5, role);

            // Execute the update
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Signup successful! User has been added.");
            } else {
                System.err.println("Signup failed! No rows were inserted.");
            }

        } catch (SQLException e) {
            // Handle exceptions
            System.err.println("Error executing signup: " + e.getMessage());
        }
    }
}
