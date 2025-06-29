package com.mycompany.besofrances.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.besofrances.dao.ProductoDao;
import com.mycompany.besofrances.dao.CategoriaDao;
import com.mycompany.besofrances.dao.HistorialDao;
import com.mycompany.besofrances.dao.CierreStockDao;
import com.mycompany.besofrances.dao.MovimientoDao;
import com.mycompany.besofrances.model.Producto;
import com.mycompany.besofrances.model.Categoria;
import com.mycompany.besofrances.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(
    name = "ProductosApiServlet",
    urlPatterns = {"/api/productos"},
    loadOnStartup = 1
)
public class ProductosApiServlet extends HttpServlet {
    
    private ProductoDao productoDao;
    private CategoriaDao categoriaDao;
    private HistorialDao historialDao;
    private CierreStockDao cierreStockDao;
    private MovimientoDao movimientoDao;
    
    @Override
    public void init() throws ServletException {
        productoDao = new ProductoDao();
        categoriaDao = new CategoriaDao();
        historialDao = new HistorialDao();
        cierreStockDao = new CierreStockDao();
        movimientoDao = new MovimientoDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            sendJsonResponse(response, false, "No hay sesión activa");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            sendJsonResponse(response, false, "Acción no especificada");
            return;
        }

        try {
            switch (action) {
                case "listar":
                    listarProductos(request, response);
                    break;
                case "listar_productos_simples":
                    listarProductosSimples(request, response);
                    break;
                case "listar_cierres_stock":
                    listarCierresStock(request, response);
                    break;
                case "listar_movimientos":
                    listarMovimientos(request, response);
                    break;
                default:
                    sendJsonResponse(response, false, "Acción no válida");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Error interno: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Verificar sesión
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            sendJsonResponse(response, false, "No hay sesión activa");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            sendJsonResponse(response, false, "Acción no especificada");
            return;
        }

        try {
            switch (action) {
                case "agregar":
                    agregarProducto(request, response);
                    break;
                case "editar":
                    editarProducto(request, response);
                    break;
                case "eliminar":
                    eliminarProducto(request, response);
                    break;
                case "ingresar_stock":
                    ingresarStock(request, response);
                    break;
                case "cambiar_estado":
                    cambiarEstado(request, response);
                    break;
                case "mermar":
                    mermar(request, response);
                    break;
                case "cerrar_stock":
                    cerrarStock(request, response);
                    break;
                default:
                    sendJsonResponse(response, false, "Acción no válida");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJsonResponse(response, false, "Error interno: " + e.getMessage());
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String buscar = request.getParameter("buscar");
        List<Producto> productos = productoDao.listarProductosConStock(buscar);
        
        JSONArray productosJson = new JSONArray();
        for (Producto producto : productos) {
            JSONObject productoJson = new JSONObject();
            productoJson.put("id_producto", producto.getIdProducto());
            productoJson.put("nombre_producto", producto.getNombreProducto());
            productoJson.put("nombre_categoria", producto.getNombreCategoria());
            productoJson.put("id_detalle", producto.getIdDetalle());
            productoJson.put("fecha_ingreso", producto.getFechaIngreso());
            productoJson.put("fecha_vencimiento", producto.getFechaVencimiento());
            productoJson.put("estado", producto.getEstado());
            productoJson.put("cantidad", producto.getCantidad());
            productosJson.put(productoJson);
        }
        
        JSONObject result = new JSONObject();
        result.put("ok", true);
        result.put("productos", productosJson);
        
        sendJsonResponse(response, result);
    }

    private void listarProductosSimples(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String buscar = request.getParameter("buscar");
        List<Producto> productos = productoDao.listarProductosSimples(buscar);
        
        JSONArray productosJson = new JSONArray();
        for (Producto producto : productos) {
            JSONObject productoJson = new JSONObject();
            productoJson.put("id_producto", producto.getIdProducto());
            productoJson.put("nombre_producto", producto.getNombreProducto());
            productoJson.put("nombre_categoria", producto.getNombreCategoria());
            productosJson.put(productoJson);
        }
        
        JSONObject result = new JSONObject();
        result.put("ok", true);
        result.put("productos", productosJson);
        
        sendJsonResponse(response, result);
    }

    private void agregarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String nombre = request.getParameter("nombre");
        int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));
        
        if (nombre == null || nombre.trim().isEmpty()) {
            sendJsonResponse(response, false, "El nombre del producto es obligatorio");
            return;
        }
        
