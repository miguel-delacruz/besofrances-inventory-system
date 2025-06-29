package com.mycompany.besofrances.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.mycompany.besofrances.dao.CategoriaDao;
import com.mycompany.besofrances.dao.HistorialDao;
import com.mycompany.besofrances.model.Categoria;
import com.mycompany.besofrances.model.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(
    name = "CategoriasApiServlet",
    urlPatterns = {"/api/categorias"},
    loadOnStartup = 1
)
public class CategoriasApiServlet extends HttpServlet {
    
    private CategoriaDao categoriaDao;
    private HistorialDao historialDao;
    
    @Override
    public void init() throws ServletException {
        categoriaDao = new CategoriaDao();
        historialDao = new HistorialDao();
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
                    listarCategorias(request, response);
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
                    agregarCategoria(request, response);
                    break;
                case "editar":
                    editarCategoria(request, response);
                    break;
                case "eliminar":
                    eliminarCategoria(request, response);
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

    private void listarCategorias(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String buscar = request.getParameter("buscar");
        List<Categoria> categorias = categoriaDao.listarCategorias(buscar);
        
        JSONArray categoriasJson = new JSONArray();
        for (Categoria categoria : categorias) {
            JSONObject categoriaJson = new JSONObject();
            categoriaJson.put("id_categoria", categoria.getIdCategoria());
            categoriaJson.put("nombre_categoria", categoria.getNombreCategoria());
            categoriasJson.put(categoriaJson);
        }
        
        JSONObject result = new JSONObject();
        result.put("ok", true);
        result.put("categorias", categoriasJson);
        sendJsonResponse(response, result);
    }

    private void agregarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String nombre = request.getParameter("nombre_categoria");
        
        if (nombre == null || nombre.trim().isEmpty()) {
            sendJsonResponse(response, false, "El nombre es requerido");
            return;
        }
        
        int resultado = categoriaDao.agregarCategoria(nombre.trim());
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Agregar Categoría", null, "Categoría agregada: " + nombre);
            sendJsonResponse(response, true, "Categoría agregada correctamente");
        } else {
            sendJsonResponse(response, false, "Error al agregar la categoría");
        }
    }

    private void editarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));
        String nombre = request.getParameter("nombre_categoria");
        
        if (nombre == null || nombre.trim().isEmpty()) {
            sendJsonResponse(response, false, "El nombre es requerido");
            return;
        }
        
        int resultado = categoriaDao.editarCategoria(idCategoria, nombre.trim());
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Editar Categoría", null, "Categoría editada: " + nombre);
            sendJsonResponse(response, true, "Categoría editada correctamente");
        } else {
            sendJsonResponse(response, false, "Error al editar la categoría");
        }
    }

    private void eliminarCategoria(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        int idCategoria = Integer.parseInt(request.getParameter("id_categoria"));
        
        int resultado = categoriaDao.eliminarCategoria(idCategoria);
        
        if (resultado > 0) {
            int idUsuario = obtenerIdUsuario(request);
            historialDao.registrarHistorial(idUsuario, "Eliminar Categoría", null, "Categoría eliminada ID: " + idCategoria);
            sendJsonResponse(response, true, "Categoría eliminada correctamente");
        } else {
            sendJsonResponse(response, false, "Error al eliminar la categoría");
        }
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