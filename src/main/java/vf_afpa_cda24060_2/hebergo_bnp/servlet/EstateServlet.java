package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vf_afpa_cda24060_2.hebergo_bnp.dao.AddressesDAO;
import vf_afpa_cda24060_2.hebergo_bnp.dao.CitiesDAO;
import vf_afpa_cda24060_2.hebergo_bnp.dao.EstateDao;
import vf_afpa_cda24060_2.hebergo_bnp.model.Addresses;
import vf_afpa_cda24060_2.hebergo_bnp.model.Cities;
import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "estate_servlet", value = "/EstateServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EstateServlet extends HttpServlet {

    @Resource(name = "jdbc/MyDataSource") // Fixed: Injecting the resource correctly
    private DataSource dataSource;

    private EstateDao estateDao;

    @Override
    public void init() throws ServletException {
        // Initialize DAO once
        estateDao = new EstateDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            HttpSession session = request.getSession(false);
            List<Estate> estatesList;

            switch (action) {
                case "add":
                    request.getRequestDispatcher("/WEB-INF/jsp/add-estate.jsp").forward(request, response);
                    break;
                case "delete":
                    int id = Integer.parseInt(request.getParameter("id"));
                    estateDao.deleteEstate(id);
                    response.sendRedirect("EstateServlet?action=estate");
                    break;
                case "carrousel":
                    estatesList = estateDao.getAllEstates();
                    request.setAttribute("estatesList", estatesList);
                    request.getRequestDispatcher("/accueil.jsp").forward(request, response);
                    break;
                case "estate":
                    estatesList = estateDao.getAllEstates();
                    request.setAttribute("list", estatesList);
                    request.getRequestDispatcher("/WEB-INF/jsp/estates.jsp").forward(request, response);
                    break;
                case "hostList":
                    if (session != null && session.getAttribute("user") != null) {
                        estatesList = estateDao.findEstateByHost((User) session.getAttribute("user"));
                        request.setAttribute("estatesList", estatesList);
                    }
                    request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);
                    break;
                case "searchEstate":
                    id = Integer.parseInt(request.getParameter("idEstate"));
                    estateDao.getEstateById(id);
                    break;
                default:
                    estatesList = estateDao.getAllEstates();
                    request.setAttribute("list", estatesList);
                    request.getRequestDispatcher("/WEB-INF/jsp/estates.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("login_users.jsp");
                return;
            }
            User user = (User) session.getAttribute("user");

            // Get params from the form values
            String idStr = request.getParameter("idEstate");
            String name = request.getParameter("nameEstate");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("dailyPrice"));
            int idUser = user.getIdUser();

            String city = request.getParameter("city");
            String address = request.getParameter("address");
            int zip = Integer.parseInt(request.getParameter("zip"));

            // File Upload Logic
            Part filePart = request.getPart("photoFile");
            String fileName = null;
            String relativePath = null;

            if (filePart != null && filePart.getSize() > 0) {
                String originalName = filePart.getSubmittedFileName();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                fileName = name.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + extension;

                String uploadPath = getServletContext().getRealPath("/asset/images");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();

                filePart.write(uploadPath + File.separator + fileName);
                relativePath = "asset/images/" + fileName;
            }

            // DB Operation with single Connection and Transaction
            try (Connection connection = dataSource.getConnection()) {
                connection.setAutoCommit(false); // start transaction

                try {
                    // Create and Get city
                    Cities myCity = new Cities();
                    myCity.setLabelCity(city);
                    myCity.setZipCode(zip);
                    CitiesDAO citiesDAO = new CitiesDAO();
                    citiesDAO.create(connection, myCity); // Ensure this method accepts Connection

                    // Create and Get address
                    Addresses myAddress = new Addresses();
                    myAddress.setNumberStreet(address);
                    myAddress.setIdCity(myCity.getIdCity());
                    AddressesDAO addressesDAO = new AddressesDAO();
                    addressesDAO.create(connection, myAddress); // Ensure this method accepts Connection

                    // create estate object
                    Estate estate = new Estate();
                    estate.setNameEstate(name);
                    estate.setDescription(description);
                    estate.setDailyPrice(price);
                    estate.setIdUser(idUser);
                    estate.setIdAddress(myAddress.getIdAddress());
                    estate.setValid(true); // Default to true or based on your logic
                    estate.setIdAdmin(1); //temporary because the DB doesnt allow idAdmin to be null

                    if (relativePath != null) {
                        estate.setPhotoEstate(relativePath);
                    }

                    // Add or Update, not in use right now
                    if (idStr != null && !idStr.isEmpty()) {
                        estate.setIdEstate(Integer.parseInt(idStr));
                        estateDao.updateEstate(connection, estate);
                    } else {
                        estateDao.addEstate(connection, estate);
                    }

                    connection.commit(); // Commit all changes
                } catch (Exception e) {
                    connection.rollback(); // Rollback if any part fails
                    throw e;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error: " + e.getMessage());
                return;
            }

            response.sendRedirect("index.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving estate: " + e.getMessage());
        }
    }
}