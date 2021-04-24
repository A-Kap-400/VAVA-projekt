package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import users.Admin;
import users.Kniha;
import users.Zakaznik;

/**
 *
 * @author kappe & velco
 */
public class JDBC { // Java Database Connectivity

    // Parametre pre spojenie k databaze
    private String url;
    private String user;
    private String password;

    // Pripojenie k databaze
    private Connection conn;
    private PreparedStatement statement;

    /**
     * Tento konstruktor vytvori objekt, ktory je pripojeny k databaze. Tutorial
     * pre narabanie k databaze:
     * https://www.tutorialspoint.com/postgresql/postgresql_java.htm
     */
    public JDBC() {
        this.url = "jdbc:postgresql://localhost:5432/postgres";
        this.user = "postgres";
        this.password = "9i#Bnh1U0&!^4oc4"; // "admin";

        this.conn = null;
        this.statement = null;
    }

    // Getters and setters for: url, user, password
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Napojenie na databazu. Je nutne mat spravne nastavene hodnoty url, user a
     * password.
     */
    public void connect() {
        try {
            this.statement = null;
            this.conn = DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully."); // TODO log4j
        } catch (SQLException e) {
            this.conn = null;

            System.out.println("Connection failed."); // TODO log4j
        }
    }

    /**
     * Ukoncenie spojenia k databaze.
     */
    public void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Database connection ended."); // TODO log4j
        } catch (SQLException e) {
            System.out.println("Disconnection failed."); // TODO log4j
        }
    }

    // toto pouzi aj pre zobrazovanie zamestnancov
    public void loadAdmin(Data data, String meno_ID_zamestnanec) {
        try {
            boolean jeInt;
            int id_zam = 0;

            try {
                id_zam = Integer.valueOf(meno_ID_zamestnanec);
                jeInt = true;
            } catch (NumberFormatException e) {
                jeInt = false;
            }

            statement = conn.prepareStatement(
                    "SELECT id, admin_name, password_hash, password_salt, root, created_at, updated_at "
                    + "FROM library.administrators "
                    + "WHERE deleted_at is NULL "
                    + ((meno_ID_zamestnanec.isEmpty()) ? ("") : ("AND " + ((jeInt) ? ("id = ?") : ("admin_name = ?"))))
                    + ";");

            if (!meno_ID_zamestnanec.isEmpty()) {
                if (jeInt) {
                    statement.setInt(1, id_zam);
                } else {
                    statement.setString(1, meno_ID_zamestnanec);
                }
            }

            ResultSet resultSet = statement.executeQuery();

            data.setAdminArrayList(new ArrayList<>());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String admin_name = resultSet.getString("admin_name");
                String password_hash = resultSet.getString("password_hash");
                String password_salt = resultSet.getString("password_salt");
                boolean root = resultSet.getBoolean("root");
                Date vytovoreny = resultSet.getTimestamp("created_at");
                Date upraveny = resultSet.getTimestamp("updated_at");

                data.getAdminArrayList().add(new Admin(id, admin_name, password_hash, password_salt, root, vytovoreny, upraveny));

            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("Function loadAdmin() failed."); // TODO log4j
        }
    }

    public void neprihlasenyKnihaZobraz(Data data, String nazov, String zaner, String autor, Boolean dostupne) {
        try {
            ArrayList<String> premenne = new ArrayList<>();

            if (!nazov.isBlank()) {
                nazov = "%" + nazov + "%";
                premenne.add(nazov);
            }
            if (!zaner.isBlank()) {
                zaner = "%" + zaner + "%";
                premenne.add(zaner);
            }
            if (!autor.isBlank()) {
                autor = "%" + autor + "%";
                premenne.add(autor);
            }

            statement = conn.prepareStatement(
                    "SELECT books.id, books.typ, books.name, books.author, books.borrow_until"
                    + " FROM library.Books AS books LEFT JOIN library.Users AS users ON users.id = books.user_id"
                    + " WHERE books.deleted_at is NULL AND users.deleted_at is NULL"
                    + ((nazov.isEmpty()) ? ("") : (" AND books.name ILIKE ?"))
                    + ((zaner.isEmpty()) ? ("") : (" AND books.typ ILIKE ?"))
                    + ((autor.isEmpty()) ? ("") : (" AND books.author ILIKE ?"))
                    + ((dostupne) ? (" AND books.borrow_until is NULL") : (""))
                    + ";"
            );

            for (int i = 0; i < premenne.size(); i++) {
                statement.setString(i + 1, premenne.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            data.setKnihaArrayList(new ArrayList<>());

            while (resultSet.next()) {
                int id_kniha = resultSet.getInt("id");
                String zaner_kniha = resultSet.getString("typ");
                String nazov_kniha = resultSet.getString("name");
                String autor_kniha = resultSet.getString("author");
                Date pozicanaDo_kniha = resultSet.getTimestamp("borrow_until");

                data.getKnihaArrayList().add(new Kniha(id_kniha, zaner_kniha, nazov_kniha, autor_kniha, pozicanaDo_kniha));
            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("Function neprihlasenyKnihaZobraz() failed."); // TODO log4j
        }
    }

    public void knihaPozicat(int user_id, Date borrow_until, int book_id) {
        try {
            statement = conn.prepareStatement(
                    "UPDATE library.Books SET user_id = ?, borrow_until = ? WHERE id = ? AND deleted_at is NULL;"
            );

            statement.setInt(1, user_id);
            statement.setDate(2, (java.sql.Date) borrow_until);
            statement.setInt(3, book_id);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function knihaPozicat() failed."); // TODO log4j
        }
    }

    public void knihaVratit(int book_id) {
        try {
            statement = conn.prepareStatement(
                    "UPDATE library.Books SET user_id = NULL, borrow_until = NULL WHERE id = ?;"
            );

            statement.setInt(1, book_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function knihaVratit() failed."); // TODO log4j
        }
    }

    public void knihaPridat(String meno_kniha, String autor_kniha, String zaner_kniha, String popis_kniha) {
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO library.Books "
                    + "(name,author,typ,description) "
                    + "VALUES "
                    + "(?, ?, ?, ?);"
            );

            statement.setString(1, meno_kniha);
            statement.setString(2, autor_kniha);
            statement.setString(3, zaner_kniha);
            statement.setString(4, popis_kniha);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function () failed."); // TODO log4j
        }
    }

    public void knihaOdstran(int book_id) {
        try {
            statement = conn.prepareStatement(
                    "UPDATE library.Books SET deleted_at = current_timestamp WHERE id = ? AND deleted_at is NULL;"
            );

            statement.setInt(1, book_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function knihaOdstran() failed."); // TODO log4j
        }
    }

    public void knihaZobraz(Data data, String nazov, String zaner, String autor, Boolean dostupne, Boolean neskore, String meno_id, String kniha_id) {
        try {
            ArrayList<String> premenne = new ArrayList<>();

            if (!nazov.isEmpty()) {
                nazov = "%" + nazov + "%";
                premenne.add(nazov);
            }
            if (!zaner.isEmpty()) {
                zaner = "%" + zaner + "%";
                premenne.add(zaner);
            }
            if (!autor.isEmpty()) {
                autor = "%" + autor + "%";
                premenne.add(autor);
            }

            boolean jeInt;
            int id_zak = 0;
            int id_knih = 0;
            int pocitadlo = 1;

            try {
                id_zak = Integer.valueOf(meno_id);
                pocitadlo += 1;
                jeInt = true;
            } catch (NumberFormatException e) {
                jeInt = false;
            }

            statement = conn.prepareStatement(
                    "SELECT books.id, books.typ, books.name, books.author, books.borrow_until, users.user_name"
                    + " FROM library.Books AS books LEFT JOIN library.Users AS users ON users.id = books.user_id"
                    + " WHERE books.deleted_at is NULL AND users.deleted_at is NULL"
                    + ((meno_id.isEmpty()) ? ("") : ("AND " + ((jeInt) ? ("users.id = ?") : ("users.user_name = ?"))))
                    + ((kniha_id.isEmpty()) ? ("") : (" AND books.id = ?"))
                    + ((nazov.isEmpty()) ? ("") : (" AND books.name ILIKE ?"))
                    + ((zaner.isEmpty()) ? ("") : (" AND books.typ ILIKE ?"))
                    + ((autor.isEmpty()) ? ("") : (" AND books.author ILIKE ?"))
                    + ((dostupne) ? (" AND books.borrow_until is NULL") : (""))
                    + ((neskore) ? (" AND books.borrow_until < current_timestamp") : (""))
                    + ";"
            );

            if (!meno_id.isEmpty()) {
                if (jeInt) {
                    statement.setInt(1, id_zak);
                } else {
                    statement.setString(1, meno_id);
                }
            }

            if (!kniha_id.isEmpty()) {
                pocitadlo += 1;
                id_knih = Integer.valueOf(kniha_id);
                statement.setInt(2, id_knih);
            }

            for (int i = 0; i < premenne.size(); i++) {
                statement.setString(pocitadlo, premenne.get(i));
                pocitadlo += 1;
            }

            ResultSet resultSet = statement.executeQuery();

            data.setKnihaArrayList(new ArrayList<>());

            while (resultSet.next()) {
                int id_kniha = resultSet.getInt("id");
                String zaner_kniha = resultSet.getString("typ");
                String nazov_kniha = resultSet.getString("name");
                String autor_kniha = resultSet.getString("author");
                String zak_kniha = resultSet.getString("user_name");
                Date pozicanaDo_kniha = resultSet.getTimestamp("borrow_until");

                data.getKnihaArrayList().add(new Kniha(id_kniha, zaner_kniha, nazov_kniha, autor_kniha, pozicanaDo_kniha, zak_kniha));

            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("Function knihaZobraz() failed."); // TODO log4j
        }
    }

    public void zakaznikPridat(String menoPriezvisko_zakaznik, String adresa_zakaznik, String psc_zakaznik, String mesto_zakaznik, String telCislo_zakaznik) {
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO library.Users "
                    + "(user_name, address, psc, city, phone_number) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?);"
            );

            statement.setString(1, menoPriezvisko_zakaznik);
            statement.setString(2, adresa_zakaznik);
            statement.setString(3, psc_zakaznik);
            statement.setString(4, mesto_zakaznik);
            statement.setString(5, telCislo_zakaznik);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function zakaznikPridat() failed."); // TODO log4j
        }
    }

    public void zakaznikOdstran(int zak_id) {
        try {
            statement = conn.prepareStatement(
                    "UPDATE library.Users SET deleted_at = current_timestamp WHERE id = ? and deleted_at is NULL"
            );

            statement.setInt(1, zak_id);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function zakaznikOdstran() failed."); // TODO log4j
        }
    }

    public void zakaznikUpravit(String menoPriezvisko_zakaznik, String adresa_zakaznik, String psc_zakaznik, String mesto_zakaznik, String telCislo_zakaznik, int zak_id) {
        try {
            statement = conn.prepareStatement(
                    "UPDATE library.Users SET user_name = ?,  address = ?, psc = ?, city = ?, phone_number = ? "
                    + "WHERE id = ? AND deleted_at is NULL;"
            );

            statement.setString(1, menoPriezvisko_zakaznik);
            statement.setString(2, adresa_zakaznik);
            statement.setString(3, psc_zakaznik);
            statement.setString(4, mesto_zakaznik);
            statement.setString(5, telCislo_zakaznik);
            statement.setInt(6, zak_id);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function zakaznikUpravit() failed."); // TODO log4j
        }
    }

    public void zakaznikZobrazit(Data data, String zakMeno_id, boolean neskore) {
        try {
            boolean jeInt;
            int id_zam = 0;

            try {
                id_zam = Integer.valueOf(zakMeno_id);
                jeInt = true;
            } catch (NumberFormatException e) {
                jeInt = false;
            }

            statement = conn.prepareStatement(
                    "SELECT DISTINCT users.id, user_name, address, psc, city, phone_number FROM library.Users RIGHT JOIN library.Books b on users.id = b.user_id "
                    + "WHERE b.deleted_at is NULL AND users.deleted_at is NULL "
                    + ((zakMeno_id.isEmpty()) ? ("") : ("AND " + ((jeInt) ? ("users.id = ?") : ("user_name = ?"))))
                    + ((neskore) ? (" AND b.borrow_until < current_timestamp ") : (""))
                    + " ORDER BY users.id ASC, b.borrow_until ASC; "
                    + ";");

            if (!zakMeno_id.isEmpty()) {
                if (jeInt) {
                    statement.setInt(1, id_zam);
                } else {
                    statement.setString(1, zakMeno_id);
                }
            }

            ResultSet resultSet = statement.executeQuery();

            data.setZakaznikArrayList(new ArrayList<>());

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String user_name = resultSet.getString("user_name");
                String address = resultSet.getString("address");
                String psc = resultSet.getString("psc");
                String city = resultSet.getString("city");
                String phone_number = resultSet.getString("phone_number");

                data.getZakaznikArrayList().add(new Zakaznik(id, user_name, address, city, psc, phone_number));

            }

            resultSet.close();

        } catch (SQLException e) {
            System.out.println("Function zakaznikZobrazit() failed."); // TODO log4j
        }
    }

    public void adminPridat(String pouzMeno, String heslo_hash, String heslo_sol, Boolean admin) {
        try {
            statement = conn.prepareStatement(
                    "INSERT INTO library.Administrators "
                    + "(admin_name,password_hash,password_salt,root) "
                    + "VALUES "
                    + "(?, ?, ?, ?);"
            );

            statement.setString(1, pouzMeno);
            statement.setString(2, heslo_hash);
            statement.setString(3, heslo_sol);
            statement.setBoolean(4, admin);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function adminPridat() failed."); // TODO log4j
        }
    }

    public void adminOdstranit(String meno_ID_admin) {
        try {
            boolean jeInt;
            int id_adm = 0;

            try {
                id_adm = Integer.valueOf(meno_ID_admin);
                jeInt = true;
            } catch (NumberFormatException e) {
                jeInt = false;
            }

            statement = conn.prepareStatement(
                    "UPDATE library.Administrators SET deleted_at = current_timestamp WHERE" + ((jeInt) ? ("id = ?") : ("admin_name = ?")) + " AND deleted_at is NULL;"
            );

            if (jeInt) {
                statement.setInt(1, id_adm);
            } else {
                statement.setString(1, meno_ID_admin);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function adminOdstranit() failed."); // TODO log4j
        }
    }

    public void adminZmenaHesla(String pouzMeno_ID, String heslo_hash, String heslo_sol) {
        try {
            boolean jeInt;
            int id_adm = 0;

            try {
                id_adm = Integer.valueOf(pouzMeno_ID);
                jeInt = true;
            } catch (NumberFormatException e) {
                jeInt = false;
            }

            statement = conn.prepareStatement(
                    "UPDATE library.Administrators SET password_hash = ?, password_salt = ? WHERE " + ((jeInt) ? ("id = ?") : ("admin_name = ?")) + "  AND deleted_at is NULL;"
            );

            statement.setString(1, heslo_hash);
            statement.setString(2, heslo_sol);

            if (jeInt) {
                statement.setInt(3, id_adm);
            } else {
                statement.setString(3, pouzMeno_ID);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Function adminZmenaHesla() failed."); // TODO log4j
        }
    }

}
