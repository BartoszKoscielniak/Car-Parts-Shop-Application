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
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Cart_Controller implements Initializable {

        Hibernate_Controller hibernate_controller = new Hibernate_Controller();
        Session session = hibernate_controller.getSession();

        @FXML
        private Label currentlyLoggedIn;

        @FXML
        private TextField showWorker;

        @FXML
        private TextField showBranch;

        @FXML
        private TextField invoiceNumber;

        @FXML
        private TextField priceSummary;

        @FXML
        private Button realizeButton;

        @FXML
        private ChoiceBox<String> chooseOrder;

        @FXML
        private TableView<CarParts> cart_table;

        @FXML
        private TableColumn<CarParts, String> name_col;

        @FXML
        private TableColumn<CarParts, Integer> price_col;

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
                currentlyLogged();
                invoiceNumber.setEditable( false );
                showBranch.setEditable( false );
                showWorker.setEditable( false );
                realize();
        }

        public void initCols() {
                price_col.setCellValueFactory( new PropertyValueFactory<CarParts, Integer>( "price" ) );
                name_col.setCellValueFactory( new PropertyValueFactory<CarParts,String>( "part_name"  ) );
        }

        public void loadData(int id_zam){
                Order order = session.get( Order.class, id_zam );
                List<CarParts> resultList = order.getCarParts();
                ObservableList<CarParts> data = FXCollections.observableList(resultList);
                cart_table.setItems( data );
                initCols();

        }

        public void  currentlyLogged() {
                LoginScene_Controller loginScene_controller = new LoginScene_Controller();
                currentlyLoggedIn.setText( loginScene_controller.getNameTemp() + " " + loginScene_controller.getSurnameTemp() );
        }

        public void realize() {
                List<Order> orderList = session.createQuery( "FROM Order" ).getResultList();
                ObservableList<Order> orderObservableList = FXCollections.observableArrayList(orderList);
                int i = 0;
                while(i < orderObservableList.size()){
                        chooseOrder.getItems().add("Numer zamowienia: " + String.valueOf( orderObservableList.get( i ).getId_order()) + ", Status realizacji: "
                                + orderObservableList.get( i ).getIfCompleted() );
                        i++;
                }

                chooseOrder.setOnAction(event -> {
                        int id_zam = chooseOrder.getSelectionModel().getSelectedIndex() + 1;
                        loadData( id_zam );
                        Order temp = session.get( Order.class,id_zam );
                        showWorker.setText( temp.getWorker().getName() + " " + temp.getWorker().getSurname() );
                        showBranch.setText( temp.getWorker().getShop().getCity() );
                        invoiceNumber.setText( String.valueOf( id_zam ) );
                        List<CarParts> kosztCalkowityList = temp.getCarParts();
                        int a = 0;
                        float kosztCalkowity = 0;
                        while(a < kosztCalkowityList.size()){
                                kosztCalkowity = kosztCalkowity + kosztCalkowityList.get( a ).getPrice();
                                a++;
                        }
                        final float obrot = kosztCalkowity;
                        priceSummary.setText( String.valueOf( "Podsumowanie: " + kosztCalkowity + " zl" ) );
                        realizeButton.setOnAction(event1 -> {
                                if(!temp.getIfCompleted().equals( "Zrealizowano" )){
                                        Shop tempShop = session.get( Shop.class, temp.getWorker().getShop().getId_shop() );
                                        Transaction transaction = session.beginTransaction( );
                                        temp.setIfCompleted( "Zrealizowano" );
                                        tempShop.setTurnover( (tempShop.getTurnover() + obrot) );
                                        transaction.commit( );
                                }
                        } );
                } );

                invoiceNumber.setEditable( false );
                showBranch.setEditable( false );
                showWorker.setEditable( false );


        }
}
