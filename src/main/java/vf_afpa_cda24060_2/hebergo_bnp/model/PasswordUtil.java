package vf_afpa_cda24060_2.hebergo_bnp.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for password hashing using Argon2id algorithm.
 * Argon2id is the recommended password hashing algorithm as of 2025.
 */
public class PasswordUtil {
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
     * Hash a password using Argon2id algorithm.
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
            String hash = argon2.hash(ITERATIONS, MEMORY, PARALLELISM, password.toCharArray());
            logger.debug("Password hashed successfully");
            return hash;
        } catch (Exception e) {
            logger.error("Error hashing password", e);
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    /**
     * Verify a password against a hash.
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
            boolean matches = argon2.verify(hash, password.toCharArray());
            logger.debug("Password verification: {}", matches ? "success" : "failed");
            return matches;
        } catch (Exception e) {
            logger.error("Error verifying password", e);
            return false;
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
}