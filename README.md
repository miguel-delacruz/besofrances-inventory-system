# 🍽️ BesoFrances - Sistema de Gestión de Inventario

Sistema web de gestión de inventario para el restaurante BesoFrances, convertido de PHP a JSP con Jakarta EE y Tomcat 11.

## 📋 Características

- **Gestión de Productos**: Agregar, editar, eliminar productos con categorías
- **Control de Stock**: Ingreso de stock, mermas, cambios de estado
- **Gestión de Categorías**: Organización de productos por categorías
- **Historial de Acciones**: Registro completo de todas las operaciones
- **Cierre de Stock**: Generación de reportes de inventario
- **Autenticación**: Sistema de login con roles (Administrador, Entrenador, FullTime, PartTime)
- **Interfaz Responsiva**: Diseño moderno y adaptable

## 🏗️ Arquitectura

### Capas del Sistema
- **Presentación**: JSP + CSS + JavaScript
- **Control**: Servlets (Jakarta EE)
- **Acceso a Datos**: DAOs (Data Access Objects)
- **Base de Datos**: MySQL

### DAOs Implementados
- `ProductoDao`: Gestión completa de productos y stock
- `CategoriaDao`: Administración de categorías
- `UsuarioDao`: Autenticación y gestión de usuarios
- `HistorialDao`: Registro de acciones del sistema
- `CierreStockDao`: Generación de cierres de inventario
- `MovimientoDao`: Consulta de movimientos históricos

## 🚀 Instalación

### Prerrequisitos
- Java 17 o superior
- Apache Tomcat 11
- MySQL 8.0 o superior
- Maven (opcional, para compilación)

### 1. Configuración de la Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE analisisbf CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE analisisbf;

-- Importar el archivo SQL proporcionado
-- El archivo contiene todas las tablas y datos de ejemplo
```

### 2. Configuración del Proyecto

1. **Clonar o descargar** el proyecto
2. **Configurar la conexión** en `src/main/java/com/mycompany/besofrances/config/Conexion.java`:
   ```java
   private static final String HOST = "localhost";
   private static final String USER = "root";
   private static final String PASS = "";
   private static final String DB = "analisisbf";
   ```

### 3. Compilación y Despliegue

#### Opción A: Usando Maven
```bash
mvn clean package
```

#### Opción B: Usando IDE (NetBeans, Eclipse, IntelliJ)
1. Importar como proyecto Maven
2. Compilar el proyecto
3. Desplegar en Tomcat

### 4. Despliegue en Tomcat

1. Copiar el archivo `target/BesoFrances-1.0-SNAPSHOT.war` a `$TOMCAT_HOME/webapps/`
2. Iniciar Tomcat
3. Acceder a `http://localhost:8080/BesoFrances-1.0-SNAPSHOT/`

## 👥 Usuarios de Prueba

### Administrador
- **Usuario**: `nicolead`
- **Contraseña**: `123456`
- **Rol**: Administrador
- **Perfil**: Admin

### Usuario Regular
- **Usuario**: `richardpt`
- **Contraseña**: `123456`
- **Rol**: PartTime
- **Perfil**: Usuario

## 📊 Estructura de la Base de Datos

### Tablas Principales
- `usuarios`: Información de usuarios y autenticación
- `rol`: Roles del sistema (Administrador, Entrenador, FullTime, PartTime)
- `producto`: Productos del inventario
- `categoria`: Categorías de productos
- `detalle_producto`: Stock y estados de productos
- `historial_accion`: Registro de todas las acciones
- `cierre_stock`: Reportes de cierre de inventario

### Relaciones
- Usuarios → Roles (1:N)
- Productos → Categorías (N:1)
- Productos → Detalle_Producto (1:N)
- Usuarios → Historial_Accion (1:N)

## 🔧 APIs REST

### Productos (`/api/productos`)
- `GET ?action=listar` - Listar productos con stock
- `GET ?action=listar_productos_simples` - Listar productos básicos
- `POST ?action=agregar` - Agregar producto
- `POST ?action=editar` - Editar producto
- `POST ?action=eliminar` - Eliminar producto
- `POST ?action=ingresar_stock` - Ingresar stock
- `POST ?action=cambiar_estado` - Cambiar estado de producto
- `POST ?action=mermar` - Registrar merma
- `POST ?action=cerrar_stock` - Generar cierre de stock
- `GET ?action=listar_cierres_stock` - Listar cierres
- `GET ?action=listar_movimientos` - Listar movimientos

### Categorías (`/api/categorias`)
- `GET ?action=listar` - Listar categorías
- `POST ?action=agregar` - Agregar categoría
- `POST ?action=editar` - Editar categoría
- `POST ?action=eliminar` - Eliminar categoría

## 🎨 Interfaz de Usuario

### Páginas Principales
- **Login**: `/views/login.jsp`
- **Selección de Perfil**: `/views/roles/seleccionar_perfil.jsp`
- **Panel Admin**: `/views/admin/page_admin.jsp`
- **Panel Usuario**: `/views/usuario/page_usuario.jsp`

### Características de la UI
- Diseño responsivo
- Modales para formularios
- Búsqueda en tiempo real
- Tablas dinámicas
- Notificaciones de estado

## 🔒 Seguridad

- **Autenticación**: Login con usuario y contraseña MD5
- **Autorización**: Control de acceso por roles
- **Sesiones**: Gestión de sesiones con filtros
- **Validación**: Validación de datos en frontend y backend

## 📝 Logs y Auditoría

- **Historial Completo**: Todas las acciones se registran
- **Información Detallada**: Usuario, fecha, acción, motivo
- **Trazabilidad**: Seguimiento completo de cambios en productos

## 🛠️ Desarrollo

### Estructura del Proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/mycompany/besofrances/
│   │       ├── config/          # Configuración y conexión
│   │       ├── controller/      # Servlets
│   │       ├── dao/            # Data Access Objects
│   │       ├── filter/         # Filtros de seguridad
│   │       └── model/          # Clases modelo
│   ├── resources/
│   │   └── META-INF/
│   └── webapp/
│       ├── css/               # Estilos
│       ├── js/                # JavaScript
│       ├── img/               # Imágenes
│       └── views/             # Páginas JSP
```

### Tecnologías Utilizadas
- **Backend**: Java 17, Jakarta EE 11, Servlets
- **Frontend**: JSP, CSS3, JavaScript (ES6+)
- **Base de Datos**: MySQL 8.0
- **Servidor**: Apache Tomcat 11
- **Build Tool**: Maven

## 🐛 Solución de Problemas

### Error de Conexión a BD
1. Verificar que MySQL esté ejecutándose
2. Confirmar credenciales en `Conexion.java`
3. Verificar que la base de datos `analisisbf` exista

### Error 404 en Servlets
1. Verificar mapeos en `@WebServlet`
2. Confirmar que el WAR se desplegó correctamente
3. Revisar logs de Tomcat

### Error de Autenticación
1. Verificar que el usuario exista en la BD
2. Confirmar que la contraseña esté en MD5
3. Verificar permisos de rol

## 📞 Soporte

Para reportar problemas o solicitar nuevas funcionalidades:
1. Revisar la documentación
2. Verificar logs del servidor
3. Probar con usuarios de ejemplo
4. Contactar al equipo de desarrollo

## 📄 Licencia

Este proyecto es propiedad de BesoFrances y está destinado para uso interno del restaurante.

---

**Versión**: 1.0-SNAPSHOT  
**Última actualización**: Junio 2025  
**Desarrollado para**: BesoFrances Restaurant 