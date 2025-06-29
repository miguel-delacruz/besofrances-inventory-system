package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import com.mycompany.besofrances.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    
    public List<Producto> listarProductosConStock(String buscar) throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre_producto, c.nombre_categoria, " +
                    "dp.id_detalle, dp.fecha_ingreso, dp.fecha_vencimiento, dp.estado, dp.cantidad " +
                    "FROM producto p " +
                    "JOIN categoria c ON p.id_categoria = c.id_categoria " +
                    "JOIN detalle_producto dp ON p.id_producto = dp.id_producto " +
                    "WHERE dp.cantidad > 0";
        
        List<Object> params = new ArrayList<>();
        if (buscar != null && !buscar.trim().isEmpty()) {
            sql += " AND (p.nombre_producto LIKE ? OR c.nombre_categoria LIKE ?)";
            params.add("%" + buscar + "%");
            params.add("%" + buscar + "%");
        }
        
        sql += " ORDER BY dp.id_detalle ASC";
        
        List<Producto> productos = new ArrayList<>();
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, params.toArray())) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                producto.setIdDetalle(rs.getInt("id_detalle"));
                producto.setFechaIngreso(rs.getString("fecha_ingreso"));
                producto.setFechaVencimiento(rs.getString("fecha_vencimiento"));
                producto.setEstado(rs.getString("estado"));
                producto.setCantidad(rs.getInt("cantidad"));
                productos.add(producto);
            }
        }
        return productos;
    }
    
    public List<Producto> listarProductosSimples(String buscar) throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre_producto, c.nombre_categoria " +
                    "FROM producto p " +
                    "LEFT JOIN categoria c ON p.id_categoria = c.id_categoria " +
                    "WHERE 1=1";
        
        List<Object> params = new ArrayList<>();
        if (buscar != null && !buscar.trim().isEmpty()) {
            sql += " AND (p.nombre_producto LIKE ? OR c.nombre_categoria LIKE ?)";
            params.add("%" + buscar + "%");
            params.add("%" + buscar + "%");
        }
        
        sql += " ORDER BY p.id_producto ASC";
        
        List<Producto> productos = new ArrayList<>();
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, params.toArray())) {
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setNombreCategoria(rs.getString("nombre_categoria"));
                productos.add(producto);
            }
        }
        return productos;
    }
    
    public int agregarProducto(String nombre, int idCategoria) throws SQLException {
        String sql = "INSERT INTO producto (nombre_producto, id_categoria) VALUES (?, ?)";
        return DatabaseUtil.executeUpdate(sql, nombre, idCategoria);
    }
    
    public int editarProducto(int idProducto, String nombre, int idCategoria) throws SQLException {
        String sql = "UPDATE producto SET nombre_producto = ?, id_categoria = ? WHERE id_producto = ?";
        return DatabaseUtil.executeUpdate(sql, nombre, idCategoria, idProducto);
    }
    
    public int eliminarProducto(int idProducto) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        return DatabaseUtil.executeUpdate(sql, idProducto);
    }
    
    public int ingresarStock(int idProducto, String estado, String fechaIngreso, 
                            String fechaVencimiento, int cantidad) throws SQLException {
        String sql = "INSERT INTO detalle_producto (id_producto, estado, fecha_ingreso, fecha_vencimiento, cantidad) VALUES (?, ?, ?, ?, ?)";
        return DatabaseUtil.executeUpdate(sql, idProducto, estado, fechaIngreso, fechaVencimiento, cantidad);
    }
    
    public String obtenerNombreProducto(int idProducto) throws SQLException {
        String sql = "SELECT nombre_producto FROM producto WHERE id_producto = ?";
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, idProducto)) {
            if (rs.next()) {
                return rs.getString("nombre_producto");
            }
        }
        return "Desconocido";
    }
    
    public int obtenerCantidadActual(int idDetalle) throws SQLException {
        String sql = "SELECT cantidad FROM detalle_producto WHERE id_detalle = ?";
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, idDetalle)) {
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
        }
        return 0;
    }
    
    public int actualizarCantidadDetalle(int idDetalle, int nuevaCantidad) throws SQLException {
        String sql = "UPDATE detalle_producto SET cantidad = ? WHERE id_detalle = ?";
        return DatabaseUtil.executeUpdate(sql, nuevaCantidad, idDetalle);
    }
    
    public int obtenerIdProductoPorDetalle(int idDetalle) throws SQLException {
        String sql = "SELECT id_producto FROM detalle_producto WHERE id_detalle = ?";
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, idDetalle)) {
            if (rs.next()) {
                return rs.getInt("id_producto");
            }
        }
        return 0;
    }
    
    public int obtenerUltimoIdInsertado() throws SQLException {
        try (ResultSet rs = DatabaseUtil.executeQuery("SELECT LAST_INSERT_ID() as id")) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return 0;
    }
} 