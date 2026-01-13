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
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "rentsServlet", value = "/rentsServlet")
public class rentsServlet extends HttpServlet {

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
        displayRents(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch(action) {
            case "create":
                Integer idUser = Integer.parseInt(request.getParameter("id-user"));
                Integer idEstate = Integer.parseInt(request.getParameter("id-estate"));
                LocalDate purchaseDate = LocalDate.parse(request.getParameter("purchase-date"));
                LocalDate startRent =  LocalDate.parse(request.getParameter("start-rent"));
                LocalDate endRent =  LocalDate.parse(request.getParameter("end-rent"));
                Double totalPrice = Double.parseDouble(request.getParameter("total-price"));
                String paymentNumber = request.getParameter("payment-number");
                Rents newRents = new Rents(idUser, idEstate, purchaseDate, startRent, endRent, totalPrice, paymentNumber);

                try(Connection conn = ds.getConnection()){
                    rentsDAO.create(conn, newRents);
                }catch(SQLException sqle){
                    System.out.println("Erreur connection create servlet rentsServlet rentsDAO: " + sqle.getMessage());
                }
                break;
            case "modify":
                Integer idRentsToModify = Integer.parseInt(request.getParameter("id-rents-m"));

                Integer idUserM = Integer.parseInt(request.getParameter("id-user-m"));
                Integer idEstateM = Integer.parseInt(request.getParameter("id-estate-m"));
                LocalDate purchaseDateM = LocalDate.parse(request.getParameter("purchase-date-m"));
                LocalDate startRentM =  LocalDate.parse(request.getParameter("start-rent-m"));
                LocalDate endRentM =  LocalDate.parse(request.getParameter("end-rent-m"));
                Double totalPriceM = Double.parseDouble(request.getParameter("total-price-m"));
                String paymentNumberM = request.getParameter("payment-number-m");
                Rents updateRents = new Rents(idRentsToModify, idUserM, idEstateM, purchaseDateM, startRentM,
                        endRentM, totalPriceM, paymentNumberM);

                try(Connection conn = ds.getConnection()){
                    rentsDAO.update(conn, updateRents);
                }catch(SQLException sqle){
                    System.out.println("Erreur connection modify servlet rentsServlet rentsDAO: " + sqle.getMessage());
                }
                break;
            case "findById":
                Rents rentsToFind = new Rents();
                Integer idRentsToFind = Integer.parseInt(request.getParameter("id-rents-to-find"));

                try(Connection conn = ds.getConnection()){
                    rentsToFind = rentsDAO.findById(conn,idRentsToFind);
                }catch(SQLException sqle){
                    System.out.println("Erreur connection findById servlet rentsDAO: " + sqle.getMessage());
                }
                request.setAttribute("rentsToFind", rentsToFind);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/manageRents.jsp");
                break;
            default:
                break;
        }


        displayRents(request, response);
    }

    private void displayRents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rents> rentsList = List.of();
        try(Connection conn = ds.getConnection()){
            rentsList = rentsDAO.findAll(conn);
        }catch(SQLException sqle){
            System.out.println("Erreur connection rentsDAO: " + sqle.getMessage());
        }

        request.setAttribute("rentsList", rentsList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/manageRents.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {

    }
}