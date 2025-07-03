package br.com.poo.urna.model;

import java.time.LocalDateTime;

/**
 * Representa um voto registrado na urna.
 */
public class Voto { 
    private String candidatoId; // ID do candidato votado (ou "BRANCO", "NULO")
    private String tipoVoto;    // "VALIDO", "BRANCO", "NULO"
    private LocalDateTime timestamp; // Momento do voto
   
    /**
     * Construtor da classe Voto.
     * @param candidatoId O ID do candidato (ou "BRANCO", "NULO").
     * @param tipoVoto O tipo de voto ("VALIDO", "BRANCO", "NULO").
     */
    public Voto(String candidatoId, String tipoVoto) { 
        this.candidatoId = candidatoId;
        this.tipoVoto = tipoVoto;
        this.timestamp = LocalDateTime.now(); // Pega o timestamp no momento da criação do objeto
  
    }

    // Getters
    public String getCandidatoId() { 
        return candidatoId;
    }

    public String getTipoVoto() { 
        return tipoVoto;
    }


    public LocalDateTime getTimestamp() { 
        return timestamp;
    }

 
}
