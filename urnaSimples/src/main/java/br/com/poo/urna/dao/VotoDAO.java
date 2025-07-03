package br.com.poo.urna.dao;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import br.com.poo.urna.model.Voto;

import java.time.ZoneOffset;
import java.util.Date;

/**
 * Objeto de Acesso a Dados (DAO) para a entidade Voto.
 * Gerencia as operações de persistência de Votos no MongoDB.
 */
public class VotoDAO { 

    private MongoCollection <Document> votosCollection;

    /**
     * Construtor do VotoDAO. Obtém a coleção "votos" do banco de dados.
     */
    public VotoDAO() { 
        MongoDatabase database = MongoDBConnection.getDatabase();
        this.votosCollection = database.getCollection("votos");
    }

    /**
     * Registra um voto no banco de dados.
     * @param voto O objeto Voto a ser registrado.
     */
    public void registrarVoto(Voto voto) { 
        try {
            // Mapeia o objeto Voto para um documento BSON
            Document votoDoc = new Document("candidatoId", voto.getCandidatoId())
                    .append("tipoVoto", voto.getTipoVoto())
                    .append("timestamp", Date.from(voto.getTimestamp().toInstant(ZoneOffset.UTC)));
               

            votosCollection.insertOne(votoDoc);
            System.out.println("Voto registrado: " + voto.getCandidatoId() + " (" + voto.getTipoVoto() + ")");
        } catch (Exception e) {
            System.err.println("Erro ao registrar voto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Conta o número de votos válidos para um candidato específico.
     * @param candidatoId O ID do candidato.
     * @return O número de votos válidos para o candidato.
     */
    public long contarVotosParaCandidato(String candidatoId) { 
        return votosCollection.countDocuments(new Document("candidatoId", candidatoId).append("tipoVoto", "VALIDO"));
    }

    /**
     * Conta o número total de votos brancos.
     * @return O número de votos brancos.
     */
    public long contarVotosBrancos() { 
        return votosCollection.countDocuments(new Document("tipoVoto", "BRANCO"));
    }

    /**
     * Conta o número total de votos nulos.
     * @return O número de votos nulos.
     */
    public long contarVotosNulos() { 
        return votosCollection.countDocuments(new Document("tipoVoto", "NULO"));
    }

    /**
     * Conta o total de todos os votos (válidos, brancos e nulos).
     * @return O número total de votos.
     */
    public long contarTotalVotos() { 
        return votosCollection.countDocuments();
    }
    
}
