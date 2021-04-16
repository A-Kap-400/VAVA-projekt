/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import users.Admin;

/**
 *
 * @author velco
 */
public final class Data {
    
    private static Data instance;
    
    private ArrayList<Admin> adminArrayList = new ArrayList<>();
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

    public void setAdminArrayList(ArrayList<Admin> adminArrayList) {
        this.adminArrayList = adminArrayList;
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
