package controller;

import dominio.VendaIngresso;
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
import negocio.VendaIngressoNegocio;
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



public class VendaIngressoController implements Initializable {

    @FXML
    private VBox painelTabelaVendaIngresso;
    @FXML
    private TableView<VendaIngresso> tableViewVendaIngresso;
    @FXML
    private TableColumn<VendaIngresso, String> tableColumnid;
    @FXML
    private TableColumn<VendaIngresso, String> tableColumnsessao;
    
    
    @FXML
    private AnchorPane painelVendaIngresso;
    @FXML
    private TextField textFieldid;
    @FXML
    private TextField textFieldsessao;
        
    private int tela;
    private List<VendaIngresso> listavendas;
    private VendaIngresso vendaSelecionada;

    private ObservableList<VendaIngresso> observableListavendas;
    private VendaIngressoNegocio vendaingressoNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        vendaingressoNegocio = new VendaIngressoNegocio();

        //Codigo meio redundante - por isso as vezes � melhor um controller para cada view 
        if (tableViewVendaIngresso != null) {
            carregarTableViewVendaIngresso();
        }

    }        
    
    private void carregarTableViewVendaIngresso() {
        tableColumnid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnsessao.setCellValueFactory(new PropertyValueFactory<>("sessao"));
        
        listavendas = vendaingressoNegocio.listar();

        observableListavendas = FXCollections.observableArrayList(listavendas);
        tableViewVendaIngresso.setItems(observableListavendas);
    }

    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        vendaSelecionada = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelVendaIngresso.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaVendaIngresso.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewVendaIngresso();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        VendaIngresso vendaSelec = tableViewVendaIngresso.getSelectionModel().getSelectedItem();
        if (vendaSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("PainelVendaIngresso.fxml"));
            Parent root = (Parent) loader.load();

            VendaIngressoController controller = (VendaIngressoController) loader.getController();
            controller.setVendaSelecionada(vendaSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaVendaIngresso.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewVendaIngresso();
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma venda para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException, NegocioException {
        VendaIngresso vendaSelec = tableViewVendaIngresso.getSelectionModel().getSelectedItem();
        if (vendaSelec != null) {
            try {
                vendaingressoNegocio.deletar(vendaSelec);
                this.carregarTableViewVendaIngresso();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma venda para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelVendaIngresso.getScene().getWindow();
        
        if(vendaSelecionada == null) //Se for cadastrar
        {
            try {
                vendaingressoNegocio.salvar(new VendaIngresso(
                        textFieldid.getText(), 
                        textFieldsessao.getText() 
                        
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                vendaSelecionada.setId(textFieldid.getText());
                vendaSelecionada.setSessao(textFieldsessao.getText()
                );                        
                vendaingressoNegocio.atualizar(vendaSelecionada);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelVendaIngresso.getScene().getWindow();
        stage.close();

    }

    public VendaIngresso getVendaSelecionada() {
        return vendaSelecionada;
    }

    public void setVendaSelecionada(VendaIngresso vendaSelecionada) {
        this.vendaSelecionada = vendaSelecionada;
        textFieldid.setText(vendaSelecionada.getid());
        textFieldsessao.setText(vendaSelecionada.getsessao());
        
    }
}