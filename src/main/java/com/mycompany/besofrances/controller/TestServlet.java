package com.mycompany.besofrances.controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(
    name = "TestServlet",
    urlPatterns = {"/test"},
    loadOnStartup = 1
)
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>✅ Test Servlet Funcionando</h1>");
            out.println("<p>El servlet está mapeado correctamente.</p>");
            out.println("<p>Context Path: " + request.getContextPath() + "</p>");
            out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
            out.println("<p>Servlet Path: " + request.getServletPath() + "</p>");
            out.println("<br>");
            out.println("<a href='" + request.getContextPath() + "/seleccionar_perfil'>Ir a Selección de Perfil</a>");
            out.println("<br>");
            out.println("<a href='" + request.getContextPath() + "/login'>Ir a Login</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
} 