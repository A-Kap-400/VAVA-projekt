package users;

import java.util.Date;

/**
 *
 * @author velco & kappel
 */
public final class Kniha {

    private int id_kniha;
    private String zaner;
    private String nazov;
    private String autor;
    private Date pozicaneDo;

    public Kniha(int id_kniha, String zaner, String nazov, String autor, Date pozicaneDo, String meno) {
//        super(meno); // TODO toto treba opravit
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

    public void setId_kniha(int id_kniha) {
        this.id_kniha = id_kniha;
    }

    public String getZaner() {
        return zaner;
    }

    public void setZaner(String zaner) {
        this.zaner = zaner;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getPozicaneDo() {
        return pozicaneDo;
    }

    public void setPozicaneDo(Date pozicaneDo) {
        this.pozicaneDo = pozicaneDo;
    }

}
