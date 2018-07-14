package controller;

import dominio.Sala;
import ingressoscinema.IngressosCinema;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import negocio.NegocioException;
import negocio.SalaNegocio;
import view.PrintUtil;
import javafx.geometry.Pos;
 import javafx.scene.Scene;
 import javafx.scene.control.Button;
 import javafx.scene.layout.Pane;
 import javafx.scene.layout.VBox;
 import javafx.scene.paint.Color;
 import javafx.scene.shape.Rectangle;
 import javafx.scene.text.Text;
 import javafx.stage.Stage;
 import javafx.stage.StageStyle;
 import javafx.stage.Window;
 import javafx.util.Duration;



public class SalaController implements Initializable {

    @FXML
    private VBox painelTabelaSala;
    @FXML
    private TableView<Sala> tableViewSalas;
    @FXML
    private TableColumn<Sala, String> tableColumnnSala;
    @FXML
    private TableColumn<Sala, String> tableColumnnAssentos;
    
    
    @FXML
    private AnchorPane painelSala;
    @FXML
    private TextField textFieldnSala;
    @FXML
    private TextField textFieldnAssentos;
        
    private int tela;
    private List<Sala> listaSalas;
    private Sala salaSelecionada;

    private ObservableList<Sala> observableListaSalas;
    private SalaNegocio salaNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        salaNegocio = new SalaNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewSalas != null) {
            carregarTableViewSalas();
        }

    }        
    
    private void carregarTableViewSalas() {
        tableColumnnSala.setCellValueFactory(new PropertyValueFactory<>("nsala"));
        tableColumnnAssentos.setCellValueFactory(new PropertyValueFactory<>("nassentos"));
        
        listaSalas = salaNegocio.listar();

        observableListaSalas = FXCollections.observableArrayList(listaSalas);
        tableViewSalas.setItems(observableListaSalas);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        salaSelecionada = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelSala.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaSala.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewSalas();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Sala salaSelec = tableViewSalas.getSelectionModel().getSelectedItem();
        if (salaSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PainelSala.fxml"));
            Parent root = (Parent) loader.load();

            SalaController controller = (SalaController) loader.getController();
            controller.setSalaSelecionada(salaSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaSala.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewSalas();
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma sala para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException, NegocioException {
        Sala salaSelec = tableViewSalas.getSelectionModel().getSelectedItem();
        if (salaSelec != null) {
            try {
                salaNegocio.deletar(salaSelec);
                this.carregarTableViewSalas();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma sala para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelSala.getScene().getWindow();
        
        if(salaSelecionada == null) //Se for cadastrar
        {
            try {
                salaNegocio.salvar(new Sala(
                        textFieldnSala.getText(), 
                        textFieldnAssentos.getText() 
                        
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                salaSelecionada.setnSala(textFieldnSala.getText());
                salaSelecionada.setnAssentos(textFieldnAssentos.getText()
                );                        
                salaNegocio.atualizar(salaSelecionada);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelSala.getScene().getWindow();
        stage.close();

    }

    public Sala getSalaSelecionada() {
        return salaSelecionada;
    }

    public void setSalaSelecionada(Sala salaSelecionada) {
        this.salaSelecionada = salaSelecionada;
        textFieldnSala.setText(salaSelecionada.getnSala());
        textFieldnAssentos.setText(salaSelecionada.getnAssentos2());
        
    }
}