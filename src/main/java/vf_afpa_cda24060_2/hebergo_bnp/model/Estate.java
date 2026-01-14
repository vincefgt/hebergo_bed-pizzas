package vf_afpa_cda24060_2.hebergo_bnp.model;

public class Estate {
    private int id; //to be changed to user object
    private int idAdmin; //to be changed to user object
    private String nameEstate;
    private String description;
    private boolean isValid;
    private double dailyPrice;
    private String photoEstate;
    private String address; // to be changed to address object

    //empty constructor
    public Estate(){}

    //constructor with params
    public Estate(int id, int idAdmin, String nameEstate, String description, boolean isValid, double dailyPrice, String photoEstate, String address) {
        this.id = id;
        this.idAdmin = idAdmin;
        this.nameEstate = nameEstate;
        this.description = description;
        this.isValid = isValid;
        this.dailyPrice = dailyPrice;
        this.photoEstate = photoEstate;
        this.address = address;
    }

    //get and set
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Estate{" +
                "Id = " + id +
                ", Id admin = " + idAdmin +
                ", Name estate = " + nameEstate +
                ", Description = " + description +
                ", Is Valid = " + isValid +
                ", Daily price = " + dailyPrice +
                ", image url = " + photoEstate +
                ", address = " + address;
    }
}
