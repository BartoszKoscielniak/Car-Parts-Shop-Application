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
    private TableView<Pracownik> workerTableView;

    @FXML
    private TableColumn<Pracownik, String> imie_col;

    @FXML
    private TableColumn<Pracownik, String> nazwisko_col;

    @FXML
    private TableColumn<Pracownik, DateCell> data_col;

    @FXML
    private TableColumn<Pracownik, Integer> telefon_col;

    @FXML
    private TableColumn<Pracownik, Integer> pesel_col;

    @FXML
    private TableColumn<Pracownik, Integer> zarobki_col;

    @FXML
    private TableColumn<Pracownik, String> miejscowosc_col;

    @FXML
    private TableColumn<Pracownik, String> usunButton_col;

    @FXML
    private Label obecnieZalogowany;

    @FXML
    private DatePicker insertDataUrodzenia;

    @FXML
    private Button dodajButton;

    @FXML
    private ChoiceBox<String> oddzialList;

    @FXML
    private TextField insertImie;

    @FXML
    private TextField insertNazwisko;

    @FXML
    private TextField insertNrTele;

    @FXML
    private TextField insertPesel;

    @FXML
    private TextField insertZarobki;

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
        imie_col.setCellValueFactory( new PropertyValueFactory<Pracownik,String>( "Imie" ) );
        nazwisko_col.setCellValueFactory( new PropertyValueFactory<Pracownik,String>( "Nazwisko" ) );
        telefon_col.setCellValueFactory( new PropertyValueFactory<Pracownik,Integer>( "Nr_telefonu" ) );
        pesel_col.setCellValueFactory( new PropertyValueFactory<Pracownik,Integer>( "Pesel" ) );
        zarobki_col.setCellValueFactory( new PropertyValueFactory<Pracownik,Integer>( "Zarobki" ) );

        Callback<TableColumn<Pracownik, String>, TableCell<Pracownik, String>> cellFactory
                = //
                new Callback<TableColumn<Pracownik, String>, TableCell<Pracownik, String>>() {

                    @Override
                    public TableCell call(final TableColumn<Pracownik, String> param) {
                        final TableCell<Pracownik, String> cell = new TableCell<Pracownik, String>() {

                            final Button btn = new Button("Usun");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    btn.setOnAction(event -> {
                                        Pracownik pracownik = getTableView().getItems().get(getIndex());
                                        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
                                        if(!(loginScene_controller.getIdZalogowanegoPracownika() == pracownik.getId_prac())){

                                                Transaction transaction = session.beginTransaction();
                                                Query query = session.createQuery( "DELETE FROM Pracownik WHERE id_prac ='" + pracownik.getId_prac( ) + "'" );
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

        Callback<TableColumn<Pracownik, DateCell>, TableCell<Pracownik, DateCell>> datePickerFactory
                = //
                new Callback<TableColumn<Pracownik, DateCell>, TableCell<Pracownik, DateCell>>() {
                    @Override
                    public TableCell call(final TableColumn<Pracownik, DateCell> param) {
                        final TableCell<Pracownik, String> cell = new TableCell<Pracownik, String>() {
                            final DatePicker datePicker = new DatePicker(  );

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Pracownik pracownik = getTableView().getItems().get(getIndex());
                                    Pracownik temp = session.get( Pracownik.class,pracownik.getId_prac() );
                                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                    LocalDate localDate = LocalDate.parse(temp.getData_zatrudnienia(), dateTimeFormatter);
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

        Callback<TableColumn<Pracownik, String>, TableCell<Pracownik, String>> miejscowoscCellFactory
                = //
                new Callback<TableColumn<Pracownik, String>, TableCell<Pracownik, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Pracownik, String> param) {
                        final TableCell<Pracownik, String> cell = new TableCell<Pracownik, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    Pracownik temp = getTableView().getItems( ).get( getIndex());
                                    Pracownik temp1 = session.get( Pracownik.class,temp.getId_prac() );
                                    setText( temp1.getSklep().getMiejscowosc() );
                                }
                            }
                        };
                        return cell;
                    }
                };


        data_col.setCellFactory(datePickerFactory);
        miejscowosc_col.setCellFactory(miejscowoscCellFactory);
        usunButton_col.setCellFactory(cellFactory);
    }

    public void loadData(){
        List<Pracownik> results = session.createQuery( "From Pracownik" ).getResultList();
        ObservableList<Pracownik> data = FXCollections.observableArrayList(results);
        workerTableView.setItems(data);
    }

    public void addNewWorker(){
        List<Sklep> oddzial = session.createQuery( "FROM Sklep" ).getResultList();
        ObservableList<Sklep> oddzialObservableList = FXCollections.observableArrayList(oddzial);
        int i = 0;
        while(i < oddzialObservableList.size()) {
            oddzialList.getItems( ).add( oddzialObservableList.get( i ).getMiejscowosc() );
            i++;
        }
            dodajButton.setOnAction( event -> {

                if(!insertImie.getText().isEmpty() && !insertNazwisko.getText().isEmpty() && !insertNrTele.getText().isEmpty() && !insertPesel.getText().isEmpty() && !insertZarobki.getText().isEmpty() && insertDataUrodzenia.getValue() != null && oddzialList.getValue() != null){
                    String date = insertDataUrodzenia.getValue( ).format( DateTimeFormatter.ofPattern( "dd-MM-yyyy" ) );
                    Transaction transaction = session.beginTransaction();
                    Pracownik tempPrac = new Pracownik( insertImie.getText(), insertNazwisko.getText(),date, Long.parseLong( insertNrTele.getText()), Long.parseLong( insertPesel.getText() ), Double.parseDouble( insertZarobki.getText() ) );
                    Sklep tempSkl = session.get( Sklep.class, oddzial.get(oddzialList.getSelectionModel().getSelectedIndex()).getId_sklepu() );
                    tempPrac.setSklep( tempSkl );
                    session.save( tempPrac );
                    transaction.commit();
                    loadData( );
                }


            } );

    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        obecnieZalogowany.setText( loginScene_controller.getImietemp() + " " + loginScene_controller.getNazwiskotemp() );
    }

}
