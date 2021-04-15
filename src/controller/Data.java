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
    private ArrayList<Admin> adminArrayList = new ArrayList();

    public ArrayList<Admin> getAdminArrayList() {
        return adminArrayList;
    }

    public void setAdminArrayList(ArrayList<Admin> adminArrayList) {
        this.adminArrayList = adminArrayList;
    }
    
}
