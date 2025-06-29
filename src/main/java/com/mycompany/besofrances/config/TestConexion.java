package com.mycompany.besofrances.config;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConexion {
    
    public static void main(String[] args) {
        System.out.println("=== PRUEBA DE CONEXIÓN A LA BASE DE DATOS ===");
        
        // Probar conexión básica
        if (Conexion.isConnectionValid()) {
            System.out.println("✅ Conexión a la base de datos exitosa");
        } else {
            System.out.println("❌ Error de conexión a la base de datos");
            return;
        }
        
        // Probar consulta de usuarios
        try {
            String sql = "SELECT COUNT(*) as total FROM usuarios";
            ResultSet rs = DatabaseUtil.executeQuery(sql);
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("✅ Usuarios en la BD: " + total);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error consultando usuarios: " + e.getMessage());
        }
        
        // Probar consulta de productos
        try {
            String sql = "SELECT COUNT(*) as total FROM producto";
            ResultSet rs = DatabaseUtil.executeQuery(sql);
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("✅ Productos en la BD: " + total);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error consultando productos: " + e.getMessage());
        }
        
        // Probar consulta de categorías
        try {
            String sql = "SELECT COUNT(*) as total FROM categoria";
            ResultSet rs = DatabaseUtil.executeQuery(sql);
            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("✅ Categorías en la BD: " + total);
            }
        } catch (SQLException e) {
            System.out.println("❌ Error consultando categorías: " + e.getMessage());
        }
        
        // Probar autenticación de usuario
        try {
            String sql = "SELECT u.*, r.nombre_rol FROM usuarios u JOIN rol r ON u.id_rol = r.id_rol WHERE u.usuario = ? AND u.password = ?";
            ResultSet rs = DatabaseUtil.executeQuery(sql, "nicolead", "fc63f87c08d505264caba37514cd0cfd");
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String rol = rs.getString("nombre_rol");
                System.out.println("✅ Usuario de prueba encontrado: " + nombre + " (Rol: " + rol + ")");
            } else {
                System.out.println("⚠️ Usuario de prueba no encontrado");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error en autenticación: " + e.getMessage());
        }
        
        System.out.println("=== FIN DE PRUEBAS ===");
    }
} 