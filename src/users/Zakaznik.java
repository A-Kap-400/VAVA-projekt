package users;

/**
 *
 * @author velco
 */
public class Zakaznik {

    private int id_zak;
    private String meno;
    private String adresa;
    private String mesto;
    private String psc;
    private String telCislo;

    public Zakaznik() {
    }

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

    public String getMeno() {
        return meno;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getMesto() {
        return mesto;
    }

    public String getPsc() {
        return psc;
    }

    public String getTelCislo() {
        return telCislo;
    }

}
