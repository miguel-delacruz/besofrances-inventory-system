package com.mycompany.besofrances.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.besofrances.config.DatabaseUtil;
import com.mycompany.besofrances.model.Categoria;
import com.mycompany.besofrances.model.Usuario;

@WebServlet(
    name = "DashboardServlet",
    urlPatterns = {"/dashboard", "/admin", "/usuario"},
    loadOnStartup = 1
)
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Obtener el objeto Usuario de la sesión
        Usuario datosUsuario = (Usuario) session.getAttribute("usuario");
        String perfil = (String) session.getAttribute("perfil");
        
        try {
            // Obtener categorías para el formulario de productos
            List<Categoria> categorias = obtenerCategorias();
            request.setAttribute("categorias", categorias);
            
            // Determinar qué página mostrar según el perfil
            String paginaDestino;
            if ("admin".equals(perfil)) {
                paginaDestino = "/views/admin/page_admin.jsp";
            } else {
                paginaDestino = "/views/usuario/page_usuario.jsp";
            }
            
            request.getRequestDispatcher(paginaDestino).forward(request, response);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error de base de datos");
        }
    }

    private List<Categoria> obtenerCategorias() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categoria ORDER BY id_categoria ASC";
        
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