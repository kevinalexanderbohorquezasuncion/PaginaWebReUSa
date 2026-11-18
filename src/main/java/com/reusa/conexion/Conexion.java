package com.reusa.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {
    private Conexion() {}

    public static Connection conectar() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null
                || url.isBlank() || user.isBlank()) {
            throw new SQLException("Configura DB_URL, DB_USER y DB_PASSWORD.");
        }
        return DriverManager.getConnection(url, user, password);
    }
}
