package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CierreStockDao {
    
    public int insertarCierreStock(String fechaCierre, int idProducto, String nombreProducto, 
                                  String estado, int cantidad, int idUsuario) throws SQLException {
        String sql = "INSERT INTO cierre_stock (fecha_cierre, id_producto, nombre_producto, estado, cantidad, id_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        return DatabaseUtil.executeUpdate(sql, fechaCierre, idProducto, nombreProducto, estado, cantidad, idUsuario);
    }
    
    public List<Object[]> listarCierresStock(String fecha) throws SQLException {
        String sql = "SELECT cs.id_cierre, cs.fecha_cierre, u.nombre AS nombre_responsable, " +
                    "u.apellido_paterno AS apellido_responsable, cs.nombre_producto, cs.estado, cs.cantidad " +
                    "FROM cierre_stock cs " +
                    "JOIN usuarios u ON cs.id_usuario = u.id_usuario " +
                    "WHERE cs.fecha_cierre = ? " +
                    "ORDER BY cs.id_cierre ASC";
        
        List<Object[]> cierres = new ArrayList<>();
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, fecha)) {
            while (rs.next()) {
                Object[] cierre = new Object[6];
                cierre[0] = rs.getInt("id_cierre");
                cierre[1] = rs.getString("fecha_cierre");
                cierre[2] = rs.getString("nombre_responsable") + " " + rs.getString("apellido_responsable");
                cierre[3] = rs.getString("nombre_producto");
                cierre[4] = rs.getString("estado");
                cierre[5] = rs.getInt("cantidad");
                cierres.add(cierre);
            }
        }
        return cierres;
    }
} 