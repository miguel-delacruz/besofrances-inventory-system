<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión - BesoFrances</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <a href="${pageContext.request.contextPath}/seleccionar_perfil" class="btn-regresar">Regresar</a>
    
    <div class="login-container">
        <img src="${pageContext.request.contextPath}/img/BesoFranceslogo.png" alt="Logo" class="logo-login">
        <h2>Iniciar Sesión</h2>
        
        <% 
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) { 
        %>
            <div class="error"><%= error %></div>
        <% } %>
        
        <form method="POST" id="loginForm">
            <input type="hidden" name="perfil" id="perfilInput" value="${perfil != null ? perfil : ''}">
            <input type="text" name="usuario" placeholder="Usuario" required autofocus>
            <input type="password" name="password" placeholder="Contraseña" required>
            <button type="submit">Ingresar</button>
        </form>
    </div>
    
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
</body>
</html>