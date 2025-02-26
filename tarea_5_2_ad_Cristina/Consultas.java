package com.mycompany.tarea_2_5_ad;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import com.mongodb.client.model.Filters;

/**
 * Clase para realizar consultas en la colección movies.
 */
public class Consultas {
    
    public static void main(String[] args) {
        MongoDatabase database = CrearBD.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        // 1. Obtener todos los documentos
        System.out.println("\n===== Todos los documentos =====");
        imprimirResultados(collection.find());
        
        // 2. Obtener documentos con writer igual a "Quentin Tarantino"
        System.out.println("\n===== Peliculas escritas por Quentin Tarantino =====");
        imprimirResultados(collection.find(Filters.eq("writer", "Quentin Tarantino")));
        
        // 3. Obtener documentos con actors que incluyan a "Brad Pitt"
        System.out.println("\n===== Peliculas con Brad Pitt =====");
        imprimirResultados(collection.find(Filters.in("actors", "Brad Pitt")));
        
        // 4. Obtener documentos con franchise igual a "The Hobbit"
        System.out.println("\n===== Peliculas de la franquicia The Hobbit =====");
        imprimirResultados(collection.find(Filters.eq("franchise", "The Hobbit")));
        
        // 5. Obtener todas las películas de los 90s (1990-1999)
        System.out.println("\n===== Peliculas de los años 90s =====");
        imprimirResultados(collection.find(Filters.and(Filters.gte("year", 1990), 
                Filters.lte("year", 1999))));
        
        // 6. Obtener las películas estrenadas entre el año 2000 y 2010
        System.out.println("\n===== Peliculas estrenadas entre 2000 y 2010 =====");
        imprimirResultados(collection.find(Filters.and(Filters.gte("year", 2000), 
                Filters.lte("year", 2010))));
    }
    
    public static void imprimirResultados(FindIterable<Document> iterable) {
        for (Document doc : iterable) {
            System.out.println("----------------------------");
            System.out.println(doc.toJson());
            System.out.println("----------------------------");
        }
    }
}