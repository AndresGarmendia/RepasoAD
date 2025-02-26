/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea5_2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;

public class InsertMovies {
    public static void main(String[] args) {
        MongoDatabase database = ConexionMongoDB.connect();
        MongoCollection<Document> collection = database.getCollection("movies");

        Document movie1 = new Document("title", "Fight Club")
            .append("writer", "Chuck Palahniuk")
            .append("year", 1999)
            .append("actors", Arrays.asList("Brad Pitt", "Edward Norton"));

        collection.insertOne(movie1);

        System.out.println("Película insertada correctamente.");
    }
}
