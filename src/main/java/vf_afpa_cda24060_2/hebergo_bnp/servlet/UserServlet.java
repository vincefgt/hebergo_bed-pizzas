/*package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vf_afpa_cda24060_2.hebergo_bnp.dao.userDAO_vince;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "UserServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {

    private userDAO_vince userDAO;
   private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            //Connection connection = DataSource.getConnection();
            userDAO = new userDAO_vince(DataSource);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }

    // Afficher le formulaire de création
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/user.jsp").forward(request, response);
    }

    // Traiter la soumission du formulaire
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Récupérer les paramètres du formulaire
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String idAddressStr = request.getParameter("idAddress");
        String idRoleStr = request.getParameter("idRole");
        String idAdminStr = request.getParameter("idAdmin");

        // Validation
        StringBuilder errors = new StringBuilder();

        if (firstname == null || firstname.trim().isEmpty()) {
            errors.append("Le prénom est obligatoire. ");
        }

        if (lastname == null || lastname.trim().isEmpty()) {
            errors.append("Le nom est obligatoire. ");
        }

        if (phone == null || !phone.matches("\\d{10}")) {
            errors.append("Le téléphone doit contenir exactement 10 chiffres. ");
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.append("L'email n'est pas valide. ");
        }

        if (password == null || password.length() < 8) {
            errors.append("Le mot de passe doit contenir au moins 8 caractères. ");
        }

        if (!password.equals(confirmPassword)) {
            errors.append("Les mots de passe ne correspondent pas. ");
        }

        if (idAddressStr == null || idAddressStr.trim().isEmpty()) {
            errors.append("L'adresse est obligatoire. ");
        }

        // Vérifier les doublons
        try {
            if (userDAO.emailExists(email)) {
                errors.append("Cet email est déjà utilisé. ");
            }

            if (userDAO.phoneExists(phone)) {
                errors.append("Ce numéro de téléphone est déjà utilisé. ");
            }
        } catch (SQLException e) {
            errors.append("Erreur lors de la vérification des doublons. ");
            e.printStackTrace();
        }

        // Si erreurs, retourner au formulaire
        if (errors.length() > 0) {
            request.setAttribute("error", errors.toString());
            request.setAttribute("firstname", firstname);
            request.setAttribute("lastname", lastname);
            request.setAttribute("phone", phone);
            request.setAttribute("email", email);
            request.setAttribute("idAddress", idAddressStr);
            request.setAttribute("idRole", idRoleStr);
            request.setAttribute("idAdmin", idAdminStr);
            request.getRequestDispatcher("/WEB-INF/views/user-create.jsp").forward(request, response);
            return;
        }

        // Créer l'utilisateur
        try {
            User user = new User();
            user.setFirstname(firstname.trim());
            user.setLastname(lastname.trim());
            user.setPhone(phone.trim());
            user.setEmail(email.trim());
            user.setPasswordHash(hashPassword(password));
            user.setIdAddress(Integer.parseInt(idAddressStr));

            if (idRoleStr != null && !idRoleStr.trim().isEmpty()) {
                user.setIdRole(Integer.parseInt(idRoleStr));
            }

            if (idAdminStr != null && !idAdminStr.trim().isEmpty()) {
                user.setIdAdmin(Integer.parseInt(idAdminStr));
            }

            user.setDeleted(false);

            User createdUser = userDAO.create(user);

            // Succès - rediriger
            request.getSession().setAttribute("success", "Utilisateur créé avec succès !");
            response.sendRedirect(request.getContextPath() + "/user/list");

        } catch (SQLException e) {
            request.setAttribute("error", "Erreur lors de la création de l'utilisateur: " + e.getMessage());
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/views/user-create.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Format de nombre invalide.");
            request.getRequestDispatcher("/WEB-INF/views/user-create.jsp").forward(request, response);
        }
    }

    // Hash le mot de passe avec SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erreur lors du hashage du mot de passe", e);
        }
    }

    @Override
    public void destroy() {
        try {
            if (DatabaseConnection.getConnection() != null) {
                DatabaseConnection.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}*/