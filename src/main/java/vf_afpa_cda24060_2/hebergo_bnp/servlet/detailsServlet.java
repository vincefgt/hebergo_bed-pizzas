package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vf_afpa_cda24060_2.hebergo_bnp.dao.EstateDao;
import vf_afpa_cda24060_2.hebergo_bnp.dao.RentsDAO;
import vf_afpa_cda24060_2.hebergo_bnp.dao.userDAO;
import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;
import vf_afpa_cda24060_2.hebergo_bnp.model.Rents;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "detailsServlet", value = "/detailsServlet")
public class detailsServlet extends HttpServlet {

    private EstateDao estateDao;
    private userDAO userDAO;
    private RentsDAO rentsDAO;

    @Resource(name= "jdbc/MyDataSource")
    private DataSource ds;

    @Override
    public void init() {
        estateDao = new EstateDao();
        try {
            userDAO = new userDAO();
        } catch (SQLException e) {
            System.out.println("Erreur dans instance userDAO dans ServletDetails"+e.getMessage());
        }
        try {
            rentsDAO = new RentsDAO();
        } catch (SQLException e) {
            System.out.println("Erreur dans instance rentsDAO dans ServletDetails"+e.getMessage());
        }

        // Charger la clé Stripe depuis le fichier de configuration
        loadStripeApiKey();
    }

    /**
     * Charge la clé API Stripe depuis le fichier stripe.properties
     */
    private void loadStripeApiKey() {
        java.util.Properties props = new java.util.Properties();
        String configPath = getServletContext().getRealPath("/") + "stripe.properties";

        try (java.io.FileInputStream fis = new java.io.FileInputStream(configPath)) {
            props.load(fis);
            String secretKey = props.getProperty("stripe.secret.key");

            if (secretKey == null || secretKey.trim().isEmpty()) {
                System.err.println("Configuration Stripe manquante");
                throw new RuntimeException("Configuration Stripe manquante");
            }

            Stripe.apiKey = secretKey;

            // Stocker la clé publique dans le contexte pour l'utiliser dans les JSP
            String publicKey = props.getProperty("stripe.public.key");
            if (publicKey != null && !publicKey.trim().isEmpty()) {
                getServletContext().setAttribute("stripePublicKey", publicKey);
            }

        } catch (java.io.FileNotFoundException e) {
            System.err.println("Fichier stripe.properties non trouvé : " + configPath);
            throw new RuntimeException("Fichier de configuration Stripe manquant", e);
        } catch (java.io.IOException e) {
            System.err.println("Impossible de lire stripe.properties");
            throw new RuntimeException("Erreur lecture configuration Stripe", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idEstate = Integer.parseInt(request.getParameter("idEstate"));
        Boolean available = true;
        Estate estate = new Estate();
        String success =  request.getParameter("successRents");

        try {
            estate = estateDao.getEstateById(idEstate);
        } catch (NamingException ne) {
            System.out.println("Erreur de récupération getEstateById dans la servlet détails."+ ne.getMessage());
        }

        User user = new User();
        try{
            user = userDAO.findById(estate.getIdUser());
        } catch (SQLException sqle) {
            System.out.println("Erreur findById detailsServlet");
        }

        List<Rents> rentsList = new ArrayList<>();
        try (Connection conn = ds.getConnection()) {
            rentsList = rentsDAO.findByIdEstate(conn, idEstate);
        } catch (SQLException e) {
            System.out.println("Erreur findByIdEstate detailsServlet");
        }

        request.setAttribute("available", available);
        request.setAttribute("rentsList", rentsList);
        request.setAttribute("estate", estate);
        request.setAttribute("user", user);
        request.setAttribute("successRents", success);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // ACTION 1 : Créer un PaymentIntent (appel AJAX avant paiement)
        if ("create-payment-intent".equals(action)) {
            handleCreatePaymentIntent(request, response);
            return;
        }

        // ACTION 2 : Enregistrer la réservation (APRÈS paiement réussi)
        if ("save-booking".equals(action)) {
            handleSaveBooking(request, response);
            return;
        }

        // Action inconnue
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"unknown action\"}");
    }

    /**
     * Crée un PaymentIntent Stripe et retourne le clientSecret
     */
    private void handleCreatePaymentIntent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String amountStr = request.getParameter("amount");

        if (amountStr == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"missing amount\"}");
            return;
        }

        long amount;
        try {
            amount = Long.parseLong(amountStr);
        } catch (NumberFormatException nfe) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\":\"invalid amount\"}");
            return;
        }

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency("eur")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            try (PrintWriter out = response.getWriter()) {
                out.write("{\"clientSecret\":\"" + intent.getClientSecret() + "\"}");
            }
        } catch (StripeException se) {
            System.out.println("Erreur Stripe: " + se.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"stripe_error: " + se.getMessage() + "\"}");
        }
    }

    /**
     * Enregistre la réservation en BDD (appelé APRÈS confirmation du paiement)
     */
    private void handleSaveBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            LocalDate startRent = LocalDate.parse(request.getParameter("start-rent"));
            LocalDate endRent = LocalDate.parse(request.getParameter("end-rent"));
            Integer idEstate = Integer.parseInt(request.getParameter("id-estate"));
            Integer idUser = Integer.parseInt(request.getParameter("id-user"));
            Double totalPrice = Double.valueOf(request.getParameter("total-price"));
            String paymentIntentId = request.getParameter("payment-intent-id"); // ID du paiement Stripe

            System.out.println("Enregistrement réservation - Estate: " + idEstate + ", User: " + idUser + ", Dates: " + startRent + " -> " + endRent);

            // Vérifier la disponibilité
            List<Rents> rentsList = new ArrayList<>();
            try (Connection conn = ds.getConnection()) {
                rentsList = rentsDAO.findByIdEstate(conn, idEstate);
            } catch (SQLException e) {
                System.out.println("Erreur findByIdEstate detailsServlet: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"database_error\"}");
                return;
            }

            // Vérification de la disponibilité du bien
            boolean available = true;
            for(Rents rent: rentsList){
                if(endRent.isBefore(rent.getStartRent()) || startRent.isAfter(rent.getEndRent())){
                    available = true;
                } else {
                    available = false;
                    break;
                }
            }

            if(!available){
                System.out.println("Dates non disponibles!");
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"error\":\"dates_not_available\"}");
                return;
            }

            // Enregistrer la réservation
            Estate estate;
            try {
                estate = estateDao.getEstateById(idEstate);
            } catch (NamingException e) {
                System.out.println("Erreur getEstateById: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"estate_not_found\"}");
                return;
            }

            Rents newRent = new Rents(idUser, estate.getIdEstate(), LocalDate.now(), startRent, endRent, totalPrice, paymentIntentId != null ? paymentIntentId : "stripe_payment");

            try(Connection conn = ds.getConnection()){
                rentsDAO.create(conn, newRent);
                System.out.println("Réservation enregistrée avec succès!");

                // Retourner succès en JSON
                response.getWriter().write("{\"success\":true,\"message\":\"Réservation enregistrée\",\"idEstate\":" + idEstate + "}");

            } catch (SQLException sqle) {
                System.out.println("Erreur createRents: " + sqle.getMessage());
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\":\"save_failed\"}");
            }

        } catch (Exception e) {
            System.out.println("Erreur handleSaveBooking: " + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"server_error: " + e.getMessage() + "\"}");
        }

    }



    @Override
    public void destroy() {

    }
}