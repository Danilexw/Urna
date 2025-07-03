package br.com.poo.urna.controller;

import br.com.poo.urna.dao.CandidatoDAO;
import br.com.poo.urna.dao.VotoDAO;
import br.com.poo.urna.model.Candidato;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador da tela de relatório da eleição.
 * Exibe os resultados finais da votação.
 */
public class RelatorioController { 

    @FXML private TextArea relatorioTextArea; 
    @FXML private Label labelTituloRelatorio; 

    private VotoDAO votoDAO = new VotoDAO();
    private CandidatoDAO candidatoDAO = new CandidatoDAO();

    @FXML
    public void initialize() { 
        labelTituloRelatorio.setText("RELATÓRIO DA ELEIÇÃO");
        gerarRelatorio(); // Gera e exibe o relatório ao inicializar o controlador 
    }

    /**
     * Gera o relatório final da eleição, contabilizando votos e porcentagens.
     */
    private void gerarRelatorio() { 
        StringBuilder relatorio = new StringBuilder();

        long totalVotos = votoDAO.contarTotalVotos(); // Conta todos os votos registrados

        if (totalVotos == 0) {
            relatorio.append("Nenhum voto registrado ainda.");
            relatorioTextArea.setText(relatorio.toString());
            return;
        }

        // Mapa para armazenar votos por candidato
        Map<String, Long> votosPorCandidato = new HashMap<>(); 
        List<Candidato> todosCandidatos = candidatoDAO.buscarTodosCandidatos(); 

        // Contabiliza votos válidos para cada candidato
        for (Candidato candidato : todosCandidatos) { 
            long votos = votoDAO.contarVotosParaCandidato(candidato.getId());
            votosPorCandidato.put(candidato.getId(), votos);
        }

        // Ordena os candidatos por número de votos (do maior para o menor)
        votosPorCandidato.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> {
                    String candidatoId = entry.getKey();
                    long votos = entry.getValue();
                    Candidato candidato = todosCandidatos.stream()
                                                    .filter(c -> c.getId().equals(candidatoId))
                                                    .findFirst()
                                                    .orElse(null);
                    if (candidato != null) {
                        double porcentagem = (double) votos / totalVotos * 100;
                        relatorio.append(String.format("Candidato %s (%s): %.0f%% (%d votos)\n",
                                candidato.getNome(), candidato.getId(), porcentagem, votos)); 
                    }
                });

        // Contabiliza votos brancos
        long votosBrancos = votoDAO.contarVotosBrancos();
        double porcentagemBrancos = (double) votosBrancos / totalVotos * 100;
        relatorio.append(String.format("Votos Brancos: %.0f%% (%d votos)\n", porcentagemBrancos, votosBrancos)); 

        // Contabiliza votos nulos
        long votosNulos = votoDAO.contarVotosNulos();
        double porcentagemNulos = (double) votosNulos / totalVotos * 100;
        relatorio.append(String.format("Votos Nulos: %.0f%% (%d votos)\n", porcentagemNulos, votosNulos)); 

        relatorioTextArea.setText(relatorio.toString());
    }
}
