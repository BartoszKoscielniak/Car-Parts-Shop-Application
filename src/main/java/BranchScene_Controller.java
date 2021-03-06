import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.util.Callback;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BranchScene_Controller implements Initializable {

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();

    Alert alert = new Alert( Alert.AlertType.ERROR );

    @FXML
    private Label obecnieZalogowany;

    @FXML
    private TextField nazwaMiejscowosci;

    @FXML
    private Button dodajOddzial;

    @FXML
    private TableView<Sklep> branchTableView;

    @FXML
    private TableColumn<Sklep, String> miejscowosc_col;

    @FXML
    private TableColumn<Sklep, Integer> obrot_col;

    @FXML
    private TableColumn liczbaPrac_col;

    @FXML
    private TableColumn<Sklep, String> usunButton;

    @FXML
    void branchButton( MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/branchScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cartButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/cartScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void currentWorkerButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/currentWorkerScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void shopButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/shopScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void workersButton(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View/workersScene.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {
        initCols();
        loadData();
        addNewBranch();
        currentlyLogged();
    }

    public void initCols(){
        miejscowosc_col.setCellValueFactory( new PropertyValueFactory<Sklep,String>( "Miejscowosc" ) );
        obrot_col.setCellValueFactory( new PropertyValueFactory<Sklep,Integer>( "Obrot" ) );

        Callback<TableColumn<Sklep, String>, TableCell<Sklep, String>> cellFactory
                = //
                new Callback<TableColumn<Sklep, String>, TableCell<Sklep, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Sklep, String> param) {
                        final TableCell<Sklep, String> cell = new TableCell<Sklep, String>() {

                            final Button btn = new Button("Usun");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Sklep sklep1 = getTableView().getItems( ).get( getIndex());
                                            btn.setOnAction( event -> {
                                                if(sklep1.getPracownik().isEmpty()){
                                                    Transaction transaction = session.beginTransaction( );
                                                    Query query = session.createQuery( "DELETE FROM Sklep WHERE id_sklepu ='" + sklep1.getId_sklepu( ) + "'" );
                                                    query.executeUpdate( );
                                                    transaction.commit( );
                                                    loadData( );
                                                } else {
                                                    alert.setTitle( "Error" );
                                                    alert.setContentText( "Nie mozna usunac sklepu majacego pracownikow" );
                                                    alert.show();
                                                }
                                            } );
                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Sklep, String>, TableCell<Sklep, String>> cellFactory2
                = //
                new Callback<TableColumn<Sklep, String>, TableCell<Sklep, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Sklep, String> param) {
                        final TableCell<Sklep, String> cell = new TableCell<Sklep, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Sklep sklep1 = getTableView().getItems( ).get( getIndex());
                                    List<Pracownik> pracownikList = new ArrayList<>();
                                    session.beginTransaction();
                                    pracownikList = session.createQuery( "FROM Pracownik" ).getResultList();
                                    session.getTransaction().commit();
                                    int i = 0;
                                    int temp = 0;
                                    while(i < pracownikList.size()){
                                        if(pracownikList.get( i ).getSklep().getId_sklepu() == sklep1.getId_sklepu()){
                                            temp++;
                                        }
                                        i++;
                                    }
                                    setText( String.valueOf( temp) );


                                }
                            }
                        };
                        return cell;
                    }
                };

        liczbaPrac_col.setCellFactory(cellFactory2);
        usunButton.setCellFactory(cellFactory);
    }

    public void loadData(){
        List<Sklep> results = session.createQuery( "From Sklep" ).getResultList();
        ObservableList<Sklep> data = FXCollections.observableArrayList(results);
        branchTableView.setItems( data );
    }

    public void addNewBranch(){
        dodajOddzial.setOnAction( event -> {
            if(!nazwaMiejscowosci.getText().isEmpty()){
                Sklep temp = new Sklep( nazwaMiejscowosci.getText(),0 );
                session.beginTransaction();
                session.save( temp );
                session.getTransaction().commit();
                loadData();
            }
            nazwaMiejscowosci.clear();
            nazwaMiejscowosci.setPromptText( "Wpisz miejscowosc!" );
        } );
    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        obecnieZalogowany.setText( loginScene_controller.getImietemp() + " " + loginScene_controller.getNazwiskotemp() );
    }

}
