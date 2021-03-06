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
        private Label obecnieZalogowany;

        @FXML
        private TextField showPracownik;

        @FXML
        private TextField showOddzial;

        @FXML
        private TextField showNumerFaktury;

        @FXML
        private TextField podsumowanieCeny;

        @FXML
        private Button zrealizujButton;

        @FXML
        private ChoiceBox<String> chooseZamowienie;

        @FXML
        private TableView<CzescSamochodowa> cart_table;

        @FXML
        private TableColumn<CzescSamochodowa, String> nazwa_col;

        @FXML
        private TableColumn<CzescSamochodowa, Integer> cena_col;

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
                showNumerFaktury.setEditable( false );
                showOddzial.setEditable( false );
                showPracownik.setEditable( false );
                realize();
        }

        public void initCols() {
                cena_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa, Integer>( "cena" ) );
                nazwa_col.setCellValueFactory( new PropertyValueFactory<CzescSamochodowa,String>( "nazwa_czesci"  ) );
        }

        public void loadData(int id_zam){
                Zamowienie zamowienie = session.get( Zamowienie.class, id_zam );
                List<CzescSamochodowa> resultList = zamowienie.getCzescSamochodowa();
                ObservableList<CzescSamochodowa> data = FXCollections.observableList(resultList);
                cart_table.setItems( data );
                initCols();

        }

        public void  currentlyLogged() {
                LoginScene_Controller loginScene_controller = new LoginScene_Controller();
                obecnieZalogowany.setText( loginScene_controller.getImietemp() + " " + loginScene_controller.getNazwiskotemp() );
        }

        public void realize() {
                List<Zamowienie> zamowienieList = session.createQuery( "FROM Zamowienie" ).getResultList();
                ObservableList<Zamowienie> zamowienieObservableList = FXCollections.observableArrayList(zamowienieList);
                int i = 0;
                while(i < zamowienieObservableList.size()){
                        chooseZamowienie.getItems().add("Numer zamowienia: " + String.valueOf( zamowienieObservableList.get( i ).getId_zamowienia()) + ", Status realizacji: "
                                + zamowienieObservableList.get( i ).getZrealizowano() );
                        i++;
                }

                chooseZamowienie.setOnAction( event -> {
                        int id_zam = chooseZamowienie.getSelectionModel().getSelectedIndex() + 1;
                        loadData( id_zam );
                        Zamowienie temp = session.get( Zamowienie.class,id_zam );
                        showPracownik.setText( temp.getPracownik().getImie() + " " + temp.getPracownik().getNazwisko() );
                        showOddzial.setText( temp.getPracownik().getSklep().getMiejscowosc() );
                        showNumerFaktury.setText( String.valueOf( id_zam ) );
                        List<CzescSamochodowa> kosztCalkowityList = temp.getCzescSamochodowa();
                        int a = 0;
                        float kosztCalkowity = 0;
                        while(a < kosztCalkowityList.size()){
                                kosztCalkowity = kosztCalkowity + kosztCalkowityList.get( a ).getCena();
                                a++;
                        }
                        final float obrot = kosztCalkowity;
                        podsumowanieCeny.setText( String.valueOf( "Podsumowanie: " + kosztCalkowity + " zl" ) );
                        zrealizujButton.setOnAction( event1 -> {
                                if(!temp.getZrealizowano().equals( "Zrealizowano" )){
                                        Sklep tempSklep = session.get( Sklep.class, temp.getPracownik().getSklep().getId_sklepu() );
                                        Transaction transaction = session.beginTransaction( );
                                        temp.setZrealizowano( "Zrealizowano" );
                                        tempSklep.setObrot( (tempSklep.getObrot() + obrot) );
                                        transaction.commit( );
                                }
                        } );
                } );

                showNumerFaktury.setEditable( false );
                showOddzial.setEditable( false );
                showPracownik.setEditable( false );


        }
}
