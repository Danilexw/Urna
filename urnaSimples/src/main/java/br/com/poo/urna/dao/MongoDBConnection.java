package br.com.poo.urna.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 * Gerencia a conexão com o banco de dados MongoDB.
 * Segue o padrão Singleton para garantir uma única instância de conexão.
 */
public class MongoDBConnection { 

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    
    private static final String CONNECTION_STRING = "mongodb://localhost:27017"; 
    private static final String DATABASE_NAME = "bancoUrna"; 

    /**
     * Retorna a instância do banco de dados MongoDB. Se a conexão ainda não foi estabelecida, a cria.
     * @return A instância do MongoDatabase.
     */
    public static MongoDatabase getDatabase() { 
        if (database == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING); 
                database = mongoClient.getDatabase(DATABASE_NAME); 
                System.out.println("Conectado ao MongoDB: " + DATABASE_NAME);
            } catch (Exception e) {
                System.err.println("Erro ao conectar ao MongoDB: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return database;
    }

    /**
     * Fecha a conexão com o MongoDB. 
     */
    public static void closeConnection() { 
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("Conexão com o MongoDB fechada.");
        }
    }
}
