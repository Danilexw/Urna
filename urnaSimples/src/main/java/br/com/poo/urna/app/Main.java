package br.com.poo.urna.app;

import br.com.poo.urna.dao.MongoDBConnection;
import br.com.poo.urna.dao.InicializadorBancoDados; 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principal da aplicação da Urna Eletrônica Simplificada.
 * Gerencia o ciclo de vida da aplicação JavaFX e inicializa o banco de dados.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("--- Iniciando Sistema da Urna Eletrônica ---");

        // Este método já lida com a conexão e o try-catch de erros críticos.
        InicializadorBancoDados.verificarEPopular();

        System.out.println("\nSistema pronto para uso. Carregando interface...");

        Parent root = FXMLLoader.load(getClass().getResource("/br/com/poo/urna/view/UrnaView.fxml"));

        Scene scene = new Scene(root,800,600);
		scene.getStylesheets().add(getClass().getResource("/br/com/poo/urna/view/application.css").toExternalForm());
		primaryStage.setScene(scene);
        primaryStage.setTitle("Urna Eletrônica Simples");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Fecha a conexão com o MongoDB quando a aplicação é encerrada
        MongoDBConnection.closeConnection();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}