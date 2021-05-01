package users;

import java.util.Date;

/**
 * Tento projekt vznikol vramci predmetu Vyvoj aplikacii s viacvrstvovou
 * architekturou na Fakulte informatiky a informacnych technologii na Slovenskej
 * technickej univerzite.
 *
 * Kto co robil?
 * Akos Kappel - XML, logovanie, I/O, internacionalizacia, regularne vyrazy
 * Patrik Velcicky - GUI, kolekcie, SQL queries, dokumentacia
 * 
 * @author Akos Kappel
 * @author Patrik Velcicky
 */
public final class Kniha {

    private int idKniha;
    private String zaner;
    private String nazov;
    private String autor;
    private Date pozicaneDo;
    private Zakaznik pozicaneKomu;

    public Kniha(int idKniha, String zaner, String nazov, String autor, Date pozicaneDo, String meno) {
        this.idKniha = idKniha;
        this.zaner = zaner;
        this.nazov = nazov;
        this.autor = autor;
        this.pozicaneDo = pozicaneDo;
        this.pozicaneKomu = new Zakaznik(meno);
    }

    public Kniha(int idKniha, String zaner, String nazov, String autor, Date pozicaneDo) {
        this.idKniha = idKniha;
        this.zaner = zaner;
        this.nazov = nazov;
        this.autor = autor;
        this.pozicaneDo = pozicaneDo;
    }

    public int getIdKniha() {
        return idKniha;
    }

    public void setIdKniha(int idKniha) {
        this.idKniha = idKniha;
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

    public Zakaznik getPozicaneKomu() {
        return pozicaneKomu;
    }

    public void setPozicaneKomu(Zakaznik pozicaneKomu) {
        this.pozicaneKomu = pozicaneKomu;
    }

}
