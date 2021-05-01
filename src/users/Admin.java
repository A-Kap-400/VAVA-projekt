package users;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import org.apache.log4j.Logger;

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
public final class Admin {

    private int id;
    private String name;
    private String passwordHash;
    private String passwordSalt;
    private boolean root;
    private Date vytvoreny;
    private Date upraveny;

    private static final Logger LOGGER = Logger.getLogger(Admin.class);

    public Admin(int id, String name, String passwordHash, String passwordSalt, boolean root, Date vytvoreny, Date upraveny) {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
        this.root = root;
        this.vytvoreny = vytvoreny;
        this.upraveny = upraveny;
    }

    public Admin(String admin_name, String passwd, boolean root) {
        this.name = admin_name;
        createPasswordHash(passwd);
        this.root = root;
    }

    @Override
    public String toString() {
        return "Admin(name=" + name + ", passwordHash=" + passwordHash + ", root=" + root + ')';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.passwordHash);
        return hash;
    }

    // Metoda na porovnavanie adminov.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Admin other = (Admin) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public Date getVytvoreny() {
        return vytvoreny;
    }

    public void setVytvoreny(Date vytvoreny) {
        this.vytvoreny = vytvoreny;
    }

    public Date getUpraveny() {
        return upraveny;
    }

    public void setUpraveny(Date upraveny) {
        this.upraveny = upraveny;
    }

    /**
     * Vytvorenie soli pre heslo a vypocet hashu ako h = HASH(password + salt).
     *
     * @param passwd heslo
     */
    public void createPasswordHash(String passwd) {
        this.passwordSalt = generatePasswordSalt();
        this.passwordHash = getHashValue(passwd, this.passwordSalt);
    }

    // https://www.baeldung.com/java-random-string#apachecommons-bounded
    /**
     * Funkcia na generovanie soli pre heslo.
     *
     * @return sol
     */
    private static String generatePasswordSalt() {
        return new Random().ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(32)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Tato funkcia vypocita hashovu hodnotu z pouzivatelskeho hesla a soli. Na
     * vypocet sa pouziva kryptograficka funkcia SHA256.
     *
     * @param passwd heslo
     * @param salt sol
     * @return hash
     */
    public static String getHashValue(String passwd, String salt) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update((passwd + salt).getBytes());
            return new BigInteger(1, messageDigest.digest()).toString(16);

        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("Nepodarilo sa vypočítať hash hesla.");
            return null;
        }
    }

}
