package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vf_afpa_cda24060_2.hebergo_bnp.dao.RentsDAO;
import vf_afpa_cda24060_2.hebergo_bnp.dao.RolesDAO;
import vf_afpa_cda24060_2.hebergo_bnp.model.Roles;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "rolesServlet", value = "/rolesServlet")
public class rolesServlet extends HttpServlet {

    private RolesDAO rolesDAO;

    @Resource(name="jdbc/MyDataSource")
    private DataSource ds;

    @Override
    public void init() {
        try{
            rolesDAO = new RolesDAO();
        } catch (SQLException sqle) {
            System.out.println("Erreur création instance rolesDAO" + sqle.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        displayRoles(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void displayRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Roles> rolesList = List.of();
        try(Connection conn = ds.getConnection()){
            rolesList = rolesDAO.findAll(conn);
        } catch (SQLException e) {
            System.out.println("Erreur findAll roles Servlet" + e.getMessage());
        }
        request.setAttribute("rolesList", rolesList);
        request.getRequestDispatcher("/WEB-INF/jsp/manageRoles.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}