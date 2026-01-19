package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                String labelRole = request.getParameter("label-role");
                Roles newRoles = new Roles(labelRole);
                 try(Connection conn = ds.getConnection()){
                     rolesDAO.create(conn, newRoles);
                 }catch (SQLException sqle) {
                     System.out.println("Erreur create rolesServlet: " + sqle.getMessage());
                 }
                 break;

            case "findById":
                Roles roleToFind = new Roles();
                Integer idRoleToFind = Integer.parseInt(request.getParameter("id-role-to-find"));
                try(Connection conn = ds.getConnection()){
                    roleToFind = rolesDAO.findById(conn,idRoleToFind);
                }catch(SQLException sqle){
                    System.out.println("Erreur connection findById servlet rentsDAO: " + sqle.getMessage());
                }
                request.setAttribute("roleToFind", roleToFind);
                request.getRequestDispatcher("/WEB-INF/jsp/manageRoles.jsp");
                break;

            case "modify":
                Integer idRoleToModify =  Integer.parseInt(request.getParameter("id-role-m"));
                String labelRoleM = request.getParameter("label-role-m");
                Roles updateRole = new Roles(idRoleToModify, labelRoleM);

                try(Connection conn = ds.getConnection()){
                    rolesDAO.update(conn, updateRole);
                }catch (SQLException sqle) {
                    System.out.println("Erreur updateRole rolesServlet: " + sqle.getMessage());
                }
                break;
            case "delete":
                Integer idRoleToDelete =  Integer.parseInt(request.getParameter("id-role-d"));

                try(Connection conn = ds.getConnection()){
                    rolesDAO.deleteById(conn, idRoleToDelete);
                } catch (SQLException e) {
                    System.out.println("Erreur delete role rolesServlet" + e.getMessage());
                }
                break;

            default:
                break;
        }

        displayRoles(request, response);
    }

    private void displayRoles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Roles> rolesList = List.of();
        try(Connection conn = ds.getConnection()){
            rolesList = rolesDAO.findAll(conn);
        } catch (SQLException e) {
            System.out.println("Erreur findAll roles Servlet" + e.getMessage());
        }
        request.setAttribute("rolesList", rolesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/manageRoles.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    public void destroy() {

    }
}