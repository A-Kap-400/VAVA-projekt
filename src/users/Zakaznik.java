package users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author velco & Akos Kappel
 */
public class Zakaznik {

    private int idZakaznik;
    private String meno;
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

    public Zakaznik(String meno) {
        this.meno = meno;
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

    /**
     * Validacia PSC.
     *
     * @param psc
     * @return
     */
    public static boolean isValidPSC(String psc) {
        String regex = "^[0-9]{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(psc);
        return matcher.matches();
    }

    /**
     * Validacia telefonneho cisla.
     *
     * @param phoneNumber telefonne cislo
     * @return
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^((0{2}|\\+)[0-9]{3}[- ]?|0) ?[0-9]{3}[- ]?[0-9]{3}[- ]?[0-9]{3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

}
