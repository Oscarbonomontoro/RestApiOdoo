package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//sisi
public class ConexionDB {

    public static Connection connection = null;

    public static Connection getConnection() {

        String dbName = "odoo";
        String dbPort = "5432";
        String username = "odoo";
        String password = "odoo";

        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:" + dbPort + "/" + dbName + "?user=" + username + "&password=" + password;
            // obtain a physical connection
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return connection;

    }
}
//si

