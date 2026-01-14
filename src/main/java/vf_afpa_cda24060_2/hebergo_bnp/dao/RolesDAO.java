package vf_afpa_cda24060_2.hebergo_bnp.dao;


import vf_afpa_cda24060_2.hebergo_bnp.model.Roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolesDAO extends DAO <Roles>{

    public RolesDAO() throws SQLException {
        super();
    }

    @Override
    public Roles create(Connection connection, Roles entity) throws SQLException {
        String sql = "INSERT INTO roles (label_role) VALUES (?)";

        try{
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1,entity.getLabelRole());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                entity.setIdRole(rs.getInt(1));
            }
        }catch(SQLException sqle){
            System.out.println("Erreur create RolesDAO: " + sqle.getMessage());
        }
        return entity;
    }

    @Override
    public Object update(Connection connection, Roles entity) throws SQLException {
        String sql = "UPDATE roles SET label_role=? WHERE id_role=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,entity.getLabelRole());
            ps.setInt(2,entity.getIdRole());
            ps.executeUpdate();
        }catch(SQLException sqle){
            System.out.println("Erreur update RolesDAO: " + sqle.getMessage());
        }
        return entity;
    }

    @Override
    public boolean deleteById(Connection connection, Integer pId) throws SQLException {
        String sql = "DELETE FROM roles WHERE id_role=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,pId);
            ps.executeUpdate();
        }catch(SQLException sqle){
            System.out.println("Erreur delete RolesDAO: " + sqle.getMessage());
        }
        return true;
    }

    @Override
    public Roles findById(Connection connection, Integer pId) throws SQLException {
        Roles role = new Roles();
        String sql = "SELECT * FROM roles WHERE id_role=?";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,pId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Integer idRole = rs.getInt("id_role");
                String labelRole = rs.getString("label_role");
                role =  new Roles(idRole, labelRole);
            }
        }catch(SQLException sqle){
            System.out.println("Erreur find RolesDAO: " + sqle.getMessage());
        }
        return role;
    }

    @Override
    public List<Roles> findAll(Connection connection) throws SQLException {
        List<Roles> rolesList = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Integer idRole = rs.getInt("id_role");
                String labelRole = rs.getString("label_role");

                Roles role = new Roles(idRole, labelRole);
                rolesList.add(role);
            }
        }catch(SQLException sqle){
            System.out.println("Erreur find RolesDAO: " + sqle.getMessage());
        }
        return rolesList;
    }
}
