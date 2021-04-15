/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package users;

import controller.Data;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author velco
 */
public final class Admin {
    private int id;
    private String admin_name;
    private String password_hash;
    private String password_salt;
    private boolean root;
    

    public Admin(int id, String admin_name, String password_hash, String password_salt, boolean root) {
        this.id = id;
        this.admin_name = admin_name;
        this.password_hash = password_hash;
        this.password_salt = password_salt;
        this.root = root;
    }

    public Admin(String admin_name, String passwd, boolean root) {
        this.admin_name = admin_name;
        setPassword_hash(passwd);
        this.root = root;
    }

    public boolean isRoot() {
        return root;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getAdmin_name() {
        return admin_name;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public String getPassword_salt() {
        return password_salt;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public void setPassword_hash(String passwd) {
        setPassword_salt();
        
        this.password_hash = getHashValue(passwd, this.password_salt);
    }
    
    private String getHashValue(String passwd, String salt){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((passwd + salt).getBytes());
            return new BigInteger(1,messageDigest.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            return "False";
        }
    } 

    private void setPassword_salt() {
//        https://www.baeldung.com/java-random-string#apachecommons-bounded 
        
        this.password_salt  = new Random().ints(48, 123)
          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
          .limit(32)
          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
          .toString();
    }
    
    public boolean login(String name, String passwd){
        Data data = new Data();
        
        for(Admin a : data.getAdminArrayList()){
            passwd = getHashValue(passwd, a.password_salt);
            if (a.admin_name.equals(name) && a.password_hash.equals(passwd)){
                data.setPrihlaseny(a);
                return true;
            }
        }
        
        return false;
        
    }

    public void logoff(){
        Data data = new Data();
        data.setPrihlaseny(null);
    }
    
}
