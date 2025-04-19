package dto;

public class SoapResponse {
    public boolean success;
    public String message;
    public Object data;

    public SoapResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
        this.data = null;
    }

    public SoapResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
