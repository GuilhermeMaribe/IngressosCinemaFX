
package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author guilh
 */
public class PrincipalController {
    
    @FXML
    private VBox painelPrincipal;
    @FXML
    private ToolBar tool; 
    @FXML
    private Button sala;
    @FXML
    private Button filme;
    @FXML
    private Button sessao;
    @FXML
    private Button venda;
    
    
            
    
    @FXML
    public void tratarBotaoSala(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelTabelaSala.fxml"));
        stage.setScene(new Scene(root));
        stage.showAndWait();
         
    }

    @FXML
    public void tratarBotaoFilme(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelTabelaFilme.fxml"));
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    public void tratarBotaoSessao(ActionEvent event) throws IOException {
    }

    @FXML
    public void tratarBotaoVender(ActionEvent event) throws IOException {
       
    }

    
    }
