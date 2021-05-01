package controller;

import java.util.ArrayList;
import users.Admin;
import users.Kniha;
import users.Zakaznik;

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
public final class Data {

    private static Data instance;

    private ArrayList<Admin> adminArrayList;
    private ArrayList<Zakaznik> zakaznikArrayList;
    private ArrayList<Kniha> knihaArrayList;
    private Admin prihlaseny;

    private Data() {
        this.adminArrayList = new ArrayList<>();
        this.zakaznikArrayList = new ArrayList<>();
        this.knihaArrayList = new ArrayList<>();
        this.prihlaseny = null;
    }

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    public Admin getPrihlaseny() {
        return prihlaseny;
    }

    public void setPrihlaseny(Admin prihlaseny) {
        this.prihlaseny = prihlaseny;
    }

    public ArrayList<Admin> getAdminArrayList() {
        return adminArrayList;
    }

    public ArrayList<Zakaznik> getZakaznikArrayList() {
        return zakaznikArrayList;
    }

    public ArrayList<Kniha> getKnihaArrayList() {
        return knihaArrayList;
    }

    public void setAdminArrayList(ArrayList<Admin> adminArrayList) {
        this.adminArrayList = adminArrayList;
    }

    public void setZakaznikArrayList(ArrayList<Zakaznik> zakaznikArrayList) {
        this.zakaznikArrayList = zakaznikArrayList;
    }

    public void setKnihaArrayList(ArrayList<Kniha> knihaArrayList) {
        this.knihaArrayList = knihaArrayList;
    }

    public void addAdmin(Admin a) {
        this.adminArrayList.add(a);
    }

    public void addBook(Kniha k) {
        this.knihaArrayList.add(k);
    }

    public void addCustomer(Zakaznik z) {
        this.zakaznikArrayList.add(z);
    }

    public boolean login(String name, String passwd) {
        String pwHash;

        for (Admin a : this.getAdminArrayList()) {
            if (a.getName().equals(name)) {
                pwHash = Admin.getHashValue(passwd, a.getPasswordSalt());

                if (a.getName().equals(name) && a.getPasswordHash().equals(pwHash)) {
                    this.setPrihlaseny(a);
                    return true;
                }
            }
        }

        return false;
    }

    public void logoff() {
        this.setPrihlaseny(null);
    }

}
