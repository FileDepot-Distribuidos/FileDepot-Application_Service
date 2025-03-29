package dto;

public class SoapResponse {
    public boolean success;
    public String message;

    public SoapResponse() {
    }

    public SoapResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
