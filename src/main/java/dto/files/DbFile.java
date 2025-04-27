package dto.files;

public class DbFile {
    private int idFILE;
    private String name;
    private String hash;
    private int DIRECTORY_idDIRECTORY;
    private int NODE_idNODE;
    private int USER_idUSER;

    public DbFile() {
    }

    public int getIdFILE() {
        return idFILE;
    }

    public String getName() {
        return name;
    }

    public String getHash() {
        return hash;
    }

    public int getDIRECTORY_idDIRECTORY() {
        return DIRECTORY_idDIRECTORY;
    }

    public int getNODE_idNODE() {
        return NODE_idNODE;
    }

    public int getUSER_idUSER() {
        return USER_idUSER;
    }

    public void setIdFILE(int idFILE) {
        this.idFILE = idFILE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setDIRECTORY_idDIRECTORY(int DIRECTORY_idDIRECTORY) {
        this.DIRECTORY_idDIRECTORY = DIRECTORY_idDIRECTORY;
    }

    public void setNODE_idNODE(int NODE_idNODE) {
        this.NODE_idNODE = NODE_idNODE;
    }

    public void setUSER_idUSER(int USER_idUSER) {
        this.USER_idUSER = USER_idUSER;
    }
}
