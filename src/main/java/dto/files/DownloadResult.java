package dto.files;

// 1. DTO para el resultado de la descarga
public class DownloadResult {
  private final String filename;
  private final String contentBase64;
  private final long fileSize;
  private final String fileType;

  public DownloadResult(String filename, String contentBase64, long fileSize, String fileType) {
    this.filename = filename;
    this.contentBase64 = contentBase64;
    this.fileSize = fileSize;
    this.fileType = fileType;
  }

  public String getFilename() {
    return filename;
  }

  public String getContentBase64() {
    return contentBase64;
  }

  public long getFileSize() {
    return fileSize;
  }

  public String getFileType() {
    return fileType;
  }
}

