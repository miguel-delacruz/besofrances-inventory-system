<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
// Verificar si ya hay una sesión activa
jakarta.servlet.http.HttpSession currentSession = request.getSession(false);
if (currentSession != null && 
    currentSession.getAttribute("usuario") != null && 
    currentSession.getAttribute("perfil") != null) {
    
    // Si ya tiene sesión válida, redirigir al dashboard
    response.sendRedirect(request.getContextPath() + "/dashboard");
    return;
}

// Si no hay sesión válida, redirigir a selección de perfil
response.sendRedirect(request.getContextPath() + "/seleccionar_perfil");
%>