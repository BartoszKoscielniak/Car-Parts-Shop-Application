import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class CurrentWorkerScene_Controller implements Initializable {

    @FXML
    private TextField insertNazwa;

    @FXML
    private TextField insertCena;

    @FXML
    private TextField insertIlosc;

    @FXML
    private TextField insertNumer;

    @FXML
    private ChoiceBox<String> chooseKategoria;

    @FXML
    private Button dodajButton;

    @FXML
    private Label obecniezalogowany;

    @FXML
    private TextField insertNazwa1;

    @FXML
    private Button dodajButton1;

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();

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
        addNewPart();
        currentlyLogged();
    }

    public void addNewPart(){
        List<Kategoria> kategoria;
        kategoria = session.createQuery( "FROM Kategoria" ).getResultList();
        ObservableList<Kategoria> kategoriaObservableList = FXCollections.observableArrayList(kategoria);
        chooseKategoria.getItems().clear();
        insertNumer.clear();
        insertNazwa.clear();
        insertIlosc.clear();
        insertCena.clear();
        insertNazwa1.clear();
        int i = 0;
        while(i < kategoriaObservableList.size()) {
            chooseKategoria.getItems( ).add( kategoriaObservableList.get( i ).getNazwa_kategorii()   );
            i++;
        }

        dodajButton.setOnAction( event -> {
            if(!insertCena.getText().isEmpty() || !insertIlosc.getText().isEmpty() || !insertNazwa.getText().isEmpty() || !insertNumer.getText().isEmpty()){
                Transaction transaction = session.beginTransaction();
                CzescSamochodowa tempCze = new CzescSamochodowa( insertNazwa.getText(), Long.parseLong( insertNumer.getText() ), Float.parseFloat( insertCena.getText() ), Integer.parseInt( insertIlosc.getText() ), "Dostepny" );
                Kategoria tempKat = session.get( Kategoria.class, kategoria.get( chooseKategoria.getSelectionModel().getSelectedIndex() ).getId_kat());
                tempCze.setKategoria( tempKat );
                session.save( tempCze  );
                transaction.commit();
            }
        } );

        dodajButton1.setOnAction( event -> {
            if(!insertNazwa1.getText().isEmpty()){
                Kategoria temp = new Kategoria( insertNazwa1.getText() );
                Transaction transaction = session.beginTransaction();
                session.save( temp );
                transaction.commit();
                addNewPart();
            }
        } );
    }



    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        obecniezalogowany.setText( loginScene_controller.getImietemp() + " " + loginScene_controller.getNazwiskotemp() );
    }
}
