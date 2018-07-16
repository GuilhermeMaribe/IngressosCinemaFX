
package controller;


import dominio.Filme;
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
import negocio.FilmeNegocio;
import negocio.NegocioException;
import static sun.plugin.javascript.navig.JSType.URL;
import view.PrintUtil;
import ingressoscinema.IngressosCinemaFilme;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;





/**
 *
 * @author guilh
 */
public class FilmeController implements Initializable {
    
    @FXML
    private VBox painelTabelaFilme;
    @FXML
    private TableView<Filme> tableViewFilmes;
    @FXML
    private TableColumn<Filme, String> tableColumncodigo;
    @FXML
    private TableColumn<Filme, String> tableColumnnome;
    @FXML
    private TableColumn<Filme, String> tableColumngenero;
    @FXML
    private TableColumn<Filme, String> tableColumnsinopse;
    
    
    @FXML
    private AnchorPane painelFilme;
    @FXML
    private TextField textFieldcodigo;
    @FXML
    private TextField textFieldnome;
    @FXML
    private TextField textFieldgenero;
    @FXML
    private TextField textFieldsinopse;
        
    private int tela;
    private List<Filme> listaFilmes;
    private Filme filmeSelecionado;

    private ObservableList<Filme> observableListaFilmes;
    private FilmeNegocio filmeNegocio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        filmeNegocio = new FilmeNegocio();

        //Codigo meio redundante - por isso as vezes é melhor um controller para cada view 
        if (tableViewFilmes != null) {
            carregarTableViewFilmes();
        }

    }        
    
    private void carregarTableViewFilmes() {
        tableColumncodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tableColumnnome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumngenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tableColumnsinopse.setCellValueFactory(new PropertyValueFactory<>("sinopse"));
        
        listaFilmes = filmeNegocio.listar();

        observableListaFilmes = FXCollections.observableArrayList(listaFilmes);
        tableViewFilmes.setItems(observableListaFilmes);
    }

    
    
    @FXML
    public void tratarBotaoCadastrar(ActionEvent event) throws IOException {
        filmeSelecionado = null;
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("PainelFilme.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(painelTabelaFilme.getScene().getWindow());
        stage.showAndWait();
        carregarTableViewFilmes();
    }

    @FXML
    public void tratarBotaoEditar(ActionEvent event) throws IOException {
        Filme filmeSelec = tableViewFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelec != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/controller/PainelFilme.fxml"));
            Parent root = (Parent) loader.load();

            FilmeController controller = (FilmeController) loader.getController();
            controller.setFilmeSelecionado(filmeSelec);

            Stage dialogStage = new Stage();
            dialogStage.setScene(new Scene(root));
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(painelTabelaFilme.getScene().getWindow());
            dialogStage.showAndWait();
            carregarTableViewFilmes();
        } else {
            PrintUtil.printMessageError("Precisa selecionar um filme para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoRemover(ActionEvent event) throws IOException, NegocioException {
        Filme filmeSelec = tableViewFilmes.getSelectionModel().getSelectedItem();
        if (filmeSelec != null) {
            try {
                filmeNegocio.deletar(filmeSelec);
                this.carregarTableViewFilmes();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
        } else {
            PrintUtil.printMessageError("Precisa selecionar uma sala para esta opcao");
        }
    }

    @FXML
    public void tratarBotaoSalvar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFilme.getScene().getWindow();
        
        if(filmeSelecionado == null) //Se for cadastrar
        {
            try {
                filmeNegocio.salvar(new Filme(
                        textFieldcodigo.getText(), 
                        textFieldnome.getText(),
                        textFieldgenero.getText(),
                        textFieldsinopse.getText()
                        
                ));                
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }
            
        }
        else //Se for editar
        {
            try {
                filmeSelecionado.setCodigo(textFieldcodigo.getText());
                filmeSelecionado.setNome(textFieldnome.getText());
                filmeSelecionado.setGenero(textFieldgenero.getText());
                filmeSelecionado.setSinopse(textFieldsinopse.getText()
                );                        
                filmeNegocio.atualizar(filmeSelecionado);
                stage.close();
            } catch (NegocioException ex) {
                PrintUtil.printMessageError(ex.getMessage());
            }

            
        } 
    }

    @FXML
    public void tratarBotaoCancelar(ActionEvent event) throws IOException {
        Stage stage = (Stage) painelFilme.getScene().getWindow();
        stage.close();

    }

    public Filme getFilmeSelecionado() {
        return filmeSelecionado;
    }

    public void setFilmeSelecionado(Filme filmeSelecionado) {
        this.filmeSelecionado = filmeSelecionado;
        textFieldcodigo.setText(filmeSelecionado.getCodigo());
        textFieldnome.setText(filmeSelecionado.getNome());
        textFieldgenero.setText(filmeSelecionado.getGenero());
        textFieldsinopse.setText(filmeSelecionado.getSinopse());
        
    }
}

