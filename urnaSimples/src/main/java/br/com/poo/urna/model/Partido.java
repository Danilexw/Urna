package br.com.poo.urna.model;

public class Partido {
	 private String numero; // Número do partido 
	    private String sigla; // Sigla do partido 

	    /**
	     * Construtor da classe Partido.
	     * @param numero O número do partido.
	     * @param sigla A sigla do partido.
	     */
	    public Partido(String numero, String sigla) { 
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


}
