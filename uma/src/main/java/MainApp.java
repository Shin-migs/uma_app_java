
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL fxmlUrl = getClass().getClassLoader().getResource("main.fxml");
        if (fxmlUrl == null) {
            throw new RuntimeException("Cannot find main.fxml - check it is in src/main/resources");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Scene scene = new Scene(loader.load());
        
        stage.getIcons().add(new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg=="));

        stage.setTitle("Uma Musume Roster Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
 