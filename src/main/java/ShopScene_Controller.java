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

    List<CarParts> resultList = session.createQuery( "FROM CarParts" ).getResultList();
    ObservableList<CarParts> data = FXCollections.observableList(resultList);

    Alert createCart = new Alert( Alert.AlertType.ERROR );

    @FXML
    private Label currentlyLoggedIn;

    @FXML
    private TextField insertSerialNumber;

    @FXML
    private TextField fromTextArea;

    @FXML
    private TextField toTextArea;

    @FXML
    private Button applyButton;

    @FXML
    private TableView<CarParts> shopTableView;

    @FXML
    private TableColumn<CarParts, String> category_col;

    @FXML
    private TableColumn<CarParts, String> name_col;

    @FXML
    private TableColumn<CarParts, Integer> serialNumber_col;

    @FXML
    private TableColumn<CarParts, Boolean> availability_col;

    @FXML
    private TableColumn<CarParts,Integer> price_col;

    @FXML
    private TableColumn<CarParts, Integer> amount_col;

    @FXML
    private TableColumn<CarParts, String> addToCartButton;

    @FXML
    private Button newCart;

    @FXML
    private Button clearCriteria;

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
        name_col.setCellValueFactory( new PropertyValueFactory<CarParts, String>( "part_name" ) );
        serialNumber_col.setCellValueFactory( new PropertyValueFactory<CarParts, Integer>( "serial_number" ) );
        availability_col.setCellValueFactory( new PropertyValueFactory<CarParts,Boolean>( "availability" ) );
        price_col.setCellValueFactory( new PropertyValueFactory<CarParts,Integer>( "price" ) );
        amount_col.setCellValueFactory( new PropertyValueFactory<CarParts,Integer>( "amount" ) );

        Callback<TableColumn<CarParts, String>, TableCell<CarParts, String>> categoryCellFactory
                = //
                new Callback<TableColumn<CarParts, String>, TableCell<CarParts, String>>() {
                    @Override
                    public TableCell call(final TableColumn<CarParts, String> param) {
                        final TableCell<CarParts, String> cell = new TableCell<CarParts, String>() {

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    CarParts carParts = getTableView().getItems( ).get( getIndex());
                                    CarParts temp = session.get( CarParts.class, carParts.getId_part() );
                                    setText( temp.getKategoria().getCategory_name() );
                                }
                            }
                        };
                        return cell;
                    }
                };

        Callback<TableColumn<CarParts, String>, TableCell<CarParts, String>> addButtonCellFactory
                = //
                new Callback<TableColumn<CarParts, String>, TableCell<CarParts, String>>() {

                    @Override
                    public TableCell call(final TableColumn<CarParts, String> param) {
                        final TableCell<CarParts, String> cell = new TableCell<CarParts, String>() {

                            final Button btn = new Button("Dodaj do koszyka");

                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    CarParts czesctemp = getTableView().getItems().get( getIndex() );
                                    if(czesctemp.getAmount() == 0){
                                        CarParts temp = session.get( CarParts.class,czesctemp.getId_part() );
                                        Transaction transaction = session.beginTransaction();
                                        temp.setAvailability( "Niedostepny" );
                                        transaction.commit();
                                    }
                                    btn.setOnAction( event -> {
                                        boolean znaleziono = false;
                                        if(czesctemp.getAmount() != 0){
                                            List<Order> orderList = session.createQuery( "FROM Order" ).getResultList( );
                                            int i = 0;
                                            while (i < orderList.size( )) {
                                                if ( orderList.get( i ).getIfCompleted( ).equals( "Niezrealizowane" ) ){
                                                    Order tempZam = session.get( Order.class , orderList.get( i ).getId_order( ) );
                                                    CarParts tempCze = session.get( CarParts.class , czesctemp.getId_part( ) );
                                                    int ilosc_czesci = tempCze.getAmount( );
                                                    List<CarParts> tempList = tempZam.getCzescSamochodowa( );
                                                    tempList.add( tempCze );
                                                    tempZam.setCzescSamochodowa( tempList );
                                                    Transaction transaction = session.beginTransaction( );
                                                    tempCze.setAmount( ilosc_czesci - 1 );
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

        addToCartButton.setCellFactory(addButtonCellFactory);
        category_col.setCellFactory( categoryCellFactory );
    }

    public void loadData() {
        shopTableView.setItems(data);
    }

    public void filtering() {
        newCart.setOnAction(event -> {
            LoginScene_Controller loginScene_controller = new LoginScene_Controller();
            Workers tempPrac = session.get( Workers.class,loginScene_controller.getLoggedInWorkerID() );
            Order temp = new Order(  );
            temp.setIfCompleted( "Niezrealizowane" );
            temp.setPracownik( tempPrac );
            Transaction transaction = session.beginTransaction();
            session.save( temp );
            transaction.commit();
        } );

        applyButton.setOnAction(event -> {
            ObservableList<CarParts> temp3 = FXCollections.observableArrayList(  );
            if(!fromTextArea.getText().isEmpty() && !toTextArea.getText().isEmpty() && !insertSerialNumber.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(Integer.parseInt( fromTextArea.getText() ) <= data.get( c ).getPrice() && Integer.parseInt( toTextArea.getText() ) >= data.get( c ).getPrice() && data.get( c ).getSerial_number() == Integer.parseInt( insertSerialNumber.getText() )&& data.get( c ).getSerial_number() == Integer.parseInt( insertSerialNumber.getText() )){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!fromTextArea.getText().isEmpty() && !insertSerialNumber.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( fromTextArea.getText() ) <= data.get( c ).getPrice() && data.get( c ).getSerial_number() == Integer.parseInt( insertSerialNumber.getText() )){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!toTextArea.getText().isEmpty() && !insertSerialNumber.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( toTextArea.getText() ) >= data.get( c ).getPrice() && data.get( c ).getSerial_number() == Integer.parseInt( insertSerialNumber.getText() )){
                        temp3.add( data.get( c )  );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );

            }else if(!insertSerialNumber.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(data.get( c ).getSerial_number() == Integer.parseInt( insertSerialNumber.getText() )){
                        temp3.add(data.get( c ));

                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            }else if(!fromTextArea.getText().isEmpty() && !toTextArea.getText().isEmpty()){
                int c = 0;
                while(c < data.size()){
                    if(Integer.parseInt( fromTextArea.getText() ) <= data.get( c ).getPrice() && Integer.parseInt( toTextArea.getText() ) >= data.get( c ).getPrice()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!fromTextArea.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( fromTextArea.getText() ) <= data.get( c ).getPrice()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            } else if(!toTextArea.getText().isEmpty()) {
                int c = 0;
                while(c < data.size()) {
                    if(Integer.parseInt( toTextArea.getText() ) >= data.get( c ).getPrice()){
                        temp3.add( data.get( c ) );
                    }
                    c++;
                }
                shopTableView.setItems( temp3 );
            }

        } );

        clearCriteria.setOnAction(event -> {
            shopTableView.setItems(data);
            insertSerialNumber.clear();
            fromTextArea.clear();
            toTextArea.clear();
        } );
    }

    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        currentlyLoggedIn.setText( loginScene_controller.getNameTemp() + " " + loginScene_controller.getSurnameTemp() );
    }
}
