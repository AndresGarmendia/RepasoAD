package com.mycompany.tarea_2_5_ad;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;

/**
 * Clase para eliminar documentos en la colección movies.
 */
public class Eliminar {
    
    public static void main(String[] args) {
        MongoDatabase database = CrearBD.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        // 1. Eliminar la película "Pee Wee Herman's Big Adventure"
        collection.deleteOne(Filters.eq("title", "Pee Wee Herman's Big Adventure"));
        System.out.println("Película 'Pee Wee Herman's Big Adventure' eliminada exitosamente.");
        
        // 2. Eliminar la película "Avatar"
        collection.deleteOne(Filters.eq("title", "Avatar"));
        System.out.println("Película 'Avatar' eliminada exitosamente.");
    }
}
