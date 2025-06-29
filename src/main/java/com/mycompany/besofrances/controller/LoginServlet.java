/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.besofrances.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.besofrances.dao.UsuarioDao;
import com.mycompany.besofrances.model.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author eskmi
 */
@WebServlet(
    name = "LoginServlet",
    urlPatterns = {"/login"},
    loadOnStartup = 1
)
public class LoginServlet extends HttpServlet {
    
    private UsuarioDao usuarioDao;
    
    @Override
    public void init() throws ServletException {
        usuarioDao = new UsuarioDao();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Mostrar la página de login
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        String perfil = request.getParameter("perfil");
        String error = "";

        // Debug: Mostrar información del intento de login
        System.out.println("Intento de login - Usuario: " + usuario + ", Perfil: " + perfil);

        if (usuario != null && password != null && perfil != null) {
            try {
                // Encriptar contraseña con MD5 (equivalente al PHP)
                String passwordMD5 = getMD5Hash(password);
                
                // Autenticar usuario usando el DAO
                Usuario user = usuarioDao.autenticarUsuario(usuario, passwordMD5);
                
                if (user != null) {
                    // Debug: Mostrar información del usuario encontrado
                    String rolUsuario = user.getRol();
                    System.out.println("Usuario encontrado - Rol: " + rolUsuario);
                    
                    // Validar el rol según el perfil seleccionado
                    if ("admin".equals(perfil) && 
                        ("Administrador".equals(rolUsuario) || "Entrenador".equals(rolUsuario))) {
                        
                        // Crear sesión
                        HttpSession session = request.getSession();
                        session.setAttribute("usuario", user);
                        session.setAttribute("rol", rolUsuario);
                        session.setAttribute("perfil", "admin");
                        
                        System.out.println("Redirigiendo a panel admin");
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                        return;
                        
                    } else if ("usuario".equals(perfil) && 
                               ("FullTime".equals(rolUsuario) || "PartTime".equals(rolUsuario))) {
                        
                        // Crear sesión
                        HttpSession session = request.getSession();
                        session.setAttribute("usuario", user);
                        session.setAttribute("rol", rolUsuario);
                        session.setAttribute("perfil", "usuario");
                        
                        System.out.println("Redirigiendo a panel usuario");
                        response.sendRedirect(request.getContextPath() + "/dashboard");
                        return;
                        
                    } else {
                        error = "No tienes permisos para acceder con este perfil. Rol actual: " + 
                               rolUsuario + ", Perfil seleccionado: " + perfil;
                        System.out.println("Error de permisos - Rol: " + rolUsuario + ", Perfil: " + perfil);
                    }
                } else {
                    error = "Usuario o contraseña incorrectos.";
                    System.out.println("Usuario no encontrado o contraseña incorrecta: " + usuario);
                }
            } catch (SQLException e) {
                error = "Error de base de datos: " + e.getMessage();
                System.err.println("Error SQL: " + e.getMessage());
            } catch (Exception e) {
                error = "Error interno del servidor.";
                System.err.println("Error: " + e.getMessage());
            }
        }

        // Si hay error, volver a mostrar la página de login con el error
        request.setAttribute("error", error);
        request.setAttribute("perfil", perfil);
        request.getRequestDispatcher("/views/login.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Genera hash MD5 de una cadena (equivalente a md5() en PHP)
     */
    private String getMD5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar hash MD5", e);
        }
    }
}
