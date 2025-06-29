package com.mycompany.besofrances.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidades para manejo de base de datos
 * Equivalente a las funciones de conexión en PHP
 */
public class DatabaseUtil {
    
    private static final Logger LOGGER = Logger.getLogger(DatabaseUtil.class.getName());
    
    /**
     * Verifica si la conexión a la base de datos está disponible
     * @return true si la conexión es válida, false en caso contrario
     */
    public static boolean isDatabaseAvailable() {
        return Conexion.isConnectionValid();
    }
    
    /**
     * Ejecuta una consulta y retorna un ResultSet
     * @param sql La consulta SQL a ejecutar
     * @param params Los parámetros de la consulta
     * @return ResultSet con los resultados
     * @throws SQLException si hay error en la consulta
     */
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // Establecer parámetros
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            return stmt.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ejecutando consulta: " + sql, e);
            throw e;
        }
    }
    
    /**
     * Ejecuta una actualización (INSERT, UPDATE, DELETE)
     * @param sql La consulta SQL a ejecutar
     * @param params Los parámetros de la consulta
     * @return Número de filas afectadas
     * @throws SQLException si hay error en la consulta
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            
            // Establecer parámetros
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            
            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error ejecutando actualización: " + sql, e);
            throw e;
        } finally {
            closeResources(null, stmt, conn);
        }
    }
    
    /**
     * Cierra los recursos de base de datos
     * @param rs ResultSet a cerrar
     * @param stmt PreparedStatement a cerrar
     * @param conn Connection a cerrar
     */
    public static void closeResources(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error cerrando ResultSet", e);
        }
        
        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error cerrando PreparedStatement", e);
        }
        
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error cerrando Connection", e);
        }
    }
    
    /**
     * Verifica si una tabla existe en la base de datos
     * @param tableName Nombre de la tabla a verificar
     * @return true si la tabla existe, false en caso contrario
     */
    public static boolean tableExists(String tableName) {
        String sql = "SHOW TABLES LIKE ?";
        
        try (ResultSet rs = executeQuery(sql, tableName)) {
            return rs.next();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error verificando existencia de tabla: " + tableName, e);
            return false;
        }
    }
    
    /**
     * Obtiene información de la base de datos (equivalente a mysqli_info en PHP)
     * @return String con información de la conexión
     */
    public static String getDatabaseInfo() {
        try (Connection conn = Conexion.getConnection()) {
            return "MySQL " + conn.getMetaData().getDatabaseProductVersion() + 
                   " - " + conn.getCatalog() + 
                   " - " + conn.getMetaData().getUserName();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error obteniendo información de la base de datos", e);
            return "Error obteniendo información de la base de datos";
        }
    }
} 