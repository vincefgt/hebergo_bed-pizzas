package dao;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    //DataSource to get database connection
    private DataSource dataSource;

    //constructor
    public UserDao(){
        try {
            InitialContext ctx = new InitialContext();

            //Lookup the DataSource configured in context.xml
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDataSource");

        }catch (Exception e){
            throw new RuntimeException("Error initializing DataSource",e);
        }
    }

    //fetch user names from database
    public List<String> getNames(Connection connection) throws SQLException {

        List<String> list = new ArrayList<>();
        String sql = "select firstname from users";

        try(PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            //loop through the result
            while (rs.next()){
                list.add(rs.getString("firstname"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}


