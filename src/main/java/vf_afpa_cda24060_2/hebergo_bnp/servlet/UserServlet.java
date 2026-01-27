package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vf_afpa_cda24060_2.hebergo_bnp.dao.userDAO;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.management.DynamicMBean;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "UserServlet", value = "/user-servlet")
public class UserServlet extends HttpServlet {

    private userDAO userDAO;
    @Resource(name = "jdbc/MyDataSource")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        try {
            userDAO = new userDAO();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur création instance userDAO" + e);
        }
    }
    // Afficher le formulaire de création
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String actionUser = request.getParameter("actionUser");
        HttpSession session = request.getSession(false); // update user in session scope
        String urlReturn = "/index.jsp"; // url to return to home page
        if (actionUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response);  // action par défaut
            return;
        }
        try {
            switch (actionUser) {
                case "paramUser": // ---------------------------- PARAM -------------------------
                    request.getRequestDispatcher("/EstateServlet?action=estate").include(request, response);
                    request.getRequestDispatcher("/EstateServlet?action=hostList").include(request, response);
                    request.setAttribute("listUsers", userDAO.findAll());
                    request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);
                    break;
                case "signup": // ---------------------------- SIGN IN -------------------------
                    request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response); // otherwise Forward to login page
                    break;
                case "login": // ------------------- LOG IN ------------------------------------
                    // Check if user is already logged in
                    if (session != null && session.getAttribute("user") != null) {
                        response.sendRedirect(request.getContextPath() + urlReturn); // return to landing page
                        return;}
                    request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response); // otherwise Forward to login page
                    break;
                case "logout": // ---------------------- LOG OUT -------------------------------
                    session = request.getSession(false);
                    if (session != null && session.getAttribute("user") != null) {
                        session.invalidate();
                        response.sendRedirect(request.getContextPath() + urlReturn); // return to landing page
                        return;}
                    break;
                case "searchUser":
                    User user = (User) session.getAttribute("user");
                    userDAO.findById(user.getIdUser());
                    break;
                case "allUsers":
                    request.setAttribute("listUsers", userDAO.findAll());
                    break;
                case "researchUser":
                    String userIdParam = request.getParameter("idUser");
                    if (userIdParam == null || userIdParam.trim().isEmpty()) {
                        request.setAttribute("errorMessage", "ID utilisateur manquant");
                        request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);
                        return;
                    }
                    try {
                        int userId = Integer.parseInt(userIdParam);
                        User foundUser = userDAO.findById(userId);

                        if (foundUser != null) {
                            request.setAttribute("foundUser", foundUser);
                            request.setAttribute("successMessage", "Utilisateur trouvé : " + foundUser.getFirstname() + " " + foundUser.getLastname());
                        } else {
                            request.setAttribute("errorMessage", "Aucun utilisateur trouvé avec l'ID : " + userId);
                        }
                        // Recharger les listes complètes
                        // Rediriger vers la page param avec l'onglet admin actif
                        request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);

                    } catch (NumberFormatException e) {
                        request.setAttribute("errorMessage", "Format d'ID invalide");
                        request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);}
                    break;
                case "deleteUser":
                    userDAO.deleteById(Integer.parseInt(request.getParameter("idUser")));
                    break;
                default:
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //variable global both signup /login share
        String adUrl = "/index.jsp"; // index for reload carrousel
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String actionUser = request.getParameter("actionUser");
        switch (actionUser) {
            case "signup":    //--------------------- SIGN IN ---------------------
                request.setCharacterEncoding("UTF-8");
                // Récupérer les paramètres du formulaire
                String firstname = request.getParameter("firstname");
                String lastname = request.getParameter("lastname");
                String phone = request.getParameter("phone");
                email = request.getParameter("email");
                password = request.getParameter("password");
                String confirmPassword = request.getParameter("confirmPassword");
                String idAddressStr = request.getParameter("idAddress");
                String idRoleStr = request.getParameter("idRole");
                String idAdminStr = request.getParameter("idAdmin");

                // Validation
                StringBuilder errors = new StringBuilder();
                if (firstname == null || firstname.trim().isEmpty()) {
                    errors.append("Le prénom est obligatoire. ");}
                if (lastname == null || lastname.trim().isEmpty()) {
                    errors.append("Le nom est obligatoire. ");}
                if (phone == null || !phone.matches("\\d{10}")) {
                    errors.append("Le téléphone doit contenir exactement 10 chiffres. ");}
                if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    errors.append("L'email n'est pas valide. ");}
                //TODO redefine password at 8 lenght
                if (password == null || password.length() < 1) {
                    errors.append("Le mot de passe doit contenir au moins 8 caractères. ");}
                if (!password.equals(confirmPassword)) {
                    errors.append("Les mots de passe ne correspondent pas. ");}

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
                        request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response);
                        return;
                    }

                    // Créer l'utilisateur
                    try {
                        User user = new User(); //model
                        user.setFirstname(firstname.trim());
                        user.setLastname(lastname.trim());
                        user.setPhone(phone.trim());
                        user.setEmail(email.trim());
                        user.setPasswordHash(vf_afpa_cda24060_2.hebergo_bnp.util.PasswordUtil.hashPassword(password)); // Hash password w Argon2.id
                        if (idAddressStr != null && !idAddressStr.trim().isEmpty()) {
                            user.setIdAddress(Integer.parseInt(idAddressStr));}
                        if (idRoleStr != null && !idRoleStr.trim().isEmpty()) {
                            user.setIdRole(Integer.parseInt(idRoleStr));}
                        if (idAdminStr != null && !idAdminStr.trim().isEmpty()) {
                            user.setIdAdmin(Integer.parseInt(idAdminStr));}
                        user.setDeleted(false);

                        User createdUser = userDAO.create(user); //DAO

                        // Succès - rediriger
                        request.getSession().setAttribute("success", "Utilisateur créé avec succès !");
                        response.sendRedirect(request.getContextPath() + adUrl); //TODO link to user account
                        //request.getRequestDispatcher(adUrl).forward(request, response);

                    } catch (SQLException e) {
                        request.setAttribute("error", "Erreur lors de la création de l'utilisateur: " + e.getMessage());
                        e.printStackTrace();
                        request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response);
                    } catch (NumberFormatException e) {
                        request.setAttribute("error", "Format de nombre invalide.");
                        request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response);
                    }
                break;
            case "login": // ---------------------- LOG IN -----------------------------
                // Get form parameters
                 email = request.getParameter("email");
                 password = request.getParameter("password");
                 String remember = request.getParameter("remember");
                // Validate input
                if (email == null || email.trim().isEmpty()) {
                    request.setAttribute("error", "Le nom d'utilisateur est requis");
                    request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response);
                    return;
                }
                if (password == null || password.isEmpty()) {
                    request.setAttribute("error", "Le mot de passe est requis");
                    request.getRequestDispatcher("/WEB-INF/jsp/signup_users.jsp").forward(request, response);
                    return;
                }
                try { // Try to find by email
                    User user = userDAO.findByEmail(email.trim());
                    if (user == null) {
                        //getLogger.warn("Login attempt with non-existent username: {}", email); //TODO logger
                        request.setAttribute("error", "Nom d'utilisateur inconnu");
                        request.setAttribute("username", email);
                        request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response); // stay on log in page
                        return;}
                    // Verify password using Argon2id
                    boolean isPasswordValid = vf_afpa_cda24060_2.hebergo_bnp.util.PasswordUtil.verifyPassword(user.getPasswordHash(), password);
                    if (!isPasswordValid) {
                        //logger.warn("Failed login attempt for user: {}", email); //TODO logger
                        request.setAttribute("error", "Mot de passe incorrect");
                        request.setAttribute("username", email);
                        request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response); //TODO info use to index page
                        return;}
                    // Check if user account is active
                    if (user.getIsDeleted()) {
                        // logger.warn("Login attempt for inactive account: {}", email); //TODO logger
                        request.setAttribute("error", "Votre compte est désactivé. Veuillez contacter l'administrateur.");
                        request.getRequestDispatcher("/WEB-INF/jsp/login_users.jsp").forward(request, response);
                        return;}
                    // Successful login - create session
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    session.setMaxInactiveInterval(30 * 60); // Set session timeout (30 minutes) without remenber me
                    if ("on".equals(remember)) {  // "Remember Me" functionality
                        session.setMaxInactiveInterval(7 * 24 * 60 * 60);} // 7 days
                    //log connection user
                    //userDAO.updateLastLogin(user.getId());   // Update last login timestamp
                    //logger.info("User logged in successfully: {}", username);
                    request.setAttribute("success", "Welcome back! " + user.getFirstname());
                    response.sendRedirect("index.jsp");
                    /*
                    // Redirect based on user role
                    String redirectUrl = getRedirectUrl(user, request);
                    response.sendRedirect(redirectUrl);
                    */
                } catch (Exception e) {
                    //logger.error("Error during login process", e);
                    request.setAttribute("error", "Une erreur s'est produite lors de la connexion. Veuillez réessayer.");
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                break;
            case "update": //--------------------------------- update user -------------------------------
                request.setCharacterEncoding("UTF-8");
                // Récupérer les paramètres du formulaire
                firstname = request.getParameter("firstname");
                lastname = request.getParameter("lastname");
                phone = request.getParameter("phone");
                email = request.getParameter("email");
                password = request.getParameter("password");
                String newPassword = request.getParameter("newPassword");
                String confirmNewPassword = request.getParameter("confirmNewPassword");
                // Validation
                errors = new StringBuilder();
                if (firstname == null || firstname.trim().isEmpty()) {
                    errors.append("Le prénom est obligatoire. ");}
                if (lastname == null || lastname.trim().isEmpty()) {
                    errors.append("Le nom est obligatoire. ");}
                if (phone == null || !phone.matches("\\d{10}")) {
                    errors.append("Le téléphone doit contenir exactement 10 chiffres. ");}
                if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                    errors.append("L'email n'est pas valide. ");}
                if (newPassword == null || newPassword.length() < 8) {
                    errors.append("Le mot de passe doit contenir au moins 8 caractères. ");}
                if (!newPassword.equals(confirmNewPassword)) {
                    errors.append("Les mots de passe ne correspondent pas. ");}
                // Vérifier les doublons
                try {
                    if (userDAO.emailExists(email)) {
                        errors.append("Cet email est déjà utilisé. ");}
                    if (userDAO.phoneExists(phone)) {
                        errors.append("Ce numéro de téléphone est déjà utilisé. ");}
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
                    request.setAttribute("password", password);
                    //request.getRequestDispatcher("/public/signup_users.jsp").forward(request, response);
                    return;}

                // Update l'utilisateur
                try {
                    User updateUser = new User(); //model
                    updateUser.setFirstname(firstname.trim());
                    updateUser.setLastname(lastname.trim());
                    updateUser.setPhone(phone.trim());
                    updateUser.setEmail(email.trim());
                    updateUser.setPasswordHash(newPassword);

                    userDAO.update(updateUser); // Call DAO update BDD
                    HttpSession session = request.getSession(true); // update user in session scope
                    session.setAttribute("user", updateUser);
                    // Succès - rediriger
                    request.getSession().setAttribute("success", "Utilisateur mis à jour avec succès !");
                    response.sendRedirect(request.getContextPath() + "/WEB-INF/jsp/login_users.jsp");

                } catch (SQLException e) {
                    request.setAttribute("error", "Erreur lors de la création de l'utilisateur: " + e.getMessage());
                    e.printStackTrace();
                    request.getRequestDispatcher("/public/param_users.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Format de nombre invalide.");
                    request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);
                }
                break;
            default:
                break;
        }
    }

    /**
     * Determine redirect URL based on user role
     */
    private String getRedirectUrl(User user, HttpServletRequest request) {
        String contextPath = request.getContextPath();

        // Check if there's a redirect URL in session (from protected page access attempt)
        HttpSession session = request.getSession(false);
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                session.removeAttribute("redirectUrl");
                return redirectUrl;
            }
        }

        // Default redirect based on role
        switch (user.getIdRole()) {
            case 1:
                return contextPath + "/admin/dashboard";
            case 2:
                return contextPath + "/manager/dashboard";
            case 3:
            default:
                return contextPath + "/index";
        }
    }

    @Override
    public void destroy() {
        try {
            if (dataSource.getConnection() != null) {
                dataSource.getConnection().close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}