/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ejercicio_2.tarea_5._hector_jimenez;

/**
 * Clase que ejecutara todas las operaciones
 * @author Hector Jimenez
 */
public class Main {

    /**
     * Clase principal encargada de ejecutar todo el programa
     * @param args
     */
    public static void main(String[] args) {
        DBmanager.conexionBD();
        Operaciones operaciones = new Operaciones(DBmanager.coleccion);

        // Insertar películas
        operaciones.insertarPeliculas();

        // Consultas
        System.out.println("Todas las películas:");
        operaciones.todasLasPeliculas();
        
        System.out.println("\nPelículas de Quentin Tarantino:");
        operaciones.peliculasPorEscritor("Quentin Tarantino");

        System.out.println("\nPelículas con Brad Pitt:");
        operaciones.peliculasPorActor("Brad Pitt");

        System.out.println("\n📌 Películas de los 90s:");
        operaciones.peliculasPorRangoDeAños(1990, 1999);

        // Actualizar
        operaciones.actualizarSynopsis("The Hobbit: An Unexpected Journey", "A reluctant hobbit, Bilbo Baggins, sets out to the Lonely Mountain with dwarves to reclaim their home from the dragon Smaug.");

        operaciones.anadirActor("Pulp Fiction", "Samuel L. Jackson");

        // Búsqueda por texto
        operaciones.crearInice();
        System.out.println("Películas con la palabra 'Bilbo':");
        operaciones.peliculasPorTexto("Bilbo");

        // Eliminar películas
        operaciones.eliminarPelicula("Pee Wee Herman's Big Adventure");
        operaciones.eliminarPelicula("Avatar");

        DBmanager.cerrarConexion();
    }
}

