package users;

/**
 *
 * @author velco & Akos Kappel
 */
public class Zakaznik {

    private int idZakaznik;
    private String meno;
    private String email; // TODO pridat email
    private String adresa;
    private String mesto;
    private String psc;
    private String telCislo;

    public Zakaznik(int idZakaznik, String meno, String adresa, String mesto, String psc, String telCislo) {
        this.idZakaznik = idZakaznik;
        this.meno = meno;
        this.adresa = adresa;
        this.mesto = mesto;
        this.psc = psc;
        this.telCislo = telCislo;
    }

    public int getIdZakaznik() {
        return idZakaznik;
    }

    public void setIdZakaznik(int idZakaznik) {
        this.idZakaznik = idZakaznik;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

}
