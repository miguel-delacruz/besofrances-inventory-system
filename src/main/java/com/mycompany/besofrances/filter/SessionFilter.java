package com.mycompany.besofrances.filter;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/admin/*", "/usuario/*", "/dashboard/*"})
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        
        // Verificar si hay sesión activa y perfil asignado
        if (session == null || 
            session.getAttribute("usuario") == null || 
            session.getAttribute("rol") == null || 
            session.getAttribute("perfil") == null) {
            
            // Redirigir a selección de perfil si no hay sesión válida
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/seleccionar_perfil");
            return;
        }
        
        // Control de perfiles (equivalente al código PHP)
        String perfil = (String) session.getAttribute("perfil");
        String rol = (String) session.getAttribute("rol");
        String requestURI = httpRequest.getRequestURI();
        
        // Verificar acceso a rutas de administrador
        if (requestURI.contains("/admin/") || requestURI.contains("/dashboard/")) {
            if ("admin".equals(perfil) && 
                !("Administrador".equals(rol) || "Entrenador".equals(rol))) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/seleccionar_perfil");
                return;
            }
        }
        
        // Verificar acceso a rutas de usuario
        if (requestURI.contains("/usuario/")) {
            if ("usuario".equals(perfil) && 
                !("FullTime".equals(rol) || "PartTime".equals(rol))) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/seleccionar_perfil");
                return;
            }
        }
        
        // Si todo está bien, continuar con la cadena de filtros
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Limpieza del filtro
    }
}