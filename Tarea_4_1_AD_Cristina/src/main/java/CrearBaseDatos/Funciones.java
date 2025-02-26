package CrearBaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Funciones {

    // Crear la base de datos
    public static void crearBD(String URL, String BD, String Usuario, String Contrasena) {
        String url = URL + "postgres"; // Conexión a la base de datos de administración
        String sql = "CREATE DATABASE \"" + BD + "\""; // Comando SQL para crear la base de datos

        // Intentar conectar a la base de datos y ejecutar el comando de creación
        try (Connection conexion = DriverManager.getConnection(url, Usuario, Contrasena);
                Statement declaracion = conexion.createStatement()) {
            declaracion.executeUpdate("DROP DATABASE IF EXISTS \"" + BD + "\""); // Eliminar base de datos si ya existe
            declaracion.executeUpdate(sql); // Crear la base de datos
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
                + // Definición del campo Id
                "Nombre VARCHAR(25),"
                + // Definición del campo Nombre
                "Descripcion VARCHAR(50),"
                + // Definición del campo Descripcion
                "Equipo BOOLEAN,"
                + // Definición del campo Equipo (verdadero o falso)
                "Jugadores INTEGER)"; // Definición del campo Jugadores (número de jugadores)

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
    public static void crearTabla(String URL_BD, String Usuario, String Contrasena, 
            String nom_Tabla, String nom_Tipo) {
        // Comando SQL para crear la tabla utilizando el tipo compuesto
        String sql = "CREATE TABLE \"" + nom_Tabla + "\" ("
                + "Deporte \"" + nom_Tipo + "\","
                + // Columna Deporte con tipo compuesto
                "Alumnos INTEGER)"; // Columna para almacenar el número de alumnos

        // Intentar conectar a la base de datos y ejecutar el comando para crear la tabla
        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena); 
                Statement declaracion = conexion.createStatement()) {
            declaracion.executeUpdate(sql); // Crear la tabla
            System.out.println("La tabla '" + nom_Tabla + "' se ha creado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage()); // Capturar error si ocurre
        }
    }

    // Insertar registros en la tabla con manejo de transacciones
    public static void insertarRegistros(String URL_BD, String Usuario, String Contrasena, String nom_Tabla) {
        // Comando SQL para insertar varios registros en la tabla
        String sql = "INSERT INTO \"" + nom_Tabla + "\" (Deporte, Alumnos) VALUES "
                + "(ROW(1, 'Balonmano', 'Pelota, Porteria y Mano', true, 7)::\"DeportePelotaTYPE\", 41), "
                + "(ROW(2, 'Padel', 'Pelota, Raqueta y Red', false, 4)::\"DeportePelotaTYPE\", 76), "
                + "(ROW(3, 'Baloncesto', 'Pelota, Canasta y Mano', true, 7)::\"DeportePelotaTYPE\", 52)";

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena)) {
            // Desactivar auto-commit para manejar transacciones manualmente
            conexion.setAutoCommit(false);

            try (Statement declaracion = conexion.createStatement()) {
                // Insertar registros
                declaracion.executeUpdate(sql);

                // Confirmar la transacción
                conexion.commit();
                System.out.println("Los registros han sido insertados correctamente.");
            } catch (SQLException e) {
                // Realizar rollback si ocurre un error
                conexion.rollback();
                System.err.println("Error al insertar los registros. Se ha "
                        + "realizado un rollback: " + e.getMessage());
            } finally {
                // Restaurar el estado de auto-commit
                conexion.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage()); // Capturar error de conexión
        }
    }

    // Mostrar los registros de la tabla en formato tabular
    public static void mostrarTabla(String URL_BD, String Usuario, String Contrasena, String nom_Tabla) {
        // Comando SQL para seleccionar los registros de la tabla
        String sql = "SELECT (Deporte).Id AS Id, (Deporte).Nombre AS Nombre, "
                + "(Deporte).Descripcion AS Descripcion, (Deporte).Equipo AS Equipo, "
                + "(Deporte).Jugadores AS Jugadores, Alumnos "
                + "FROM \"" + nom_Tabla + "\"";

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena);
                Statement declaracion = conexion.createStatement(); 
                ResultSet resultado = declaracion.executeQuery(sql)) {

            // Imprimir encabezados de la tabla en la consola
            System.out.println("+----+-------------+--------------------------+--------+-----------+---------+");
            System.out.printf("| %-2s | %-11s | %-24s | %-5s | %-8s | %-6s |\n",
                    "Id", "Nombre", "Descripcion", "Equipo", "Jugadores", "Alumnos");
            System.out.println("+----+-------------+--------------------------+--------+-----------+---------+");

            // Recorrer el resultado y mostrar cada fila con formato
            while (resultado.next()) {
                int id = resultado.getInt("Id");
                String nombre = resultado.getString("Nombre");
                String descripcion = resultado.getString("Descripcion");
                boolean equipo = resultado.getBoolean("Equipo");
                int jugadores = resultado.getInt("Jugadores");
                int alumnos = resultado.getInt("Alumnos");

                // Mostrar cada registro de la tabla en formato tabular
                System.out.printf("| %-2d | %-11s | %-24s | %-6s | %-9d | %-7d |\n",
                        id, nombre, descripcion, equipo ? "Si" : "No", jugadores, alumnos);
            }
            System.out.println("+----+-------------+--------------------------+--------+-----------+---------+");

        } catch (SQLException e) {
            System.out.println("Error al mostrar la tabla: " + e.getMessage()); // Capturar error si ocurre
        }
    }

}
