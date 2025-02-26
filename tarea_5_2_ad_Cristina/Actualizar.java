package com.mycompany.tarea_2_5_ad;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

/**
 * Clase para actualizar documentos en la colección movies.
 */
public class Actualizar {
    
    public static void main(String[] args) {
        MongoDatabase database = CrearBD.connect();
        MongoCollection<Document> collection = database.getCollection("movies");
        
        // 1. Agregar sinopsis a "The Hobbit: An Unexpected Journey"
        collection.updateOne(
            Filters.eq("title", "The Hobbit: An Unexpected Journey"),
            Updates.set("synopsis", "A reluctant hobbit, Bilbo Baggins, sets out"
                    + " to the Lonely Mountain with a spirited group of dwarves "
                    + "to reclaim their mountain home - and the gold within it "
                    + "- from the dragon Smaug.")
        );
        
        // 2. Agregar sinopsis a "The Hobbit: The Desolation of Smaug"
        collection.updateOne(
            Filters.eq("title", "The Hobbit: The Desolation of Smaug"),
            Updates.set("synopsis", "The dwarves, along with Bilbo Baggins and "
                    + "Gandalf the Grey, continue their quest to reclaim Erebor,"
                    + " their homeland, from Smaug. Bilbo Baggins is in possession"
                    + " of a mysterious and magical ring.")
        );
        
        // 3. Agregar un actor llamado "Samuel L. Jackson" a la película "Pulp Fiction"
        collection.updateOne(
            Filters.eq("title", "Pulp Fiction"),
            Updates.addToSet("actors", "Samuel L. Jackson")
        );
        
        System.out.println("Actualizaciones realizadas exitosamente.");
    }
}
