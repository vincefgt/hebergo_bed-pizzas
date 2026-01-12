package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Rents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentsDAO extends DAO<Rents> {

    public RentsDAO() throws SQLException {
        super();
    }

    @Override
    public Rents create(Connection connection, Rents entity) throws SQLException {
        String sql = "INSERT INTO Rents (id_user, id_estate, purchase_date, start_rent, end_rent, total_price," +
                " payment_number) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, entity.getIdUser());
            ps.setInt(2, entity.getIdEstate());
            ps.setDate(3, java.sql.Date.valueOf(entity.getPurchaseDate()));
            ps.setDate(4, java.sql.Date.valueOf(entity.getStartRent()));
            ps.setDate(5, java.sql.Date.valueOf(entity.getEndRent()));
            ps.setDouble(6, entity.getTotalPrice());
            ps.setString(7, entity.getPaymentNumber());
            ps.executeUpdate();

        }catch(SQLException sqle){
            System.out.println("Erreur create RentsDAO: " + sqle.getMessage());
        }

        return entity;
    }

    @Override
    public boolean update(Connection connection, Rents entity) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteById(Connection connection, Integer pId) throws SQLException {
        return false;
    }

    @Override
    public Rents findById(Connection connection, Integer pId) throws SQLException {
        return null;
    }

    @Override
    public List findAll(Connection connection) throws SQLException {
        List<Rents> rentsList = new ArrayList<Rents>();

        String sql = "SELECT * FROM rents";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Integer idUser = rs.getInt("id_user");
                Integer idEstate = rs.getInt("id_estate");
                LocalDate purchaseDate = rs.getDate("purchase_date").toLocalDate();
                LocalDate startRent =  rs.getDate("start_rent").toLocalDate();
                LocalDate endRent =  rs.getDate("end_rent").toLocalDate();
                Double totalPrice = rs.getDouble("total_price");
                String paymentNumber = rs.getString("payment_number");

                Rents rent =  new Rents(idUser, idEstate, purchaseDate, startRent, endRent, totalPrice, paymentNumber);
                rentsList.add(rent);
            }
        }catch(SQLException sqle){
            System.out.println("Erreur findAll RentsDAO: " + sqle.getMessage());
        }

        return rentsList;
    }
}
