/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea_2_5_ad;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
/**
 *
 * @author Usuario
 */
public class CrearBD {
    private static final String URI = "mongodb://127.0.0.1:27017";// Dirección de MongoDB
    private static final String DB_NAME = "mongo_exercise"; // Nombre de la BD

    public static MongoDatabase connect() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DB_NAME);
    }

    public static void main(String[] args) {
        MongoDatabase db = connect();
        System.out.println("Conexion exitosa a la base de datos: " + db.getName());
    }
}
