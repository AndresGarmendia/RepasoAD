package com.tarea5_2;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class MongoDBTask {
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "mongo_exercise";
    private static final String COLLECTION_NAME = "movies";

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create(CONNECTION_STRING)) {
            MongoDatabase database = mongoClient.getDatabase(DATABASE_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            // Verificar si la colección existe y crearla si no
            if (!collectionExists(database, COLLECTION_NAME)) {
                database.createCollection(COLLECTION_NAME);
                System.out.println("Colección creada: " + COLLECTION_NAME);
            }

            // 1. Insertar documentos
            insertDocuments(collection);

            // 2. Consultas
            executeQueries(collection);

            // 3. Actualizaciones
            performUpdates(collection);

            // 4. Búsquedas de texto
            createTextIndex(collection);
            executeTextQueries(collection);

            // 5. Eliminaciones
            deleteDocuments(collection);

        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean collectionExists(MongoDatabase database, String collectionName) {
        for (String name : database.listCollectionNames()) {
            if (name.equals(collectionName)) return true;
        }
        return false;
    }

    private static void insertDocuments(MongoCollection<Document> collection) {
        try {
            collection.drop(); // Elimina la colección si ya existe para evitar duplicados

            collection.insertMany(Arrays.asList(
                new Document()
                    .append("title", "Fight Club")
                    .append("writer", "Chuck Palahniuk")
                    .append("year", 1999)
                    .append("actors", Arrays.asList("Brad Pitt", "Edward Norton")),

                new Document()
                    .append("title", "Pulp Fiction")
                    .append("writer", "Quentin Tarantino")
                    .append("year", 1994)
                    .append("actors", Arrays.asList("John Travolta", "Uma Thurman")),

                new Document()
                    .append("title", "Inglorious Basterds")
                    .append("writer", "Quentin Tarantino")
                    .append("year", 2009)
                    .append("actors", Arrays.asList("Brad Pitt", "Diane Kruger", "Eli Roth")),

                new Document()
                    .append("title", "The Hobbit: An Unexpected Journey")
                    .append("writer", "J.R.R. Tolkien")
                    .append("year", 2012)
                    .append("franchise", "The Hobbit"),

                new Document()
                    .append("title", "The Hobbit: The Desolation of Smaug")
                    .append("writer", "J.R.R. Tolkien")
                    .append("year", 2013)
                    .append("franchise", "The Hobbit"),

                new Document()
                    .append("title", "The Hobbit: The Battle of the Five Armies")
                    .append("writer", "J.R.R. Tolkien")
                    .append("year", 2014)
                    .append("franchise", "The Hobbit")
                    .append("synopsis", "Bilbo and Company are forced to engage in a war..."),

                new Document("title", "Pee Wee Herman's Big Adventure"),
                new Document("title", "Avatar")
            ));

            System.out.println("Documentos insertados correctamente.");
        } catch (Exception e) {
            System.err.println("Error insertando documentos: " + e.getMessage());
        }
    }

    private static void executeQueries(MongoCollection<Document> collection) {
        try {
            // 1. Obtener todos los documentos
            System.out.println("\n=== Todos los documentos ===");
            collection.find().forEach(document -> printDocument(document));

            // 2. Obtener documentos con writer igual a "Quentin Tarantino"
            System.out.println("\n=== Películas de Quentin Tarantino ===");
            collection.find(eq("writer", "Quentin Tarantino"))
                    .forEach(document -> printDocument(document));

            // 3. Obtener documentos con actors que incluyan a "Brad Pitt"
            System.out.println("\n=== Películas con Brad Pitt ===");
            collection.find(in("actors", "Brad Pitt"))
                    .forEach(document -> printDocument(document));

            // 4. Obtener documentos con franchise igual a "The Hobbit"
            System.out.println("\n=== Películas de The Hobbit ===");
            collection.find(eq("franchise", "The Hobbit"))
                    .forEach(document -> printDocument(document));

            // 5. Obtener todas las películas de los 90s
            System.out.println("\n=== Películas de los 90s ===");
            collection.find(and(gte("year", 1990), lt("year", 2000)))
                    .forEach(document -> printDocument(document));

            // 6. Obtener las películas estrenadas entre el año 2000 y 2010
            System.out.println("\n=== Películas entre 2000 y 2010 ===");
            collection.find(and(gte("year", 2000), lte("year", 2010)))
                    .forEach(document -> printDocument(document));

        } catch (Exception e) {
            System.err.println("Error en consultas: " + e.getMessage());
        }
    }

    private static void performUpdates(MongoCollection<Document> collection) {
        try {
            // 1. Agregar sinopsis a "The Hobbit: An Unexpected Journey"
            UpdateResult result1 = collection.updateOne(
                eq("title", "The Hobbit: An Unexpected Journey"),
                set("synopsis", "A reluctant hobbit, Bilbo Baggins, sets out to the Lonely Mountain with a spirited group of dwarves to reclaim their mountain home - and the gold within it - from the dragon Smaug.")
            );
            System.out.println("\nSinopsis agregada a 'The Hobbit: An Unexpected Journey': " + result1.getModifiedCount());

            // 2. Agregar sinopsis a "The Hobbit: The Desolation of Smaug"
            UpdateResult result2 = collection.updateOne(
                eq("title", "The Hobbit: The Desolation of Smaug"),
                set("synopsis", "The dwarves, along with Bilbo Baggins and Gandalf the Grey, continue their quest to reclaim Erebor, their homeland, from Smaug. Bilbo Baggins is in possession of a mysterious and magical ring.")
            );
            System.out.println("Sinopsis agregada a 'The Hobbit: The Desolation of Smaug': " + result2.getModifiedCount());

            // 3. Agregar un actor llamado "Samuel L. Jackson" a la película "Pulp Fiction"
            UpdateResult result3 = collection.updateOne(
                eq("title", "Pulp Fiction"),
                push("actors", "Samuel L. Jackson")
            );
            System.out.println("Actor 'Samuel L. Jackson' agregado a 'Pulp Fiction': " + result3.getModifiedCount());

        } catch (Exception e) {
            System.err.println("Error actualizando documentos: " + e.getMessage());
        }
    }

    private static void createTextIndex(MongoCollection<Document> collection) {
        try {
            collection.createIndex(Indexes.text("synopsis"));
            System.out.println("\nÍndice de texto creado en el campo 'synopsis'.");
        } catch (Exception e) {
            System.err.println("Error creando índice de texto: " + e.getMessage());
        }
    }

    private static void executeTextQueries(MongoCollection<Document> collection) {
        try {
            // 1. Encontrar las películas que en la sinopsis contengan la palabra "Bilbo"
            System.out.println("\n=== Películas con 'Bilbo' en la sinopsis ===");
            collection.find(text("Bilbo")).forEach(document -> printDocument(document));

            // 2. Encontrar las películas que en la sinopsis contengan la palabra "Gandalf"
            System.out.println("\n=== Películas con 'Gandalf' en la sinopsis ===");
            collection.find(text("Gandalf")).forEach(document -> printDocument(document));

            // 3. Encontrar las películas que en la sinopsis contengan la palabra "Bilbo" y no la palabra "Gandalf"
            System.out.println("\n=== Películas con 'Bilbo' y sin 'Gandalf' en la sinopsis ===");
            collection.find(text("Bilbo -Gandalf")).forEach(document -> printDocument(document));

            // 4. Encontrar las películas que en la sinopsis contengan la palabra "dwarves" o "hobbit"
            System.out.println("\n=== Películas con 'dwarves' o 'hobbit' en la sinopsis ===");
            collection.find(text("dwarves hobbit")).forEach(document -> printDocument(document));

            // 5. Encontrar las películas que en la sinopsis contengan la palabra "gold" y "dragon"
            System.out.println("\n=== Películas con 'gold' y 'dragon' en la sinopsis ===");
            collection.find(text("\"gold\" \"dragon\"")).forEach(document -> printDocument(document));

        } catch (Exception e) {
            System.err.println("Error en búsqueda de texto: " + e.getMessage());
        }
    }

    private static void deleteDocuments(MongoCollection<Document> collection) {
        try {
            // 1. Eliminar la película "Pee Wee Herman's Big Adventure"
            collection.deleteOne(eq("title", "Pee Wee Herman's Big Adventure"));
            System.out.println("\nPelícula 'Pee Wee Herman's Big Adventure' eliminada.");

            // 2. Eliminar la película "Avatar"
            collection.deleteOne(eq("title", "Avatar"));
            System.out.println("Película 'Avatar' eliminada.");

        } catch (Exception e) {
            System.err.println("Error eliminando documentos: " + e.getMessage());
        }
    }

    private static void printDocument(Document document) {
        System.out.println("----------------------------------------");
        for (String key : document.keySet()) {
            System.out.println(key + ": " + document.get(key));
        }
        System.out.println("----------------------------------------");
    }
}