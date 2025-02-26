/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea5_2;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;

public class TextSearchMovies {
    public static void main(String[] args) {
        MongoDatabase database = ConexionMongoDB.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        System.out.println("\n1. Encontrar películas con 'Bilbo' en la sinopsis:");
        FindIterable<Document> bilboMovies = collection.find(text("Bilbo"));
        for (Document movie : bilboMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n2. Encontrar películas con 'Gandalf' en la sinopsis:");
        FindIterable<Document> gandalfMovies = collection.find(text("Gandalf"));
        for (Document movie : gandalfMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n3. Encontrar películas con 'Bilbo' pero no 'Gandalf' en la sinopsis:");
        FindIterable<Document> bilboNotGandalfMovies = collection.find(and(text("Bilbo"), not(text("Gandalf"))));
        for (Document movie : bilboNotGandalfMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n4. Encontrar películas con 'dwarves' o 'hobbit' en la sinopsis:");
        FindIterable<Document> dwarvesOrHobbitMovies = collection.find(text("dwarves hobbit"));
        for (Document movie : dwarvesOrHobbitMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n5. Encontrar películas con 'gold' y 'dragon' en la sinopsis:");
        FindIterable<Document> goldAndDragonMovies = collection.find(and(text("gold"), text("dragon")));
        for (Document movie : goldAndDragonMovies) {
            System.out.println(movie.toJson());
        }
    }
}

