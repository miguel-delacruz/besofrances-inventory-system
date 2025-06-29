<%-- 
    Document   : page_usuario
    Created on : 23 jun. 2025, 9:29:23 p. m.
    Author     : eskmi
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Usuario</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categorias.css">
</head>
<body>
    <div class="sidebar">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/img/BesoFranceslogo.png" alt="Logo" class="logo">
        </div>
        <nav>
            <a href="#inicio">Inicio</a>
            <a href="#ingreso-productos">Ingreso de Productos</a>
            <a href="#productos">Productos</a>
            <a href="#movimientos">Movimientos</a>
        </nav>
        <div class="user-info">
            <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Cerrar Sesión</a>
        </div>
    </div>

    <div class="main-content">
        <section id="inicio" class="content-section">
            <h2>Bienvenido</h2>
            <div class="user-details">
                <p><strong class="label">Código:</strong><span class="value"> ${usuario.codigo}</span></p>
                <p><strong class="label">Rol:</strong><span class="value"> ${usuario.rol}</span></p>
                <p><strong class="label">Nombre:</strong><span class="value"> ${usuario.nombre}</span></p>
                <p><strong class="label">Apellido Paterno:</strong><span class="value"> ${usuario.apellidoPaterno}</span></p>
                <p><strong class="label">Apellido Materno:</strong><span class="value"> ${usuario.apellidoMaterno}</span></p>
            </div>
        </section>

        <section id="ingreso-productos" class="content-section">
            <h2>Ingreso de Productos</h2>

            <input type="text" id="buscar_ingreso_producto" placeholder="Buscar producto..." class="buscar-input">

            <div class="productos-table-container">
                <table class="productos-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="ingreso-productos-list">
                        <!-- Aquí se cargarán los productos vía JavaScript -->
                    </tbody>
                </table>
            </div>
        </section>

        <section id="productos" class="content-section">
            <h2>Listado de Productos</h2>

            <input type="text" id="buscar_productos_listado" placeholder="Buscar producto..." class="buscar-input">

            <div class="productos-table-container">
                <table class="productos-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Fecha Ingreso</th>
                            <th>Fecha Vencimiento</th>
                            <th>Estado</th>
                            <th>Cantidad</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="productos-listado">
                        <!-- Aquí se cargarán los productos vía JavaScript -->
                    </tbody>
                </table>
            </div>
        </section>

        <section id="movimientos" class="content-section">
            <h2>Movimientos</h2>

            <!-- Campos de búsqueda -->
            <div class="search-controls">
                <input type="date" id="buscar_movimientos_fecha" class="buscar-input">
            </div>

            <!-- Botón Generar Reportes -->
            <button id="btn-generar-reporte-movimientos" class="btn-cerrar-stock">Generar Reportes</button>

            <!-- Tabla de Movimientos -->
            <div class="productos-table-container">
                <table class="productos-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Fecha</th>
                            <th>Responsable</th>
                            <th>Producto</th>
                            <th>Acción</th>
                            <th>Motivo</th>
                            <th>Cantidad</th>
                            <th>Estado Anterior</th>
                            <th>Estado Nuevo</th>
                        </tr>
                    </thead>
                    <tbody id="movimientos-list">
                        <!-- Aquí se cargarán los movimientos vía JavaScript -->
                    </tbody>
                </table>
            </div>
        </section>
    </div>

    <!-- Modales -->
    <div id="modal-ingreso-stock" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Ingresar Stock</h3>
            <form id="form-ingreso-stock">
                <input type="hidden" id="ingreso_id_producto" name="id_producto">
                <input type="text" id="ingreso_nombre_producto" name="nombre_producto" readonly>
                <select id="ingreso_estado" name="estado" required>
                    <option value="">Seleccionar Estado</option>
                    <option value="refrigerado">Refrigerado</option>
                    <option value="congelado">Congelado</option>
                </select>
                <input type="date" id="ingreso_fecha_ingreso" name="fecha_ingreso" required>
                <input type="date" id="ingreso_fecha_vencimiento" name="fecha_vencimiento" required>
                <input type="number" id="ingreso_cantidad" name="cantidad" placeholder="Cantidad" min="1" required>
                <button type="submit">Ingresar Stock</button>
            </form>
        </div>
    </div>

    <div id="modal-cambiar-estado" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Cambiar Estado</h3>
            <form id="form-cambiar-estado">
                <input type="hidden" id="cambio_id_detalle" name="id_detalle">
                <input type="text" id="cambio_nombre_producto" name="nombre_producto" readonly>
                <select id="cambio_estado_nuevo" name="estado_nuevo" required>
                    <option value="">Seleccionar Nuevo Estado</option>
                    <option value="refrigerado">Refrigerado</option>
                    <option value="congelado">Congelado</option>
                </select>
                <input type="text" id="cambio_motivo" name="motivo" placeholder="Motivo del cambio" required>
                <button type="submit">Cambiar Estado</button>
            </form>
        </div>
    </div>

    <div id="modal-mermar" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Registrar Merma</h3>
            <form id="form-mermar">
                <input type="hidden" id="merma_id_detalle" name="id_detalle">
                <input type="text" id="merma_nombre_producto" name="nombre_producto" readonly>
                <input type="number" id="merma_cantidad" name="cantidad" placeholder="Cantidad a mermar" min="1" required>
                <input type="text" id="merma_motivo" name="motivo" placeholder="Motivo de la merma" required>
                <button type="submit">Registrar Merma</button>
            </form>
        </div>
    </div>

    <script>
      const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/page_usuario.js"></script>
</body>
</html>
