package br.com.poo.urna.controller;


import br.com.poo.urna.dao.CandidatoDAO;
import br.com.poo.urna.dao.VotoDAO;
import br.com.poo.urna.model.Candidato;
import br.com.poo.urna.model.Partido;
import br.com.poo.urna.model.Voto;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controlador da interface da urna eletrônica.
 * Lida com a interação do usuário e a lógica de votação.
 */
public class UrnaController { 


    @FXML private Button btnConfirma;
    @FXML private HBox digitosVotoContainer; // Contêiner para os TextFields de dígitos
    @FXML private Label labelSeuVoto;
    @FXML private Label labelVereador;
    @FXML private Label labelNomeCandidato;   // Nome do candidato 
    @FXML private Label labelTelaFim;         // Mensagem Fim
    @FXML private Label labelVotou;			  // Mensagem Votou
    @FXML private Label labelPartidoSigla;    // Sigla do partido
    @FXML private Label labelMensagem;        // Mensagens de feedback 
    @FXML private ImageView imagemCandidato;  // Foto do candidato 
    @FXML private VBox infoCandidatoBox;      // Contêiner das informações do candidato
    @FXML private VBox fimBox;				  // Contêiner das informações da Tela Fim
    @FXML private ImageView partido91;		  // Consulta dos partidos e candidatos
    @FXML private ImageView partido92;
    @FXML private ImageView partido93;
    @FXML private ImageView partido94;
    @FXML private ImageView partido95;
    @FXML private Button botaoFecharImagem;
    

    private StringBuilder numeroDigitado = new StringBuilder();
    private static final String SENHA_FINALIZAR = "99999"; 

    private CandidatoDAO candidatoDAO = new CandidatoDAO();
    private VotoDAO votoDAO = new VotoDAO();

    @FXML
    public void initialize() { 
        limparTelaVoto(); // Inicializa a tela da urna
        labelSeuVoto.setVisible(true);
        labelVereador.setVisible(true);
        digitosVotoContainer.setVisible(true);
        infoCandidatoBox.setVisible(false); // Esconde informações do candidato inicialmente
        fimBox.setVisible(false);           // Esconde informações da tela final
    }

    /**
     * Manipula o clique nos botões numéricos (0-9).
     * @param event O evento de clique do botão.
     */
    @FXML
    private void handleDigito(ActionEvent event) { 
        String digito = ((javafx.scene.control.Button) event.getSource()).getText();
        if (numeroDigitado.length() < 5) { // Limita a 5 dígitos (para 91001, 99999, etc.)
            numeroDigitado.append(digito);
            atualizarDigitosVisuais();
            buscarEExibirCandidato(numeroDigitado.toString());
        }
    }

    /**
     * Atualiza os Labels que exibem os dígitos digitados.
     */
    private void atualizarDigitosVisuais() { // 

        for (int i = 0; i < 5; i++) {
            Label digitoLabel = (Label) digitosVotoContainer.getChildren().get(i);
            if (i < numeroDigitado.length()) {
                digitoLabel.setText(String.valueOf(numeroDigitado.charAt(i)));
            } else {
                digitoLabel.setText(""); // Limpa os campos vazios
            }
        }
        labelMensagem.setText(""); // Limpa mensagem ao digitar
    }

    /**
     * Busca o candidato no banco de dados e exibe suas informações.
     * @param numero O número digitado pelo eleitor. 
     */ 
    private void buscarEExibirCandidato(String numero) { 
    	 if (numero.length() == 2) {
    	        Partido partido = candidatoDAO.buscarPartidoPorNumero(numero); // Chama o método no DAO

    	        if (partido != null) {
    	            // Se um partido for encontrado com os dois dígitos
    	            labelPartidoSigla.setText(partido.getSigla()); // Exibe a sigla do partido
    	            infoCandidatoBox.setVisible(true); // Mostra a caixa de informações
    	        }
    	    }
    	    // --- Lógica para buscar o candidato completo (quando mais de 2 dígitos) ---
    	    else if (numero.length() > 2) { 
            Candidato candidato = candidatoDAO.buscarCandidatoPorNumero(numero);
                if (candidato != null) {
                    labelNomeCandidato.setText(candidato.getNome());
                    labelPartidoSigla.setText(candidato.getPartido().getSigla());
                    infoCandidatoBox.setVisible(true);
                    imagemCandidato.setImage(new Image(getClass().getResourceAsStream("/images/" + candidato.getId() + ".png")));
                    labelMensagem.setText(""); // Limpa qualquer mensagem de erro anterior
                    
                } else {
                    infoCandidatoBox.setVisible(true);
                    labelNomeCandidato.setText("NÚMERO ERRADO");
                    labelPartidoSigla.setText("VOTO NULO"); 
                    labelMensagem.setText("CONFIRMA para VOTO NULO ou CORRIGE para reiniciar"); 
                }
            } else {
                infoCandidatoBox.setVisible(false);
                fimBox.setVisible(false);
                labelNomeCandidato.setText("");
                labelPartidoSigla.setText("");
                labelMensagem.setText("");
            }
    }
            
            
    

