package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovimientoDao {
    
    public List<Object[]> listarMovimientos(String fecha) throws SQLException {
        String sql = "SELECT ha.id_historial, ha.fecha_hora, u.nombre AS nombre_responsable, " +
                    "u.apellido_paterno AS apellido_responsable, " +
                    "COALESCE(ha.nombre_producto, p.nombre_producto) AS nombre_producto, " +
                    "ha.tipo_accion, ha.motivo, ha.cantidad, ha.estado_anterior, ha.estado_nuevo " +
                    "FROM historial_accion ha " +
                    "JOIN usuarios u ON ha.id_usuario = u.id_usuario " +
                    "LEFT JOIN producto p ON ha.id_producto = p.id_producto " +
                    "WHERE DATE(ha.fecha_hora) = ? " +
                    "ORDER BY ha.fecha_hora DESC";
        
        List<Object[]> movimientos = new ArrayList<>();
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, fecha)) {
            while (rs.next()) {
                Object[] movimiento = new Object[9];
                movimiento[0] = rs.getInt("id_historial");
                movimiento[1] = rs.getString("fecha_hora");
                movimiento[2] = rs.getString("nombre_responsable") + " " + rs.getString("apellido_responsable");
                movimiento[3] = rs.getString("nombre_producto");
                movimiento[4] = rs.getString("tipo_accion");
                movimiento[5] = rs.getString("motivo");
                movimiento[6] = rs.getInt("cantidad");
                movimiento[7] = rs.getString("estado_anterior");
                movimiento[8] = rs.getString("estado_nuevo");
                movimientos.add(movimiento);
            }
        }
        return movimientos;
    }
} 