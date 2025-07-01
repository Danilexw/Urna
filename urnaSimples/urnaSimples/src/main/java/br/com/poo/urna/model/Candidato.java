package br.com.poo.urna.model;

public class Candidato {

	/**
	 * Representa um candidato na eleição.
	 */
	
	    private String id; // Ex: "91001", pode ser o número digitado pelo eleitor
	    private String nome; // [cite: 5]
	    private Partido partido; // Objeto aninhado, conforme instruído [cite: 5]
	    

	    /**
	     * Construtor da classe Candidato.
	     * @param id O identificador único do candidato (geralmente o número completo).
	     * @param nome O nome do candidato.
	     * @param partido O objeto Partido associado ao candidato.
	     * @param cargo O cargo para o qual o candidato concorre.
	     */
	    public Candidato(String id, String nome, Partido partido) { // [cite: 26]
	        this.id = id;
	        this.nome = nome;
	        this.partido = partido;
	    }

	    // Getters para os atributos (Conforme boas práticas de POO) [cite: 26]
	    public String getId() { // [cite: 36]
	        return id;
	    }

	    public String getNome() { // [cite: 36]
	        return nome;
	    }

	    public Partido getPartido() { // [cite: 36]
	        return partido;
	    }

	    // Setters (se necessário, mas para objetos de modelo imutáveis, podem ser omitidos)
	    public void setId(String id) { // [cite: 36]
	        this.id = id;
	    }

	    public void setNome(String nome) { // [cite: 36]
	        this.nome = nome;
	    }

	    public void setPartido(Partido partido) { // [cite: 36]
	        this.partido = partido;
	    }


	}
