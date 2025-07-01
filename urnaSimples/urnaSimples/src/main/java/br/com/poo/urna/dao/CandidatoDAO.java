package br.com.poo.urna.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import br.com.poo.urna.model.Candidato;
import br.com.poo.urna.model.Partido;

import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acesso a Dados (DAO) para a entidade Candidato.
 * Gerencia as operações de persistência de Candidatos no MongoDB.
 */
public class CandidatoDAO { // [cite: 26]

    private MongoCollection<Document> candidatosCollection;

    /**
     * Construtor do CandidatoDAO. Obtém a coleção "candidatos" do banco de dados.
     */
    public CandidatoDAO() { 
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.candidatosCollection = database.getCollection("candidatos");
    }

    /**
     * Insere um novo candidato no banco de dados.
     * @param candidato O objeto Candidato a ser inserido.
     */
    public void inserirCandidato(Candidato candidato) { // [cite: 36]
        try {
            // Mapeia o objeto Candidato para um documento BSON
            Document partidoDoc = new Document("numero", candidato.getPartido().getNumero())
                                        .append("sigla", candidato.getPartido().getSigla());

            Document candidatoDoc = new Document("_id", candidato.getId()) // Usando o ID fornecido
                    .append("nome", candidato.getNome()) // [cite: 5]
                    .append("partido", partidoDoc); // 


            candidatosCollection.insertOne(candidatoDoc);
            System.out.println("Candidato " + candidato.getNome() + " (ID: " + candidato.getId() + ") inserido com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inserir candidato " + candidato.getNome() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Candidato buscarCandidatoPorNumero(String id) { // [cite: 36]
        try {
            Document query = new Document("_id", id); // A busca é feita pelo campo _id
            Document result = candidatosCollection.find(query).first();

            if (result != null) {
                Document partidoMongoDoc = result.get("partido", Document.class);
                
                // Correção aqui: Usa "numero" (minúsculo) como chave, conforme está no MongoDB
                // Mantendo a robustez para caso o tipo no DB seja Integer e precise de conversão para String
                Object numeroRaw = partidoMongoDoc.get("numero"); // Pega como Object
                String numeroDoPartidoString = null;

                if (numeroRaw instanceof Integer) {
                    numeroDoPartidoString = String.valueOf((Integer) numeroRaw);
                } else if (numeroRaw instanceof String) {
                    numeroDoPartidoString = (String) numeroRaw;
                }
                // Se for null ou outro tipo, numeroDoPartidoString permanecerá null.

                Partido partido = new Partido(
                    numeroDoPartidoString, // Passa a String (convertida ou já existente)
                    partidoMongoDoc.getString("sigla")
                );
                
                return new Candidato(
                    result.getString("_id"), // Assume que _id é String como "91001"
                    result.getString("nome"),
                    partido
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar candidato por ID e cargo: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Busca um candidato pelo seu ID e cargo.
     * @param id O ID do candidato a ser buscado (ex: "91001").
     * @param cargo O cargo do candidato (ex: "VEREADOR").
     * @return O objeto Candidato encontrado, ou null se não for encontrado.
     */
//    public Candidato buscarCandidatoPorNumero(String id) { // [cite: 36]
//        try {
//            Document query = new Document("_id", id);
//            Document result = candidatosCollection.find(query).first();
//
//            if (result != null) {
//                // Mapeia o Document do MongoDB de volta para um objeto Candidato
//                Document partidoMongoDoc = result.get("partido", Document.class);
//                Partido partido = new Partido(
//                    partidoMongoDoc.getString("numero"),
//                    partidoMongoDoc.getString("sigla")
//                );
//                
//                return new Candidato(
//                    result.getString("_id"),
//                    result.getString("nome"),
//                    partido
//                );
//            }
//        } catch (Exception e) {
//            System.err.println("Erro ao buscar candidato por ID e cargo: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * Retorna uma lista de todos os candidatos.
     * @return Uma lista de objetos Candidato.
     */
    public List<Candidato> buscarTodosCandidatos() { // [cite: 36]
        List<Candidato> candidatos = new ArrayList<>();
        try {
            for (Document doc : candidatosCollection.find()) {
                Document partidoMongoDoc = doc.get("partido", Document.class);
                Partido partido = new Partido(
                    partidoMongoDoc.getString("numero"),
                    partidoMongoDoc.getString("sigla")
                );
                candidatos.add(new Candidato(
                    doc.getString("_id"),
                    doc.getString("nome"),
                    partido   
                ));
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os candidatos: " + e.getMessage());
            e.printStackTrace();
        }
        return candidatos;
    }
    
    public Partido buscarPartidoPorNumero(String numero) {
    	
    	   List<Candidato> todosOsCandidatos = this.buscarTodosCandidatos(); // Obtenha seus candidatos

    	    if (todosOsCandidatos != null) {
    	        for (Candidato c : todosOsCandidatos) {
    	            // Verifica se o Partido e seu número não são null,
    	            // e então converte o Integer do Partido para String para comparação
    	            if (c != null && c.getPartido() != null &&
    	                c.getPartido().getNumero() != null &&
    	                String.valueOf(c.getPartido().getNumero()).equals(numero)) { // <-- Linha ajustada
    	                return c.getPartido();
    	            }
    	        }
    	    }
    	    return null; // Retorna null se nenhum partido for encontrado

   }
}