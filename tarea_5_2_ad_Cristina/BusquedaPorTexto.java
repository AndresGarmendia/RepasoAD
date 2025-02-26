package com.mycompany.tarea_2_5_ad;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import com.mongodb.client.model.Filters;

/**
 * Clase para realizar búsquedas por texto en la colección movies.
 */
public class BusquedaPorTexto {
    
    public static void main(String[] args) {
        MongoDatabase database = CrearBD.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        // 1. Encontrar las películas que en la sinopsis contengan la palabra "Bilbo"
        System.out.println("\n===== Peliculas con 'Bilbo' en la sinopsis =====");
        imprimirResultados(collection.find(Filters.regex("synopsis", "Bilbo")));
        
        // 2. Encontrar las películas que en la sinopsis contengan la palabra "Gandalf"
        System.out.println("\n===== Peliculas con 'Gandalf' en la sinopsis =====");
        imprimirResultados(collection.find(Filters.regex("synopsis", "Gandalf")));
        
        // 3. Encontrar las películas que en la sinopsis contengan la palabra "Bilbo" y no la palabra "Gandalf"
        System.out.println("\n===== Peliculas con 'Bilbo' pero sin 'Gandalf' en "
                + "la sinopsis =====");
        imprimirResultados(collection.find(Filters.and(Filters.regex("synopsis", 
                "Bilbo"), Filters.not(Filters.regex("synopsis", "Gandalf")))));
        
        // 4. Encontrar las películas que en la sinopsis contengan la palabra "dwarves" o "hobbit"
        System.out.println("\n===== Peliculas con 'dwarves' o 'hobbit' en la sinopsis =====");
        imprimirResultados(collection.find(Filters.or(Filters.regex("synopsis",
                "dwarves"), Filters.regex("synopsis", "hobbit"))));
        
        // 5. Encontrar las películas que en la sinopsis contengan la palabra "gold" y "dragon"
        System.out.println("\n===== Peliculas con 'gold' y 'dragon' en la sinopsis =====");
        imprimirResultados(collection.find(Filters.and(Filters.regex("synopsis", 
                "gold"), Filters.regex("synopsis", "dragon"))));
    }
    
    public static void imprimirResultados(FindIterable<Document> iterable) {
        for (Document doc : iterable) {
            System.out.println("----------------------------");
            System.out.println(doc.toJson());
            System.out.println("----------------------------");
        }
    }
}
