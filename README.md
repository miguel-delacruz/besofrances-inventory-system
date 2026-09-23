# BesoFrances Inventory Management System

Web-based inventory management system developed as an academic software project for restaurant operations. The application supports product and category management, stock movements, inventory closing, audit history, authentication, and role-based access.

## Tech Stack

- **Backend:** Java 17, Jakarta EE, Servlets
- **Frontend:** JSP, JavaScript, CSS
- **Database:** MySQL 8
- **Build:** Maven
- **Application server:** Apache Tomcat 11

## Continuous Integration

This project uses GitHub Actions to automatically build and validate the application on every push and pull request to `main`.

The CI pipeline:

- Runs on Ubuntu
- Uses Java 17
- Caches Maven dependencies
- Builds the application with `mvn clean package`
- Verifies that the WAR can be generated successfully

## Key Features

- Product and category management
- Stock entry and inventory adjustments
- Waste/loss registration
- Inventory movement history
- Inventory closing and reporting
- Authentication and role-based access
- Separate administrator and user interfaces
- Audit history for inventory operations

## Architecture

The application follows a layered structure:

```text
Presentation      JSP / JavaScript / CSS
       ↓
Controllers       Jakarta Servlets
       ↓
Data Access       DAO classes
       ↓
Database          MySQL

```

The repository separates responsibilities into controllers, data-access objects, models, filters, configuration, and presentation resources.

## Project Structure

```text
src/main/
├── java/com/mycompany/besofrances/
│   ├── config/       # Database and application configuration
│   ├── controller/   # Jakarta Servlets
│   ├── dao/          # Data Access Objects
│   ├── filter/       # Session and access filters
│   └── model/        # Domain models
├── resources/
└── webapp/
    ├── css/
    ├── js/
    ├── img/
    └── views/
```

## Main Components

### Data Access

- `ProductoDao` — product and stock operations
- `CategoriaDao` — category management
- `UsuarioDao` — user authentication and access
- `HistorialDao` — audit history
- `CierreStockDao` — inventory closing
- `MovimientoDao` — inventory movement queries

### HTTP Endpoints

The application exposes servlet-based endpoints for operations such as:

- listing, creating, editing, and deleting products
- stock entry and adjustments
- waste/loss registration
- inventory closing
- movement history
- category management

## Database

The project includes `analisisbf.sql` with the database structure and sample data.

Main entities include:

- users and roles
- products and categories
- product stock details
- action history
- inventory closings

## Running Locally

### Requirements

- Java 17+
- Apache Tomcat 11
- MySQL 8+
- Maven

### 1. Prepare the database

Create the MySQL database and import `analisisbf.sql`.

```sql
CREATE DATABASE analisisbf
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
```

### 2. Configure the database connection

Update the local database settings in:

```text
src/main/java/com/mycompany/besofrances/config/Conexion.java
```

### 3. Build

```bash
mvn clean package
```

### 4. Deploy

Deploy the generated WAR file from `target/` to Apache Tomcat and start the server.

Additional deployment notes are available in:

- `INSTRUCCIONES_DESPLEGUE.md`
- `VERIFICACION_DESPLEGUE.md`

## What I Learned

This project gave me practical experience with layered Java web architecture, HTTP request handling with Servlets, the DAO pattern, relational database access, session management, role-based access control, inventory workflows, Maven builds, and deployment to Apache Tomcat.

## Security Note

This is an academic project originally developed in 2025. The current version uses MD5 for password hashing, which is **not suitable for production systems**. A production-ready version should migrate password storage to a modern password-hashing algorithm such as Argon2 or bcrypt and move database credentials out of source code into environment-based configuration.

## Future Improvements

- Replace MD5 password hashing with Argon2 or bcrypt
- Move database credentials to environment variables
- Add automated unit and integration tests
- Containerize the application and database for reproducible local environments
- Add screenshots and an architecture diagram to the project documentation

---

**Academic project · 2025**
