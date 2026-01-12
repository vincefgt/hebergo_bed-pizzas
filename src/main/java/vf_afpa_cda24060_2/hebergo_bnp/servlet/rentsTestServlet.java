package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vf_afpa_cda24060_2.hebergo_bnp.dao.RentsDAO;
import vf_afpa_cda24060_2.hebergo_bnp.model.Rents;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "rentsTestServlet", value = "/rentsTestServlet")
public class rentsTestServlet extends HttpServlet {

    private RentsDAO rentsDAO;

    @Resource(name="jdbc/MyDataSource")
    private DataSource ds;

    @Override
    public void init() {
        try{
            rentsDAO = new RentsDAO();
        }catch(SQLException sqle){
            System.out.println("Erreur création instance rentsDAO" + sqle.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rents> rentsList = List.of();
        try(Connection conn = ds.getConnection()){
            rentsList = rentsDAO.findAll(conn);
        }catch(SQLException sqle){
            System.out.println("Erreur connection rentsDAO: " + sqle.getMessage());
        }

        request.setAttribute("rentsList", rentsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/rentsTest.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void destroy() {

    }
}