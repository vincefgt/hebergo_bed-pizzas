package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Cities;
import vf_afpa_cda24060_2.hebergo_bnp.exception.SaisieException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitiesDAO extends DAO<Cities> {

    public CitiesDAO() throws SQLException {
        super();
    }

    @Override
    public Cities create(Connection connection, Cities entity) throws SQLException {
        String sql = "INSERT INTO Cities (label_city, zip_code) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entity.getLabelCity());
            ps.setInt(2, entity.getZipCode());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                entity.setIdCity(rs.getInt(1));
            }
        }
        return entity;
    }

    @Override
    public Cities update(Connection connection, Cities entity) throws SQLException {
        String sql = "UPDATE Cities SET label_city = ?, zip_code = ? WHERE id_city = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getLabelCity());
            ps.setInt(2, entity.getZipCode());
            ps.setInt(3, entity.getIdCity());
            ps.executeUpdate();
        }
        return entity;
    }

    @Override
    public boolean deleteById(Connection connection, Integer pId) throws SQLException {
        String sql = "DELETE FROM Cities WHERE id_city = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Cities findById(Connection connection, Integer pId) throws SQLException {
        String sql = "SELECT * FROM Cities WHERE id_city = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, pId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Cities city = new Cities();
                city.setIdCity(rs.getInt("id_city"));
                city.setZipCode(rs.getInt("zip_code"));
                city.setLabelCity(rs.getString("label_city"));
                return city;
            }
        } catch (SaisieException e) {
            throw new SQLException(e);
        }
        return null;
    }

    @Override
    public List<Cities> findAll(Connection connection) throws SQLException {
        List<Cities> list = new ArrayList<>();
        String sql = "SELECT * FROM Cities";

        try (PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cities city = new Cities();
                city.setIdCity(rs.getInt("id_city"));
                city.setZipCode(rs.getInt("zip_code"));
                city.setLabelCity(rs.getString("label_city"));
                list.add(city);
            }
        } catch (SaisieException e) {
            throw new SQLException(e);
        }
        return list;
    }
}
