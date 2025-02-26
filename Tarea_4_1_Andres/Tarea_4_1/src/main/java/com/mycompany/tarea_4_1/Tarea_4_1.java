/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.tarea_4_1;

import java.sql.*;

public class Tarea_4_1 {

    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "dam";
    private static final String PASSWORD = "dam";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;

        try {
            // Conectar al servidor PostgreSQL
            connection = DriverManager.getConnection(URL + "postgres", USER, PASSWORD);
            statement = connection.createStatement();

            // Eliminar la base de datos "Deportes" si ya existe
            String dropDatabaseQuery = "DROP DATABASE IF EXISTS Deportes";
            statement.executeUpdate(dropDatabaseQuery);
            System.out.println("Base de datos 'Deportes' eliminada si existía.");

            // Crear la base de datos "Deportes"
            String createDatabaseQuery = "CREATE DATABASE Deportes";
            statement.executeUpdate(createDatabaseQuery);
            System.out.println("Base de datos 'Deportes' creada correctamente.");

            // Cerrar la conexión actual
            statement.close();
            connection.close();

            // Conectar a la nueva base de datos "Deportes"
            connection = DriverManager.getConnection(URL + "deportes", USER, PASSWORD);
            statement = connection.createStatement();

            // Crear el tipo "DeportePelotaTYPE"
            String createTypeQuery = """
                    DO $$ BEGIN 
                        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'deportepelotatype') THEN 
                            CREATE TYPE DeportePelotaTYPE AS (
                                Id INT, 
                                Nombre VARCHAR(25), 
                                Descripcion VARCHAR(50), 
                                Equipo BOOLEAN, 
                                Jugadores INT
                            ); 
                        END IF; 
                    END $$;
                    """;
            statement.executeUpdate(createTypeQuery);
            System.out.println("Tipo 'DeportePelotaTYPE' creado correctamente.");

            // Crear la tabla "Deportes_de_Pelota"
            String createTableQuery = """
                    DO $$ BEGIN 
                        IF NOT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'deportes_de_pelota') THEN 
                            CREATE TABLE Deportes_de_Pelota (
                                Deporte DeportePelotaTYPE, 
                                Alumnos INT
                            ); 
                        END IF; 
                    END $$;
                    """;
            statement.executeUpdate(createTableQuery);
            System.out.println("Tabla 'Deportes_de_Pelota' creada correctamente.");

            // Insertar registros dentro de una transacción
            connection.setAutoCommit(false);
            try (PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO Deportes_de_Pelota (Deporte, Alumnos) VALUES (ROW(?, ?, ?, ?, ?), ?)"
            )) {

                pstmt.setInt(1, 1);
                pstmt.setString(2, "Balonmano");
                pstmt.setString(3, "Pelota, Porteria y Mano");
                pstmt.setBoolean(4, true);
                pstmt.setInt(5, 7);
                pstmt.setInt(6, 41);
                pstmt.executeUpdate();

                pstmt.setInt(1, 2);
                pstmt.setString(2, "Padel");
                pstmt.setString(3, "Pelota, Raqueta y Red");
                pstmt.setBoolean(4, false);
                pstmt.setInt(5, 4);
                pstmt.setInt(6, 76);
                pstmt.executeUpdate();

                pstmt.setInt(1, 3);
                pstmt.setString(2, "Baloncesto");
                pstmt.setString(3, "Pelota, Canasta y Mano");
                pstmt.setBoolean(4, true);
                pstmt.setInt(5, 7);
                pstmt.setInt(6, 52);
                pstmt.executeUpdate();

                connection.commit();
                System.out.println("Registros insertados correctamente.");

            } catch (SQLException e) {
                connection.rollback();
                System.err.println("Error durante la inserción. Se realizó un rollback: " + e.getMessage());
            }

            // Mostrar los datos de la tabla
            System.out.println("Contenido de la tabla 'Deportes_de_Pelota':");
            String selectQuery = "SELECT * FROM Deportes_de_Pelota";
            ResultSet rs = statement.executeQuery(selectQuery);

            while (rs.next()) {
                String deporte = rs.getString(1);
                int alumnos = rs.getInt(2);

                System.out.println("Deporte: " + deporte + ", Alumnos: " + alumnos);
            }

        } catch (SQLException e) {
            System.err.println("Error en la conexión o en la consulta: " + e.getMessage());
        }
    }
}
