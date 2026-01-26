package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Estate;
import vf_afpa_cda24060_2.hebergo_bnp.model.User;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstateDao {

    private DataSource dataSource;

    // Constructor
    public EstateDao(){
        try {
            InitialContext ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/MyDataSource");
        } catch (Exception e) {
            // Note: If running on Tomcat 10+, ensure context.xml is correctly configured
            // we don't throw exception here to allow manual connection passing
        }
    }

    private Estate mapResultSetToEstate(ResultSet resultSet) throws SQLException {
        Estate estate = new Estate();
        estate.setIdEstate(resultSet.getInt("id_estate"));
        estate.setIdAdmin(resultSet.getInt("id_admin"));
        estate.setNameEstate(resultSet.getString("name_estate"));
        estate.setDescription(resultSet.getString("descriptions"));
        estate.setValid(resultSet.getBoolean("is_valid"));
        estate.setDailyPrice(resultSet.getDouble("daily_price"));
        estate.setPhotoEstate(resultSet.getString("photo_estate"));
        estate.setIdAddress(resultSet.getInt("id_address"));
        estate.setIdUser(resultSet.getInt("id_user"));
        return estate;
    }

    public Estate getEstateById(int id) throws NamingException {
        Estate estate = null;
        String sql = "select * from estates where id_estate = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    estate = mapResultSetToEstate(resultSet);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting estate by id", e);
        }
        return estate;
    }

    public List<Estate> getAllEstates(){
        List<Estate> estates = new ArrayList<>();
        String sql = "select * from estates";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    estates.add(mapResultSetToEstate(resultSet));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting all estates", e);
        }
        return estates;
    }

    private void setEstateParams(Estate estate, PreparedStatement preparedStatement) throws SQLException {
        // Handling potential null for idAdmin
        if (estate.getIdAdmin() == 0) {
            preparedStatement.setNull(1, java.sql.Types.INTEGER);
        } else {
            preparedStatement.setInt(1, estate.getIdAdmin());
        }
        preparedStatement.setString(2, estate.getNameEstate());
        preparedStatement.setString(3, estate.getDescription());
        preparedStatement.setBoolean(4, estate.isValid());
        preparedStatement.setDouble(5, estate.getDailyPrice());
        preparedStatement.setString(6, estate.getPhotoEstate());
        preparedStatement.setInt(7, estate.getIdAddress());
        preparedStatement.setInt(8, estate.getIdUser());
    }

    // New method to support Transactions from Servlet
    public void addEstate(Connection connection, Estate estate) throws SQLException {
        String sql = "insert into estates(id_admin, name_estate, descriptions, is_valid, daily_price, photo_estate, id_address, id_user) values(?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setEstateParams(estate, preparedStatement);
            preparedStatement.executeUpdate();
        }
    }

    // Overloaded method for backward compatibility
    public void addEstate(Estate estate) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            addEstate(connection, estate);
        }
    }

    // New method to support Transactions from Servlet
    public void updateEstate(Connection connection, Estate estate) throws SQLException {
        String sql = "update estates set id_admin = ?, name_estate = ?, descriptions = ?, is_valid = ?, daily_price = ?, photo_estate = ?, id_address = ?, id_user = ? where id_estate = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            setEstateParams(estate, preparedStatement);
            preparedStatement.setInt(9, estate.getIdEstate());
            preparedStatement.executeUpdate();
        }
    }

    // Overloaded method for backward compatibility
    public void updateEstate(Estate estate) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            updateEstate(connection, estate);
        }
    }

    public void deleteEstate(int id) throws SQLException {
        String sql = "delete from estates where id_estate = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error deleting estate " + id, e);
        }
    }

    public List<Estate> findEstateByHost(User user) throws SQLException {
        List<Estate> estatesHost = new ArrayList<>();
        String sql = "select * from estates where id_user = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getIdUser());

            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()){
                    estatesHost.add(mapResultSetToEstate(resultSet));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error finding estates by host", e);
        }
        return estatesHost;
    }
}