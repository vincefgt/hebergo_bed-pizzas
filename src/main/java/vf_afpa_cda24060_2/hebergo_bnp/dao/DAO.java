package vf_afpa_cda24060_2.hebergo_bnp.dao;

import vf_afpa_cda24060_2.hebergo_bnp.model.Addresses;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class DAO <T> {

    protected DAO() throws SQLException {
    }

    /**
     * Creation et persistance d'un objet T
     * @param entity T
     * @return T
     */
    public abstract T create(Connection connection, T entity) throws SQLException;

    /**
     * Mise à jour et persistance d'un objet T
     * @param entity T
     * @return 1 for success 0 for error
     */
    public abstract Object update(Connection connection, T entity) throws SQLException;

    /**
     * Suppression et persistance d'un objet T
     * @param pId Integer
     * @return 1 for success 0 for error
     */
    public abstract boolean deleteById(Connection connection, Integer pId) throws SQLException;

    /**
     * Recherche par id
     * @param pId Integer
     * @return l'objet T ciblé
     */
    public abstract T findById(Connection connection, Integer pId) throws SQLException;


    /**
     * recherche global
     * @return liste de tous les objets T
     */
    public abstract List<T> findAll(Connection connection) throws SQLException;
}
