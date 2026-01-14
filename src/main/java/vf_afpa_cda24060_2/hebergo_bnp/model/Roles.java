package vf_afpa_cda24060_2.hebergo_bnp.model;

public class Roles {

    private Integer idRole;
    private String labelRole;

    /**
     * constructeur par défaut
     */
    public Roles(){}

    /**
     * constructeur complet
     * @param idRole Integer
     * @param labelRole String
     */
    public Roles(Integer idRole, String labelRole) {
        this.idRole = idRole;
        this.labelRole = labelRole;
    }

    /**
     * constructeur sans idRole
     * @param labelRole String
     */
    public Roles(String labelRole) {
        this.labelRole = labelRole;
    }

    /**
     * getter idRole
     * @return Integer
     */
    public Integer getIdRole() {
        return this.idRole;
    }

    /**
     * setter idRole
     * @param idRole Integer
     */
    public void setIdRole(Integer idRole) {
        if(idRole == null){
            throw new IllegalArgumentException("idRole est invalide (null)");
        }
        this.idRole = idRole;
    }

    /**
     * getter labelRole
     * @return String
     */
    public String getLabelRole() {
        return this.labelRole;
    }

    /**
     * setter labelRole
     * @param labelRole String
     */
    public void setLabelRole(String labelRole) {
        if(labelRole == null){
            throw new IllegalArgumentException("labelRole est invalide (null)");
        }
        this.labelRole = labelRole;
    }
}
