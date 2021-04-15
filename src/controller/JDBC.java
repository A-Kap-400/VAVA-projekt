package controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import users.Admin;

/**
 *
 * @author kappe
 */
public class JDBC { // Java Database Connectivity

    public static void main(String[] args) {
        Connection conn = null;
        Statement statement = null;
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "admin"; // Nastavit heslo pre lokalnu DB

// Tutorial: https://www.tutorialspoint.com/postgresql/postgresql_java.htm
        try {
//            Class.forName("org.postgres.Driver");
            conn = DriverManager.getConnection(url, user, password);

//            conn.setAutoCommit(false);
            System.out.println("Opened database successfully\n");

            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, admin_name, password_hash, password_salt, root FROM library.administrators WHERE deleted_at is NULL;");

            Data data = new Data();
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String admin_name = resultSet.getString("admin_name");
                String password_hash = resultSet.getString("password_hash");
                String password_salt = resultSet.getString("password_salt");
                boolean root = resultSet.getBoolean("root");
                  
                data.getAdminArrayList().add(new Admin(id, admin_name, password_hash, password_salt, root));

                System.out.println("id = " + id);
                System.out.println("name = " + admin_name);
                System.out.println("passwd = " + password_hash);
                System.out.println("salt = " + password_salt);
                System.out.println("root = " + root);
                System.out.println("");
            }

            resultSet.close();
            statement.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Connection failed.");
        }
    }

}
