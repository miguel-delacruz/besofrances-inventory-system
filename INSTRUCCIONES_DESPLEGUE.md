# 🚀 Instrucciones de Despliegue - BesoFrances

## 📋 Pasos para Solucionar el Error 404

### 1. **Verificar que Tomcat esté ejecutándose**
- Abrir el administrador de Tomcat: `http://localhost:8080`
- Verificar que el estado sea "Running"

### 2. **Desplegar el WAR actualizado**
1. Detener Tomcat si está ejecutándose
2. Eliminar la carpeta anterior si existe: `webapps/BesoFrances-1.0-SNAPSHOT/`
3. Copiar el nuevo WAR: `target/BesoFrances-1.0-SNAPSHOT.war` → `webapps/`
4. Iniciar Tomcat
5. Esperar a que se despliegue completamente

### 3. **Verificar el despliegue**
- Acceder a: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/test`
- Debería mostrar la página de prueba

### 4. **Probar el flujo completo**
1. Ir a: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/`
2. Debería redirigir a selección de perfil
3. Seleccionar perfil (Admin o Usuario)
4. Hacer login con:
   - **Admin**: `nicolead` / `123456`
   - **Usuario**: `richardpt` / `123456`
5. Debería redirigir al dashboard correcto

## 🔧 Servlets Mapeados

### Servlets Principales
- `/test` → TestServlet (prueba)
- `/login` → LoginServlet (autenticación)
- `/dashboard` → DashboardServlet (panel principal)
- `/seleccionar_perfil` → SeleccionarPerfilServlet
- `/logout` → LogoutServlet

### APIs REST
- `/api/productos` → ProductosApiServlet
- `/api/categorias` → CategoriasApiServlet

## 🐛 Solución de Problemas

### Error 404 - Recurso no encontrado
1. **Verificar mapeos**: Revisar `@WebServlet` en cada servlet
2. **Recompilar**: Asegurar que el WAR esté actualizado
3. **Reiniciar Tomcat**: Detener e iniciar completamente
4. **Verificar logs**: Revisar `logs/catalina.out`

### Error de Conexión a BD
1. Verificar que MySQL esté ejecutándose
2. Confirmar credenciales en `Conexion.java`
3. Probar conexión con `TestConexion.java`

### Error de Sesión
1. Verificar que las cookies estén habilitadas
2. Revisar configuración de sesiones en `web.xml`
3. Confirmar que el filtro de sesión esté funcionando

## 📁 Estructura de Archivos

```
webapps/
└── BesoFrances-1.0-SNAPSHOT/
    ├── WEB-INF/
    │   ├── classes/          # Clases compiladas
    │   ├── lib/             # Librerías
    │   └── web.xml          # Configuración web
    ├── css/                 # Estilos
    ├── js/                  # JavaScript
    ├── img/                 # Imágenes
    ├── views/               # Páginas JSP
    └── index.jsp            # Página principal
```

## 🔍 Verificación de Funcionamiento

### 1. **Página de Prueba**
- URL: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/test`
- Debe mostrar información del servlet

### 2. **Selección de Perfil**
- URL: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/seleccionar_perfil`
- Debe mostrar opciones de perfil

### 3. **Login**
- URL: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/login`
- Debe mostrar formulario de login

### 4. **Dashboard**
- URL: `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/dashboard`
- Requiere sesión activa

## 📞 Contacto

Si persisten los problemas:
1. Revisar logs de Tomcat
2. Verificar configuración de MySQL
3. Confirmar que todas las dependencias estén presentes
4. Probar con el servlet de prueba primero

---

**Última actualización**: Junio 2025 