        int resultado = productoDao.agregarProducto(nombre, idCategoria);
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Agregar Producto", null, "Producto agregado: " + nombre);
            sendJsonResponse(response, true, "Producto agregado correctamente");
        } else {
            sendJsonResponse(response, false, "Error al agregar el producto");
        }
    }

    private void editarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        String nombre = request.getParameter("nombre");
        int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));
        
        if (nombre == null || nombre.trim().isEmpty()) {
            sendJsonResponse(response, false, "El nombre del producto es obligatorio");
            return;
        }
        
        int resultado = productoDao.editarProducto(idProducto, nombre, idCategoria);
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Editar Producto", idProducto, "Producto editado: " + nombre);
            sendJsonResponse(response, true, "Producto editado correctamente");
        } else {
            sendJsonResponse(response, false, "Error al editar el producto");
        }
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        String nombreProducto = productoDao.obtenerNombreProducto(idProducto);
        
        int resultado = productoDao.eliminarProducto(idProducto);
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Eliminar Producto", idProducto, "Producto eliminado: " + nombreProducto);
            sendJsonResponse(response, true, "Producto eliminado correctamente");
        } else {
            sendJsonResponse(response, false, "Error al eliminar el producto");
        }
    }

    private void ingresarStock(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idProducto = Integer.parseInt(request.getParameter("id_producto"));
        String estado = request.getParameter("estado");
        String fechaIngreso = request.getParameter("fecha_ingreso");
        String fechaVencimiento = request.getParameter("fecha_vencimiento");
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        
        if (estado == null || estado.trim().isEmpty()) {
            sendJsonResponse(response, false, "El estado es obligatorio");
            return;
        }
        
        if (fechaIngreso == null || fechaIngreso.trim().isEmpty()) {
            sendJsonResponse(response, false, "La fecha de ingreso es obligatoria");
            return;
        }
        
        int resultado = productoDao.ingresarStock(idProducto, estado, fechaIngreso, fechaVencimiento, cantidad);
        
        if (resultado > 0) {
            String nombreProducto = productoDao.obtenerNombreProducto(idProducto);
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorialConProducto(idUsuario, idProducto, nombreProducto, "Ingresar Stock", "Stock ingresado", cantidad);
            sendJsonResponse(response, true, "Stock ingresado correctamente");
        } else {
            sendJsonResponse(response, false, "Error al ingresar el stock");
        }
    }

    private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idDetalle = Integer.parseInt(request.getParameter("id_detalle"));
        String estadoNuevo = request.getParameter("estado_nuevo");
        String motivo = request.getParameter("motivo");
        
        if (estadoNuevo == null || estadoNuevo.trim().isEmpty()) {
            sendJsonResponse(response, false, "El nuevo estado es obligatorio");
            return;
        }
        
        // Obtener estado anterior y cantidad actual
        String estadoAnterior = "Desconocido";
        int cantidad = productoDao.obtenerCantidadActual(idDetalle);
        
        // Actualizar estado en detalle_producto
        String sql = "UPDATE detalle_producto SET estado = ? WHERE id_detalle = ?";
        int resultado = com.mycompany.besofrances.config.DatabaseUtil.executeUpdate(sql, estadoNuevo, idDetalle);
        
        if (resultado > 0) {
            int idProducto = productoDao.obtenerIdProductoPorDetalle(idDetalle);
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorialConEstados(idUsuario, idProducto, "Cambiar Estado", estadoAnterior, estadoNuevo, cantidad);
            sendJsonResponse(response, true, "Estado cambiado correctamente");
        } else {
            sendJsonResponse(response, false, "Error al cambiar el estado");
        }
    }

    private void mermar(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idDetalle = Integer.parseInt(request.getParameter("id_detalle"));
        int cantidadMermar = Integer.parseInt(request.getParameter("cantidad"));
        String motivo = request.getParameter("motivo");
        
        if (cantidadMermar <= 0) {
            sendJsonResponse(response, false, "La cantidad a mermar debe ser mayor a 0");
            return;
        }
        
        int cantidadActual = productoDao.obtenerCantidadActual(idDetalle);
        
        if (cantidadMermar > cantidadActual) {
            sendJsonResponse(response, false, "No hay suficiente stock para mermar");
            return;
        }
        
        int nuevaCantidad = cantidadActual - cantidadMermar;
        int resultado = productoDao.actualizarCantidadDetalle(idDetalle, nuevaCantidad);
        
        if (resultado > 0) {
            int idProducto = productoDao.obtenerIdProductoPorDetalle(idDetalle);
            String nombreProducto = productoDao.obtenerNombreProducto(idProducto);
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorialConProducto(idUsuario, idProducto, nombreProducto, "Merma", motivo, cantidadMermar);
            sendJsonResponse(response, true, "Merma registrada correctamente");
        } else {
            sendJsonResponse(response, false, "Error al registrar la merma");
        }
    }

    private void cerrarStock(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String fechaCierre = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int idUsuario = obtenerIdUsuario(request);
        
        // Obtener todos los productos con stock
        List<Producto> productos = productoDao.listarProductosConStock(null);
        
        int registrosInsertados = 0;
        for (Producto producto : productos) {
            int resultado = cierreStockDao.insertarCierreStock(
                fechaCierre, 
                producto.getIdProducto(), 
                producto.getNombreProducto(), 
                producto.getEstado(), 
                producto.getCantidad(), 
                idUsuario
            );
            if (resultado > 0) {
                registrosInsertados++;
            }
        }
        
        if (registrosInsertados > 0) {
            historialDao.registrarHistorial(idUsuario, "Cerrar Stock", null, "Cierre de stock realizado para " + registrosInsertados + " productos");
            sendJsonResponse(response, true, "Cierre de stock realizado correctamente. " + registrosInsertados + " productos registrados.");
        } else {
            sendJsonResponse(response, false, "Error al realizar el cierre de stock");
        }
    }

    private void listarCierresStock(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String fecha = request.getParameter("fecha");
        if (fecha == null || fecha.trim().isEmpty()) {
            fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        
        List<Object[]> cierres = cierreStockDao.listarCierresStock(fecha);
        
        JSONArray cierresJson = new JSONArray();
        for (Object[] cierre : cierres) {
            JSONObject cierreJson = new JSONObject();
            cierreJson.put("id_cierre", cierre[0]);
            cierreJson.put("fecha_cierre", cierre[1]);
            cierreJson.put("responsable", cierre[2]);
            cierreJson.put("nombre_producto", cierre[3]);
            cierreJson.put("estado", cierre[4]);
            cierreJson.put("cantidad", cierre[5]);
            cierresJson.put(cierreJson);
        }
        
        JSONObject result = new JSONObject();
        result.put("ok", true);
        result.put("cierres", cierresJson);
        
        sendJsonResponse(response, result);
    }

    private void listarMovimientos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String fecha = request.getParameter("fecha");
        if (fecha == null || fecha.trim().isEmpty()) {
            fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        
        List<Object[]> movimientos = movimientoDao.listarMovimientos(fecha);
        
        JSONArray movimientosJson = new JSONArray();
        for (Object[] movimiento : movimientos) {
            JSONObject movimientoJson = new JSONObject();
            movimientoJson.put("id_historial", movimiento[0]);
            movimientoJson.put("fecha_hora", movimiento[1]);
            movimientoJson.put("responsable", movimiento[2]);
            movimientoJson.put("nombre_producto", movimiento[3]);
            movimientoJson.put("tipo_accion", movimiento[4]);
            movimientoJson.put("motivo", movimiento[5]);
            movimientoJson.put("cantidad", movimiento[6]);
            movimientoJson.put("estado_anterior", movimiento[7]);
            movimientoJson.put("estado_nuevo", movimiento[8]);
            movimientosJson.put(movimientoJson);
        }
        
        JSONObject result = new JSONObject();
        result.put("ok", true);
        result.put("movimientos", movimientosJson);
        
        sendJsonResponse(response, result);
    }

    private int obtenerIdUsuario(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("usuario") != null) {
            Usuario usuario = (Usuario) session.getAttribute("usuario");
            return usuario.getIdUsuario();
        }
        return 0;
    }

    private void sendJsonResponse(HttpServletResponse response, boolean ok, String message) throws IOException {
        JSONObject result = new JSONObject();
        result.put("ok", ok);
        result.put("message", message);
        sendJsonResponse(response, result);
    }

    private void sendJsonResponse(HttpServletResponse response, boolean ok, String message, String key, Object value) throws IOException {
        JSONObject result = new JSONObject();
        result.put("ok", ok);
        result.put("message", message);
        result.put(key, value);
        sendJsonResponse(response, result);
    }

    private void sendJsonResponse(HttpServletResponse response, JSONObject result) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(result.toString());
            out.flush();
        }
    }
} 