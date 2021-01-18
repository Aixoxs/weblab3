package se.ifmo.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection connect() {
        Connection connection = null;
        try {
            Class.forName ("org.postgresql.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","1234");
            System.out.println ("Database connection established");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        new SQLScriptRunner().executeScript(connection,"db.sql");
        return connection;
    }
}