/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea5_2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

public class EliminarMovies {
    public static void main(String[] args) {
        MongoDatabase database = ConexionMongoDB.connect();
        MongoCollection<Document> collection = database.getCollection("movies");

        System.out.println("\n1. Eliminando la película 'Pee Wee Herman's Big Adventure'");
        collection.deleteOne(eq("title", "Pee Wee Herman's Big Adventure"));
        
        System.out.println("\n2. Eliminando la película 'Avatar'");
        collection.deleteOne(eq("title", "Avatar"));
        
        System.out.println("\nPelículas eliminadas correctamente.");
    }
}
