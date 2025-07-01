package br.com.poo.urna.model;

public class Partido {
	 private String numero; // Número do partido [cite: 5]
	    private String sigla; // Sigla do partido [cite: 5]

	    /**
	     * Construtor da classe Partido.
	     * @param numero O número do partido.
	     * @param sigla A sigla do partido.
	     */
	    public Partido(String numero, String sigla) { // [cite: 26]
	        this.numero = numero;
	        this.sigla = sigla;
	    }

	    // Getters
	    public String getNumero() { 
	        return numero;
	    }

	    public String getSigla() { 
	        return sigla;
	    }

	    // Setters
	    public void setNumero(String numero) { 
	        this.numero = numero;
	    }

	    public void setSigla(String sigla) { 
	        this.sigla = sigla;
	    }
}
