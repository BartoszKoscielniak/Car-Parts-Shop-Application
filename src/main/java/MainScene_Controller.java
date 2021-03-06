import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScene_Controller implements Initializable {

        Hibernate_Controller hibernate_controller = new Hibernate_Controller( );
        Session session = hibernate_controller.getSession( );

        @FXML
        private Label obecnieZalogowany;

        @FXML
        private Label nazwaOddzialu;

        @FXML
        void branchButton ( MouseEvent event ) throws IOException {
                Parent root = FXMLLoader.load( getClass( ).getResource( "View/branchScene.fxml" ) );
                Scene scene = new Scene( root );
                Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                stage.setScene( scene );
                stage.show( );
        }

        @FXML
        void cartButton ( MouseEvent event ) throws IOException {
                Parent root = FXMLLoader.load( getClass( ).getResource( "View/cartScene.fxml" ) );
                Scene scene = new Scene( root );
                Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                stage.setScene( scene );
                stage.show( );
        }

        @FXML
        void currentWorkerButton ( MouseEvent event ) throws IOException {
                Parent root = FXMLLoader.load( getClass( ).getResource( "View/currentWorkerScene.fxml" ) );
                Scene scene = new Scene( root );
                Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                stage.setScene( scene );
                stage.show( );
        }

        @FXML
        void shopButton ( MouseEvent event ) throws IOException {
                Parent root = FXMLLoader.load( getClass( ).getResource( "View/shopScene.fxml" ) );
                Scene scene = new Scene( root );
                Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                stage.setScene( scene );
                stage.show( );
        }

        @FXML
        void workersButton ( MouseEvent event ) throws IOException {
                Parent root = FXMLLoader.load( getClass( ).getResource( "View/workersScene.fxml" ) );
                Scene scene = new Scene( root );
                Stage stage = (Stage) ( (Node) event.getSource( ) ).getScene( ).getWindow( );
                stage.setScene( scene );
                stage.show( );
        }

        @Override
        public void initialize ( URL location , ResourceBundle resources ) {
                setObecnieZalogowany();
        }

        public void setObecnieZalogowany () {
                Pracownik temp = session.get( Pracownik.class,LoginScene_Controller.getIdZalogowanegoPracownika() );
                obecnieZalogowany.setText( LoginScene_Controller.getImietemp() + " " + LoginScene_Controller.getNazwiskotemp() );
                nazwaOddzialu.setText( temp.getSklep().getMiejscowosc() );
        }
}