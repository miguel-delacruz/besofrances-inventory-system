/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.besofrances.dao;

import com.mycompany.besofrances.config.DatabaseUtil;
import com.mycompany.besofrances.model.Usuario;
import java.sql.*;

/**
 *
 * @author eskmi
 */
public class UsuarioDao {
    
    public Usuario autenticarUsuario(String usuario, String password) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u JOIN rol r ON u.id_rol = r.id_rol WHERE u.usuario = ? AND u.password = ?";
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, usuario, password)) {
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setCodigo(rs.getString("codigo"));
                user.setNombre(rs.getString("nombre"));
                user.setApellidoPaterno(rs.getString("apellido_paterno"));
                user.setApellidoMaterno(rs.getString("apellido_materno"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setRol(rs.getString("nombre_rol"));
                user.setIdRol(rs.getInt("id_rol"));
                return user;
            }
        }
        return null;
    }
    
    public Usuario obtenerUsuarioPorId(int idUsuario) throws SQLException {
        String sql = "SELECT u.*, r.nombre_rol FROM usuarios u JOIN rol r ON u.id_rol = r.id_rol WHERE u.id_usuario = ?";
        try (ResultSet rs = DatabaseUtil.executeQuery(sql, idUsuario)) {
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setIdUsuario(rs.getInt("id_usuario"));
                user.setCodigo(rs.getString("codigo"));
                user.setNombre(rs.getString("nombre"));
                user.setApellidoPaterno(rs.getString("apellido_paterno"));
                user.setApellidoMaterno(rs.getString("apellido_materno"));
                user.setUsuario(rs.getString("usuario"));
                user.setPassword(rs.getString("password"));
                user.setRol(rs.getString("nombre_rol"));
                user.setIdRol(rs.getInt("id_rol"));
                return user;
            }
        }
        return null;
    }
    
    public int actualizarPerfil(int idUsuario, String nombre, String apellidoPaterno, 
                               String apellidoMaterno, String usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, apellido_paterno = ?, apellido_materno = ?, usuario = ? WHERE id_usuario = ?";
        return DatabaseUtil.executeUpdate(sql, nombre, apellidoPaterno, apellidoMaterno, usuario, idUsuario);
    }
    
    public int cambiarPassword(int idUsuario, String nuevaPassword) throws SQLException {
        String sql = "UPDATE usuarios SET password = ? WHERE id_usuario = ?";
        return DatabaseUtil.executeUpdate(sql, nuevaPassword, idUsuario);
    }
}
