package vf_afpa_cda24060_2.hebergo_bnp.model;

public class User {

    private Integer idUser;
    private Integer idAdmin;
    private Integer idRole;
    private Integer idAddress;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String passwordHash;
    private boolean isDeleted;

    // Constructors
    public User() {
        this.idUser = null;
        this.idRole = 1; // Default value
        this.isDeleted = false;
        this.idAddress = null;
    }
    public User(Integer idUser, Integer idAddress, String firstname, String lastname, String phone, String email, String passwordHash) {
        this();
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and Setters
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", idAdmin=" + idAdmin +
                ", idRole=" + idRole +
                ", idAddress=" + idAddress +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}