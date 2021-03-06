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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShopScene_Controller implements Initializable {

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();

    List<CzescSamochodowa> resultList = session.createQuery( "FROM CzescSamochodowa" ).getResultList();
    ObservableList<CzescSamochodowa> data = FXCollections.observableList(resultList);

    Alert createCart = new Alert( Alert.AlertType.ERROR );

    @FXML
    private Label obecnieZalogowany;

    @FXML
    private TextField insertSeryjny;

    @FXML
    private TextField odTextArea;

    @FXML
    private TextField doTextArea;

    @FXML
    private Button zastosujBttn;

    @FXML
    private TableView<CzescSamochodowa> shopTableView;

    @FXML
    private TableColumn<CzescSamochodowa, String> kategoria_col;

    @FXML
    private TableColumn<CzescSamochodowa, String> nazwa_col;

    @FXML
    private TableColumn<CzescSamochodowa, Integer> numerSeryjny_col;

    @FXML
    private TableColumn<CzescSamochodowa, Boolean> dostepnosc_col;

    @FXML
    private TableColumn<CzescSamochodowa,Integer> cena_col;

    @FXML
    private TableColumn<CzescSamochodowa, Integer> ilosc_col;

    @FXML
    private TableColumn<CzescSamochodowa, String> dodajDoKoszykaButton;

    @FXML
    private Button nowyKoszyk;

    @FXML
    private Button wyczyscKryteria;

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
        filtering();
        currentlyLogged();
    }

    public void initCols() {
        nazwa_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa, String>( "nazwa_czesci" ) );
        numerSeryjny_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa, Integer>( "numer_seryjny" ) );
        dostepnosc_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa,Boolean>( "dostepnosc" ) );
        cena_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa,Integer>( "cena" ) );
        ilosc_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa,Integer>( "ilosc" ) );

        Callback<TableColumn<CzescSamochodowa, String>, TableCell<CzescSamochodowa, String>> kategoriaCellFactory
                = //
                new Callback<TableColumn<CzescSamochodowa, String>, TableCell<CzescSamochodowa, String>>() {
                    @Override
                    public TableCell call(final TableColumn<CzescSamochodowa, String> param) {
                        final TableCell<CzescSamochodowa, String> cell = new TableCell<CzescSamochodowa, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    CzescSamochodowa czescSamochodowa = getTableView().getItems( ).get( getIndex());
                                    CzescSamochodowa temp = session.get( CzescSamochodowa.class,czescSamochodowa.getId_czesci() );
                                    setText( temp.getKategoria().getNazwa_kategorii() );
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<CzescSamochodowa, String>, TableCell<CzescSamochodowa, String>> addButtoncellFactory
                = //
                new Callback<TableColumn<CzescSamochodowa, String>, TableCell<CzescSamochodowa, String>>() {

                    @Override
                    public TableCell call(final TableColumn<CzescSamochodowa, String> param) {
                        final TableCell<CzescSamochodowa, String> cell = new TableCell<CzescSamochodowa, String>() {

                            final Button btn = new Button("Dodaj do koszyka");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    CzescSamochodowa czesctemp = getTableView().getItems().get( getIndex() );
                                    if(czesctemp.getIlosc() == 0){
                                        CzescSamochodowa temp = session.get( CzescSamochodowa.class,czesctemp.getId_czesci() );
                                        Transaction transaction = session.beginTransaction();
                                        temp.setDostepnosc( "Niedostepny" );
                                        transaction.commit();
                                    }
                                    btn.setOnAction( event -> {
                                        boolean znaleziono = false;
                                        if(czesctemp.getIlosc() != 0){
                                            List<Zamowienie> zamowienieList = session.createQuery( "FROM Zamowienie" ).getResultList( );
                                            int i = 0;
                                            while (i < zamowienieList.size( )) {
                                                if ( zamowienieList.get( i ).getZrealizowano( ).equals( "Niezrealizowane" ) ){
                                                    Zamowienie tempZam = session.get( Zamowienie.class , zamowienieList.get( i ).getId_zamowienia( ) );
                                                    CzescSamochodowa tempCze = session.get( CzescSamochodowa.class , czesctemp.getId_czesci( ) );
                                                    int ilosc_czesci = tempCze.getIlosc( );
                                                    List<CzescSamochodowa> tempList = tempZam.getCzescSamochodowa( );
                                                    tempList.add( tempCze );
                                                    tempZam.setCzescSamochodowa( tempList );
                                                    Transaction transaction = session.beginTransaction( );
                                                    tempCze.setIlosc( ilosc_czesci - 1 );
                                                    session.save( tempZam );
                                                    transaction.commit( );
                                                    znaleziono = true;
                                                    break;
                                                } else {
                                                    i++;

                                                }

                                            }
                                        }
                                        if(znaleziono == false){
                                            createCart.setTitle( "Error" );
                                            createCart.setContentText( "Stworz nowy koszyk lub wybierz inny element!" );
                                            createCart.show();
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

        dodajDoKoszykaButton.setCellFactory(addButtoncellFactory);
        kategoria_col.setCellFactory( kategoriaCellFactory );
    }

    public void loadData() {
        shopTableView.setItems(data);
    }

    public void filtering() {
        nowyKoszyk.setOnAction( event -> {
            LoginScene_Controller loginScene_controller = new LoginScene_Controller();
            Pracownik tempPrac = session.get( Pracownik.class,loginScene_controller.getIdZalogowanegoPracownika() );
            Zamowienie temp = new Zamowienie(  );
            temp.setZrealizowano( "Niezrealizowane" );
            temp.setPracownik( tempPrac );
            Transaction transaction = session.beginTransaction();
            session.save( temp );
            transaction.commit();
        } );

        zastosujBttn.setOnAction( event -> {
            ObservableList<CzescSamochodowa> temp3 = FXCollections.observableArrayList(  );
            if(!odTextArea.getText().isEmpty() && !doTextArea.getText().isEmpty() && !insertSeryjny.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(Integer.parseInt( odTextArea.getText() ) <= data.get( c ).getCena() && Integer.parseInt( doTextArea.getText() ) >= data.get( c ).getCena() && data.get( c ).getNumer_seryjny() == Integer.parseInt( insertSeryjny.getText() )&& data.get( c ).getNumer_seryjny() == Integer.parseInt( insertSeryjny.getText() )){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!odTextArea.getText().isEmpty() && !insertSeryjny.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( odTextArea.getText() ) <= data.get( c ).getCena() && data.get( c ).getNumer_seryjny() == Integer.parseInt( insertSeryjny.getText() )){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!doTextArea.getText().isEmpty() && !insertSeryjny.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( doTextArea.getText() ) >= data.get( c ).getCena() && data.get( c ).getNumer_seryjny() == Integer.parseInt( insertSeryjny.getText() )){
                        temp3.add( data.get( c )  );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );

            }else if(!insertSeryjny.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(data.get( c ).getNumer_seryjny() == Integer.parseInt( insertSeryjny.getText() )){
                        temp3.add(data.get( c ));

                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            }else if(!odTextArea.getText().isEmpty() && !doTextArea.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(Integer.parseInt( odTextArea.getText() ) <= data.get( c ).getCena() && Integer.parseInt( doTextArea.getText() ) >= data.get( c ).getCena()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!odTextArea.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( odTextArea.getText() ) <= data.get( c ).getCena()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!doTextArea.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( doTextArea.getText() ) >= data.get( c ).getCena()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            }

        } );

        wyczyscKryteria.setOnAction( event -> {
            shopTableView.setItems(data);
            insertSeryjny.clear();
            odTextArea.clear();
            doTextArea.clear();
        } );
    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        obecnieZalogowany.setText( loginScene_controller.getImietemp() + " " + loginScene_controller.getNazwiskotemp() );
    }
}
