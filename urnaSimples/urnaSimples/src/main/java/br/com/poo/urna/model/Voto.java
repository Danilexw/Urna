package br.com.poo.urna.model;

import java.time.LocalDateTime;

/**
 * Representa um voto registrado na urna.
 */
public class Voto { // [cite: 26]
    private String candidatoId; // ID do candidato votado (ou "BRANCO", "NULO")
    private String tipoVoto;    // "VALIDO", "BRANCO", "NULO"
    private LocalDateTime timestamp; // Momento do voto
   
    /**
     * Construtor da classe Voto.
     * @param candidatoId O ID do candidato (ou "BRANCO", "NULO").
     * @param tipoVoto O tipo de voto ("VALIDO", "BRANCO", "NULO").
     */
    public Voto(String candidatoId, String tipoVoto) { // [cite: 26]
        this.candidatoId = candidatoId;
        this.tipoVoto = tipoVoto;
        this.timestamp = LocalDateTime.now(); // Pega o timestamp no momento da criação do objeto
  
    }

    // Getters
    public String getCandidatoId() { // [cite: 36]
        return candidatoId;
    }

    public String getTipoVoto() { // [cite: 36]
        return tipoVoto;
    }


    public LocalDateTime getTimestamp() { // [cite: 36]
        return timestamp;
    }

    // Setters (se necessário)
    public void setCandidatoId(String candidatoId) { // [cite: 36]
        this.candidatoId = candidatoId;
    }

    public void setTipoVoto(String tipoVoto) { // [cite: 36]
        this.tipoVoto = tipoVoto;
    }


    public void setTimestamp(LocalDateTime timestamp) { // [cite: 36]
        this.timestamp = timestamp;
    }

 
}
