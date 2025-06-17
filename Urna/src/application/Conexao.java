package application;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conexao {

    public static void main(String[] args) {
        try {
            // Conecta ao servidor MongoDB local
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

            // Cria (ou acessa) o banco chamado "meu_banco"
            MongoDatabase database = mongoClient.getDatabase("urnaBanco");

            System.out.println("Banco criado ou acessado com sucesso: " + database.getName());

            // Após uso, feche a conexão
            mongoClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
