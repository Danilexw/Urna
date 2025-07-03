package br.com.poo.urna.dao;

import br.com.poo.urna.dao.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe para cuidar do banco de dados no começo do programa.
 */
public class InicializadorBancoDados {

    /**
     * Este método olha se o banco de dados tem os candidatos.
     * Se não tiver, ele coloca os candidatos lá.
     */
    public static void verificarEPopular() {
        try {
            // Pega a conexão com o banco de dados
            MongoDatabase database = MongoDBConnection.getDatabase();
            MongoCollection<Document> candidatosCollection = database.getCollection("candidatos");

            // Conta quantos candidatos já existem na coleção
            long contagem = candidatosCollection.countDocuments();

            // Se a contagem for 0, o banco está vazio
            if (contagem == 0) {
                // 1. Exibe a mensagem que o professor pediu
                System.err.println("AVISO: O banco de dados está vazio!");
                
                // 2. Chama a função para colocar os dados
                System.out.println("Populando a coleção de candidatos...");
                popularDadosIniciais(candidatosCollection);
                System.out.println("Candidatos inseridos no banco!");
            } else {
                // Se já tem dados, só avisa no console
                System.out.println("O banco de dados já está populado com " + contagem + " candidatos.");
            }

        } catch (Exception e) {
            // Se der qualquer erro, mostra a mensagem e para o programa
            System.err.println("ERRO GRAVE AO CONECTAR COM O BANCO DE DADOS!");
            e.printStackTrace(); // Mostra os detalhes do erro
            System.exit(1); // Fecha o programa porque sem banco não funciona
        }
    }

    /**
     * Coloca os candidatos iniciais no banco.
     * @param candidatosCollection A coleção do MongoDB para adicionar os candidatos.
     */
    private static void popularDadosIniciais(MongoCollection<Document> candidatosCollection) {
        
        // --- Partido dos Esportes (PEsp) ---
        Document partidoEsp = new Document("numero", 91).append("sigla", "PEsp").append("nomeCompleto", "Partido dos Esportes");
        Document candidatoGolfe = new Document("_id", 91001).append("nome", "Golfe").append("partido", partidoEsp);
        Document candidatoBeisebol = new Document("_id", 91002).append("nome", "Beisebol").append("partido", partidoEsp);
        Document candidatoGinastica = new Document("_id", 91003).append("nome", "Ginástica Artística").append("partido", partidoEsp);

        // --- Partido dos Ritmos Musicais (PMus) ---
        Document partidoMus = new Document("numero", 92).append("sigla", "PMus").append("nomeCompleto", "Partido dos Ritmos Musicais");
        Document candidatoRock = new Document("_id", 92001).append("nome", "Rock").append("partido", partidoMus);
        Document candidatoSertanejo = new Document("_id", 92002).append("nome", "Sertanejo").append("partido", partidoMus);
        Document candidatoMPB = new Document("_id", 92003).append("nome", "Música Popular Brasileira").append("partido", partidoMus);

        // --- Partido das Profissões (PProf) ---
        Document partidoProf = new Document("numero", 93).append("sigla", "PProf").append("nomeCompleto", "Partido das Profissões");
        Document candidatoAstronauta = new Document("_id", 93001).append("nome", "Astronauta").append("partido", partidoProf);
        Document candidatoPintor = new Document("_id", 93002).append("nome", "Pintor").append("partido", partidoProf);
        Document candidatoEnfermeira = new Document("_id", 93003).append("nome", "Enfermeira").append("partido", partidoProf);

        // --- Partido das Festas Populares (PFest) ---
        Document partidoFest = new Document("numero", 94).append("sigla", "PFest").append("nomeCompleto", "Partido das Festas Populares");
        Document candidatoPascoa = new Document("_id", 94001).append("nome", "Páscoa").append("partido", partidoFest);
        Document candidatoOktoberfest = new Document("_id", 94002).append("nome", "Oktoberfest").append("partido", partidoFest);
        Document candidatoReveillon = new Document("_id", 94003).append("nome", "Reveillon").append("partido", partidoFest);

        // --- Partido do Folclore (PFolc) ---
        Document partidoFolc = new Document("numero", 95).append("sigla", "PFolc").append("nomeCompleto", "Partido do Folclore");
        Document candidatoCurupira = new Document("_id", 95001).append("nome", "Curupira").append("partido", partidoFolc);
        Document candidatoCabra = new Document("_id", 95002).append("nome", "Cabra Cabriola").append("partido", partidoFolc);
        Document candidatoCuca = new Document("_id", 95003).append("nome", "Cuca").append("partido", partidoFolc);

        // Colocando todos os candidatos em uma lista
        List<Document> todosOsCandidatos = new ArrayList<>();
        todosOsCandidatos.add(candidatoGolfe);
        todosOsCandidatos.add(candidatoBeisebol);
        todosOsCandidatos.add(candidatoGinastica);
        todosOsCandidatos.add(candidatoRock);
        todosOsCandidatos.add(candidatoSertanejo);
        todosOsCandidatos.add(candidatoMPB);
        todosOsCandidatos.add(candidatoAstronauta);
        todosOsCandidatos.add(candidatoPintor);
        todosOsCandidatos.add(candidatoEnfermeira);
        todosOsCandidatos.add(candidatoPascoa);
        todosOsCandidatos.add(candidatoOktoberfest);
        todosOsCandidatos.add(candidatoReveillon);
        todosOsCandidatos.add(candidatoCurupira);
        todosOsCandidatos.add(candidatoCabra);
        todosOsCandidatos.add(candidatoCuca);

        // Inserindo um por um no banco de dados
        for (Document candidatoDoc : todosOsCandidatos) {
            candidatosCollection.insertOne(candidatoDoc);
        }
    }
}