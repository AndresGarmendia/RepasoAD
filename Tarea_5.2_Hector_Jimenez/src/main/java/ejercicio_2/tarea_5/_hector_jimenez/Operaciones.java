/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio_2.tarea_5._hector_jimenez;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;

/**
 * Clase encargada de almacenar todas las acciones
 * @author Hector Jimenez
 */
public class Operaciones {
    private final MongoCollection<Document> coleccion;

    /**
     * Constructor prinicipal de las operaciones
     * @param coleccion Es lo equivalente a una especie de bd en MongoDB
     */
    public Operaciones(MongoCollection<Document> coleccion) {
        this.coleccion = coleccion;
    }

    /**
     * Método para insertar películas
     */
    public void insertarPeliculas() {
        coleccion.insertMany(Arrays.asList(
            new Document("title", "Fight Club")
                .append("writer", "Chuck Palahniuk")
                .append("year", 1999)
                .append("actors", Arrays.asList("Brad Pitt", "Edward Norton")),
            new Document("title", "Pulp Fiction")
                .append("writer", "Quentin Tarantino")
                .append("year", 1994)
                .append("actors", Arrays.asList("John Travolta", "Uma Thurman")),
            new Document("title", "Inglorious Basterds")
                .append("writer", "Quentin Tarantino")
                .append("year", 2009)
                .append("actors", Arrays.asList("Brad Pitt", "Diane Kruger", "Eli Roth"))
        ));
        System.out.println(" ¡¡Películas insertadas correctamente !! ");
    }

    /**
     *Método para localizar todas las películas
     */
    public void todasLasPeliculas() {
        for (Document doc : coleccion.find()) {
            System.out.println(doc.toJson());
        }
    }

    /**
     * Método de búsqueda por escritor
     * @param escritor nombre del escritor a filtrar
     */
    public void peliculasPorEscritor(String escritor) {
        for (Document doc : coleccion.find(new Document("writer", escritor))) {
            System.out.println(doc.toJson());
        }
    }

    /**
     * Método de búsqueda por nombre de autor
     * @param actor nombre del autor a filtrar
     */
    public void peliculasPorActor(String actor) {
        for (Document doc : coleccion.find(new Document("actors", actor))) {
            System.out.println(doc.toJson());
        }
    }

    /**
     * Método de búsqueda por intérvalo de fechas
     * @param anoInicial año inicial del cual se quiere empezar a filtrar
     * @param anoFinal año final hasta el que se quiere filtrar
     */
    public void peliculasPorRangoDeAños(int anoInicial, int anoFinal) {
        for (Document doc : coleccion.find(new Document("year", new Document("$gte", anoInicial).append("$lte", anoFinal)))) {
            System.out.println(doc.toJson());
        }
    }
    
    /**
     * Método para la actualización de la synopsis de una película
     * @param titulo título de la película a actualizar
     * @param synopsis Synopsis a esribir en dicho titulo
     */
    public void actualizarSynopsis(String titulo, String synopsis) {
        coleccion.updateOne(new Document("title", titulo),
            new Document("$set", new Document("synopsis", synopsis)));
        System.out.println("Sinopsis actualizada para: " + titulo);
    }    
    
    /**
     * Método para añadir actores en películas
     * @param titulo título de la película a añadir
     * @param actor actores a añadir a dicha película
     */
    public void anadirActor(String titulo, String actor) {
        coleccion.updateOne(new Document("title", titulo),
            new Document("$push", new Document("actors", actor)));
        System.out.println("Actor agregado a la película: " + actor);
    }
    
    /**
     * Método para crear un Índice
     */
    public void crearInice() {
        coleccion.createIndex(new Document("synopsis", "text"));
        System.out.println("Indice de texto creado.");
    }

    /**
     * Método para buscar películas por cualquier texto
     * @param texto el texto por el que buscar
     */
    public void peliculasPorTexto(String texto) {
        for (Document doc : coleccion.find(new Document("$text", new Document("$search", texto)))) {
            System.out.println(doc.toJson());
        }
    }    
    
    /**
     * Método para eliminar una película por titulo
     * @param titulo título de la película a eliminar
     */
    public void eliminarPelicula(String titulo) {
        coleccion.deleteOne(new Document("title", titulo));
        System.out.println("Película eliminada: " + titulo);
    }    
}

