package dto.files;

public class DbFile {
    private int idFILE;
    private String name;
    private String type;
    private long bytes;
    private String hash;
    private int owner_id;
    private String creation_date;
    private String last_modified;
    private int original_idNODE;
    private int copy_idNODE;
    private int DIRECTORY_idDIRECTORY;

    public int getIdFILE() { return idFILE; }
    public void setIdFILE(int idFILE) { this.idFILE = idFILE; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public long getBytes() { return bytes; }
    public void setBytes(long bytes) { this.bytes = bytes; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public int getOwner_id() { return owner_id; }
    public void setOwner_id(int owner_id) { this.owner_id = owner_id; }

    public String getCreation_date() { return creation_date; }
    public void setCreation_date(String creation_date) { this.creation_date = creation_date; }

    public String getLast_modified() { return last_modified; }
    public void setLast_modified(String last_modified) { this.last_modified = last_modified; }

    public int getOriginal_idNODE() { return original_idNODE; }
    public void setOriginal_idNODE(int original_idNODE) { this.original_idNODE = original_idNODE; }

    public int getCopy_idNODE() { return copy_idNODE; }
    public void setCopy_idNODE(int copy_idNODE) { this.copy_idNODE = copy_idNODE; }

    public int getDIRECTORY_idDIRECTORY() { return DIRECTORY_idDIRECTORY; }
    public void setDIRECTORY_idDIRECTORY(int DIRECTORY_idDIRECTORY) { this.DIRECTORY_idDIRECTORY = DIRECTORY_idDIRECTORY; }
}
