package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vf_afpa_cda24060_2.hebergo_bnp.dao.EstateDao;
import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet(name = "estate_servlet",value = "/EstateServlet")

//we are using this annotation to tell the servlet that files will be posted
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EstateServlet extends HttpServlet {
    private EstateDao estateDao;
    //private DataSource dataSource;
    @Override
    public void init(){
        estateDao = new EstateDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Estate> estatesList;
        String action;
        try {
            action = request.getParameter("action");
            List<Estate> estates = estateDao.getAllEstates();
            request.setAttribute("list",estates);
            switch(action){
                case "add":
                    request.getRequestDispatcher("/WEB-INF/jsp/add-estate.jsp").forward(request, response);
                    break;
                case "delete":
                   int id = Integer.parseInt(request.getParameter("id"));
                   estateDao.deleteEstate(id);
                   response.sendRedirect("estates");
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
                    estates = estateDao.getAllEstates();
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
            // get params from the form values in jsp
            String idStr = request.getParameter("idEstate");
            String name = request.getParameter("nameEstate");
            String description = request.getParameter("description");
            double price =Double.parseDouble(request.getParameter("dailyPrice"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            int idAddress = Integer.parseInt(request.getParameter("idAddress"));
            int idAdmin = Integer.parseInt(request.getParameter("idAdmin"));

            // =============== File Upload Logic ==============
            // the object filePart has the details of the file
            Part filePart = request.getPart("photoFile");
            String fileName = null;
            String relativePath = null;

            if (filePart != null && filePart.getSize() > 0) {
                String originalName = filePart.getSubmittedFileName();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                fileName = name.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + extension;

                //upload file
                String uploadPath = getServletContext().getRealPath("/asset/images");
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) uploadDir.mkdir();

                filePart.write(uploadPath + File.separator + fileName);

                relativePath = "asset/images/" + fileName;
            }

            // create new estate
            Estate estate = new Estate();
            estate.setNameEstate(name);
            estate.setDescription(description);
            estate.setDailyPrice(price);
            //HttpSession session = request.getSession();
            //User user = (User) session.getAttribute("user");
            //estate.setIdUser(user.getIdUser());
            estate.setIdUser(idUser);
            estate.setIdAddress(idAddress);
            estate.setIdAdmin(idAdmin);

            if(relativePath != null){
                estate.setPhotoEstate(relativePath);
            }

            // to decide Add or Update
            if (idStr != null && !idStr.isEmpty()) {
                estate.setIdEstate(Integer.parseInt(idStr));
                estateDao.updateEstate(estate);
            } else {
                estateDao.addEstate(estate);
            }
            response.sendRedirect("index.jsp");

        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving estate: " + e.getMessage());
        }
    }
}
