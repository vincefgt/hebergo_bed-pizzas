package controller;

import dao.UserDao;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "allNamesServlet", value = "/names")
public class UserNamesServlet extends HttpServlet {
    private UserDao daoUsers;

    //we inject the resources from app server (context.xml configuration)
    @Resource(name = "jdbc/MyDataSource")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        // init dao when server starts
        daoUsers = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> names = null;
        try {
            //get connection to database
            names = daoUsers.getNames(dataSource.getConnection());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //send names list to JSP (names.jsp)
        request.setAttribute("names", names);
        this.getServletContext().getRequestDispatcher("/names.jsp").forward(request, response);
    }

}
