package com.mycompany.besofrances.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    // Configuración exacta como en conexion.php
    private static final String HOST = "localhost";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final String DB = "analisisbf";
    private static final String URL = "jdbc:mysql://" + HOST + ":3306/" + DB;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error al cargar el driver de MySQL", e);
        }
    }
    
    // Método para verificar la conexión (equivalente al if de PHP)
    public static boolean isConnectionValid() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return false;
        }
    }
}