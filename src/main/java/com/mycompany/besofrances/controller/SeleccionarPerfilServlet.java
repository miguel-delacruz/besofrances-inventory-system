package com.mycompany.besofrances.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
    name = "SeleccionarPerfilServlet",
    urlPatterns = {"/seleccionar_perfil"},
    loadOnStartup = 1
)
public class SeleccionarPerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar si ya hay una sesión activa
        if (request.getSession(false) != null && 
            request.getSession().getAttribute("usuario") != null) {
            
            // Si ya tiene sesión, redirigir según su perfil
            String perfil = (String) request.getSession().getAttribute("perfil");
            if ("admin".equals(perfil)) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            } else if ("usuario".equals(perfil)) {
                response.sendRedirect(request.getContextPath() + "/dashboard");
                return;
            }
        }
        
        // Mostrar vista de selección de perfil
        request.getRequestDispatcher("/views/roles/seleccionar_perfil.jsp").forward(request, response);
    }
}