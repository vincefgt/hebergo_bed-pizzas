package vf_afpa_cda24060_2.hebergo_bnp.model;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import static vf_afpa_cda24060_2.hebergo_bnp.logger.MyLogger.getLOGGER;


/**
 * Utility class for password hashing using Argon2id algorithm.
 * Argon2id is the recommended password hashing algorithm as of 2025.
 * This implementation includes pepper for additional security.
 */
public class PasswordUtil {
    // pepper integration
    private static String PEPPER = "";
    /**
     * Bloc static pour charger les fichier de config
     * et récuperer le PEPPER
     */
    static {
        // recupération du poivre
        Properties properties = new Properties();
        try (InputStream input = PasswordUtil.class.getClassLoader().getResourceAsStream("conf.properties")) {
            if (input == null) {
                getLOGGER().error("ERROR : conf.properties introuvable");
                throw new IllegalStateException("conf.properties introuvable");
            }
            properties.load(input);
            PEPPER = properties.getProperty("password.pepper");
            if (PEPPER == null) {
                getLOGGER().error("Pepper n'est pas défini dans la config");
                throw new IllegalStateException("Pepper n'est pas défini dans la config");
            }
        } catch (IOException ex) {
            getLOGGER().error("Error loading conf.properties: {}", ex.getMessage());
            throw new IllegalStateException("Error loading conf.properties", ex);
        }
    }

    private static final Logger logger = LogManager.getLogger(PasswordUtil.class);
    // Argon2 parameters (can be adjusted based on security requirements)
    private static final int ITERATIONS = 2;        // Number of iterations
    private static final int MEMORY = 65536;        // Memory cost in KB (64 MB)
    private static final int PARALLELISM = 1;       // Number of parallel threads

    // Create Argon2 instance (Argon2id variant)
    private static final Argon2 argon2 = Argon2Factory.create(
            Argon2Factory.Argon2Types.ARGON2id,
            32,  // Salt length
            64   // Hash length
    );

    /**
     * Extract salt from encrypted password
     * @param encryptedPassword The encrypted password hash
     * @return The extracted salt
     */
    private static String extractSalt(String encryptedPassword) {
        // echappement du dollar sinon regex
        String[] parts = encryptedPassword.split("\\$"); //  reminder format BDD : $argon2id$v=19$m=65536,t=3,p=4$<salt>$<hash>
        // si le split donne moins de 3 éléments alors problème
        if (parts.length < 4) {
            getLOGGER().error("Hash seems malformed [hash={}] [parts={}]", encryptedPassword, Arrays.toString(parts));
            throw new IllegalArgumentException("Malformed encrypted password.");
        }
        return parts[3]; // retourne le sel
    }

    /**
     * Hash a password using Argon2id algorithm with pepper.
     * The pepper is concatenated with the password before hashing for additional security.
     *
     * @param password The plain text password to hash
     * @return The hashed password as a string
     * @throws IllegalArgumentException if password is null or empty
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        try {
            //  reminder format BDD : $argon2id$v=19$m=65536,t=3,p=4$<salt>$<hash>
            // Combine password with pepper before hashing
            String pepperedPassword = password + PEPPER;
            String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, pepperedPassword.toCharArray());
            logger.debug("Password hashed successfully with pepper");
            return hash;
        } catch (Exception e) {
            logger.error("Error hashing password", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    /**
     * Verify a password against a hash.
     * The pepper is applied to the password before verification.
     *
     * @param hash The stored hash to verify against
     * @param password The plain text password to verify
     * @return true if the password matches the hash, false otherwise
     * @throws IllegalArgumentException if hash or password is null or empty
     */
    public static boolean verifyPassword(String hash, String password) {
        if (hash == null || hash.isEmpty()) {
            throw new IllegalArgumentException("Hash cannot be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            // Combine password with pepper before verification
            String pepperedPassword = password + PEPPER;
            boolean matches = argon2.verify(hash, pepperedPassword.toCharArray());
            logger.debug("Password verification with pepper: {}", matches ? "success" : "failed");
            return matches;
        } catch (Exception e) {
            logger.error("Error verifying password", e);
            return false;
        }
    }

    /**
     * Wipe the Argon2 instance (call this on application shutdown).
     * This clears any sensitive data from memory.
     */
    public static void cleanup() {
        try {
            argon2.wipeArray(new char[0]); // Wipe any internal arrays
            logger.info("Argon2 cleanup completed");
        } catch (Exception e) {
            logger.error("Error during Argon2 cleanup", e);
        }
    }

    /**
     * Check if a hash needs to be rehashed with updated parameters.
     * This is useful for upgrading hashes when security parameters change.
     *
     * @param hash The hash to check
     * @return true if the hash needs rehashing, false otherwise
     */
    public static boolean needsRehash(String hash) {
        if (hash == null || hash.isEmpty()) {
            return true;
        }
        try {
            // The Argon2 library doesn't provide a direct needsRehash method,
            // but you can implement custom logic here based on your requirements
            // For example, checking if the hash uses old parameters
            return false;
        } catch (Exception e) {
            logger.error("Error checking if hash needs rehash", e);
            return true;
        }
    }
}