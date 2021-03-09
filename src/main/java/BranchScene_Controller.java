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
    private Label currentlyLoggedIn;

    @FXML
    private TextField city;

    @FXML
    private Button addBranch;

    @FXML
    private TableView<Shop> branchTableView;

    @FXML
    private TableColumn<Shop, String> city_col;

    @FXML
    private TableColumn<Shop, Integer> turnover_col;

    @FXML
    private TableColumn employeesAmount_col;

    @FXML
    private TableColumn<Shop, String> deleteButton;

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
        city_col.setCellValueFactory( new PropertyValueFactory<Shop,String>( "city" ) );
        turnover_col.setCellValueFactory( new PropertyValueFactory<Shop,Integer>( "turnover" ) );

        Callback<TableColumn<Shop, String>, TableCell<Shop, String>> cellFactory
                = //
                new Callback<TableColumn<Shop, String>, TableCell<Shop, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Shop, String> param) {
                        final TableCell<Shop, String> cell = new TableCell<Shop, String>() {

                            final Button btn = new Button("Usun");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Shop shop1 = getTableView().getItems( ).get( getIndex());
                                            btn.setOnAction( event -> {
                                                if(shop1.getWorker().isEmpty()){
                                                    Transaction transaction = session.beginTransaction( );
                                                    Query query = session.createQuery( "DELETE FROM Shop WHERE id_shop ='" + shop1.getId_shop( ) + "'" );
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

        Callback<TableColumn<Shop, String>, TableCell<Shop, String>> cellFactory2
                = //
                new Callback<TableColumn<Shop, String>, TableCell<Shop, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Shop, String> param) {
                        final TableCell<Shop, String> cell = new TableCell<Shop, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Shop shop1 = getTableView().getItems( ).get( getIndex());
                                    List<Workers> workersList = new ArrayList<>();
                                    session.beginTransaction();
                                    workersList = session.createQuery( "FROM Workers" ).getResultList();
                                    session.getTransaction().commit();
                                    int i = 0;
                                    int temp = 0;
                                    while(i < workersList.size()){
                                        if(workersList.get( i ).getShop().getId_shop() == shop1.getId_shop()){
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

        employeesAmount_col.setCellFactory(cellFactory2);
        deleteButton.setCellFactory(cellFactory);
    }

    public void loadData(){
        List<Shop> results = session.createQuery( "From Shop" ).getResultList();
        ObservableList<Shop> data = FXCollections.observableArrayList(results);
        branchTableView.setItems( data );
    }

    public void addNewBranch(){
        addBranch.setOnAction(event -> {
            if(!city.getText().isEmpty()){
                Shop temp = new Shop( city.getText(),0 );
                session.beginTransaction();
                session.save( temp );
                session.getTransaction().commit();
                loadData();
            }
            city.clear();
            city.setPromptText( "Wpisz miejscowosc!" );
        } );
    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        currentlyLoggedIn.setText( loginScene_controller.getNameTemp() + " " + loginScene_controller.getSurnameTemp() );
    }

}
