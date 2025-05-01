package dto.files;

import java.util.List;

public class UploadFileBatch {
    public List<UploadFile> files;

    public UploadFileBatch() {
    }

    public UploadFileBatch(List<UploadFile> files) {
        this.files = files;
    }
}
