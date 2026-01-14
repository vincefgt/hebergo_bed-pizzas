package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vf_afpa_cda24060_2.hebergo_bnp.dao.EstateDao;
import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "estates",value = "/estates")
public class EstateServlet extends HttpServlet {
    private EstateDao estateDao;
    private DataSource dataSource;
    @Override
    public void init(){
        estateDao = new EstateDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Estate> estates = estateDao.getAllEstates();
            request.setAttribute("list",estates);
            request.getRequestDispatcher("/WEB-INF/jsp/estates.jsp").forward(request,response);
        }catch (Exception e){
            e.printStackTrace();
            response.getWriter().write("error" + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("nameEstate");
            String description = request.getParameter("description");
            double price =Double.parseDouble(request.getParameter("dailyPrice"));
            int idUser = Integer.parseInt(request.getParameter("idUser"));
            int idAddress = Integer.parseInt(request.getParameter("idAddress"));
            int idAdmin = Integer.parseInt(request.getParameter("idAdmin"));

            Estate estate = new Estate();
            estate.setNameEstate(name);
            estate.setDescription(description);
            estate.setDailyPrice(price);
            estate.setIdUser(idUser);
            estate.setIdAddress(idAddress);
            estate.setIdAdmin(idAdmin);

            estateDao.addEstate(estate);

            response.sendRedirect("estates");

        }catch (Exception e){
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error saving estate: " + e.getMessage());
        }
    }
}
