package vf_afpa_cda24060_2.hebergo_bnp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import javax.sql.DataSource;
import static java.lang.System.out;

@WebServlet(name = "connectionServlet", value = "/connection-servlet")
    public class ConnectionServlet extends HttpServlet {

    @Resource(name = "jdbc/MyDataSource")
    private DataSource dataSource;

    @Override
    public void init() {
        try {
            Connection connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ServletContext context = getServletContext();
        context.setAttribute("dataSource", dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain;charset=UTF-8");

        try (Connection connection = dataSource.getConnection()) {

            if (connection.isValid(5)) {
                resp.getWriter().println("✅ Connexion à la base de données OK");
                resp.getWriter().println(connection);
            } else {
                resp.getWriter().println("❌ Connexion invalide");
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            resp.getWriter().println("❌ Erreur de connexion");
        }
    }

    @Override
    public void destroy() {
    }
}

