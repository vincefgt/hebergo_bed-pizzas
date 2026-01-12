package vf_afpa_cda24060_2.hebergo_bnp.model;

import java.time.LocalDate;

public class Rents {

    private Integer idUser;
    private Integer idEstate;
    private LocalDate purchaseDate;
    private LocalDate startRent;
    private LocalDate endRent;
    private Double total_price;
    private String paymentNumber;

    /**
     * constructeur par défaut
     */
    public Rents(){}

    /**
     * constructeur complet
     * @param idUser Integer
     * @param idEstate Integer
     * @param purchaseDate LocalDate
     * @param startRent LocalDate
     * @param endRent LocalDate
     * @param total_price Double
     * @param paymentNumber String
     */
    public Rents(Integer idUser, Integer idEstate, LocalDate purchaseDate, LocalDate startRent, LocalDate endRent,
                 Double total_price, String paymentNumber) {
        setIdUser(idUser);
        setIdEstate(idEstate);
        setPurchaseDate(purchaseDate);
        setStartRent(startRent);
        setEndRent(endRent);
        setTotalPrice(total_price);
        setPaymentNumber(paymentNumber);
    }

    /**
     *  getter idUser
     * @return Integer
     */
    public Integer getIdUser() {
        return this.idUser;
    }

    /**
     * setter idUser
     * @param idUser Integer
     */
    public void setIdUser(Integer idUser) {
        if(idUser == null || idUser <= 0){
            throw new IllegalArgumentException("idUser ne peux être null ou négatif");
        }
        this.idUser = idUser;
    }

    /**
     * getter idEstate
     * @return Integer
     */
    public Integer getIdEstate() {
        return this.idEstate;
    }

    /**
     * setter idEstate
     * @param idEstate Integer
     */
    public void setIdEstate(Integer idEstate) {
        if(idEstate == null || idEstate <= 0){
            throw new IllegalArgumentException("idEstate ne peux être null ou négatif");
        }
        this.idEstate = idEstate;
    }

    /**
     * getter purchaseDate
     * @return LocalDate
     */
    public LocalDate getPurchaseDate() {
        return this.purchaseDate;
    }

    /**
     * setter purchaseDate
     * @param purchaseDate LocalDate
     */
    public void setPurchaseDate(LocalDate purchaseDate) {
        LocalDate today = LocalDate.now();
        if(purchaseDate == null || purchaseDate.isBefore(today)){
            throw new IllegalArgumentException("La date de commande est invalde");
        }
        this.purchaseDate = purchaseDate;
    }

    /**
     * getter startRent
     * @return LocalDate
     */
    public LocalDate getStartRent() {
        return this.startRent;
    }

    /**
     * setter startRent
     * @param startRent LocalDate
     */
    public void setStartRent(LocalDate startRent) {
        if(startRent == null || startRent.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("La date de début de location est invalde");
        }
        this.startRent = startRent;
    }

    /**
     * getter endRent
     * @return LocalDate
     */
    public LocalDate getEndRent() {
        return this.endRent;
    }

    /**
     * setter endRent
     * @param endRent LocalDate
     */
    public void setEndRent(LocalDate endRent) {
        if(endRent == null || endRent.isBefore(this.startRent)){
            throw new IllegalArgumentException("La date de fin de location est invalde");
        }
        this.endRent = endRent;
    }

    /**
     * getter totalPrice
     * @return Double
     */
    public Double getTotalPrice() {
        return this.total_price;
    }

    /**
     * setter totalPrice
     * @param totalPrice Double
     */
    public void setTotalPrice(Double totalPrice) {
        if(totalPrice == null || totalPrice <= 0){
            throw new IllegalArgumentException("Le prix total est invalde");
        }
        this.total_price = totalPrice;
    }

    /**
     * getter paymentNumber
     * @return String
     */
    public String getPaymentNumber() {
        return this.paymentNumber;
    }

    /**
     * setter paymentNumber
     * @param paymentNumber String
     */
    public void setPaymentNumber(String paymentNumber) {
        if(paymentNumber == null || paymentNumber.isEmpty()){
            throw new IllegalArgumentException("Le numéro de paiement est invalide");
        }
        this.paymentNumber = paymentNumber;
    }

}
