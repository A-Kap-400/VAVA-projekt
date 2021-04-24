package users;

import java.util.Date;

/**
 *
 * @author velco
 */
public final class Kniha extends Zakaznik {

    private int id_kniha;
    private String zaner;
    private String nazov;
    private String autor;
    private Date pozicaneDo;

    public Kniha() {
    }

    public Kniha(int id_kniha, String zaner, String nazov, String autor, Date pozicaneDo, String meno) {
        super(meno);
        this.id_kniha = id_kniha;
        this.zaner = zaner;
        this.nazov = nazov;
        this.autor = autor;
        this.pozicaneDo = pozicaneDo;
    }

    public Kniha(int id_kniha, String zaner, String nazov, String autor, Date pozicaneDo) {
        this.id_kniha = id_kniha;
        this.zaner = zaner;
        this.nazov = nazov;
        this.autor = autor;
        this.pozicaneDo = pozicaneDo;
    }

    public int getId_kniha() {
        return id_kniha;
    }

    public String getZaner() {
        return zaner;
    }

    public String getNazov() {
        return nazov;
    }

    public String getAutor() {
        return autor;
    }

    public Date getPozicaneDo() {
        return pozicaneDo;
    }

}
