package application;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class MongoDB {
    public static void main(String[] args) {
        // Cria cliente usando factory
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        
        MongoDatabase database = mongoClient.getDatabase("candidato");
        MongoCollection<Document> collection = database.getCollection("candidatos");
        
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }
}

