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
    private static int idZalogowanegoPracownika;
    private static String imietemp;
    private static String nazwiskotemp;
    private static String data_zatrudnieniatemp;
    private static double nr_telefonutemp;
    private static double peseltemp;
    private static double zarobkitemp;
    private static boolean logged = false;

    Hibernate_Controller hibernate_controller = new Hibernate_Controller();
    Session session = hibernate_controller.getSession();
    List<Pracownik> pracownikList = session.createQuery( "FROM Pracownik" ).getResultList();

    @FXML
    private PasswordField peselPasswd;

    @FXML
    private TextField nazwiskoText;

    @FXML
    private Button zalogujButton;

    @FXML
    void jakZalogowacMouseClicked(MouseEvent event) {
        alert.setTitle( "Jak sie zalogowac?" );
        alert.setContentText( "Do testu prosze uzyc: \n Nazwisko: Kowalski \n Pesel: 79128348" );
        alert.setHeaderText( null );
        alert.show();
    }

    @Override
    public void initialize ( URL location , ResourceBundle resources ) {

        int i = 0;
        boolean znaleziono = false;
        while(i < pracownikList.size()) {
            if(pracownikList.get( i ).getNazwisko().equals( "qwe" ) && pracownikList.get( i ).getPesel() == 123){
                znaleziono = true;
            }
            i++;
        }
        login();

        if(znaleziono == false){
            Pracownik pracownik = new Pracownik( "Kamil" , "qwe" , "06-06-2020" , 519344934 , 123 , 3000 );
            Sklep sklep = new Sklep( "Warszawa" , 0 );
            Transaction transaction = session.beginTransaction( );
            pracownik.setSklep( sklep );
            session.save( pracownik );
            session.save( sklep );
            transaction.commit( );
        }
    }

    public void login() {

        zalogujButton.setOnAction( new EventHandler<ActionEvent>( ) {
            @Override
            public void handle ( ActionEvent event ) {
                int i = 0;
                while(i < pracownikList.size()) {
                    if ( ( nazwiskoText.getText( ) ).equals( pracownikList.get( i ).getNazwisko( ) ) && Integer.parseInt( peselPasswd.getText( ) ) == pracownikList.get( i ).getPesel( ) ){
                        Parent root = null;
                        idZalogowanegoPracownika = pracownikList.get( i ).getId_prac();
                        imietemp = pracownikList.get( i ).getImie();
                        nazwiskotemp = pracownikList.get( i ).getNazwisko();
                        data_zatrudnieniatemp = pracownikList.get( i ).getData_zatrudnienia();
                        nr_telefonutemp = pracownikList.get( i ).getNr_telefonu();
                        peseltemp = pracownikList.get( i ).getPesel();
                        zarobkitemp = pracownikList.get( i ).getZarobki();
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
                    nazwiskoText.clear( );
                    peselPasswd.clear( );
                }
            }
        } );
    }

    public static int getIdZalogowanegoPracownika () {
        return idZalogowanegoPracownika;
    }

    public static String getImietemp () {
        return imietemp;
    }

    public static String  getNazwiskotemp () {
        return nazwiskotemp;
    }

    public static String getData_zatrudnieniatemp () {
        return data_zatrudnieniatemp;
    }

    public static double getNr_telefonutemp () {
        return nr_telefonutemp;
    }

    public static double getPeseltemp () {
        return peseltemp;
    }

    public static double getZarobkitemp () {
        return zarobkitemp;
    }
}
