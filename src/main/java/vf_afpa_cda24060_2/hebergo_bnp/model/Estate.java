package vf_afpa_cda24060_2.hebergo_bnp.model;

public class Estate {
    private int idEstate; //to be changed to user object
    private int idAdmin; //to be changed to user object
    private String nameEstate;
    private String description;
    private boolean isValid;
    private double dailyPrice;
    private String photoEstate;
    private int idAddress; // to be changed to address object
    private int idUser;
    
    //empty constructor
    public Estate(){}

    //constructor with params
    public Estate(int idEstate, int idAdmin, String nameEstate, String description, boolean isValid, double dailyPrice, String photoEstate, int idAddress, int idUser) {
        this.idEstate = idEstate;
        this.idAdmin = idAdmin;
        this.nameEstate = nameEstate;
        this.description = description;
        this.isValid = isValid;
        this.dailyPrice = dailyPrice;
        this.photoEstate = photoEstate;
        this.idAddress = idAddress;
        this.idUser = idUser;
    }

    //get and set
    public int getIdEstate() {
        return this.idEstate;
    }
    public void setIdEstate(int idEstate) {
        this.idEstate = idEstate;
    }
    public int getIdAdmin() {
        return this.idAdmin;
    }
    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
    public String getNameEstate() {
        return this.nameEstate;
    }
    public void setNameEstate(String nameEstate) {
        this.nameEstate = nameEstate;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isValid() {
        return this.isValid;
    }
    public void setValid(boolean valid) {
        this.isValid = valid;
    }
    public double getDailyPrice() {
        return this.dailyPrice;
    }
    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }
    public String getPhotoEstate() {
        return this.photoEstate;
    }
    public void setPhotoEstate(String photoEstate) {
        this.photoEstate = photoEstate;
    }
    public int getIdAddress() {
        return this.idAddress;
    }
    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }
    public int getIdUser() {
        return this.idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Estate{" +
                "Id = " + idEstate +
                ", Id admin = " + idAdmin +
                ", Name estate = " + nameEstate +
                ", Description = " + description +
                ", Is Valid = " + isValid +
                ", Daily price = " + dailyPrice +
                ", image url = " + photoEstate +
                ", address = " + idAddress +
                ", Id user = " + idUser;
    }
}
