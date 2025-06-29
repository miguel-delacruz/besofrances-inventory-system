package com.mycompany.besofrances.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(
    name = "LogoutServlet",
    urlPatterns = {"/logout"},
    loadOnStartup = 1
)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            // Invalidar la sesión
            session.invalidate();
        }
        
        // Limpiar sessionStorage del cliente y redirigir
        response.setContentType("text/html;charset=UTF-8");
        String contextPath = request.getContextPath();
        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<script>");
        response.getWriter().println("    // Limpiar sessionStorage");
        response.getWriter().println("    sessionStorage.clear();");
        response.getWriter().println("    // Redirigir a selección de perfil");
        response.getWriter().println("    window.location.href = '" + contextPath + "/seleccionar_perfil';");
        response.getWriter().println("</script>");
        response.getWriter().println("</head>");
        response.getWriter().println("<body>");
        response.getWriter().println("    <p>Cerrando sesión...</p>");
        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 