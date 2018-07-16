
package controller;

import dominio.Sessao;
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
import negocio.SessaoNegocio;
import view.PrintUtil;

/**
 *
 * @author guilh
 */
public class SessaoController implements Initializable {
    @FXML
    private VBox painelTabelaSessao;
    @FXML
    private TableView<Sessao> tableViewSessoes;
    @FXML
    private TableColumn<Sessao, String> tableColumnqtdIngresso;
    @FXML
    private TableColumn<Sessao, String> tableColumnhorario;
    @FXML
    private TableColumn<Sessao, String> tableColumnsala;
    @FXML
    private TableColumn<Sessao, String> tableColumnfilme;
    
    
    @FXML
    private AnchorPane painelSessao;
    @FXML
    private TextField textFieldqtdIngresso;
    @FXML
    private TextField textFieldhorario;
    @FXML
    private TextField textFieldsala;
    @FXML
    private TextField textFieldfilme;
        
    private int tela;
    private List<Sessao> listaSessoes;
    private Sessao sessaoSelecionada;

    private ObservableList<Sessao> observableListaSessoes;
    private SessaoNegocio sessaoNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sessaoNegocio = new SessaoNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewSessoes != null) {
            carregarTableViewSessoes();
        }

    }        
    
    private void carregarTableViewSessoes() {
        tableColumnqtdIngresso.setCellValueFactory(new PropertyValueFactory<>("qtd_ingresso"));
        tableColumnhorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        tableColumnsala.setCellValueFactory(new PropertyValueFactory<>("id_sala"));
        tableColumnfilme.setCellValueFactory(new PropertyValueFactory<>("id_filme"));
        
        listaSessoes = sessaoNegocio.listar();

        observableListaSessoes = FXCollections.observableArrayList(listaSessoes);
        tableViewSessoes.setItems(observableListaSessoes);
    }

    
    
    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        sessaoSelecionada = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelSessao.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaSessao.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewSessoes();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Sessao sessaoSelec = tableViewSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/PainelSessao.fxml"));
            Parent root = (Parent) loader.load();

            SessaoController controller = (SessaoController) loader.getController();
            controller.setSessaoSelecionada(sessaoSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaSessao.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewSessoes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma sessao para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException, NegocioException {
        Sessao sessaoSelec = tableViewSessoes.getSelectionModel().getSelectedItem();
        if (sessaoSelec != null) {
            try {
                sessaoNegocio.deletar(sessaoSelec);
                this.carregarTableViewSessoes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma sessao para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelSessao.getScene().getWindow();
        
        if(sessaoSelecionada == null) //Se for cadastrar
        {
            try {
                sessaoNegocio.salvar(new Sessao(
                        textFieldqtdIngresso.getText(), 
                        textFieldhorario.getText(),
                        textFieldsala.getText(),
                        textFieldfilme.getText()
                        
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                sessaoSelecionada.setQtdIngresso(textFieldqtdIngresso.getText());
                sessaoSelecionada.setHorario(textFieldhorario.getText());
                sessaoSelecionada.setSala(textFieldSala.getText());
                sessaoSelecionada.setFilme(textFieldfilme.getText()
                );                        
                sessaoNegocio.atualizar(sessaoSelecionada);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelSessao.getScene().getWindow();
        stage.close();

    }

    public Sessao getSessaoSelecionada() {
        return sessaoSelecionada;
    }

    public void setSessaoSelecionada(Sessao sessaoSelecionada) {
        this.sessaoSelecionada = sessaoSelecionada;
        textFieldqtdIngresso.setText(sessaoSelecionada.getQtdIngresso());
        textFieldhorario.setText(sessaoSelecionada.getHorario());
        textFieldsala.setText(sessaoSelecionada.getSala());
        textFieldfilme.setText(sessaoSelecionada.getFilme());
        
    }
    
}
