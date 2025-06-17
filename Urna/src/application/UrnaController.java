package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UrnaController {

    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;

    private Label[] labels;
    private int currentIndex = 0;

    @FXML
    public void initialize() {
        labels = new Label[]{label1, label2, label3, label4, label5};
    }

    public void handleNumberButton(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String numero = btn.getText();

        if (currentIndex < labels.length) {
            labels[currentIndex].setText(numero);
            labels[currentIndex].setVisible(true);
            currentIndex++;
        }
    }

    public void handleCorrige() {
    	for(Label label : labels) {
    		label.setText("");
    		label.setVisible(false);
    	}
        currentIndex = 0;
    }
    
    public void handleConfirmar() {
        
    }
    
    public void handleBranco() {
    	
    }
    
    @FXML
    private ImageView partido91;
    @FXML
    private ImageView partido92;
    @FXML
    private ImageView partido93;
    @FXML
    private ImageView partido94;
    @FXML
    private ImageView partido95;
    
    @FXML
    private Button botaoFecharImagem;

    @FXML
    public void handlePartido91() {
        partido91.setImage(new Image(getClass().getResourceAsStream("91.png")));
        partido91.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    
    @FXML
    public void handlePartido92() {
        partido92.setImage(new Image(getClass().getResourceAsStream("92.png")));
        partido92.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido93() {
        partido93.setImage(new Image(getClass().getResourceAsStream("93.png")));
        partido93.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido94() {
        partido93.setImage(new Image(getClass().getResourceAsStream("94.png")));
        partido93.setVisible(true);
        botaoFecharImagem.setVisible(true);
    }
    @FXML
    public void handlePartido95() {
        partido93.setImage(new Image(getClass().getResourceAsStream("95.png")));
        partido93.setVisible(true);
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
