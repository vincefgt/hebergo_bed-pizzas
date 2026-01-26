package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Addresses;
import vf_afpa_cda24060_2.hebergo_bnp.model.Rents;

import java.sql.*;
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
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, entity.getIdUser());
            ps.setInt(2, entity.getIdEstate());
            ps.setDate(3, Date.valueOf(entity.getPurchaseDate()));
            ps.setDate(4, Date.valueOf(entity.getStartRent()));
            ps.setDate(5, Date.valueOf(entity.getEndRent()));
            ps.setDouble(6, entity.getTotalPrice());
            ps.setString(7, entity.getPaymentNumber());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                entity.setIdRents(rs.getInt(1));
            }

        }catch(SQLException sqle){
            System.out.println("Erreur create RentsDAO: " + sqle.getMessage());
        }

        return entity;
    }

    @Override
    public Rents update(Connection connection, Rents entity) throws SQLException {
        String sql = "UPDATE rents SET id_user=?, id_estate=?, start_rent=?, end_rent=?, " +
                "total_price=?, payment_number=? WHERE id_rents=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, entity.getIdUser());
            ps.setInt(2, entity.getIdEstate());
            ps.setDate(3, Date.valueOf(entity.getStartRent()));
            ps.setDate(4, Date.valueOf(entity.getEndRent()));
            ps.setDouble(5, entity.getTotalPrice());
            ps.setString(6, entity.getPaymentNumber());
            ps.setInt(7, entity.getIdRents());

            ps.executeUpdate();
        }catch(SQLException sqle){
            System.out.println("Erreur update RentsDAO: " + sqle.getMessage());
        }
        return entity;
    }

    @Override
    public boolean deleteById(Connection connection, Integer pId) throws SQLException {
        String sql = "DELETE FROM rents WHERE id_rents=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pId);

            ps.executeUpdate();
        }catch(SQLException sqle){
            System.out.println("Erreur delete RentsDAO: " + sqle.getMessage());
        }
        return true;
    }

    @Override
    public Rents findById(Connection connection, Integer pId) throws SQLException {
        Rents rents = new Rents();
        String  sql = "SELECT * FROM rents WHERE id_rents=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, pId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Integer idRents = rs.getInt("id_rents");
                Integer idUser = rs.getInt("id_user");
                Integer idEstate = rs.getInt("id_estate");
                LocalDate purchaseDate = rs.getDate("purchase_date").toLocalDate();
                LocalDate startRent =  rs.getDate("start_rent").toLocalDate();
                LocalDate endRent =  rs.getDate("end_rent").toLocalDate();
                Double totalPrice = rs.getDouble("total_price");
                String paymentNumber = rs.getString("payment_number");

                rents = new Rents(idRents, idUser, idEstate, purchaseDate, startRent, endRent,
                        totalPrice, paymentNumber);
            }
        }catch(SQLException sqle){
            System.out.println("Erreur findById RentsDAO: " + sqle.getMessage());
        }
        return rents;
    }


    @Override
    public List findAll(Connection connection) throws SQLException {
        List<Rents> rentsList = new ArrayList<Rents>();

        String sql = "SELECT * FROM rents";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Integer idRents = rs.getInt("id_rents");
                Integer idUser = rs.getInt("id_user");
                Integer idEstate = rs.getInt("id_estate");
                LocalDate purchaseDate = rs.getDate("purchase_date").toLocalDate();
                LocalDate startRent =  rs.getDate("start_rent").toLocalDate();
                LocalDate endRent =  rs.getDate("end_rent").toLocalDate();
                Double totalPrice = rs.getDouble("total_price");
                String paymentNumber = rs.getString("payment_number");

                Rents rent =  new Rents(idRents, idUser, idEstate, purchaseDate, startRent, endRent, totalPrice, paymentNumber);
                rentsList.add(rent);
            }
        }catch(SQLException sqle){
            System.out.println("Erreur findAll RentsDAO: " + sqle.getMessage());
        }

        return rentsList;
    }

    public List<Rents> findByIdEstate(Connection connection, Integer idEstate){
        String sql = "SELECT * FROM rents WHERE id_estate=?";
        List<Rents> rentsList = new ArrayList<>();

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, idEstate);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Integer idRents = rs.getInt("id_rents");
                Integer idUser = rs.getInt("id_user");
                LocalDate purchaseDate = rs.getDate("purchase_date").toLocalDate();
                LocalDate startRent =  rs.getDate("start_rent").toLocalDate();
                LocalDate endRent =  rs.getDate("end_rent").toLocalDate();
                Double totalPrice = rs.getDouble("total_price");
                String paymentNumber = rs.getString("payment_number");

                Rents rent =  new Rents(idRents, idUser, idEstate, purchaseDate, startRent, endRent, totalPrice, paymentNumber);
                rentsList.add(rent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rentsList;
    }
}
