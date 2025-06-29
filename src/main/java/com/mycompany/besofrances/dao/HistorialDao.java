package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import java.sql.*;

public class HistorialDao {
    
    public int registrarHistorial(int idUsuario, String tipoAccion, Integer idProducto, String motivo) throws SQLException {
        if (idProducto != null) {
            String sql = "INSERT INTO historial_accion (id_usuario, tipo_accion, id_producto, motivo) VALUES (?, ?, ?, ?)";
            return DatabaseUtil.executeUpdate(sql, idUsuario, tipoAccion, idProducto, motivo);
        } else {
            String sql = "INSERT INTO historial_accion (id_usuario, tipo_accion, motivo) VALUES (?, ?, ?)";
            return DatabaseUtil.executeUpdate(sql, idUsuario, tipoAccion, motivo);
        }
    }
    
    public int registrarHistorialConProducto(int idUsuario, int idProducto, String nombreProducto, 
                                           String tipoAccion, String motivo, int cantidad) throws SQLException {
        String sql = "INSERT INTO historial_accion (id_usuario, id_producto, nombre_producto, tipo_accion, motivo, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        return DatabaseUtil.executeUpdate(sql, idUsuario, idProducto, nombreProducto, tipoAccion, motivo, cantidad);
    }
    
    public int registrarHistorialConEstados(int idUsuario, int idProducto, String tipoAccion, 
                                           String estadoAnterior, String estadoNuevo, int cantidad) throws SQLException {
        String sql = "INSERT INTO historial_accion (id_usuario, id_producto, tipo_accion, estado_anterior, estado_nuevo, cantidad) VALUES (?, ?, ?, ?, ?, ?)";
        return DatabaseUtil.executeUpdate(sql, idUsuario, idProducto, tipoAccion, estadoAnterior, estadoNuevo, cantidad);
    }
} 