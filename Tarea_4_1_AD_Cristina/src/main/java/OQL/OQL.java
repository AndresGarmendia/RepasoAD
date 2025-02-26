
package OQL;

import java.sql.*;

/**
 *
 * @author Alumno
 */
public class OQL {

    // Crear la base de datos
    public static void crearBD(String URL, String BD, String Usuario, String Contrasena) {
        String url = URL + "postgres"; // Conexión a la base de datos de administración
        String sql = "CREATE DATABASE \"" + BD + "\""; // Comando SQL para crear la base de datos

        // Intentar conectar a la base de datos y ejecutar el comando de creación
        try (Connection conexion = DriverManager.getConnection(url, Usuario, Contrasena); 
             Statement declaracion = conexion.createStatement()) {
            declaracion.executeUpdate("DROP DATABASE IF EXISTS \"" + BD + "\""); // Eliminar base de datos si ya existe
            declaracion.executeUpdate(sql); // Crear la nueva base de datos
            System.out.println("La base de datos " + BD + " ha sido creada y estas conectada a ella.");
        } catch (SQLException e) {
            System.err.println("Error al crear la base de datos: " + e.getMessage()); // Capturar error si ocurre
        }
    }

    // Crear el tipo de dato compuesto
    public static void crearTipo(String URL_BD, String Usuario, String Contrasena, String nom_Tipo) {
        // Comando SQL para crear un tipo compuesto
        String sql = "CREATE TYPE \"" + nom_Tipo + "\" AS (" 
                   + "Id INTEGER," 
                   + "Nombre VARCHAR(25),"
                   + "Descripcion VARCHAR(50),"
                   + "Equipo BOOLEAN," 
                   + "Jugadores INTEGER)"; 

        // Intentar conectar a la base de datos y ejecutar el comando para crear el tipo
        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena); 
             Statement declaracion = conexion.createStatement()) {
            declaracion.executeUpdate("DROP TYPE IF EXISTS \"" + nom_Tipo + "\""); // Eliminar tipo si ya existe
            declaracion.executeUpdate(sql); // Crear el nuevo tipo
            System.out.println("El tipo '" + nom_Tipo + "' se ha creado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear el tipo: " + e.getMessage()); // Capturar error si ocurre
        }
    }

    // Crear la tabla en la base de datos
    public static void crearTabla(String URL_BD, String Usuario, String Contrasena, String nom_Tabla, String nom_Tipo) {
        // Comando SQL para crear la tabla utilizando el tipo compuesto
        String sql = "CREATE TABLE \"" + nom_Tabla + "\" ("
                   + "Deporte \"" + nom_Tipo + "\"," // Columna Deporte con tipo compuesto
                   + "Alumnos INTEGER)"; // Columna para almacenar el número de alumnos

        // Intentar conectar a la base de datos y ejecutar el comando para crear la tabla
        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena); 
             Statement declaracion = conexion.createStatement()) {
            declaracion.executeUpdate(sql); // Crear la tabla
            System.out.println("La tabla '" + nom_Tabla + "' se ha creado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage()); // Capturar error si ocurre
        }
    }

    // Insertar registros en la tabla con transacciones y rollback en caso de error
    public static void insertarRegistros(String URL_BD, String Usuario, String Contrasena, String nom_Tabla) {
        String sqlInsert = "INSERT INTO \"" + nom_Tabla + "\" (Deporte, Alumnos) VALUES (?, ?)"; // Comando SQL para insertar

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena)) {
            conexion.setAutoCommit(false); // Iniciar transacción, evitando confirmación automática de las operaciones

            // Intentar insertar registros en la tabla
            try (PreparedStatement ps = conexion.prepareStatement(sqlInsert)) {
                // Insertar primer registro (Balonmano)
                ps.setObject(1, new Object[]{1, "Balonmano", "Pelota, Porteria y Mano", true, 7}, java.sql.Types.OTHER);
                ps.setInt(2, 41);
                ps.executeUpdate();

                // Insertar segundo registro (Padel)
                ps.setObject(1, new Object[]{2, "Padel", "Pelota, Raqueta y Red", false, 4}, java.sql.Types.OTHER);
                ps.setInt(2, 76);
                ps.executeUpdate();

                // Insertar tercer registro (Baloncesto)
                ps.setObject(1, new Object[]{3, "Baloncesto", "Pelota, Canasta y Mano", true, 7}, java.sql.Types.OTHER);
                ps.setInt(2, 52);
                ps.executeUpdate();

                conexion.commit(); // Confirmar transacción si todo ha ido bien
                System.out.println("Registros insertados correctamente.");
            } catch (SQLException e) {
                conexion.rollback(); // Revertir cambios si ocurre un error
                System.err.println("Error al insertar registros: " + e.getMessage()); // Capturar error si ocurre
            }
        } catch (SQLException e) {
            System.err.println("Error de conexión al insertar registros: " + e.getMessage()); // Capturar error de conexión
        }
    }

    // Mostrar los registros de la tabla en formato tabular
    public static void mostrarTabla(String URL_BD, String Usuario, String Contrasena, String nom_Tabla) {
        String sql = "SELECT (Deporte).Id AS Id, (Deporte).Nombre AS Nombre, "
                   + "(Deporte).Descripcion AS Descripcion, (Deporte).Equipo AS Equipo, "
                   + "(Deporte).Jugadores AS Jugadores, Alumnos "
                   + "FROM \"" + nom_Tabla + "\""; // Comando SQL para seleccionar los registros de la tabla

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena); 
             Statement declaracion = conexion.createStatement(); 
             ResultSet resultado = declaracion.executeQuery(sql)) {

            // Mostrar encabezado de la tabla en consola
            System.out.println("+----+-------------+--------------------------+-------+----------+--------+");
            System.out.printf("| %-2s | %-11s | %-24s | %-5s | %-8s | %-6s |\n", 
                              "Id", "Nombre", "Descripcion", "Equipo", "Jugadores", "Alumnos");
            System.out.println("+----+-------------+--------------------------+-------+----------+--------+");

            // Recorrer los resultados y mostrar cada registro
            while (resultado.next()) {
                int id = resultado.getInt("Id");
                String nombre = resultado.getString("Nombre");
                String descripcion = resultado.getString("Descripcion");
                boolean equipo = resultado.getBoolean("Equipo");
                int jugadores = resultado.getInt("Jugadores");
                int alumnos = resultado.getInt("Alumnos");

                // Mostrar cada registro de la tabla en formato tabular
                System.out.printf("| %-2d | %-11s | %-24s | %-5s | %-8d | %-6d |\n",
                                  id, nombre, descripcion, equipo ? "Sí" : "No", jugadores, alumnos);
            }
            System.out.println("+----+-------------+--------------------------+-------+----------+--------+");

        } catch (SQLException e) {
            System.err.println("Error al mostrar la tabla: " + e.getMessage()); // Capturar error si ocurre
        }
    }

    public static void main(String[] args) {
        String URL = "jdbc:postgresql://localhost:5432/"; // URL para la conexión a PostgreSQL
        String BD = "Deporte"; // Nombre de la base de datos a crear
        String URL_BD = URL + BD; // URL completa de la base de datos
        String Usuario = "dam"; // Nombre de usuario para la conexión
        String Contrasena = "dam"; // Contraseña para la conexión

        // Llamadas a las funciones para crear la base de datos, el tipo, la tabla, insertar y mostrar registros
        crearBD(URL, BD, Usuario, Contrasena); // Crear la base de datos
        crearTipo(URL_BD, Usuario, Contrasena, "DeportePelotaTYPE"); // Crear el tipo de dato compuesto
        crearTabla(URL_BD, Usuario, Contrasena, "DeportesDePelota", "DeportePelotaTYPE"); // Crear la tabla
        insertarRegistros(URL_BD, Usuario, Contrasena, "DeportesDePelota"); // Insertar registros en la tabla
        mostrarTabla(URL_BD, Usuario, Contrasena, "DeportesDePelota"); // Mostrar la tabla
    }
}
