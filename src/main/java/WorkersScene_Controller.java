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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class WorkersScene_Controller implements Initializable {

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();

    Alert alert = new Alert( Alert.AlertType.ERROR );

    @FXML
    private TableView<Workers> workerTableView;

    @FXML
    private TableColumn<Workers, String> name_col;

    @FXML
    private TableColumn<Workers, String> surname_col;

    @FXML
    private TableColumn<Workers, DateCell> date_col;

    @FXML
    private TableColumn<Workers, Integer> phoneNumber_col;

    @FXML
    private TableColumn<Workers, Integer> pesel_col;

    @FXML
    private TableColumn<Workers, Integer> earings_col;

    @FXML
    private TableColumn<Workers, String> city_col;

    @FXML
    private TableColumn<Workers, String> deleteButton_col;

    @FXML
    private Label currentlyLoggedIn;

    @FXML
    private DatePicker insertDateOfBirth;

    @FXML
    private Button addButton;

    @FXML
    private ChoiceBox<String> branchList;

    @FXML
    private TextField insertName;

    @FXML
    private TextField insertSurname;

    @FXML
    private TextField insertPhoneNumber;

    @FXML
    private TextField insertPesel;

    @FXML
    private TextField insertEarings;

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
        loadData();
        initCols();
        addNewWorker();
        currentlyLogged();
    }

    public void initCols() {
        name_col.setCellValueFactory( new PropertyValueFactory<Workers,String>( "name" ) );
        surname_col.setCellValueFactory( new PropertyValueFactory<Workers,String>( "surname" ) );
        phoneNumber_col.setCellValueFactory( new PropertyValueFactory<Workers,Integer>( "phoneNumber" ) );
        pesel_col.setCellValueFactory( new PropertyValueFactory<Workers,Integer>( "Pesel" ) );
        earings_col.setCellValueFactory( new PropertyValueFactory<Workers,Integer>( "earings" ) );

        Callback<TableColumn<Workers, String>, TableCell<Workers, String>> cellFactory
                = //
                new Callback<TableColumn<Workers, String>, TableCell<Workers, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Workers, String> param) {
                        final TableCell<Workers, String> cell = new TableCell<Workers, String>() {

                            final Button btn = new Button("Usun");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    btn.setOnAction(event -> {
                                        Workers workers = getTableView().getItems().get(getIndex());
                                        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
                                        if(!(loginScene_controller.getLoggedInWorkerID() == workers.getId_worker())){

                                                Transaction transaction = session.beginTransaction();
                                                Query query = session.createQuery( "DELETE FROM Workers WHERE id_worker ='" + workers.getId_worker( ) + "'" );
                                                query.executeUpdate( );
                                                transaction.commit();
                                                loadData( );
                                        }else{
                                            alert.setTitle( "Error" );
                                            alert.setContentText( "Nie mozna usunac zalogowanego pracownika!" );
                                            alert.show();
                                        }
                                    });

                                    setGraphic(btn);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Workers, DateCell>, TableCell<Workers, DateCell>> datePickerFactory
                = //
                new Callback<TableColumn<Workers, DateCell>, TableCell<Workers, DateCell>>() {
                    @Override
                    public TableCell call(final TableColumn<Workers, DateCell> param) {
                        final TableCell<Workers, String> cell = new TableCell<Workers, String>() {
                            final DatePicker datePicker = new DatePicker(  );

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Workers workers = getTableView().getItems().get(getIndex());
                                    Workers temp = session.get( Workers.class, workers.getId_worker() );
                                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    LocalDate localDate = LocalDate.parse(temp.getDateOfEmployment(), dateTimeFormatter);
                                    datePicker.setValue( localDate);
                                    datePicker.setEditable( false );
                                    setGraphic(datePicker);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<Workers, String>, TableCell<Workers, String>> cityCellFactory
                = //
                new Callback<TableColumn<Workers, String>, TableCell<Workers, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Workers, String> param) {
                        final TableCell<Workers, String> cell = new TableCell<Workers, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Workers temp = getTableView().getItems( ).get( getIndex());
                                    Workers temp1 = session.get( Workers.class,temp.getId_worker() );
                                    setText( temp1.getSklep().getCity() );
                                }
                            }
                        };
                        return cell;
                    }
                };


        date_col.setCellFactory(datePickerFactory);
        city_col.setCellFactory(cityCellFactory);
        deleteButton_col.setCellFactory(cellFactory);
    }

    public void loadData(){
        List<Workers> results = session.createQuery( "From Workers" ).getResultList();
        ObservableList<Workers> data = FXCollections.observableArrayList(results);
        workerTableView.setItems(data);
    }

    public void addNewWorker(){
        List<Shop> oddzial = session.createQuery( "FROM Shop" ).getResultList();
        ObservableList<Shop> oddzialObservableList = FXCollections.observableArrayList(oddzial);
        int i = 0;
        while(i < oddzialObservableList.size()) {
            branchList.getItems( ).add( oddzialObservableList.get( i ).getCity() );
            i++;
        }
            addButton.setOnAction(event -> {

                if(!insertName.getText().isEmpty() && !insertSurname.getText().isEmpty() && !insertPhoneNumber.getText().isEmpty() && !insertPesel.getText().isEmpty() && !insertEarings.getText().isEmpty() && insertDateOfBirth.getValue() != null && branchList.getValue() != null){
                    String date = insertDateOfBirth.getValue( ).format( DateTimeFormatter.ofPattern( "dd-MM-yyyy" ) );
                    Transaction transaction = session.beginTransaction();
                    Workers tempPrac = new Workers( insertName.getText(), insertSurname.getText(),date, Long.parseLong( insertPhoneNumber.getText()), Long.parseLong( insertPesel.getText() ), Double.parseDouble( insertEarings.getText() ) );
                    Shop tempSkl = session.get( Shop.class, oddzial.get(branchList.getSelectionModel().getSelectedIndex()).getId_shop() );
                    tempPrac.setSklep( tempSkl );
                    session.save( tempPrac );
                    transaction.commit();
                    loadData( );
                }


            } );

    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        currentlyLoggedIn.setText( loginScene_controller.getNameTemp() + " " + loginScene_controller.getSurnameTemp() );
    }

}
