package users;

/**
 *
 * @author velco & kappel
 */
public class Zakaznik {

    private int id_zak;
    private String meno;
    private String adresa;
    private String mesto;
    private String psc;
    private String telCislo;
    private String email;

    public Zakaznik(String meno) {
        this.meno = meno;
    }

    public Zakaznik(int id_zak, String meno, String adresa, String mesto, String psc, String telCislo) {
        this.id_zak = id_zak;
        this.meno = meno;
        this.adresa = adresa;
        this.mesto = mesto;
        this.psc = psc;
        this.telCislo = telCislo;
    }

    public int getId_zak() {
        return id_zak;
    }

    public void setId_zak(int id_zak) {
        this.id_zak = id_zak;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getPsc() {
        return psc;
    }

    public void setPsc(String psc) {
        this.psc = psc;
    }

    public String getTelCislo() {
        return telCislo;
    }

    public void setTelCislo(String telCislo) {
        this.telCislo = telCislo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
