package vf_afpa_cda24060_2.hebergo_bnp.servlet;

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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idEstate = Integer.parseInt(request.getParameter("idEstate"));
        Estate estate = new Estate();
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

        request.setAttribute("estate", estate);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/details.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rents> rentsList = new ArrayList<>();
        LocalDate startRent = LocalDate.parse(request.getParameter("start-rent"));
        LocalDate endRent = LocalDate.parse(request.getParameter("end-rent"));
        Integer idEstate = Integer.parseInt(request.getParameter("id-estate"));

        try (Connection conn = ds.getConnection()) {
            rentsList = rentsDAO.findByIdEstate(conn, idEstate);
        } catch (SQLException e) {
            System.out.println("Erreur findByIdEstate detailsServlet");
        }

        for(Rents rent: rentsList){
            if(endRent.isBefore(rent.getStartRent()) || startRent.isAfter(rent.getEndRent())){
                // enregistre create rents
            }else{
                // renvoyer alerte
            }
        }

    }

    @Override
    public void destroy() {

    }
}