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

            case "create":
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

            case "findById":
                Integer idAddressToFind = Integer.parseInt(request.getParameter("id-address-toFind"));
                Integer idCityToFind = Integer.parseInt(request.getParameter("id-city-toFind"));

                Addresses addressToFind = new Addresses();
                Cities cityToFind = new Cities();

                try (Connection conn = ds.getConnection()) {
                    addressToFind = addressesDAO.findById(conn, idAddressToFind);
                } catch (SQLException sqle) {
                    System.out.println("Erreur find address addressesCitiesServlet :" + sqle);
                }
                try (Connection conn = ds.getConnection()) {
                    cityToFind = citiesDAO.findById(conn, idCityToFind);
                } catch (SQLException sqle) {
                    System.out.println("Erreur find city addressesCitiesServlet :" + sqle);
                }
                request.setAttribute("addressToFind", addressToFind);
                request.setAttribute("cityToFind", cityToFind);
                request.getRequestDispatcher("/WEB-INF/jsp/manageAddressesCities.jsp").forward(request, response);

            case "modify":
                Integer idAddressM = Integer.parseInt(request.getParameter("id-address-m"));
                String numberStreetM = request.getParameter("number-street-m");
                Integer idCityM = Integer.parseInt(request.getParameter("id-city-m"));
                Addresses addressM = new Addresses(idAddressM,numberStreetM, idCityM);

                Integer idCityToModify = Integer.parseInt(request.getParameter("id-city-toModify"));
                String labelCityM = request.getParameter("label-city-m");
                Integer zipCodeM = Integer.parseInt(request.getParameter("zip-code-m"));
                Cities cityM;
                try {
                    cityM = new Cities(idCityToModify, labelCityM, zipCodeM);
                } catch (SaisieException e) {
                    throw new RuntimeException(e);
                }

                try(Connection conn = ds.getConnection()){
                    addressesDAO.update(conn, addressM);
                }catch (SQLException sqle){
                    System.out.println("Erreur modify address addressesCitiesServlet :" + sqle);
                }
                try(Connection conn = ds.getConnection()){
                    citiesDAO.update(conn, cityM);
                }catch (SQLException sqle){
                    System.out.println("Erreur modify city addressesCitiesServlet :" + sqle);
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