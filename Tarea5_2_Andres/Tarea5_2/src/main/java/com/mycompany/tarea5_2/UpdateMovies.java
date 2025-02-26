/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea5_2;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
import org.bson.Document;

public class UpdateMovies {
    public static void main(String[] args) {
        MongoDatabase database = ConexionMongoDB.connect();
        MongoCollection<Document> collection = database.getCollection("movies");

        System.out.println("\n1. Agregar sinopsis a 'The Hobbit: An Unexpected Journey'");
        collection.updateOne(eq("title", "The Hobbit: An Unexpected Journey"),
            set("synopsis", "A reluctant hobbit, Bilbo Baggins, sets out to the Lonely Mountain with a spirited group of dwarves to reclaim their mountain home - and the gold within it - from the dragon Smaug."));

        System.out.println("\n2. Agregar sinopsis a 'The Hobbit: The Desolation of Smaug'");
        collection.updateOne(eq("title", "The Hobbit: The Desolation of Smaug"),
            set("synopsis", "The dwarves, along with Bilbo Baggins and Gandalf the Grey, continue their quest to reclaim Erebor, their homeland, from Smaug. Bilbo Baggins is in possession of a mysterious and magical ring."));

        System.out.println("\n3. Agregar un actor llamado 'Samuel L. Jackson' a la película 'Pulp Fiction'");
        collection.updateOne(eq("title", "Pulp Fiction"),
            addToSet("actors", "Samuel L. Jackson"));
    }
}
