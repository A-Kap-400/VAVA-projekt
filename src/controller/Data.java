package controller;

import java.util.ArrayList;
import users.Admin;
import users.Kniha;
import users.Zakaznik;

/**
 *
 * @author velco & kappel
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
