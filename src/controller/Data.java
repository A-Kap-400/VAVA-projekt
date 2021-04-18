/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import users.Admin;
import users.Kniha;
import users.Zakaznik;

/**
 *
 * @author velco
 */
public final class Data {
    
    private static Data instance;
    
    private ArrayList<Admin> adminArrayList = new ArrayList<>();
    private ArrayList<Zakaznik> zakaznikArrayList = new ArrayList<>();
    private ArrayList<Kniha> knihaArrayList = new ArrayList<>();
    private Admin prihlaseny = null;

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
    
    
    
    public boolean login(String name, String passwd){
        Admin admin = new Admin();

        for(Admin a : this.getAdminArrayList()){
            passwd = admin.getHashValue(passwd, a.getPassword_salt());

            if (a.getAdmin_name().equals(name) && a.getPassword_hash().equals(passwd)){
                this.setPrihlaseny(a);
                return true;
            }
        }

        return false;

    }

    public void logoff(){
        this.setPrihlaseny(null);
    }
    
    
}
