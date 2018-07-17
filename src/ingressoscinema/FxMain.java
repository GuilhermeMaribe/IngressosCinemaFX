/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingressoscinema;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author guilh
 */
public class FxMain extends Application {
    
   @Override
   public void start(Stage first) throws IOException {
        Parent main = FXMLLoader.load(getClass().getResource("/controller/PainelPrincipal.fxml"));
        Scene index = new Scene(main);
        first.setTitle("Ingressos Cinema");
        first.setScene(index);
        first.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
