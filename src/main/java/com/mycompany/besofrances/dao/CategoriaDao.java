package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import com.mycompany.besofrances.model.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDao {
    
    public List<Categoria> listarCategorias(String buscar) throws SQLException {
        String sql = "SELECT * FROM categoria WHERE 1=1";
        
        if (buscar != null && !buscar.trim().isEmpty()) {
            sql += " AND nombre_categoria LIKE ?";
            sql += " ORDER BY id_categoria ASC";
            
            List<Categoria> categorias = new ArrayList<>();
            try (ResultSet rs = DatabaseUtil.executeQuery(sql, "%" + buscar + "%")) {
                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("id_categoria"));
                    categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                    categorias.add(categoria);
                }
            }
            return categorias;
        } else {
            sql += " ORDER BY id_categoria ASC";
            
            List<Categoria> categorias = new ArrayList<>();
            try (ResultSet rs = DatabaseUtil.executeQuery(sql)) {
                while (rs.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setIdCategoria(rs.getInt("id_categoria"));
                    categoria.setNombreCategoria(rs.getString("nombre_categoria"));
                    categorias.add(categoria);
                }
            }
            return categorias;
        }
    }
    
    public int agregarCategoria(String nombre) throws SQLException {
        String sql = "INSERT INTO categoria (nombre_categoria) VALUES (?)";
        return DatabaseUtil.executeUpdate(sql, nombre);
    }
    
    public int editarCategoria(int idCategoria, String nombre) throws SQLException {
        String sql = "UPDATE categoria SET nombre_categoria = ? WHERE id_categoria = ?";
        return DatabaseUtil.executeUpdate(sql, nombre, idCategoria);
    }
    
    public int eliminarCategoria(int idCategoria) throws SQLException {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
        return DatabaseUtil.executeUpdate(sql, idCategoria);
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