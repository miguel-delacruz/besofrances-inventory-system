<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="jakarta.servlet.http.*" %>

<%
// Verificación de sesión (equivalente a verificar_sesion.php)
jakarta.servlet.http.HttpSession currentSession = request.getSession(false);
if (currentSession != null && 
    currentSession.getAttribute("usuario") != null && 
    currentSession.getAttribute("rol") != null && 
    currentSession.getAttribute("perfil") != null) {
    
    // Si ya tiene sesión válida, redirigir según su perfil
    String perfil = (String) currentSession.getAttribute("perfil");
    String rol = (String) currentSession.getAttribute("rol");
    
    // Control de perfiles
    if ("admin".equals(perfil) && ("Administrador".equals(rol) || "Entrenador".equals(rol))) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        return;
    } else if ("usuario".equals(perfil) && ("FullTime".equals(rol) || "PartTime".equals(rol))) {
        response.sendRedirect(request.getContextPath() + "/usuario/perfil");
        return;
    }
}
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Seleccionar Perfil - BesoFrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/seleccionar_perfil.css">
</head>
<body>
    <div class="container-logo">
        <img src="${pageContext.request.contextPath}/img/BesoFranceslogo.png" alt="Logo" class="logo">
    </div>
    
    <div class="perfil-container">
        <h2>¿Cómo deseas ingresar?</h2>
        <div class="opciones">
            <div class="opcion" data-perfil="admin">
                <img src="${pageContext.request.contextPath}/img/usr.png" alt="Administrador">
                <span>Administrador</span>
            </div>
            <div class="opcion" data-perfil="usuario">
                <img src="${pageContext.request.contextPath}/img/usr.png" alt="Usuario">
                <span>Usuario</span>
            </div>
        </div>
    </div>
    
    <!-- Script externo para funcionalidad -->
    <script src="${pageContext.request.contextPath}/js/seleccionar_perfil.js"></script>
</body>
</html>
