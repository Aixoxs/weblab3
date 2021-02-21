package se.ifmo.web.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPool {
    private static ConnectionPool instance = null;
    private static ComboPooledDataSource ds;

    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
            createTable();
        }
        return instance;
    }

    static void createTable(){
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()){
            statement.execute("create table if not exists points\n" +
                    "(\n" +
                    "    id           serial not null\n" +
                    "        constraint points_pkey\n" +
                    "            primary key,\n" +
                    "    inside_area  boolean,\n" +
                    "    radius       double precision,\n" +
                    "    x_coordinate double precision,\n" +
                    "    y_coordinate double precision\n" +
                    ");");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Context ctx;
        Connection c = null;
        try {
            ctx = new InitialContext();
            c = getDataSource().getConnection();
        } catch (NamingException | PropertyVetoException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        if (ds == null) {ds = new ComboPooledDataSource();
            ds.setDriverClass("org.postgresql.Driver");
            ds.setUser("postgres");
            ds.setPassword("1234");
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        }

        return ds;
    }
}
