package vf_afpa_cda24060_2.hebergo_bnp.model;

public class ApiResponse {
    private boolean success;
    private String message;
    private Object estate;

    public ApiResponse(boolean success, String message, Object estate) {
        this.success = success;
        this.message = message;
        this.estate = estate;
    }
    // Getters et setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Object getEstate() { return estate; }
    public void setEstate(Object estate) { this.estate = estate; }
}