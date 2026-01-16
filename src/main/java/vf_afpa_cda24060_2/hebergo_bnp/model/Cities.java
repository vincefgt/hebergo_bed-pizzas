package vf_afpa_cda24060_2.hebergo_bnp.model;

import vf_afpa_cda24060_2.hebergo_bnp.exception.SaisieException;

public class Cities {
    // Attributs
    private int idCity;
    private String labelCity;
    private int zipCode;

    // Constructeur
    /**
     * Instantiates a new City.
     */
    public Cities() {
    }

    /**
     * Instantiates a new City.
     *
     * @param pIdCity    the p id city
     * @param pLabelCity the p label city
     * @param pZipCode   the p zip code
     * @throws SaisieException the saisie exception
     */
    public Cities(int pIdCity, String pLabelCity, int pZipCode) throws SaisieException {
        this.setIdCity(pIdCity);
        this.setLabelCity(pLabelCity);
        this.setZipCode(pZipCode);
    }

    public Cities(String pLabelCity,int pZipCode) throws SaisieException {
        this.setLabelCity(pLabelCity);
        this.setZipCode(pZipCode);
    }

    /**
     * Gets id city.
     *
     * @return the id city
     */
    public int getIdCity() {
        return idCity;
    }

    // Getters and Setters
    /**
     * Sets id city.
     *
     * @param pIdCity the p id city
     */
    public void setIdCity(int pIdCity) {
        this.idCity = pIdCity;
    }

    /**
     * Gets label city.
     *
     * @return the label city
     */
    public String getLabelCity() {
        return labelCity;
    }

    /**
     * Sets label city.
     *
     * @param pLabelCity the p label city
     * @throws SaisieException the saisie exception
     */
    public void setLabelCity(String pLabelCity) throws SaisieException {
        if(pLabelCity == null) {
            throw new SaisieException("Label city is null");
        }
        this.labelCity = pLabelCity;
    }

    /**
     * Gets zip code.
     *
     * @return the zip code
     */
    public int getZipCode() {
        return zipCode;
    }

    /**
     * Sets zip code.
     *
     * @param pZipCode the p zip code
     */
    public void setZipCode(int pZipCode) {
        this.zipCode = pZipCode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("City [idCity=");
        sb.append(idCity);
        sb.append(", labelCity=");
        sb.append(labelCity);
        sb.append(", zipCode=");
        sb.append(zipCode);
        sb.append("]");

        return sb.toString();
    }
}
