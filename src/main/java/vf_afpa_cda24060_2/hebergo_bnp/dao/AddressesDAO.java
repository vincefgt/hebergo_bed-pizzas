package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Addresses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressesDAO extends DAO<Addresses> {

    public AddressesDAO() throws SQLException {
    }

    @Override
    public Addresses create(Connection connection, Addresses entity) throws SQLException {
        StringBuilder sqlCreate = new StringBuilder();
        // Ne pas insérer id_address si auto-increment
        sqlCreate.append("INSERT INTO Addresses (number_street, id_city) ");
        sqlCreate.append("VALUES (?,?)");

        try (PreparedStatement statement = connection.prepareStatement(sqlCreate.toString(),
                Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, entity.getNumberStreet());
            statement.setInt(2, entity.getIdCity());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                entity.setIdAddress(rs.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return entity;
    }

    @Override
    public Addresses update(Connection connection, Addresses entity) throws SQLException {
        StringBuilder sqlUpdate = new StringBuilder();
        sqlUpdate.append("UPDATE Addresses SET ");
        sqlUpdate.append("number_street = ?, ");
        sqlUpdate.append("id_city = ? ");
        sqlUpdate.append("WHERE id_address = ?");

        try (PreparedStatement statement = connection.prepareStatement(sqlUpdate.toString())) {
            // CORRECTION: Ordre des paramètres corrigé
            statement.setString(1, entity.getNumberStreet());
            statement.setInt(2, entity.getIdCity());
            statement.setInt(3, entity.getIdAddress()); // WHERE clause en dernier

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Update failed, no rows affected.");
            }
        }

        return entity;
    }

    @Override
    public boolean deleteById(Connection connection, Integer pId) throws SQLException {
        StringBuilder sqlDelete = new StringBuilder();
        sqlDelete.append("DELETE FROM Addresses WHERE id_address = ?");

        try (PreparedStatement statement = connection.prepareStatement(sqlDelete.toString())) {
            statement.setInt(1, pId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        }
    }

    @Override
    public Addresses findById(Connection connection, Integer pId) throws SQLException {
        StringBuilder sqlFind = new StringBuilder();
        sqlFind.append("SELECT * FROM Addresses WHERE id_address = ?");

        try (PreparedStatement statement = connection.prepareStatement(sqlFind.toString())) {
            statement.setInt(1, pId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Addresses addresses = new Addresses();
                addresses.setIdAddress(rs.getInt("id_address"));
                addresses.setNumberStreet(rs.getString("number_street"));
                addresses.setIdCity(rs.getInt("id_city"));
                return addresses;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null; // Retourner null si non trouvé
    }


    //method added by Mahmoud to be used to check if added estate address exists already in DB
    public Addresses findAddressByName(Connection connection, String addressName) throws SQLException {
        String sql = "SELECT * FROM Addresses WHERE number_street = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, addressName);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Addresses address = new Addresses();
                address.setIdAddress(rs.getInt("id_address"));
                address.setNumberStreet(rs.getString("number_street"));
                address.setIdCity(rs.getInt("id_city"));
                return address;
            }
        }
        return null;
    }

    @Override
    public List<Addresses> findAll(Connection connection) throws SQLException {
        List<Addresses> addressesList = new ArrayList<>();

        StringBuilder sqlFindAll = new StringBuilder();
        sqlFindAll.append("SELECT * FROM Addresses");

        try (PreparedStatement statement = connection.prepareStatement(sqlFindAll.toString());
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Addresses address = new Addresses();
                address.setIdAddress(rs.getInt("id_address"));
                address.setNumberStreet(rs.getString("number_street"));
                address.setIdCity(rs.getInt("id_city"));
                addressesList.add(address);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return addressesList;
    }
}