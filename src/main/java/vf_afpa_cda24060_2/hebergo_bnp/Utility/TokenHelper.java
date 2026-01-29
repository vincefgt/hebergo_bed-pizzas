package vf_afpa_cda24060_2.hebergo_bnp.Utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.Random;

public final class TokenHelper {

    /**
     * CSRF_TOKEN_VALUE_NAME.
     */
    public static final String CSRF_TOKEN_VALUE_NAME = "_csrfToken";
    public static final String CSRF_TOKEN_VALUE_NAME_SESSION = "csrfToken_";

    /**
     * CSRF_TOKEN_LENGTH.
     */
    public static final int CSRF_TOKEN_LENGTH = 100;

    private TokenHelper() {
        throw new UnsupportedOperationException(
                "This class is not meant to be instanciated");
    }

    /**
     * méthode générant des token d'une longueur passée en paramètre.
     *
     * @param length : longueur du token
     * @return String : le token
     */
    public static String generateToken(final int length) {
        Random rng = new SecureRandom();

        // variable contenant les caractères autorisés pour la génération
        String charPool = "0123456789"
                + "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // variable de construction
        StringBuilder builder = new StringBuilder();

        // boucle de construction
        for (int i = 0; i < length; i++) {
            // génération d'un nombre aléatoire
            int randIndex = rng.nextInt(charPool.length());
            // ajout du caractere à l'emplacement
            builder.append(charPool.charAt(randIndex));
        }
        // retourne le token
        return builder.toString();
    }

    /**
     * méthode renvoyant un token de 100 caractères. (CSRF_TOKEN_LENGTH = 100)
     * @return String : token
     */
    public static String generateCsrfToken() {
        return generateToken(CSRF_TOKEN_LENGTH);
    }

    public static boolean isValidToken(HttpServletRequest request) {
        // Token envoyé par le formulaire
        String requestToken = request.getParameter(CSRF_TOKEN_VALUE_NAME);
        if (requestToken == null) {
            return false;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }

        // Token stocké en session
        String sessionToken = (String) session.getAttribute(CSRF_TOKEN_VALUE_NAME);
        if (sessionToken == null) {
            return false;
        }

        return sessionToken.equals(requestToken);
    }


}
