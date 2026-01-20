package vf_afpa_cda24060_2.hebergo_bnp.dao;

import jakarta.annotation.Resource;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class userDAO {
    @Resource(name = "jdbc/MyDataSource")
    private DataSource dataSource;
    Connection connection;

    //constructor
    public userDAO() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            //Lookup the DataSource configured in context.xml
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDataSource");
            this.connection = dataSource.getConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing DataSource", e);
        }
    }
    public userDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

    // CREATE
    public User create( User user) throws SQLException {
        String sql = "INSERT INTO USERS (id_role, firstname, lastname, " +
                "phone, email, password_hash, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, user.getIdRole()); // by default 1
            stmt.setString(2, user.getFirstname());
            stmt.setString(3, user.getLastname());
            stmt.setString(4, user.getPhone());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getPasswordHash());
            stmt.setBoolean(7, user.isDeleted());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setIdUser(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
        return user;
    }

    // READ names
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

    // READ - Find by ID
    public User findById(int idUser) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE id_user = ?";
        User  user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    user = mapResultSetToUser(rs);
                }
            }
        }
        return user;
    }

    // READ - Find by Email
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        }
        return null;
    }

    // READ - Find all
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());}

        return users;
    }

    // READ - Find all non-deleted
    public List<User> findAllActive() throws SQLException {
        String sql = "SELECT * FROM USERS WHERE is_deleted = FALSE OR is_deleted IS NULL";
        List<User> users = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        }
        return users;
    }

    // UPDATE
    public boolean update(User user) throws SQLException {
        String sql = "UPDATE USERS SET id_admin = ?, id_role = ?, id_address = ?, " +
                "firstname = ?, lastname = ?, phone = ?, email = ?, " +
                "password_hash = ?, is_deleted = ? WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, user.getIdAdmin(), Types.INTEGER);
            stmt.setInt(2, user.getIdRole());
            stmt.setInt(3, user.getIdAddress());
            stmt.setString(4, user.getFirstname());
            stmt.setString(5, user.getLastname());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getEmail());
            stmt.setString(8, user.getPasswordHash());
            stmt.setBoolean(9, user.isDeleted());
            stmt.setInt(10, user.getIdUser());
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE - Soft delete
    public boolean softDelete(int idUser) throws SQLException {
        String sql = "UPDATE USERS SET is_deleted = TRUE WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            return stmt.executeUpdate() > 0;
        }
    }

    // DELETE - Hard delete
    public boolean delete(int idUser) throws SQLException {
        String sql = "DELETE FROM USERS WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            return stmt.executeUpdate() > 0;
        }
    }

    // Helper method to map ResultSet to User object
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();

        user.setIdUser(rs.getInt("id_user"));
        int idAdmin = rs.getInt("id_admin");
        user.setIdAdmin(rs.wasNull() ? null : idAdmin);
        user.setIdRole(rs.getInt("id_role"));
        user.setIdAddress(rs.getInt("id_address"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setDeleted(rs.getBoolean("is_deleted"));

        return user;
    }

    // Check if email exists
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USERS WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    // Check if phone exists
    public boolean phoneExists(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM USERS WHERE phone = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, phone);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}