package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vf_afpa_cda24060_2.hebergo_bnp.dao.AddressesDAO;
import vf_afpa_cda24060_2.hebergo_bnp.dao.CitiesDAO;
import vf_afpa_cda24060_2.hebergo_bnp.exception.SaisieException;
import vf_afpa_cda24060_2.hebergo_bnp.model.Addresses;
import vf_afpa_cda24060_2.hebergo_bnp.model.Cities;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AddressesCitiesServlet", value = "/AddressesCitiesServlet")
public class AddressesCitiesServlet extends HttpServlet {

    private AddressesDAO addressesDAO;
    private CitiesDAO citiesDAO;

    @Resource(name="jdbc/MyDataSource")
    private DataSource ds;

    @Override
    public void init() {
        try{
            addressesDAO = new AddressesDAO();
        }catch(Exception sqle){
            System.out.println("Erreur création instance addressesDAO :" + sqle);
        }

        try{
            citiesDAO = new CitiesDAO();
        }catch(Exception sqle){
            System.out.println("Erreur création instance citiesDAO :" + sqle);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        displayArdesses(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch(action) {

            case "create":v
                String numberStreet = request.getParameter("number-street");
                Integer idCity = Integer.parseInt(request.getParameter("id-city"));
                Addresses address = new Addresses(numberStreet, idCity);

                String labelCity = request.getParameter("label-city");
                Integer zipCode = Integer.parseInt(request.getParameter("zip-code"));
                Cities city = new Cities();
                try {
                    city = new Cities(labelCity, zipCode);
                } catch (SaisieException e) {
                    System.out.println("Erreur create instance city addressesCitiesServlet :" + e);
                }

                try(Connection conn = ds.getConnection()){
                    addressesDAO.create(conn, address);
                    citiesDAO.create(conn, city);
                }catch(SQLException sqle){
                    System.out.println("Erreur create addressses");
                }
                break;
            default:
                break;
        }
        displayArdesses(request, response);
    }

    private void displayArdesses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Addresses> addressesList = null;
        List< Cities> citiesList = null;
        try(Connection conn = ds.getConnection()){
            addressesList = addressesDAO.findAll(conn);
            citiesList = citiesDAO.findAll(conn);
        }catch(SQLException sqle){
            System.out.println("Erreur findAll addressesCitiesServlet :" + sqle);
        }
        request.setAttribute("addressesList", addressesList);
        request.setAttribute("citiesList", citiesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/manageAddressesCities.jsp");
        dispatcher.forward(request, response);
    }
    @Override
    public void destroy() {

    }
}