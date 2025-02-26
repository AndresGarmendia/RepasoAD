/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ejercicio_2.tarea_5._hector_jimenez;

import com.mongodb.client.*;
import org.bson.Document;

/**
 * Clase encargada de la conexión a MongoDB
 * @author Hector Jimenez
 */
public class DBmanager {
    private static final String URI = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "mongo_exercise";
    private static final String COLLECTION_NAME = "movies";
    private static MongoClient mongoClient;
    private static MongoDatabase bd;
    static MongoCollection<Document> coleccion;

    /**
     * Método de conexión
     */
    public static void conexionBD() {
        mongoClient = MongoClients.create(URI);
        bd = mongoClient.getDatabase(DATABASE_NAME);
        coleccion = bd.getCollection(COLLECTION_NAME);
        System.out.println("Conectado a MongoDB!! ");
    }

    /**
     *Método de desconexión
     */
    public static void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión cerrada correctamente");
        }
    }
}

