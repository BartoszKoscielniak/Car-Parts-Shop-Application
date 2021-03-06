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

public class ManagmentScene_Controller implements Initializable {

    @FXML
    private TextField insertName;

    @FXML
    private TextField insertPrice;

    @FXML
    private TextField insertAmount;

    @FXML
    private TextField insertSerialNumber;

    @FXML
    private ChoiceBox<String> chooseCategory;

    @FXML
    private Button addButton;

    @FXML
    private Label currentlyLoggedIn;

    @FXML
    private TextField insertName1;

    @FXML
    private Button addButton1;

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
        List<Category> category;
        category = session.createQuery( "FROM Category" ).getResultList();
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList(category);
        chooseCategory.getItems().clear();
        insertSerialNumber.clear();
        insertName.clear();
        insertAmount.clear();
        insertPrice.clear();
        insertName1.clear();
        int i = 0;
        while(i < categoryObservableList.size()) {
            chooseCategory.getItems( ).add( categoryObservableList.get( i ).getCategory_name()   );
            i++;
        }

        addButton.setOnAction(event -> {
            if(!insertPrice.getText().isEmpty() || !insertAmount.getText().isEmpty() || !insertName.getText().isEmpty() || !insertSerialNumber.getText().isEmpty()){
                Transaction transaction = session.beginTransaction();
                CarParts tempCze = new CarParts( insertName.getText(), Long.parseLong( insertSerialNumber.getText() ), Float.parseFloat( insertPrice.getText() ), Integer.parseInt( insertAmount.getText() ), "Dostepny" );
                Category tempKat = session.get( Category.class, category.get( chooseCategory.getSelectionModel().getSelectedIndex() ).getId_cat());
                tempCze.setKategoria( tempKat );
                session.save( tempCze  );
                transaction.commit();
            }
        } );

        addButton1.setOnAction(event -> {
            if(!insertName1.getText().isEmpty()){
                Category temp = new Category( insertName1.getText() );
                Transaction transaction = session.beginTransaction();
                session.save( temp );
                transaction.commit();
                addNewPart();
            }
        } );
    }



    public void  currentlyLogged() {
        LoginScene_Controller loginScene_controller = new LoginScene_Controller();
        currentlyLoggedIn.setText( loginScene_controller.getNameTemp() + " " + loginScene_controller.getSurnameTemp() );
    }
}
