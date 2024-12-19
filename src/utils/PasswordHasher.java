package utils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHasher {
     /**
     * Hashes a password using MD5 (Note: MD5 is used for educational purposes only)
     * @param password The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String password) {
        try {
            // Get instance of MD5 hashing algorithm
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Convert password to bytes and generate hash
            byte[] messageDigest = md.digest(password.getBytes());
            
            // Convert byte array to signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            
            // Convert to hex value
            String hashtext = no.toString(16);
            
            // Add preceding zeros if necessary
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Checks if a password matches its hash
     * @param password The password to check
     * @param storedHash The stored hash to check against
     * @return true if password matches, false otherwise
     */
    public static boolean checkPassword(String password, String storedHash) {
        String newHash = hashPassword(password);
        return newHash.equals(storedHash);
    }
}
