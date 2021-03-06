import controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new FXMLLoader(getClass().getResource("/view/root.fxml")).load();
        primaryStage.setTitle("Point Volleyball");
        primaryStage.setScene(new Scene(root, 625, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
