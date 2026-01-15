package vf_afpa_cda24060_2.hebergo_bnp.model;

public class Addresses {
    // Attributs
    private int idAddress;
    private String numberStreet;
    private int idCity;

    // Constructeur
    public Addresses() {
    }

    public Addresses(int pIdAddress, String pNumberStreet, int pIdCity) {
        this.setIdAddress(pIdAddress);
        this.setNumberStreet(pNumberStreet);
        this.setIdCity(pIdCity); // CORRECTION: setIdCity au lieu de getIdCity
    }

    // Getters and Setters
    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int pIdAddress) {
        this.idAddress = pIdAddress;
    }

    public String getNumberStreet() {
        return numberStreet;
    }

    public void setNumberStreet(String pNumberStreet) {
        this.numberStreet = pNumberStreet;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Addresses [idAddress=");
        sb.append(idAddress);
        sb.append(", idCity=");
        sb.append(idCity);
        sb.append(", numberStreet=");
        sb.append(numberStreet);
        sb.append("]");

        return sb.toString();
    }
}