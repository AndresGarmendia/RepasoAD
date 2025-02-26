/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea5_2;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class ConsultasMovies {
    public static void main(String[] args) {
        MongoDatabase database = ConexionMongoDB.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        System.out.println("\n1. Obtener todos los documentos:");
        FindIterable<Document> allMovies = collection.find();
        for (Document movie : allMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n2. Obtener documentos con writer igual a 'Quentin Tarantino':");
        FindIterable<Document> tarantinoMovies = collection.find(eq("writer", "Quentin Tarantino"));
        for (Document movie : tarantinoMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n3. Obtener documentos con actors que incluyan a 'Brad Pitt':");
        FindIterable<Document> bradPittMovies = collection.find(all("actors", "Brad Pitt"));
        for (Document movie : bradPittMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n4. Obtener documentos con franchise igual a 'The Hobbit':");
        FindIterable<Document> hobbitMovies = collection.find(eq("franchise", "The Hobbit"));
        for (Document movie : hobbitMovies) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n5. Obtener todas las películas de los 90s:");
        FindIterable<Document> movies90s = collection.find(and(gte("year", 1990), lte("year", 1999)));
        for (Document movie : movies90s) {
            System.out.println(movie.toJson());
        }

        System.out.println("\n6. Obtener las películas estrenadas entre el año 2000 y 2010:");
        FindIterable<Document> movies2000s = collection.find(and(gte("year", 2000), lte("year", 2010)));
        for (Document movie : movies2000s) {
            System.out.println(movie.toJson());
        }
    }
}
