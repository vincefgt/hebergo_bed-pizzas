/*package vf_afpa_cda24060_2.hebergo_bnp.dao;

import jakarta.annotation.Resource;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class userDAO_vince {
    @Resource(name = "jdbc/MyDataSource")
    private DataSource dataSource;
    Connection connection;

    //constructor
    public userDAO_vince() {
        this.connection = DataSource.getConnection();
    }
    public userDAO_vince(Connection connection) throws SQLException {
        this.connection = connection;
    }

    // CREATE
    public User create(User user) throws SQLException {
        String sql = "INSERT INTO USERS (id_admin, id_role, id_address, firstname, lastname, " +
                "phone, email, password_hash, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setObject(1, user.getIdAdmin(), Types.INTEGER);
            stmt.setInt(2, user.getIdRole());
            stmt.setInt(3, user.getIdAddress());
            stmt.setString(4, user.getFirstname());
            stmt.setString(5, user.getLastname());
            stmt.setString(6, user.getPhone());
            stmt.setString(7, user.getEmail());
            stmt.setString(8, user.getPasswordHash());
            stmt.setBoolean(9, user.isDeleted());

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

    // READ - Find by ID
    public Optional<User> findById(int idUser) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE id_user = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUser);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        return Optional.empty();
    }

    // READ - Find by Email
    public Optional<User> findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM USERS WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToUser(rs));
                }
            }
        }
        return Optional.empty();
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
        }

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
}*/