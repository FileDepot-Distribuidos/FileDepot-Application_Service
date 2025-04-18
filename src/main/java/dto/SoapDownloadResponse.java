package dto;

public class SoapDownloadResponse {
  public boolean success;
  public String message;
  public String filename;
  public String fileType;
  public String data;  // aquí metemos el base64

  public SoapDownloadResponse(boolean success, String message,
                              String filename, String fileType, String data) {
    this.success = success;
    this.message = message;
    this.filename = filename;
    this.fileType = fileType;
    this.data = data;
  }
}

