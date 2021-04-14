package controller;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

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
        String password = ""; // Nastavit heslo pre lokalnu DB

// Tutorial: https://www.tutorialspoint.com/postgresql/postgresql_java.htm
        try {
//            Class.forName("org.postgres.Driver");
            conn = DriverManager.getConnection(url, user, password);

//            conn.setAutoCommit(false);
            System.out.println("Opened database successfully\n");

            statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM testTable;");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                System.out.println("id = " + id);
                System.out.println("name = " + name);
                System.out.println("age = " + age);
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
