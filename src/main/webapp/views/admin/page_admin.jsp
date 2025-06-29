<%-- 
    Document   : page_admin
    Created on : 23 jun. 2025, 9:29:01 p. m.
    Author     : eskmi
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel Administrativo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/categorias.css">
</head>
<body>
    <div class="sidebar">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/img/BesoFranceslogo.png" alt="Logo" class="logo">
        </div>
        <nav>
            <a href="#inicio">Inicio</a>
            <a href="#categorias">Categorías</a>
            <a href="#nuevo-producto">Nuevo Producto</a>
            <a href="#ingreso-productos">Ingreso de Productos</a>
            <a href="#productos">Productos</a>
            <a href="#cierre-stock">Cierre de Stock</a>
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

        <section id="categorias" class="content-section">
            <h2>Categorías</h2>
            <input type="text" id="buscar_categoria" placeholder="Buscar categoría..." class="buscar-input">
            <form id="form-categoria" class="form-categoria">
                <input type="text" id="nombre_categoria" name="nombre_categoria" placeholder="Nombre de la categoría" required>
                <button type="submit">Agregar Categoría</button>
            </form>

            <div class="categorias-table-container">
                <table class="categorias-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="categorias-list">
                        <!-- Aquí se cargarán las categorías vía JavaScript -->
                    </tbody>
                </table>
            </div>
        </section>

        <section id="nuevo-producto" class="content-section">
            <h2>Nuevo Producto</h2>

            <input type="text" id="buscar_producto" placeholder="Buscar producto..." class="buscar-input">

            <form id="form-producto" class="form-categoria">
                <input type="text" id="nombre_producto" name="nombre_producto" placeholder="Nombre del producto" required>
                <select id="categoria_producto" name="id_categoria" required>
                    <option value="">Seleccionar Categoría</option>
                    <c:forEach var="categoria" items="${categorias}">
                        <option value="${categoria.idCategoria}">${categoria.nombreCategoria}</option>
                    </c:forEach>
                </select>
                <button type="submit">Agregar Producto</button>
            </form>

            <div class="productos-table-container">
                <table class="productos-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Categoría</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody id="productos-list">
                        <!-- Aquí se cargarán los productos vía JavaScript -->
                    </tbody>
                </table>
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

            <!-- Botón Cerrar Stock -->
            <button id="btn-cerrar-stock" class="btn-cerrar-stock">Cerrar Stock</button>

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

        <section id="cierre-stock" class="content-section">
            <h2>Cierre de Stock</h2>

            <!-- Campos de búsqueda -->
            <div class="search-controls">
                <input type="date" id="buscar_cierre_fecha" class="buscar-input">
            </div>

            <!-- Botón Generar Reportes -->
            <button id="btn-generar-reporte-cierre" class="btn-cerrar-stock">Generar Reportes</button>

            <!-- Tabla de Cierre de Stock -->
            <div class="productos-table-container">
                <table class="productos-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Fecha</th>
                            <th>Responsable</th>
                            <th>Producto</th>
                            <th>Estado</th>
                            <th>Cantidad</th>
                        </tr>
                    </thead>
                    <tbody id="cierre-stock-list">
                        <!-- Aquí se cargarán los cierres de stock vía JavaScript -->
                    </tbody>
                </table>
            </div>

        </section>

        <!-- Nueva sección de Movimientos -->
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

    <div id="modal" class="modal">
        <div class="modal-content">
            <h3 id="modal-title"></h3>
            <form id="modal-form">
                <input type="text" id="modal-input" placeholder="Ingrese el motivo" required>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Confirmar</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Nuevo Modal para Ingreso de Stock -->
    <div id="ingreso-stock-modal" class="modal">
        <div class="modal-content">
            <h3>Ingresar Stock</h3>
            <form id="form-ingreso-stock">
                <input type="hidden" id="ingreso-stock-producto-id">
                <div class="form-group">
                    <label for="ingreso-stock-cantidad">Cantidad:</label>
                    <input type="number" id="ingreso-stock-cantidad" required min="1">
                </div>
                <div class="form-group">
                    <label for="ingreso-stock-estado">Estado:</label>
                    <select id="ingreso-stock-estado" required>
                        <option value="congelado">Congelado</option>
                        <option value="refrigerado">Refrigerado</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="ingreso-stock-fecha-ingreso">Fecha de Ingreso:</label>
                    <input type="date" id="ingreso-stock-fecha-ingreso" required>
                </div>
                <div class="form-group">
                    <label for="ingreso-stock-fecha-vencimiento">Fecha de Vencimiento:</label>
                    <input type="date" id="ingreso-stock-fecha-vencimiento" required>
                </div>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Guardar</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarIngresoStockModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Modal de Cambio de Estado -->
    <div id="cambio-estado-modal" class="modal">
        <div class="modal-content">
            <h3>Cambiar Estado</h3>
            <form id="form-cambio-estado">
                <input type="hidden" id="cambio-estado-producto-id">
                <input type="hidden" id="cambio-estado-detalle-id">
                <input type="hidden" id="cambio-estado-actual">
                <div class="form-group">
                    <label for="cambio-estado-fecha-vencimiento">Nueva Fecha de Vencimiento:</label>
                    <input type="date" id="cambio-estado-fecha-vencimiento" required>
                </div>
                <div class="form-group">
                    <label>Estado Actual:</label>
                    <p id="cambio-estado-actual-texto"></p>
                </div>
                <div class="form-group">
                    <label>Nuevo Estado:</label>
                    <p id="cambio-estado-nuevo-texto"></p>
                </div>
                <div class="form-group">
                    <label for="cambio-estado-cantidad">Cantidad a Cambiar:</label>
                    <input type="number" id="cambio-estado-cantidad" required min="1">
                </div>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Guardar</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarCambioEstadoModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Nuevo Modal para Merma -->
    <div id="merma-modal" class="modal">
        <div class="modal-content">
            <h3>Registrar Merma</h3>
            <form id="form-merma">
                <input type="hidden" id="merma-detalle-id">
                <input type="hidden" id="merma-producto-id">
                <div class="form-group">
                    <label for="merma-cantidad">Cantidad a Mermar:</label>
                    <input type="number" id="merma-cantidad" required min="1">
                </div>
                <div class="form-group">
                    <label for="merma-motivo">Motivo:</label>
                    <textarea id="merma-motivo" rows="3" required></textarea>
                </div>
                <div class="modal-buttons">
                    <button type="submit" class="btn-confirmar">Guardar Merma</button>
                    <button type="button" class="btn-cancelar" onclick="cerrarMermaModal()">Cancelar</button>
                </div>
            </form>
        </div>
    </div>

    <script>
      const contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="${pageContext.request.contextPath}/js/categorias.js"></script>
</body>
</html>
