package dto.directory;

public class DbDirectory {
    private int idDIRECTORY;
    private String path;
    private int PARENT_idDIRECTORY;
    private int USER_idUSER;

    public int getIdDIRECTORY() {
        return idDIRECTORY;
    }

    public String getPath() {
        return path;
    }

    public int getPARENT_idDIRECTORY() {
        return PARENT_idDIRECTORY;
    }

    public int getUSER_idUSER() {
        return USER_idUSER;
    }
}
