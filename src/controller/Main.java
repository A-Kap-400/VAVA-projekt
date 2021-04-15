package controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import users.Admin;


/**
 * Tento projekt vznikol vramci predmetu Vyvoj aplikacii s viacvrstvovou
 * architekturou na Fakulte informatiky a informacnych technologii na Slovenskej
 * technickej univerzite.
 *
 * @author Akos Kappel
 * @author Patrik Velcicky
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { 

        System.out.println("Hello world!");
        System.out.println("Ahoj");

        
//        for (int j = 0; j<1 ; j++){
//        String sol  = new Random().ints(48, 123)
//          .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
//          .limit(32)
//          .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//          .toString();
//        
//        MessageDigest messageDigest;
//        String passwd = "";
//        try {
//            messageDigest = MessageDigest.getInstance("SHA-256");
//            messageDigest.update(("Admin" + sol).getBytes());
//            passwd  =  new BigInteger(1,messageDigest.digest()).toString(16);
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        System.out.println(sol + "\n" + passwd + "\n");
//        }
    }   

    
        

}
    
    
    
    
