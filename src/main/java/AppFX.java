import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppFX extends Application {
    @Override
    public void start ( Stage primaryStage ) throws Exception {
        Parent root  = FXMLLoader.load( getClass().getClassLoader().getResource( "View/loginScene.fxml" ) );
        primaryStage.setTitle( "Car Parts Shop" );
        primaryStage.setResizable( false );
        primaryStage.setScene( new Scene( root ) );
        primaryStage.show();
    }
}
