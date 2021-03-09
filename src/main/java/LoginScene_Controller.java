import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LoginScene_Controller implements Initializable {

    private Alert alert = new Alert(AlertType.INFORMATION);
    private Alert wrongLoginPsswd = new Alert(AlertType.ERROR);
    private static int LoggedInWorkerID;
    private static String nameTemp;
    private static String surnameTemp;
    private static String employment_data;
    private static double telephoneNumberTemp;
    private static double peselTemp;
    private static double earningsTemp;
    private static boolean logged = false;

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();
    List<Workers> workersList = session.createQuery( "FROM Workers" ).getResultList();

    @FXML
    private PasswordField peselPasswd;

    @FXML
    private TextField surnameText;

    @FXML
    private Button LogInButton;

    @FXML
    void howToLogIn(MouseEvent event) {
        alert.setTitle( "Jak sie zalogowac?" );
        alert.setContentText( "Do testu prosze uzyc: \n Nazwisko: qwe \n Pesel: 123" );
        alert.setHeaderText( null );
        alert.show();
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {

        int i = 0;
        boolean found = false;
        while(i < workersList.size()) {
            if(workersList.get( i ).getSurname().equals( "qwe" ) && workersList.get( i ).getPesel() == 123){
                found = true;
            }
            i++;
        }
        login();

        if(found == false){
            Workers workers = new Workers( "Kamil" , "qwe" , "06-06-2020" , 519344934 , 123 , 3000 );
            Shop shop = new Shop( "Warszawa" , 0 );
            Transaction transaction = session.beginTransaction( );
            workers.setSklep(shop);
            session.save(workers);
            session.save(shop);
            transaction.commit( );
        }
    }

    public void login() {

        LogInButton.setOnAction(new EventHandler<ActionEvent>( ) {
            @Override
            public void handle ( ActionEvent event ) {
                int i = 0;
                while(i < workersList.size()) {
                    if ( ( surnameText.getText( ) ).equals( workersList.get( i ).getSurname( ) ) && Integer.parseInt( peselPasswd.getText( ) ) == workersList.get( i ).getPesel( ) ){
                        Parent root = null;
                        LoggedInWorkerID = workersList.get( i ).getId_worker();
                        nameTemp = workersList.get( i ).getName();
                        surnameTemp = workersList.get( i ).getSurname();
                        employment_data = workersList.get( i ).getDateOfEmployment();
                        telephoneNumberTemp = workersList.get( i ).getPhoneNumber();
                        peselTemp = workersList.get( i ).getPesel();
                        earningsTemp = workersList.get( i ).getEarings();
                        try {
                            root = FXMLLoader.load( getClass( ).getResource( "View/main.fxml" ) );
                        } catch (IOException e) {
                            e.printStackTrace( );
                        }
                        Scene scene = new Scene( root );
                        Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                        stage.setScene( scene );
                        stage.show( );
                        logged = true;
                        session.close( );
                        break;
                    }
                    i++;
                }
                if(!logged){
                    wrongLoginPsswd.setTitle( "Error" );
                    wrongLoginPsswd.setContentText( "Podaj prawidlowy login lub haslo" );
                    wrongLoginPsswd.show( );
                    surnameText.clear( );
                    peselPasswd.clear( );
                }
            }
        } );
    }

    public static int getLoggedInWorkerID() {
        return LoggedInWorkerID;
    }

    public static String getNameTemp() {
        return nameTemp;
    }

    public static String getSurnameTemp() {
        return surnameTemp;
    }

    public static String getEmployment_data() {
        return employment_data;
    }

    public static double getTelephoneNumberTemp() {
        return telephoneNumberTemp;
    }

    public static double getPeselTemp() {
        return peselTemp;
    }

    public static double getEarningsTemp() {
        return earningsTemp;
    }
}
