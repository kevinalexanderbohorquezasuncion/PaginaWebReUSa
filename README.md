# ReUsa Web

Aplicación web Java 21 + Jakarta Servlets + JSP + Tomcat 10.1 + PostgreSQL.

## Requisitos
- JDK 21
- Maven 3.9+
- Apache Tomcat 10.1 (para ejecución local)
- PostgreSQL (por ejemplo, Neon)

## 1. Crear la base de datos
En Neon crea una base de datos PostgreSQL. Abre `database/schema.sql` y ejecuta todo el contenido en el SQL Editor.

## 2. Configurar variables de entorno
La aplicación lee estas variables y no guarda credenciales en el código:

- `DB_URL`: cadena JDBC, por ejemplo `jdbc:postgresql://HOST/DB?sslmode=require`
- `DB_USER`: usuario de PostgreSQL
- `DB_PASSWORD`: contraseña de PostgreSQL

En Windows PowerShell, para la sesión actual:
```powershell
$env:DB_URL="jdbc:postgresql://HOST/DB?sslmode=require"
$env:DB_USER="USUARIO"
$env:DB_PASSWORD="CONTRASEÑA"
```
Sustituye los valores por los datos reales de Neon. No subas credenciales a GitHub.

## 3. Compilar
Desde la carpeta del proyecto:
```bash
mvn clean package
```
Se genera `target/ReUsa.war`.

## 4. Ejecutar en Tomcat local
Copia `target/ReUsa.war` a `TOMCAT/webapps/` e inicia Tomcat. Abre:
`http://localhost:8080/ReUsa/`

## 5. Publicar en Render
Este proyecto incluye un `Dockerfile`. En Render crea un servicio **Web Service**, conecta el repositorio GitHub y selecciona entorno Docker. Configura `DB_URL`, `DB_USER` y `DB_PASSWORD` en Environment. Render asignará una URL HTTPS.

## Funciones incluidas
- Registro con contraseña cifrada BCrypt.
- Inicio y cierre de sesión.
- Catálogo y búsqueda por nombre/categoría.
- Publicación de artículos.
- Detalle de producto y comentarios por artículo.

## Limitaciones de esta primera versión
- No incluye todavía carga de fotografías, edición/eliminación de artículos, marcar como vendido ni respuestas a comentarios.
- Las fotos requieren almacenamiento persistente externo; no deben guardarse en el disco efímero del hosting.
- Antes de usarla en producción conviene añadir protección CSRF, límites de intentos de inicio de sesión, validación adicional y moderación de comentarios.