    /**
     * Manipula o clique no botão "BRANCO". 
     * @param event O evento de clique do botão.
     */
    @FXML
    private void handleBranco(ActionEvent event) { 
        numeroDigitado.setLength(0); // Limpa qualquer número digitado
        limparTelaVoto();
        labelMensagem.setText("VOTO EM BRANCO. Pressione CONFIRMA para finalizar."); 
        infoCandidatoBox.setVisible(false);
    }

    /**
     * Manipula o clique no botão "CORRIGE". 
     * @param event O evento de clique do botão.
     */
    @FXML
    private void handleCorrige(ActionEvent event) { 
        numeroDigitado.setLength(0); // Limpa o número digitado
        limparTelaVoto();
    }

    /**
     * Manipula o clique no botão "CONFIRMA". 
     * @param event O evento de clique do botão.
     */
    @FXML
    private void handleConfirma(ActionEvent event) {
        if (labelMensagem.getText().equals("VOTO EM BRANCO. Pressione CONFIRMA para finalizar.")) {
            // Confirmar voto em branco
            votoDAO.registrarVoto(new Voto("BRANCO", "BRANCO")); // Dados da urna
            exibirTelaFinalizacaoVoto();
        } else if (numeroDigitado.toString().equals(SENHA_FINALIZAR)) { 
            // Senha de finalização da votação
            finalizarVotacao();
        } else if (infoCandidatoBox.isVisible() && !labelNomeCandidato.getText().equals("NÚMERO ERRADO")) {
            // Confirmar voto em candidato válido
            String candidatoId = numeroDigitado.toString();
            votoDAO.registrarVoto(new Voto(candidatoId, "VALIDO"));
            exibirTelaFinalizacaoVoto();
        } else if (labelNomeCandidato.getText().equals("NÚMERO ERRADO")) {
            // Confirmar voto nulo após número inválido
            votoDAO.registrarVoto(new Voto("NULO", "NULO"));
            exibirTelaFinalizacaoVoto();
        } else {
            // Caso nenhum número tenha sido digitado e não seja voto em branco
            labelMensagem.setText("Nenhum voto selecionado. Digite um número ou BRANCO.");
        }
        numeroDigitado.setLength(0); // Limpa após a confirmação
        limparTelaVoto();
    }

    /**
     * Limpa a tela da urna para um novo voto.
     */
    private void limparTelaVoto() { 
        // Limpa os Labels de dígitos
        for (int i = 0; i < 5; i++) {
            Label digitoLabel = (Label) digitosVotoContainer.getChildren().get(i);
            digitoLabel.setText("");
        }
        
        labelNomeCandidato.setText("");
        labelPartidoSigla.setText("");
        labelMensagem.setText("");
        infoCandidatoBox.setVisible(false);
        imagemCandidato.setImage(null); // Limpa a imagem

    }
    
    /**
     * Limpa totalmente a tela antes de aparecer a mensagem de FIM
     * 
     */
    private void limparAntesFim() {
    	labelSeuVoto.setVisible(false);
    	labelVereador.setVisible(false);
    	digitosVotoContainer.setVisible(false);
    }

    /**
     * Exibe uma tela de finalização de voto temporária antes de retornar ao estado inicial.
     * 
     */
    private void exibirTelaFinalizacaoVoto() { 
    	    limparAntesFim();
    	    fimBox.setVisible(true);  
    	    PauseTransition delay = new PauseTransition(Duration.seconds(3));
    	    
    	    delay.setOnFinished(event -> {
        	    initialize();
    	    });
    	    
    	    
    	    
    	    delay.play(); 
    }

    /**
     * Finaliza a votação e exibe o relatório. 
     */
    private void finalizarVotacao() { 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/poo/urna/view/RelatorioView.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) btnConfirma.getScene().getWindow(); 
            stage.setScene(new Scene(root));
            stage.setTitle("Relatório da Eleição");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela de relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }
    


    @FXML
    public void handlePartido91() {
        partido91.setImage(new Image(getClass().getResourceAsStream("/images/91.png")));
        partido91.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    
    @FXML
    public void handlePartido92() {
        partido92.setImage(new Image(getClass().getResourceAsStream("/images/92.png")));
        partido92.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido93() {
        partido93.setImage(new Image(getClass().getResourceAsStream("/images/93.png")));
        partido93.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido94() {
        partido94.setImage(new Image(getClass().getResourceAsStream("/images/94.png")));
        partido94.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido95() {
        partido95.setImage(new Image(getClass().getResourceAsStream("/images/95.png")));
        partido95.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }

    @FXML
    public void handleFecharImagem() {
    	partido91.setVisible(false);
    	partido92.setVisible(false);
    	partido93.setVisible(false);
    	partido94.setVisible(false);
    	partido95.setVisible(false);
        botaoFecharImagem.setVisible(false);
    }
}
