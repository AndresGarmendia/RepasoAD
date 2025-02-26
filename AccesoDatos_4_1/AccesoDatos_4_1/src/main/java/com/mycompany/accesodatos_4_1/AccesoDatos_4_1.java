package com.mycompany.accesodatos_4_1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoDatos_4_1 {

    // Datos de conexión a la base de datos
    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String BD = "Deportes4";
    private static final String URL_BD = URL + BD;
    private static final String Usuario = "dam";
    private static final String Contrasena = "dam";

    public static void main(String[] args) {
        try {
            System.out.println("====================================================");
            System.out.println("            INICIANDO EJECUCIÓN DEL PROGRAMA       ");
            System.out.println("====================================================");

            // Crear la base de datos
            crearBaseDeDatos();

            // Crear el tipo compuesto y la tabla
            crearTipoYTabla();

            // Insertar los registros en una transacción
            insertarRegistros();

            // Mostrar los registros en la tabla
            mostrarTabla();

        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            // Eliminar la base de datos al final
            eliminarBaseDeDatos();
        }

        System.out.println("====================================================");
        System.out.println("         EJECUCIÓN FINALIZADA CORRECTAMENTE        ");
        System.out.println("====================================================");
    }

    public static void crearBaseDeDatos() throws SQLException {
        String sql = "CREATE DATABASE \"" + BD + "\"";
        try (Connection conexion = DriverManager.getConnection(URL + "postgres", Usuario, Contrasena);
             Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("[INFO] Base de datos 'Deportes' creada correctamente.");
        } catch (SQLException e) {
            if (e.getMessage().contains("already exists")) {
                System.out.println("[INFO] La base de datos ya existe.");
            } else {
                throw e;
            }
        }
    }

    public static void crearTipoYTabla() throws SQLException {
        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena);
             Statement stmt = conexion.createStatement()) {

            // Crear el tipo compuesto "DeportePelotaTYPE"
            String crearTipo = "CREATE TYPE DeportePelotaTYPE AS (" +
                               "Id INT, " +
                               "Nombre VARCHAR(25), " +
                               "Descripcion VARCHAR(50), " +
                               "Equipo BOOLEAN, " +
                               "Jugadores INT)";
            stmt.executeUpdate(crearTipo);
            System.out.println("[INFO] Tipo compuesto 'DeportePelotaTYPE' creado correctamente.");

            // Crear la tabla "Deportes_de_Pelota"
            String crearTabla = "CREATE TABLE Deportes_de_Pelota (" +
                                "Deporte DeportePelotaTYPE, " +
                                "Alumnos INT)";
            stmt.executeUpdate(crearTabla);
            System.out.println("[INFO] Tabla 'Deportes_de_Pelota' creada correctamente.");
        }
    }

    public static void insertarRegistros() throws SQLException {
        String insertarSQL = "INSERT INTO Deportes_de_Pelota (Deporte, Alumnos) VALUES (ROW(?, ?, ?, ?, ?)::DeportePelotaTYPE, ?)";

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena);
             PreparedStatement stmt = conexion.prepareStatement(insertarSQL)) {

            // Iniciar la transacción
            conexion.setAutoCommit(false);

            System.out.println("----------------------------------------------------");
            System.out.println("            INSERTANDO REGISTROS EN LA TABLA        ");
            System.out.println("----------------------------------------------------");

            // Insertar primer registro
            stmt.setInt(1, 1); // Id
            stmt.setString(2, "Balonmano"); // Nombre
            stmt.setString(3, "Pelota, Porteria y Mano"); // Descripción
            stmt.setBoolean(4, true); // Equipo
            stmt.setInt(5, 7); // Jugadores
            stmt.setInt(6, 41); // Alumnos
            stmt.executeUpdate();

            // Insertar segundo registro
            stmt.setInt(1, 2); // Id
            stmt.setString(2, "Padel"); // Nombre
            stmt.setString(3, "Pelota, Raqueta y Red"); // Descripción
            stmt.setBoolean(4, false); // Equipo
            stmt.setInt(5, 4); // Jugadores
            stmt.setInt(6, 76); // Alumnos
            stmt.executeUpdate();

            // Insertar tercer registro
            stmt.setInt(1, 3); // Id
            stmt.setString(2, "Baloncesto"); // Nombre
            stmt.setString(3, "Pelota, Canasta y Mano"); // Descripción
            stmt.setBoolean(4, true); // Equipo
            stmt.setInt(5, 7); // Jugadores
            stmt.setInt(6, 52); // Alumnos
            stmt.executeUpdate();

            // Confirmar la transacción
            conexion.commit();
            System.out.println("[INFO] Registros insertados correctamente en la tabla 'Deportes_de_Pelota'.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Error al insertar los registros: " + e.getMessage());
            throw e;
        }
    }

    public static void mostrarTabla() throws SQLException {
        String consultaSQL = "SELECT (Deporte).Id AS Id, " +
                             "(Deporte).Nombre AS Nombre, " +
                             "(Deporte).Descripcion AS Descripcion, " +
                             "(Deporte).Equipo AS Equipo, " +
                             "(Deporte).Jugadores AS Jugadores, " +
                             "Alumnos " +
                             "FROM Deportes_de_Pelota";

        try (Connection conexion = DriverManager.getConnection(URL_BD, Usuario, Contrasena);
             Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(consultaSQL)) {

            System.out.println("----------------------------------------------------");           
            System.out.println("                   DATOS DE LA TABLA               ");
            System.out.println("----------------------------------------------------");
            System.out.printf("%-5s %-15s %-30s %-7s %-10s %-7s%n", "Id", "Nombre", "Descripcion", "Equipo", "Jugadores", "Alumnos");

            while (rs.next()) {
                System.out.printf("%-5d %-15s %-30s %-7b %-10d %-7d%n",
                        rs.getInt("Id"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"),
                        rs.getBoolean("Equipo"),
                        rs.getInt("Jugadores"),
                        rs.getInt("Alumnos"));
            }
        }
    }

    public static void eliminarBaseDeDatos() {
        String sql = "DROP DATABASE IF EXISTS \"" + BD + "\"";

        try (Connection conexion = DriverManager.getConnection(URL + "postgres", Usuario, Contrasena);
             Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("[INFO] La base de datos '" + BD + "' se ha eliminado correctamente.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Error al eliminar la base de datos: " + e.getMessage());
        }
    }
}

