package br.com.poo.urna.model;

public class Candidato {

	/**
	 * Representa um candidato na eleição.
	 */
	
	    private String id; // Ex: "91001", pode ser o número digitado pelo eleitor
	    private String nome; 
	    private Partido partido; 
	    

	    /**
	     * Construtor da classe Candidato.
	     * @param id O identificador único do candidato (geralmente o número completo).
	     * @param nome O nome do candidato.
	     * @param partido O objeto Partido associado ao candidato.
	     */
	    public Candidato(String id, String nome, Partido partido) { 
	        this.id = id;
	        this.nome = nome;
	        this.partido = partido;
	    }

	    // Getters para os atributos 
	    public String getId() { 
	        return id;
	    }

	    public String getNome() { 
	        return nome;
	    }

	    public Partido getPartido() { 
	        return partido;
	    }


	}
