/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea_2_5_ad;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Arrays;

/**
 *
 * @author Usuario
 */
public class InsertarDatos {

    public static void main(String[] args) {
        MongoDatabase database = CrearBD.connect();
        MongoCollection<Document> collection = database.getCollection("movies");

        // Insertar documentos
        collection.insertMany(Arrays.asList(
                new Document("title", "Fight Club")
                        .append("writer", "Chuck Palahniuk")
                        .append("year", 1999)
                        .append("actors", Arrays.asList("Brad Pitt", "Edward "
                                + "Norton")),
                new Document("title", "Pulp Fiction")
                        .append("writer", "Quentin Tarantino")
                        .append("year", 1994)
                        .append("actors", Arrays.asList("John Travolta", "Uma "
                                + "Thurman")),
                new Document("title", "Inglorious Basterds")
                        .append("writer", "Quentin Tarantino")
                        .append("year", 2009)
                        .append("actors", Arrays.asList("Brad Pitt", "Diane "
                                + "Kruger", "Eli Roth")),
                new Document("title", "The Hobbit: An Unexpected Journey")
                        .append("writer", "J.R.R. Tolkien")
                        .append("year", 2012)
                        .append("franchise", "The Hobbit"),
                new Document("title", "The Hobbit: The Desolation of Smaug")
                        .append("writer", "J.R.R. Tolkien")
                        .append("year", 2013)
                        .append("franchise", "The Hobbit"),
                new Document("title", "The Hobbit: The Battle of the Five Armies")
                        .append("writer", "J.R.R. Tolkien")
                        .append("year", 2012)
                        .append("franchise", "The Hobbit")
                        .append("synopsis", "Bilbo and Company are forced to "
                                + "engage in a war against an array of combatants "
                                + "and keep the Lonely Mountain from falling into "
                                + "the hands of a rising darkness."),
                new Document("title", "Pee Wee Herman's Big Adventure"),
                new Document("title", "Avatar")
        ));

        System.out.println("Documentos insertados exitosamente.");
    }
}
