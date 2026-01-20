package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vf_afpa_cda24060_2.hebergo_bnp.dao.EstateDao;
import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "estates",value = "/estates")

//we are using this annotation to tell the servlet that files will be posted
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EstateServlet extends HttpServlet {
    private EstateDao estateDao;
    private DataSource dataSource;
    @Override
    public void init(){
        estateDao = new EstateDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EstateDao estateDao = new EstateDao();
        List<Estate> estatesList;
        String action = request.getParameter("action");

        try {
            String action = request.getParameter("action");
            List<Estate> estates = estateDao.getAllEstates();
            request.setAttribute("list",estates);
            switch(action){
                case "delete":
                   int id = Integer.parseInt(request.getParameter("id"));
                   estateDao.deleteEstate(id);
                   response.sendRedirect("estates");
                   return;
                   break;
                case "carrousel":
                    estateDao = new EstateDao();
                    estatesList = estateDao.getAllEstates();

                    request.setAttribute("estatesList", estatesList);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/accueil.jsp");
                    dispatcher.forward(request,response);
                    break;
                case "estate":
                    estateDao = new EstateDao();
                    estatesList = estateDao.getAllEstates();
                    request.getRequestDispatcher("/WEB-INF/jsp/estates.jsp").forward(request,response);
                    break;
                case "hostList":
                    estateDao = new EstateDao();
                    HttpSession session = request.getSession(false); // update user in session scope
                    estatesList = estateDao.findEstateByHost((User) session.getAttribute("user"));
                    request.setAttribute("estatesList", estatesList);
                    
                    request.getRequestDispatcher("/WEB-INF/jsp/param_users.jsp").forward(request, response);
                    break;
                default:
                     // List all estates
                    List<Estate> estates = estateDao.getAllEstates();
                    request.setAttribute("list", estates);
                    request.getRequestDispatcher("/WEB-INF/jsp/estates.jsp").forward(request, response);
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // get params
            String idStr = request.getParameter("idEstate");
            String name = request.getParameter("nameEstate");
            String description = request.getParameter("description");
            double price =Double.parseDouble(request.getParameter("dailyPrice"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            int idAddress = Integer.parseInt(request.getParameter("idAddress"));
            int idAdmin = Integer.parseInt(request.getParameter("idAdmin"));

            // =============== File Upload Logic ==============
            // the object filePart has the details of the file
            jakarta.servlet.http.Part filePart = request.getPart("photoFile");
            //giving the file a unique name
            String fileName = request.getParameter("nameEstate") + "_" + System.currentTimeMillis();

            String uploadPath = getServletContext().getRealPath("") + //generates the webapp path dynamically
                    "asset" +
                    java.io.File.separator + // it means add / or \ depending on the system
                    "images";

            //transmit the file data from the request
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // save file in the dynamic path
            String fullPath = uploadPath + java.io.File.separator + fileName;
            filePart.write(fullPath);

            //full image path
            String relativePath = "asset/images/" + fileName;

            Estate estate = new Estate();
            estate.setNameEstate(name);
            estate.setDescription(description);
            estate.setDailyPrice(price);
            estate.setIdUser(idUser);
            estate.setIdAddress(idAddress);
            estate.setIdAdmin(idAdmin);
            estate.setPhotoEstate(relativePath); // save image path in DB

            // to decide Add or Update
            if (idStr != null && !idStr.isEmpty()) {
                estate.setIdEstate(Integer.parseInt(idStr));
                estateDao.updateEstate(estate);
            } else {
                estateDao.addEstate(estate);
            }
            response.sendRedirect("estates");
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving estate: " + e.getMessage());
        }
    }

}
